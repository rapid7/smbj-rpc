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
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import com.google.common.io.CountingOutputStream;
import com.google.common.io.LittleEndianDataOutputStream;

public class PrimitiveOutput implements DataOutput {
    private final CountingOutputStream dataOutStream;
    private final DataOutput dataOut;

    public PrimitiveOutput(final OutputStream outputStream) {
        if (outputStream == null) {
            throw new IllegalArgumentException("Invalid OutputStream: " + outputStream);
        }
        dataOutStream = new CountingOutputStream(outputStream);
        dataOut = new LittleEndianDataOutputStream(dataOutStream);
    }

    public void align()
        throws IOException {
        align(Alignment.FOUR);
    }

    public void align(Alignment alignment)
        throws IOException {
        if (alignment == Alignment.ONE)
            return;
        final long alignmentOffset = alignment.getOffByOneAlignment() + dataOutStream.getCount() & ~alignment.getOffByOneAlignment();
        while (alignmentOffset > dataOutStream.getCount()) {
            writeByte(0);
        }
    }

    public long getCount() {
        return dataOutStream.getCount();
    }

    @Override
    public void write(final int b)
        throws IOException {
        dataOut.write(b);
    }

    @Override
    public void write(final byte[] b)
        throws IOException {
        dataOut.write(b);
    }

    @Override
    public void write(final byte[] b, final int off, final int len)
        throws IOException {
        dataOut.write(b, off, len);
    }

    @Override
    public void writeBoolean(final boolean v)
        throws IOException {
        dataOut.writeBoolean(v);
    }

    @Override
    public void writeByte(final int v)
        throws IOException {
        dataOut.writeByte(v);
    }

    @Override
    public void writeShort(final int v)
        throws IOException {
        dataOut.writeShort(v);
    }

    @Override
    public void writeChar(final int v)
        throws IOException {
        dataOut.writeChar(v);
    }

    @Override
    public void writeInt(final int v)
        throws IOException {
        dataOut.writeInt(v);
    }

    @Override
    public void writeLong(final long v)
        throws IOException {
        dataOut.writeLong(v);
    }

    @Override
    public void writeFloat(float v)
        throws IOException {
        dataOut.writeFloat(v);
    }

    @Override
    public void writeDouble(double v)
        throws IOException {
        dataOut.writeDouble(v);
    }

    @Override
    public void writeBytes(final String s)
        throws IOException {
        dataOut.writeBytes(s);
    }

    @Override
    public void writeChars(final String s)
        throws IOException {
        dataOut.writeChars(s);
    }

    @Override
    public void writeUTF(String s)
        throws IOException {
        dataOut.writeUTF(s);
    }
}
