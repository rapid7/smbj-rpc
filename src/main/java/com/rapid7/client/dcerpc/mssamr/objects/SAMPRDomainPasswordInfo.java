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
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * Alignment: 8
   typedef struct _DOMAIN_PASSWORD_INFORMATION {
    USHORT        MinPasswordLength;
    USHORT        PasswordHistoryLength;
    ULONG         PasswordProperties;
    LARGE_INTEGER MaxPasswordAge;
    LARGE_INTEGER MinPasswordAge;
  } DOMAIN_PASSWORD_INFORMATION, *PDOMAIN_PASSWORD_INFORMATION;
  */
public class SAMPRDomainPasswordInfo implements Unmarshallable {

    // <NDR: unsigned short> USHORT MinPasswordLength;
    private int minPasswordLength;
    // <NDR: unsigned short> USHORT PasswordHistoryLength;
    private int passwordHistoryLength;
    // <NDR: unsigned long> ULONG PasswordProperties;
    // This is a bit field so we can store as an int
    private int passwordProperties;
    // <NDR: hyper> LARGE_INTEGER MaxPasswordAge;
    private long maxPasswordAge;
    // <NDR: hyper> LARGE_INTEGER MinPasswordAge;
    private long minPasswordAge;

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public int getPasswordHistoryLength() {
        return passwordHistoryLength;
    }

    public int getPasswordProperties() {
        return passwordProperties;
    }

    public long getMaxPasswordAge() {
        return maxPasswordAge;
    }

    public long getMinPasswordAge() {
        return minPasswordAge;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No premable
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 8
        in.align(Alignment.EIGHT);
        // <NDR: unsigned short> MinPasswordLength;
        // Alignment: 2 - Already aligned
        this.minPasswordLength = in.readShort();
        // <NDR: unsigned short> PasswordHistoryLength;
        // Alignment: 2 - Already aligned
        this.passwordHistoryLength = in.readShort();
        // <NDR: unsigned long> unsigned long PasswordProperties;
        // Alignment: 4 - Already aligned
        this.passwordProperties = in.readInt();
        // <NDR: hyper> OLD_LARGE_INTEGER MaxPasswordAge;
        // Alignment: 8 - Already aligned
        this.maxPasswordAge = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER MinPasswordAge;
        // Alignment: 8 - Already aligned
        this.minPasswordAge = in.readLong();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // No deferrals
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinPasswordLength(), getPasswordHistoryLength(), getPasswordProperties(),
            getMaxPasswordAge(), getMinPasswordAge());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainPasswordInfo)) {
            return false;
        }
        SAMPRDomainPasswordInfo other = (SAMPRDomainPasswordInfo) obj;
        return Objects.equals(getMinPasswordLength(), other.getMinPasswordLength())
            && Objects.equals(getPasswordHistoryLength(), other.getPasswordHistoryLength())
            && Objects.equals(getPasswordProperties(), other.getPasswordProperties())
            && Objects.equals(getMaxPasswordAge(), other.getMaxPasswordAge())
            && Objects.equals(getMinPasswordAge(), other.getMinPasswordAge());
    }

    @Override
    public String toString() {
        return String.format(
            "SAMPRDomainPasswordInfo{minimumPasswordLength:%s, passwordHistoryLength:%s,passwordProperties:%s, "
                + "maximumPasswordAge:%s, minimumPasswordAge:%s}",
            getMinPasswordLength(), getPasswordHistoryLength(), getPasswordProperties(), getMaxPasswordAge(),
            getMinPasswordAge());
    }
}
