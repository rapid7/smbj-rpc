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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRPSIDArray;

/**
 * The SamrGetAliasMembership method obtains the union of all aliases that a given set of SIDs is a member of.
 *
 * <pre>
 * long SamrGetAliasMembership(
 *   [in] SAMPR_HANDLE DomainHandle,
 *   [in] PSAMPR_PSID_ARRAY SidArray,
 *   [out] PSAMPR_ULONG_ARRAY Membership
 * );
 * </pre>
 *
 * <p> DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.</p>
 * <p> SidArray: A list of SIDs.</p>
 * <p> Membership: The union of all aliases (represented by RIDs) that all SIDs in SidArray are a member of.</p>
 */
public class SamrGetAliasMembershipRequest extends RequestCall<SamrGetAliasMembershipResponse> {
    public static final short OP_NUM = 16;

    private final byte[] handle;
    private final SAMPRPSIDArray sids;

    public SamrGetAliasMembershipRequest(final byte[] handle, final SAMPRPSIDArray sids) {
        super(OP_NUM);
        this.handle = handle;
        this.sids = sids;
    }

    @Override
    public void marshal(PacketOutput out) throws IOException {
        out.write(handle);
        out.writeMarshallable(this.sids);
    }

    @Override
    public SamrGetAliasMembershipResponse getResponseObject() {
        return new SamrGetAliasMembershipResponse();
    }
}
