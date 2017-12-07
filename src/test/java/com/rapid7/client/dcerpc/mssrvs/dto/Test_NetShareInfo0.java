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

public class Test_NetShareInfo0 {

    @Test
    public void test_init_null() {
        NetShareInfo0 obj = new NetShareInfo0(null);
        assertNull(obj.getNetName());
    }

    @Test
    public void test_init() {
        String netName = "NetName";
        NetShareInfo0 obj = new NetShareInfo0(netName);
        assertSame(obj.getNetName(), netName);
    }

    @Test
    public void test_hashCode() {
        String netName1 = "NetName1";
        String netName2 = "NetName2";
        NetShareInfo0 obj1 = new NetShareInfo0(netName1);
        NetShareInfo0 obj2 = new NetShareInfo0(netName1);
        NetShareInfo0 obj3 = new NetShareInfo0(netName2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
    }

    @Test
    public void test_equals() {
        String netName1 = "NetName1";
        String netName2 = "NetName2";
        NetShareInfo0 obj1 = new NetShareInfo0(netName1);
        NetShareInfo0 obj2 = new NetShareInfo0(netName1);
        NetShareInfo0 obj3 = new NetShareInfo0(netName2);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
    }

    @Test
    public void test_toString_null() {
        NetShareInfo0 obj = new NetShareInfo0(null);
        assertEquals(obj.toString(), "NetShareInfo0{netName: null}");
    }

    @Test
    public void test_toString() {
        NetShareInfo0 obj = new NetShareInfo0("NetName");
        assertEquals(obj.toString(), "NetShareInfo0{netName: \"NetName\"}");
    }
}
