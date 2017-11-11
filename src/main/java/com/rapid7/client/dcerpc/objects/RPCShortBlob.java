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

package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <b>Alignment: 4</b><pre>
 *     unsigned short Length;: 2
 *     unsigned short MaximumLength;: 2
 *     [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;: 4</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/dd304035.aspx">RPC_SHORT_BLOB</a>
 * <blockquote><pre>The RPC_SHORT_BLOB structure holds a counted array of unsigned short values.
 *      typedef struct _RPC_SHORT_BLOB {
 *          unsigned short Length;
 *          unsigned short MaximumLength;
 *          [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;
 *      } RPC_SHORT_BLOB,
 *      *PRPC_SHORT_BLOB;
 *  Length: The number of bytes of data contained in the Buffer member.
 *  MaximumLength: The length, in bytes, of the Buffer member.
 *  Buffer: A buffer containing Length/2 unsigned short values.</pre></blockquote>
 */
public class RPCShortBlob implements Marshallable, Unmarshallable {
    private int length;
    private int maximumLength;
    private int[] buffer;

    public int[] getBuffer() {
        return buffer;
    }

    public void setBuffer(int[] buffer) {
        this.buffer = buffer;
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // No preamble. Conformant array of `WCHAR*` is a reference, and so preamble is not required.
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment
        out.align(Alignment.FOUR);
        if (buffer == null) {
            // <NDR: unsigned short> unsigned short Length;
            // Alignment 2 - Already aligned
            out.writeShort((short) 0);
            // <NDR: unsigned short> unsigned short MaximumLength;
            // Alignment 2 - Already aligned
            out.writeShort((short) 0);
            // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;
            // Alignment 4 - Already aligned
            out.writeNull();
        } else {
            // <NDR: unsigned short> unsigned short Length;
            // Alignment 2 - Already aligned
            out.writeShort((short) buffer.length);
            // <NDR: unsigned short> unsigned short MaximumLength;
            // Alignment 2 - Already aligned
            out.writeShort((short) buffer.length);
            // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;
            // Alignment 4 - Already aligned
            out.writeReferentID();
        }
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (buffer != null) {
            // MaximumCount for conformant array
            out.align(Alignment.FOUR);
            out.writeInt(buffer.length);
            // Offset for varying array
            // Alignment 4 - Already aligned
            out.writeInt(0);
            // ActualCount for varying array
            // Alignment 4 - Already aligned
            out.writeInt(buffer.length);
            // Entries for conformant+varying array
            // Alignment 1 - Already aligned
            for (int s : buffer) {
                // TODO unsigned short
                out.writeShort((short) s);
            }
        }
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble. Conformant array of `WCHAR*` is a reference, and so preamble is not required.
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned short> unsigned short Length;
        // Alignment: 2 - Already aligned
        int length = in.readShort();
        // <NDR: unsigned short> unsigned short MaximumLength;
        // Alignment: 2 - Already aligned
        in.readShort();
        // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            // This is 0 cost - Compile time constants are internal objects
            buffer = new int[length];
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (buffer != null) {
            //Preamble
            in.align(Alignment.FOUR);
            in.readInt();

            //Entity
            // <NDR: unsigned long> Offset for varying array
            // Alignment: 4 - Already aligned
            final int offset = in.readInt();
            // <NDR: unsigned long> ActualCount for varying array
            // Alignment: 4 - Already aligned
            final int actualCount = in.readInt();
            if (actualCount != buffer.length) {
                throw new IllegalArgumentException(String.format("Expected Length == Buffer.ActualCount: %d != %d", actualCount, buffer.length));
            }
            //Deferrals
            // Entities for conformant array
            // Read prefix (if any)
            for (int i = 0; i < offset; i++) {
                // <NDR: unsigned short>
                // Alignment: 2 - Already aligned
                in.readShort();
            }
            // Read subset
            for (int i = 0; i < buffer.length; i++) {
                // <NDR: unsigned short>
                // Alignment: 2 - Already aligned
                buffer[i] = in.readShort();
            }
        }
    }
}
