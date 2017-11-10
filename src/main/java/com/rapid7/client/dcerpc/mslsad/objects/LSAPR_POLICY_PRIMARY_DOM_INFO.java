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
import com.rapid7.client.dcerpc.objects.RPC_SID;
import com.rapid7.client.dcerpc.objects.RPC_UNICODE_STRING;

/**
 * Structure: LSAPR_POLICY_PRIMARY_DOM_INFO
 *  typedef struct _LSAPR_POLICY_PRIMARY_DOM_INFO {
 *      RPC_UNICODE_STRING Name;
 *      PRPC_SID Sid;
 *  } LSAPR_POLICY_PRIMARY_DOM_INFO,
 *  *PLSAPR_POLICY_PRIMARY_DOM_INFO;
 *
 * Alignment: 4 (Max[4,4])
 *      RPC_UNICODE_STRING Name;: 4
 *      PRPC_SID Sid;: 4
 */
public class LSAPR_POLICY_PRIMARY_DOM_INFO implements Unmarshallable {

    // <NDR: struct> RPC_UNICODE_STRING Name;
    private RPC_UNICODE_STRING name;
    // <NDR: pointer> PRPC_SID Sid;
    private RPC_SID sid;

    public RPC_UNICODE_STRING getName() {
        return name;
    }

    public void setName(RPC_UNICODE_STRING name) {
        this.name = name;
    }

    public RPC_SID getSid() {
        return sid;
    }

    public void setSid(RPC_SID sid) {
        this.sid = sid;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name = RPC_UNICODE_STRING.of(false);
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
            sid = new RPC_SID();
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
        } else if (! (obj instanceof LSAPR_POLICY_PRIMARY_DOM_INFO)) {
            return false;
        }
        LSAPR_POLICY_PRIMARY_DOM_INFO other = (LSAPR_POLICY_PRIMARY_DOM_INFO) obj;
        return Objects.equals(getName(), other.getName())
                && Objects.equals(getSid(), other.getSid());
    }

    @Override
    public String toString() {
        return String.format("LSAPR_POLICY_PRIMARY_DOM_INFO{Name:%s, Sid:%s}", getName(), getSid());
    }
}
