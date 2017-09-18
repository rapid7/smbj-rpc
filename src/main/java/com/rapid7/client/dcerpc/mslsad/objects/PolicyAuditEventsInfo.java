/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mslsad.objects;

/**
 * The POLICY_AUDIT_EVENTS_INFO structure is used to set and query the system's
 * auditing rules. The LsaQueryInformationPolicy and LsaSetInformationPolicy
 * functions use this structure when their InformationClass parameters are set
 * to PolicyAuditEventsInformation.
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
 *      "https://msdn.microsoft.com/en-us/library/windows/desktop/ms721901(v=vs.85).aspx">POLICY_AUDIT_EVENTS_INFO
 *      structure</a>
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
