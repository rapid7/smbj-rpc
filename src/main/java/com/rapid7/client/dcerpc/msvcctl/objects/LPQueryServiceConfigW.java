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
import java.util.Arrays;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245871.aspx">LPQUERY_SERVICE_CONFIGW</a>
 * <blockquote><pre>The QUERY_SERVICE_CONFIGW structure defines configuration information about an installed service. String values are stored in Unicode.
 *      typedef struct _QUERY_SERVICE_CONFIGW {
 *          DWORD dwServiceType;
 *          DWORD dwStartType;
 *          DWORD dwErrorControl;
 *          [string,range(0, 8 * 1024)] LPWSTR lpBinaryPathName;
 *          [string,range(0, 8 * 1024)] LPWSTR lpLoadOrderGroup;
 *          DWORD dwTagId;
 *          [string,range(0, 8 * 1024)] LPWSTR lpDependencies;
 *          [string,range(0, 8 * 1024)] LPWSTR lpServiceStartName;
 *          [string,range(0, 8 * 1024)] LPWSTR lpDisplayName;
 *      } QUERY_SERVICE_CONFIGW,
 *      *LPQUERY_SERVICE_CONFIGW;</pre></blockquote>
 */
public class LPQueryServiceConfigW implements Unmarshallable {
    private static final String[] EMPTY_DEPENDENCIES = new String[0];

    // <NDR: unsigned long> DWORD dwServiceType;
    private int dwServiceType;
    // <NDR: unsigned long> DWORD dwStartType;
    private int dwStartType;
    // <NDR: unsigned long> DWORD dwErrorControl;
    private int dwErrorControl;
    // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpBinaryPathName;
    private WChar.NullTerminated lpBinaryPathName;
    // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpLoadOrderGroup;
    private WChar.NullTerminated lpLoadOrderGroup;
    // <NDR: unsigned long> DWORD dwTagId;
    private int dwTagId;
    // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpDependencies;
    private String[] lpDependencies;
    // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpServiceStartName;
    private WChar.NullTerminated lpServiceStartName;
    // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpDisplayName;
    private WChar.NullTerminated lpDisplayName;

    public int getDwServiceType() {
        return dwServiceType;
    }

    public void setDwServiceType(int dwServiceType) {
        this.dwServiceType = dwServiceType;
    }

    public int getDwStartType() {
        return dwStartType;
    }

    public void setDwStartType(int dwStartType) {
        this.dwStartType = dwStartType;
    }

    public int getDwErrorControl() {
        return dwErrorControl;
    }

    public void setDwErrorControl(int dwErrorControl) {
        this.dwErrorControl = dwErrorControl;
    }

    public WChar.NullTerminated getLpBinaryPathName() {
        return lpBinaryPathName;
    }

    public void setLpBinaryPathName(WChar.NullTerminated lpBinaryPathName) {
        this.lpBinaryPathName = lpBinaryPathName;
    }

    public WChar.NullTerminated getLpLoadOrderGroup() {
        return lpLoadOrderGroup;
    }

    public void setLpLoadOrderGroup(WChar.NullTerminated lpLoadOrderGroup) {
        this.lpLoadOrderGroup = lpLoadOrderGroup;
    }

    public int getDwTagId() {
        return dwTagId;
    }

    public void setDwTagId(int dwTagId) {
        this.dwTagId = dwTagId;
    }

    public String[] getLpDependencies() {
        return lpDependencies;
    }

    public void setLpDependencies(String[] lpDependencies) {
        this.lpDependencies = lpDependencies;
    }

    public WChar.NullTerminated getLpServiceStartName() {
        return lpServiceStartName;
    }

    public void setLpServiceStartName(WChar.NullTerminated lpServiceStartName) {
        this.lpServiceStartName = lpServiceStartName;
    }

    public WChar.NullTerminated getLpDisplayName() {
        return lpDisplayName;
    }

    public void setLpDisplayName(WChar.NullTerminated lpDisplayName) {
        this.lpDisplayName = lpDisplayName;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble, no non-referent conformant arrays
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned long> DWORD dwServiceType;
        // Alignment: 4 - Already aligned
        this.dwServiceType = in.readInt();
        // <NDR: unsigned long> DWORD dwStartType;
        // Alignment: 4 - Already aligned
        this.dwStartType = in.readInt();
        // <NDR: unsigned long> DWORD dwErrorControl;
        // Alignment: 4 - Already aligned
        this.dwErrorControl = in.readInt();
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpBinaryPathName;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.lpBinaryPathName = new WChar.NullTerminated();
        else
            this.lpBinaryPathName = null;
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpLoadOrderGroup;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.lpLoadOrderGroup = new WChar.NullTerminated();
        else
            this.lpLoadOrderGroup = null;
        // <NDR: unsigned long> DWORD dwTagId;
        // Alignment: 4 - Already aligned
        this.dwTagId = in.readInt();
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpDependencies;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.lpDependencies = EMPTY_DEPENDENCIES;
        else
            this.lpDependencies = null;
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpServiceStartName;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.lpServiceStartName = new WChar.NullTerminated();
        else
            this.lpServiceStartName = null;
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpDisplayName;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.lpDisplayName = new WChar.NullTerminated();
        else
            this.lpDisplayName = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpBinaryPathName;
        if (this.lpBinaryPathName != null)
            in.readUnmarshallable(this.lpBinaryPathName);
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpLoadOrderGroup;
        if (this.lpLoadOrderGroup != null)
            in.readUnmarshallable(this.lpLoadOrderGroup);
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpDependencies;
        if (this.lpDependencies != null) {
            /*
             * A pointer to an array of null-separated names of services or service groups that
             * MUST start before this service. The array is doubly null-terminated.
             * Service group names are prefixed with a "+" character (to distinguish them
             * from service names). If the pointer is NULL or if it points to an empty string,
             * the service has no dependencies. Cyclic dependency between services is not allowed.
             * The character set is Unicode. Dependency on a service means that this service can only
             * run if the service it depends on is running. Dependency on a group means that this service
             * can run if at least one member of the group is running after an attempt to start all members of the group.
             */
            // The above documentation from Microsoft is a lie.
            // It does indeed null terminate the 'array', but not doubly.
            // What this actually is, is a regular WChar that is null terminated, and instead of
            // additional nulls, it uses UTF-16 0x0047 ('/').
            //      e.g. "SamSS/NTDS/"
            // Therefore, we will read this as if it were a single string, and use '/' as our delimiter.
            final WChar.NullTerminated lpDependency = new WChar.NullTerminated();
            in.readUnmarshallable(lpDependency);
            // String.split does not use a regex for single character expressions, so this is efficient enough
            this.lpDependencies = lpDependency.getValue().split("/");
        }
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpServiceStartName;
        if (this.lpServiceStartName != null)
            in.readUnmarshallable(this.lpServiceStartName);
        // <NDR: pointer[struct]> [string,range(0, 8 * 1024)] LPWSTR lpDisplayName;
        if (this.lpDisplayName != null)
            in.readUnmarshallable(this.lpDisplayName);
    }

    @Override
    public int hashCode() {
        int ret = Objects.hash(getDwServiceType(), getDwStartType(), getDwErrorControl(),
                getLpBinaryPathName(), getLpLoadOrderGroup(), getDwTagId(), getLpServiceStartName(), getLpDisplayName());
        return (ret * 31) + Arrays.hashCode(getLpDependencies());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LPQueryServiceConfigW)) {
            return false;
        }
        final LPQueryServiceConfigW other = (LPQueryServiceConfigW) obj;
        return getDwServiceType() == other.getDwServiceType()
                && getDwStartType() == other.getDwStartType()
                && getDwErrorControl() == other.getDwErrorControl()
                && Objects.equals(getLpBinaryPathName(), other.getLpBinaryPathName())
                && Objects.equals(getLpLoadOrderGroup(), other.getLpLoadOrderGroup())
                && getDwTagId() == other.getDwTagId()
                && Arrays.equals(getLpDependencies(), other.getLpDependencies())
                && Objects.equals(getLpServiceStartName(), other.getLpServiceStartName())
                && Objects.equals(getLpDisplayName(), other.getLpDisplayName());
    }

    @Override
    public String toString() {
        return String.format("QUERY_SERVICE_CONFIGW{dwServiceType: %d, dwStartType: %d, dwErrorControl: %d, " +
                        "lpBinaryPathName: %s, lpLoadOrderGroup: %s, dwTagId: %d, lpDependencies: %s, " +
                        "lpServiceStartName: %s, lpDisplayName: %s}",
                getDwServiceType(), getDwStartType(), getDwErrorControl(), getLpBinaryPathName(), getLpLoadOrderGroup(),
                getDwTagId(), Arrays.toString(getLpDependencies()), getLpServiceStartName(), getLpDisplayName());
    }
}
