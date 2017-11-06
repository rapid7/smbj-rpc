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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

public class LsarLookupSidsWithAcctPrivRpcResponse extends RequestResponse {
    private SID[] sids;
    private int returnValue;

    public SID[] getSids() {
        return sids;
    }

    public int getReturnValue() {
        return returnValue;
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
      /* Rpc Info
       *
       * MajorVer:    05
       * MinorVer:    00
       * PacketType:  02 (Response)
       * Flags:       03
       * PackType:    10000000
       * FragLen:     4000
       * AuthLen:     0000
       * CallId:      05000000
       * AllocHint:   28000000
       * ContextId:   0000
       * CancelCount: 00
       * Rsvd:        00
       *
       * SidCnt:      03000000
       * Ptr:         A0BC1600
       * MaxCnt:      03000000
       *   Ptr1:      98F71600
       *   Ptr2:      20E21600
       *   Ptr3:      F0FD1600
       * Sid1:
       *   NumAuths:  02000000
       *   SID:       01 02 000000000005 20000000 27020000
       * Sid3:
       *   NumAuths:  02000000
       *   SID:       01 02 000000000005 20000000 25020000
       * Sid3:
       *   NumAuths:  02000000
       *   SID:       01 02 000000000005 20000000 20020000
       *
       * Status:      00000000
       */

        final int sidCnt = packetIn.readInt();
        int ptr = packetIn.readInt();// 0 if status == 0xc0000060
        if (sidCnt >= 1) {
            // maxCnt + Reference IDs
            packetIn.fullySkipBytes(4 + 4 * sidCnt);

            sids = new SID[sidCnt];
            for (int i = 0; i < sidCnt; i++) {
                sids[i] = read(packetIn);
            }
        }

        returnValue = packetIn.readInt();
    }

    public SID read(PacketInput packetIn) throws IOException {
        // Skip count
        packetIn.fullySkipBytes(4);
        // Revision (1 byte)
        byte revision = packetIn.readByte();
        // SubAuthorityCount (1 byte)
        int subAuthorityCount = packetIn.readByte();
        // IdentifierAuthority (6 bytes)
        byte[] sidIdentifierAuthority = packetIn.readRawBytes(6);
        // SubAuthority (variable * 4 bytes)
        long[] subAuthorities = new long[subAuthorityCount];
        for (int i = 0; i < subAuthorityCount; i++) {
            subAuthorities[i] = packetIn.readInt();
        }
        return new SID(revision, sidIdentifierAuthority, subAuthorities);
    }

}
