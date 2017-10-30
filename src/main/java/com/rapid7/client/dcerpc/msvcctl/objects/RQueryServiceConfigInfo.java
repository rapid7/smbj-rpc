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
package com.rapid7.client.dcerpc.msvcctl.objects;

/**
 *  Query Service Config responses from:
 * https://msdn.microsoft.com/en-us/library/cc245948.aspx
 */

public class RQueryServiceConfigInfo
{
    //Service Type
    public final static int SERVICE_KERNEL_DRIVER = 0x1;
    public final static int SERVICE_FILE_SYSTEM_DRIVER = 0x2;
    public final static int SERVICE_WIN32_OWN_PROCESS = 0x10;
    public final static int SERVICE_WIN32_SHARE_PROCESS = 0x20;

    //Startup Type
    public final static int SERVICE_BOOT_START = 0x0;
    public final static int SERVICE_SYSTEM_START = 0x1;
    public final static int SERVICE_AUTO_START = 0x2;
    public final static int SERVICE_DEMAND_START = 0x3;
    public final static int SERVICE_DISABLED = 0x4;

    //Error control
    public final static int SERVICE_ERROR_IGNORE = 0x0;
    public final static int SERVICE_ERROR_NORMAL = 0x1;
    public final static int SERVICE_ERROR_SEVERE = 0x2;
    public final static int SERVICE_ERROR_CRITICAL = 0x3;

    private int serviceType; //defaults
    private int startType;
    private int errorControl;
    private String binaryPathName;
    private String loadOrderGroup;
    private int tagId;
    private String dependencies;
    private String serviceStartName;
    private String displayName;

    public RQueryServiceConfigInfo(int serviceType, int startType, int errorControl, String binaryPathName, String loadOrderGroup, int tagId, String dependencies, String serviceStartName, String displayName) {
        this.serviceType = serviceType;
        this.startType = startType;
        this.errorControl = errorControl;
        this.binaryPathName = binaryPathName;
        this.loadOrderGroup = loadOrderGroup;
        this.tagId = tagId;
        this.dependencies = dependencies;
        this.serviceStartName = serviceStartName;
        this.displayName = displayName;
    }

    public int getServiceType() {
        return serviceType;
    }

    public int getStartType() {
        return startType;
    }

    public int getErrorControl() {
        return errorControl;
    }

    public String getBinaryPathName() {
        return binaryPathName;
    }

    public String getLoadOrderGroup() {
        return loadOrderGroup;
    }

    public int getTagId() {
        return tagId;
    }

    public String getDependencies() {
        return dependencies;
    }

    public String getServiceStartName() {
        return serviceStartName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RQueryServiceConfigInfo that = (RQueryServiceConfigInfo) o;

        if (serviceType != that.serviceType) return false;
        if (startType != that.startType) return false;
        if (errorControl != that.errorControl) return false;
        if (tagId != that.tagId) return false;
        if (!binaryPathName.equals(that.binaryPathName)) return false;
        if (!loadOrderGroup.equals(that.loadOrderGroup)) return false;
        if (!dependencies.equals(that.dependencies)) return false;
        if (!serviceStartName.equals(that.serviceStartName)) return false;
        return displayName.equals(that.displayName);
    }

    @Override
    public int hashCode() {
        int result = serviceType;
        result = 31 * result + startType;
        result = 31 * result + errorControl;
        result = 31 * result + binaryPathName.hashCode();
        result = 31 * result + loadOrderGroup.hashCode();
        result = 31 * result + tagId;
        result = 31 * result + dependencies.hashCode();
        result = 31 * result + serviceStartName.hashCode();
        result = 31 * result + displayName.hashCode();
        return result;
    }
}
