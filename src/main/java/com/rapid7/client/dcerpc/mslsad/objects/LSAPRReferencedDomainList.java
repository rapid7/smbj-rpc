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
import java.util.Arrays;
import java.util.List;

/**
 *   Documentation from https://msdn.microsoft.com/en-us/library/cc234453.aspx
 *   <h1 class="title">2.2.12 LSAPR_REFERENCED_DOMAIN_LIST</h1>
 *
 *  <p>The LSAPR_REFERENCED_DOMAIN_LIST structure contains
 *  information about the <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domains</a>
 *  referenced in a lookup operation.</p>
 *
 *  <dl>
 *  <dd>
 *  <div><pre> typedef struct _LSAPR_REFERENCED_DOMAIN_LIST {
 *     unsigned long Entries;
 *     [size_is(Entries)] PLSAPR_TRUST_INFORMATION Domains;
 *     unsigned long MaxEntries;
 *   } LSAPR_REFERENCED_DOMAIN_LIST,
 *    *PLSAPR_REFERENCED_DOMAIN_LIST;
 *  </pre></div>
 *  </dd></dl>
 *
 *  <p><strong>Entries:</strong>  Contains the number of
 *  domains referenced in the lookup operation.</p>
 *
 *  <p><strong>Domains:</strong>  Contains a set of
 *  structures that identify domains. If the <strong>Entries</strong> field in this structure
 *  is not 0, this field MUST be non-NULL. If <strong>Entries</strong> is 0, this field MUST
 *  be ignored.</p>
 *
 *  <p><strong>MaxEntries:</strong>  This field MUST be
 *  ignored. The content is unspecified, and no requirements are placed on its
 *  value since it is never used.</p>
 *
 *
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

    public LSAPRTrustInformation[] getLsaprTrustInformations() {
        return lsaprTrustInformations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LSAPRReferencedDomainList that = (LSAPRReferencedDomainList) o;

        if (entries != that.entries) return false;
        if (maxEntries != that.maxEntries) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(lsaprTrustInformations, that.lsaprTrustInformations);
    }

    @Override
    public int hashCode() {
        int result = entries;
        result = 31 * result + Arrays.hashCode(lsaprTrustInformations);
        result = 31 * result + (int) (maxEntries ^ (maxEntries >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "LSAPRReferencedDomainList{" +
                "lsaprTrustInformations=" + Arrays.toString(lsaprTrustInformations) +
                '}';
    }
}
