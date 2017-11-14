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

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Arrays;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <b>Alignment: 4</b> (Max[2,4])<pre>
 *     unsigned short UnitsPerWeek;: 2
 *     [size_is(1260), length_is((UnitsPerWeek+7)/8)] unsigned char* LogonHours;: 4
 * </pre>
 * <blockquote><pre>The SAMPR_LOGON_HOURS structure contains logon policy information that describes when a user account is permitted to authenticate.
 *      typedef struct _SAMPR_LOGON_HOURS {
 *          unsigned short UnitsPerWeek;
 *          [size_is(1260), length_is((UnitsPerWeek+7)/8)] unsigned char* LogonHours;
 *      } SAMPR_LOGON_HOURS,
 *      *PSAMPR_LOGON_HOURS;
 *  UnitsPerWeek: A division of the week (7 days). For example, the value 7 means that each unit is a day; a value of (7*24) means that the units are hours. The minimum granularity of time is one minute, where the UnitsPerWeek would be 10080; therefore, the maximum size of LogonHours is 10080/8, or 1,260 bytes.
 *  LogonHours: A pointer to a bit field containing at least UnitsPerWeek number of bits. The leftmost bit represents the first unit, starting at Sunday, 12 A.M. If a bit is set, authentication is allowed to occur; otherwise, authentication is not allowed to occur.
 *  For example, if the UnitsPerWeek value is 168 (that is, the units per week is hours, resulting in a 21-byte bit field), and if the leftmost bit is set and the rightmost bit is set, the user is able to log on for two consecutive hours between Saturday, 11 P.M. and Sunday, 1 A.M.</pre></blockquote>
 */
public class SAMPRLogonHours implements Unmarshallable {
    // <NDR: unsigned short> unsigned short UnitsPerWeek;
    private short unitsPerWeek;
    // <NDR: pointer> [size_is(1260), length_is((UnitsPerWeek+7)/8)] unsigned char* LogonHours;
    private char[] logonHours;

    public short getUnitsPerWeek() {
        return unitsPerWeek;
    }

    public void setUnitsPerWeek(short unitsPerWeek) {
        this.unitsPerWeek = unitsPerWeek;
    }

    public char[] getLogonHours() {
        return logonHours;
    }

    public void setLogonHours(char[] logonHours) {
        this.logonHours = logonHours;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned short> unsigned short UnitsPerWeek;
        // Alignment: 2 - Already aligned
        this.unitsPerWeek = in.readShort();
        // <NDR: pointer> [size_is(1260), length_is((UnitsPerWeek+7)/8)] unsigned char* LogonHours;
        in.fullySkipBytes(2); // Alignment: 4 - Wrote exactly two bytes above since alignment
        if (in.readReferentID() != 0) {
            logonHours = new char[(unitsPerWeek + 7) / 8]; // Integer division is intended - Adding 7 allows this to round down
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (logonHours != null) {
            // <NDR conformant varying array> [size_is(1260), length_is((UnitsPerWeek+7)/8)] unsigned char* LogonHours;
            // Array alignment: 4
            in.align(Alignment.FOUR);
            // <NDR: unsigned long> MaximumCount
            // Alignment: 4 - Already aligned
            in.fullySkipBytes(4);
            // <NDR: unsigned long> Offset
            // Alignment: 4 - Already aligned
            in.fullySkipBytes(4);
            // <NDR: unsigned long> ActualCount
            // Alignment: 4 - Already aligned
            in.fullySkipBytes(4);
            // <NDR: unsigned char> unsigned char
            // Alignment: 1 - Already aligned
            for (int i = 0; i < logonHours.length; i++) {
                logonHours[i] = in.readUnsignedByte();
            }
        }
    }

    @Override
    public int hashCode() {
        int ret = (int) this.unitsPerWeek;
        ret = 31 * ret + Arrays.hashCode(logonHours);
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRLogonHours)) {
            return false;
        }
        SAMPRLogonHours other = (SAMPRLogonHours) obj;
        return getUnitsPerWeek() == other.getUnitsPerWeek()
                && Arrays.equals(getLogonHours(), other.getLogonHours());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_LOGON_HOURS{UnitsPerWeek:%d,size(LogonHours):%s}",
                this.unitsPerWeek, (this.logonHours == null ? "null" : this.logonHours.length));
    }
}
