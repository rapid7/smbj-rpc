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

public class Test_MembershipWithAttributes {

    @Test
    public void test_getters() {
        MembershipWithAttributes obj = new MembershipWithAttributes(50L, 100);
        assertEquals(obj.getRelativeID(), 50L);
        assertEquals(obj.getAttributes(), 100);
    }

    @Test
    public void test_hashCode() {
        MembershipWithAttributes obj1 = new MembershipWithAttributes(50L, 100);
        MembershipWithAttributes obj2 = new MembershipWithAttributes(51L, 100);
        MembershipWithAttributes obj3 = new MembershipWithAttributes(50L, 101);
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
    }

    @Test
    public void test_equals() {
        MembershipWithAttributes obj1 = new MembershipWithAttributes(50L, 100);
        MembershipWithAttributes obj2 = new MembershipWithAttributes(51L, 100);
        MembershipWithAttributes obj3 = new MembershipWithAttributes(50L, 101);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
    }

    @Test
    public void test_toString() {
        assertEquals(new MembershipWithAttributes(50L, 100).toString(), "MembershipWithAttributes{relativeID: 50, attributes: 100}");
    }
}
