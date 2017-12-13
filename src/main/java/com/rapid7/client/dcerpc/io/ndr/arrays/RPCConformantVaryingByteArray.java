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

package com.rapid7.client.dcerpc.io.ndr.arrays;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class RPCConformantVaryingByteArray implements Unmarshallable {
    private int offset;
    private byte[] array;

    public RPCConformantVaryingByteArray() {

    }

    public byte[] getArray() {
        return this.array;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // MaximumCount
        in.align(Alignment.FOUR);
        in.fullySkipBytes(4);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure alignment: 4
        in.align(Alignment.FOUR);
        // Offset
        // Alignment: 4 - Already aligned
        this.offset = in.readIndex("Offset");
        // Alignment: 4 - Already aligned
        this.array = new byte[in.readIndex("ActualCount")];
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // Alignment: 1 - Already aligned
        in.fullySkipBytes(this.offset);
        for (int i = 0; i < this.array.length; i++)
            this.array[i] = in.readByte();
    }
}
