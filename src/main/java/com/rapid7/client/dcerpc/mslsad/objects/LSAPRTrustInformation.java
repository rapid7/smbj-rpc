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

/**
 *  Documentation from https://msdn.microsoft.com/en-us/library/cc234259.aspx
 *  <h1 class="title">2.2.7.1 LSAPR_TRUST_INFORMATION</h1>
 *
 *
 *  <p>The LSAPR_TRUST_INFORMATION structure identifies a <a href="https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_b0276eb2-4e65-4cf1-a718-e0920a614aca">domain</a>.</p>
 *
 *  <dl>
 *  <dd>
 *  <div><pre> typedef struct _LSAPR_TRUST_INFORMATION {
 *     RPC_UNICODE_STRING Name;
 *     PRPC_SID Sid;
 *   } LSAPR_TRUST_INFORMATION,
 *    *PLSAPR_TRUST_INFORMATION;
 *  </pre></div>
 *  </dd></dl>
 *
 *  <p><strong>Name:</strong>  This field contains a name
 *  for the domain that is subject to the restrictions of a NetBIOS name, as
 *  specified in <a href="https://go.microsoft.com/fwlink/?LinkId=90266">[RFC1088]</a>.
 *  This value SHOULD be used (by implementations external to this protocol) to
 *  identify the domain via the NetBIOS, as specified in [RFC1088].</p>
 *
 *  <p><strong>Sid:</strong>  The <a href="https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_83f2020d-0804-4840-a5ac-e06439d50f8d">SID</a> of the domain. This
 *  field MUST NOT be NULL.</p>
 *
 */
public class LSAPRTrustInformation implements Unmarshallable
{
    private RPCUnicodeString.NonNullTerminated name;
    private RPCSID sid;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        name = new RPCUnicodeString.NonNullTerminated();
        name.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        name.unmarshalEntity(in);
        in.align(Alignment.FOUR);

        if (in.readReferentID() != 0){
            sid = new RPCSID();
        } else sid = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        name.unmarshalDeferrals(in);
        if (sid != null){
            in.readUnmarshallable(sid);
        }
    }

    public RPCUnicodeString.NonNullTerminated getName() {
        return name;
    }

    public RPCSID getSid() {
        return sid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LSAPRTrustInformation that = (LSAPRTrustInformation) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return sid != null ? sid.equals(that.sid) : that.sid == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sid != null ? sid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LSAPRTrustInformation{" +
                "name=" + name +
                ", sid=" + sid +
                '}';
    }
}
