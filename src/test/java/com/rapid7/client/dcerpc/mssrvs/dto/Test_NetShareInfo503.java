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

package com.rapid7.client.dcerpc.mssrvs.dto;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_NetShareInfo503 {

    @Test
    public void test_init_null() {
        NetShareInfo503 obj = new NetShareInfo503(null, 25, null, 30, 35, 40, null, null, null, null);
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 25);
        assertNull(obj.getRemark());
        assertEquals(obj.getPermissions(), 30);
        assertEquals(obj.getMaxUses(), 35);
        assertEquals(obj.getCurrentUses(), 40);
        assertNull(obj.getPath());
        assertNull(obj.getPasswd());
        assertNull(obj.getServerName());
        assertNull(obj.getSecurityDescriptor());
    }

    @Test
    public void test_init() {
        String netName = "NetName";
        String remark = "Remark";
        String path = "Path";
        String passwd = "Passwd";
        String serverName = "ServerName";
        byte[] securityDescriptor = new byte[2];
        NetShareInfo503 obj = new NetShareInfo503(netName, 25, remark, 30, 35, 40, path, passwd, serverName, securityDescriptor);
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
        String netName1 = "NetName1";
        String netName2 = "NetName2";
        int type1 = 25;
        int type2 = 30;
        String remark1 = "Remark1";
        String remark2 = "Remark2";
        int permissions1 = 35;
        int permissions2 = 40;
        int maxUses1 = 45;
        int maxUses2 = 50;
        int currentUses1 = 55;
        int currentUses2 = 60;
        String path1 = "Path1";
        String path2 = "Path2";
        String passwd1 = "Passwd1";
        String passwd2 = "Passwd2";
        String serverName1 = "ServerName1";
        String serverName2 = "ServerName2";
        byte[] securityDescriptor1 = new byte[]{1, 2};
        byte[] securityDescriptor2 = new byte[]{3, 4, 5};
        NetShareInfo503 obj1 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj2 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj3 = new NetShareInfo503(netName2, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj4 = new NetShareInfo503(netName1, type2, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj5 = new NetShareInfo503(netName1, type1, remark2, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj6 = new NetShareInfo503(netName1, type1, remark1, permissions2, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj7 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses2, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj8 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses2, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj9 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path2, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj10 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd2, serverName1, securityDescriptor1);
        NetShareInfo503 obj11 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName2, securityDescriptor1);
        NetShareInfo503 obj12 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
        assertNotEquals(obj1.hashCode(), obj6.hashCode());
        assertNotEquals(obj1.hashCode(), obj7.hashCode());
        assertNotEquals(obj1.hashCode(), obj8.hashCode());
        assertNotEquals(obj1.hashCode(), obj9.hashCode());
        assertNotEquals(obj1.hashCode(), obj10.hashCode());
        assertNotEquals(obj1.hashCode(), obj11.hashCode());
        assertNotEquals(obj1.hashCode(), obj12.hashCode());
    }

    @Test
    public void test_equals() {
        String netName1 = "NetName1";
        String netName2 = "NetName2";
        int type1 = 25;
        int type2 = 30;
        String remark1 = "Remark1";
        String remark2 = "Remark2";
        int permissions1 = 35;
        int permissions2 = 40;
        int maxUses1 = 45;
        int maxUses2 = 50;
        int currentUses1 = 55;
        int currentUses2 = 60;
        String path1 = "Path1";
        String path2 = "Path2";
        String passwd1 = "Passwd1";
        String passwd2 = "Passwd2";
        String serverName1 = "ServerName1";
        String serverName2 = "ServerName2";
        byte[] securityDescriptor1 = new byte[]{1, 2};
        byte[] securityDescriptor2 = new byte[]{3, 4, 5};
        NetShareInfo503 obj1 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj2 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj3 = new NetShareInfo503(netName2, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj4 = new NetShareInfo503(netName1, type2, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj5 = new NetShareInfo503(netName1, type1, remark2, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj6 = new NetShareInfo503(netName1, type1, remark1, permissions2, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj7 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses2, currentUses1, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj8 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses2, path1, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj9 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path2, passwd1, serverName1, securityDescriptor1);
        NetShareInfo503 obj10 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd2, serverName1, securityDescriptor1);
        NetShareInfo503 obj11 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName2, securityDescriptor1);
        NetShareInfo503 obj12 = new NetShareInfo503(netName1, type1, remark1, permissions1, maxUses1, currentUses1, path1, passwd1, serverName1, securityDescriptor2);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
        assertNotEquals(obj1, obj5);
        assertNotEquals(obj1, obj6);
        assertNotEquals(obj1, obj7);
        assertNotEquals(obj1, obj8);
        assertNotEquals(obj1, obj9);
        assertNotEquals(obj1, obj10);
        assertNotEquals(obj1, obj11);
        assertNotEquals(obj1, obj12);
    }

    @Test
    public void test_toString_null() {
        NetShareInfo503 obj = new NetShareInfo503(null, 25, null, 30, 35, 40, null, null, null, null);
        assertEquals(obj.toString(), "NetShareInfo503{netName: null, type: 25, remark: null, permissions: 30, maxUses: 35, currentUses: 40, path: null, passwd: null, serverName: null, size(securityDescriptor): null}");
    }

    @Test
    public void test_toString() {
        NetShareInfo503 obj = new NetShareInfo503("NetName", 25, "Remark", 30, 35, 40, "Path", "Passwd", "ServerName", new byte[]{1, 2});
        assertEquals(obj.toString(), "NetShareInfo503{netName: \"NetName\", type: 25, remark: \"Remark\", permissions: 30, maxUses: 35, currentUses: 40, path: \"Passwd\", passwd: \"Passwd\", serverName: \"ServerName\", size(securityDescriptor): 2}");
    }
}
