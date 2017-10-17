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
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesResponse;
import com.rapid7.client.dcerpc.mslsad.messages.LsarOpenPolicy2Request;
import com.rapid7.client.dcerpc.mslsad.messages.LsarQueryInformationPolicyRequest;
import com.rapid7.client.dcerpc.mslsad.messages.PolicyAuditEventsInformationResponse;
import com.rapid7.client.dcerpc.mslsad.objects.LookupNamesInfo;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
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
    private ContextHandle policyHandle;

    public LocalSecurityAuthorityService(final RPCTransport transport) {
        this.transport = transport;
    }

    private void getPolicyHandle()
            throws IOException {
        final LsarOpenPolicy2Request openRequest =
                new LsarOpenPolicy2Request("", EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final HandleResponse openResponse = transport.call(openRequest);
        policyHandle = openResponse.getHandle();
    }
    public PolicyAuditEventsInfo getAuditPolicy()
        throws IOException {
        if (policyHandle == null) getPolicyHandle();
        final LsarQueryInformationPolicyRequest queryRequest =
            new LsarQueryInformationPolicyRequest(policyHandle, POLICY_AUDIT_EVENTS_INFORMATION);
        final PolicyAuditEventsInformationResponse queryResponse = transport.call(queryRequest);
        return queryResponse.getPolicyAuditInformation();
    }

    public LookupNamesInfo lookupName(String name)
            throws IOException {
        if (policyHandle == null) getPolicyHandle();
        final String[] names = {name};
        final LsarLookupNamesRequest lookupNamesRequest =
                new LsarLookupNamesRequest(policyHandle, names);
        final LsarLookupNamesResponse lsarLookupNamesResponse = transport.call(lookupNamesRequest);
        return lsarLookupNamesResponse.getLookupNamesInfo();
    }

    private final RPCTransport transport;
}
