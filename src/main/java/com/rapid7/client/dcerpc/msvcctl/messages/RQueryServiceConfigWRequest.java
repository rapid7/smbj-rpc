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
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

public class RQueryServiceConfigWRequest extends RequestCall<RQueryServiceConfigWResponse> {

    public final static int MAX_BUFFER_SIZE = 8192;

    private final static short OP_NUM = 17;
    private final byte[] serviceHandle;
    private final int bufSize;

    public RQueryServiceConfigWRequest(final byte[] handle, int bufSize) {
        super(OP_NUM);
        this.serviceHandle = handle;
        this.bufSize = bufSize;
    }

    @Override
    public RQueryServiceConfigWResponse getResponseObject() {
        return new RQueryServiceConfigWResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle);
        packetOut.writeInt(bufSize);
    }
}
