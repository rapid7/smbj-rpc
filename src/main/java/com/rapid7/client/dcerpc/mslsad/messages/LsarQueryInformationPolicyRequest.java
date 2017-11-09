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
import com.rapid7.client.dcerpc.mslsad.objects.LSAPR_POLICY_ACCOUNT_DOM_INFO;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPR_POLICY_AUDIT_EVENTS_INFO;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPR_POLICY_PRIMARY_DOM_INFO;
import com.rapid7.client.dcerpc.mslsad.objects.POLICY_INFORMATION_CLASS;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public abstract class LsarQueryInformationPolicyRequest<T extends Unmarshallable> extends RequestCall<LsarQueryInformationPolicyResponse<T>> {
    private final static short OP_NUM = 7;
    private final ContextHandle policyHandle;
    private final POLICY_INFORMATION_CLASS infoLevel;

    public LsarQueryInformationPolicyRequest(final ContextHandle policyHandle, final POLICY_INFORMATION_CLASS infoLevel) {
        super(OP_NUM);
        this.policyHandle = policyHandle;
        this.infoLevel = infoLevel;
    }

    abstract T newPolicyInformation();

    @Override
    public LsarQueryInformationPolicyResponse<T> getResponseObject() {
        //noinspection unchecked
        return (LsarQueryInformationPolicyResponse<T>) new LsarQueryInformationPolicyResponse(newPolicyInformation(), infoLevel);
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        packetOut.writeMarshallable(policyHandle);
        packetOut.writeShort(infoLevel.getInfoLevel());
    }

    public static class PolicyAuditEventsInformation extends LsarQueryInformationPolicyRequest<LSAPR_POLICY_AUDIT_EVENTS_INFO> {
        public PolicyAuditEventsInformation(final ContextHandle policyHandle) {
            super(policyHandle, POLICY_INFORMATION_CLASS.POLICY_AUDIT_EVENTS_INFORMATION);
        }

        @Override
        LSAPR_POLICY_AUDIT_EVENTS_INFO newPolicyInformation() {
            return new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        }
    }

    public static class PolicyPrimaryDomainInformation extends LsarQueryInformationPolicyRequest<LSAPR_POLICY_PRIMARY_DOM_INFO> {
        public PolicyPrimaryDomainInformation(final ContextHandle policyHandle) {
            super(policyHandle, POLICY_INFORMATION_CLASS.POLICY_PRIMARY_DOMAIN_INFORMATION);
        }

        @Override
        LSAPR_POLICY_PRIMARY_DOM_INFO newPolicyInformation() {
            return new LSAPR_POLICY_PRIMARY_DOM_INFO();
        }
    }

    public static class PolicyAccountDomainInformation extends LsarQueryInformationPolicyRequest<LSAPR_POLICY_ACCOUNT_DOM_INFO> {
        public PolicyAccountDomainInformation(final ContextHandle policyHandle) {
            super(policyHandle, POLICY_INFORMATION_CLASS.POLICY_ACCOUNT_DOMAIN_INFORMATION);
        }

        @Override
        LSAPR_POLICY_ACCOUNT_DOM_INFO newPolicyInformation() {
            return new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        }
    }
}
