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
 * <a href="https://msdn.microsoft.com/en-us/library/gg258137.aspx">POLICY_AUDIT_EVENT_TYPE</a>
 * Represents the category of {@link EventAuditOptions}.
 */
public enum PolicyAuditEventType {
    AUDIT_CATEGORY_SYSTEM((byte) 1),
    AUDIT_CATEGORY_LOGON((byte) 2),
    AUDIT_CATEGORY_OBJECT_ACCESS((byte) 3),
    AUDIT_CATEGORY_PRIVILEGE_USE((byte) 4),
    AUDIT_CATEGORY_DETAILED_TRACKING((byte) 5),
    AUDIT_CATEGORY_POLICY_CHANGE((byte) 6),
    AUDIT_CATEGORY_ACCOUNT_MANAGEMENT((byte) 7),
    AUDIT_CATEGORY_DIRECTORY_SERVICE_ACCESS((byte) 8),
    AUDIT_CATEGORY_ACCOUNT_LOGON((byte) 9);

    private final byte value;

    PolicyAuditEventType(final byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
