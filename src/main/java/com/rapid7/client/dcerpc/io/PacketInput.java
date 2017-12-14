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
package com.rapid7.client.dcerpc.io;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class PacketInput extends PrimitiveInput {
    public PacketInput(final InputStream inputStream) {
        super(inputStream);
    }

    public <T extends Unmarshallable> T readUnmarshallable(T unmarshallable) throws IOException {
        unmarshallable.unmarshalPreamble(this);
        unmarshallable.unmarshalEntity(this);
        unmarshallable.unmarshalDeferrals(this);
        return unmarshallable;
    }

    public int readReferentID() throws IOException {
        // Currently only supports NDR20
        return readInt();
    }

    public byte[] readRawBytes(int length) throws IOException {
        byte[] bytes = new byte[length];
        readRawBytes(bytes);

        return bytes;
    }

    public void readRawBytes(byte[] buf) throws IOException {
        readFully(buf, 0, buf.length);
    }

    public int readIndex(String name) throws IOException {
        final long ret = readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("%s %d > %d", name, ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }
}
