/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServicesAcceptedControls;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceStatusInfo;

public class RQueryServiceStatusResponse extends RequestResponse {
    private int returnValue;
    private IServiceStatusInfo serviceStatusInfo;

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        ServiceType serviceType = ServiceType.fromInt(packetIn.readInt());
        ServiceStatusType currentState = ServiceStatusType.fromInt(packetIn.readInt());
        ServicesAcceptedControls controlsAccepted = ServicesAcceptedControls.fromInt(packetIn.readInt());
        int win32ExitCode = packetIn.readInt();
        int serviceSpecificExitCode = packetIn.readInt();
        int checkPoint = packetIn.readInt();
        int waitHint = packetIn.readInt();
        serviceStatusInfo = new ServiceStatusInfo(serviceType, currentState, controlsAccepted, win32ExitCode, serviceSpecificExitCode, checkPoint, waitHint);
        returnValue = packetIn.readInt();
    }

    public IServiceStatusInfo getServiceStatusInfo() {
        return serviceStatusInfo;
    }

    public int getReturnValue() {
        return returnValue;
    }
}
