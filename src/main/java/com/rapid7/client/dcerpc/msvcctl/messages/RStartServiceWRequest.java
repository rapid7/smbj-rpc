package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.EmptyResponse;
import java.io.IOException;

public class RStartServiceWRequest extends RequestCall<EmptyResponse>
{
    private final static short OP_NUM = 19;
    private ContextHandle serviceHandle;

    public RStartServiceWRequest(ContextHandle handle){
        super(OP_NUM);
        this.serviceHandle = handle;
    }

    @Override public EmptyResponse getResponseObject()
    {
        return new EmptyResponse();
    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException {
        packetOut.write(serviceHandle.getBytes());
        packetOut.writeNull(); //argc
        packetOut.writeNull(); //argv
    }
}
