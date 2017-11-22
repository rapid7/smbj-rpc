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

/**
 * Represents a mask which is applied to any {@link PolicyAuditEventType}
 */
public enum EventAuditOptions {
    /**
     * Leave existing auditing options unchanged for events of this type;
     * used only for set operations. This value cannot be combined with values in this table.
     */
    POLICY_AUDIT_EVENT_UNCHANGED((byte) 0),
    /**
     * Upon updates, this value causes the existing auditing options for events of this
     * type to be deleted and replaced with any other new values specified.
     * If specified by itself, this value cancels all auditing options for events of this type.
     * This value is used only for set operations.
     */
    POLICY_AUDIT_EVENT_NONE((byte) 4),
    /**
     * When auditing is enabled, audit all successful occurrences of events of the given type.
     */
    POLICY_AUDIT_EVENT_SUCCESS((byte) 1),
    /**
     * When auditing is enabled, audit all unsuccessful occurrences of events of the given type.
     */
    POLICY_AUDIT_EVENT_FAILURE((byte) 2);

    private final byte value;

    EventAuditOptions(final byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    /**
     * @param value Value to check.
     * @return True iff the given value matches the mask for this {@link EventAuditOptions}.
     */
    public boolean is(final byte value) {
        return getValue() == value;
    }

    /**
     * @param options The event audit options for any {@link PolicyAuditEventType}.
     * @return True iff this {@link EventAuditOptions} is set in the provided options.
     */
    public boolean isSet(final int options) {
        return (options & getValue()) != 0;
    }
}
