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
import org.apache.commons.lang3.ArrayUtils;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

public class SamrLookupIdsInDomainRequest extends RequestCall<SamrLookupIdsInDomainResponse> {
    public static short OP_NUM = 18;
    private final byte[] handle;
    private final int count;
    private final Integer[] relativeIDs;

    public SamrLookupIdsInDomainRequest(final byte[] domainHandle, int... rids) {
        super(OP_NUM);
        this.handle = domainHandle;
        this.count = rids.length;
        if (count > 1000)
            throw new IllegalArgumentException(
                "Count cannot exceed 1000 to limit the amount of memory client can force the server to allocate: "
                    + count);
        this.relativeIDs = ArrayUtils.toObject(rids);
    }

    @Override
    public void marshal(PacketOutput out) throws IOException {
        out.write(handle);
        out.writeInt(count);
        out.writeInt(1000);
        out.writeInt(0);
        out.writeInt(count);
        for (Integer rid : relativeIDs) {
            out.writeInt(rid);
        }
    }

    @Override
    public SamrLookupIdsInDomainResponse getResponseObject() {
        return new SamrLookupIdsInDomainResponse();
    }

}
