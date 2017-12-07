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

public class Test_NetShareInfo501 {

    @Test
    public void test_init_null() {
        NetShareInfo501 obj = new NetShareInfo501(null, 25, null, 30);
        assertNull(obj.getNetName());
        assertEquals(obj.getType(), 25);
        assertNull(obj.getRemark());
        assertEquals(obj.getFlags(), 30);
    }

    @Test
    public void test_init() {
        String netName = "NetName";
        String remark = "Remark";
        NetShareInfo501 obj = new NetShareInfo501(netName, 25, remark, 30);
        assertSame(obj.getNetName(), netName);
        assertEquals(obj.getType(), 25);
        assertSame(obj.getRemark(), remark);
        assertEquals(obj.getFlags(), 30);
    }

    @Test
    public void test_hashCode() {
        String netName1 = "NetName1";
        String netName2 = "NetName2";
        int type1 = 25;
        int type2 = 30;
        String remark1 = "Remark1";
        String remark2 = "Remark2";
        int flags1 = 35;
        int flags2 = 40;
        NetShareInfo501 obj1 = new NetShareInfo501(netName1, type1, remark1, flags1);
        NetShareInfo501 obj2 = new NetShareInfo501(netName1, type1, remark1, flags1);
        NetShareInfo501 obj3 = new NetShareInfo501(netName2, type1, remark1, flags1);
        NetShareInfo501 obj4 = new NetShareInfo501(netName1, type2, remark1, flags1);
        NetShareInfo501 obj5 = new NetShareInfo501(netName1, type1, remark2, flags1);
        NetShareInfo501 obj6 = new NetShareInfo501(netName1, type1, remark1, flags2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
        assertNotEquals(obj1.hashCode(), obj6.hashCode());
    }

    @Test
    public void test_equals() {
        String netName1 = "NetName1";
        String netName2 = "NetName2";
        int type1 = 25;
        int type2 = 30;
        String remark1 = "Remark1";
        String remark2 = "Remark2";
        int flags1 = 35;
        int flags2 = 40;
        NetShareInfo501 obj1 = new NetShareInfo501(netName1, type1, remark1, flags1);
        NetShareInfo501 obj2 = new NetShareInfo501(netName1, type1, remark1, flags1);
        NetShareInfo501 obj3 = new NetShareInfo501(netName2, type1, remark1, flags1);
        NetShareInfo501 obj4 = new NetShareInfo501(netName1, type2, remark1, flags1);
        NetShareInfo501 obj5 = new NetShareInfo501(netName1, type1, remark2, flags1);
        NetShareInfo501 obj6 = new NetShareInfo501(netName1, type1, remark1, flags2);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
        assertNotEquals(obj1, obj5);
        assertNotEquals(obj1, obj6);
    }

    @Test
    public void test_toString_null() {
        NetShareInfo501 obj = new NetShareInfo501(null, 25, null, 30);
        assertEquals(obj.toString(), "NetShareInfo501{netName: null, type: 25, remark: null, flags: 30}");
    }

    @Test
    public void test_toString() {
        NetShareInfo501 obj = new NetShareInfo501("NetName", 25, "Remark", 30);
        assertEquals(obj.toString(), "NetShareInfo501{netName: \"NetName\", type: 25, remark: \"Remark\", flags: 30}");
    }
}
