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
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAccountDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyPrimaryDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;
import com.rapid7.client.dcerpc.objects.ContextHandle;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc234390.aspx">LsarQueryInformationPolicy</a>
 * <blockquote><pre>The LsarQueryInformationPolicy method is invoked to query values that represent the server's information policy.
 *      NTSTATUS LsarQueryInformationPolicy(
 *          [in] LSAPR_HANDLE PolicyHandle,
 *          [in] POLICY_INFORMATION_CLASS InformationClass,
 *          [out, switch_is(InformationClass)]
 *          PLSAPR_POLICY_INFORMATION* PolicyInformation
 *      );
 *  PolicyHandle: An RPC context handle obtained from either LsarOpenPolicy or LsarOpenPolicy2.
 *  InformationClass: A parameter that specifies what type of information the caller is requesting.
 *  PolicyInformation: A parameter that references policy information structure on return.
 *  Return Values: The following is a summary of the return values that an implementation MUST return, as specified by the message processing below.
 *      0x00000000 STATUS_SUCCESS                   The request was successfully completed.
 *      0xC000009A STATUS_INSUFFICIENT_RESOURCES    There are insufficient resources to complete the request.
 *      0xC0000022 STATUS_ACCESS_DENIED             The caller does not have the permissions to perform the operation.
 *      0xC000000D STATUS_INVALID_PARAMETER         One of the parameters is incorrect. For instance, this can happen if InformationClass is out of range or if PolicyInformation is NULL.
 *      0xC0000008 STATUS_INVALID_HANDLE            PolicyHandle is not a valid handle.
 * Processing:
 *  This message MUST be processed in an identical manner to LsarQueryInformationPolicy2.</pre></blockquote>
 */
public abstract class LsarQueryInformationPolicyRequest<T extends Unmarshallable> extends RequestCall<LsarQueryInformationPolicyResponse<T>> {
    public final static short OP_NUM = 7;

    private final ContextHandle policyHandle;

    public LsarQueryInformationPolicyRequest(final ContextHandle policyHandle) {
        super(OP_NUM);
        this.policyHandle = policyHandle;
    }

    public ContextHandle getPolicyHandle() {
        return policyHandle;
    }

    public abstract PolicyInformationClass getPolicyInformationClass();

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        // [in] LSAPR_HANDLE PolicyHandle,
        packetOut.writeMarshallable(policyHandle);
        // <NDR: unsigned short> [in] POLICY_INFORMATION_CLASS InformationClass,
        // Alignment: 2 - Already aligned. ContextHandle writes 20 bytes above
        packetOut.writeShort(getPolicyInformationClass().getInfoLevel());
    }

    public static class PolicyAuditEventsInformation extends LsarQueryInformationPolicyRequest<LSAPRPolicyAuditEventsInfo> {
        public PolicyAuditEventsInformation(final ContextHandle policyHandle) {
            super(policyHandle);
        }

        @Override
        public PolicyInformationClass getPolicyInformationClass() {
            return PolicyInformationClass.POLICY_AUDIT_EVENTS_INFORMATION;
        }

        @Override
        public LsarQueryInformationPolicyResponse.PolicyAuditEventsInformation getResponseObject() {
            return new LsarQueryInformationPolicyResponse.PolicyAuditEventsInformation();
        }
    }

    public static class PolicyPrimaryDomainInformation extends LsarQueryInformationPolicyRequest<LSAPRPolicyPrimaryDomInfo> {
        public PolicyPrimaryDomainInformation(final ContextHandle policyHandle) {
            super(policyHandle);
        }

        @Override
        public PolicyInformationClass getPolicyInformationClass() {
            return PolicyInformationClass.POLICY_PRIMARY_DOMAIN_INFORMATION;
        }

        @Override
        public LsarQueryInformationPolicyResponse.PolicyPrimaryDomainInformation getResponseObject() {
            return new LsarQueryInformationPolicyResponse.PolicyPrimaryDomainInformation();
        }
    }

    public static class PolicyAccountDomainInformation extends LsarQueryInformationPolicyRequest<LSAPRPolicyAccountDomInfo> {
        public PolicyAccountDomainInformation(final ContextHandle policyHandle) {
            super(policyHandle);
        }

        @Override
        public PolicyInformationClass getPolicyInformationClass() {
            return PolicyInformationClass.POLICY_ACCOUNT_DOMAIN_INFORMATION;
        }

        @Override
        public LsarQueryInformationPolicyResponse.PolicyAccountDomainInformation getResponseObject() {
            return new LsarQueryInformationPolicyResponse.PolicyAccountDomainInformation();
        }
    }
}
