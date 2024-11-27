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
 * DOMAIN_SERVER_ROLE_INFORMATION
 * 
 typedef struct _DOMAIN_SERVER_ROLE_INFORMATION {
   DOMAIN_SERVER_ROLE DomainServerRole;
 } DOMAIN_SERVER_ROLE_INFORMATION,
  *PDOMAIN_SERVER_ROLE_INFORMATION;
  *
  *@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/cb0e586a-29c8-49b2-8ced-c273a7476c22">DOMAIN_SERVER_ROLE_INFORMATION</a>
  */
public class SAMPRDomainServerRoleInformation implements Unmarshallable, Marshallable {


	// <NDR: unsigned long> ULONG DomainServerRole;
    private long domainServerRole;

	public long getDomainServerRole() {
		return domainServerRole;
	}
	public void setDomainServerRole(long domainServerRole) {
		this.domainServerRole = domainServerRole;
	}


    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    	//NONE
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.EIGHT);
        // <NDR: hyper> OLD_LARGE_INTEGER DomainModifiedCount;
        // Alignment: 8 - Already aligned
        domainServerRole = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    	//NONE
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(getDomainServerRole() );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainServerRoleInformation)) {
            return false;
        }
        SAMPRDomainServerRoleInformation other = (SAMPRDomainServerRoleInformation) obj;
        return Objects.equals(getDomainServerRole(), other.getDomainServerRole())
            ;
    }

    @Override
    public String toString() {
        return String.format(
            "SAMPRDomainServerRole {domainServerRole:%s}",
            getDomainServerRole());
    }

	@Override
	public void marshalPreamble(PacketOutput out) throws IOException { 
    	//NONE
	 }

	@Override
	public void marshalEntity(PacketOutput out) throws IOException { 

        // Structure Alignment: 4
        out.align(Alignment.EIGHT);
        // <NDR: hyper> OLD_LARGE_INTEGER DomainModifiedCount;
        // Alignment: 8 - Already aligned
        out.writeInt((int)domainServerRole);
	 }

	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException { 
    	//NONE
	 }
}
