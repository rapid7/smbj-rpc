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

package com.rapid7.client.dcerpc.msvcctl.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245911.aspx">LPSERVICE_STATUS</a>
 * <blockquote><pre>The SERVICE_STATUS structure defines information about a service.
 *      typedef struct {
 *          DWORD dwServiceType;
 *          DWORD dwCurrentState;
 *          DWORD dwControlsAccepted;
 *          DWORD dwWin32ExitCode;
 *          DWORD dwServiceSpecificExitCode;
 *          DWORD dwCheckPoint;
 *          DWORD dwWaitHint;
 *      } SERVICE_STATUS,
 *      *LPSERVICE_STATUS;</pre></blockquote>
 */
public class LPServiceStatus implements Unmarshallable {

    // <NDR: unsigned long> DWORD dwServiceType;
    private int dwServiceType;
    // <NDR: unsigned long> DWORD dwCurrentState;
    private int dwCurrentState;
    // <NDR: unsigned long> DWORD dwControlsAccepted;
    private int dwControlsAccepted;
    // <NDR: unsigned long> DWORD dwWin32ExitCode;
    private int dwWin32ExitCode;
    // <NDR: unsigned long> DWORD dwServiceSpecificExitCode;
    private int dwServiceSpecificExitCode;
    // <NDR: unsigned long> DWORD dwCheckPoint;
    private int dwCheckPoint;
    // <NDR: unsigned long> DWORD dwWaitHint;
    private int dwWaitHint;

    public int getDwServiceType() {
        return dwServiceType;
    }

    public int getDwCurrentState() {
        return dwCurrentState;
    }

    public int getDwControlsAccepted() {
        return dwControlsAccepted;
    }

    public int getDwWin32ExitCode() {
        return dwWin32ExitCode;
    }

    public int getDwServiceSpecificExitCode() {
        return dwServiceSpecificExitCode;
    }

    public int getDwCheckPoint() {
        return dwCheckPoint;
    }

    public int getDwWaitHint() {
        return dwWaitHint;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned long> DWORD dwServiceType;
        // Alignment: 4 - Already aligned
        this.dwServiceType = in.readInt();
        // <NDR: unsigned long> DWORD dwCurrentState;
        // Alignment: 4 - Already aligned
        this.dwCurrentState = in.readInt();
        // <NDR: unsigned long> DWORD dwControlsAccepted;
        // Alignment: 4 - Already aligned
        this.dwControlsAccepted = in.readInt();
        // <NDR: unsigned long> DWORD dwWin32ExitCode;
        // Alignment: 4 - Already aligned
        this.dwWin32ExitCode = in.readInt();
        // <NDR: unsigned long> DWORD dwServiceSpecificExitCode;
        // Alignment: 4 - Already aligned
        this.dwServiceSpecificExitCode = in.readInt();
        // <NDR: unsigned long> DWORD dwCheckPoint;
        // Alignment: 4 - Already aligned
        this.dwCheckPoint = in.readInt();
        // <NDR: unsigned long> DWORD dwWaitHint;
        // Alignment: 4 - Already aligned
        this.dwWaitHint = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // No deferrals
    }


}
