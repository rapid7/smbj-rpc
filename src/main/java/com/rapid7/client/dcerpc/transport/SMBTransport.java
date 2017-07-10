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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.messages.SMB2IoctlRequest;
import com.hierynomus.mssmb2.messages.SMB2IoctlRequest.ControlCode;
import com.hierynomus.mssmb2.messages.SMB2IoctlResponse;
import com.hierynomus.smbj.session.Session;
import com.rapid7.client.dcerpc.RPCRequest;
import com.rapid7.client.dcerpc.RPCResponse;

public class SMBTransport implements RPCTransport {
    private final Session session;
    private final SMB2Dialect negotiatedDialect;
    private final SMB2FileId fileId;
    private final long sessionId;
    private final long treeId;
    private int callID = 1;

    public SMBTransport(
        final Session session,
        final SMB2Dialect negotiatedDialect,
        final SMB2FileId fileId,
        final long sessionId,
        final long treeId) {
        this.session = session;
        this.negotiatedDialect = negotiatedDialect;
        this.fileId = fileId;
        this.sessionId = sessionId;
        this.treeId = treeId;
    }

    @Override
    public <T extends RPCResponse> T transact(final RPCRequest<T> request)
        throws IOException {
        final int callID = this.callID++;
        final byte[] requestBytes = request.marshal(callID);
        final SMB2IoctlRequest ioctlRequest = new SMB2IoctlRequest(negotiatedDialect, sessionId, treeId,
            ControlCode.FSCTL_PIPE_TRANSCEIVE, fileId, requestBytes, true);
        final Future<SMB2IoctlResponse> future = session.send(ioctlRequest);
        final SMB2IoctlResponse response;
        try {
            response = future.get();
        } catch (final ExecutionException | InterruptedException exception) {
            final IOException ioException = new IOException();
            ioException.addSuppressed(exception);
            throw ioException;
        }
        final byte[] responseBytes = response.getOutputBuffer();
        return request.unmarshal(responseBytes, callID);
    }
}
