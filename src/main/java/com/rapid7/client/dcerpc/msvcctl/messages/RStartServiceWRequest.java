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
import com.rapid7.client.dcerpc.messages.EmptyResponse;

public class RStartServiceWRequest extends RequestCall<EmptyResponse> {
    private final static short OP_NUM = 19;
    private final byte[] serviceHandle;

    public RStartServiceWRequest(byte[] handle) {
        super(OP_NUM);
        this.serviceHandle = handle;
    }

    @Override
    public EmptyResponse getResponseObject() {
        return new EmptyResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle);
        packetOut.writeNull(); //argc (not implemented)
        packetOut.writeNull(); //argv (not implemented)
    }
}
