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

import com.rapid7.client.dcerpc.io.ndr.Alignment;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

public class Test_PrimitiveOutput {

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Invalid OutputStream: null")
    public void constructorNullByteBuffer() {
        new PacketOutput(null);
    }

    @DataProvider
    public Object[][] data_align() {
        return new Object[][] {
                {Alignment.ONE, "", ""},
                {Alignment.ONE, "FF", "FF"},
                {Alignment.TWO, "", ""},
                {Alignment.TWO, "FF", "FF00"},
                {Alignment.TWO, "FFFF", "FFFF"},
                {Alignment.FOUR, "", ""},
                {Alignment.FOUR, "FF", "FF000000"},
                {Alignment.FOUR, "FFFF", "FFFF0000"},
                {Alignment.FOUR, "FFFFFF", "FFFFFF00"},
                {Alignment.FOUR, "FFFFFFFF", "FFFFFFFF"},
                {Alignment.EIGHT, "FF", "FF00000000000000"},
                {Alignment.EIGHT, "FFFF", "FFFF000000000000"},
                {Alignment.EIGHT, "FFFFFF", "FFFFFF0000000000"},
                {Alignment.EIGHT, "FFFFFFFF", "FFFFFFFF00000000"},
                {Alignment.EIGHT, "FFFFFFFFFF", "FFFFFFFFFF000000"},
                {Alignment.EIGHT, "FFFFFFFFFFFF", "FFFFFFFFFFFF0000"},
                {Alignment.EIGHT, "FFFFFFFFFFFFFF", "FFFFFFFFFFFFFF00"},
                {Alignment.EIGHT, "FFFFFFFFFFFFFFFF", "FFFFFFFFFFFFFFFF"},
        };
    }

    @Test(dataProvider = "data_align")
    public void test_align(Alignment alignment, String hex, String expectHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(Hex.decode(hex));
        packetOut.align(alignment);
        assertEquals(Hex.toHexString(outputStream.toByteArray()).toUpperCase(), expectHex.replace(" ", ""));
    }

    @DataProvider
    public Object[][] data_pad() {
        return new Object[][] {
                {0, ""},
                {1, "00"},
                {2, "0000"},
                {4, "00000000"},
                {8, "0000000000000000"},
        };
    }

    @Test(dataProvider = "data_pad")
    public void pad(long n, String expectedHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new PacketOutput(outputStream).pad(n);
        assertEquals(expectedHex, Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void getCount() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        assertEquals(0, packetOut.getCount());
    }

    @Test
    public void write() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(0);
        packetOut.write(0xFF);
        assertEquals("00FF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeArray() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(new byte[]{0x00, (byte) 0xFF});
        assertEquals("00FF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeArrayEx() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.write(new byte[]{0x00, (byte) 0xFF}, 0, 2);
        packetOut.write(new byte[]{0x00, (byte) 0xFF}, 1, 1);
        packetOut.write(new byte[]{0x00, (byte) 0xFF}, 0, 1);
        assertEquals("00FFFF00", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeBoolean() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeBoolean(false);
        packetOut.writeBoolean(true);
        assertEquals("0001", Hex.toHexString(outputStream.toByteArray()));
    }

    @Test
    public void writeByte() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeByte((byte) 0);
        packetOut.writeByte((byte) 0xFF);
        assertEquals("00FF", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @DataProvider
    public Object[][] data_writeShort() {
        return new Object[][] {
                // Signed
                {Short.MIN_VALUE, "0080"},
                {-50, "CEFF"},
                {0, "0000"},
                {256, "0001"},
                {Short.MAX_VALUE, "FF7F"},
                // Unsigned
                {Short.MAX_VALUE+1, "0080"},
                {Short.MAX_VALUE*2, "FEFF"},
                {Short.MAX_VALUE*2 + 1, "FFFF"}
        };
    }

    @Test(dataProvider = "data_writeShort")
    public void writeShort(int value, String expectedHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new PacketOutput(outputStream).writeShort(value);
        assertEquals(expectedHex, Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @DataProvider
    public Object[][] data_writeChar() {
        return new Object[][] {
                {Character.MIN_VALUE, "0000"},
                {50, "3200"},
                {256, "0001"},
                {Character.MAX_VALUE, "FFFF"},
        };
    }

    @Test(dataProvider = "data_writeChar")
    public void writeChar(int value, String expectedHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new PacketOutput(outputStream).writeChar(value);
        assertEquals(expectedHex, Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @DataProvider
    public Object[][] data_writeInt() {
        return new Object[][] {
                {Integer.MIN_VALUE, "00000080"},
                {0, "00000000"},
                {50, "32000000"},
                {50462976, "00010203"},
                {Integer.MAX_VALUE, "FFFFFF7F"}
        };
    }

    @Test(dataProvider = "data_writeInt")
    public void writeInt(int value, String expectedHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new PacketOutput(outputStream).writeInt(value);
        assertEquals(expectedHex, Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @DataProvider
    public Object[][] data_writeInt_long() {
        return new Object[][] {
                // Signed
                {(long) Integer.MIN_VALUE, "00000080"},
                {0L, "00000000"},
                {8L, "08000000"},
                {50462976L, "00010203"},
                {(long) Integer.MAX_VALUE, "FFFFFF7F"},
                // Unsigned
                {(long) Integer.MAX_VALUE+1, "00000080"},
                {(long) Integer.MAX_VALUE*2, "FEFFFFFF"},
                {(long) Integer.MAX_VALUE*2 + 1, "FFFFFFFF"}
        };
    }

    @Test(dataProvider = "data_writeInt_long")
    public void writeInt_long(long value, String expectedHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new PacketOutput(outputStream).writeInt(value);
        assertEquals(expectedHex, Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @DataProvider
    public Object[][] data_writeLong() {
        return new Object[][] {
                {Long.MIN_VALUE, "0000000000000080"},
                {0L, "0000000000000000"},
                {50L, "3200000000000000"},
                {Long.MAX_VALUE, "FFFFFFFFFFFFFF7F"},
        };
    }

    @Test(dataProvider = "data_writeLong")
    public void writeLong(long value, String expectedHex) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new PacketOutput(outputStream).writeLong(value);
        assertEquals(expectedHex, Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeBytes() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeBytes("Hello World");
        assertEquals("48656C6C6F20576F726C64", Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }

    @Test
    public void writeChars() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        packetOut.writeChars("Hello World");
        assertEquals("480065006C006C006F00200057006F0072006C006400",
                Hex.toHexString(outputStream.toByteArray()).toUpperCase());
    }
}
