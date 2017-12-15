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
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import java.io.IOException;
import java.util.Objects;

/**
 * <b>Alignment: 4</b>
 * Documentation from https://msdn.microsoft.com/en-us/library/cc234461.aspx
 *
 * <h1 class="title">2.2.19 LSAPR_TRANSLATED_NAME</h1>
 *
 * <p>The LSAPR_TRANSLATED_NAME structure contains information
 * about a <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_f3ef2572-95cf-4c5c-b3c9-551fd648f409">security principal</a>,
 * along with the human-readable identifier for that security principal. This
 * structure MUST always be accompanied by an <a href="https://msdn.microsoft.com/en-us/library/cc234453.aspx">LSAPR_REFERENCED_DOMAIN_LIST</a>
 * structure when <strong>DomainIndex</strong> is not -1, which contains the <a href="https://msdn.microsoft.com/en-us/library/cc234422.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domain</a> information for the
 * security principals.</p>
 *
 * <dl>
 * <dd>
 * <div><pre> typedef struct _LSAPR_TRANSLATED_NAME {
 *    SID_NAME_USE Use;
 *    RPC_UNICODE_STRING Name;
 *    long DomainIndex;
 *  } LSAPR_TRANSLATED_NAME,
 *   *PLSAPR_TRANSLATED_NAME;
 * </pre></div>
 * </dd></dl>
 *
 * <p><strong>Use:</strong>  Defines the type of the
 * security principal, as specified in section <a href="https://msdn.microsoft.com/en-us/library/cc234454.aspx">2.2.13</a>.</p>
 *
 * <p><strong>Name:</strong>  Contains the name of the
 * security principal, with syntaxes described in section <a href="https://msdn.microsoft.com/en-us/library/cc234492.aspx">3.1.4.5</a>. The
 * RPC_UNICODE_STRING structure is defined in <a href="https://msdn.microsoft.com/en-us/library/cc230273.aspx">[MS-DTYP]</a>
 * section <a href="https://msdn.microsoft.com/en-us/library/cc230365.aspx">2.3.10</a>.</p>
 *
 * <p><strong>DomainIndex:</strong>  Contains the index
 * into the corresponding LSAPR_REFERENCED_DOMAIN_LIST structure that specifies
 * the domain that the security principal is in. A <strong>DomainIndex</strong> value of -1
 * MUST be used to specify that there are no corresponding domains. Other negative
 * values MUST NOT be used.</p>
 *
 */
public class LSAPRTranslatedName implements Unmarshallable {
    // <NDR: short> SID_NAME_USE Use;
    private short use;
    // <NDR: struct> RPC_UNICODE_STRING Name;
    private RPCUnicodeString.NonNullTerminated name;
    // <NDR: long> long DomainIndex
    private int domainIndex;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        name = new RPCUnicodeString.NonNullTerminated();
        name.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: short> SID_NAME_USE Use;
        // Alignment: 2 - Already aligned
        use = in.readShort();
        // <NDR: struct> RPC_UNICODE_STRING Name;
        // Alignment: 4
        in.fullySkipBytes(2);
        name.unmarshalEntity(in);
        // <NDR: long> long DomainIndex
        in.align(Alignment.FOUR);
        domainIndex = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.unmarshalDeferrals(in);
    }

    public int getUse() {
        return use;
    }

    public RPCUnicodeString getName() {
        return name;
    }

    public int getDomainIndex() {
        return domainIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPRTranslatedName)) {
            return false;
        }
        final LSAPRTranslatedName other = (LSAPRTranslatedName) obj;
        return this.use == other.use
                && Objects.equals(this.name, other.name)
                && this.domainIndex == other.domainIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.use, this.name, this.domainIndex);
    }

    @Override
    public String toString() {
        return String.format("LSAPR_TRANSLATED_NAME{Use:%d,Name:%s,DomainIndex:%d",
                this.use, this.name, this.domainIndex);
    }
}
