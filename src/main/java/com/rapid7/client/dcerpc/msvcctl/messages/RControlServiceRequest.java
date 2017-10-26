package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.msvcctl.objects.RQueryServiceStatusInfo;
import com.rapid7.client.dcerpc.objects.EmptyResponse;
import java.io.IOException;

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
    private ContextHandle serviceHandle;
    private int operation;

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
