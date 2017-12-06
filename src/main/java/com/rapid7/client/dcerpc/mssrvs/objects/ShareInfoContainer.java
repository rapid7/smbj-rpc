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

package com.rapid7.client.dcerpc.mssrvs.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.Arrays;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public abstract class ShareInfoContainer<T extends ShareInfo> implements Unmarshallable {

    private T[] buffer;

    public T[] getBuffer() {
        return buffer;
    }

    abstract T[] createBuffer(final int size);

    abstract T createEntry();

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        final int entriesRead = readIndex("EntriesRead", in);
        if (in.readReferentID() != 0) {
            if (entriesRead < 0) {
                throw new UnmarshalException(String.format(
                        "Expected entriesRead >= 0, got: %d", entriesRead));
            }
            this.buffer = createBuffer(entriesRead);
        } else {
            this.buffer = null;
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (this.buffer != null) {
            // Maximum Size
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            for (int i = 0; i < this.buffer.length; i++) {
                this.buffer[i] = createEntry();
                this.buffer[i].unmarshalPreamble(in);
            }
            for (final T entry : this.buffer) {
                entry.unmarshalEntity(in);
            }
            for (final T entry : this.buffer) {
                entry.unmarshalDeferrals(in);
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.buffer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfoContainer)) {
            return false;
        }
        return Arrays.equals(this.buffer, ((ShareInfoContainer) obj).buffer);
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
