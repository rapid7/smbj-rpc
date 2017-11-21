/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_SAMPRULongArray {
    @Test
    public void test_getters() {
        SAMPRULongArray obj = new SAMPRULongArray();
        assertEquals(obj.getArray(), null);
    }

    @Test
    public void test_setters() {
        SAMPRULongArray obj = new SAMPRULongArray();
        long[] array = new long[]{1, 2, 3};
        obj.setArray(array);
        assertSame(obj.getArray(), array);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        SAMPRULongArray obj = new SAMPRULongArray();
        obj.unmarshalPreamble(in);
        assertEquals(obj.getArray(), null);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Count: 1, Reference: 0
                {"01000000 00000000", 0, null},
                // Count: 25, Reference: 1
                {"19000000 01000000", 0, new long[25]},
                // Alignments
                {"FFFFFFFF 19000000 01000000", 1, new long[25]},
                {"FFFFFFFF 19000000 01000000", 2, new long[25]},
                {"FFFFFFFF 19000000 01000000", 3, new long[25]}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, long[] expectArray) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        SAMPRULongArray obj = new SAMPRULongArray();
        obj.unmarshalEntity(in);
        assertEquals(obj.getArray(), expectArray);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // null
                {"", 0, null, null},
                // MaximumCount: 2, Array: {1, 2147483648L, 2}
                {"02000000 01000000 00000080 02000000", 0, new long[3], new long[]{1, 2147483648L, 2}},
                // ALignments
                {"FFFFFFFF 02000000 01000000 00000080 02000000", 1, new long[3], new long[]{1, 2147483648L, 2}},
                {"FFFFFFFF 02000000 01000000 00000080 02000000", 2, new long[3], new long[]{1, 2147483648L, 2}},
                {"FFFFFFFF 02000000 01000000 00000080 02000000", 3, new long[3], new long[]{1, 2147483648L, 2}}
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, long[] array, long[] expectArray) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        SAMPRULongArray obj = new SAMPRULongArray();
        obj.setArray(array);
        obj.unmarshalDeferrals(in);
        assertEquals(obj.getArray(), expectArray);
        assertEquals(bin.available(), 0);
    }

    @Test
    public void test_hashCode() {
        SAMPRULongArray obj1 = new SAMPRULongArray();
        SAMPRULongArray obj2 = new SAMPRULongArray();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setArray(new long[]{1, 2, 3});
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setArray(new long[]{1, 2, 3});
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        SAMPRULongArray obj1 = new SAMPRULongArray();
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        SAMPRULongArray obj2 = new SAMPRULongArray();
        assertEquals(obj1, obj2);
        obj1.setArray(new long[]{1, 2, 3});
        assertNotEquals(obj1, obj2);
        obj2.setArray(new long[]{1, 2, 3});
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new SAMPRULongArray().toString(), "SAMPR_ULONG_ARRAY{size(Element):null");
    }

    @Test
    public void test_toString() {
        SAMPRULongArray obj = new SAMPRULongArray();
        obj.setArray(new long[]{1, 2, 3});
        assertEquals(obj.toString(), "SAMPR_ULONG_ARRAY{size(Element):3");
    }
}
