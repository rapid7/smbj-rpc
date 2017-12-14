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

import java.util.Objects;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServicesAcceptedControls;

public class ServiceStatusInfo implements IServiceStatusInfo {

    private final ServiceType serviceType;
    private final ServiceStatusType currentState;
    private final ServicesAcceptedControls controlsAccepted;
    private final int win32ExitCode;
    private final int serviceSpecificExitCode;
    private final int checkPoint;
    private final int waitHint;

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
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof IServiceStatusInfo)) {
            return false;
        }
        final IServiceStatusInfo other = (IServiceStatusInfo) obj;
        return getServiceType() == other.getServiceType()
                && getCurrentState() == other.getCurrentState()
                && getControlsAccepted() == other.getControlsAccepted()
                && getWin32ExitCode() == other.getWin32ExitCode()
                && getServiceSpecificExitCode() == other.getServiceSpecificExitCode()
                && getCheckPoint() == other.getCheckPoint()
                && getWaitHint() == other.getWaitHint();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServiceType(), getCurrentState(), getControlsAccepted(),
                getWin32ExitCode(), getServiceSpecificExitCode(), getCheckPoint(), getWaitHint());
    }


    @Override
    public String toString() {
        return String.format("ServiceStatusInfo{serviceType: %s, currentState: %s, controlsAccepted: %s, " +
                "win32ExitCode: %d, serviceSpecificExitCode: %d, checkPoint: %d, waitHint: %d}",
                getServiceType(), getCurrentState(), getControlsAccepted(), getWin32ExitCode(),
                getServiceSpecificExitCode(), getCheckPoint(), getWaitHint());
    }
}
