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
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class RPCConformantBuffer implements Unmarshallable, Marshallable {

    private int maximumCount;

    public RPCConformantBuffer(final int maximumCount) {
        this.maximumCount = maximumCount;
    }

    public int getMaximumCount() {
        return this.maximumCount;
    }

    @Override
    public void unmarshalPreamble(final PacketInput in) throws IOException {
        this.maximumCount = in.readIndex("MaximumCount");
    }

    @Override
    public void unmarshalEntity(final PacketInput in) throws IOException {
        // No entity
    }

    @Override
    public void unmarshalDeferrals(final PacketInput in) throws IOException {
        // No deferrals
    }

    @Override
    public void marshalPreamble(final PacketOutput out) throws IOException {
        out.align(Alignment.FOUR);
        out.writeInt(getMaximumCount());
    }

    @Override
    public void marshalEntity(final PacketOutput out) throws IOException {
        // No entity
    }

    @Override
    public void marshalDeferrals(final PacketOutput out) throws IOException {
        // No deferrals
    }
}
