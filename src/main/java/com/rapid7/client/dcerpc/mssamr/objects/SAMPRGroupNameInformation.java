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


public class SAMPRGroupNameInformation implements Unmarshallable, Marshallable {
	public SAMPRGroupNameInformation(SAMPRGroupGeneralInformation generalInformation) {
		name = generalInformation.getName();
	}
    // <NDR: struct> RPC_UNICODE_STRING Name;
    private RPCUnicodeString.NonNullTerminated name;

    public RPCUnicodeString.NonNullTerminated getName() {
        return name;
    }

    public void setName(RPCUnicodeString.NonNullTerminated name) {
        this.name = name;
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
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.unmarshalDeferrals(in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRGroupGeneralInformation)) {
            return false;
        }
        SAMPRGroupGeneralInformation other = (SAMPRGroupGeneralInformation) obj;
        return Objects.equals(getName(), other.getName());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_GROUP_NAME_INFORMATION {Name:%s}",
                getName());
    }

	@Override 
	public void marshalPreamble(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.marshalPreamble(out);
	}

	@Override
	public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 4
        out.align(Alignment.FOUR);
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.marshalEntity(out);
	}
	
	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException {
        // <NDR: struct> RPC_UNICODE_STRING Name;
        name.marshalDeferrals(out);
	}
}
