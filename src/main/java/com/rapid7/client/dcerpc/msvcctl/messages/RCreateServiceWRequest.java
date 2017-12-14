/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceManagerHandle;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceManagerAccessLevel;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceConfigInfo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RCreateServiceWRequest extends RequestCall<RCreateServiceWResponse> {
    private final static short OP_NUM = 12;
    private ServiceManagerHandle serviceHandle;
    private IServiceConfigInfo serviceConfigInfo;
    private int access;
    private String serviceName;

    public RCreateServiceWRequest(
            final ServiceManagerHandle handle,
            final IServiceConfigInfo serviceConfigInfo,
            final ServiceManagerAccessLevel managerAccessLevel,
            final String serviceName){
        super(OP_NUM);
        this.serviceHandle = handle;
        this.serviceConfigInfo = serviceConfigInfo;
        this.access = managerAccessLevel.getValue();
        this.serviceName = serviceName;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle.getBytes());
        packetOut.writeString(serviceName, true);

        if (serviceConfigInfo.getDisplayName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getDisplayName(), true);
        else packetOut.writeNull();

        packetOut.writeInt(access);
        packetOut.writeInt(serviceConfigInfo.getServiceType().getValue());
        packetOut.writeInt(serviceConfigInfo.getStartType().getValue());
        packetOut.writeInt(serviceConfigInfo.getErrorControl().getValue());
        packetOut.writeString(serviceConfigInfo.getBinaryPathName(), true);

        if (serviceConfigInfo.getLoadOrderGroup() != null)
            packetOut.writeStringRef(serviceConfigInfo.getLoadOrderGroup(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getTagId() != 0) packetOut.writeIntRef(serviceConfigInfo.getTagId());
        else packetOut.writeNull();

        if (serviceConfigInfo.getDependencies() != null) {
            StringBuffer sb = new StringBuffer(serviceConfigInfo.getDependencies());

            //Doubly nul-terminated
            sb.append('\u0000');
            sb.append('\u0000');

            //Get unicode byte array
            byte[] dependencies = sb.toString().getBytes(StandardCharsets.UTF_16LE);

            packetOut.writeReferentID();
            packetOut.writeInt(dependencies.length);
            packetOut.write(dependencies);
            packetOut.align();
            //dependency size
            packetOut.writeInt(dependencies.length);
        } else {
            packetOut.writeNull();
            //dependency size
            packetOut.writeInt(0);
        }

        if (serviceConfigInfo.getServiceStartName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getServiceStartName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getPassword() != null) {
            // This is not working.  The password may need to be encrypted according to MS-SCMR
            //
            packetOut.writeReferentID();
            packetOut.writeInt(serviceConfigInfo.getPassword().length());
            packetOut.write(serviceConfigInfo.getPassword().getBytes());
            packetOut.align();
            //password size
            packetOut.writeInt(serviceConfigInfo.getPassword().length());
        } else {
            packetOut.writeNull();
            //password size
            packetOut.writeInt(0);
        }


    }

    @Override
    public RCreateServiceWResponse getResponseObject() {
        return new RCreateServiceWResponse();
    }

}
