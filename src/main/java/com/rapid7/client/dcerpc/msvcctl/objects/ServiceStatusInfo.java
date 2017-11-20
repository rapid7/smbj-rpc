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


import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServicesAcceptedControls;

/**
 * Service Status responses from:
 * https://msdn.microsoft.com/en-us/library/cc245911.aspx
 */

public class ServiceStatusInfo implements IServiceStatusInfo {

    final private ServiceType serviceType;
    final private ServiceStatusType currentState;
    final private ServicesAcceptedControls controlsAccepted;
    final private int win32ExitCode;
    final private int serviceSpecificExitCode;
    final private int checkPoint;
    final private int waitHint;

    public ServiceStatusInfo(ServiceType serviceType, ServiceStatusType currentState, ServicesAcceptedControls controlsAccepted, int win32ExitCode, int serviceSpecificExitCode, int checkPoint, int waitHint) {
        this.serviceType = serviceType;
        this.currentState = currentState;
        this.controlsAccepted = controlsAccepted;
        this.win32ExitCode = win32ExitCode;
        this.serviceSpecificExitCode = serviceSpecificExitCode;
        this.checkPoint = checkPoint;
        this.waitHint = waitHint;
    }

    @Override
    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public ServiceStatusType getCurrentState() {
        return currentState;
    }

    @Override
    public ServicesAcceptedControls getControlsAccepted() {
        return controlsAccepted;
    }

    @Override
    public int getWin32ExitCode() {
        return win32ExitCode;
    }

    @Override
    public int getServiceSpecificExitCode() {
        return serviceSpecificExitCode;
    }

    @Override
    public int getCheckPoint() {
        return checkPoint;
    }

    @Override
    public int getWaitHint() {
        return waitHint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceStatusInfo that = (ServiceStatusInfo) o;

        if (win32ExitCode != that.win32ExitCode) return false;
        if (serviceSpecificExitCode != that.serviceSpecificExitCode) return false;
        if (checkPoint != that.checkPoint) return false;
        if (waitHint != that.waitHint) return false;
        if (serviceType != that.serviceType) return false;
        if (currentState != that.currentState) return false;
        return controlsAccepted == that.controlsAccepted;
    }

    @Override
    public int hashCode() {
        int result = serviceType.hashCode();
        result = 31 * result + currentState.hashCode();
        result = 31 * result + controlsAccepted.hashCode();
        result = 31 * result + win32ExitCode;
        result = 31 * result + serviceSpecificExitCode;
        result = 31 * result + checkPoint;
        result = 31 * result + waitHint;
        return result;
    }
}
