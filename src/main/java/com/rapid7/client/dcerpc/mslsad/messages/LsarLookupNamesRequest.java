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

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import java.io.IOException;
import java.util.EnumSet;

public class LsarLookupNamesRequest extends RequestCall<LsarLookupNamesResponse> {
    private final static short OP_NUM = 14;
    private final static int LSA_LOOKUP_NAMES_ALL = 0x1;

    private final String[] names;
    private final ContextHandle policyHandle;

    public LsarLookupNamesRequest(final ContextHandle policyHandle, final String[] names) {
        super(OP_NUM);
        this.names = names;
        this.policyHandle = policyHandle;
    }

    @Override
    public LsarLookupNamesResponse getResponseObject() {
        return new LsarLookupNamesResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut)
        throws IOException {
        packetOut.write(policyHandle.getBytes());
        packetOut.writeInt(names.length);
        writeNames(packetOut);
        packetOut.writeInt(0); //count for SID
        packetOut.writeNull(); // SID
        packetOut.writeInt(LSA_LOOKUP_NAMES_ALL);
        packetOut.writeNull(); // Count (ignored on input)
    }

    private void writeNames(final PacketOutput packetOut)
            throws IOException {
        // conformat array type
        packetOut.writeInt(names.length);
        for (String name: names) {
            writeRPCUnicodeString(packetOut, name);
        }
    }

    private void writeRPCUnicodeString(final PacketOutput packetOut, String name)
        throws IOException {
        packetOut.writeShort(name.length() * 2); //length
        packetOut.writeShort(name.length() * 2); //max length
        packetOut.writeStringRef(name, false);
    }
}
