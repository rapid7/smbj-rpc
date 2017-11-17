/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mslsad.objects;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *   typedef struct _LSAPR_REFERENCED_DOMAIN_LIST {
 unsigned long Entries;
 [size_is(Entries)] PLSAPR_TRUST_INFORMATION Domains;
 unsigned long MaxEntries;
 } LSAPR_REFERENCED_DOMAIN_LIST,
 *PLSAPR_REFERENCED_DOMAIN_LIST;

 */
public class LSAPRReferencedDomainList implements Unmarshallable {

    private int entries;
    private LSAPRTrustInformation[] lsaprTrustInformations;
    private long maxEntries;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        // Entries
        entries = in.readInt();

        if (in.readReferentID() != 0) {
            lsaprTrustInformations = new LSAPRTrustInformation[entries];
        }
        else lsaprTrustInformations = null;

        //Max entries
        maxEntries = in.readUnsignedInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in)
        throws IOException {
        // Reading conformant array
        if (lsaprTrustInformations != null)
        {
            in.align(Alignment.FOUR);
            in.readInt(); // Max count

            // LSAPT_TRUST_INFORMATION Domains
            for (int i = 0; i < entries; i++)
            {
                LSAPRTrustInformation lsaprTrustInformation = new LSAPRTrustInformation();
                in.readUnmarshallable(lsaprTrustInformation);
                lsaprTrustInformations[i] = lsaprTrustInformation;
            }
        }
    }
}
