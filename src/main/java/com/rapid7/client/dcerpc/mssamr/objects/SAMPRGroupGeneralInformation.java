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

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>Alignment: 4</b><pre>
 *     RPC_UNICODE_STRING Name;: 4
 *     unsigned long Attributes;: 4
 *     unsigned long MemberCount;: 4
 *     RPC_UNICODE_STRING AdminComment;: 4</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc245583.aspx">SAMPR_GROUP_GENERAL_INFORMATION</a>
 * <blockquote><pre>The SAMPR_GROUP_GENERAL_INFORMATION structure contains group fields.
 *      typedef struct _SAMPR_GROUP_GENERAL_INFORMATION {
 *          RPC_UNICODE_STRING Name;
 *          unsigned long Attributes;
 *          unsigned long MemberCount;
 *          RPC_UNICODE_STRING AdminComment;
 *      } SAMPR_GROUP_GENERAL_INFORMATION,
 *      *PSAMPR_GROUP_GENERAL_INFORMATION;
 *  For information on each field, see section 2.2.5.1.</pre></blockquote>
 */

public class SAMPRGroupGeneralInformation implements Unmarshallable, Marshallable {
    // <NDR: struct> RPC_UNICODE_STRING Name;
    private RPCUnicodeString.NonNullTerminated name;
    // <NDR: unsigned long> unsigned long Attributes;
    // This is a bit field so representing as an int is fine
    private int attributes;
    // <NDR: unsigned long> unsigned long MemberCount;
    private long memberCount;
    // <NDR: struct> RPC_UNICODE_STRING AdminComment;
    private RPCUnicodeString.NonNullTerminated adminComment;

    public RPCUnicodeString.NonNullTerminated getName() {
        return name;
    }

    public void setName(RPCUnicodeString.NonNullTerminated name) {
        this.name = name;
    }

    public int getAttributes() {
        return attributes;
    }

    public void setAttributes(int attributes) {
        this.attributes = attributes;
    }

    public long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    public RPCUnicodeString.NonNullTerminated getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(RPCUnicodeString.NonNullTerminated adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name = new RPCUnicodeString.NonNullTerminated();
        name.unmarshalPreamble(in);
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment = new RPCUnicodeString.NonNullTerminated();
        adminComment.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.unmarshalEntity(in);
        // <NDR: unsigned long> unsigned long Attributes;
        in.align(Alignment.FOUR);
        attributes = in.readInt();
        // <NDR: unsigned long> unsigned long MemberCount;
        // Alignment: 4 - Already aligned
        memberCount = in.readUnsignedInt();
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment.unmarshalEntity(in);
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.unmarshalDeferrals(in);
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment.unmarshalDeferrals(in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAttributes(), getMemberCount(), getAdminComment());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRGroupGeneralInformation)) {
            return false;
        }
        SAMPRGroupGeneralInformation other = (SAMPRGroupGeneralInformation) obj;
        return Objects.equals(getName(), other.getName())
                && Objects.equals(getAttributes(), other.getAttributes())
                && Objects.equals(getMemberCount(), other.getMemberCount())
                && Objects.equals(getAdminComment(), other.getAdminComment());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_GROUP_GENERAL_INFORMATION {Name:%s,Attributes:%d,MemberCount:%d,AdminComment:%s}",
                getName(), getAttributes(), getMemberCount(), getAdminComment());
    }

	@Override 
	public void marshalPreamble(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.marshalPreamble(out);
        // <NDR: struct> RPC_UNICODE_STRING Admincomment;
        adminComment.marshalPreamble(out);
	}

	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 4
        out.align(Alignment.FOUR);
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.marshalEntity(out);
        // <NDR: unsigned long> unsigned long Attributes;
        out.align(Alignment.FOUR);
        out.writeInt(attributes);
        // <NDR: unsigned long> unsigned long MemberCount;
        // Alignment: 4 - Already aligned
        out.writeInt(attributes);
        // <NDR: struct> RPC_UNICODE_STRING Admincomment;
        adminComment.marshalEntity(out);
	}
	
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.marshalDeferrals(out);
        // <NDR: struct> RPC_UNICODE_STRING Admincomment;
        adminComment.marshalDeferrals(out);
	}
}
