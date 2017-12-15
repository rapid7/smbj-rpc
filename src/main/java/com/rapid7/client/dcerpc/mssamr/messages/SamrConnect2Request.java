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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <blockquote><pre>The SamrConnect2 method returns a handle to a server object.
 *
 *      long SamrConnect2(
 *          [in, unique, string] PSAMPR_SERVER_NAME ServerName,
 *          [out] SAMPR_HANDLE* ServerHandle,
 *          [in] unsigned long DesiredAccess
 *      );
 *
 *  ServerName: The null-terminated NETBIOS name of the server; this parameter MAY be ignored on receipt.
 *  ServerHandle: An RPC context handle, as specified in section 2.2.3.2.
 *  DesiredAccess: An ACCESS_MASK that indicates the access requested for ServerHandle on output. See section 2.2.1.3 for a listing of possible values.</pre></blockquote>
 */
public class SamrConnect2Request extends RequestCall<HandleResponse> {
    public final static short OP_NUM = 57;

    // <NDR: pointer[struct]> [in, unique, string] PSAMPR_SERVER_NAME ServerName
    private final WChar.NullTerminated serverName;
    // <NDR: unsigned long> [in] unsigned long DesiredAccess
    private final int desiredAccess;

    public SamrConnect2Request(WChar.NullTerminated serverName, int desiredAccess) {
        super(OP_NUM);
        this.serverName = serverName;
        this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: pointer[conformant varying array]> [in, unique, string] PSAMPR_SERVER_NAME ServerName
        if (packetOut.writeReferentID(this.serverName)) {
            packetOut.writeMarshallable(this.serverName);
            // Alignment for DesiredAccess
            packetOut.align(Alignment.FOUR);
        }
        // <NDR: unsigned long> [in] unsigned long DesiredAccess
        packetOut.writeInt(this.desiredAccess);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
