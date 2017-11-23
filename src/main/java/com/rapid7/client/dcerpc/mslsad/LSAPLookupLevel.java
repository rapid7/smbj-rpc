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
package com.rapid7.client.dcerpc.mslsad;

/**
 * The LSAP_LOOKUP_LEVEL enumeration defines different scopes for searches
 * during translation.
 *
 * <pre>
 * typedef enum _LSAP_LOOKUP_LEVEL
 * {
 *   LsapLookupWksta = 1,
 *   LsapLookupPDC,
 *   LsapLookupTDL,
 *   LsapLookupGC,
 *   LsapLookupXForestReferral,
 *   LsapLookupXForestResolve,
 *   LsapLookupRODCReferralToFullDC
 * } LSAP_LOOKUP_LEVEL,
 *  *PLSAP_LOOKUP_LEVEL;
 * </pre>
 */
public enum LSAPLookupLevel {
    /**
     * <p>
     * <strong>LsapLookupWksta:</strong> <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SIDs</a>
     * MUST be searched in
     * the views under the Security Principal SID and Security Principal SID History
     * columns in the following order:
     * </p>
     *
     * <p>
     * Predefined
     * Translation View, as specified in section <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234477.aspx">3.1.1.1.1</a>.
     * </p>
     *
     * <p>
     * Configurable
     * Translation View, as specified in section <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234505.aspx">3.1.1.1.2</a>.
     * </p>
     *
     * <p>
     * Builtin
     * Domain Principal View of the account database on the <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_ae65dac0-cd24-4e83-a946-6d1097b71553">RPC
     * server</a>, as specified in
     * section <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234478.aspx">3.1.1.1.3</a>.
     * </p>
     *
     * <p>
     * Account
     * Domain View of account database on that machine, as specified in section
     * <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234504.aspx">3.1.1.1.6</a>.
     * </p>
     *
     * <p>
     * If the machine is not joined to a <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domain</a>,
     * the search ends
     * here.
     * </p>
     *
     * <p>
     * If
     * the machine is not a <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_76a05049-3531-4abd-aec8-30e19954b4bd">domain
     * controller</a>: the Account Domain View of the domain to which this machine
     * is
     * joined.
     * </p>
     *
     * <p>
     * Forest
     * View (section <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234507.aspx">3.1.1.1.9</a>)
     * of the <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_fd104241-4fb3-457c-b2c4-e0c18bb20b62">forest</a>
     * of the
     * domain to which this machine is joined, unless <em>ClientRevision</em> is
     * 0x00000001 and the machine is joined to a mixed mode domain, as specified in
     * <a href=
     * "https://msdn.microsoft.com/en-us/library/cc223122.aspx">[MS-ADTS]</a>
     * section
     * <a href="https://msdn.microsoft.com/en-us/library/cc223740.aspx">6.1.4.1</a>.
     * </p>
     *
     * <p>
     * Forest
     * Views of <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_3b76a71f-9697-4836-9c69-09899b23c21b">trusted
     * forests</a>
     * for the forest of the domain to which this machine is joined, unless
     * ClientRevision is 0x00000001 and the machine is joined to a mixed mode
     * domain,
     * as specified in [MS-ADTS] section 6.1.4.1.
     * </p>
     *
     * <p>
     * Account
     * Domain Views of externally <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_f2f00d47-6cf2-4b32-b8f7-b63e38e2e9c4">trusted
     * domains</a> for the domain to which this machine is joined.
     * </p>
     */
    LsapLookupWksta,
    /**
     * <p>
     * <strong>LsapLookupPDC:</strong> SIDs MUST be searched in the
     * views under the Security Principal SID and Security Principal SID History
     * columns in the following order:
     * </p>
     *
     * <p>
     * Account
     * Domain View of the domain to which this machine is joined.
     * </p>
     *
     * <p>
     * Forest
     * View of the forest of the domain to which this machine is joined, unless
     * <em>ClientRevision</em>
     * is 0x00000001 and the machine is joined to a mixed mode domain, as specified
     * in
     * [MS-ADTS] section 6.1.4.1.
     * </p>
     *
     * <p>
     * Forest
     * Views of trusted forests for the forest of the domain to which this machine
     * is
     * joined, unless <em>ClientRevision</em> is 0x00000001 and the machine is
     * joined to
     * a mixed mode domain, as specified in [MS-ADTS] section 6.1.4.1.
     * </p>
     *
     * <p>
     * Account
     * Domain Views of externally trusted domains for the domain to which this
     * machine
     * is joined.
     * </p>
     */
    LsapLookupPDC,
    /**
     * <p>
     * <strong>LsapLookupTDL:</strong> SIDs MUST be searched in the
     * databases under the Security Principal SID column in the following view:
     * </p>
     *
     * <p>
     * Account
     * Domain View of the <a href=
     * "https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_40a58fa4-953e-4cf3-96c8-57dba60237ef">domain
     * NC</a>
     * for the domain to which this machine is joined.
     * </p>
     */
    LsapLookupTDL,
    /**
     * <p>
     * <strong>LsapLookupGC:</strong> SIDs MUST be searched in the
     * databases under the Security Principal SID and Security Principal SID History
     * columns in the following view:
     * </p>
     *
     * <p>
     * Forest
     * View of the forest of the domain to which this machine is joined.
     * </p>
     */
    LsapLookupGC,
    /**
     * <p>
     * <strong>LsapLookupXForestReferral:</strong> SIDs MUST be
     * searched in the databases under the Security Principal SID and Security
     * Principal SID History columns in the following views:
     * </p>
     *
     * <p>
     * Forest
     * Views of trusted forests for the forest of the domain to which this machine
     * is
     * joined.
     * </p>
     */
    LsapLookupXForestReferral,
    /**
     * <p>
     * <strong>LsapLookupXForestResolve:</strong> SIDs MUST be
     * searched in the databases under the Security Principal SID and Security
     * Principal SID History columns in the following view:
     * </p>
     *
     * <p>
     * Forest
     * View of the forest of the domain to which this machine is joined.
     * </p>
     */
    LsapLookupXForestResolve,
    /**
     * <p>
     * <strong>LsapLookupRODCReferralToFullDC:</strong> SIDs MUST be
     * searched in the databases under the Security Principal SID and Security
     * Principal SID History columns in the following order:
     * </p>
     *
     * <p>
     * Forest
     * Views of trusted forests for the forest of the domain to which this machine
     * is
     * joined, unless ClientRevision is 0x00000001 and the machine is joined to a
     * mixed mode domain, as specified in [MS-ADTS] section 6.1.4.1.
     * </p>
     *
     * <p>
     * Account
     * Domain Views of externally trusted domains for the domain to which this
     * machine
     * is joined.
     * </p>
     */
    LsapLookupRODCReferralToFullDC;

    public short getValue() {
        return (short) (ordinal() + 1);
    }
}