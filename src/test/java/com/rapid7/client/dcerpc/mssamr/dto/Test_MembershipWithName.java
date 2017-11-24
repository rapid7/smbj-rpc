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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_MembershipWithName {

    @Test
    public void test_getters() {
        String name = "test";
        MembershipWithName obj = new MembershipWithName(50L, name);
        assertEquals(obj.getRelativeID(), 50L);
        assertSame(obj.getName(), name);
    }

    @Test
    public void test_hashCode() {
        MembershipWithName obj1 = new MembershipWithName(50L, "test");
        MembershipWithName obj2 = new MembershipWithName(51L, "test");
        MembershipWithName obj3 = new MembershipWithName(50L, "test2");
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
    }

    @Test
    public void test_equals() {
        MembershipWithName obj1 = new MembershipWithName(50L, "test");
        MembershipWithName obj2 = new MembershipWithName(51L, "test");
        MembershipWithName obj3 = new MembershipWithName(50L, "test2");
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
    }

    @Test
    public void test_toString_null() {
        assertEquals(new MembershipWithName(50L, null).toString(), "MembershipWithName{relativeID: 50, name: null}");
    }
    @Test
    public void test_toString() {
        assertEquals(new MembershipWithName(50L, "test").toString(), "MembershipWithName{relativeID: 50, name: \"test\"}");
    }
}
