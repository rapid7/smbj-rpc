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

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>Alignment: 4</b> (Max[4,4])<pre>
 *      RPC_UNICODE_STRING Name;: 4
 *      PRPC_SID Sid;: 4</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc234265.aspx">LSAPR_POLICY_PRIMARY_DOM_INFO</a>
 * <blockquote><pre>
 * The LSAPR_POLICY_PRIMARY_DOM_INFO structure defines the server's primary domain.
 *  The following structure corresponds to the PolicyPrimaryDomainInformation information class.
 *      typedef struct _LSAPR_POLICY_PRIMARY_DOM_INFO {
 *          RPC_UNICODE_STRING Name;
 *          PRPC_SID Sid;
 *      } LSAPR_POLICY_PRIMARY_DOM_INFO,
 *      *PLSAPR_POLICY_PRIMARY_DOM_INFO;
 *  Name: This field contains a name for the primary domain that is subject to the restrictions of a NetBIOS name, as specified in [RFC1088]. The value SHOULD be used (by implementations external to this protocol) to identify the domain via the NetBIOS API, as specified in [RFC1088].
 *  Sid: The SID of the primary domain.
 * </pre></blockquote>
 */
public class LSAPRPolicyPrimaryDomInfo implements Unmarshallable {

    // <NDR: struct> RPC_UNICODE_STRING Name;
    private RPCUnicodeString.NonNullTerminated name;
    // <NDR: pointer> PRPC_SID Sid;
    private RPCSID sid;

    public RPCUnicodeString.NonNullTerminated getName() {
        return name;
    }

    public void setName(RPCUnicodeString.NonNullTerminated name) {
        this.name = name;
    }

    public RPCSID getSid() {
        return sid;
    }

    public void setSid(RPCSID sid) {
        this.sid = sid;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name = new RPCUnicodeString.NonNullTerminated();
        name.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.unmarshalEntity(in);
        // <NDR: pointer> PRPC_SID Sid;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0) {
            sid = new RPCSID();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        name.unmarshalDeferrals(in);
        if (sid != null) {
            // <NDR: struct>
            in.readUnmarshallable(sid);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSid());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPRPolicyPrimaryDomInfo)) {
            return false;
        }
        LSAPRPolicyPrimaryDomInfo other = (LSAPRPolicyPrimaryDomInfo) obj;
        return Objects.equals(getName(), other.getName())
                && Objects.equals(getSid(), other.getSid());
    }

    @Override
    public String toString() {
        return String.format("LSAPR_POLICY_PRIMARY_DOM_INFO{Name:%s, Sid:%s}", getName(), getSid());
    }
}
