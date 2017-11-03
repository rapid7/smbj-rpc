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
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public class RControlServiceRequest extends RequestCall<RQueryServiceStatusResponse>
{

    public final static int SERVICE_CONTROL_STOP = 0x1;
    public final static int SERVICE_CONTROL_PAUSE = 0x2;
    public final static int SERVICE_CONTROL_CONTINUE = 0x3;
    public final static int SERVICE_CONTROL_INTERROGATE = 0x4;
    public final static int SERVICE_CONTROL_PARAMCHANGE = 0x6;
    public final static int SERVICE_CONTROL_NETBINDADD = 0x7;
    public final static int SERVICE_CONTROL_NETBINDREMOVE = 0x8;
    public final static int SERVICE_CONTROL_NETBINDENABLE = 0x9;
    public final static int SERVICE_CONTROL_NETBINDDISABLE = 0xA;

    private final static short OP_NUM = 1;
    private final ContextHandle serviceHandle;
    private final int operation;

    public RControlServiceRequest(final ContextHandle handle, final int operation){
        super(OP_NUM);
        this.serviceHandle = handle;
        this.operation = operation;
    }

    @Override public RQueryServiceStatusResponse getResponseObject() {
        return new RQueryServiceStatusResponse();
    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException {
        packetOut.write(serviceHandle.getBytes());
        packetOut.writeInt(operation);
    }
}
