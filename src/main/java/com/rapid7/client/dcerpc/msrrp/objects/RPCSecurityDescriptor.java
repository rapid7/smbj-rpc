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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
<p>The RPC_SECURITY_DESCRIPTOR structure represents the <a href="https://msdn.microsoft.com/en-us/library/cc244893.aspx#gt_8a7f6700-8311-45bc-af10-82e10accd331">RPC</a> security descriptors.</p>
<pre> typedef struct _RPC_SECURITY_DESCRIPTOR {
   [size_is(cbInSecurityDescriptor), length_is(cbOutSecurityDescriptor)]
     PBYTE lpSecurityDescriptor;
   DWORD cbInSecurityDescriptor;
   DWORD cbOutSecurityDescriptor;
 } RPC_SECURITY_DESCRIPTOR,
 *  *PRPC_SECURITY_DESCRIPTOR;
</pre>
<p><strong>lpSecurityDescriptor:</strong>  A buffer that
contains a <a href="https://msdn.microsoft.com/en-us/library/cc230366.aspx">SECURITY_DESCRIPTOR</a>,
as specified in <a href="https://msdn.microsoft.com/en-us/library/cc230273.aspx">[MS-DTYP]</a>
section 2.4.6.</p>
<p><strong>cbInSecurityDescriptor:</strong>  The size in
bytes of the security descriptor.</p>
<p><strong>cbOutSecurityDescriptor:</strong>  The size
in bytes of the security descriptor.</p>
@see <a href="https://msdn.microsoft.com/en-us/library/cc244924.aspx">https://msdn.microsoft.com/en-us/library/cc244924.aspx</a>
 */
public class RPCSecurityDescriptor implements Unmarshallable, Marshallable {
    private int cbInSecurityDescriptor;
    private int cbOutSecurityDescriptor;
    private byte[] rawSecurityDescriptor = new byte[0];

    public void setCbInSecurityDescriptor(final int cbInSecurityDescriptor) {
        this.cbInSecurityDescriptor = cbInSecurityDescriptor;
    }

    @Override
    public void unmarshalPreamble(final PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(final PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        final int refID = in.readReferentID();
        if (refID != 0) {
            cbInSecurityDescriptor = in.readInt();
            cbOutSecurityDescriptor = in.readInt();
            rawSecurityDescriptor = new byte[cbOutSecurityDescriptor];
        }
    }

    @Override
    public void unmarshalDeferrals(final PacketInput in) throws IOException {
        if (rawSecurityDescriptor == null)
            return;
        in.align(Alignment.FOUR);
        in.readInt();
        in.readInt(); // offset
        final int actualSize = in.readInt();
        if (cbOutSecurityDescriptor != actualSize)
            throw new java.rmi.UnmarshalException("The actual size of the array does not match the expected.");
        // TODO: Return parsed object.
        in.readRawBytes(rawSecurityDescriptor);
    }

    @Override
    public void marshalPreamble(final PacketOutput out) throws IOException {
    }

    @Override
    public void marshalEntity(final PacketOutput out) throws IOException {
        out.writeReferentID();
        out.writeInt(cbInSecurityDescriptor);
        out.writeInt(cbOutSecurityDescriptor);
    }

    @Override
    public void marshalDeferrals(final PacketOutput out) throws IOException {
        out.writeInt(cbInSecurityDescriptor);
        out.writeInt(0);
        out.writeInt(cbOutSecurityDescriptor);
        out.write(rawSecurityDescriptor);
    }

    public byte[] getRawSecurityDescriptor() {
        return rawSecurityDescriptor;
    }
}
