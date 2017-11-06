/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public abstract class SamArray<T extends Unmarshallable> implements Unmarshallable {

    private int count;
    private List<T> array;

    public SamArray() {
    }

    public int getEntryCount() {
        return count;
    }

    public List<T> getArray() {
        if (array == null)
            throw new IllegalStateException("");
        return Collections.unmodifiableList(array);
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.FOUR;
    }

    @Override
    public void unmarshallPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshallEntity(PacketInput in) throws IOException {
        int refId1 = in.readReferentID();
        count = in.readInt();
        int ref2Id2 = in.readReferentID();
        int entryCount = in.readInt();
        for (int i = 0; i < entryCount; i++) {
            T t = initEntry();
            if (array == null)
                array = new ArrayList<>();
            array.add(t);
            t.unmarshallPreamble(in);
            t.unmarshallEntity(in);
        }
    }

    @Override
    public void unmarshallDeferrals(PacketInput in) throws IOException {
        for (T t : array) {
            t.unmarshallDeferrals(in);
        }
    }

    protected abstract T initEntry();
}
