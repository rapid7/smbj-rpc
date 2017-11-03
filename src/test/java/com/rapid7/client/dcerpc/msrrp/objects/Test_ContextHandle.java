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
package com.rapid7.client.dcerpc.msrrp.objects;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public class Test_ContextHandle {
    private final ContextHandle maximumHandle = new ContextHandle("ffffffffffffffffffffffffffffffffffffffff");
    private final ContextHandle minimumHandle = new ContextHandle("0000000000000000000000000000000000000000");
    private final ContextHandle partialHandle = new ContextHandle("80808080808080808080808080808080");
    private final ContextHandle defaultHandle = new ContextHandle();

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullHandle() {
        new ContextHandle(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorBigString() {
        new ContextHandle("ffffffffffffffffffffffffffffffffffffffff0");
    }

    @Test
    public void maximumGetBytes() {
        final byte[] expectBytes = new byte[20];
        Arrays.fill(expectBytes, (byte) 0xff);
        assertArrayEquals(expectBytes, maximumHandle.getBytes());
    }

    @Test
    public void minimumGetBytes() {
        final byte[] expectBytes = new byte[20];
        assertArrayEquals(expectBytes, minimumHandle.getBytes());
    }

    @Test
    public void partialGetBytes() {
        final byte[] expectBytes = new byte[20];
        Arrays.fill(expectBytes, 4, 20, (byte) 0x80);
        assertArrayEquals(expectBytes, partialHandle.getBytes());
    }

    @Test
    public void defaultGetBytes() {
        final byte[] expectBytes = new byte[20];
        assertArrayEquals(expectBytes, defaultHandle.getBytes());
    }

    @Test
    public void maximumGetLength() {
        assertEquals(20, maximumHandle.getLength());
    }

    @Test
    public void minimumGetLength() {
        assertEquals(20, minimumHandle.getLength());
    }

    @Test
    public void partialGetLength() {
        assertEquals(20, partialHandle.getLength());
    }

    @Test
    public void defaultGetLength() {
        assertEquals(20, defaultHandle.getLength());
    }

    @Test
    public void maximumSetBytes() {
        final byte[] maximumBytes = new byte[20];
        final ContextHandle handle = new ContextHandle();

        Arrays.fill(maximumBytes, (byte) 0xff);

        handle.setBytes(maximumBytes);

        assertArrayEquals(maximumBytes, handle.getBytes());
    }

    @Test
    public void minimumSetBytes() {
        final byte[] minimumBytes = new byte[20];
        final ContextHandle handle = new ContextHandle();

        handle.setBytes(minimumBytes);

        assertArrayEquals(minimumBytes, handle.getBytes());
    }

    @Test
    public void partialSetBytes() {
        final byte[] partialBytes = new byte[20];
        final ContextHandle handle = new ContextHandle();

        Arrays.fill(partialBytes, 4, 20, (byte) 0);

        handle.setBytes(partialBytes);

        assertArrayEquals(partialBytes, handle.getBytes());
    }

    @Test
    public void defaultSetBytes() {
        final byte[] defaultBytes = new byte[20];
        final ContextHandle handle = new ContextHandle();

        handle.setBytes(defaultBytes);

        assertArrayEquals(defaultBytes, handle.getBytes());
    }

    @Test
    public void testToString() {
        assertEquals("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", maximumHandle.toString());
        assertEquals("0000000000000000000000000000000000000000", minimumHandle.toString());
        assertEquals("0000000080808080808080808080808080808080", partialHandle.toString());
        assertEquals("0000000000000000000000000000000000000000", defaultHandle.toString());
    }

    @Test
    public void testHashCode() {
        assertNotEquals(maximumHandle.hashCode(), minimumHandle.hashCode());
        assertNotEquals(maximumHandle.hashCode(), partialHandle.hashCode());
        assertNotEquals(maximumHandle.hashCode(), defaultHandle.hashCode());
        assertEquals(minimumHandle.hashCode(), minimumHandle.hashCode());
        assertEquals(minimumHandle.hashCode(), defaultHandle.hashCode());
    }

    @Test
    public void testEquals() {
        assertNotEquals(maximumHandle, minimumHandle);
        assertNotEquals(maximumHandle, partialHandle);
        assertNotEquals(maximumHandle, defaultHandle);
        assertEquals(minimumHandle, minimumHandle);
        assertEquals(minimumHandle, defaultHandle);
    }

    @Test
    public void unmarshall() throws IOException {
        byte[] buf = new String("1234567890abcdefghij").getBytes();
        PacketInput input = new PacketInput(new ByteArrayInputStream(buf));
        ContextHandle handle = new ContextHandle();
        input.readUnmarshallable(handle);
        assertArrayEquals(buf, handle.getBytes());

        byte[] buf2 = new String("1234567890").getBytes();
        PacketInput input2 = new PacketInput(new ByteArrayInputStream(buf));
        ContextHandle handle2 = new MyContextHandle();
        input2.readUnmarshallable(handle2);
        assertArrayEquals(buf2, handle2.getBytes());
    }

    private static class MyContextHandle extends ContextHandle {
        public MyContextHandle() {
            super(10);
        }
    }
}
