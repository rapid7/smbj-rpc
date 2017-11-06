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
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public class LsarLookupAcctPrivsRpcRequest extends RequestCall<LsarLookupAcctPrivsRpcResponse> {
    private final static short OP_NUM = 36;
    private final ContextHandle handle;
    private final SID sid;

    public LsarLookupAcctPrivsRpcRequest(final ContextHandle handle, final String sid) {
        super(OP_NUM);
        this.handle = handle;
        this.sid = SID.fromString(sid);
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
      /*
       * Rpc Info MajorVer: 05
       * MinorVer: 00
       * PacketType: 00 (Request)
       * Flags: 03
       * PackType: 10000000
       * FragLen: 4000
       * AuthLen: 0000
       * CallId: 04000000
       * AllocHint: 28000000
       * ContextId: 0000
       * OpNum: 2400 (LsaLookupAcctPrivs)
       *
       * PolicyHnd: 00000000EE88616D65EFD711BCDDEA5AF8147838
       * Sid NumAuths: 00000002
       * RevNum: 01
       * NumAuths: 02
       * IdAuth: 000000000005
       * SubAuth: 20000000 20020000
       */

        // set the policy handle
        packetOut.write(handle.getBytes());

        // num sub-auths
        packetOut.writeInt(sid.getSubAuthorities().length);

      /*
       * SID Format:
       * RevNum: 01
       * NumAuths: 04
       * IdAuth: 000000000005
       * SubAuth1:15000000
       * SubAuth2: 942E0D30
       * SubAuth3: 1025F335
       * SubAuth4: 61301E37
       */
        write(sid, packetOut);
    }

    @Override
    public LsarLookupAcctPrivsRpcResponse getResponseObject() {
        return new LsarLookupAcctPrivsRpcResponse();
    }

    public void write(SID sid, PacketOutput packetOut) throws IOException {
        // Revision (1 byte)
        packetOut.write(sid.getRevision());
        // SubAuthorityCount (1 byte)
        packetOut.write(sid.getSubAuthorities().length);
        if (sid.getSidIdentifierAuthority().length > 6) {
            throw new IllegalArgumentException("The IdentifierAuthority can not be larger than 6 bytes");
        }
        // IdentifierAuthority (6 bytes)
        packetOut.write(sid.getSidIdentifierAuthority());
        for (int i = 0; i < sid.getSubAuthorities().length; i++) {
            // SubAuthority (variable * 4 bytes)
            packetOut.writeInt((int) sid.getSubAuthorities()[i]);
        }
    }

}
