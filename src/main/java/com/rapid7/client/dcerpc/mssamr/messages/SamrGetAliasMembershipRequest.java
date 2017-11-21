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
import java.util.Arrays;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCSIDArray;

public class SamrGetAliasMembershipRequest extends RequestCall<SamrGetAliasMembershipResponse> {
    public static final short OP_NUM = 16;

    private final DomainHandle handle;
    private final RPCSID[] sids;

    public SamrGetAliasMembershipRequest(DomainHandle handle, RPCSID... sids) {
        super(OP_NUM);
        this.handle = handle;
        this.sids = sids;
    }

    @Override
    public void marshal(PacketOutput out) throws IOException {
        out.write(handle.getBytes());
        RPCSIDArray array = new RPCSIDArray();
        array.setMaxCount(sids.length);
        array.setArray(Arrays.asList(sids));
        out.writeInt(sids.length);
        out.writeReferentID();
        out.writeMarshallable(array);
    }

    @Override
    public SamrGetAliasMembershipResponse getResponseObject() {
        return new SamrGetAliasMembershipResponse();
    }
}
