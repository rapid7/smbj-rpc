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
 *  Documentation from https://msdn.microsoft.com/en-us/library/cc234462.aspx
 *  <h1 class="title">2.2.20 LSAPR_TRANSLATED_NAMES</h1>
 *
 * <p>The LSAPR_TRANSLATED_NAMES structure defines a set of
 * translated names. This is used in the response to a translation request from <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SIDs</a> to names.</p>
 *
 * <dl>
 * <dd>
 * <div><pre> typedef struct _LSAPR_TRANSLATED_NAMES {
 *    [range(0,20480)] unsigned long Entries;
 *    [size_is(Entries)] PLSAPR_TRANSLATED_NAME Names;
 *  } LSAPR_TRANSLATED_NAMES,
 *   *PLSAPR_TRANSLATED_NAMES;
 * </pre></div>
 * </dd></dl>
 *
 * <p><strong>Entries:</strong>  Contains the number of
 * translated names.<a id="Appendix_A_Target_10"></a><a href="https://msdn.microsoft.com/en-us/library/cc234510.aspx#Appendix_A_10">&lt;10&gt;</a></p>
 *
 * <p><strong>Names:</strong>  Contains a set of translated
 * names, as specified in section <a href="https://msdn.microsoft.com/en-us/library/cc234461.aspx">2.2.19</a>. If the Entries
 * field in this structure is not 0, this field MUST be non-NULL. If Entries is 0,
 * this field MUST be ignored.</p>
 *
 *
 */
public class LSAPRTranslatedNames implements Unmarshallable
{
    // <NDR: unsigned long> [range(0,20480)] unsigned long Entries;
    // Only used in marshalling
    // <NDR: pointer[conformant array]> [size_is(Entries)] PLSAPR_TRANSLATED_NAME Names;
    private LSAPRTranslatedName[] names;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // [range(0,20480)] unsigned long Entries;
        // Alignment: 4 - Already aligned
        final int entries = in.readInt();
        // <NDR: pointer[conformant array]> [size_is(Entries)] PLSAPR_TRANSLATED_NAME Names;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            names = new LSAPRTranslatedName[entries];
        else
            names = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (names != null) {
            // MaximumCount: [size_is(Entries)] PLSAPR_TRANSLATED_NAME Names;
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);

            // Entries: [size_is(Entries)] PLSAPR_TRANSLATED_NAME Names;
            for (int i = 0; i < names.length; i++){
                LSAPRTranslatedName lsaprTranslatedName = new LSAPRTranslatedName();
                lsaprTranslatedName.unmarshalPreamble(in);
                names[i] = lsaprTranslatedName;
            }
            for (LSAPRTranslatedName lsaprTranslatedName: names){
                lsaprTranslatedName.unmarshalEntity(in);
            }
            for (LSAPRTranslatedName lsaprTranslatedName: names){
                lsaprTranslatedName.unmarshalDeferrals(in);
            }
        }
    }

    public LSAPRTranslatedName[] getNames() {
        return names;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(names);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPRTranslatedNames)) {
            return false;
        }
        return Arrays.equals(this.names, ((LSAPRTranslatedNames) obj).names);
    }

    @Override
    public String toString() {
        return String.format("LSAPR_TRANSLATED_NAMES{Names:%s}", Arrays.toString(names));
    }
}
