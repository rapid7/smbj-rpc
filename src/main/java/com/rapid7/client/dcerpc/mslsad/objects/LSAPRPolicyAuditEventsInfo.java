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
 * <b>Alignment: 4</b> (Max[1,4,4])<pre>
 *      unsigned char AuditingMode;: 1
 *      [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;: 4
 *      [range(0,1000)] unsigned long MaximumAuditEventCount;: 4</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc234264.aspx?f=255&MSPPError=-2147217396">LSAPR_POLICY_AUDIT_EVENTS_INFO</a>
 * <blockquote><pre>
 * The LSAPR_POLICY_AUDIT_EVENTS_INFO structure contains auditing options on the server.
 *      typedef struct _LSAPR_POLICY_AUDIT_EVENTS_INFO {
 *          unsigned char AuditingMode;
 *          [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;
 *          [range(0,1000)] unsigned long MaximumAuditEventCount;
 *      } LSAPR_POLICY_AUDIT_EVENTS_INFO,
 *      *PLSAPR_POLICY_AUDIT_EVENTS_INFO;
 *
 *  AuditingMode: 0 indicates that auditing is disabled. All other values indicate that auditing is enabled.
 *  EventAuditingOptions: An array of values specifying the auditing options for a particular audit type. The auditing type of an element is represented by its index in the array, which is identified by the POLICY_AUDIT_EVENT_TYPE enumeration (see section 2.2.4.20). Each element MUST contain one or more of the values in the table below.
 *      If the MaximumAuditEventCount field has a value other than 0, this field MUST NOT be NULL.
 *  MaximumAuditEventCount: The number of entries in the EventAuditingOptions array.</pre></blockquote>
 */
public class LSAPRPolicyAuditEventsInfo implements Unmarshallable {
    // <NDR: unsigned char> unsigned char AuditingMode;
    private char auditingMode;
    // <NDR: pointer> [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;
    // Even though these are unsigned long's, we store as int because it's just a bitmask
    private int[] eventAuditingOptions;
    // <NDR: unsigned long> [range(0,1000)] unsigned long MaximumAuditEventCount;
    private int maximumAuditEventCount;

    public char getAuditingMode() {
        return auditingMode;
    }

    public void setAuditingMode(char auditingMode) {
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
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned char> unsigned char AuditingMode;: 1
        // Alignment: 1 - Already aligned
        this.auditingMode = in.readUnsignedByte();
        // <NDR: pointer> [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;
        // Alignment: 4 - Pad 3 bytes
        in.fullySkipBytes(3);
        boolean eventAuditingOptionsNull = in.readReferentID() == 0;
        // <NDR: unsigned long> [range(0,1000)] unsigned long MaximumAuditEventCount;
        // Alignment: 4 - Already aligned
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
        if (this.eventAuditingOptions != null) {
            // <NDR: conformant array> [size_is(MaximumAuditEventCount)] unsigned long* EventAuditingOptions;
            // Alignment: 4 (Max[4,4])
            in.align(Alignment.FOUR);
            // <NDR: unsigned long> MaximumCount
            // Alignment: 4 - Already aligned
            final int maximumCount = in.readInt();
            if (maximumCount < this.maximumAuditEventCount) {
                throw new IllegalArgumentException(String.format(
                        "Expected MaximumAuditingEventCount (%d) >= EventAuditingOptions.MaximumCount (%d)",
                        maximumCount, this.maximumAuditEventCount));
            }
            for (int i = 0; i < this.eventAuditingOptions.length; i++) {
                // <NDR: unsigned long> MaximumCount
                // Alignment: 4 - Already aligned
                this.eventAuditingOptions[i] = in.readInt();
            }
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getAuditingMode());
        result = (31 * result) + Arrays.hashCode(getEventAuditingOptions());
        return (31 * result) + Objects.hashCode(getMaximumAuditEventCount());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof LSAPRPolicyAuditEventsInfo)) {
            return false;
        }
        LSAPRPolicyAuditEventsInfo other = (LSAPRPolicyAuditEventsInfo) obj;
        return Objects.equals(getAuditingMode(), other.getAuditingMode()) &&
                Arrays.equals(getEventAuditingOptions(), other.getEventAuditingOptions()) &&
                Objects.equals(getMaximumAuditEventCount(), other.getMaximumAuditEventCount());
    }

    @Override
    public String toString() {
        return String.format(
                "LSAPR_POLICY_AUDIT_EVENTS_INFO{AuditingMode:%d, EventAuditingOptions:%s, MaximumAuditEventCount:%d}",
                (int) getAuditingMode(), Arrays.toString(getEventAuditingOptions()), getMaximumAuditEventCount());
    }
}
