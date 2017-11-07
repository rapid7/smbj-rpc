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

/**
 * The POLICY_AUDIT_EVENTS_INFO structure is used to set and query the system's auditing rules. The
 * LsaQueryInformationPolicy and LsaSetInformationPolicy functions use this structure when their InformationClass
 * parameters are set to PolicyAuditEventsInformation.
 *
 * <pre>
 * typedef struct _POLICY_AUDIT_EVENTS_INFO {
 *   BOOLEAN                     AuditingMode;
 *   PPOLICY_AUDIT_EVENT_OPTIONS EventAuditingOptions;
 *   ULONG                       MaximumAuditEventCount;
 * } POLICY_AUDIT_EVENTS_INFO, *PPOLICY_AUDIT_EVENTS_INFO;
 * </pre>
 *
 * @see <a href =
 * "https://msdn.microsoft.com/en-us/library/windows/desktop/ms721901(v=vs.85).aspx">POLICY_AUDIT_EVENTS_INFO
 * structure</a>
 */
public class PolicyAuditEventsInfo {
    public PolicyAuditEventsInfo(final boolean auditingMode, final int[] eventAuditingOption, final int count) {
        this.auditingMode = auditingMode;
        this.eventAuditingOption = eventAuditingOption;
        this.count = count;
    }

    public boolean isAuditEnabled() {
        return auditingMode;
    }

    public int[] getEventAuditOption() {
        return eventAuditingOption;
    }

    public int getCount() {
        return count;
    }

    private final boolean auditingMode;
    private final int[] eventAuditingOption;
    private final int count;
}
