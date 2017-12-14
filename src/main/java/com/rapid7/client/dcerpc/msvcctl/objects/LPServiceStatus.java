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
import java.util.Objects;
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

    public void setDwServiceType(int dwServiceType) {
        this.dwServiceType = dwServiceType;
    }

    public int getDwCurrentState() {
        return dwCurrentState;
    }

    public void setDwCurrentState(int dwCurrentState) {
        this.dwCurrentState = dwCurrentState;
    }

    public int getDwControlsAccepted() {
        return dwControlsAccepted;
    }

    public void setDwControlsAccepted(int dwControlsAccepted) {
        this.dwControlsAccepted = dwControlsAccepted;
    }

    public int getDwWin32ExitCode() {
        return dwWin32ExitCode;
    }

    public void setDwWin32ExitCode(int dwWin32ExitCode) {
        this.dwWin32ExitCode = dwWin32ExitCode;
    }

    public int getDwServiceSpecificExitCode() {
        return dwServiceSpecificExitCode;
    }

    public void setDwServiceSpecificExitCode(int dwServiceSpecificExitCode) {
        this.dwServiceSpecificExitCode = dwServiceSpecificExitCode;
    }

    public int getDwCheckPoint() {
        return dwCheckPoint;
    }

    public void setDwCheckPoint(int dwCheckPoint) {
        this.dwCheckPoint = dwCheckPoint;
    }

    public int getDwWaitHint() {
        return dwWaitHint;
    }

    public void setDwWaitHint(int dwWaitHint) {
        this.dwWaitHint = dwWaitHint;
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

    @Override
    public int hashCode() {
        return Objects.hash(getDwServiceType(), getDwCurrentState(), getDwControlsAccepted(),
                getDwWin32ExitCode(), getDwServiceSpecificExitCode(), getDwCheckPoint(), getDwWaitHint());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LPServiceStatus)) {
            return false;
        }
        final LPServiceStatus other = (LPServiceStatus) obj;
        return getDwServiceType() == other.getDwServiceType()
                && getDwCurrentState() == other.getDwCurrentState()
                && getDwControlsAccepted() == other.getDwControlsAccepted()
                && getDwWin32ExitCode() == other.getDwWin32ExitCode()
                && getDwServiceSpecificExitCode() == other.getDwServiceSpecificExitCode()
                && getDwCheckPoint() == other.getDwCheckPoint()
                && getDwWaitHint() == other.getDwWaitHint();
    }

    @Override
    public String toString() {
        return String.format("LPSERVICE_STATUS{dwServiceType: %d, dwCurrentState: %d, dwControlsAccepted: %d, " +
                "dwWin32ExitCode: %d, dwServiceSpecificExitCode: %d, dwCheckPoint: %d, dwWaitHint: %d}",
                getDwServiceType(), getDwCurrentState(), getDwControlsAccepted(), getDwWin32ExitCode(),
                getDwServiceSpecificExitCode(), getDwCheckPoint(), getDwWaitHint());
    }
}
