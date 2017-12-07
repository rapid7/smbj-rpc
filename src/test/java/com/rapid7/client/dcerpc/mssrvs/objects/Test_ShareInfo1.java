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

public class Test_ShareInfo1 {

    @Test
    public void test_getters() {
        ShareInfo1 obj = new ShareInfo1();
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 0);
        assertNull(obj.getRemark());
    }

    @Test
    public void test_setters() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        WChar.NullTerminated remark = WChar.NullTerminated.of("Remark");
        ShareInfo1 obj = new ShareInfo1();
        obj.setNetName(netName);
        obj.setType(25);
        obj.setRemark(remark);
        assertSame(obj.getNetName(), netName);
        assertEquals(obj.getType(), 25);
        assertSame(obj.getRemark(), remark);
    }

    @Test
    public void test_hashCode() {
        ShareInfo1 obj1 = new ShareInfo1();
        ShareInfo1 obj2 = new ShareInfo1();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setNetName(WChar.NullTerminated.of("NetName"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setNetName(WChar.NullTerminated.of("NetName"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setType(25);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setType(25);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setRemark(WChar.NullTerminated.of("Remark"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setRemark(WChar.NullTerminated.of("Remark"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        ShareInfo1 obj1 = new ShareInfo1();
        ShareInfo1 obj2 = new ShareInfo1();
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test123");
        obj1.setNetName(WChar.NullTerminated.of("NetName"));
        assertNotEquals(obj1, obj2);
        obj2.setNetName(WChar.NullTerminated.of("NetName"));
        assertEquals(obj1, obj2);
        obj1.setType(25);
        assertNotEquals(obj1, obj2);
        obj2.setType(25);
        assertEquals(obj1, obj2);
        obj1.setRemark(WChar.NullTerminated.of("Remark"));
        assertNotEquals(obj1, obj2);
        obj2.setRemark(WChar.NullTerminated.of("Remark"));
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new ShareInfo1().toString(), "SHARE_INFO_1{shi1_netname: null, shi1_type: 0, shi1_remark: null}");
    }

    @Test
    public void test_toString() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        WChar.NullTerminated remark = WChar.NullTerminated.of("Remark");
        ShareInfo1 obj = new ShareInfo1();
        obj.setNetName(netName);
        obj.setType(25);
        obj.setRemark(remark);
        assertEquals(obj.toString(), "SHARE_INFO_1{shi1_netname: \"NetName\", shi1_type: 25, shi1_remark: \"Remark\"}");
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);

        ShareInfo1 obj = new ShareInfo1();
        obj.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 0);
        assertNull(obj.getRemark());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // NetName[pointer=0], Type=25, Remark[pointer=0]
                {"00000000 19000000 00000000", null, 25, null},
                // NetName[pointer=2], Type=25, Remark[pointer=4]
                {"02000000 19000000 04000000", "", 25, ""},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, String expectNetName,
            int expectType, String expectRemark) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        ShareInfo1 obj = new ShareInfo1();
        obj.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getNetName(), (expectNetName == null ? null : WChar.NullTerminated.of(expectNetName)));
        assertEquals(obj.getType(), expectType);
        assertEquals(obj.getRemark(), (expectRemark == null ? null : WChar.NullTerminated.of(expectRemark)));
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // Null
                {"", null, null},
                {
                    // NetName[MaximumCount=9, Offset=0, ActualCount=9, data=testƟ123]
                    "09000000000000000900000074006500730074009f013100320033000000" +
                    // Alignment
                    "0000" +
                    // Remark[MaximumCount=8, Offset=0, ActualCount=8, data=testƟ12]
                    "08000000000000000800000074006500730074009f01310032000000",
                    "testƟ123", "testƟ12"
                },
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, String expectNetName, String expectRemark) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        ShareInfo1 obj = new ShareInfo1();
        if (expectNetName != null)
            obj.setNetName(new WChar.NullTerminated());
        obj.setType(25);
        if (expectRemark != null)
            obj.setRemark(new WChar.NullTerminated());
        obj.unmarshalDeferrals(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getNetName(), (expectNetName == null ? null : WChar.NullTerminated.of(expectNetName)));
        assertEquals(obj.getType(), 25);
        assertEquals(obj.getRemark(), (expectRemark == null ? null : WChar.NullTerminated.of(expectRemark)));
    }
}
