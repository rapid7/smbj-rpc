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
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Test_PrimitiveInput {

    @Test(expectedExceptions = {IllegalArgumentException.class}, expectedExceptionsMessageRegExp = "Invalid InputStream: null")
    public void constructorNullByteBuffer() {
        new PacketInput(null);
    }

    @DataProvider
    public Object[][] data_align() {
        return new Object[][] {
                {Alignment.ONE, 0, 0},
                {Alignment.ONE, 1, 1},
                {Alignment.TWO, 0, 0},
                {Alignment.TWO, 2, 1},
                {Alignment.TWO, 2, 2},
                {Alignment.FOUR, 0, 0},
                {Alignment.FOUR, 4, 1},
                {Alignment.FOUR, 4, 2},
                {Alignment.FOUR, 4, 3},
                {Alignment.FOUR, 4, 4},
                {Alignment.EIGHT, 0, 0},
                {Alignment.EIGHT, 8, 1},
                {Alignment.EIGHT, 8, 2},
                {Alignment.EIGHT, 8, 3},
                {Alignment.EIGHT, 8, 4},
                {Alignment.EIGHT, 8, 5},
                {Alignment.EIGHT, 8, 6},
                {Alignment.EIGHT, 8, 7},
                {Alignment.EIGHT, 8, 8},
        };
    }

    @Test(dataProvider = "data_align")
    public void test_align(Alignment alignment, int size, int offset) throws IOException {
        final ByteArrayInputStream bin = new ByteArrayInputStream(new byte[size]);
        final PacketInput packetIn = new PacketInput(bin);
        packetIn.fullySkipBytes(offset);
        packetIn.align(alignment);
        assertEquals(bin.available(), 0);
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
    public void getCount_readUnsignedInt() throws IOException {
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

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readFully() throws IOException {
        getPacketInput("").readFully(new byte[1]);
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readFullyEx() throws IOException {
        getPacketInput("").readFully(new byte[1], 0, 1);
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_fullySkipBytes() throws IOException {
        getPacketInput("").fullySkipBytes(1);
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readBoolean() throws IOException {
        getPacketInput("").readBoolean();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readByte() throws IOException {
        getPacketInput("").readByte();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readUnsignedByte() throws IOException {
        getPacketInput("").readUnsignedByte();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readShort() throws IOException {
        getPacketInput("").readShort();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readUnsignedShort() throws IOException {
        getPacketInput("").readUnsignedShort();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readChar() throws IOException {
        getPacketInput("").readChar();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readInt() throws IOException {
        getPacketInput("").readInt();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readUnsignedInt() throws IOException {
        getPacketInput("").readUnsignedInt();
    }

    @Test(expectedExceptions = {EOFException.class})
    public void eof_readLong() throws IOException {
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

    @DataProvider
    public Object[][] data_readRawBytes() {
        return new Object[][] {
                {new byte[]{0x00, 0x01}, "0001020304", 2},
                {new byte[]{0x00, 0x01, 0x02}, "0001020304", 3},
        };
    }

    @Test(dataProvider = "data_readRawBytes")
    public void readRawBytes(byte[] expected, String hex, int count) throws IOException {
        assertArrayEquals(expected, getPacketInput(hex).readRawBytes(count));
    }

    @Test
    public void fullySkipBytes() throws IOException {
        final PacketInput packetIn = getPacketInput("00");
        packetIn.fullySkipBytes(1);
        assertEquals(1, packetIn.getCount());
    }

    @DataProvider
    public Object[][] data_readBoolean() {
        return new Object[][] {
                {false, "00"},
                {true, "01"},
                {true, "FF"},
        };
    }

    @Test(dataProvider = "data_readBoolean")
    public void readBoolean(boolean expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readBoolean());
        assertEquals(true, getPacketInput("01").readBoolean());
        assertEquals(true, getPacketInput("FF").readBoolean());
    }

    @DataProvider
    public Object[][] data_readByte() {
        return new Object[][] {
                {Byte.MIN_VALUE, "80"},
                {(byte) 0, "00"},
                {(byte) 13, "0D"},
                {(byte) -1, "FF"},
                {Byte.MAX_VALUE, "7F"}
        };
    }

    @Test(dataProvider = "data_readByte")
    public void readByte(byte expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readByte());
    }

    @DataProvider
    public Object[][] data_readUnsignedByte() {
        return new Object[][] {
                {(short) 0, "00"},
                {(short) 13, "0D"},
                {(short) ((Byte.MAX_VALUE*2)+1), "FF"}
        };
    }

    @Test(dataProvider = "data_readUnsignedByte")
    public void readUnsignedByte(short expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readUnsignedByte());
    }

    @DataProvider
    public Object[][] data_readShort() {
        return new Object[][] {
                {Short.MIN_VALUE, "0080"},
                {(short) 0, "0000"},
                {(short) 256, "0001"},
                {Short.MAX_VALUE, "FF7F"},
        };
    }

    @Test(dataProvider = "data_readShort")
    public void readShort(short expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readShort());
    }

    @DataProvider
    public Object[][] data_readUnsignedShort() {
        return new Object[][] {
                {0, "0000"},
                {256, "0001"},
                {(Short.MAX_VALUE*2) + 1, "FFFF"},
        };
    }

    @Test(dataProvider = "data_readUnsignedShort")
    public void readUnsignedShort(int expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readUnsignedShort());
    }

    @DataProvider
    public Object[][] data_readChar() {
        return new Object[][] {
                {(char) 0, "0000"},
                {(char) 256, "0001"},
                {(char) ((Short.MAX_VALUE*2) + 1), "FFFF"},
        };
    }

    @Test(dataProvider = "data_readChar")
    public void readChar(char expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readChar());
    }

    @DataProvider
    public Object[][] data_readInt() {
        return new Object[][] {
                {Integer.MIN_VALUE, "00000080"},
                {0, "00000000"},
                {50462976, "00010203"},
                {-1, "FFFFFFFF"},
                {Integer.MAX_VALUE, "FFFFFF7F"}
        };
    }

    @Test(dataProvider = "data_readInt")
    public void readInt(int expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readInt());
    }

    @DataProvider
    public Object[][] data_readUnsignedInt() throws IOException {
        return new Object[][] {
                {0L, "00000000"},
                {8L, "08000000"},
                {50462976L, "00010203"},
                {((long)Integer.MAX_VALUE), "FFFFFF7F"},
                {((long)Integer.MAX_VALUE)+1, "00000080"},
                {((long)Integer.MAX_VALUE)*2 + 1, "FFFFFFFF"},
        };
    }

    @Test(dataProvider = "data_readUnsignedInt")
    public void readUnsignedInt(long expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readUnsignedInt());
    }

    @DataProvider
    public Object[][] data_readLong() {
        return new Object[][] {
                {Long.MIN_VALUE, "0000000000000080"},
                {0, "0000000000000000"},
                {506097522914230528L, "0001020304050607"},
                {-1, "FFFFFFFFFFFFFFFF"},
                {Long.MAX_VALUE, "FFFFFFFFFFFFFF7F"}
        };
    }

    @Test(dataProvider = "data_readLong")
    public void readLong(long expected, String hex) throws IOException {
        assertEquals(expected, getPacketInput(hex).readLong());
    }

    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
