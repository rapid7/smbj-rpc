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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceConfigInfo;

public class RQueryServiceConfigWResponse extends RequestResponse {
    private ServiceConfigInfo serviceConfigInfo;
    private int bytesNeeded;

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        readQueryServiceConfg(packetIn);
        bytesNeeded = packetIn.readInt();
    }

    private void readQueryServiceConfg(PacketInput packetIn) throws IOException {
        ServiceType serviceType = ServiceType.fromInt(packetIn.readInt());
        ServiceStartType startType = ServiceStartType.fromInt(packetIn.readInt());
        ServiceError errorControl = ServiceError.fromInt(packetIn.readInt());
        int binaryPathRef = packetIn.readReferentID();
        int loadOrderRef = packetIn.readReferentID();
        int tagId = packetIn.readInt();
        int dependencyRef = packetIn.readReferentID();
        int serviceStartNameRef = packetIn.readReferentID();
        int displayNameRef = packetIn.readReferentID();


        String binaryPath = (binaryPathRef != 0) ? packetIn.readString(true) : null;
        String loadOrderGroup = (loadOrderRef != 0) ? packetIn.readString(true) : null;
        String dependencies = (dependencyRef != 0) ? packetIn.readString(true) : null;
        String serviceStartName = (serviceStartNameRef != 0) ? packetIn.readString(true) : null;
        String displayName = (displayNameRef != 0) ? packetIn.readString(true) : null;

        serviceConfigInfo = new ServiceConfigInfo(serviceType, startType, errorControl, binaryPath, loadOrderGroup, tagId, dependencies, serviceStartName, displayName);
    }

    public int getBytesNeeded() {
        return bytesNeeded;
    }

    public ServiceConfigInfo getServiceConfigInfo() {
        return serviceConfigInfo;
    }
}
