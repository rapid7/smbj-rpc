/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

public class SamrQueryInformationDomainResponse extends RequestResponse {
    /** The server enforces password complexity policy. */
    private static final int DOMAIN_PASSWORD_COMPLEX = 0x00000001;

    /**
     * The password cannot be changed without logging on. Otherwise, if your
     * password has expired, you can change your password and then log on.
     */
    private static final int DOMAIN_PASSWORD_NO_ANON_CHANGE = 0x00000002;

    /**
     * The server MUST store the clear text password and not just the computed
     * hashes.
     */
    private static final int DOMAIN_PASSWORD_STORE_CLEARTEXT = 0x00000010;

    /** An enumeration indicating which attributes to return. */
    private short domainInformationClass;

    /**
     * A 16-bit unsigned integer indicating the minimum password length policy
     * setting.
     */
    private short minimumPasswordLength;

    /**
     * A 16-bit unsigned integer indicating the policy setting for the password
     * history length.
     */
    private short passwordHistoryLength;

    /**
     * A 32-bit bit field indicating the password properties policy setting. The
     * defined bits are shown in the following table. All bits can be combined
     * using a logical OR in any combination. Undefined bits SHOULD be persisted
     * by the server (that is, stored in its database) and returned to future
     * queries. Clients SHOULD ignore undefined bits.
     *
     * +---------------------------------+--------------------------------------------------------------------------+
     * | Name/value | Description |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_COMPLEX | The server enforces password complexity
     * policy. | | 0x00000001 | |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_NO_ANON_CHANGE | Reserved. No effect on password
     * policy. | | 0x00000002 | |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_NO_CLEAR_CHANGE | Change-password methods that provide
     * the cleartext password are disabled | | 0x00000004 | by the server. |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_LOCKOUT_ADMINS | Reserved. No effect on password policy. | |
     * 0x00000008 | |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_PASSWORD_STORE_CLEARTEXT | The server MUST store the cleartext
     * password, not just the computed | | 0x00000010 | hashes. |
     * +---------------------------------+--------------------------------------------------------------------------+
     * | DOMAIN_REFUSE_PASSWORD_CHANGE | Reserved. No effect on password policy.
     * | | 0x00000020 | |
     * +---------------------------------+--------------------------------------------------------------------------+
     */
    private int passwordProperties;

    /**
     * A 32-bit value, with delta-time syntax, indicating the policy setting for
     * the maximum time in second(s) allowed before a password reset or change
     * is required.
     */
    private int maximumPasswordAge;

    /**
     * A 32-bit value, with delta-time syntax, indicating the policy setting for
     * the minimum time in second(s) allowed before a password change operation
     * is allowed.
     */
    private int minimumPasswordAge;

    /**
     * A 32-bit value, with delta-time syntax, indicating the policy setting for
     * the amount of time in second(s) that an interactive log on session is
     * allowed to continue.
     */
    private int forceLogoff;

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        int referenceId = packetIn.readReferentID();
        short domainInformationClass = packetIn.readShort();
        packetIn.fullySkipBytes(2);
        switch (domainInformationClass) {
            case SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION:
                /*
                 * typedef struct _DOMAIN_PASSWORD_INFORMATION { unsigned short
                 * MinPasswordLength; unsigned short PasswordHistoryLength;
                 * unsigned long PasswordProperties; OLD_LARGE_INTEGER
                 * MaxPasswordAge; OLD_LARGE_INTEGER MinPasswordAge; }
                 * DOMAIN_PASSWORD_INFORMATION, PDOMAIN_PASSWORD_INFORMATION;
                 */
                this.domainInformationClass = SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION;
                minimumPasswordLength = packetIn.readShort();
                passwordHistoryLength = packetIn.readShort();
                passwordProperties = packetIn.readInt();
                maximumPasswordAge = packetIn.readInterval();
                minimumPasswordAge = packetIn.readInterval();

                System.out.println("maximumPasswordAge:" + maximumPasswordAge);
                System.out.println("minimumPasswordAge:" + minimumPasswordAge);

                break;

            case SamrQueryInformationDomainRequest.DOMAIN_LOGOFF_INFORMATION:
                this.domainInformationClass = SamrQueryInformationDomainRequest.DOMAIN_LOGOFF_INFORMATION;
                /*
                 * typedef struct _DOMAIN_LOGOFF_INFORMATION { OLD_LARGE_INTEGER
                 * ForceLogoff; } DOMAIN_LOGOFF_INFORMATION,
                 * PDOMAIN_LOGOFF_INFORMATION;
                 */
                forceLogoff = packetIn.readInt();
                break;

            default:
                throw new IllegalArgumentException(
                    "Domain Information Class \"" + domainInformationClass + "\" not implemented.");
        }

    }

    /**
     * This method will provide the minimum password length policy setting.
     *
     * @return A 16-bit unsigned integer indicating the minimum password length
     *         policy setting.
     */
    public short getMinimumPasswordLength() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return minimumPasswordLength;
    }

    /**
     * This method will provide the policy setting for the password history
     * length.
     *
     * @return A 16-bit unsigned integer indicating the policy setting for the
     *         password history length.
     */
    public short getPasswordHistorySize() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return passwordHistoryLength;
    }

    /**
     * This method will provide if the server enforces the password complexity
     * policy.
     *
     * @return {@code true} if the server enforces the password complexity
     *         policy.
     */
    public boolean isDomainPasswordComplex() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return (passwordProperties & DOMAIN_PASSWORD_COMPLEX) != 0;
    }

    /**
     * This method will provide if the password cannot be changed without
     * logging on. Otherwise, if the password has expired, the password must be
     * changed before the account can log on.
     *
     * @return {@code true} if the password cannot be changed without logging
     *         on. Otherwise, if your password has expired, you can change your
     *         password and then log on.
     */
    public boolean isDomainPasswordNoAnonChange() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return (passwordProperties & DOMAIN_PASSWORD_NO_ANON_CHANGE) != 0;
    }

    /**
     * This method will provide if the server MUST store clear text password(s)
     * and not just the computed hashes.
     *
     * @return {@code true} if the server MUST store clear text password(s) and
     *         not just the computed hashes.
     */
    public boolean isDomainPasswordStoredClearText() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return (passwordProperties & DOMAIN_PASSWORD_STORE_CLEARTEXT) != 0;
    }

    /**
     * This method will provide the policy setting, with delta-time syntax, for
     * the maximum time in second(s) allowed before a password reset or change
     * is required.
     *
     * @return A 32-bit value, with delta-time syntax, indicating the policy
     *         setting for the maximum time in second(s) allowed before a
     *         password reset or change is required.
     */
    public int getMaximumPasswordAge() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return maximumPasswordAge;
    }

    /**
     * This method will provide the policy setting, with delta-time syntax, for
     * the minimum time in second(s) allowed before a password change operation
     * is allowed.
     *
     * @return A 32-bit value, with delta-time syntax, indicating the policy
     *         setting for the minimum time in second(s) allowed before a
     *         password change operation is allowed.
     */
    public int getMinimumPasswordAge() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_PASSWORD_INFORMATION) {
            throw new IllegalStateException();
        }

        return minimumPasswordAge;
    }

    /**
     * This method will provide the policy setting, with delta-time syntax, for
     * the amount of time in second(s) that an interactive log on session is
     * allowed to continue. The value of -1 is the equivalent to infinite.
     *
     * @return A 32-bit value, with delta-time syntax, indicating the policy
     *         setting for the amount of time in second(s) that an interactive
     *         log on session is allowed to continue. The value of -1 is the
     *         equivalent to infinite.
     */
    public int getForceLogoff() {
        if (domainInformationClass != SamrQueryInformationDomainRequest.DOMAIN_LOGOFF_INFORMATION) {
            throw new IllegalStateException();
        }

        return forceLogoff;
    }

}
