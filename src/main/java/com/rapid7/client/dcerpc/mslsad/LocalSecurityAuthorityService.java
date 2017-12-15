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
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mslsad;

import java.io.IOException;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mslsad.dto.LSAPLookupLevel;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyDomainInfo;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyHandle;
import com.rapid7.client.dcerpc.mslsad.messages.LsarCloseRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountRightsRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountsWithUserRightRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSIDsRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSIDsResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarOpenPolicy2Request;
import com.rapid7.client.dcerpc.mslsad.messages.LsarQueryInformationPolicyRequest;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAccountDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyPrimaryDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedName;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedSID;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTrustInformation;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.objects.WChar;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

/**
 * This class implements a partial Local Security Authority service in according with [MS-LSAD] and [MS-LSAT].
 *
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234225.aspx">[MS-LSAD]</a>
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234420.aspx">[MS-LSAT]</a>
 */
public class LocalSecurityAuthorityService extends Service {
    private final static int MAXIMUM_ALLOWED = 33554432;

    /**
     * Create a new {@link LocalSecurityAuthorityService} backed by the provided {@link RPCTransport}
     * which should be bound to the lsarpc interface.
     * @param transport The {@link RPCTransport} bound to the lsarpc interface.
     */
    public LocalSecurityAuthorityService(final RPCTransport transport) {
        super(transport);
    }

    /**
     * Open a new {@link PolicyHandle}.
     *
     * @return A new {@link PolicyHandle}.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public PolicyHandle openPolicyHandle() throws IOException {
        final LsarOpenPolicy2Request request = new LsarOpenPolicy2Request(WChar.NullTerminated.of(""), MAXIMUM_ALLOWED);
        return parsePolicyHandle(callExpectSuccess(request, "LsarOpenPolicy2").getHandle());
    }

    /**
     * Close the provided {@link PolicyHandle}.
     * If the handle has already been closed or is otherwise invalid it will be ignored.
     * @param handle The handle to close.
     * @return True if the provided handle was closed successfully.
     * False if the handle was invalid (i.e. already closed). Throws {@link RPCException} otherwise.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns a response value other than ERROR_SUCCESS or STATUS_INVALID_HANDLE.
     */
    public boolean closePolicyHandle(final PolicyHandle handle) throws IOException {
        final LsarCloseRequest request = new LsarCloseRequest(parseHandle(handle));
        final HandleResponse response = call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()))
            return true;
        else if (SystemErrorCode.STATUS_INVALID_HANDLE.is(response.getReturnValue()))
            return false;
        throw new RPCException("LsarClose", response.getReturnValue());
    }

    /**
     * Use LsarQueryInformationPolicy to retrieve the {@link PolicyAuditEventsInfo} for the given
     * policy handle.
     *
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @return The {@link PolicyAuditEventsInfo} for the given policy handle.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public PolicyAuditEventsInfo getPolicyAuditEventsInfo(final PolicyHandle policyHandle) throws IOException {
        final LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation request =
                new LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation(parseHandle(policyHandle));
        final LSAPRPolicyAuditEventsInfo policyInformation =
                callExpectSuccess(request, "LsarQueryInformationPolicy[2]").getPolicyInformation();
        if (policyInformation == null)
            return null;
        return new PolicyAuditEventsInfo((policyInformation.getAuditingMode() != 0),
                policyInformation.getEventAuditingOptions());
    }

    /**
     * Use LsarQueryInformationPolicy to retrieve the {@link PolicyDomainInfo} for the given
     * policy handle.
     *
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @return The {@link PolicyDomainInfo} for the given policy handle.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public PolicyDomainInfo getPolicyPrimaryDomainInformation(final PolicyHandle policyHandle)
            throws IOException {
        final LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation request =
                new LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation(parseHandle(policyHandle));
        final LSAPRPolicyPrimaryDomInfo policyInformation =
                callExpectSuccess(request, "LsarQueryInformationPolicy[3]").getPolicyInformation();
        if (policyInformation == null)
            return null;
        return new PolicyDomainInfo(parseRPCUnicodeString(policyInformation.getName()),
                parseRPCSID(policyInformation.getSid()));
    }

    /**
     * Use LsarQueryInformationPolicy to retrieve the {@link PolicyDomainInfo} for the given
     * policy handle.
     *
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @return The {@link PolicyDomainInfo} for the given policy handle.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public PolicyDomainInfo getPolicyAccountDomainInformation(final PolicyHandle policyHandle)
            throws IOException {
        final LsarQueryInformationPolicyRequest.PolicyAccountDomainInformation request =
                new LsarQueryInformationPolicyRequest.PolicyAccountDomainInformation(parseHandle(policyHandle));
        final LSAPRPolicyAccountDomInfo policyInformation =
                callExpectSuccess(request, "LsarQueryInformationPolicy[5]").getPolicyInformation();
        if (policyInformation == null)
            return null;
        return new PolicyDomainInfo(parseRPCUnicodeString(policyInformation.getDomainName()),
                parseRPCSID(policyInformation.getDomainSid()));
    }

    /**
     * Use LsaEnumerateAccountRights to retrieve a list of account rights for the given sid belonging to the server
     * which is represented by the policyHandle.
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @param sid The account to lookup rights for represented as an {@link SID}.
     * @return A list of account rights belonging to the given {@link SID} on the server represented by {@link PolicyHandle}.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public String[] getAccountRights(final PolicyHandle policyHandle, final SID sid) throws IOException {
        final LsarEnumerateAccountRightsRequest request =
                new LsarEnumerateAccountRightsRequest(parseHandle(policyHandle), parseSID(sid));
        final RPCUnicodeString.NonNullTerminated[] privNames =
                callExpectSuccess(request, "LsarEnumerateAccountRights").getPrivNames();
        return parseRPCUnicodeStrings(privNames);
    }

    /**
     * Use LsarEnumerateAccountsWithUserRight to retrieve a list of {@link SID}s which represent accounts
     * that have the provided userRight.
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @param userRight The user right to lookup accounts for.
     * @return A list of {@link SID}s which represent accounts that have the provided userRight.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SID[] getAccountsWithUserRight(final PolicyHandle policyHandle, final String userRight)
            throws IOException {
        final LsarEnumerateAccountsWithUserRightRequest request = new LsarEnumerateAccountsWithUserRightRequest(
                parseHandle(policyHandle), RPCUnicodeString.NonNullTerminated.of(userRight));
        final RPCSID[] rpcsids = callExpect(request, "LsarEnumerateAccountsWithUserRight",
                SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_NO_MORE_ENTRIES).getSids();
        return parseRPCSIDs(rpcsids);
    }

    /**
     * Look up {@link SID}s for the given names. Uses {@link LSAPLookupLevel#LSAP_LOOKUP_WKSTA} as a lookup level.
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @param names Array of names to lookup {@link SID}s for.
     * @return An array of {@link SID}s. Each entry index in this list corresponds to the same entry index in
     * the provided names array. A null entry indicates that the given name was not found.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SID[] lookupSIDsForNames(final PolicyHandle policyHandle, String... names) throws IOException {
        return lookupSIDsForNames(policyHandle, LSAPLookupLevel.LSAP_LOOKUP_WKSTA, names);
    }

    /**
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @param lookupLevel Look up level as defined in {@link LSAPLookupLevel}.
     * @param names Array of names to lookup {@link SID}s for.
     * @return An array of {@link SID}s. Each entry index in this list corresponds to the same entry index in
     * the provided names array. A null entry indicates that the given name was not found.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SID[] lookupSIDsForNames(final PolicyHandle policyHandle, final LSAPLookupLevel lookupLevel, final String... names)
            throws IOException {
        final LsarLookupNamesRequest request = new LsarLookupNamesRequest(
                parseHandle(policyHandle), parseNonNullTerminatedStrings(names), lookupLevel.getValue());
        final LsarLookupNamesResponse response = callExpect(request, "LsarLookupNames",
                SystemErrorCode.ERROR_SUCCESS,
                SystemErrorCode.STATUS_SOME_NOT_MAPPED);
        LSAPRTranslatedSID[] translatedSIDs = response.getTranslatedSIDs().getSIDs();
        if (translatedSIDs == null)
            translatedSIDs = new LSAPRTranslatedSID[0];
        LSAPRTrustInformation[] domainArray = response.getReferencedDomains().getDomains();
        if (domainArray == null)
            domainArray = new LSAPRTrustInformation[0];
        // Create DTO SIDs
        final SID[] sids = new SID[translatedSIDs.length];
        for (int i = 0; i < translatedSIDs.length; i++) {
            final LSAPRTranslatedSID translatedSID = translatedSIDs[i];
            if (translatedSID == null)
                continue;
            //get domain SID
            final int domainIndex = (int) translatedSID.getDomainIndex();
            //DomainIndex can be -1 if name is unknown / domain SID does not exist
            if (domainIndex < 0)
                continue;
            final RPCSID sid = domainArray[domainIndex].getSid();
            //can be null because it's a pointer
            if (sid == null)
                continue;
            final SID dtoSID = parseRPCSID(sid, false);
            //add RID to SID
            sids[i] = dtoSID.resolveRelativeID(translatedSID.getRelativeId());
        }
        return sids;
    }

    /**
     * Look up names for the given {@link SID}s. Uses {@link LSAPLookupLevel#LSAP_LOOKUP_WKSTA} as a lookup level.
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @param sids Array of {@link SID}s to lookup
     * @return An array of names. Each entry index in this list corresponds to the same entry index in
     * the provided sods array. A null entry indicates that the given {@link SID} was not found.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public String[] lookupNamesForSIDs(final PolicyHandle policyHandle, SID ... sids) throws IOException {
        return lookupNamesForSIDs(policyHandle, LSAPLookupLevel.LSAP_LOOKUP_WKSTA, sids);
    }

    /**
     * @param policyHandle A valid policy handle obtained from {@link #openPolicyHandle()}.
     * @param lookupLevel Look up level as defined in {@link LSAPLookupLevel}.
     * @param sids Array of {@link SID}s to lookup
     * @return An array of names. Each entry index in this list corresponds to the same entry index in
     *         the provided sids array. The original SID would be returned as a string if the given
     *         {@link SID} was not mapped.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public String[] lookupNamesForSIDs(final PolicyHandle policyHandle, final LSAPLookupLevel lookupLevel, SID ... sids)
            throws IOException {
        final LsarLookupSIDsRequest request = new LsarLookupSIDsRequest(parseHandle(policyHandle), parseSIDs(sids),
                lookupLevel.getValue());
        final LsarLookupSIDsResponse lsarLookupSIDsResponse = callExpect(request, "LsarLookupSIDs",
                SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_SOME_NOT_MAPPED);
        LSAPRTranslatedName[] nameArray = lsarLookupSIDsResponse.getTranslatedNames().getNames();
        if (nameArray == null)
            nameArray = new LSAPRTranslatedName[0];
        final String[] mappedNames = new String[nameArray.length];
        for (int i = 0; i < nameArray.length; i++) {
            mappedNames[i] = parseRPCUnicodeString(nameArray[i].getName());
        }
        return mappedNames;
    }

    private PolicyHandle parsePolicyHandle(final byte[] handle) {
        return new PolicyHandle(handle);
    }
}
