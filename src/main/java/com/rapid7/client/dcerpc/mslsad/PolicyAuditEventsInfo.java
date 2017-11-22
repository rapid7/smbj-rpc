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

import java.util.Arrays;
import java.util.Objects;

public class PolicyAuditEventsInfo {
    private final boolean auditEnabled;
    private final int[] eventAuditingOptions;

    public PolicyAuditEventsInfo(final boolean auditEnabled, final int[] eventAuditingOptions) {
        if (eventAuditingOptions == null) {
            throw new IllegalArgumentException("Expecting non-null eventAuditingOptions");
        } else if (eventAuditingOptions.length != 9) {
            throw new IllegalArgumentException(String.format(
                    "Expecting 9 elements in eventAuditingOptions, got: %d", eventAuditingOptions.length));
        }
        this.auditEnabled = auditEnabled;
        this.eventAuditingOptions = eventAuditingOptions;
    }

    /**
     * @return True iff auditing is enabled.
     */
    public boolean isAuditEnabled() {
        return auditEnabled;
    }

    /**
     * Return the auditing options for a particular {@link PolicyAuditEventType}.
     * The auditing options are interpreted by using the masks provided by {@link EventAuditOptions}.
     * <pre>
     *      int options = policyAuditEventsInfo.getEventAuditingOptions(PolicyAuditEventType.AUDIT_CATEGORY_DETAILED_TRACKING);
     *      boolean enabledForSuccess = EventAuditOptions.POLICY_AUDIT_EVENT_SUCCESS.isSet(options);
     *      boolean enabledForFailure = EventAuditOptions.POLICY_AUDIT_EVENT_FAILURE.isSet(options);
     * </pre>
     * The auditing type of an element is represented by its index in the array, which is identified by the POLICY_AUDIT_EVENT_TYPE enumeration (see section 2.2.4.20). Each element MUST contain one or more of the values in the table below.
     * @param eventType What auditing options to return. All types are supported.
     * @return The auditing options for a particular {@link PolicyAuditEventType}.
     */
    public int getEventAuditingOptions(PolicyAuditEventType eventType) {
        return this.eventAuditingOptions[eventType.getValue()-1];
    }

    @Override
    public int hashCode() {
        int ret = this.auditEnabled ? 1 : 0;
        ret = (31 * ret) + Arrays.hashCode(this.eventAuditingOptions);
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof PolicyAuditEventsInfo)) {
            return false;
        }
        final PolicyAuditEventsInfo other = (PolicyAuditEventsInfo) obj;
        return Objects.equals(this.auditEnabled, other.auditEnabled)
                && Arrays.equals(this.eventAuditingOptions, other.eventAuditingOptions);
    }

    @Override
    public String toString() {
        return String.format("PolicyAuditEventsInfo{auditMode:%b, eventAuditingOptions:%s}",
                this.auditEnabled, Arrays.toString(this.eventAuditingOptions));
    }
}
