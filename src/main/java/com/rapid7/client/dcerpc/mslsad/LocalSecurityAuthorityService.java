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

import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mslsad.messages.*;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPR_POLICY_AUDIT_EVENTS_INFO;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPR_POLICY_PRIMARY_DOM_INFO;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

/**
 * This class implements a partial Local Security Authority service in according with [MS-LSAD] and [MS-LSAT].
 *
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234225.aspx">[MS-LSAD]</a>
 * @see <a href= "https://msdn.microsoft.com/en-us/library/cc234420.aspx">[MS-LSAT]</a>
 */
public class LocalSecurityAuthorityService {

    public LocalSecurityAuthorityService(final RPCTransport transport) {
        this.transport = transport;
    }

    public void checkHandle(ContextHandle handle) throws IOException {
        if (handle == null) {
            throw new IllegalArgumentException("ContextHandle is invalid: " + handle);
        }
    }

    public ContextHandle openPolicyHandle(String serverName) throws IOException {
        final LsarOpenPolicy2Request request = new LsarOpenPolicy2Request(serverName, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        HandleResponse response = transport.call(request);
        if (response.getReturnValue() != 0) {
            throw new RPCException("LsarOpenPolicy2Request", response.getReturnValue());
        }
        return response.getHandle();
    }

    public LSAPR_POLICY_AUDIT_EVENTS_INFO getAuditPolicy(ContextHandle handle) throws IOException {
        checkHandle(handle);
        final LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation queryRequest = new LsarQueryInformationPolicyRequest.PolicyAuditEventsInformation(handle);
        return transport.call(queryRequest).getPolicyInformation();
    }

    public LSAPR_POLICY_PRIMARY_DOM_INFO getPolicyPrimaryDomainInformation(ContextHandle policyHandle) throws IOException {
        checkHandle(policyHandle);
        final LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation queryRequest = new LsarQueryInformationPolicyRequest.PolicyPrimaryDomainInformation(policyHandle);
        return transport.call(queryRequest).getPolicyInformation();
    }

    public String[] getLookupAcctPrivs(ContextHandle handle, String sid) throws IOException {
        checkHandle(handle);

        final LsarLookupAcctPrivsRpcRequest queryRequest = new LsarLookupAcctPrivsRpcRequest(handle, sid);
        final LsarLookupAcctPrivsRpcResponse queryResponse = transport.call(queryRequest);
        return queryResponse.getPrivNames();
    }

    public SID[] enumerateAccountsWithPrivilege(ContextHandle handle, String privilege) throws IOException {
        checkHandle(handle);

        final LsarLookupSidsWithAcctPrivRpcRequest queryRequest = new LsarLookupSidsWithAcctPrivRpcRequest(handle, privilege);
        final LsarLookupSidsWithAcctPrivRpcResponse queryResponse = transport.call(queryRequest);
        return queryResponse.getSids();
    }

    public void closePolicyHandle(ContextHandle handle) throws IOException {
        checkHandle(handle);

        LsarClosePolicyRpcRequest closeRequest = new LsarClosePolicyRpcRequest(handle);
        transport.call(closeRequest);
    }

    private final RPCTransport transport;
}

