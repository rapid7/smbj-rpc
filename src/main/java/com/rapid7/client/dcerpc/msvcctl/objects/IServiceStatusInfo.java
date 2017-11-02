package com.rapid7.client.dcerpc.msvcctl.objects;

import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServicesAcceptedControls;

public interface IServiceStatusInfo {
    ServiceType getServiceType();

    ServiceStatusType getCurrentState();

    ServicesAcceptedControls getControlsAccepted();

    int getWin32ExitCode();

    int getServiceSpecificExitCode();

    int getCheckPoint();

    int getWaitHint();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
