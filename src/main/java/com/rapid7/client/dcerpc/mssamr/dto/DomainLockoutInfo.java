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

public class DomainLockoutInfo {
    private final long lockoutDuration;
    private final long lockoutObservationWindow;
    private final int lockoutThreshold;

    public DomainLockoutInfo(final long lockoutDuration, final long lockoutObservationWindow,
            final int lockoutThreshold) {
        this.lockoutDuration = lockoutDuration;
        this.lockoutObservationWindow = lockoutObservationWindow;
        this.lockoutThreshold = lockoutThreshold;
    }

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
    public int hashCode() {
        return Objects.hash(getLockoutDuration(), getLockoutObservationWindow(), getLockoutThreshold());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof DomainLockoutInfo)) {
            return false;
        }
        final DomainLockoutInfo other = (DomainLockoutInfo) obj;
        return getLockoutDuration() == other.getLockoutDuration()
                && getLockoutObservationWindow() == other.getLockoutObservationWindow()
                && getLockoutThreshold() == other.getLockoutThreshold();
    }

    @Override
    public String toString() {
        return String.format("DomainLockoutInfo{lockoutDuration: %d, lockoutObservationWindow: %d, " +
                "lockoutThreshold: %d}", getLockoutDuration(), getLockoutObservationWindow(), getLockoutThreshold());
    }
}
