/*
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
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

public class LsarEnumerateAccountRightsResponse extends RequestResponse {
    // <NDR: pointer[conformant array]> [out] PLSAPR_USER_RIGHT_SET UserRights
    private RPCUnicodeString.NonNullTerminated[] privNames;

    public RPCUnicodeString.NonNullTerminated[] getPrivNames() {
        return privNames;
    }

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
        final int privCnt = packetIn.readInt();
        // <NDR: pointer[conformant array]> [out] PLSAPR_USER_RIGHT_SET UserRights
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0) {
            this.privNames = new RPCUnicodeString.NonNullTerminated[privCnt];
            // Maximum Count: <NDR: pointer[conformant array]> [out] PLSAPR_USER_RIGHT_SET UserRights
            // Alignment: 4 - Already aligned
            packetIn.fullySkipBytes(4);
            // Entries: <NDR: pointer[conformant array]> [out] PLSAPR_USER_RIGHT_SET UserRights
            for (int i = 0; i < this.privNames.length; i++) {
                this.privNames[i] = new RPCUnicodeString.NonNullTerminated();
                this.privNames[i].unmarshalPreamble(packetIn);
            }
            for (final RPCUnicodeString.NonNullTerminated privName : this.privNames) {
                privName.unmarshalEntity(packetIn);
            }
            for (final RPCUnicodeString.NonNullTerminated privName : this.privNames) {
                privName.unmarshalDeferrals(packetIn);
            }
        } else {
            this.privNames = null;
        }
    }
}
