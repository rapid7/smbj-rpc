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
package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public abstract class RPCConformantArray<T> implements Unmarshallable, Marshallable {

    private int maximumCount;
    protected final T[] array;

    protected RPCConformantArray(T[] array) {
        this(array, -1);
    }

    protected RPCConformantArray(T[] array, int maximumCount) {
        if (array == null) {
            this.maximumCount = Math.min(0, maximumCount);
        } else {
            this.maximumCount = (maximumCount < array.length) ? array.length : maximumCount;
        }
        this.array = array;
    }

    public int getMaximumCount() {
        return this.maximumCount;
    }

    public T[] getArray() {
        return this.array;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        this.maximumCount = in.readInt();
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // No entity
    }

    @Override
    public abstract void unmarshalDeferrals(PacketInput in) throws IOException;

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        out.align(Alignment.FOUR);
        out.writeInt(this.maximumCount);
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // No entity
    }

    @Override
    public abstract void marshalDeferrals(PacketOutput out) throws IOException;
}
