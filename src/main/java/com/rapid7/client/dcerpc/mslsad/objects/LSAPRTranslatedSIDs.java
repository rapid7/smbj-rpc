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
import java.io.IOException;
import java.util.Arrays;

/**
 *  Documentation from https://msdn.microsoft.com/en-us/library/cc234457.aspx
 * <h1 class="title">2.2.15 LSAPR_TRANSLATED_SIDS</h1>
 *
 *  <p>The LSAPR_TRANSLATED_SIDS structure defines a set of
 *  translated <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SIDs</a>.</p>
 *
 *  <dl>
 *  <dd>
 *  <div><pre> typedef struct _LSAPR_TRANSLATED_SIDS {
 *     [range(0,1000)] unsigned long Entries;
 *     [size_is(Entries)] PLSA_TRANSLATED_SID Sids;
 *   } LSAPR_TRANSLATED_SIDS,
 *    *PLSAPR_TRANSLATED_SIDS;
 *  </pre></div>
 *  </dd></dl>
 *
 *  <p><strong>Entries:</strong>  Contains the number of
 *  translated SIDs.<a id="Appendix_A_Target_7"></a><a href="https://msdn.microsoft.com/en-us/library/cc234510.aspx#Appendix_A_7">&lt;7&gt;</a></p>
 *
 *  <p><strong>Sids:</strong>  Contains a set of structures
 *  that contain translated SIDs. If the Entries field in this structure is not 0,
 *  this field MUST be non-NULL. If Entries is 0, this field MUST be NULL.</p>
 *
 */
public class LSAPRTranslatedSIDs implements Unmarshallable {
    // <NDR: unsigned long> [range(0,1000)] unsigned long Entries;
    // Only considered during marshalling
    // <NDR: pointer[conformant array]> [size_is(Entries)] PLSA_TRANSLATED_SID Sids;
    private LSAPRTranslatedSID[] sids;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned long> [range(0,1000)] unsigned long Entries;
        // Alignment: 4 - Already aligned
        final int entries = in.readInt();
        // <NDR: pointer[conformant array]> [size_is(Entries)] PLSA_TRANSLATED_SID Sids;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            sids = new LSAPRTranslatedSID[entries];
        else
            sids = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (sids != null) {
            // MaximumCount: [size_is(Entries)] PLSA_TRANSLATED_SID Sids;
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            
            for (int i = 0; i < sids.length; i++){
                final LSAPRTranslatedSID lsaprTranslatedSID = new LSAPRTranslatedSID();
                lsaprTranslatedSID.unmarshalPreamble(in);
                sids[i] = lsaprTranslatedSID;
            }
            for (LSAPRTranslatedSID lsaprTranslatedSID : sids) {
                lsaprTranslatedSID.unmarshalEntity(in);
            }
            for (LSAPRTranslatedSID lsaprTranslatedSID : sids) {
                lsaprTranslatedSID.unmarshalDeferrals(in);
            }
        }
    }

    public LSAPRTranslatedSID[] getSIDs() {
        return sids;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPRTranslatedSIDs)) {
            return false;
        }
        return Arrays.equals(this.sids, ((LSAPRTranslatedSIDs) obj).sids);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.sids);
    }

    @Override
    public String toString() {
        return String.format("LSAPR_TRANSLATED_SIDS{Sids:%s", Arrays.toString(this.sids));
    }
}
