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

package com.rapid7.client.dcerpc.mssrvs.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.WChar;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_ShareInfo0 {

    @Test
    public void test_getters() {
        ShareInfo0 obj = new ShareInfo0();
        assertNull(obj.getNetName());
    }

    @Test
    public void test_setters() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        ShareInfo0 obj = new ShareInfo0();
        obj.setNetName(netName);
        assertSame(obj.getNetName(), netName);
    }

    @Test
    public void test_hashCode() {
        ShareInfo0 obj1 = new ShareInfo0();
        ShareInfo0 obj2 = new ShareInfo0();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setNetName(WChar.NullTerminated.of("NetName"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setNetName(WChar.NullTerminated.of("NetName"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        ShareInfo0 obj1 = new ShareInfo0();
        ShareInfo0 obj2 = new ShareInfo0();
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test123");
        obj1.setNetName(WChar.NullTerminated.of("NetName"));
        assertNotEquals(obj1, obj2);
        obj2.setNetName(WChar.NullTerminated.of("NetName"));
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new ShareInfo0().toString(), "SHARE_INFO_0{shi0_netname: null}");
    }

    @Test
    public void test_toString() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        ShareInfo0 obj = new ShareInfo0();
        obj.setNetName(netName);
        assertEquals(obj.toString(), "SHARE_INFO_0{shi0_netname: \"NetName\"}");
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);

        ShareInfo0 obj = new ShareInfo0();
        obj.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertNull(obj.getNetName());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // NetName[pointer=0]
                {"00000000", 0, null},
                // NetName[pointer=2]
                {"02000000", 0, ""},
                // Alignments
                {"00000000 00000002", 1, ""},
                {"00000000 00000002", 2, ""},
                {"00000000 00000002", 3, ""}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, String expectNetName) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        ShareInfo0 obj = new ShareInfo0();
        obj.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getNetName(), (expectNetName == null ? null : WChar.NullTerminated.of(expectNetName)));
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // null
                {"", 0, null},
                // NetName[MaximumCount=9, Offset=0, ActualCount=9]
                {"09000000000000000900000074006500730074009f013100320033000000", 0, "testƟ123"},
                // Alignments
                {"00000000 09000000000000000900000074006500730074009f013100320033000000", 1, "testƟ123"},
                {"00000000 09000000000000000900000074006500730074009f013100320033000000", 2, "testƟ123"},
                {"00000000 09000000000000000900000074006500730074009f013100320033000000", 3, "testƟ123"},
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, String expectNetName) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        ShareInfo0 obj = new ShareInfo0();
        if (expectNetName != null)
            obj.setNetName(new WChar.NullTerminated());
        obj.unmarshalDeferrals(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getNetName(), (expectNetName == null ? null : WChar.NullTerminated.of(expectNetName)));
    }
}
