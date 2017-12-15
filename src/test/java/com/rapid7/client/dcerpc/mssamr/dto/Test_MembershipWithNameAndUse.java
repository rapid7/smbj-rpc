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

package com.rapid7.client.dcerpc.mssamr.dto;

import com.rapid7.client.dcerpc.dto.SIDUse;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_MembershipWithNameAndUse {
    @Test
    public void test_getters() {
        MembershipWithNameAndUse obj = new MembershipWithNameAndUse(1L, "name1", 2);
        assertEquals(obj.getRelativeID(), 1L);
        assertEquals(obj.getName(), "name1");
        assertEquals(obj.getUseValue(), 2);
        assertSame(obj.getUse(), SIDUse.SID_TYPE_GROUP);
    }

    @Test
    public void test_use_Unknown() {
        MembershipWithNameAndUse obj = new MembershipWithNameAndUse(1L, "name1", 200);
        assertEquals(obj.getUseValue(), 200);
        assertNull(obj.getUse());
    }

    @Test
    public void test_hashCode() {
        MembershipWithNameAndUse obj1 = new MembershipWithNameAndUse(1L, "name1", 200);
        MembershipWithNameAndUse obj2 = new MembershipWithNameAndUse(1L, "name1", 200);
        MembershipWithNameAndUse obj3 = new MembershipWithNameAndUse(2L, "name1", 200);
        MembershipWithNameAndUse obj4 = new MembershipWithNameAndUse(1L, "name2", 200);
        MembershipWithNameAndUse obj5 = new MembershipWithNameAndUse(1L, "name1", 300);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
    }

    @Test
    public void test_equals() {
        MembershipWithNameAndUse obj1 = new MembershipWithNameAndUse(1L, "name1", 200);
        MembershipWithNameAndUse obj2 = new MembershipWithNameAndUse(1L, "name1", 200);
        MembershipWithNameAndUse obj3 = new MembershipWithNameAndUse(2L, "name1", 200);
        MembershipWithNameAndUse obj4 = new MembershipWithNameAndUse(1L, "name2", 200);
        MembershipWithNameAndUse obj5 = new MembershipWithNameAndUse(1L, "name1", 300);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
        assertNotEquals(obj1, obj5);
    }

    @Test
    public void test_toString() {
        MembershipWithNameAndUse obj = new MembershipWithNameAndUse(1L, "name1", 2);
        assertEquals(obj.toString(), "MembershipWithNameAndUse{relativeID: 1, name: \"name1\", use: 2 (SID_TYPE_GROUP)}");
    }

    @Test
    public void test_toString_nulls() {
        MembershipWithNameAndUse obj = new MembershipWithNameAndUse(1L, null, 200);
        assertEquals(obj.toString(), "MembershipWithNameAndUse{relativeID: 1, name: null, use: 200}");
    }
}
