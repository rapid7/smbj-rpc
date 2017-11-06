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

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Test_PrimitiveInput {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullByteBuffer() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid InputStream: null");
        new PacketInput(null);
    }

    @Test
    public void align() throws IOException {
        final PacketInput packetIn = getPacketInput("0000000000000000");
        assertEquals(0, packetIn.getCount());

        packetIn.align();
        assertEquals(0, packetIn.getCount());

        packetIn.fullySkipBytes(1);
        assertEquals(1, packetIn.getCount());

        packetIn.align();
        assertEquals(4, packetIn.getCount());

        packetIn.fullySkipBytes(3);
        assertEquals(7, packetIn.getCount());

        packetIn.align();
        assertEquals(8, packetIn.getCount());

        packetIn.align();
        assertEquals(8, packetIn.getCount());
    }

    @Test
    public void getCount() {
        final PacketInput packetIn = getPacketInput("");
        assertEquals(0, packetIn.getCount());
    }

    @Test
    public void getCount_readFully() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.readFully(new byte[1]);
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void getCount_readFullyEx() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.readFully(new byte[1], 0, 1);
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void getCount_fullySkipBytes() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.fullySkipBytes(1);
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void getCount_readBoolean() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.readBoolean();
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void getCount_readByte() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.readByte();
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void getCount_readUnsignedByte() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.readUnsignedByte();
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void getCount_readShort() throws IOException {
        final PacketInput packetIn = getPacketInput("0000");
        packetIn.readShort();
        assertEquals(2, packetIn.getCount());
    }

    @Test
    public void getCount_readUnsignedShort() throws IOException {
        final PacketInput packetIn = getPacketInput("0000");
        packetIn.readUnsignedShort();
        assertEquals(2, packetIn.getCount());
    }

    @Test
    public void getCount_readChar() throws IOException {
        final PacketInput packetIn = getPacketInput("0000");
        packetIn.readChar();
        assertEquals(2, packetIn.getCount());
    }

    @Test
    public void getCount_readInt() throws IOException {
        final PacketInput packetIn = getPacketInput("00000000");
        packetIn.readInt();
        assertEquals(4, packetIn.getCount());
    }

    @Test
    public void getCount_readLong() throws IOException {
        final PacketInput packetIn = getPacketInput("0000000000000000");
        packetIn.readLong();
        assertEquals(8, packetIn.getCount());
    }

    @Test
    public void eof_readFully() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readFully(new byte[1]);
    }

    @Test
    public void eof_readFullyEx() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readFully(new byte[1], 0, 1);
    }

    @Test
    public void eof_fullySkipBytes() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").fullySkipBytes(1);
    }

    @Test
    public void eof_readBoolean() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readBoolean();
    }

    @Test
    public void eof_readByte() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readByte();
    }

    @Test
    public void eof_readUnsignedByte() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readUnsignedByte();
    }

    @Test
    public void eof_readShort() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readShort();
    }

    @Test
    public void eof_readUnsignedShort() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readUnsignedShort();
    }

    @Test
    public void eof_readChar() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readChar();
    }

    @Test
    public void eof_readInt() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readInt();
    }

    @Test
    public void eof_readLong() throws IOException {
        thrown.expect(EOFException.class);
        getPacketInput("").readLong();
    }

    @Test
    public void readFully() throws IOException {
        final byte[] result = new byte[1];
        getPacketInput("00").readFully(result);
        assertArrayEquals(new byte[]{0x00}, result);
    }

    @Test
    public void readFullyEx() throws IOException {
        final byte[] result = new byte[1];
        getPacketInput("00").readFully(result, 0, 1);
        assertArrayEquals(new byte[]{0x00}, result);
    }

    @Test
    public void readRawBytes() throws IOException {
        final byte[] result1 = getPacketInput("0001020304").readRawBytes(2);
        assertArrayEquals(new byte[]{0x00, 0x01}, result1);

        final byte[] result2 = getPacketInput("0001020304").readRawBytes(3);
        assertArrayEquals(new byte[]{0x00, 0x01, 0x02}, result2);

    }

    @Test
    public void fullySkipBytes() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.fullySkipBytes(1);
        assertEquals(1, packetIn.getCount());
    }

    @Test
    public void readBoolean() throws IOException {
        assertEquals(false, getPacketInput("00").readBoolean());
        assertEquals(true, getPacketInput("01").readBoolean());
        assertEquals(true, getPacketInput("FF").readBoolean());
    }

    @Test
    public void readByte() throws IOException {
        assertEquals(0, getPacketInput("00").readByte());
        assertEquals(-1, getPacketInput("FF").readByte());
    }

    @Test
    public void readUnsignedByte() throws IOException {
        assertEquals(0, getPacketInput("00").readUnsignedByte());
        assertEquals(255, getPacketInput("FF").readUnsignedByte());
    }

    @Test
    public void readShort() throws IOException {
        assertEquals(0, getPacketInput("0000").readShort());
        assertEquals(256, getPacketInput("0001").readShort());
        assertEquals(-1, getPacketInput("FFFF").readShort());
    }

    @Test
    public void readUnsignedShort() throws IOException {
        assertEquals(0, getPacketInput("0000").readUnsignedShort());
        assertEquals(256, getPacketInput("0001").readUnsignedShort());
        assertEquals(65535, getPacketInput("FFFF").readUnsignedShort());
    }

    @Test
    public void readChar() throws IOException {
        assertEquals(0, getPacketInput("0000").readChar());
        assertEquals(256, getPacketInput("0001").readChar());
        assertEquals(65535, getPacketInput("FFFF").readChar());
    }

    @Test
    public void readInt() throws IOException {
        assertEquals(0, getPacketInput("00000000").readInt());
        assertEquals(50462976, getPacketInput("00010203").readInt());
        assertEquals(-1, getPacketInput("FFFFFFFF").readInt());
    }

    @Test
    public void readLong() throws IOException {
        assertEquals(0, getPacketInput("0000000000000000").readLong());
        assertEquals(506097522914230528l, getPacketInput("0001020304050607").readLong());
        assertEquals(-1, getPacketInput("FFFFFFFFFFFFFFFF").readLong());
    }

    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
