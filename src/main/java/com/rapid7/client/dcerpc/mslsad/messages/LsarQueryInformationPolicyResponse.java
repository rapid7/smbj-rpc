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
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAccountDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRPolicyPrimaryDomInfo;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;

public abstract class LsarQueryInformationPolicyResponse<T extends Unmarshallable> extends RequestResponse {
    private T policyInformation;

    public abstract PolicyInformationClass getPolicyInformationClass();

    abstract T createPolicyInformation();

    public T getPolicyInformation() {
        return policyInformation;
    }

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        if(packetIn.readReferentID() != 0) {
            final int infoLevel = packetIn.readUnsignedShort();
            if (infoLevel != getPolicyInformationClass().getInfoLevel()) {
                throw new UnmarshalException(String.format(
                        "Incoming POLICY_INFORMATION_CLASS %d does not match expected: %d",
                        infoLevel, getPolicyInformationClass().getInfoLevel()));
            }
            this.policyInformation = createPolicyInformation();
            packetIn.readUnmarshallable(this.policyInformation);
        } else {
            this.policyInformation = null;
        }
    }

    public static class PolicyAuditEventsInformation extends LsarQueryInformationPolicyResponse<LSAPRPolicyAuditEventsInfo> {
        @Override
        public PolicyInformationClass getPolicyInformationClass() {
            return PolicyInformationClass.POLICY_AUDIT_EVENTS_INFORMATION;
        }

        @Override
        LSAPRPolicyAuditEventsInfo createPolicyInformation() {
            return new LSAPRPolicyAuditEventsInfo();
        }
    }

    public static class PolicyPrimaryDomainInformation extends LsarQueryInformationPolicyResponse<LSAPRPolicyPrimaryDomInfo> {
        @Override
        public PolicyInformationClass getPolicyInformationClass() {
            return PolicyInformationClass.POLICY_PRIMARY_DOMAIN_INFORMATION;
        }

        @Override
        LSAPRPolicyPrimaryDomInfo createPolicyInformation() {
            return new LSAPRPolicyPrimaryDomInfo();
        }
    }

    public static class PolicyAccountDomainInformation extends LsarQueryInformationPolicyResponse<LSAPRPolicyAccountDomInfo> {
        @Override
        public PolicyInformationClass getPolicyInformationClass() {
            return PolicyInformationClass.POLICY_ACCOUNT_DOMAIN_INFORMATION;
        }

        @Override
        LSAPRPolicyAccountDomInfo createPolicyInformation() {
            return new LSAPRPolicyAccountDomInfo();
        }
    }
}
