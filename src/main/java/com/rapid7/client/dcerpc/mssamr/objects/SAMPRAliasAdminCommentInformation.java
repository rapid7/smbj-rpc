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
 * SAMPR_ALIAS_ADM_COMMENT_INFORMATION
 * 
 * <b>Alignment: 4</b><pre>
The SAMPR_ALIAS_ADM_COMMENT_INFORMATION structure contains group fields.

     typedef struct SAMPR_ALIAS_ADM_COMMENT_INFORMATION {
       RPC_UNICODE_STRING AdminComment;
     } SAMPR_GROUP_ADM_COMMENT_INFORMATION,
      *PSAMPR_GROUP_ADM_COMMENT_INFORMATION;

For information on each field, see section 2.2.5.1.</pre></blockquote>
@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/0968f7a5-d87c-4fec-85c4-5287a7be6cf4">SAMPR_ALIAS_ADM_COMMENT_INFORMATION</a>
 */

public class SAMPRAliasAdminCommentInformation implements Unmarshallable, Marshallable {
	public SAMPRAliasAdminCommentInformation(SAMPRAliasGeneralInformation generalInformation) {
		adminComment = generalInformation.getAdminComment();
	}
    private RPCUnicodeString.NonNullTerminated adminComment;

    public RPCUnicodeString.NonNullTerminated getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(RPCUnicodeString.NonNullTerminated adminComment) {
        this.adminComment = adminComment;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment = new RPCUnicodeString.NonNullTerminated();
        adminComment.unmarshalPreamble(in);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        adminComment.unmarshalEntity(in);
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING AdminComment;
        adminComment.unmarshalDeferrals(in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAdminComment());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRAliasAdminCommentInformation)) {
            return false;
        }
        SAMPRAliasAdminCommentInformation other = (SAMPRAliasAdminCommentInformation) obj;
        return Objects.equals(getAdminComment(), other.getAdminComment());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_GROUP_ADMINCOMMENT_INFORMATION {getAdminComment:%s}",
                getAdminComment());
    }

	@Override 
	public void marshalPreamble(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Admincomment;
        adminComment.marshalPreamble(out);
	}

	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 4
        out.align(Alignment.FOUR);
        adminComment.marshalEntity(out);
	}
	
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Admincomment;
        adminComment.marshalDeferrals(out);
	}
}
