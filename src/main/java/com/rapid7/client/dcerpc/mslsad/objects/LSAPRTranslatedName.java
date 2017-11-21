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

/**
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
public class LSAPRTranslatedName implements Unmarshallable
{
    private int use;
    private RPCUnicodeString.NonNullTerminated name;
    private long domainIndex;

    @Override
    public void unmarshalPreamble(PacketInput in)
        throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in)
        throws IOException {
        in.align(Alignment.FOUR);
        use = in.readInt();
        name = new RPCUnicodeString.NonNullTerminated();
        name.unmarshalEntity(in);
        domainIndex = in.readUnsignedInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in)
        throws IOException {
        name.unmarshalDeferrals(in);
    }

    public int getUse() {
        return use;
    }

    public RPCUnicodeString getName() { return name;
    }

    public long getDomainIndex() {
        return domainIndex;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LSAPRTranslatedName that = (LSAPRTranslatedName)o;

        return use == that.use && domainIndex == that.domainIndex &&
            (name != null ? name.equals(that.name) : that.name == null);
    }

    @Override public int hashCode()
    {
        int result = use;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int)(domainIndex ^ (domainIndex >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "LSAPRTranslatedSID{" +
                "use=" + use +
                ", name=" + name +
                ", domainIndex=" + domainIndex +
                '}';
    }
}
