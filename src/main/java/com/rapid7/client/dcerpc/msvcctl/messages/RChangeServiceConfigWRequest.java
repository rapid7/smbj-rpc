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
    private int serviceType = SERVICE_NO_CHANGE; //defaults
    private int startType = SERVICE_NO_CHANGE;
    private int errorControl = SERVICE_NO_CHANGE;
    private String binaryPathName;
    private String loadOrderGroup;
    private int tagId;
    private byte[] dependencies;
    private String serviceStartName;
    private String password;
    private String displayName;


    public RChangeServiceConfigWRequest(
        final ContextHandle handle,
        final int serviceType,
        final int startType,
        final int errorControl){
        super(OP_NUM);
        this.serviceHandle = handle;
        this.serviceType = serviceType;
        this.startType = startType;
        this.errorControl = errorControl;
    }

    public void setBinaryPathName(String binaryPathName)
    {
        this.binaryPathName = binaryPathName;
    }

    public void setLoadOrderGroup(String loadOrderGroup)
    {
        this.loadOrderGroup = loadOrderGroup;
    }

    public void setTagId(int tagId)
    {
        this.tagId = tagId;
    }

    public void setDependencies(byte[] dependencies)
    {
        this.dependencies = dependencies;
    }

    public void setServiceStartName(String serviceStartName)
    {
        this.serviceStartName = serviceStartName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    @Override public RChangeServiceConfigWResponse getResponseObject() {
        return new RChangeServiceConfigWResponse();
    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException {
        packetOut.write(serviceHandle.getBytes());
        packetOut.writeInt(serviceType);
        packetOut.writeInt(startType);
        packetOut.writeInt(errorControl);
        if (binaryPathName != null) packetOut.writeStringRef(binaryPathName, true);
        else packetOut.writeNull();

        if (loadOrderGroup != null) packetOut.writeStringRef(loadOrderGroup, true);
        else packetOut.writeNull();

        if (tagId != 0) packetOut.writeIntRef(tagId);
        else packetOut.writeNull();

        if (dependencies != null) {
            packetOut.writeReferentID();
            packetOut.writeInt(dependencies.length); //conformat array, count
            packetOut.write(dependencies);
            packetOut.align();
            //dependency size
            packetOut.writeInt(dependencies.length);
        } else {
            packetOut.writeNull();
            //dependency size
            packetOut.writeInt(0);
        }

        if (serviceStartName != null) packetOut.writeStringRef(serviceStartName, true);
        else packetOut.writeNull();

        if (password != null) {
            packetOut.writeReferentID();
            packetOut.writeInt(password.length());
            packetOut.write(password.getBytes());
            packetOut.align();
            //password size
            packetOut.writeInt(password.length());
        } else {
            packetOut.writeNull();
            //password size
            packetOut.writeInt(0);
        }

        if (displayName != null) packetOut.writeStringRef(displayName, true);
        else packetOut.writeNull();
    }
}
