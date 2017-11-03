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
package com.rapid7.client.dcerpc.io;

import com.rapid7.client.dcerpc.io.ndr.Alignment;
import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import com.google.common.io.CountingInputStream;
import com.google.common.io.LittleEndianDataInputStream;

class PrimitiveInput implements DataInput {
    private final CountingInputStream dataInStream;
    private final DataInput dataIn;

    public PrimitiveInput(final InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Invalid InputStream: " + inputStream);
        }
        dataInStream = new CountingInputStream(inputStream);
        dataIn = new LittleEndianDataInputStream(dataInStream);
    }

    public void align()
        throws IOException {
        align(Alignment.FOUR);
    }

    public void align(Alignment alignment)
       throws IOException {
        final long alignmentOffset = alignment.getOffByOneAlignment() + dataInStream.getCount() & ~alignment.getOffByOneAlignment();
        while (alignmentOffset > dataInStream.getCount()) {
            readByte();
        }
    }

    public long getCount() {
        return dataInStream.getCount();
    }

    @Override
    public void readFully(final byte[] b)
        throws IOException {
        dataIn.readFully(b);
    }

    @Override
    public void readFully(final byte[] b, final int off, final int len)
        throws IOException {
        dataIn.readFully(b, off, len);
    }

    @Override
    public int skipBytes(int n)
       throws IOException {
        return dataIn.skipBytes(n);
    }

    public void fullySkipBytes(final int n)
        throws IOException {
        if (n != dataIn.skipBytes(n)) {
            throw new EOFException();
        }
    }

    @Override
    public boolean readBoolean()
        throws IOException {
        return dataIn.readBoolean();
    }

    @Override
    public byte readByte()
        throws IOException {
        return dataIn.readByte();
    }

    @Override
    public int readUnsignedByte()
        throws IOException {
        return dataIn.readUnsignedByte();
    }

    @Override
    public short readShort()
        throws IOException {
        return dataIn.readShort();
    }

    @Override
    public int readUnsignedShort()
        throws IOException {
        return dataIn.readUnsignedShort();
    }

    @Override
    public char readChar()
        throws IOException {
        return dataIn.readChar();
    }

    @Override
    public int readInt()
        throws IOException {
        return dataIn.readInt();
    }

    @Override
    public long readLong()
        throws IOException {
        return dataIn.readLong();
    }

    @Override
    public float readFloat()
       throws IOException {
        return dataIn.readFloat();
    }

    @Override
    public double readDouble()
       throws IOException {
        return dataIn.readDouble();
    }

    @Override
    public String readLine()
       throws IOException {
        return dataIn.readLine();
    }

    @Override
    public String readUTF()
       throws IOException {
        return dataIn.readUTF();
    }
}
