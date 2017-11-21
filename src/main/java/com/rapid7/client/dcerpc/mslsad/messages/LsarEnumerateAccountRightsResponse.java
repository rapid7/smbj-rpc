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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

public class LsarEnumerateAccountRightsResponse extends RequestResponse {
    // [out] PLSAPR_USER_RIGHT_SET UserRights
    private String[] privNames;

    public String[] getPrivNames() {
        return privNames;
    }

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
      /*
       * Rpc Info
       *
       * MajorVer: 05
       * MinorVer: 00
       * PacketType: 02 (Response)
       * Flags: 03R
       * PackType: 10000000
       * FragLen: EC00
       * AuthLen: 0000
       * CallId: 03000000
       * AllocHint: D4000000
       * ContextId: 0000
       * CancelCount: 00
       * Rsvd: 00
       *
       * Count: 11000000
       * Ptr: 80DC1600
       * Count: 11000000
       * UniHdr1: 2600 2800985A1700
       * UniHdr2: 2200 2400 68F41600 ...
       * UniStr1: 14000000 00000000 13000000 53006500530065....
       * SeSecurityPrivilege ...
       * Status: 00000000
       */

        final int privCnt = packetIn.readInt();
        int ptr = packetIn.readInt();// 0 if status == 0xc0000034
        if (privCnt >= 1) {
            // MaxCnt(4 bytes) + UnitHdr(8 bytes)* privCnt
            packetIn.fullySkipBytes(4 + 8 * privCnt);

            privNames = new String[privCnt];
            for (int i = 0; i < privCnt; i++) {
                privNames[i] = packetIn.readString(true);
            }
        }
    }
}


