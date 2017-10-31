/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceStatusInfo;
import java.io.IOException;

public class RQueryServiceStatusResponse extends RequestResponse
{
    private ServiceStatusInfo serviceStatusInfo;
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
        serviceStatusInfo = new ServiceStatusInfo(
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

    public ServiceStatusInfo getServiceStatusInfo() {
        return serviceStatusInfo;
    }

    public SystemErrorCode getReturnCode() {
        return SystemErrorCode.getErrorCode(returnCode);
    }
}
