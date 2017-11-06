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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyAuditEventsInfo;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;

import static com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass.POLICY_AUDIT_EVENTS_INFORMATION;

/**
 * The response class for LsarQueryInformationPolicyResponse policy formation class POLICY_AUDIT_EVENTS_INFORMATION.
 *
 * <pre>
 * Local Security Authority, lsa_QueryInfoPolicy
 * Operation: lsa_QueryInfoPolicy (7)
 * [Request in frame: 1979640]
 * Pointer to Info (lsa_PolicyInformation)
 * Referent ID: 0x00020000
 * lsa_PolicyInformation
 * Info
 * Audit Events
 * Auditing Mode: 1
 * Pointer to Settings (lsa_PolicyAuditPolicy)
 * Referent ID: 0x00020004
 * Max Count: 9
 * Settings: LSA_AUDIT_POLICY_ALL (3)
 * Settings: LSA_AUDIT_POLICY_ALL (3)
 * Settings: LSA_AUDIT_POLICY_ALL (3)
 * Settings: LSA_AUDIT_POLICY_FAILURE (2)
 * Settings: LSA_AUDIT_POLICY_FAILURE (2)
 * Settings: LSA_AUDIT_POLICY_ALL (3)
 * Settings: LSA_AUDIT_POLICY_ALL (3)
 * Settings: LSA_AUDIT_POLICY_NONE (0)
 * Settings: LSA_AUDIT_POLICY_ALL (3)
 * Count: 9
 * NT Error: STATUS_SUCCESS (0x00000000)
 * </pre>
 */
public class PolicyAuditEventsInformationResponse extends RequestResponse {
    private final static PolicyInformationClass infoLevel = POLICY_AUDIT_EVENTS_INFORMATION;
    private PolicyAuditEventsInfo auditInfo;

    public PolicyAuditEventsInfo getPolicyAuditInformation() {
        return auditInfo;
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        packetIn.readReferentID();
        final int info = packetIn.readInt();
        if (info != infoLevel.getInfoLevel()) {
            throw new IllegalArgumentException("Unexpected information level");
        }

        final boolean auditMode = packetIn.readInt() != 0;
        packetIn.readReferentID();
        final int count1 = packetIn.readInt();
        final int count2 = packetIn.readInt();
        final int count = Math.min(count1, count2);
        final int[] auditFlags = new int[count];
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                auditFlags[i] = packetIn.readInt();
            }
        }
        auditInfo = new PolicyAuditEventsInfo(auditMode, auditFlags, count);
    }
}
