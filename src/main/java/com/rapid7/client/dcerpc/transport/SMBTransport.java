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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.messages.SMB2ReadResponse;
import com.hierynomus.smbj.common.SMBApiException;
import com.hierynomus.smbj.share.NamedPipe;
import com.rapid7.client.dcerpc.RPCRequest;
import com.rapid7.client.dcerpc.RPCResponse;

public class SMBTransport implements RPCTransport {
    private final static int MAXIMUM_TRANSACT_SIZE = 65536;
    private final static int MAXIMUM_READ_SIZE = 65536;
    private final NamedPipe namedPipe;
    private int callID = 1;

    public SMBTransport(final NamedPipe namedPipe) {
        this.namedPipe = namedPipe;
    }

    @Override
    public <T extends RPCResponse> T transact(final RPCRequest<T> request)
        throws IOException {
        final int callID = this.callID++;
        final byte[] requestBytes = request.marshal(callID);
        try {
            final byte[] responseBytes = new byte[MAXIMUM_TRANSACT_SIZE];
            namedPipe.transact(requestBytes, responseBytes);
            return request.unmarshal(responseBytes, callID);
        } catch (final SMBApiException exception) {
            final NtStatus status = exception.getStatus();
            if (status.equals(NtStatus.STATUS_BUFFER_OVERFLOW)) {
                for (;;) {
                    final SMB2ReadResponse readResponse = namedPipe.read(MAXIMUM_READ_SIZE);
                    if (0 >= readResponse.getDataRemaining()) {
                        break;
                    }
                }
            }
            final ByteArrayOutputStream responseBytes = new ByteArrayOutputStream();
            namedPipe.write(requestBytes);
            for (;;) {
                final SMB2ReadResponse readResponse = namedPipe.read(MAXIMUM_READ_SIZE);
                responseBytes.write(readResponse.getData());
                if (0 >= readResponse.getDataRemaining()) {
                    break;
                }
            }
            return request.unmarshal(responseBytes.toByteArray(), callID);
        }
    }
}
