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

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import com.google.common.io.CountingInputStream;
import com.google.common.io.LittleEndianDataInputStream;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

class PrimitiveInput {
    private final CountingInputStream dataInStream;
    private final DataInput dataIn;

    public PrimitiveInput(final InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Invalid InputStream: null");
        }
        dataInStream = new CountingInputStream(inputStream);
        dataIn = new LittleEndianDataInputStream(dataInStream);
    }

    public void align(Alignment alignment) throws IOException {
        if (alignment == Alignment.ONE)
            return;
        long readBytes = ((alignment.getOffByOneAlignment() + dataInStream.getCount()) & ~alignment.getOffByOneAlignment()) - dataInStream.getCount();
        while (readBytes-- > 0)
            readByte();
    }

    public long getCount() {
        return dataInStream.getCount();
    }

    public void readFully(final byte[] b) throws IOException {
        dataIn.readFully(b);
    }

    public void readFully(final byte[] b, final int off, final int len) throws IOException {
        dataIn.readFully(b, off, len);
    }

    public void fullySkipBytes(final int n) throws IOException {
        if (n != dataIn.skipBytes(n)) {
            throw new EOFException();
        }
    }

    public boolean readBoolean() throws IOException {
        return dataIn.readBoolean();
    }

    public byte readByte() throws IOException {
        return dataIn.readByte();
    }

    public char readUnsignedByte() throws IOException {
        return (char) dataIn.readUnsignedByte();
    }

    public short readShort() throws IOException {
        return dataIn.readShort();
    }

    public int readUnsignedShort() throws IOException {
        return dataIn.readUnsignedShort();
    }

    public char readChar() throws IOException {
        return dataIn.readChar();
    }

    public int readInt() throws IOException {
        return dataIn.readInt();
    }

    public long readUnsignedInt() throws IOException {
        return readInt() & 0xFFFFFFFFL;
    }

    public long readLong() throws IOException {
        return dataIn.readLong();
    }

    public float readFloat() throws IOException {
        return dataIn.readFloat();
    }

    public double readDouble() throws IOException {
        return dataIn.readDouble();
    }
}
