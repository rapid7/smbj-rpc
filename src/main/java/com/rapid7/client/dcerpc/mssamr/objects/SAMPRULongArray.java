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

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.Arrays;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc245825.aspx">SAMPR_ULONG_ARRAY</a>
 * <blockquote><pre>
 *      typedef struct _SAMPR_ULONG_ARRAY {
 *          unsigned long Count;
 *          [size_is(Count)] unsigned long * Element;
 *      } SAMPR_ULONG_ARRAY, *PSAMPR_ULONG_ARRAY;</pre></blockquote>
 */
public class SAMPRULongArray implements Unmarshallable {
    private long[] array;

    public long[] getArray() {
        return array;
    }

    public void setArray(long[] array) {
        this.array = array;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // unsigned long Count;
        final int count = readIndex("Count", in);
        if (in.readReferentID() != 0) {
            this.array = new long[count];
        } else {
            this.array = null;
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (this.array != null) {
            // MaximumCount: [size_is(Count)] unsigned long * Element;
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            // Elements: [size_is(Count)] unsigned long * Element;
            for (int i = 0; i < this.array.length; i++) {
                this.array[i] = in.readUnsignedInt();
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getArray());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SAMPRULongArray)) {
            return false;
        }
        return Arrays.equals(getArray(), ((SAMPRULongArray) obj).getArray());
    }

    @Override
    public String toString() {
        return String.format("SAMPR_ULONG_ARRAY{size(Element):%s",
                (this.array == null ? "null" : this.array.length));
    }

    private int readIndex(String name, PacketInput in) throws IOException {
        final long ret = in.readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("%s %d > %d", name, ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }
}
