package com.rapid7.client.dcerpc.objects;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import java.io.IOException;

public class EmptyResponse extends RequestResponse
{
    private int returnValue;

    @Override public void unmarshal(PacketInput packetIn)
        throws IOException
    {
        returnValue = packetIn.readInt();
    }

    public SystemErrorCode getReturnValue() {
        return SystemErrorCode.getErrorCode(returnValue);
    }
}
