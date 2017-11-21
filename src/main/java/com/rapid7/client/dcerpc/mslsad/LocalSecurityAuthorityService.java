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

import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAccountDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyPrimaryDomInfo;
import com.rapid7.client.dcerpc.mslsad.messages.LsarClosePolicyRpcRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupAcctPrivsRpcRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupAcctPrivsRpcResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountsWithUserRightRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountsWithUserRightResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarOpenPolicy2Request;
import com.rapid7.client.dcerpc.mslsad.messages.LsarQueryInformationPolicyRequest;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import java.io.IOException;

/**
 * This class implements a partial Local Security Authority service in according with [MS-LSAD] and [MS-LSAT].
 *
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234225.aspx">[MS-LSAD]</a>
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234420.aspx">[MS-LSAT]</a>
 */
public class LocalSecurityAuthorityService {
    private final static int MAXIMUM_ALLOWED = 33554432;

    public LocalSecurityAuthorityService(final RPCTransport transport) {
        this.transport = transport;
    }

    public void checkHandle(ContextHandle handle) throws IOException {
        if (handle == null) {
            throw new IllegalArgumentException("ContextHandle is invalid: null");
        }
    }

    public ContextHandle openPolicyHandle(String serverName) throws IOException {
        final LsarOpenPolicy2Request request = new LsarOpenPolicy2Request(serverName, MAXIMUM_ALLOWED);
        final HandleResponse response = transport.call(request);
        if (response.getReturnValue() != 0) {
            throw new RPCException("LsarOpenPolicy2Request", response.getReturnValue());
        }
        return response.getHandle();
    }

    public LSAPRPolicyAuditEventsInfo getAuditPolicy(ContextHandle handle) throws IOException {
        checkHandle(handle);
        final LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation queryRequest = new LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation(handle);
        return transport.call(queryRequest).getPolicyInformation();
    }

    public LSAPRPolicyPrimaryDomInfo getPolicyPrimaryDomainInformation(ContextHandle policyHandle) throws IOException {
        checkHandle(policyHandle);
        final LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation queryRequest = new LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation(policyHandle);
        return transport.call(queryRequest).getPolicyInformation();
    }

    public LSAPRPolicyAccountDomInfo getPolicyAccountDomainInformation(ContextHandle policyHandle) throws IOException {
        checkHandle(policyHandle);
        final LsarQueryInformationPolicyRequest.PolicyAccountDomainInformation queryRequest = new LsarQueryInformationPolicyRequest.PolicyAccountDomainInformation(policyHandle);
        return transport.call(queryRequest).getPolicyInformation();
    }

    public String[] getLookupAcctPrivs(ContextHandle handle, String sid) throws IOException {
        checkHandle(handle);

        final LsarLookupAcctPrivsRpcRequest queryRequest = new LsarLookupAcctPrivsRpcRequest(handle, sid);
        final LsarLookupAcctPrivsRpcResponse queryResponse = transport.call(queryRequest);
        return queryResponse.getPrivNames();
    }

    public RPCSID[] enumerateAccountsWithPrivilege(ContextHandle policyHandle, String userRight) throws IOException {
        checkHandle(policyHandle);
        final LsarEnumerateAccountsWithUserRightRequest queryRequest =
                new LsarEnumerateAccountsWithUserRightRequest(
                        policyHandle, RPCUnicodeString.NonNullTerminated.of(userRight));
        final LsarEnumerateAccountsWithUserRightResponse queryResponse = transport.call(queryRequest);
        return queryResponse.getSids();
    }

    public void closePolicyHandle(ContextHandle handle) throws IOException {
        checkHandle(handle);

        LsarClosePolicyRpcRequest closeRequest = new LsarClosePolicyRpcRequest(handle);
        transport.call(closeRequest);
    }

    public LsarLookupNamesResponse lookupNames(ContextHandle policyHandle, String... names)
            throws IOException {
        final LsarLookupNamesRequest lookupNamesRequest =
                new LsarLookupNamesRequest(policyHandle, names);
        final LsarLookupNamesResponse lsarLookupNamesResponse = transport.call(lookupNamesRequest);
        if (lsarLookupNamesResponse.getReturnValue() != 0) {
            throw new RPCException("LsarLookupNamesResponse: ", lsarLookupNamesResponse.getReturnValue());
        } else return lsarLookupNamesResponse;
    }

    private final RPCTransport transport;
}

