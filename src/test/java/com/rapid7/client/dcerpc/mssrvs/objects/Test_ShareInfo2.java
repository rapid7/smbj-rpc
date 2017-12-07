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

public class Test_ShareInfo2 {

    @Test
    public void test_getters() {
        ShareInfo2 obj = new ShareInfo2();
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 0);
        assertNull(obj.getRemark());
        assertEquals(obj.getPermissions(), 0);
        assertEquals(obj.getMaxUses(), 0);
        assertEquals(obj.getCurrentUses(), 0);
        assertNull(obj.getPath());
        assertNull(obj.getPasswd());
    }

    @Test
    public void test_setters() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        WChar.NullTerminated remark = WChar.NullTerminated.of("Remark");
        WChar.NullTerminated path = WChar.NullTerminated.of("Path");
        WChar.NullTerminated passwd = WChar.NullTerminated.of("Passwd");
        ShareInfo2 obj = new ShareInfo2();
        obj.setNetName(netName);
        obj.setType(25);
        obj.setRemark(remark);
        obj.setPermissions(30);
        obj.setMaxUses(35);
        obj.setCurrentUses(40);
        obj.setPath(path);
        obj.setPasswd(passwd);
        assertSame(obj.getNetName(), netName);
        assertEquals(obj.getType(), 25);
        assertSame(obj.getRemark(), remark);
        assertEquals(obj.getPermissions(), 30);
        assertEquals(obj.getMaxUses(), 35);
        assertEquals(obj.getCurrentUses(), 40);
        assertSame(obj.getPath(), path);
        assertSame(obj.getPasswd(), passwd);
    }

    @Test
    public void test_hashCode() {
        ShareInfo2 obj1 = new ShareInfo2();
        ShareInfo2 obj2 = new ShareInfo2();
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
        obj1.setPermissions(30);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPermissions(30);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setMaxUses(30);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setMaxUses(30);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setCurrentUses(30);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setCurrentUses(30);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPath(WChar.NullTerminated.of("Path"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPath(WChar.NullTerminated.of("Path"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPasswd(WChar.NullTerminated.of("Passwd"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPasswd(WChar.NullTerminated.of("Passwd"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        ShareInfo2 obj1 = new ShareInfo2();
        ShareInfo2 obj2 = new ShareInfo2();
        assertEquals(obj1, obj2);
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
        obj1.setPermissions(30);
        assertNotEquals(obj1, obj2);
        obj2.setPermissions(30);
        assertEquals(obj1, obj2);
        obj1.setMaxUses(30);
        assertNotEquals(obj1, obj2);
        obj2.setMaxUses(30);
        assertEquals(obj1, obj2);
        obj1.setCurrentUses(30);
        assertNotEquals(obj1, obj2);
        obj2.setCurrentUses(30);
        assertEquals(obj1, obj2);
        obj1.setPath(WChar.NullTerminated.of("Path"));
        assertNotEquals(obj1, obj2);
        obj2.setPath(WChar.NullTerminated.of("Path"));
        assertEquals(obj1, obj2);
        obj1.setPasswd(WChar.NullTerminated.of("Passwd"));
        assertNotEquals(obj1, obj2);
        obj2.setPasswd(WChar.NullTerminated.of("Passwd"));
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new ShareInfo2().toString(), "SHARE_INFO_2{shi2_netname: null, shi2_type: 0, shi2_remark: null, shi2_permissions: 0, shi2_max_uses: 0, shi2_current_uses: 0, shi2_path: null, shi2_passwd: null}");
    }

    @Test
    public void test_toString() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        WChar.NullTerminated remark = WChar.NullTerminated.of("Remark");
        WChar.NullTerminated path = WChar.NullTerminated.of("Path");
        WChar.NullTerminated passwd = WChar.NullTerminated.of("Passwd");
        ShareInfo2 obj = new ShareInfo2();
        obj.setNetName(netName);
        obj.setType(25);
        obj.setRemark(remark);
        obj.setPermissions(30);
        obj.setMaxUses(35);
        obj.setCurrentUses(40);
        obj.setPath(path);
        obj.setPasswd(passwd);
        assertEquals(obj.toString(), "SHARE_INFO_2{shi2_netname: \"NetName\", shi2_type: 25, shi2_remark: \"Remark\", shi2_permissions: 30, shi2_max_uses: 35, shi2_current_uses: 40, shi2_path: \"Path\", shi2_passwd: \"Passwd\"}");
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);

        ShareInfo2 obj = new ShareInfo2();
        obj.unmarshalPreamble(in);
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 0);
        assertNull(obj.getRemark());
        assertEquals(obj.getPermissions(), 0);
        assertEquals(obj.getMaxUses(), 0);
        assertEquals(obj.getCurrentUses(), 0);
        assertNull(obj.getPath());
        assertNull(obj.getPasswd());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                {
                    // NetName[pointer=0], Type=25, Remark[pointer=0]
                    "00000000 19000000 00000000" +
                    // Permissions=30, MaxUses=35, CurrentUses=40
                    "1E000000 23000000 28000000" +
                    // Path=[pointer=0], Passwd=[pointer=0]
                    "00000000 00000000",
                    null, 25, null, 30, 35, 40, null, null
                },
                {
                    // NetName[pointer=2], Type=25, Remark[pointer=4]
                    "02000000 19000000 04000000" +
                    // Permissions=30, MaxUses=35, CurrentUses=40
                    "1E000000 23000000 28000000" +
                    // Path=[pointer=6], Passwd=[pointer=8]
                    "06000000 08000000",
                    "", 25, "", 30, 35, 40, "", ""
                },
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, String expectNetName,
            int expectType, String expectRemark, int expectPermissions,
            int expectMaxUses, int expectCurrentUses, String expectPath,
            String expectPasswd) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        ShareInfo2 obj = new ShareInfo2();
        obj.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getNetName(), (expectNetName == null ? null : WChar.NullTerminated.of(expectNetName)));
        assertEquals(obj.getType(), expectType);
        assertEquals(obj.getRemark(), (expectRemark == null ? null : WChar.NullTerminated.of(expectRemark)));
        assertEquals(obj.getPermissions(), expectPermissions);
        assertEquals(obj.getMaxUses(), expectMaxUses);
        assertEquals(obj.getCurrentUses(), expectCurrentUses);
        assertEquals(obj.getPath(), (expectPath == null ? null : WChar.NullTerminated.of(expectPath)));
        assertEquals(obj.getPasswd(), (expectPasswd == null ? null : WChar.NullTerminated.of(expectPasswd)));
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // Null
                {"", null, null, null, null},
                {
                    // NetName[MaximumCount=9, Offset=0, ActualCount=9, data=testƟ123]
                    "09000000000000000900000074006500730074009f013100320033000000" +
                    // Alignment
                    "0000" +
                    // Remark[MaximumCount=8, Offset=0, ActualCount=8, data=testƟ12]
                    "08000000000000000800000074006500730074009f01310032000000" +
                    // Path[MaximumCount=7, Offset=0, ActualCount=7, data=testƟ1]
                    "07000000000000000700000074006500730074009f0131000000" +
                    // Alignment
                    "0000" +
                    // Passwd[MaximumCount=6, Offset=0, ActualCount=6, data=testƟ]
                    "06000000000000000600000074006500730074009f010000",
                    "testƟ123", "testƟ12", "testƟ1", "testƟ"
                },
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, String expectNetName, String expectRemark,
            String expectPath, String expectPasswd) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        ShareInfo2 obj = new ShareInfo2();
        if (expectNetName != null)
            obj.setNetName(new WChar.NullTerminated());
        obj.setType(25);
        if (expectRemark != null)
            obj.setRemark(new WChar.NullTerminated());
        obj.setPermissions(30);
        obj.setMaxUses(35);
        obj.setCurrentUses(40);
        if (expectPath != null)
            obj.setPath(new WChar.NullTerminated());
        if (expectPasswd != null)
            obj.setPasswd(new WChar.NullTerminated());
        obj.unmarshalDeferrals(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getNetName(), (expectNetName == null ? null : WChar.NullTerminated.of(expectNetName)));
        assertEquals(obj.getType(), 25);
        assertEquals(obj.getRemark(), (expectRemark == null ? null : WChar.NullTerminated.of(expectRemark)));
        assertEquals(obj.getPermissions(), 30);
        assertEquals(obj.getMaxUses(), 35);
        assertEquals(obj.getCurrentUses(), 40);
        assertEquals(obj.getPath(), (expectPath == null ? null : WChar.NullTerminated.of(expectPath)));
        assertEquals(obj.getPasswd(), (expectPasswd == null ? null : WChar.NullTerminated.of(expectPasswd)));
    }
}
