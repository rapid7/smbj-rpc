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
 * DOMAIN_MODIFIED_INFORMATION
 * 
 * typedef struct _DOMAIN_MODIFIED_INFORMATION {
   OLD_LARGE_INTEGER DomainModifiedCount;
   OLD_LARGE_INTEGER CreationTime;
 } DOMAIN_MODIFIED_INFORMATION,
  *
  *@see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/85973e1c-96f2-4c80-8135-b24d74ad7794">DOMAIN_MODIFIED_INFORMATION</a>
  */
public class SAMPRDomainModifiedInformation2 implements Unmarshallable, Marshallable {
	// <NDR: hyper> LARGE_INTEGER DomainModifiedCount;
    private long domainModifiedCount;
	// <NDR: hyper> LARGE_INTEGER CreationTime;
    private long creationTime;
	// <NDR: hyper> LARGE_INTEGER ModifiedCountAtLastPromotion;
    private long modifiedCountAtLastPromotion;

	public long getDomainModifiedCount() {
		return domainModifiedCount;
	}

	public void setDomainModifiedCount(long domainModifiedCount) {
		this.domainModifiedCount = domainModifiedCount;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getModifiedCountAtLastPromotion() {
		return modifiedCountAtLastPromotion;
	}

	public void setModifiedCountAtLastPromotion(long modifiedCountAtLastPromotion) {
		this.modifiedCountAtLastPromotion = modifiedCountAtLastPromotion;
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
        domainModifiedCount = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER CreationTime;
        // Alignment: 8 - Already aligned
        creationTime = in.readLong();
        // <NDR: hyper> OLD_LARGE_INTEGER ModifiedCountAtLastPromotion;
        // Alignment: 8 - Already aligned
        modifiedCountAtLastPromotion = in.readLong();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    	//NONE
    }
	
    @Override
    public int hashCode() {
        return Objects.hash(getDomainModifiedCount(), getCreationTime(), getModifiedCountAtLastPromotion() );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainModifiedInformation2)) {
            return false;
        }
        SAMPRDomainModifiedInformation2 other = (SAMPRDomainModifiedInformation2) obj;
        return Objects.equals(getDomainModifiedCount(), other.getDomainModifiedCount())
            && Objects.equals(getCreationTime(), other.getCreationTime())
            && Objects.equals(getModifiedCountAtLastPromotion(), other.getModifiedCountAtLastPromotion())
            ;
    }

    @Override
    public String toString() {
        return String.format(
            "SAMPRDomainModifiedInformation2 {getDomainModifiedCount:%s, getCreationTime:%s, getModifiedCountAtLastPromotion:%s}",
                getDomainModifiedCount(), getCreationTime(), getModifiedCountAtLastPromotion());
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
        out.writeLong(domainModifiedCount);
        // <NDR: hyper> OLD_LARGE_INTEGER CreationTime;
        // Alignment: 8 - Already aligned
        out.writeLong(creationTime);
        // <NDR: hyper> OLD_LARGE_INTEGER ModifiedCountAtLastPromotion;
        // Alignment: 8 - Already aligned
        out.writeLong(modifiedCountAtLastPromotion);
	 }

	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException { 
    	//NONE
	 }
}
