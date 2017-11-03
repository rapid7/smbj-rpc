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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;

public class SamrOpenAliasRequest extends RequestCall<SamrOpenAliasResponse> {
    public final static short OP_NUM = 27;

    private final DomainHandle handle;
    private final int userRid;
    private final int desiredAccess;

    public SamrOpenAliasRequest(DomainHandle handle, int userRid) {
        // SAMR_ALIAS_ACCESS_LOOKUP_INFO(8)
        this(handle, userRid, 8);
    }

    public SamrOpenAliasRequest(DomainHandle handle, int userRid, int desiredAccess) {
        super(OP_NUM);
        this.handle = handle;
        this.userRid = userRid;
        this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(handle.getBytes());
        packetOut.writeInt(desiredAccess);
        packetOut.writeInt(userRid);
    }

    @Override
    public SamrOpenAliasResponse getResponseObject() {
        return new SamrOpenAliasResponse();
    }

}