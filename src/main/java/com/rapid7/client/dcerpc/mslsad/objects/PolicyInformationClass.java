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
 * <b>Alignment: 2</b>
 * <br>
 * <a href="https://msdn.microsoft.com/en-us/library/cc234262.aspx">POLICY_INFORMATION_CLASS</a>:
 * <blockquote><pre>
 * The POLICY_INFORMATION_CLASS enumeration type contains values that specify the type of policy being queried or set by the client.
 *      typedef  enum _POLICY_INFORMATION_CLASS
 *      {
 *          PolicyAuditLogInformation = 1,
 *          PolicyAuditEventsInformation,
 *          PolicyPrimaryDomainInformation,
 *          PolicyPdAccountInformation,
 *          PolicyAccountDomainInformation,
 *          PolicyLsaServerRoleInformation,
 *          PolicyReplicaSourceInformation,
 *          PolicyInformationNotUsedOnWire,
 *          PolicyModificationInformation,
 *          PolicyAuditFullSetInformation,
 *          PolicyAuditFullQueryInformation,
 *          PolicyDnsDomainInformation,
 *          PolicyDnsDomainInformationInt,
 *          PolicyLocalAccountDomainInformation,
 *          PolicyLastEntry
 *      } POLICY_INFORMATION_CLASS,
 *      *PPOLICY_INFORMATION_CLASS;
 *  PolicyAuditLogInformation: Information about audit log.
 *  PolicyAuditEventsInformation: Auditing options.
 *  PolicyPrimaryDomainInformation: Primary domain information.
 *  PolicyPdAccountInformation: Obsolete information class.
 *  PolicyAccountDomainInformation: Account domain information.
 *  PolicyLsaServerRoleInformation: Server role information.
 *  PolicyReplicaSourceInformation: Replica source information.
 *  PolicyInformationNotUsedOnWire: This enumeration value does not appear on the wire.
 *  PolicyModificationInformation: Obsolete information class.
 *  PolicyAuditFullSetInformation: Obsolete information class.
 *  PolicyAuditFullQueryInformation: Audit log state.
 *  PolicyDnsDomainInformation: DNS domain information.
 *  PolicyDnsDomainInformationInt: DNS domain information.
 *  PolicyLocalAccountDomainInformation: Local account domain information.
 *  PolicyLastEntry: Not used in this protocol. Present to mark the end of the enumeration.
 *  The following citation contains a timeline of when each enumeration value was introduced.
 *  The values in this enumeration are used to define the contents of the LSAPR_POLICY_INFORMATION (section 2.2.4.2) union, where the structure associated with each enumeration value is specified. The structure associated with each enumeration value defines the meaning of that value to this protocol.
 *  </pre></blockquote>
 */
public enum PolicyInformationClass {
    POLICY_AUDIT_EVENTS_INFORMATION(2),
    POLICY_PRIMARY_DOMAIN_INFORMATION(3),
    POLICY_ACCOUNT_DOMAIN_INFORMATION(5);

    PolicyInformationClass(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }

    private final int infoLevel;
}
