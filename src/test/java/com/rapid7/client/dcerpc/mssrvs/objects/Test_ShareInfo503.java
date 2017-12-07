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

public class Test_ShareInfo503 {

    @Test
    public void test_getters() {
        ShareInfo503 obj = new ShareInfo503();
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 0);
        assertNull(obj.getRemark());
        assertEquals(obj.getPermissions(), 0);
        assertEquals(obj.getMaxUses(), 0);
        assertEquals(obj.getCurrentUses(), 0);
        assertNull(obj.getPath());
        assertNull(obj.getPasswd());
        assertNull(obj.getServerName());
        assertNull(obj.getSecurityDescriptor());
    }

    @Test
    public void test_setters() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        WChar.NullTerminated remark = WChar.NullTerminated.of("Remark");
        WChar.NullTerminated path = WChar.NullTerminated.of("Path");
        WChar.NullTerminated passwd = WChar.NullTerminated.of("Passwd");
        WChar.NullTerminated serverName = WChar.NullTerminated.of("ServerName");
        byte[] securityDescriptor = new byte[2];
        ShareInfo503 obj = new ShareInfo503();
        obj.setNetName(netName);
        obj.setType(25);
        obj.setRemark(remark);
        obj.setPermissions(30);
        obj.setMaxUses(35);
        obj.setCurrentUses(40);
        obj.setPath(path);
        obj.setPasswd(passwd);
        obj.setServerName(serverName);
        obj.setSecurityDescriptor(securityDescriptor);
        assertSame(obj.getNetName(), netName);
        assertEquals(obj.getType(), 25);
        assertSame(obj.getRemark(), remark);
        assertEquals(obj.getPermissions(), 30);
        assertEquals(obj.getMaxUses(), 35);
        assertEquals(obj.getCurrentUses(), 40);
        assertSame(obj.getPath(), path);
        assertSame(obj.getPasswd(), passwd);
        assertSame(obj.getServerName(), serverName);
        assertSame(obj.getSecurityDescriptor(), securityDescriptor);
    }

    @Test
    public void test_hashCode() {
        ShareInfo503 obj1 = new ShareInfo503();
        ShareInfo503 obj2 = new ShareInfo503();
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
        obj1.setServerName(WChar.NullTerminated.of("ServerName"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setServerName(WChar.NullTerminated.of("ServerName"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setSecurityDescriptor(new byte[]{1, 2});
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setSecurityDescriptor(new byte[]{1, 2});
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        ShareInfo503 obj1 = new ShareInfo503();
        ShareInfo503 obj2 = new ShareInfo503();
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
        obj1.setServerName(WChar.NullTerminated.of("ServerName"));
        assertNotEquals(obj1, obj2);
        obj2.setServerName(WChar.NullTerminated.of("ServerName"));
        assertEquals(obj1, obj2);
        obj1.setSecurityDescriptor(new byte[]{1, 2});
        assertNotEquals(obj1, obj2);
        obj2.setSecurityDescriptor(new byte[]{1, 2});
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new ShareInfo503().toString(), "SHARE_INFO_503{shi503_netname: null, shi503_type: 0, shi503_remark: null, shi503_permissions: 0, shi503_max_uses: 0, shi503_current_uses: 0, shi503_path: null, shi503_passwd: null, shi503_server_name: null, size(shi503_security_descriptor): null}");
    }

    @Test
    public void test_toString() {
        WChar.NullTerminated netName = WChar.NullTerminated.of("NetName");
        WChar.NullTerminated remark = WChar.NullTerminated.of("Remark");
        WChar.NullTerminated path = WChar.NullTerminated.of("Path");
        WChar.NullTerminated passwd = WChar.NullTerminated.of("Passwd");
        WChar.NullTerminated serverName = WChar.NullTerminated.of("ServerName");
        ShareInfo503 obj = new ShareInfo503();
        obj.setNetName(netName);
        obj.setType(25);
        obj.setRemark(remark);
        obj.setPermissions(30);
        obj.setMaxUses(35);
        obj.setCurrentUses(40);
        obj.setPath(path);
        obj.setPasswd(passwd);
        obj.setServerName(serverName);
        obj.setSecurityDescriptor(new byte[2]);
        assertEquals(obj.toString(), "SHARE_INFO_503{shi503_netname: \"NetName\", shi503_type: 25, shi503_remark: \"Remark\", shi503_permissions: 30, shi503_max_uses: 35, shi503_current_uses: 40, shi503_path: \"Path\", shi503_passwd: \"Passwd\", shi503_server_name: \"ServerName\", size(shi503_security_descriptor): 2}");
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);

        ShareInfo503 obj = new ShareInfo503();
        obj.unmarshalPreamble(in);
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 0);
        assertNull(obj.getRemark());
        assertEquals(obj.getPermissions(), 0);
        assertEquals(obj.getMaxUses(), 0);
        assertEquals(obj.getCurrentUses(), 0);
        assertNull(obj.getPath());
        assertNull(obj.getPasswd());
        assertNull(obj.getServerName());
        assertNull(obj.getSecurityDescriptor());
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
                        "00000000 00000000" +
                        // ServerName=[pointer=0]
                        "00000000" +
                        // Reserved=2, SecurityDescriptor[pointer=0]
                        "02000000 00000000",
                        null, 25, null, 30, 35, 40, null, null, null, null
                },
                {
                        // NetName[pointer=2], Type=25, Remark[pointer=4]
                        "02000000 19000000 04000000" +
                        // Permissions=30, MaxUses=35, CurrentUses=40
                        "1E000000 23000000 28000000" +
                        // Path=[pointer=6], Passwd=[pointer=8]
                        "06000000 08000000" +
                        // ServerName=[pointer=11]
                        "0B000000" +
                        // Reserved=2, SecurityDescriptor[pointer=10]
                        "02000000 0A000000",
                        "", 25, "", 30, 35, 40, "", "", "", new byte[2]
                },
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, String expectNetName,
            int expectType, String expectRemark, int expectPermissions,
            int expectMaxUses, int expectCurrentUses, String expectPath,
            String expectPasswd, String expectServerName, byte[] expectSecurityDescriptor) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        ShareInfo503 obj = new ShareInfo503();
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
        assertEquals(obj.getServerName(), (expectServerName == null ? null : WChar.NullTerminated.of(expectServerName)));
        assertEquals(obj.getSecurityDescriptor(), expectSecurityDescriptor);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // Null
                {"", null, null, null, null, null, null},
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
                    "06000000000000000600000074006500730074009f010000" +
                    // Server[MaximumCount=5, Offset=0, ActualCount=5, data=test]
                    "05000000000000000500000074006500730074000000" +
                    // Alignment
                    "0000" +
                    // SecurityDescriptr[MaxCount=10, data={1, 2}]
                    "0A0000000102",
                    "testƟ123", "testƟ12", "testƟ1", "testƟ", "test", new byte[]{1,2}
                },
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, String expectNetName, String expectRemark,
            String expectPath, String expectPasswd, String expectServerName, byte[] expectSecurityDescriptor) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        ShareInfo503 obj = new ShareInfo503();
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
        if (expectServerName != null)
            obj.setServerName(new WChar.NullTerminated());
        if (expectSecurityDescriptor != null)
            obj.setSecurityDescriptor(new byte[expectSecurityDescriptor.length]);
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
        assertEquals(obj.getServerName(), (expectServerName == null ? null : WChar.NullTerminated.of(expectServerName)));
        assertEquals(obj.getSecurityDescriptor(), expectSecurityDescriptor);
    }
}
