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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class Test_PacketInput {
    @Test
    public void readIntRef() throws IOException {
        assertNull(getPacketInput("00000000").readIntRef());
        assertEquals(0, getPacketInput("0000000100000000").readIntRef().intValue());
        assertEquals(-1, getPacketInput("00000001FFFFFFFF").readIntRef().intValue());
    }

    @Test
    public void readLongRef() throws IOException {
        assertNull(getPacketInput("00000000").readLongRef());
        assertEquals(0, getPacketInput("000000010000000000000000").readLongRef().intValue());
        assertEquals(-1, getPacketInput("00000001FFFFFFFFFFFFFFFF").readLongRef().intValue());
    }

    @Test
    public void readReferentID() throws IOException {
        assertEquals(0, getPacketInput("00000000").readReferentID());
        assertEquals(-1, getPacketInput("FFFFFFFF").readReferentID());
    }

    @Test
    public void readByteArray() throws IOException {
        assertArrayEquals(new byte[]{0x01}, getPacketInput("00000000000000000100000001").readByteArray());
        assertArrayEquals(new byte[]{0x00, 0x01}, getPacketInput("00000000010000000100000001").readByteArray());
    }

    @Test
    public void readByteArrayRef() throws IOException {
        assertNull(getPacketInput("00000000").readByteArrayRef());
        assertArrayEquals(new byte[]{0x01}, getPacketInput("0000000100000000000000000100000001000000").readByteArrayRef());
        assertArrayEquals(new byte[]{0x00, 0x01}, getPacketInput("0000000100000000010000000100000001000000").readByteArrayRef());
    }

    @Test
    public void readString() throws IOException {
        // Empty string
        assertEquals("", getPacketInput("000000000000000000000000").readString(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("00000000000000000100000000000000").readString(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("00000000000000000200000000000000").readString(true));
        // Single ? character
        assertEquals("?", getPacketInput("0000000000000000010000003F000000").readString(true));
        // Single ? character and null terminated
        assertEquals("?", getPacketInput("0000000000000000020000003F000000").readString(true));
        // Multiple ? characters
        assertEquals("??", getPacketInput("0000000000000000020000003F003F00").readString(true));
        // Single offset ? character
        assertEquals("\u0000?", getPacketInput("0000000001000000010000003F000000").readString(true));
    }

    @Test
    public void readStringRef() throws IOException {
        assertNull(getPacketInput("00000000").readStringRef(true));
        // Empty string
        assertEquals("", getPacketInput("00000001000000000000000000000000").readStringRef(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("0000000100000000000000000100000000000000").readStringRef(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("0000000100000000000000000200000000000000").readStringRef(true));
        // Single ? character
        assertEquals("?", getPacketInput("000000010000000000000000010000003F000000").readStringRef(true));
        // Single ? character and null terminated
        assertEquals("?", getPacketInput("000000010000000000000000020000003F000000").readStringRef(true));
        // Multiple ? characters
        assertEquals("??", getPacketInput("000000010000000000000000020000003F003F00").readStringRef(true));
        // Single offset ? character
        assertEquals("\u0000?", getPacketInput("000000010000000001000000010000003F000000").readStringRef(true));
    }

    @Test
    public void readStringBuf() throws IOException {
        assertNull(getPacketInput("0000000000000000").readStringBuf(true));
        // Empty string
        assertEquals("", getPacketInput("0000000000000001000000000000000000000000").readStringBuf(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("000000000000000100000000000000000100000000000000").readStringBuf(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("000000000000000100000000000000000200000000000000").readStringBuf(true));
        // Single ? character
        assertEquals("?", getPacketInput("00000000000000010000000000000000010000003F000000").readStringBuf(true));
        // Single ? character and null terminated
        assertEquals("?", getPacketInput("00000000000000010000000000000000020000003F000000").readStringBuf(true));
        // Multiple ? characters
        assertEquals("??", getPacketInput("00000000000000010000000000000000020000003F003F00").readStringBuf(true));
        // Single offset ? character
        assertEquals("\u0000?", getPacketInput("00000000000000010000000001000000010000003F000000").readStringBuf(true));
    }

    @Test
    public void readStringBufRef() throws IOException {
        assertNull(getPacketInput("00000000").readStringBufRef(true));
        // Empty string
        assertEquals("", getPacketInput("000000010000000000000001000000000000000000000000").readStringBufRef(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("00000001000000000000000100000000000000000100000000000000").readStringBufRef(true));
        // Empty null terminated string
        assertEquals("", getPacketInput("00000001000000000000000100000000000000000200000000000000").readStringBufRef(true));
        // Single ? character
        assertEquals("?", getPacketInput("0000000100000000000000010000000000000000010000003F000000").readStringBufRef(true));
        // Single ? character and null terminated
        assertEquals("?", getPacketInput("0000000100000000000000010000000000000000020000003F000000").readStringBufRef(true));
        // Multiple ? characters
        assertEquals("??", getPacketInput("0000000100000000000000010000000000000000020000003F003F00").readStringBufRef(true));
        // Single offset ? character
        assertEquals("\u0000?", getPacketInput("0000000100000000000000010000000001000000010000003F000000").readStringBufRef(true));
    }

    @Test
    public void readLong() throws IOException {
        // MaximumPasswordAge
        assertEquals(-37108517437440L, getPacketInput("0000000040DEFFFF").readLong());
    }

    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
