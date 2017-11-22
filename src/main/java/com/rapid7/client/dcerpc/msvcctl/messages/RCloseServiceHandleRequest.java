package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

import java.io.IOException;

public class RCloseServiceHandleRequest extends RequestCall<HandleResponse> {
    private final static short OP_NUM = 0;
    private final ContextHandle serviceHandle;

    public RCloseServiceHandleRequest(ContextHandle handle) {
        super(OP_NUM);
        this.serviceHandle = handle;
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle.getBytes());
    }
}
