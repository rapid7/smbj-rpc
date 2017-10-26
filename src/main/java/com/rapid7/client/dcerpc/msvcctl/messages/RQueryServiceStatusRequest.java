package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import java.io.IOException;

public class RQueryServiceStatusRequest extends RequestCall<RQueryServiceStatusResponse>
{

    private final static short OP_NUM = 6;
    private ContextHandle handle;

    public RQueryServiceStatusRequest(ContextHandle handle) {
        super(OP_NUM);
        this.handle = handle;
    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException
    {
        packetOut.write(handle.getBytes());
    }

    @Override public RQueryServiceStatusResponse getResponseObject()
    {
        return new RQueryServiceStatusResponse();
    }
}
