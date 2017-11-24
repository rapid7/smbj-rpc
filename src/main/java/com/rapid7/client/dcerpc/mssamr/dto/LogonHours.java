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

import java.util.Arrays;

/**
 * The logon policy information that describes when a user account is permitted to authenticate.
 */
public class LogonHours {
    public enum UnitsPerWeek {
        /**
         * Each bit from {@link #getLogonHours()} represents a day of the week.
         */
        DAYS,
        /**
         * Each bit from {@link #getLogonHours()} represents an hour of the week.
         */
        HOURS,
        /**
         * Each bit from {@link #getLogonHours()} represents a minute of the week.
         */
        MINUTES;

        public static UnitsPerWeek fromLogonHoursLength(final int length) {
            switch (length) {
                case 1:
                    return UnitsPerWeek.DAYS;
                case 21:
                    return UnitsPerWeek.HOURS;
                case 1260:
                    return UnitsPerWeek.MINUTES;
                default:
                    throw new IllegalArgumentException(String.format("Invalid logonHours length: %d", length));
            }
        }
    }

    private UnitsPerWeek unitsPerWeek;
    private byte[] logonHours;

    public LogonHours(final byte[] logonHours) {
        if (logonHours == null) {
            throw new IllegalArgumentException("Expecting non-null logonHours");
        }
        this.unitsPerWeek = UnitsPerWeek.fromLogonHoursLength(logonHours.length);
        this.logonHours = logonHours;
    }

    /**
     * @return The representation of the logon hours returned by {@link #getLogonHours()}.
     */
    public UnitsPerWeek getUnitsPerWeek() {
        return unitsPerWeek;
    }

    /**
     * @return An array of bit fields where each bit represents an incremental point in time represented
     * in the {@link UnitsPerWeek} returned by {@link #getUnitsPerWeek()}.
     * If the bit is 1, then the user is allowed to login.
     */
    public byte[] getLogonHours() {
        return logonHours;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.logonHours);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LogonHours)) {
            return false;
        }
        return Arrays.equals(this.logonHours, ((LogonHours) obj).logonHours);
    }

    @Override
    public String toString() {
        return String.format("LogonHours{unitsPerWeek: %s, size(logonHours): %d",
                this.unitsPerWeek, this.logonHours.length);
    }
}
