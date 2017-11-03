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

public class SamrOpenUserRequest extends RequestCall<SamrOpenUserResponse> {
    public final static short OP_NUM = 34;

    private final DomainHandle handle;
    private final int userRid;

    public SamrOpenUserRequest(DomainHandle handle, int userRid) {
        super(OP_NUM);
        this.handle = handle;
        this.userRid = userRid;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(handle.getBytes());
        // Generic rights: 0x00000000
        // Standard rights: 0x00020000
        // SAMR User specific rights: 0x0000011b
        // Samr User Access Get
        // - Groups: SAMR_USER_ACCESS_GET_GROUPS is SET
        // - Attributes: SAMR_USER_ACCESS_GET_ATTRIBUTES is SET
        // - Logoninfo: SAMR_USER_ACCESS_GET_LOGONINFO is SET
        packetOut.writeInt(0x2011B);
        packetOut.writeInt(userRid);
    }

    @Override
    public SamrOpenUserResponse getResponseObject() {
        return new SamrOpenUserResponse();
    }

}
