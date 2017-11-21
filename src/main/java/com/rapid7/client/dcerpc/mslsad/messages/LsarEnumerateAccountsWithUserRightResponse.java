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
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.objects.RPCSID;

public class LsarEnumerateAccountsWithUserRightResponse extends RequestResponse {
    // [out] PLSAPR_ACCOUNT_ENUM_BUFFER EnumerationBuffer
    private RPCSID[] sids;

    public RPCSID[] getSids() {
        return sids;
    }

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
        final int entriesRead = packetIn.readInt();
        if (packetIn.readReferentID() != 0) {
            this.sids = new RPCSID[entriesRead];
            // MaximumCount
            packetIn.fullySkipBytes(4);
            // Entries : References
            for (int i = 0; i < this.sids.length; i++) {
                if (packetIn.readReferentID() != 0)
                    this.sids[i] = new RPCSID();
            }
            for (final RPCSID sid : this.sids) {
                if (sid != null)
                    packetIn.readUnmarshallable(sid);
            }
        } else {
            if (entriesRead > 0) {
                throw new UnmarshalException(String.format("Information null but EntriesRead != 0: %d", entriesRead));
            }
            this.sids = null;
        }
    }
}
