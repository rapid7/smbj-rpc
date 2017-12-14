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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_PacketOutput {
    @Test
    public void writeReferentID() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeReferentID();
        assertEquals("00000200", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void test_writeReferentID_obj() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        assertTrue(packetOut.writeReferentID("Test123"));
        assertEquals("00000200", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void test_writeReferentID_null() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        assertFalse(packetOut.writeReferentID(null));
        assertEquals("00000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeNull() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeNull();
        assertEquals("00000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeEmptyCVArray() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeEmptyCVArray(50462976);
        assertEquals("000102030000000000000000", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }
}
