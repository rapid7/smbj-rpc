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

public class Test_MembershipWithUse {

    @Test
    public void test_getters() {
        MembershipWithUse obj = new MembershipWithUse(50L, 100);
        assertEquals(obj.getRelativeID(), 50L);
        assertEquals(obj.getUse(), 100);
    }

    @Test
    public void test_hashCode() {
        MembershipWithUse obj1 = new MembershipWithUse(50L, 100);
        MembershipWithUse obj2 = new MembershipWithUse(51L, 100);
        MembershipWithUse obj3 = new MembershipWithUse(50L, 101);
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
    }

    @Test
    public void test_equals() {
        MembershipWithUse obj1 = new MembershipWithUse(50L, 100);
        MembershipWithUse obj2 = new MembershipWithUse(51L, 100);
        MembershipWithUse obj3 = new MembershipWithUse(50L, 101);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
    }

    @Test
    public void test_toString() {
        assertEquals(new MembershipWithUse(50L, 100).toString(), "MembershipWithUse{relativeID: 50, use: 100}");
    }
}
