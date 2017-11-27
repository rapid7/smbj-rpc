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
import java.util.Objects;

/**
 * <b>Alignment: 4</b>
 * Documentation from https://msdn.microsoft.com/en-us/library/cc234455.aspx
 *  <h1 class="title">2.2.14 LSA_TRANSLATED_SID</h1>
 *
 *  <p>The LSA_TRANSLATED_SID structure contains information about
 *  a <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_f3ef2572-95cf-4c5c-b3c9-551fd648f409">security principal</a>
 *  after translation from a name to a <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SID</a>. This structure MUST
 *  always be accompanied by an <a href="https://msdn.microsoft.com/en-us/library/cc234453.aspx">LSAPR_REFERENCED_DOMAIN_LIST</a>
 *  structure when <strong>DomainIndex</strong> is not -1.</p>
 *
 *  <dl>
 *  <dd>
 *  <div><pre> typedef struct _LSA_TRANSLATED_SID {
 *     SID_NAME_USE Use;
 *     unsigned long RelativeId;
 *     long DomainIndex;
 *   } LSA_TRANSLATED_SID,
 *    *PLSA_TRANSLATED_SID;
 *  </pre></div>
 *  </dd></dl>
 *
 *  <p><strong>Use:</strong>  Defines the type of the
 *  security principal, as specified in section <a href="https://msdn.microsoft.com/en-us/library/cc234454.aspx">2.2.13</a>.</p>
 *
 *  <p><strong>RelativeId:</strong>  Contains the <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_df3d0b61-56cd-4dac-9402-982f1fedc41c">relative identifier (RID)</a>
 *  of the security principal with respect to its <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domain</a>.</p>
 *
 *  <p><strong>DomainIndex:</strong>  Contains the index
 *  into an LSAPR_REFERENCED_DOMAIN_LIST structure that specifies the domain that
 *  the security principal is in. A <strong>DomainIndex</strong> value of -1 MUST be used to
 *  specify that there are no corresponding domains. Other negative values MUST NOT
 *  be returned.</p>
 *
 */
public class LSAPRTranslatedSID implements Unmarshallable
{
    // <NDR: short> SID_NAME_USE Use;
    private short use;
    // <NDR: unsigned long> unsigned long RelativeId;
    private long relativeId;
    // <NDR: long> long DomainIndex;
    private int domainIndex;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: short> SID_NAME_USE Use;
        // Alignment: 2 - Already aligned
        this.use = in.readShort();
        // <NDR: unsigned long> unsigned long RelativeId;
        // Alignment: 4
        in.fullySkipBytes(2);
        this.relativeId = in.readUnsignedInt();
        // <NDR: long> long DomainIndex;
        // Alignment: 4 - Already aligned
        domainIndex = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // No deferrals
    }

    public int getUse() {
        return use;
    }

    public long getRelativeId() {
        return relativeId;
    }

    public long getDomainIndex() {
        return domainIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPRTranslatedSID)) {
            return false;
        }

        final LSAPRTranslatedSID other = (LSAPRTranslatedSID) obj;
        return Objects.equals(this.use, other.use)
                && Objects.equals(this.relativeId, other.relativeId)
                && Objects.equals(this.domainIndex, other.domainIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.use, this.relativeId, this.domainIndex);
    }

    @Override
    public String toString() {
        return String.format("LSA_TRANSLATED_SID{Use:%d,RelativeId:%d,DomainIndex;%d}",
                this.use, this.relativeId, this.domainIndex);
    }
}
