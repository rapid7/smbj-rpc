/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
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
