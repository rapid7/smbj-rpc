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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

public abstract class RPCConformantVaryingArray<T> extends RPCConformantArray<T> {

    private int actualCount;

    RPCConformantVaryingArray(T[] array) {
        this(array, -1);
    }

    RPCConformantVaryingArray(T[] array, int maximumCount) {
        super(array, maximumCount);
        this.actualCount = (array != null) ? array.length : 0;
    }

    public int getActualCount() {
        return this.actualCount;
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        out.align(Alignment.FOUR);
        out.writeInt(0); // offset
        out.writeInt(getActualCount());
    }
}
