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
import com.hierynomus.smbj.share.NamedPipe;
import com.rapid7.client.dcerpc.RPCRequest;
import com.rapid7.client.dcerpc.RPCResponse;

public class SMBTransport implements RPCTransport {
    private final NamedPipe namedPipe;
    private int callID = 1;

    public SMBTransport(final NamedPipe namedPipe) {
        this.namedPipe = namedPipe;
    }

    @Override
    public <T extends RPCResponse> T transact(final RPCRequest<T> request)
        throws IOException {
        final int callID;
        synchronized (this) {
            callID = this.callID++;
        }
        final byte[] requestBytes = request.marshal(callID);
        final byte[] responseBytes = namedPipe.transact(requestBytes);
        return request.unmarshal(responseBytes, callID);
    }
}
