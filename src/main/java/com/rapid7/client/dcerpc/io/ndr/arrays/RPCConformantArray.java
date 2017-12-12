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

public abstract class RPCConformantArray<T> implements Unmarshallable, Marshallable {

    private int maximumCount;
    private T[] array;

    public RPCConformantArray(final T[] array) {
        this.array = array;
        this.maximumCount = array.length;
    }

    public RPCConformantArray(final int maximumCount) {
        allocate(0);
        this.maximumCount = maximumCount;
    }

    public void allocate(final int length) {
        this.array = createArray(length);
    }

    protected abstract T[] createArray(final int length);
    protected abstract void unmarshalEntryPreamble(final PacketInput in, final int index) throws IOException;
    protected abstract void unmarshalEntryEntity(final PacketInput in, final int index) throws IOException;
    protected abstract void unmarshalEntryDeferrals(final PacketInput in, final int index) throws IOException;
    protected abstract void marshalEntryPreamble(final PacketOutput out, final int index) throws IOException;
    protected abstract void marshalEntryEntity(final PacketOutput out, final int index) throws IOException;
    protected abstract void marshalEntryDeferrals(final PacketOutput out, final int index) throws IOException;

    public int getMaximumCount() {
        return this.maximumCount;
    }

    public T[] getArray() {
        return this.array;
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
        for (int i = 0; i < this.array.length; i++)
            unmarshalEntryPreamble(in, i);
        for (int i = 0; i < this.array.length; i++)
            unmarshalEntryEntity(in, i);
        for (int i = 0; i < this.array.length; i++)
            unmarshalEntryDeferrals(in, i);
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
        if (this.array == null)
            return;
        for (int i = 0; i < this.array.length; i++)
            marshalEntryPreamble(out, i);
        for (int i = 0; i < this.array.length; i++)
            marshalEntryEntity(out, i);
        for (int i = 0; i < this.array.length; i++)
            marshalEntryDeferrals(out, i);
    }
}
