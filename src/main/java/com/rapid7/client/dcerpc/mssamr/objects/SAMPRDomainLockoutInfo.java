/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245569.aspx">SAMPR_DOMAIN_LOCKOUT_INFORMATION</a>
 *
 * <blockquote>
 * <pre>
 * The SAMPR_DOMAIN_LOCKOUT_INFORMATION structure contains domain fields.
 *
 * typedef struct _SAMPR_DOMAIN_LOCKOUT_INFORMATION {
 *   LARGE_INTEGER LockoutDuration;
 *   LARGE_INTEGER LockoutObservationWindow;
 *   unsigned short LockoutThreshold;
 * } SAMPR_DOMAIN_LOCKOUT_INFORMATION,
 *  *PSAMPR_DOMAIN_LOCKOUT_INFORMATION;
 *
 * LockoutDuration: A 64-bit value, with delta time syntax, indicating the duration for which an account is locked out before being automatically
 *  reset to an unlocked state.
 * LockoutObservationWindow: A 64-bit value, with delta time syntax, indicating the time period in which failed password attempts are counted without
 *  resetting the count to zero.
 * LockoutThreshold: A 16-bit unsigned integer indicating the number of bad password attempts within a LockoutObservationWindow that will cause an
 *  account to be locked out.
 * </pre>
 * </blockquote>
 */
public class SAMPRDomainLockoutInfo implements Unmarshallable {
    private long lockoutDuration;
    private long lockoutObservationWindow;
    private int lockoutThreshold;

    public long getLockoutDuration() {
        return lockoutDuration;
    }

    public long getLockoutObservationWindow() {
        return lockoutObservationWindow;
    }

    public int getLockoutThreshold() {
        return lockoutThreshold;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 8
        in.align(Alignment.EIGHT);
        // <NDR: hyper> OLD_LARGE_INTEGER LockoutDuration;
        // Alignment: 8 - Already aligned
        this.lockoutDuration = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER LockoutObservationWindow;
        // Alignment: 8 - Already aligned
        this.lockoutObservationWindow = in.readLong();
        // <NDR: unsigned short> unsigned short LockoutThreshold;
        // Alignment: 2 - Already aligned
        this.lockoutThreshold = in.readShort();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLockoutDuration(), getLockoutObservationWindow(), getLockoutThreshold());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainLockoutInfo)) {
            return false;
        }
        SAMPRDomainLockoutInfo other = (SAMPRDomainLockoutInfo) obj;
        return Objects.equals(getLockoutDuration(), other.getLockoutDuration())
            && Objects.equals(getLockoutObservationWindow(), other.getLockoutObservationWindow())
            && Objects.equals(getLockoutThreshold(), other.getLockoutThreshold());
    }

    @Override
    public String toString() {
        return String.format(
            "SAMPRDomainLockoutInfo{lockoutDuration:%s, lockoutObservationWindow:%s,lockoutThreshold:%s}",
            getLockoutDuration(), getLockoutObservationWindow(), getLockoutThreshold());
    }
}
