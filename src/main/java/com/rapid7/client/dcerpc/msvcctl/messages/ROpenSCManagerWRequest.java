package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msvcctl.ServiceControlManagerService;
import java.io.IOException;

public class ROpenSCManagerWRequest extends RequestCall<HandleResponse>
{
    private final static short OP_NUM = 15;
    private final String name;
    private final String databaseName;
    private final int desiredAccess;

    public ROpenSCManagerWRequest() {
        this("test", null, ServiceControlManagerService.FULL_ACCESS);
    }
    public ROpenSCManagerWRequest(String name, String databaseName, int desiredAccess) {
        super(OP_NUM);
        this.name = name;
        this.databaseName = databaseName;
        this.desiredAccess = desiredAccess;

    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException
    {
        if (name != null) packetOut.writeStringRef(name, true);
        else packetOut.writeNull();
        if (databaseName != null)packetOut.writeStringRef(databaseName, true);
        else packetOut.writeNull();
        packetOut.writeInt(desiredAccess);
    }

    @Override public HandleResponse getResponseObject()
    {
        return new HandleResponse();
    }
}
