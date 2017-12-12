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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

public class RPCConformantVaryingBuffer extends RPCConformantBuffer {
    private int offset;
    private int actualCount;

    public RPCConformantVaryingBuffer(final int maximumCount) {
        this(maximumCount, 0, 0);
    }

    public RPCConformantVaryingBuffer(final int maximumCount, final int offset, final int actualCount) {
        super(maximumCount);
        this.offset = offset;
        this.actualCount = actualCount;
    }

    public int getOffset() {
        return offset;
    }

    public int getActualCount() {
        return this.actualCount;
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        super.marshalEntity(out);
        out.align(Alignment.FOUR);
        out.writeInt(getOffset());
        out.writeInt(getActualCount());
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        super.unmarshalEntity(in);
        in.align(Alignment.FOUR);
        this.offset = in.readIndex("Offset");
        this.actualCount = in.readIndex("ActualCount");
    }
}
