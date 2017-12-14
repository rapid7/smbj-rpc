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
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public abstract class RPCConformantReferentArray<T extends Unmarshallable & Marshallable>
        extends RPCConformantArray<T> {

    public RPCConformantReferentArray(T[] array) {
        super(array);
    }

    protected abstract T createEntry();

    @Override
    protected void unmarshalEntryPreamble(PacketInput in, int index) {
        // No preamble
    }

    @Override
    protected void unmarshalEntryEntity(PacketInput in, int index) throws IOException {
        if (in.readReferentID() == 0)
            getArray()[index] = null;
        else
            getArray()[index] = createEntry();
    }

    @Override
    protected void unmarshalEntryDeferrals(PacketInput in, int index) throws IOException {
        final T entry = getArray()[index];
        if (entry != null)
            in.readUnmarshallable(entry);
    }

    @Override
    protected void marshalEntryPreamble(PacketOutput out, int index) {
        // No preamble
    }

    @Override
    protected void marshalEntryEntity(PacketOutput out, int index) throws IOException {
        out.writeReferentID(getArray()[index]);
    }

    @Override
    protected void marshalEntryDeferrals(PacketOutput out, int index) throws IOException {
        final T entry = getArray()[index];
        if (entry != null)
            out.writeMarshallable(entry);
    }
}
