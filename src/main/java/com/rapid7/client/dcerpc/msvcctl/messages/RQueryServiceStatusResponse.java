package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.objects.RQueryServiceStatusInfo;
import java.io.IOException;

public class RQueryServiceStatusResponse extends RequestResponse
{
    private RQueryServiceStatusInfo rQueryServiceStatusInfo;
    private int returnCode;

    @Override public void unmarshal(PacketInput packetIn)
        throws IOException
    {
        int serviceType = packetIn.readInt();
        int currentState = packetIn.readInt();
        int controlsAccepted = packetIn.readInt();
        int win32ExitCode = packetIn.readInt();
        int serviceSpecificExitCode = packetIn.readInt();
        int checkPoint = packetIn.readInt();
        int waitHint = packetIn.readInt();
        rQueryServiceStatusInfo = new RQueryServiceStatusInfo(
            serviceType,
            currentState,
            controlsAccepted,
            win32ExitCode,
            serviceSpecificExitCode,
            checkPoint,
            waitHint
        );
        returnCode = packetIn.readInt();
    }

    public RQueryServiceStatusInfo getrQueryServiceStatusInfo() {
        return rQueryServiceStatusInfo;
    }

    public SystemErrorCode getReturnCode() {
        return SystemErrorCode.getErrorCode(returnCode);
    }
}
