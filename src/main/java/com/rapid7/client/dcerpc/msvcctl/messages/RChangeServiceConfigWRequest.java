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

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceConfigInfo;
import java.io.IOException;

public class RChangeServiceConfigWRequest extends RequestCall<RChangeServiceConfigWResponse>
{

    // Arguments to serviceType
    public final static int SERVICE_KERNEL_DRIVER = 0x1;
    public final static int SERVICE_FILE_SYSTEM_DRIVER = 0x2;
    public final static int SERVICE_WIN32_OWN_PROCESS = 0x10;
    public final static int SERVICE_WIN32_SHARE_PROCESS = 0x20;
    public final static int SERVICE_INTERACTIVE_PROCESS = 0x100;
    public final static int SERVICE_NO_CHANGE = 0xFFFFFFFF;

    // Arguments to startType
    public final static int SERVICE_BOOT_START = 0x0;
    public final static int SERVICE_SYSTEM_START = 0x1;
    public final static int SERVICE_AUTO_START = 0x2;
    public final static int SERVICE_DEMAND_START = 0x3;
    public final static int SERVICE_DISABLED = 0x4;

    // Arguments to errorControl
    public final static int SERVICE_ERROR_IGNORE = 0x0;
    public final static int SERVICE_ERROR_NORMAL = 0x1;
    public final static int SERVICE_ERROR_SEVERE = 0x2;
    public final static int SERVICE_ERROR_CRITICAL = 0x3;


    private final static short OP_NUM = 11;
    private ContextHandle serviceHandle;
    private ServiceConfigInfo serviceConfigInfo;

    public RChangeServiceConfigWRequest(
        final ContextHandle handle,
        final ServiceConfigInfo serviceConfigInfo){
        super(OP_NUM);
        this.serviceHandle = handle;
        this.serviceConfigInfo = serviceConfigInfo;
    }

    @Override public RChangeServiceConfigWResponse getResponseObject() {
        return new RChangeServiceConfigWResponse();
    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException {
        packetOut.write(serviceHandle.getBytes());
        packetOut.writeInt(serviceConfigInfo.getServiceType().getValue());
        packetOut.writeInt(serviceConfigInfo.getStartType().getValue());
        packetOut.writeInt(serviceConfigInfo.getErrorControl().getValue());
        if (serviceConfigInfo.getBinaryPathName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getBinaryPathName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getLoadOrderGroup() != null)
            packetOut.writeStringRef(serviceConfigInfo.getLoadOrderGroup(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getTagId() != 0) packetOut.writeIntRef(serviceConfigInfo.getTagId());
        else packetOut.writeNull();

        if (serviceConfigInfo.getDependencies() != null) {
            packetOut.writeReferentID();
            packetOut.writeInt(serviceConfigInfo.getDependencies().length()); //conformat array, count
            packetOut.write(serviceConfigInfo.getDependencies().getBytes());
            packetOut.align();
            //dependency size
            packetOut.writeInt(serviceConfigInfo.getDependencies().length());
        } else {
            packetOut.writeNull();
            //dependency size
            packetOut.writeInt(0);
        }

        if (serviceConfigInfo.getServiceStartName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getServiceStartName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getPassword() != null) {
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

        if (serviceConfigInfo.getDisplayName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getDisplayName(), true);
        else packetOut.writeNull();
    }
}
