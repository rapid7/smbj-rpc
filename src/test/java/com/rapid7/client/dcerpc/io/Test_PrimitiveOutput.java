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

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test_PrimitiveOutput {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullByteBuffer() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid OutputStream: null");
        new PacketOutput(null);
    }

    @Test
    public void align()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        assertEquals(0, packetOut.getCount());

        packetOut.align();
        assertEquals(0, packetOut.getCount());

        packetOut.write(0);
        assertEquals(1, packetOut.getCount());

        packetOut.align();
        assertEquals(4, packetOut.getCount());

        packetOut.write(new byte[3]);
        assertEquals(7, packetOut.getCount());

        packetOut.align();
        assertEquals(8, packetOut.getCount());

        packetOut.align();
        assertEquals(8, packetOut.getCount());
    }

    @Test
    public void getCount() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        assertEquals(0, packetOut.getCount());
    }

    @Test
    public void write()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(0);
        packetOut.write(0xFF);
        assertEquals("00FF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeArray()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(new byte[] { 0x00, (byte) 0xFF });
        assertEquals("00FF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeArrayEx()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(new byte[] { 0x00, (byte) 0xFF }, 0, 2);
        packetOut.write(new byte[] { 0x00, (byte) 0xFF }, 1, 1);
        packetOut.write(new byte[] { 0x00, (byte) 0xFF }, 0, 1);
        assertEquals("00FFFF00", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeBoolean()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeBoolean(false);
        packetOut.writeBoolean(true);
        assertEquals("0001", Hex.toHexString(outputStream.toByteArray()));
    }

    @Test
    public void writeByte()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeByte(0);
        packetOut.writeByte(0xFF);
        assertEquals("00FF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeShort()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeShort(0);
        packetOut.writeShort(256);
        packetOut.writeShort(0xFFFF);
        assertEquals("00000001FFFF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeChar()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeChar(0);
        packetOut.writeChar(256);
        packetOut.writeChar(0xFFFF);
        assertEquals("00000001FFFF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeInt()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeInt(0);
        packetOut.writeInt(50462976);
        packetOut.writeInt(0xFFFFFFFF);
        assertEquals("0000000000010203FFFFFFFF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeLong()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeLong(0);
        packetOut.writeLong(506097522914230528l);
        packetOut.writeLong(0xFFFFFFFFFFFFFFFFl);
        assertEquals("00000000000000000001020304050607FFFFFFFFFFFFFFFF",
            Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeBytes()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeBytes("Hello World");
        assertEquals("48656C6C6F20576F726C64", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeChars()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeChars("Hello World");
        assertEquals("480065006C006C006F00200057006F0072006C006400",
            Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }
}
