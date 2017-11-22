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

public abstract class RPCReferentConformantArray<T extends Unmarshallable & Marshallable>
        extends RPCConformantArray<T> {

    public RPCReferentConformantArray(T[] array) {
        super(array);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        for (int i = 0; i < array.length; i++) {
            int refId = in.readReferentID();
            // Not contained in the deferrals.
            if (refId == 0) {
                array[i] = null;
            } else {
                array[i] = createEntity();
            }
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (array == null)
            return;

        for (T t : array) {
            if (t != null)
                in.readUnmarshallable(t);
        }
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        if (array == null)
            return;

        for (T t : array) {
            if (t != null)
                out.writeReferentID();
            else
                out.writeNull();
        }
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (array == null)
            return;

        for (T t : array) {
            if (t != null)
                out.writeMarshallable(t);
        }
    }

    protected abstract T createEntity();
}
