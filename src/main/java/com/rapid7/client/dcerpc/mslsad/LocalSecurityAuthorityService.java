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
package com.rapid7.client.dcerpc.mslsad;

import static com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass.POLICY_AUDIT_EVENTS_INFORMATION;
import java.io.IOException;
import com.hierynomus.msdtyp.SID;

import com.rapid7.client.dcerpc.mslsad.messages.LsarClosePolicyRpcRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupAcctPrivsRpcRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupAcctPrivsRpcResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSidsWithAcctPrivRpcRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSidsWithAcctPrivRpcResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarQueryInformationPolicyRequest;
import com.rapid7.client.dcerpc.mslsad.messages.PolicyAuditEventsInformationResponse;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

/**
 * This class implements a partial Local Security Authority service in according with [MS-LSAD] and [MS-LSAT].
 *
 * TODO: Add more functionalities.
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

    public PolicyAuditEventsInfo getAuditPolicy(ContextHandle handle) throws IOException {
	checkHandle(handle);

	final LsarQueryInformationPolicyRequest queryRequest = new LsarQueryInformationPolicyRequest(handle,
		POLICY_AUDIT_EVENTS_INFORMATION);
	final PolicyAuditEventsInformationResponse queryResponse = transport.call(queryRequest);
	return queryResponse.getPolicyAuditInformation();
    }

    public String[] getLookupAcctPrivs(ContextHandle handle, String sid) throws IOException {
	checkHandle(handle);

	final LsarLookupAcctPrivsRpcRequest queryRequest = new LsarLookupAcctPrivsRpcRequest(handle, sid);
	final LsarLookupAcctPrivsRpcResponse queryResponse = transport.call(queryRequest);
	return queryResponse.getPrivNames();
    }

    public SID[] enumerateAccountsWithPrivilege(ContextHandle handle, String privilege) throws IOException {
	checkHandle(handle);

	final LsarLookupSidsWithAcctPrivRpcRequest queryRequest = new LsarLookupSidsWithAcctPrivRpcRequest(handle,
		privilege);
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

