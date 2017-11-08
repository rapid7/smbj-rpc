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
 *  typedef struct _LSAPR_POLICY_PRIMARY_DOM_INFO {
 *      RPC_UNICODE_STRING Name;
 *      PRPC_SID Sid;
 *  } LSAPR_POLICY_PRIMARY_DOM_INFO,
 *  *PLSAPR_POLICY_PRIMARY_DOM_INFO;
 */
public class LSAPR_POLICY_PRIMARY_DOM_INFO implements Unmarshallable {

    // <NDR: struct> RPC_UNICODE_STRING Name;
    private RPC_UNICODE_STRING name;
    // <NDR: *struct> PRPC_SID Sid;
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
    public Alignment getAlignment() {
        // RPC_UNICODE_STRING: 4
        // PRPC_SID: 4
        return Alignment.FOUR;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        name = RPC_UNICODE_STRING.of(false);
        name.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        name.unmarshalEntity(in);
        // RPC_UNICODE_STRING.unmarshalEntity reads exactly 4 bytes, no need for alignment
        if (in.readReferentID() != 0) {
            sid = new RPC_SID();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        name.unmarshalDeferrals(in);
        // RPC_UNICODE_STRING.unmarshalDeferrals writes a variable number of bytes, align before continuing
        if (sid != null) {
            in.align(sid.getAlignment());
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
