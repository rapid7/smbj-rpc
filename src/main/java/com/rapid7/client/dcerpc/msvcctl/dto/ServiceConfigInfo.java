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
package com.rapid7.client.dcerpc.msvcctl.dto;

import java.util.Arrays;
import java.util.Objects;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;

public class ServiceConfigInfo implements IServiceConfigInfo {
    private final ServiceType serviceType;
    private final ServiceStartType startType;
    private final ServiceError errorControl;
    private final String binaryPathName;
    private final String loadOrderGroup;
    private final int tagId;
    private final String[] dependencies;
    private final String serviceStartName;
    private final String displayName;
    private final String password;

    public ServiceConfigInfo(ServiceType serviceType, ServiceStartType startType, ServiceError errorControl)
    {
        this(serviceType, startType, errorControl, null, null,
                0, null, null, null, null);
    }

    public ServiceConfigInfo(ServiceType serviceType, ServiceStartType startType, ServiceError errorControl,
            String binaryPathName, String loadOrderGroup, int tagId, String[] dependencies, String serviceStartName,
            String displayName, String password) {
        this.serviceType = serviceType;
        this.startType = startType;
        this.errorControl = errorControl;
        this.binaryPathName = binaryPathName;
        this.loadOrderGroup = loadOrderGroup;
        this.tagId = tagId;
        this.dependencies = dependencies;
        this.serviceStartName = serviceStartName;
        this.displayName = displayName;
        this.password = password;
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public ServiceStartType getStartType() {
        return startType;
    }

    @Override
    public ServiceError getErrorControl() {
        return errorControl;
    }

    @Override
    public String getBinaryPathName() {
        return binaryPathName;
    }

    @Override
    public String getLoadOrderGroup() {
        return loadOrderGroup;
    }

    @Override
    public int getTagId() {
        return tagId;
    }

    @Override
    public String[] getDependencies() {
        return dependencies;
    }

    @Override
    public String getServiceStartName() {
        return serviceStartName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof IServiceConfigInfo)) {
            return false;
        }
        final IServiceConfigInfo other = (IServiceConfigInfo) obj;
        return Objects.equals(getServiceType(), other.getServiceType())
                && Objects.equals(getStartType(), other.getStartType())
                && Objects.equals(getErrorControl(), other.getErrorControl())
                && Objects.equals(getBinaryPathName(), other.getBinaryPathName())
                && Objects.equals(getLoadOrderGroup(), other.getLoadOrderGroup())
                && getTagId() == other.getTagId()
                && Arrays.equals(getDependencies(), other.getDependencies())
                && Objects.equals(getServiceStartName(), other.getServiceStartName())
                && Objects.equals(getPassword(), other.getPassword())
                && Objects.equals(getDisplayName(), other.getDisplayName());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getServiceType(), getStartType(), getErrorControl(), getBinaryPathName(),
                getLoadOrderGroup(), getTagId(), getServiceStartName(), getPassword(), getDisplayName());
        return (31 * result) + Arrays.hashCode(getDependencies());
    }

    @Override
    public String toString() {
        return String.format("ServiceConfigInfo{serviceType: %s, startType: %s, errorControl: %s, binaryPathName: %s " +
                "loadOrderGroup: %s, dependencies: %s, serviceStartName: %s, displayName: %s}",
                getServiceType(), getStartType(), getErrorControl(), formatString(getBinaryPathName()),
                formatString(getLoadOrderGroup()), Arrays.toString(getDependencies()),
                formatString(getServiceStartName()), formatString(getDisplayName()));
    }

    private String formatString(final String str) {
        if (str == null)
            return "null";
        return String.format("\"%s\"", str);
    }
}
