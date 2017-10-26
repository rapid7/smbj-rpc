package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.objects.RQueryServiceStatusInfo;
import java.io.IOException;

public class RChangeServiceConfigWResponse extends RequestResponse
{
    private int tagId;
    private int returnCode;

    @Override public void unmarshal(PacketInput packetIn)
        throws IOException
    {
        int tagIdRefId = packetIn.readReferentID();
        if (tagIdRefId != 0) tagId = packetIn.readInt();
        returnCode = packetIn.readInt();
    }

    public int getTagId() {
        return tagId;
    }

    public SystemErrorCode getReturnCode() {
        return SystemErrorCode.getErrorCode(returnCode);
    }
}
