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
import org.junit.Test;

public class Test_PacketOutput {
    @Test
    public void writeIntRef()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeIntRef(null);
        packetOut.writeIntRef(Integer.valueOf(50462976));
        assertEquals("000000000000020000010203", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeLongRef()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeLongRef(null);
        packetOut.writeLongRef(Long.valueOf(50462976));
        assertEquals("00000000000002000001020300000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeReferentID()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeReferentID();
        assertEquals("00000200", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeNull()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeNull();
        assertEquals("00000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeEmptyArray()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeEmptyArray(50462976);
        assertEquals("000102030000000000000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeEmptyArrayRef()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeEmptyArrayRef(50462976);
        assertEquals("00000200000102030000000000000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeString_null()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeStringBuffer(null, true);
        assertEquals("0000000000000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeString()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeStringBuffer("?", true);
        assertEquals("04000400000002000200000000000000020000003F000000",
            Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeStringRef_null()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeStringBufferRef(null, true);
        assertEquals("00000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeStringRef()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeStringBufferRef("?", true);
        assertEquals("0000020004000400040002000200000000000000020000003F000000",
            Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeStringBuffer()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeStringBuffer(256);
        assertEquals("0000000200000200000100000000000000000000",
            Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeStringBufferRef()
        throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeStringBufferRef(256);
        assertEquals("000002000000000204000200000100000000000000000000",
            Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }
}
