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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;

/**
 *
 */
public class SamrEnumerateDomainsInSamServerRequest extends RequestCall<SamrEnumerateDomainsInSamServerResponse> {
    public final static short OP_NUM = 6;
    private final ServerHandle serverHandle;
    private final int enumContext;
    private final int maxLength;

    public SamrEnumerateDomainsInSamServerRequest(ServerHandle handle, int enumContext, int maxLength) {
        super(OP_NUM);
        serverHandle = handle;
        this.enumContext = enumContext;
        this.maxLength = maxLength;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serverHandle.getBytes());
        packetOut.writeInt(enumContext);
        packetOut.writeInt(maxLength);
    }

    @Override
    public SamrEnumerateDomainsInSamServerResponse getResponseObject() {
        return new SamrEnumerateDomainsInSamServerResponse();
    }
}
