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
 * Structure: LSAPR_POLICY_ACCOUNT_DOM_INFO
 * <pre>
 *      typedef struct _LSAPR_POLICY_ACCOUNT_DOM_INFO {
 *          RPC_UNICODE_STRING DomainName;
 *          PRPC_SID DomainSid;
 *      } LSAPR_POLICY_ACCOUNT_DOM_INFO,
 *      *PLSAPR_POLICY_ACCOUNT_DOM_INFO;
 * </pre>
 * Alignment: 4 (Max[4,4])
 *      RPC_UNICODE_STRING DomainName;: 4
 *      PRPC_SID DomainSid;: 4
 */
public class LSAPR_POLICY_ACCOUNT_DOM_INFO implements Unmarshallable {

    // <NDR: struct> RPC_UNICODE_STRING DomainName;
    private RPC_UNICODE_STRING domainName;
    // <NDR: pointer> PRPC_SID DomainSid;
    private RPC_SID domainSid;

    public RPC_UNICODE_STRING getDomainName() {
        return domainName;
    }

    public void setDomainName(RPC_UNICODE_STRING domainName) {
        this.domainName = domainName;
    }

    public RPC_SID getDomainSid() {
        return domainSid;
    }

    public void setDomainSid(RPC_SID domainSid) {
        this.domainSid = domainSid;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        domainName = RPC_UNICODE_STRING.of(false);
        domainName.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: struct> RPC_UNICODE_STRING DomainName;
        domainName.unmarshalEntity(in);
        // <NDR: pointer> PRPC_SID DomainSid;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0) {
            domainSid = new RPC_SID();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        domainName.unmarshalDeferrals(in);
        if (domainSid != null) {
            // <NDR: struct> RPC_SID DomainSid;
            in.readUnmarshallable(domainSid);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomainName(), getDomainSid());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof LSAPR_POLICY_ACCOUNT_DOM_INFO)) {
            return false;
        }
        LSAPR_POLICY_ACCOUNT_DOM_INFO other = (LSAPR_POLICY_ACCOUNT_DOM_INFO) obj;
        return Objects.equals(getDomainName(), other.getDomainName())
                && Objects.equals(getDomainSid(), other.getDomainSid());
    }

    @Override
    public String toString() {
        return String.format("LSAPR_POLICY_ACCOUNT_DOM_INFO{DomainName:%s, DomainSid:%s}", getDomainName(), getDomainSid());
    }
}
