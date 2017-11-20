/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public class LsarClosePolicyRpcRequest extends RequestCall<LsarClosePolicyRpcResponse> {
    private final static short OP_NUM = 0;
    private final ContextHandle handle;

    public LsarClosePolicyRpcRequest(final ContextHandle handle) {
        super(OP_NUM);
        this.handle = handle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // set the policy handle
        packetOut.write(handle.getBytes());
    }

    @Override
    public LsarClosePolicyRpcResponse getResponseObject() {
        return new LsarClosePolicyRpcResponse();
    }

}
