package com.rapid7.client.dcerpc.msvcctl.objects;


// https://msdn.microsoft.com/en-us/library/windows/desktop/ms685996(v=vs.85).aspx

public class RQueryServiceStatusInfo
{
    final private int serviceType;
    final private int currentState;
    final private int controlsAccepted;
    final private int win32ExitCode;
    final private int serviceSpecificExitCode;
    final private int checkPoint;
    final private int waitHint;

    public RQueryServiceStatusInfo(
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


}
