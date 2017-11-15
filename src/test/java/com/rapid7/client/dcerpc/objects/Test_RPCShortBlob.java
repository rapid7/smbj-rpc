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

package com.rapid7.client.dcerpc.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class Test_RPCShortBlob {

    @Test
    public void test_getters_default() {
        RPCShortBlob obj = new RPCShortBlob();
        assertEquals(obj.getBuffer(), null);
    }

    @Test
    public void test_setters() {
        RPCShortBlob obj = new RPCShortBlob();
        int[] buffer = new int[5];
        obj.setBuffer(buffer);
        assertSame(obj.getBuffer(), buffer);
    }

    @Test
    public void test_marshalPreamble() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        RPCShortBlob obj = new RPCShortBlob();
        obj.marshalPreamble(out);
        assertEquals(obj.getBuffer(), null);
        assertEquals(bout.toByteArray(), new byte[0]);
    }

    @DataProvider
    public Object[][] data_marshalEntity() {
        return new Object[][] {
                // Length: 0, MaximumLength: 0, Reference: 0
                {"", null, "0000 0000 00000000"},
                // Length: 3, MaximumLength: 3, Reference: 2
                {"", new int[]{1, 2, 65535}, "0300 0300 00000200"},

                // Alignment
                // Length: 0, MaximumLength: 0, Reference: 0
                {"ff", null, "ff000000 0000 0000 00000000"},
                {"ffff", null, "ffff0000 0000 0000 00000000"},
                {"ffffff", null, "ffffff00 0000 0000 00000000"}
        };
    }

    @Test(dataProvider = "data_marshalEntity")
    public void test_marshalEntity(String writeHex, int[] buffer, String expectedHex) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.write(Hex.decode(writeHex));

        RPCShortBlob obj = new RPCShortBlob();
        obj.setBuffer(buffer);
        obj.marshalEntity(out);
        assertSame(obj.getBuffer(), buffer);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectedHex.replace(" ", ""));
    }

    @DataProvider
    public Object[][] data_marshalDeferrals() {
        return new Object[][] {
                // Null
                {"", null, ""},

                // MaximumCount: 3, Offset: 0, ActualCount: 3, Buffer:{1, 2, 65535}
                {"", new int[]{1, 2, 65535}, "03000000 00000000 03000000 0100 0200 ffff"},

                // Alignment
                // MaximumCount: 3, Offset: 0, ActualCount: 3, Buffer:{1, 2, 65535}
                {"ff", new int[]{1, 2, 65535}, "ff000000 03000000 00000000 03000000 0100 0200 ffff"},
                {"ffff", new int[]{1, 2, 65535}, "ffff0000 03000000 00000000 03000000 0100 0200 ffff"},
                {"ffffff", new int[]{1, 2, 65535}, "ffffff00 03000000 00000000 03000000 0100 0200 ffff"},
        };
    }

    @Test(dataProvider = "data_marshalDeferrals")
    public void test_marshalDeferrals(String writeHex, int[] buffer, String expectedHex) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.write(Hex.decode(writeHex));

        RPCShortBlob obj = new RPCShortBlob();
        obj.setBuffer(buffer);
        obj.marshalDeferrals(out);
        assertSame(obj.getBuffer(), buffer);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectedHex.replace(" ", ""));
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        RPCShortBlob obj = new RPCShortBlob();
        obj.unmarshalPreamble(in);
        assertEquals(obj.getBuffer(), null);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Length: 0, MaximumLength: 0, Reference: 0
                {"0000 0000 00000000", 0, null},
                // Length: 3, MaximumLength: 3, Reference: 2
                {"0300 0300 00000200", 0, new int[3]},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, int[] expectedBuffer) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        RPCShortBlob obj = new RPCShortBlob();
        obj.unmarshalEntity(in);
        assertEquals(obj.getBuffer(), expectedBuffer);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // Null
                {"", 0, null, null},

                // MaximumCount: 1, Offset: 0, ActualCount: 3, Buffer:{1, 2, 65535}
                {"01000000 00000000 03000000 0100 0200 ffff", 0, new int[3], new int[]{1, 2, 65535}},
                // MaximumCount: 1, Offset: 2, ActualCount: 3, Buffer:{1, 2, 65535}
                {"01000000 02000000 03000000 ffff ffff 0100 0200 ffff", 0, new int[3], new int[]{1, 2, 65535}},

                // Alignment
                // MaximumCount: 5, Offset: 0, ActualCount: 3, Buffer:{1, 2, 65535}
                {"ffffffff 05000000 00000000 03000000 0100 0200 ffff", 1, new int[3], new int[]{1, 2, 65535}},
                {"ffffffff 05000000 00000000 03000000 0100 0200 ffff", 2, new int[3], new int[]{1, 2, 65535}},
                {"ffffffff 05000000 00000000 03000000 0100 0200 ffff", 3, new int[3], new int[]{1, 2, 65535}},
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, int[] buffer, int[] expectedBuffer) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        RPCShortBlob obj = new RPCShortBlob();
        obj.setBuffer(buffer);
        obj.unmarshalDeferrals(in);
        assertEquals(obj.getBuffer(), expectedBuffer);
        assertEquals(bin.available(), 0);
    }

    @Test
    public void test_unmarhalDeferrals_InvalidActualCount() throws IOException {
        // MaximumCount: 5, Offset: 0, ActualCount: 3
        String hex = "05000000 00000000 03000000";
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        RPCShortBlob obj = new RPCShortBlob();
        obj.setBuffer(new int[2]);
        IllegalArgumentException actual = null;
        try {
            obj.unmarshalDeferrals(in);
        } catch (IllegalArgumentException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "Expected Length == Buffer.ActualCount: 3 != 2");
    }

    @Test
    public void test_hashCode() {
        RPCShortBlob obj1 = new RPCShortBlob();
        RPCShortBlob obj2 = new RPCShortBlob();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setBuffer(new int[5]);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setBuffer(new int[5]);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        RPCShortBlob obj1 = new RPCShortBlob();
        RPCShortBlob obj2 = new RPCShortBlob();
        assertEquals(obj1, obj2);
        obj1.setBuffer(new int[5]);
        assertNotEquals(obj1, obj2);
        obj2.setBuffer(new int[5]);
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new RPCShortBlob().toString(), "RPC_SHORT_BLOB{size(Buffer):null}");
    }

    @Test
    public void test_toString() {
        RPCShortBlob obj = new RPCShortBlob();
        obj.setBuffer(new int[]{1, 2, 3});
        assertEquals(obj.toString(), "RPC_SHORT_BLOB{size(Buffer):3}");
    }
}
