/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msrrp.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantVaryingByteArray;

/**
 * <b>Alignment: 4</b>
 * <blockquote><pre>The RPC_SECURITY_DESCRIPTOR structure represents the RPC security descriptors.
 *
 *      typedef struct _RPC_SECURITY_DESCRIPTOR {
 *          [size_is(cbInSecurityDescriptor), length_is(cbOutSecurityDescriptor)] PBYTE lpSecurityDescriptor;
 *          DWORD cbInSecurityDescriptor;
 *          DWORD cbOutSecurityDescriptor;
 *      } RPC_SECURITY_DESCRIPTOR,
 *      *PRPC_SECURITY_DESCRIPTOR;
 *
 *  lpSecurityDescriptor: A buffer that contains a SECURITY_DESCRIPTOR, as specified in [MS-DTYP] section 2.4.6.
 *  cbInSecurityDescriptor: The size in bytes of the security descriptor.
 *  cbOutSecurityDescriptor: The size in bytes of the security descriptor.</pre></blockquote>

 */
public class RPCSecurityDescriptor implements Unmarshallable, Marshallable {
    private int cbInSecurityDescriptor;
    private byte[] lpSecurityDescriptor;

    public byte[] getLpSecurityDescriptor() {
        return this.lpSecurityDescriptor;
    }

    public void setLpSecurityDescriptor(final byte[] lpSecurityDescriptor) {
        this.lpSecurityDescriptor = lpSecurityDescriptor;
    }

    public void setCbInSecurityDescriptor(final int cbInSecurityDescriptor) {
        this.cbInSecurityDescriptor = cbInSecurityDescriptor;
    }

    @Override
    public void unmarshalPreamble(final PacketInput in) {
        // No preamble
    }

    @Override
    public void unmarshalEntity(final PacketInput in) throws IOException {
        // Strucutre Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: pointer[conformant varying array]> [size_is(cbInSecurityDescriptor), length_is(cbOutSecurityDescriptor)] PBYTE lpSecurityDescriptor;
        // Alignment: 4 - Already aligned
        final boolean lpSecurityDescriptorPresent = in.readReferentID() != 0;
        // <NDR: unsigned long> DWORD cbInSecurityDescriptor;
        // Alignment: 4 - Already aligned
        in.fullySkipBytes(4);
        // <NDR: unsigned long> DWORD cbOutSecurityDescriptor;
        // Alignment: 4 - Already aligned
        final int cbOutSecurityDescriptor = in.readIndex("cbOutSecurityDescriptor");
        if (lpSecurityDescriptorPresent)
            this.lpSecurityDescriptor = new byte[cbOutSecurityDescriptor];
        else
            this.lpSecurityDescriptor = null;
    }

    @Override
    public void unmarshalDeferrals(final PacketInput in) throws IOException {
        if (this.lpSecurityDescriptor != null) {
            // MaximumCount
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            // Offset
            // Alignment: 4 - Already aligned
            final int offset = in.readIndex("Offset");
            in.fullySkipBytes(offset);
            final int actualCount = in.readIndex("ActualCount");
            if (actualCount != this.lpSecurityDescriptor.length) {
                throw new UnmarshalException(String.format(
                        "AcutalCount of the conformant varying array does not match cbOutSecurityDescriptor: %d != %d",
                        actualCount, this.lpSecurityDescriptor.length));
            }
            // Entries
            // Alignment: 1 - Already aligned
            in.readRawBytes(this.lpSecurityDescriptor);
        }
    }

    @Override
    public void marshalPreamble(final PacketOutput out) {
        // No preamble
    }

    @Override
    public void marshalEntity(final PacketOutput out) throws IOException {
        // Structure alignment: 4
        out.align(Alignment.FOUR);
        // <NDR: pointer[conformant varying array]> [size_is(cbInSecurityDescriptor), length_is(cbOutSecurityDescriptor)] PBYTE lpSecurityDescriptor;
        // Alignment: 4 - Already aligned
        out.writeReferentID(this.lpSecurityDescriptor);
        // <NDR: unsigned long> DWORD cbInSecurityDescriptor;
        // Alignment: 4 - Already aligned
        out.writeInt(cbInSecurityDescriptor);
        // <NDR: unsigned long> DWORD cbOutSecurityDescriptor;
        // Alignment: 4 - Already aligned
        out.writeInt(0);
    }

    @Override
    public void marshalDeferrals(final PacketOutput out) throws IOException {
        if (this.lpSecurityDescriptor != null) {
            // MaximumCount
            out.align(Alignment.FOUR);
            out.writeInt(this.cbInSecurityDescriptor);
            // Offset
            // Alignment: 4 - Already aligned
            out.writeInt(0);
            // ActualCount
            // Alignment: 4 - Already aligned
            out.writeInt(this.lpSecurityDescriptor.length);
            // Entries
            // Alignment: 1 - Already aligned
            out.write(this.lpSecurityDescriptor);
        }
    }
}
