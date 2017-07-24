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
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2ImpersonationLevel;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.smbj.common.SMBApiException;
import com.hierynomus.smbj.common.SMBException;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.NamedPipe;
import com.hierynomus.smbj.share.PipeShare;
import com.hierynomus.smbj.share.Share;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.Interface;
import com.rapid7.client.dcerpc.RPCResponse;
import com.rapid7.client.dcerpc.messages.Bind;
import com.rapid7.client.dcerpc.messages.BindACK;

public enum SMBTransportFactories {
    WINREG("winreg", Interface.WINREG_V1_0, Interface.NDR_32BIT_V2),
    SRVSVC("srvsvc", Interface.SRVSVC_V3_0, Interface.NDR_32BIT_V2);

    private final static int STATUS_PIPE_NOT_AVAILABLE_BACKOFF_TIME_MS = 3000;
    private final static int STATUS_PIPE_NOT_AVAILABLE_RETRIES = 1;
    private final String name;
    private final Interface abstractSyntax;
    private final Interface transferSyntax;

    private SMBTransportFactories(final String path, final Interface abstractSyntax, final Interface transferSyntax) {
        this.name = path;
        this.abstractSyntax = abstractSyntax;
        this.transferSyntax = transferSyntax;
    }

    public RPCTransport getTransport(final Session session)
        throws IOException {
        final Share share = session.connectShare("IPC$");
        if (share instanceof PipeShare) {
            final PipeShare pipeShare = (PipeShare) share;
            final NamedPipe namedPipe = openAndHandleStatusPipeNotAvailable(pipeShare);
            final SMBTransport transport = new SMBTransport(namedPipe);
            final Bind bind = new Bind(abstractSyntax, transferSyntax);
            final RPCResponse response = transport.transact(bind);

            if (response instanceof BindACK) {
                return transport;
            }

            throw new TransportException(
                String.format("BIND %s (%s -> %s) failed.", abstractSyntax.getName(), name, abstractSyntax.getRepr()));
        }

        throw new TransportException(String.format("%s not a named pipe.", name));
    }

    private NamedPipe openAndHandleStatusPipeNotAvailable(final PipeShare pipeShare)
        throws IOException {
        final Queue<SMBApiException> exceptions = new LinkedList<>();
        for (int retry = -1; retry < STATUS_PIPE_NOT_AVAILABLE_RETRIES; retry++) {
            try {
                return pipeShare.open(name, SMB2ImpersonationLevel.Impersonation,
                    EnumSet.of(AccessMask.MAXIMUM_ALLOWED), null,
                    EnumSet.of(SMB2ShareAccess.FILE_SHARE_READ, SMB2ShareAccess.FILE_SHARE_WRITE),
                    SMB2CreateDisposition.FILE_OPEN_IF, null);
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
}
