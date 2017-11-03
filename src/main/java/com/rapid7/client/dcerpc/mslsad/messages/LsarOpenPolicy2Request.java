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
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;

public class LsarOpenPolicy2Request extends RequestCall<HandleResponse> {
    private final static short OP_NUM = 44;
    private final String systemName;
    private final EnumSet<AccessMask> desiredAccess;

    public LsarOpenPolicy2Request(final String systemName, final EnumSet<AccessMask> desiredAccess) {
        super(OP_NUM);
        this.systemName = systemName;
        this.desiredAccess = desiredAccess;
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut)
        throws IOException {
        packetOut.writeStringRef(systemName, true);

        // LSAPR_OBJECT_ATTRIBUTES
        packetOut.writeInt(24);
        packetOut.writeNull();
        packetOut.writeNull();
        packetOut.writeInt(0);
        packetOut.writeNull();

        packetOut.writeInt(0x12345678);
        packetOut.writeInt(12);
        packetOut.writeShort((short) 2);
        packetOut.writeByte((byte) 0x01);
        packetOut.writeByte((byte) 0x00);
        // LSAPR_OBJECT_ATTRIBUTES ENDS

        packetOut.writeInt((int) EnumUtils.toLong(desiredAccess));
    }
}
