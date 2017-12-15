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

public class Test_DomainLockoutInfo {

    @Test
    public void test_getters() {
        DomainLockoutInfo obj = new DomainLockoutInfo(1, 2, 3);
        assertEquals(obj.getLockoutDuration(), 1);
        assertEquals(obj.getLockoutObservationWindow(), 2);
        assertEquals(obj.getLockoutThreshold(), 3);
    }

    @Test
    public void test_hashCode() {
        DomainLockoutInfo obj1 = new DomainLockoutInfo(1, 2, 3);
        DomainLockoutInfo obj2 = new DomainLockoutInfo(1, 2, 3);
        DomainLockoutInfo obj3 = new DomainLockoutInfo(10, 2, 3);
        DomainLockoutInfo obj4 = new DomainLockoutInfo(1, 20, 3);
        DomainLockoutInfo obj5 = new DomainLockoutInfo(1, 2, 30);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
    }

    @Test
    public void test_equals() {
        DomainLockoutInfo obj1 = new DomainLockoutInfo(1, 2, 3);
        DomainLockoutInfo obj2 = new DomainLockoutInfo(1, 2, 3);
        DomainLockoutInfo obj3 = new DomainLockoutInfo(10, 2, 3);
        DomainLockoutInfo obj4 = new DomainLockoutInfo(1, 20, 3);
        DomainLockoutInfo obj5 = new DomainLockoutInfo(1, 2, 30);
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
        DomainLockoutInfo obj = new DomainLockoutInfo(1, 2, 3);
        assertEquals(obj.toString(), "DomainLockoutInfo{lockoutDuration: 1, lockoutObservationWindow: 2, lockoutThreshold: 3}");
    }
}
