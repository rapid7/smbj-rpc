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

package com.rapid7.client.dcerpc.mssamr.dto;

import java.util.Objects;

/**
 * This class represents domain password information
 */
public class DomainPasswordInfo {

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
    private static final int DOMAIN_PASSWORD_COMPLEX = 0x00000001;
    private static final int DOMAIN_PASSWORD_NO_ANON_CHANGE = 0x00000002;
    private static final int DOMAIN_PASSWORD_STORE_CLEARTEXT = 0x00000010;

    private final int minimumPasswordLength;
    private final int passwordHistoryLength;
    private final int passwordProperties;
    private final long maximumPasswordAge;
    private final long minimumPasswordAge;

    public DomainPasswordInfo(int minimumPasswordLength, int passwordHistoryLength, int passwordProperties,
            long maximumPasswordAge, long minimumPasswordAge) {
        this.minimumPasswordLength = minimumPasswordLength;
        this.passwordHistoryLength = passwordHistoryLength;
        this.passwordProperties = passwordProperties;
        this.maximumPasswordAge = maximumPasswordAge;
        this.minimumPasswordAge = minimumPasswordAge;
    }

    public boolean isDomainPasswordComplex() {
        return (this.passwordProperties & DOMAIN_PASSWORD_COMPLEX) != 0;
    }

    public boolean isDomainPasswordNoAnonChange() {
        return (this.passwordProperties & DOMAIN_PASSWORD_NO_ANON_CHANGE) != 0;
    }

    public boolean isDomainPasswordStoredClearText() {
        return (this.passwordProperties & DOMAIN_PASSWORD_STORE_CLEARTEXT) != 0;
    }

    /**
     * @return Minimum length of passwords.
     */
    public int getMinimumPasswordLength() {
        return minimumPasswordLength;
    }

    /**
     * @return How many passwords to remember.
     */
    public int getPasswordHistoryLength() {
        return passwordHistoryLength;
    }

    /**
     * @return A bit field representing password settings. You can use the following convenience
     * methods for interpretation: {@link #isDomainPasswordComplex()},
     * {@link #isDomainPasswordNoAnonChange()}, {@link #isDomainPasswordStoredClearText()}
     */
    public int getPasswordProperties() {
        return passwordProperties;
    }

    /**
     * @return Maximum password age in nanoseconds.
     */
    public long getMaximumPasswordAge() {
        return maximumPasswordAge;
    }

    /**
     * @return Minimum password age in nanoseconds.
     */
    public long getMinimumPasswordAge() {
        return minimumPasswordAge;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMinimumPasswordLength(), getPasswordHistoryLength(), getPasswordProperties(),
            getMaximumPasswordAge(), getMinimumPasswordAge());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof DomainPasswordInfo)) {
            return false;
        }
        final DomainPasswordInfo other = (DomainPasswordInfo) obj;
        return Objects.equals(getMinimumPasswordLength(), other.getMinimumPasswordLength())
            && Objects.equals(getPasswordHistoryLength(), other.getPasswordHistoryLength())
            && Objects.equals(getPasswordProperties(), other.getPasswordProperties())
            && Objects.equals(getMaximumPasswordAge(), other.getMaximumPasswordAge())
            && Objects.equals(getMinimumPasswordAge(), other.getMinimumPasswordAge());
    }

    @Override
    public String toString() {
        return String.format(
            "DomainPasswordInformation{minimumPasswordLength: %d, passwordHistoryLength: %d, passwordProperties: %d, " +
                    "maximumPasswordAge: %d, minimumPasswordAge: %d, isDomainPasswordComplex: %b, " +
                    "isDomainPasswordNoAnonChange: %b, isDomainPasswordStoredClearText: %b}",
            getMinimumPasswordLength(), getPasswordHistoryLength(), getPasswordProperties(), getMaximumPasswordAge(),
            getMinimumPasswordAge(), isDomainPasswordComplex(), isDomainPasswordNoAnonChange(),
            isDomainPasswordStoredClearText());
    }
}
