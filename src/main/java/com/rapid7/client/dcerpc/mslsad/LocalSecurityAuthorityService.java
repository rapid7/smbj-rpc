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

import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyHandle;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyPrimaryDomainInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAccountDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyPrimaryDomInfo;
import com.rapid7.client.dcerpc.mslsad.messages.LsarCloseRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountRightsRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountsWithUserRightRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSIDsRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSIDsResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarOpenPolicy2Request;
import com.rapid7.client.dcerpc.mslsad.messages.LsarQueryInformationPolicyRequest;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedName;
import com.rapid7.client.dcerpc.objects.MalformedSIDException;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import java.io.IOException;

/**
 * This class implements a partial Local Security Authority service in according with [MS-LSAD] and [MS-LSAT].
 *
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234225.aspx">[MS-LSAD]</a>
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234420.aspx">[MS-LSAT]</a>
 */
public class LocalSecurityAuthorityService extends Service {
    private final static int MAXIMUM_ALLOWED = 33554432;

    public LocalSecurityAuthorityService(final RPCTransport transport) {
        super(transport);
    }

    public PolicyHandle openPolicyHandle(final String serverName) throws IOException {
        final LsarOpenPolicy2Request request = new LsarOpenPolicy2Request(serverName, MAXIMUM_ALLOWED);
        return parsePolicyHandle(callExpectSuccess(request, "LsarOpenPolicy2").getHandle());
    }

    /**
     * Use LsarQueryInformationPolicy to retrieve the {@link PolicyAuditEventsInfo} for the given
     * policy handle.
     *
     * @param policyHandle The policy which corresponds to the returned {@link PolicyAuditEventsInfo}
     * @return The {@link PolicyAuditEventsInfo} for the given policy handle.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public PolicyAuditEventsInfo getPolicyAuditEventsInfo(final PolicyHandle policyHandle) throws IOException {
        final LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation request =
                new LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation(parseHandle(policyHandle));
        final LSAPRPolicyAuditEventsInfo policyInformation =
                callExpectSuccess(request, "LsarQueryInformationPolicy[2]").getPolicyInformation();
        return new PolicyAuditEventsInfo(
                (policyInformation.getAuditingMode() != 0),
                policyInformation.getEventAuditingOptions());
    }

    public PolicyPrimaryDomainInfo getPolicyPrimaryDomainInformation(final PolicyHandle policyHandle)
            throws IOException {
        final LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation request =
                new LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation(parseHandle(policyHandle));
        final LSAPRPolicyPrimaryDomInfo policyInformation =
                callExpectSuccess(request, "LsarQueryInformationPolicy[3]").getPolicyInformation();
        return new PolicyPrimaryDomainInfo(
                policyInformation.getName().getValue(),
                parseRPCSID(policyInformation.getSid()));
    }

    public LSAPRPolicyAccountDomInfo getPolicyAccountDomainInformation(final PolicyHandle policyHandle)
            throws IOException {
        final LsarQueryInformationPolicyRequest.PolicyAccountDomainInformation request =
                new LsarQueryInformationPolicyRequest.PolicyAccountDomainInformation(parseHandle(policyHandle));
        return callExpectSuccess(request, "LsarQueryInformationPolicy[5]").getPolicyInformation();
    }

    public String[] getLookupAcctPrivs(final PolicyHandle policyHandle, final SID sid) throws IOException {
        final LsarEnumerateAccountRightsRequest request =
                new LsarEnumerateAccountRightsRequest(parseHandle(policyHandle), parseSID(sid));
        return callExpectSuccess(request, "LsarEnumerateAccountRights").getPrivNames();
    }

    public SID[] enumerateAccountsWithPrivilege(final PolicyHandle policyHandle, final String userRight)
            throws IOException {
        final LsarEnumerateAccountsWithUserRightRequest request = new LsarEnumerateAccountsWithUserRightRequest(
                parseHandle(policyHandle), RPCUnicodeString.NonNullTerminated.of(userRight));
        final RPCSID[] rpcsids = callExpect(request, "LsarEnumerateAccountsWithUserRight",
                SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_NO_MORE_ENTRIES).getSids();
        return parseRPCSIDs(rpcsids);
    }

    public void closePolicyHandle(final PolicyHandle handle) throws IOException {
        final LsarCloseRequest request = new LsarCloseRequest(parseHandle(handle));
        callExpect(request, "LsarClose", SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_INVALID_HANDLE);
    }

    public LsarLookupNamesResponse lookupNames(final PolicyHandle policyHandle, final String... names)
            throws IOException {
        final LsarLookupNamesRequest request = new LsarLookupNamesRequest(parseHandle(policyHandle), names);
        return callExpectSuccess(request, "LsarLookupNames");
    }

    /**
     * @param policyHandle Handle to the policy
     * @param sids List of SIDs to lookup
     * @return A list of Strings containing account names. Where account names are not mapped, null is returned.
     * @throws IOException Thrown if exception happens at the RPC layer
     * @throws MalformedSIDException Thrown if any of the SIDs do not conform to the SID format
     */
    public String[] lookupSIDs(final PolicyHandle policyHandle, SID ... sids) throws IOException, MalformedSIDException {
        String[] mappedNames;
        final LsarLookupSIDsRequest request = new LsarLookupSIDsRequest(parseHandle(policyHandle), parseSIDs(sids));
        final LsarLookupSIDsResponse lsarLookupSIDsResponse = callExpect(request, "LsarLookupSIDs",
                SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_SOME_NOT_MAPPED);

        LSAPRTranslatedName[] nameArray = lsarLookupSIDsResponse.getLsaprTranslatedNames().getlsaprTranslatedNameArray();
        mappedNames = new String[nameArray.length];
        for (int i = 0; i < nameArray.length; i++) {
            mappedNames[i] = nameArray[i].getName().getValue();
        }
        return mappedNames;
    }

    private PolicyHandle parsePolicyHandle(final byte[] handle) {
        return new PolicyHandle(handle);
    }
}


