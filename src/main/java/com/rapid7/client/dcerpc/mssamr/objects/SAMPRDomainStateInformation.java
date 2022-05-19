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

/**
 * DOMAIN_STATE_INFORMATION 
 * 
 * typedef struct _DOMAIN_STATE_INFORMATION {
 * DOMAIN_SERVER_ENABLE_STATE DomainServerState;
 * } DOMAIN_STATE_INFORMATION,
 * PDOMAIN_STATE_INFORMATION;
 *
 *@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/f224edcf-8d4e-4294-b0c3-b0eda384c402">DOMAIN_STATE_INFORMATION</a>
 */
public class SAMPRDomainStateInformation implements Unmarshallable, Marshallable {


	// <NDR: unsigned long> ULONG DomainServerState;
	private long domainState;

	public long getDomainServerState() {
		return domainState;
	}
	public void setDomainState(long domainState) {
		this.domainState = domainState;
	}


    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    	//NONE
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.EIGHT);
        domainState = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    	//NONE
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(getDomainServerState() );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainStateInformation)) {
            return false;
        }
        SAMPRDomainStateInformation other = (SAMPRDomainStateInformation) obj;
        return Objects.equals(getDomainServerState(), other.getDomainServerState())
            ;
    }

    @Override
    public String toString() {
        return String.format(
            "SAMPRDomainServerState {domainServerState:%s}",
            getDomainServerState());
    }

	@Override
	public void marshalPreamble(PacketOutput out) throws IOException { 
    	//NONE
	 }

	@Override
	public void marshalEntity(PacketOutput out) throws IOException { 

        // Structure Alignment: 4
//        out.align(Alignment.EIGHT);
        // <NDR: hyper> OLD_LARGE_INTEGER DomainModifiedCount;
        // Alignment: 8 - Already aligned
        out.writeInt((int)domainState);
	 }

	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException { 
    	//NONE
	 }
}
