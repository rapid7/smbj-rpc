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

package com.rapid7.client.dcerpc.mslsad;

public enum EventAuditOptions {
    POLICY_AUDIT_EVENT_UNCHANGED((byte) 0),
    POLICY_AUDIT_EVENT_NONE((byte) 4),
    POLICY_AUDIT_EVENT_SUCCESS((byte) 1),
    POLICY_AUDIT_EVENT_FAILURE((byte) 2);

    private final byte value;
    EventAuditOptions(final byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public boolean is(final byte value) {
        return getValue() == value;
    }

    public boolean isSet(final int options) {
        return (getValue() & options) == options;
    }
}
