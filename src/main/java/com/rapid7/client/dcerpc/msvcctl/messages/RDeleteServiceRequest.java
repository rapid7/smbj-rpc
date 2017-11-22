package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

import java.io.IOException;

public class RDeleteServiceRequest extends RequestCall<EmptyResponse> {
    private final static short OP_NUM = 2;
    private final ContextHandle serviceHandle;

    public RDeleteServiceRequest(ContextHandle handle) {
        super(OP_NUM);
        this.serviceHandle = handle;
    }

    @Override
    public EmptyResponse getResponseObject() {
        return new EmptyResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle.getBytes());
    }
}