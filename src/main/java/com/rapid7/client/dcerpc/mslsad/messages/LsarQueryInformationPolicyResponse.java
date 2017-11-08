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
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mslsad.objects.POLICY_INFORMATION_CLASS;

public class LsarQueryInformationPolicyResponse<T extends Unmarshallable> extends RequestResponse {
    private final T policyInformation;
    private final POLICY_INFORMATION_CLASS policyInformationClass;

    public LsarQueryInformationPolicyResponse(T policyInformation, POLICY_INFORMATION_CLASS policyInformationClass) {
        this.policyInformation = policyInformation;
        this.policyInformationClass = policyInformationClass;
    }

    public T getPolicyInformation() {
        return policyInformation;
    }

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        if(packetIn.readReferentID() != 0) {
            // TODO Create a separate LSAR_POLICY_INFORMATION union class
            final int infoLevel = packetIn.readShort();
            if (infoLevel != this.policyInformationClass.getInfoLevel()) {
                throw new IllegalArgumentException(String.format(
                        "Incoming POLICY_INFORMATION_CLASS %d does not match expected: %d",
                        infoLevel, this.policyInformationClass.getInfoLevel()));
            }
            // Alignment for structure - It's of unknown size so ask it what its alignment is
            packetIn.align(this.policyInformation.getAlignment());
            packetIn.readUnmarshallable(this.policyInformation);
        }
    }
}
