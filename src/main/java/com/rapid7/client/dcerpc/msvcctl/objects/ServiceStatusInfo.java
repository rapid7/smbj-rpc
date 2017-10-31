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
 *  Service Status responses from:
 * https://msdn.microsoft.com/en-us/library/cc245911.aspx
 */

public class ServiceStatusInfo
{
    // Service Type
    public final static int SERVICE_KERNEL_DRIVER = 0x1;
    public final static int SERVICE_FILE_SYSTEM_DRIVER = 0x2;
    public final static int SERVICE_WIN32_OWN_PROCESS = 0x10;
    public final static int SERVICE_WIN32_SHARE_PROCESS = 0x20;
    public final static int SERVICE_WIN32_INTERACTIVE_PROCESS = 0x100;

    //Current State
    public final static int SERVICE_STOPPED = 0x1;
    public final static int SERVICE_START_PENDING = 0x2;
    public final static int SERVICE_STOP_PENDING = 0x3;
    public final static int SERVICE_RUNNING = 0x4;
    public final static int SERVICE_CONTINUE_PENDING = 0x5;
    public final static int SERVICE_PAUSE_PENDING = 0x6;
    public final static int SERVICE_PAUSED = 0x7;

    //Control codes accepted
    public final static int SERVICE_ACCEPT_NONE = 0x0;
    public final static int SERVICE_ACCEPT_STOP = 0x1;
    public final static int SERVICE_ACCEPT_PAUSE_CONTINUE = 0x2;
    public final static int SERVICE_ACCEPT_SHUTDOWN = 0x4;
    public final static int SERVICE_ACCEPT_PARAMCHANGE = 0x8;
    public final static int SERVICE_ACCEPT_HARDWAREPROFILECHANGE = 0x20;
    public final static int SERVICE_ACCEPT_POWEREVENT = 0x40;
    public final static int SERVICE_ACCEPT_SESSIONCHANGE = 0x80;
    public final static int SERVICE_ACCEPT_PRESHUTDOWN = 0x100;
    public final static int SERVICE_ACCEPT_TIMECHANGE = 0x200;
    public final static int SERVICE_ACCEPT_TRIGGEREVENT = 0x400;

    final private int serviceType;
    final private int currentState;
    final private int controlsAccepted;
    final private int win32ExitCode;
    final private int serviceSpecificExitCode;
    final private int checkPoint;
    final private int waitHint;

    public ServiceStatusInfo(
        int serviceType,
        int currentState,
        int controlsAccepted,
        int win32ExitCode,
        int serviceSpecificExitCode,
        int checkPoint,
        int waitHint)
    {
        this.serviceType = serviceType;
        this.currentState = currentState;
        this.controlsAccepted = controlsAccepted;
        this.win32ExitCode = win32ExitCode;
        this.serviceSpecificExitCode = serviceSpecificExitCode;
        this.checkPoint = checkPoint;
        this.waitHint = waitHint;
    }

    public static int getServiceKernelDriver()
    {
        return SERVICE_KERNEL_DRIVER;
    }

    public int getServiceType()
    {
        return serviceType;
    }

    public int getCurrentState()
    {
        return currentState;
    }

    public int getControlsAccepted()
    {
        return controlsAccepted;
    }

    public int getWin32ExitCode()
    {
        return win32ExitCode;
    }

    public int getServiceSpecificExitCode()
    {
        return serviceSpecificExitCode;
    }

    public int getCheckPoint()
    {
        return checkPoint;
    }

    public int getWaitHint()
    {
        return waitHint;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ServiceStatusInfo that = (ServiceStatusInfo)o;

        if (serviceType != that.serviceType)
            return false;
        if (currentState != that.currentState)
            return false;
        if (controlsAccepted != that.controlsAccepted)
            return false;
        if (win32ExitCode != that.win32ExitCode)
            return false;
        if (serviceSpecificExitCode != that.serviceSpecificExitCode)
            return false;
        if (checkPoint != that.checkPoint)
            return false;
        return waitHint == that.waitHint;
    }

    @Override public int hashCode()
    {
        int result = serviceType;
        result = 31 * result + currentState;
        result = 31 * result + controlsAccepted;
        result = 31 * result + win32ExitCode;
        result = 31 * result + serviceSpecificExitCode;
        result = 31 * result + checkPoint;
        result = 31 * result + waitHint;
        return result;
    }
}
