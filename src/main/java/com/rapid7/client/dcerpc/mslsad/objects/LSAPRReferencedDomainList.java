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
    // <NDR: unsigned long> unsigned long Entries;
    // Only used in marshalling
    // <NDR: pointer[conformant array]> [size_is(Entries)] PLSAPR_TRUST_INFORMATION Domains;
    private LSAPRTrustInformation[] domains;
    // <NDR: unsigned long> unsigned long MaxEntries;
    // Ignored

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned long> unsigned long Entries;
        // Alignment: 4 - Already aligned
        final int entries = in.readInt();
        // <NDR: pointer[conformant array]> [size_is(Entries)] PLSAPR_TRUST_INFORMATION Domains;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            domains = new LSAPRTrustInformation[entries];
        else
            domains = null;
        // <NDR: unsigned long> unsigned long MaxEntries;
        // Alignment: 4 - Already aligned
        in.fullySkipBytes(4);
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // Reading conformant array
        if (domains != null) {
            // MaximumSize: [size_is(Entries)] PLSAPR_TRUST_INFORMATION Domains;
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            // [size_is(Entries)] PLSAPR_TRUST_INFORMATION Domains;
            for (int i = 0; i < domains.length; i++) {
                final LSAPRTrustInformation lsaprTrustInformation = new LSAPRTrustInformation();
                lsaprTrustInformation.unmarshalPreamble(in);
                domains[i] = lsaprTrustInformation;
            }
            for (LSAPRTrustInformation lsaprTrustInformation : domains) {
                lsaprTrustInformation.unmarshalEntity(in);
            }
            for (LSAPRTrustInformation lsaprTrustInformation : domains) {
                lsaprTrustInformation.unmarshalDeferrals(in);
            }
        }
    }

    public LSAPRTrustInformation[] getDomains() {
        return domains;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.domains);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPRReferencedDomainList)) {
            return false;
        }
        return Arrays.equals(this.domains, ((LSAPRReferencedDomainList) obj).domains);
    }

    @Override
    public String toString() {
        return String.format("LSAPRReferencedDomainList{domains:%s}", Arrays.toString(domains));
    }
}
