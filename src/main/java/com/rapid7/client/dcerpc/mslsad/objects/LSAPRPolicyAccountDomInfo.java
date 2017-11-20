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
 *      RPC_UNICODE_STRING DomainName;: 4
 *      PRPC_SID DomainSid;: 4</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc234266.aspx?f=255&MSPPError=-2147217396">LSAPR_POLICY_ACCOUNT_DOM_INFO</a>
 * <blockquote><pre>
 * The LSAPR_POLICY_ACCOUNT_DOM_INFO structure contains information about the server's account domain. The following structure corresponds to the PolicyAccountDomainInformation and PolicyLocalAccountDomainInformation information classes.
 *      typedef struct _LSAPR_POLICY_ACCOUNT_DOM_INFO {
 *          RPC_UNICODE_STRING DomainName;
 *          PRPC_SID DomainSid;
 *      } LSAPR_POLICY_ACCOUNT_DOM_INFO,
 *      *PLSAPR_POLICY_ACCOUNT_DOM_INFO;
 *
 *  DomainName: This field contains a name for the account domain that is subjected to the restrictions of a NetBIOS name, as specified in [RFC1088]. This value SHOULD be used (by implementations external to this protocol) to identify the domain  via the NetBIOS API, as specified in [RFC1088].
 *  DomainSid: The SID of the account domain. This field MUST NOT be NULL.</pre></blockquote>
 */
public class LSAPRPolicyAccountDomInfo implements Unmarshallable {

    // <NDR: struct> RPC_UNICODE_STRING DomainName;
    private RPCUnicodeString.NonNullTerminated domainName;
    // <NDR: pointer> PRPC_SID DomainSid;
    private RPCSID domainSid;

    public RPCUnicodeString.NonNullTerminated getDomainName() {
        return domainName;
    }

    public void setDomainName(RPCUnicodeString.NonNullTerminated domainName) {
        this.domainName = domainName;
    }

    public RPCSID getDomainSid() {
        return domainSid;
    }

    public void setDomainSid(RPCSID domainSid) {
        this.domainSid = domainSid;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        domainName = new RPCUnicodeString.NonNullTerminated();
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
            domainSid = new RPCSID();
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
        } else if (! (obj instanceof LSAPRPolicyAccountDomInfo)) {
            return false;
        }
        LSAPRPolicyAccountDomInfo other = (LSAPRPolicyAccountDomInfo) obj;
        return Objects.equals(getDomainName(), other.getDomainName())
                && Objects.equals(getDomainSid(), other.getDomainSid());
    }

    @Override
    public String toString() {
        return String.format("LSAPR_POLICY_ACCOUNT_DOM_INFO{DomainName:%s, DomainSid:%s}", getDomainName(), getDomainSid());
    }
}
