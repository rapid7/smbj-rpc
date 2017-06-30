package com.rapid7.client.dcerpc;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.nio.ByteBuffer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Test_Packet {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullByteBuffer() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Buffer invalid: null");

        new Packet(null);
    }

    @Test
    public void serialize() {
        final Packet packet = new Packet();
        assertArrayEquals(new byte[0], packet.serialize());
    }

    @Test
    public void align() {
        final Packet packet = new Packet();
        assertEquals(0, packet.byteCount());
        packet.align();
        assertEquals(0, packet.byteCount());
        packet.putByte((byte) 0);
        assertEquals(1, packet.byteCount());
        packet.align();
        assertEquals(4, packet.byteCount());
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        assertEquals(6, packet.byteCount());
        packet.align();
        assertEquals(8, packet.byteCount());
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        assertEquals(11, packet.byteCount());
        packet.align();
        assertEquals(12, packet.byteCount());
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        assertEquals(16, packet.byteCount());
        packet.align();
        assertEquals(16, packet.byteCount());
    }

    @Test
    public void putByte() {
        final Packet packet = new Packet();
        packet.putByte((byte) 0xFF);
        assertArrayEquals(new byte[] { (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putBytes() {
        final Packet packet = new Packet();
        final byte[] testVector = new byte[] { 1, 2 };
        packet.putBytes(testVector);
        assertArrayEquals(testVector, packet.serialize());
    }

    @Test
    public void putShort() {
        final Packet packet = new Packet();
        packet.putShort((short) 0xFFFF);
        assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putInt() {
        final Packet packet = new Packet();
        packet.putInt(0xFFFFFFFF);
        assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putIntRef() {
        final Packet packet = new Packet();
        packet.putIntRef(0xFFFFFFFF);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x02, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF },
            packet.serialize());
    }

    @Test
    public void putNullIntRef() {
        final Packet packet = new Packet();
        packet.putIntRef(null);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putLong() {
        final Packet packet = new Packet();
        packet.putLong(0xFFFFFFFFFFFFFFFFl);
        assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putLongRef() {
        final Packet packet = new Packet();
        packet.putLongRef(0xFFFFFFFFFFFFFFFFl);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x02, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putNullLongRef() {
        final Packet packet = new Packet();
        packet.putLongRef(null);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putReferentID() {
        final Packet packet = new Packet();
        packet.putReferentID();
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x02, 0x00 }, packet.serialize());
    }

    @Test
    public void putNull() {
        final Packet packet = new Packet();
        packet.putNull();
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putEmptyArray() {
        final Packet packet = new Packet();
        packet.putEmptyArray(16777215);
        assertArrayEquals(
            new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 },
            packet.serialize());
    }

    @Test
    public void putEmptyArrayRef() {
        final Packet packet = new Packet();
        packet.putEmptyArrayRef(16777215);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x02, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringAligned() {
        final Packet packet = new Packet();
        packet.putString("Hello World!", false);
        assertArrayEquals(new byte[] { 0x18, 0x00, 0x18, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x20,
            0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringUnaligned() {
        final Packet packet = new Packet();
        packet.putString("Hello World", false);
        assertArrayEquals(new byte[] { 0x16, 0x00, 0x16, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x20,
            0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringAlignedNullTerminated() {
        final Packet packet = new Packet();
        packet.putString("Hello World", true);
        assertArrayEquals(new byte[] { 0x18, 0x00, 0x18, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x20,
            0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringUnalignedNullTerminated() {
        final Packet packet = new Packet();
        packet.putString("Hello World!", true);
        assertArrayEquals(
            new byte[] { 0x1a, 0x00, 0x1a, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0d, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x0d, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x20, 0x00, 0x57,
                0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00, 0x00, 0x00, 0x00, 0x00 },
            packet.serialize());
    }

    @Test
    public void putStringRefAligned() {
        final Packet packet = new Packet();
        packet.putStringRef("Hello World!", false);
        assertArrayEquals(
            new byte[] { 0x00, 0x00, 0x02, 0x00, 0x18, 0x00, 0x18, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f,
                0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00 },
            packet.serialize());
    }

    @Test
    public void putStringRefUnaligned() {
        final Packet packet = new Packet();
        packet.putStringRef("Hello World", false);
        assertArrayEquals(
            new byte[] { 0x00, 0x00, 0x02, 0x00, 0x16, 0x00, 0x16, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0b, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f,
                0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 },
            packet.serialize());
    }

    @Test
    public void putStringRefAlignedNullTerminated() {
        final Packet packet = new Packet();
        packet.putStringRef("Hello World", true);
        assertArrayEquals(
            new byte[] { 0x00, 0x00, 0x02, 0x00, 0x18, 0x00, 0x18, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f,
                0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 },
            packet.serialize());
    }

    @Test
    public void putStringRefUnalignedNullTerminated() {
        final Packet packet = new Packet();
        packet.putStringRef("Hello World!", true);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x1a, 0x00, 0x1a, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0d,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0d, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c,
            0x00, 0x6f, 0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00, 0x00,
            0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringRefNull() {
        final Packet packet = new Packet();
        packet.putStringRef(null, false);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringRefNullNullTerminated() {
        final Packet packet = new Packet();
        packet.putStringRef(null, true);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringBuffer() {
        final Packet packet = new Packet();
        packet.putStringBuffer(32767);
        assertArrayEquals(new byte[] { 0x00, 0x00, (byte) 0xFE, (byte) 0xFF, 0x00, 0x00, 0x02, 0x00, (byte) 0xFF, 0x7F,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putStringBufferRef() {
        final Packet packet = new Packet();
        packet.putStringBufferRef(32767);
        assertArrayEquals(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, (byte) 0xFE, (byte) 0xFF, 0x04, 0x00, 0x02,
            0x00, (byte) 0xFF, 0x7F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }, packet.serialize());
    }

    @Test
    public void putByteByIndex() {
        final Packet packet = new Packet();
        packet.putByte((byte) 0);
        packet.putByte((byte) 0);
        packet.putByte(1, (byte) 0xFF);
        assertArrayEquals(new byte[] { 0x00, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putShortByIndex() {
        final Packet packet = new Packet();
        packet.putShort((short) 0);
        packet.putByte((byte) 0);
        packet.putShort(1, (short) 0xFFFF);
        assertArrayEquals(new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putIntByIndex() {
        final Packet packet = new Packet();
        packet.putInt(0);
        packet.putByte((byte) 0);
        packet.putInt(1, 0xFFFFFFFF);
        assertArrayEquals(new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void putLongByIndex() {
        final Packet packet = new Packet();
        packet.putLong(0);
        packet.putByte((byte) 0);
        packet.putLong(1, 0xFFFFFFFFFFFFFFFFl);
        assertArrayEquals(new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, packet.serialize());
    }

    @Test
    public void getByte() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { (byte) 0xFF }));
        assertEquals((byte) 0xFF, packet.getByte());
    }

    @Test
    public void getBytesByLength() {
        final byte[] testVector = new byte[] { (byte) 0xFF, (byte) 0xFF };
        final Packet packet = new Packet(ByteBuffer.wrap(testVector));
        assertArrayEquals(testVector, packet.getBytes(2));
    }

    @Test
    public void getBytes() {
        final byte[] testVector = new byte[] { (byte) 0xFF, (byte) 0xFF };
        final Packet packet = new Packet(ByteBuffer.wrap(testVector));
        final byte[] buffer = new byte[2];
        packet.getBytes(buffer);
        assertArrayEquals(testVector, buffer);
    }

    @Test
    public void getShort() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { (byte) 0xFF, (byte) 0xFF }));
        assertEquals((short) 0xFFFF, packet.getShort());
    }

    @Test
    public void getInt() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertEquals(0xFFFFFFFF, packet.getInt());
    }

    @Test
    public void getIntRef() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertEquals(Integer.valueOf(0xFFFFFFFF), packet.getIntRef());
    }

    @Test
    public void getNullIntRef() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getIntRef());
    }

    @Test
    public void getLongRef() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertEquals(Long.valueOf(0xFFFFFFFFFFFFFFFFl), packet.getLongRef());
    }

    @Test
    public void getNullLongRef() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getLongRef());
    }

    @Test
    public void getReferentID() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00 }));
        assertEquals(0x00020000, packet.getReferentID());
    }

    @Test
    public void getByteArray() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x04, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, packet.getByteArray());
    }

    @Test
    public void getByteArrayRef() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }, packet.getByteArrayRef());
    }

    @Test
    public void getNullByteArrayRef() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getByteArrayRef());
    }

    @Test
    public void getStringAligned() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x18, 0x00, 0x18, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00,
                0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00 }));
        assertEquals("Hello World!", packet.getString(false));
    }

    @Test
    public void getStringUnaligned() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x16, 0x00, 0x16, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00,
                0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 }));
        assertEquals("Hello World", packet.getString(false));
    }

    @Test
    public void getStringAlignedNullTerminated() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x18, 0x00, 0x18, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00,
                0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 }));
        assertEquals("Hello World", packet.getString(true));
    }

    @Test
    public void getStringUnalignedNullTerminated() {
        final Packet packet = new Packet(ByteBuffer
            .wrap(new byte[] { 0x1a, 0x00, 0x1a, 0x00, 0x00, 0x00, 0x02, 0x00, 0x0d, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x0d, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x20, 0x00,
                0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00, 0x00, 0x00, 0x00, 0x00 }));
        assertEquals("Hello World!", packet.getString(true));
    }

    @Test
    public void getNullString() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getString(false));
    }

    @Test
    public void getNullStringNullTerminated() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getString(true));
    }

    @Test
    public void getStringRefAligned() {
        final Packet packet = new Packet(ByteBuffer
            .wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x18, 0x00, 0x18, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00,
                0x6f, 0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x21, 0x00 }));
        assertEquals("Hello World!", packet.getStringRef(false));
    }

    @Test
    public void getStringRefUnaligned() {
        final Packet packet = new Packet(ByteBuffer
            .wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x16, 0x00, 0x16, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0b, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x0b, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00,
                0x6f, 0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 }));
        assertEquals("Hello World", packet.getStringRef(false));
    }

    @Test
    public void getStringRefAlignedNullTerminated() {
        final Packet packet = new Packet(ByteBuffer
            .wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x18, 0x00, 0x18, 0x00, 0x04, 0x00, 0x02, 0x00, 0x0c, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x0c, 0x00, 0x00, 0x00, 0x48, 0x00, 0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00,
                0x6f, 0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00, 0x64, 0x00, 0x00, 0x00 }));
        assertEquals("Hello World", packet.getStringRef(true));
    }

    @Test
    public void getStringRefUnalignedNullTerminated() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x1a, 0x00, 0x1a, 0x00,
            0x04, 0x00, 0x02, 0x00, 0x0d, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0d, 0x00, 0x00, 0x00, 0x48, 0x00,
            0x65, 0x00, 0x6c, 0x00, 0x6c, 0x00, 0x6f, 0x00, 0x20, 0x00, 0x57, 0x00, 0x6f, 0x00, 0x72, 0x00, 0x6c, 0x00,
            0x64, 0x00, 0x21, 0x00, 0x00, 0x00, 0x00, 0x00 }));
        assertEquals("Hello World!", packet.getStringRef(true));
    }

    @Test
    public void getOuterNullStringRef() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getStringRef(false));
    }

    @Test
    public void getOuterNullStringRefNullTerminated() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getStringRef(true));
    }

    @Test
    public void getInnerNullStringRef() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getStringRef(false));
    }

    @Test
    public void getInnerNullStringRefNullTerminated() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 }));
        assertNull(packet.getStringRef(true));
    }

    @Test
    public void getByteByIndex() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, (byte) 0xFF }));
        assertEquals((byte) 0xFF, packet.getByte(1));
    }

    @Test
    public void getShortByIndex() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF }));
        assertEquals((short) 0xFFFF, packet.getShort(1));
    }

    @Test
    public void getIntByIndex() {
        final Packet packet = new Packet(
            ByteBuffer.wrap(new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertEquals(0xFFFFFFFF, packet.getInt(1));
    }

    @Test
    public void getLongByIndex() {
        final Packet packet = new Packet(ByteBuffer.wrap(new byte[] { 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF }));
        assertEquals(0xFFFFFFFFFFFFFFFFl, packet.getLong(1));
    }

    @Test
    public void skipBytes() {
        final Packet packet = new Packet();
        assertEquals(0, packet.byteCount());
        packet.skipBytes(1);
        assertEquals(1, packet.byteCount());
    }

    @Test
    public void byteCount() {
        final Packet packet = new Packet();
        assertEquals(0, packet.byteCount());
    }
}
