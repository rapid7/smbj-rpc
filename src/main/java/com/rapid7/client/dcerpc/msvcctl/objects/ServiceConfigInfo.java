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
package com.rapid7.client.dcerpc.msvcctl.objects;

import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;

/**
 *  Query Service Config responses from:
 * https://msdn.microsoft.com/en-us/library/cc245948.aspx
 */

public class ServiceConfigInfo implements IServiceConfigInfo {
    private final ServiceType serviceType;
    private final ServiceStartType startType;
    private final ServiceError errorControl;
    private String binaryPathName;
    private String loadOrderGroup;
    private int tagId;
    private String dependencies;
    private String serviceStartName;
    private String password;
    private String displayName;

    public ServiceConfigInfo(
        ServiceType serviceType,
        ServiceStartType startType,
        ServiceError errorControl)
    {
        this.serviceType = serviceType;
        this.startType = startType;
        this.errorControl = errorControl;
    }

    public ServiceConfigInfo(ServiceType serviceType,
                             ServiceStartType startType,
                             ServiceError errorControl,
                             String binaryPathName,
                             String loadOrderGroup,
                             int tagId,
                             String dependencies,
                             String serviceStartName,
                             String displayName) {
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

    public void setDependencies(String dependencies)
    {
        this.dependencies = dependencies;
    }

    public void setServiceStartName(String serviceStartName)
    {
        this.serviceStartName = serviceStartName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
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
    public String getDependencies() {
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
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceConfigInfo that = (ServiceConfigInfo) o;

        if (tagId != that.tagId) return false;
        if (serviceType != that.serviceType) return false;
        if (startType != that.startType) return false;
        if (errorControl != that.errorControl) return false;
        if (binaryPathName != null ? !binaryPathName.equals(that.binaryPathName) : that.binaryPathName != null)
            return false;
        if (loadOrderGroup != null ? !loadOrderGroup.equals(that.loadOrderGroup) : that.loadOrderGroup != null)
            return false;
        if (dependencies != null ? !dependencies.equals(that.dependencies) : that.dependencies != null) return false;
        if (serviceStartName != null ? !serviceStartName.equals(that.serviceStartName) : that.serviceStartName != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return displayName != null ? displayName.equals(that.displayName) : that.displayName == null;
    }

    @Override
    public int hashCode() {
        int result = serviceType.hashCode();
        result = 31 * result + startType.hashCode();
        result = 31 * result + errorControl.hashCode();
        result = 31 * result + (binaryPathName != null ? binaryPathName.hashCode() : 0);
        result = 31 * result + (loadOrderGroup != null ? loadOrderGroup.hashCode() : 0);
        result = 31 * result + tagId;
        result = 31 * result + (dependencies != null ? dependencies.hashCode() : 0);
        result = 31 * result + (serviceStartName != null ? serviceStartName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        return result;
    }
}
