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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
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
public class SAMPRSRSecurityDescriptor implements Unmarshallable {
    private int length;
    private short[] securityDescriptor;

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {

    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // [range(0, 256 * 1024)] unsigned long Length;
        // Alignment: 4 - Already aligned
        int length = in.readInt();
        // <NDR: unsigned long> [size_is(Length)] unsigned char* SecurityDescriptor;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0) {
            if (length > 0)
                securityDescriptor = new short[length];
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
            in.readInt();
            for (int i = 0; i < securityDescriptor.length; i++) {
                securityDescriptor[i] = (short) in.readByte();
            }
        }
    }
}
