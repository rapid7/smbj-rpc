/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mslsad.objects;

/**
 * The InformationClass parameter can take on any value in the
 * POLICY_INFORMATION_CLASS enumeration range.
 *
 * Currently supports POLICY_AUDIT_EVENTS_INFORMATION.
 *
 * TODO: Implements more information class as needed.
 * 
 * <table summary="table">
 * <tbody>
 * <tr>
 * <th scope="col">
 * <p>
 * &nbsp;Value of InformationClass parameter
 * </p>
 * </th>
 * <th scope="col">
 * <p>
 * &nbsp;Information returned to caller from abstract data model
 * </p>
 * </th>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyAuditLogInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * Auditing Log Information
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyAuditEventsInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * Event Auditing Options
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyPrimaryDomainInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * Primary Domain Information
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyPdAccountInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * MUST return an <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234267.aspx">LSAPR_POLICY_PD_ACCOUNT_INFO</a>
 * information structure, its <strong>Name</strong> member being an
 * RPC_UNICODE_STRING with <strong>Length</strong> set to 0 and
 * <strong>Buffer</strong> initialized to NULL.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyAccountDomainInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * On non–<a href=
 * "https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_76a05049-3531-4abd-aec8-30e19954b4bd">domain
 * controllers</a>: Account Domain
 * </p>
 * <p>
 * On domain controller: Primary Domain Information
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyLsaServerRoleInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * Server Role Information
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyReplicaSourceInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * Replica Source Information
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyModificationInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * MUST return STATUS_INVALID_PARAMETER
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyAuditFullSetInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * MUST return STATUS_INVALID_PARAMETER
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyAuditFullQueryInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * Audit Full Information<a id="Appendix_A_Target_56"></a><a href=
 * "https://msdn.microsoft.com/en-us/library/cc234419.aspx#Appendix_A_56">&lt;56&gt;</a>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyDnsDomainInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * DNS Domain Information<a id="Appendix_A_Target_57"></a><a href=
 * "https://msdn.microsoft.com/en-us/library/cc234419.aspx#Appendix_A_57">&lt;57&gt;</a>
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyDnsDomainInformationInt
 * </p>
 * </td>
 * <td>
 * <p>
 * DNS Domain Information
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * PolicyLocalAccountDomainInformation
 * </p>
 * </td>
 * <td>
 * <p>
 * AccountDomainInformation
 * </p>
 * </td>
 * </tr>
 * </tbody>
 * </table>
 */
public enum PolicyInformationClass {
    POLICY_AUDIT_EVENTS_INFORMATION(2);

    private PolicyInformationClass(final int infoLevel) {
        this.infoLevel = infoLevel;
    }

    public int getInfoLevel() {
        return infoLevel;
    }

    private final int infoLevel;
}
