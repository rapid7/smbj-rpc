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

package com.rapid7.client.dcerpc.mslsad.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
   typedef struct _DOMAIN_PASSWORD_INFORMATION {
    USHORT        MinPasswordLength;
    USHORT        PasswordHistoryLength;
    ULONG         PasswordProperties;
    LARGE_INTEGER MaxPasswordAge;
    LARGE_INTEGER MinPasswordAge;
  } DOMAIN_PASSWORD_INFORMATION, *PDOMAIN_PASSWORD_INFORMATION;
  */
public class SAMPRDomainPasswordInfo implements Unmarshallable {
    private static final int DOMAIN_PASSWORD_COMPLEX = 0x00000001;
    private static final int DOMAIN_PASSWORD_NO_ANON_CHANGE = 0x00000002;
    private static final int DOMAIN_PASSWORD_STORE_CLEARTEXT = 0x00000010;
    /**
     * A 32-bit bit field indicating the password properties policy setting. The defined bits are shown in the
     * following table. All bits can be combined using a logical OR in any combination. Undefined bits SHOULD be
     * persisted by the server (that is, stored in its database) and returned to future queries. Clients SHOULD ignore
     * undefined bits.
     *
     * +---------------------------------+--------------------------------------------------------------------------+
     * | Name/value                      | Description                                                              |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_COMPLEX         | The server enforces password complexity policy.                          |
     * | 0x00000001                      |                                                                          |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_NO_ANON_CHANGE  | Reserved. No effect on password policy.                                  |
     * | 0x00000002                      |                                                                          |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_NO_CLEAR_CHANGE | Change-password methods that provide the cleartext password are disabled |
     * | 0x00000004                      | by the server.                                                           |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_LOCKOUT_ADMINS           | Reserved. No effect on password policy.                                  |
     * | 0x00000008                      |                                                                          |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_STORE_CLEARTEXT | The server MUST store the cleartext password, not just the computed      |
     * | 0x00000010                      | hashes.                                                                  |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_REFUSE_PASSWORD_CHANGE   | Reserved. No effect on password policy.                                  |
     * | 0x00000020                      |                                                                          |
     * +---------------------------------+--------------------------------------------------------------------------+
     */
    private short minimumPasswordLength;
    private short passwordHistoryLength;
    private int passwordProperties;
    private int maximumPasswordAge;
    private int minimumPasswordAge;

    public boolean isDomainPasswordComplex() {
        return (this.passwordProperties & DOMAIN_PASSWORD_COMPLEX) != 0;
    }

    public boolean isDomainPasswordNoAnonChange() {
        return (this.passwordProperties & DOMAIN_PASSWORD_NO_ANON_CHANGE) != 0;
    }

    public boolean isDomainPasswordStoredClearText() {
        return (this.passwordProperties & DOMAIN_PASSWORD_STORE_CLEARTEXT) != 0;
    }

    public short getMinimumPasswordLength() {
        return minimumPasswordLength;
    }

    public short getPasswordHistoryLength() {
        return passwordHistoryLength;
    }

    public int getPasswordProperties() {
        return passwordProperties;
    }

    public int getMaximumPasswordAge() {
        return maximumPasswordAge;
    }

    public int getMinimumPasswordAge() {
        return minimumPasswordAge;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
       /* typedef struct _DOMAIN_PASSWORD_INFORMATION {
            USHORT        MinPasswordLength;
            USHORT        PasswordHistoryLength;
            ULONG         PasswordProperties;
            LARGE_INTEGER MaxPasswordAge;
            LARGE_INTEGER MinPasswordAge;
          } DOMAIN_PASSWORD_INFORMATION, *PDOMAIN_PASSWORD_INFORMATION;
        */
        this.minimumPasswordLength = in.readShort();
        this.passwordHistoryLength = in.readShort();
        this.passwordProperties = in.readInt();
        this.maximumPasswordAge = in.readInterval();
        this.minimumPasswordAge = in.readInterval();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinimumPasswordLength(), getPasswordHistoryLength(), getPasswordProperties(),
            getMaximumPasswordAge(), getMinimumPasswordAge(), isDomainPasswordComplex(), isDomainPasswordNoAnonChange(),
            isDomainPasswordStoredClearText());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainPasswordInfo)) {
            return false;
        }
        SAMPRDomainPasswordInfo other = (SAMPRDomainPasswordInfo) obj;
        return Objects.equals(getMinimumPasswordLength(), other.getMinimumPasswordLength()) &&
               Objects.equals(getPasswordHistoryLength(), other.getPasswordHistoryLength()) &&
               Objects.equals(getPasswordProperties(), other.getPasswordProperties()) &&
               Objects.equals(getMaximumPasswordAge(), other.getMaximumPasswordAge()) &&
               Objects.equals(getMinimumPasswordAge(), other.getMinimumPasswordAge()) &&
               Objects.equals(isDomainPasswordComplex(), other.isDomainPasswordComplex()) &&
               Objects.equals(isDomainPasswordNoAnonChange(), other.isDomainPasswordNoAnonChange()) &&
               Objects.equals(isDomainPasswordStoredClearText(), other.isDomainPasswordStoredClearText());
    }

    @Override
    public String toString() {
        return String.format(
            "SAMPRDomainPasswordInfo{minimumPasswordLength:%s, passwordHistoryLength:%s,passwordProperties:%s, "
                + "maximumPasswordAge:%s, minimumPasswordAge:%s, isDomainPasswordComplex:%s, isDomainPasswordNoAnonChange:%s, "
                + "isDomainPasswordStoredClearText:%s}",
            getMinimumPasswordLength(), getPasswordHistoryLength(), getPasswordProperties(), getMaximumPasswordAge(),
            getMinimumPasswordAge(), isDomainPasswordComplex(), isDomainPasswordNoAnonChange(),
            isDomainPasswordStoredClearText());
    }
}
