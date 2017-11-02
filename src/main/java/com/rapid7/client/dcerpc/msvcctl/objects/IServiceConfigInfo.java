package com.rapid7.client.dcerpc.msvcctl.objects;

import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;

public interface IServiceConfigInfo {
    ServiceType getServiceType();

    ServiceStartType getStartType();

    ServiceError getErrorControl();

    String getBinaryPathName();

    String getLoadOrderGroup();

    int getTagId();

    String getDependencies();

    String getServiceStartName();

    String getDisplayName();

    String getPassword();

    void setPassword(String password);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
