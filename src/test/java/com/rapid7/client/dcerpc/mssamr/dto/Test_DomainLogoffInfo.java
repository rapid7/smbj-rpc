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

public class Test_DomainLogoffInfo {

    @Test
    public void test_getters() {
        DomainLogoffInfo obj = new DomainLogoffInfo(2L);
        assertEquals(obj.getForceLogoff(), 2L);
    }

    @Test
    public void test_hashCode() {
        DomainLogoffInfo obj1 = new DomainLogoffInfo(2L);
        DomainLogoffInfo obj2 = new DomainLogoffInfo(2L);
        DomainLogoffInfo obj3 = new DomainLogoffInfo(3L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
    }

    @Test
    public void test_equals() {
        DomainLogoffInfo obj1 = new DomainLogoffInfo(2L);
        DomainLogoffInfo obj2 = new DomainLogoffInfo(2L);
        DomainLogoffInfo obj3 = new DomainLogoffInfo(3L);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
    }

    @Test
    public void test_toString() {
        DomainLogoffInfo obj = new DomainLogoffInfo(2L);
        assertEquals(obj.toString(), "DomainLogoffInfo{forceLogoff: 2}");
    }
}
