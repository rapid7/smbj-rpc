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


import static com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWRequest.SERVICE_NO_CHANGE;

/**
 *  Query Service Config responses from:
 * https://msdn.microsoft.com/en-us/library/cc245948.aspx
 */

public class RQueryServiceConfigInfo
{
    private int serviceType = SERVICE_NO_CHANGE; //defaults
    private int startType = SERVICE_NO_CHANGE;
    private int errorControl = SERVICE_NO_CHANGE;
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
}
