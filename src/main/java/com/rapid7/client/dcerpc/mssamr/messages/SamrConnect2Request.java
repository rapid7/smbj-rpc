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
import java.util.EnumSet;
import com.google.common.base.Strings;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

public class SamrConnect2Request extends RequestCall<SamrConnect2Response> {

    public final static short OP_NUM = 57;

    private final String serverName;
    private final EnumSet<AccessMask> desiredAccess;

    public SamrConnect2Request(String serverName, final EnumSet<AccessMask> desiredAccess) {
        super(OP_NUM);
        this.serverName = Strings.nullToEmpty(serverName);
        this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut)
            throws IOException {
        packetOut.writeStringRef(serverName, true);
        packetOut.writeInt((int) EnumUtils.toLong(desiredAccess));
    }

    @Override
    public SamrConnect2Response getResponseObject() {
        return new SamrConnect2Response();
    }
}
