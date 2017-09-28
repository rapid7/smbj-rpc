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
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

public class LsarQueryInformationPolicyRequest extends RequestCall<PolicyAuditEventsInformationResponse> {
    private final static short OP_NUM = 7;
    private final ContextHandle handle;
    private final PolicyInformationClass infoLevel;

    public LsarQueryInformationPolicyRequest(final ContextHandle handle, final PolicyInformationClass infoLevel) {
        super(OP_NUM);
        this.handle = handle;
        this.infoLevel = infoLevel;
    }

    @Override
    public PolicyAuditEventsInformationResponse getResponseObject() {
        return new PolicyAuditEventsInformationResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut)
        throws IOException {
        packetOut.write(handle.getBytes());
        packetOut.writeInt(infoLevel.getInfoLevel());
    }
}
