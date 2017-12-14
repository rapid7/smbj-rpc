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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc234486.aspx">LsarOpenPolicy2</a>
 * <blockquote><pre>The LsarOpenPolicy2 method opens a context handle to the RPC server.
 *
 *      NTSTATUS LsarOpenPolicy2(
 *          [in, unique, string] wchar_t* SystemName,
 *          [in] PLSAPR_OBJECT_ATTRIBUTES ObjectAttributes,
 *          [in] ACCESS_MASK DesiredAccess,
 *          [out] LSAPR_HANDLE* PolicyHandle
 *      );
 *
 *  Processing rules for this message are defined in [MS-LSAD] section 3.1.4.4.1.</pre></blockquote>
 */
public class LsarOpenPolicy2Request extends RequestCall<HandleResponse> {
    private final static short OP_NUM = 44;
    // <NDR: pointer[RPC_UNICODE_STRING]> [in, unique, string] wchar_t* SystemName
    private final WChar.NullTerminated systemName;
    // <NDR: unsigned long> [in] ACCESS_MASK DesiredAccess
    private final int desiredAccess;

    public LsarOpenPolicy2Request(final WChar.NullTerminated systemName, final int desiredAccess) {
        super(OP_NUM);
        this.systemName = systemName;
        this.desiredAccess = desiredAccess;
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        if (packetOut.writeReferentID(this.systemName)) {
            packetOut.writeMarshallable(this.systemName);
            // Align for LSAPR_OBJECT_ATTRIBUTES
            packetOut.align(Alignment.FOUR);
        }

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

        packetOut.writeInt(this.desiredAccess);
    }
}
