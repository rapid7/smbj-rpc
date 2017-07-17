/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.transport;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.mssmb2.messages.SMB2CreateResponse;
import com.hierynomus.protocol.commons.concurrent.Futures;
import com.hierynomus.smbj.common.SMBApiException;
import com.hierynomus.smbj.common.SMBException;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.connection.NegotiatedProtocol;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.Share;
import com.hierynomus.smbj.share.TreeConnect;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.Interface;
import com.rapid7.client.dcerpc.RPCResponse;
import com.rapid7.client.dcerpc.messages.Bind;
import com.rapid7.client.dcerpc.messages.BindACK;
import com.rapid7.client.smb2.SMB2ImpersonationLevel;
import com.rapid7.client.smb2.messages.SMB2CreateRequest;

public enum SMBTransportFactories {
    WINREG("winreg", Interface.WINREG_V1_0, Interface.NDR_32BIT_V2),
    SRVSVC("srvsvc", Interface.SRVSVC_V3_0, Interface.NDR_32BIT_V2);

    private final static int STATUS_PIPE_NOT_AVAILABLE_BACKOFF_TIME_MS = 3000;
    private final static int STATUS_PIPE_NOT_AVAILABLE_RETRIES = 1;
    private final String path;
    private final Interface abstractSyntax;
    private final Interface transferSyntax;

    private SMBTransportFactories(final String path, final Interface abstractSyntax, final Interface transferSyntax) {
        this.path = path;
        this.abstractSyntax = abstractSyntax;
        this.transferSyntax = transferSyntax;
    }

    public RPCTransport getTransport(final Session session)
        throws IOException {
        final Connection connection = session.getConnection();
        final NegotiatedProtocol negotiatedProtocol = connection.getNegotiatedProtocol();
        final SMB2Dialect dialect = negotiatedProtocol.getDialect();
        final long sessionID = session.getSessionId();
        final Share share = session.connectShare("IPC$");
        final TreeConnect tree = share.getTreeConnect();
        final long treeID = tree.getTreeId();
        final SMB2FileId fileID = openNamedPipe(share, path, STATUS_PIPE_NOT_AVAILABLE_RETRIES);
        final SMBTransport transport = new SMBTransport(session, dialect, fileID, sessionID, treeID);

        final Bind bind = new Bind(abstractSyntax, transferSyntax);
        final RPCResponse response = transport.transact(bind);

        if (response instanceof BindACK) {
            return new SMBTransport(session, dialect, fileID, sessionID, treeID);
        }

        throw new TransportException(
            String.format("BIND %s (%s -> %s) failed.", abstractSyntax.getName(), path, abstractSyntax.getRepr()));
    }

    private static SMB2FileId openNamedPipe(final Share share, final String path, final int retryLimit)
        throws IOException {
        final Queue<SMBApiException> exceptions = new LinkedList<>();
        for (int retry = -1; retry < retryLimit; retry++) {
            try {
                return openNamedPipe(share, path);
            } catch (final SMBApiException exception) {
                exceptions.add(exception);
                switch (exception.getStatus()) {
                case STATUS_PIPE_NOT_AVAILABLE:
                    // XXX: There has to be a better way to do this...
                    try {
                        Thread.sleep(STATUS_PIPE_NOT_AVAILABLE_BACKOFF_TIME_MS);
                    } catch (final InterruptedException iException) {
                        final InterruptedIOException iioException = new InterruptedIOException();
                        iioException.addSuppressed(iException);
                        throw iioException;
                    }
                    break;
                default:
                    throw new SMBException(exceptions.poll());
                }
            }
        }
        throw new SMBException(exceptions.poll());
    }

    private static SMB2FileId openNamedPipe(final Share share, final String namedPipe) {
        return open(share.getTreeConnect(), namedPipe, SMB2ImpersonationLevel.IMPERSONATION,
            AccessMask.MAXIMUM_ALLOWED.getValue(), null,
            EnumSet.of(SMB2ShareAccess.FILE_SHARE_READ, SMB2ShareAccess.FILE_SHARE_WRITE),
            SMB2CreateDisposition.FILE_OPEN_IF, null);
    }

    private static SMB2FileId open(
        final TreeConnect treeConnect,
        final String path,
        final SMB2ImpersonationLevel impersonationLevel,
        final long accessMask,
        final EnumSet<FileAttributes> fileAttributes,
        final EnumSet<SMB2ShareAccess> shareAccess,
        final SMB2CreateDisposition createDisposition,
        final EnumSet<SMB2CreateOptions> createOptions)
        throws SMBApiException {
        final Session session = treeConnect.getSession();
        final SMB2CreateRequest cr = openFileRequest(treeConnect, path, impersonationLevel, accessMask, shareAccess,
            fileAttributes, createDisposition, createOptions);
        try {
            final Future<SMB2CreateResponse> responseFuture = session.send(cr);
            final SMB2CreateResponse cresponse = Futures.get(responseFuture, SMBRuntimeException.Wrapper);
            if (cresponse.getHeader().getStatus() != NtStatus.STATUS_SUCCESS) {
                throw new SMBApiException(cresponse.getHeader(), "Create failed for " + path);
            }

            return cresponse.getFileId();
        } catch (final TransportException exception) {
            throw SMBRuntimeException.Wrapper.wrap(exception);
        }

    }

    private static SMB2CreateRequest openFileRequest(
        final TreeConnect treeConnect,
        final String path,
        final SMB2ImpersonationLevel impersonationLevel,
        final long accessMask,
        final EnumSet<SMB2ShareAccess> shareAccess,
        final EnumSet<FileAttributes> fileAttributes,
        final SMB2CreateDisposition createDisposition,
        final EnumSet<SMB2CreateOptions> createOptions) {
        final Session session = treeConnect.getSession();
        final SMB2CreateRequest createRequest = new SMB2CreateRequest(
            session.getConnection().getNegotiatedProtocol().getDialect(), session.getSessionId(),
            treeConnect.getTreeId(), impersonationLevel, accessMask, fileAttributes, shareAccess, createDisposition,
            createOptions, path);
        return createRequest;
    }
}
