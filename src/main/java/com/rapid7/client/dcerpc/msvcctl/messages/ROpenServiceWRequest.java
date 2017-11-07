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
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public class ROpenServiceWRequest extends RequestCall<HandleResponse> {

    private final static short OP_NUM = 16;

    private final String serviceName;
    private final ContextHandle serviceManagerHandle;
    private final int access;

    public ROpenServiceWRequest(ContextHandle serviceManagerHandle, String serviceName, int access) {
        super(OP_NUM);
        this.serviceManagerHandle = serviceManagerHandle;
        this.serviceName = serviceName;
        this.access = access;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceManagerHandle.getBytes());
        if (serviceName != null) packetOut.writeString(serviceName, true);
        else packetOut.writeNull();
        packetOut.writeInt(access);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
