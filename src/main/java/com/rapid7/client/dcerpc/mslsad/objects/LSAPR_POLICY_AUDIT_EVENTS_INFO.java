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
package com.rapid7.client.dcerpc.mslsad.objects;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * The LSAPR_POLICY_AUDIT_EVENTS_INFO structure is used to set and query the system's auditing rules. The
 * LsaQueryInformationPolicy and LsaSetInformationPolicy functions use this structure when their InformationClass
 * parameters are set to PolicyAuditEventsInformation.
 *
 * <pre>
 *      typedef struct _LSAPR_POLICY_AUDIT_EVENTS_INFO {
 *          unsigned char AuditingMode;
 *          [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;
 *          [range(0,1000)] unsigned long MaximumAuditEventCount;
 *      } LSAPR_POLICY_AUDIT_EVENTS_INFO,
 *       *PLSAPR_POLICY_AUDIT_EVENTS_INFO;
 * </pre>
 *
 * @see <a href =
 * "https://msdn.microsoft.com/en-us/library/windows/desktop/ms721901(v=vs.85).aspx">POLICY_AUDIT_EVENTS_INFO
 * structure</a>
 */
public class LSAPR_POLICY_AUDIT_EVENTS_INFO implements Unmarshallable {
    // <NDR: unsigned char> unsigned char AuditingMode;
    private boolean auditingMode;
    // <NDR: *conformant array> [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;
    private int[] eventAuditingOptions;
    // <NDR: unsigned long> [range(0,1000)] unsigned long MaximumAuditEventCount;
    private int maximumAuditEventCount;

    public boolean isAuditingMode() {
        return auditingMode;
    }

    public void setAuditingMode(boolean auditingMode) {
        this.auditingMode = auditingMode;
    }

    public int[] getEventAuditingOptions() {
        return eventAuditingOptions;
    }

    public void setEventAuditingOptions(int[] eventAuditingOptions) {
        this.eventAuditingOptions = eventAuditingOptions;
    }

    public int getMaximumAuditEventCount() {
        return maximumAuditEventCount;
    }

    public void setMaximumAuditEventCount(int maximumAuditEventCount) {
        this.maximumAuditEventCount = maximumAuditEventCount;
    }

    @Override
    public Alignment getAlignment() {
        // unsigned char AuditingMode: 1
        // [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions: 4
        // [range(0,1000)] unsigned long MaximumAuditEventCount: 4
        return Alignment.FOUR;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        this.auditingMode = in.readBoolean();
        // Alignment to pointer (4 bytes)
        in.fullySkipBytes(3);
        boolean eventAuditingOptionsNull = in.readReferentID() == 0;
        this.maximumAuditEventCount = in.readInt();
        if (eventAuditingOptionsNull) {
            if (this.maximumAuditEventCount != 0) {
                throw new IllegalArgumentException("If the MaximumAuditingEventCount field has a value other than 0, EventAuditingOptions MUST NOT be NULL.");
            }
            this.eventAuditingOptions = null;
        } else {
            this.eventAuditingOptions = new int[maximumAuditEventCount];
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // MaximumCount
        if (this.eventAuditingOptions != null) {
            final int maximumCount = in.readInt();
            if (maximumCount < this.maximumAuditEventCount) {
                throw new IllegalArgumentException(String.format(
                        "Expected MaximumAuditingEventCount (%d) >= EventAuditingOptions.MaximumCount (%d)",
                        maximumCount, this.maximumAuditEventCount));
            }
            for (int i = 0; i < this.eventAuditingOptions.length; i++) {
                this.eventAuditingOptions[i] = in.readInt();
            }
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(isAuditingMode());
        result = (31 * result) + Arrays.hashCode(getEventAuditingOptions());
        return (31 * result) + Objects.hashCode(getMaximumAuditEventCount());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof LSAPR_POLICY_AUDIT_EVENTS_INFO)) {
            return false;
        }
        LSAPR_POLICY_AUDIT_EVENTS_INFO other = (LSAPR_POLICY_AUDIT_EVENTS_INFO) obj;
        return Objects.equals(isAuditingMode(), other.isAuditingMode()) &&
                Arrays.equals(getEventAuditingOptions(), other.getEventAuditingOptions()) &&
                Objects.equals(getMaximumAuditEventCount(), other.getMaximumAuditEventCount());
    }

    @Override
    public String toString() {
        return String.format(
                "LSAPR_POLICY_AUDIT_EVENTS_INFO{AuditingMode:%b, EventAuditingOptions:%s, MaximumAuditEventCount:%d}",
                isAuditingMode(), Arrays.toString(getEventAuditingOptions()), getMaximumAuditEventCount());
    }
}
