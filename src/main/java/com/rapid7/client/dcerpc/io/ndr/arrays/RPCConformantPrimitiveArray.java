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

public abstract class RPCConformantPrimitiveArray<T> extends RPCConformantArray<T> {

    public RPCConformantPrimitiveArray(final T[] array) {
        super(array);
    }

    protected abstract T unmarshalPrimitive(final PacketInput in) throws IOException;
    protected abstract void marshalPrimitive(final PacketOutput out, final T entry) throws IOException;

    @Override
    protected void unmarshalEntryPreamble(final PacketInput in, final int index) {
        // Primitives have no preamble
    }

    @Override
    protected void unmarshalEntryEntity(final PacketInput in, final int index) throws IOException {
        getArray()[index] = unmarshalPrimitive(in);
    }

    @Override
    protected void unmarshalEntryDeferrals(final PacketInput in, final int index) {
        // Primitives have no deferrals
    }

    @Override
    protected void marshalEntryPreamble(final PacketOutput out, final int index) {
        // Primitives have no preamble
    }

    @Override
    protected void marshalEntryEntity(final PacketOutput out, final int index) throws IOException {
        marshalPrimitive(out, getArray()[index]);
    }

    @Override
    protected void marshalEntryDeferrals(final PacketOutput out, final int index) {
        // Primitives have no deferrals
    }
}
