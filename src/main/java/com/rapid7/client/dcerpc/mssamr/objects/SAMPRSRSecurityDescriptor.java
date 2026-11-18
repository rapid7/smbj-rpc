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
import java.rmi.UnmarshalException;
import java.util.Arrays;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <b>Alignment: 4</b> (Max[4,4])<pre>
 *     [range(0, 256 * 1024)] unsigned long Length;: 4
 *     [size_is(Length)] unsigned char* SecurityDescriptor;: 4</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc245537.aspx">SAMPR_SR_SECURITY_DESCRIPTOR</a>
 * <blockquote><pre>The SAMPR_SR_SECURITY_DESCRIPTOR structure holds a formatted security descriptor.
 *      typedef struct _SAMPR_SR_SECURITY_DESCRIPTOR {
 *          [range(0, 256 * 1024)] unsigned long Length;
 *          [size_is(Length)] unsigned char* SecurityDescriptor;
 *      } SAMPR_SR_SECURITY_DESCRIPTOR,
 *      *PSAMPR_SR_SECURITY_DESCRIPTOR;
 *  Length: The size, in bytes, of SecurityDescriptor. If zero, SecurityDescriptor MUST be ignored. The maximum size of 256 * 1024 is an arbitrary value chosen to limit the amount of memory a client can force the server to allocate.
 *  SecurityDescriptor: A binary format per the SECURITY_DESCRIPTOR format in [MS-DTYP] section 2.4.6.</pre></blockquote>
 */
public class SAMPRSRSecurityDescriptor implements Unmarshallable, Marshallable {
    // [size_is(Length)] unsigned char* SecurityDescriptor;
    // Despite being an unsigned char (char[]), store as byte[] for parsing convenience
    private byte[] securityDescriptor;

    public byte[] getSecurityDescriptor() {
        return securityDescriptor;
    }

    public void setSecurityDescriptor(byte[] securityDescriptor) {
        this.securityDescriptor = securityDescriptor;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // [range(0, 256 * 1024)] unsigned long Length;
        // Alignment: 4 - Already aligned
        int length = readLength(in);
        // <NDR: unsigned long> [size_is(Length)] unsigned char* SecurityDescriptor;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0) {
            if (length > 0)
                securityDescriptor = new byte[length];
        } else {
            securityDescriptor = null;
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (securityDescriptor != null) {
            // <NDR: unsigned long> [size_is(Length)] unsigned char* SecurityDescriptor;
            in.align(Alignment.FOUR);
            // MaximumCount
            in.fullySkipBytes(4);
            for (int i = 0; i < securityDescriptor.length; i++) {
                // <NDR: unsigned char>
                // Alignment: 1 - Already aligned
                securityDescriptor[i] = in.readByte();
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.securityDescriptor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRSRSecurityDescriptor)) {
            return false;
        }
        return Arrays.equals(getSecurityDescriptor(), ((SAMPRSRSecurityDescriptor) obj).getSecurityDescriptor());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_SR_SECURITY_DESCRIPTOR{size_of(SecurityDescriptor):%s}",
                securityDescriptor == null ? "null" : securityDescriptor.length);
    }

    private int readLength(PacketInput in) throws IOException {
        final long ret = in.readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("Length %d > %d", ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }

	@Override
	public void marshalPreamble(PacketOutput out) throws IOException { 
		// No Preamble
	 }

	@Override
	public void marshalEntity(PacketOutput out) throws IOException { 
        // Structure Alignment: 4
        out.align(Alignment.FOUR);
        // [range(0, 256 * 1024)] unsigned long Length;
        // Alignment: 4 - Already aligned
        if (securityDescriptor != null)
        	out.writeInt(securityDescriptor.length);
        else
        	out.writeInt(0);
        // <NDR: unsigned long> [size_is(Length)] unsigned char* SecurityDescriptor;
        // Alignment: 4 - Already aligned
    	out.writeReferentID(securityDescriptor); //TODO: maybe write 0 for null descriptor
	 }

	@Override
	public void marshalDeferrals(PacketOutput out) throws IOException { 
        if (securityDescriptor != null) {
            // <NDR: unsigned long> [size_is(Length)] unsigned char* SecurityDescriptor;
            out.align(Alignment.FOUR);
            // MaximumCount
            out.writeInt(0); 		//.fullySkipBytes(4);
            for (int i = 0; i < securityDescriptor.length; i++) {
                // <NDR: unsigned char>
                // Alignment: 1 - Already aligned
            	out.writeByte(securityDescriptor[i]);
            }
        }
	 }
}
