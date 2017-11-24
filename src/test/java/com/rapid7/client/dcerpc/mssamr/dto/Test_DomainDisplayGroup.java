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

public class Test_DomainDisplayGroup {

    @Test
    public void test_getters() {
        String name = "name1";
        String comment = "comment1";
        DomainDisplayGroup obj = new DomainDisplayGroup(50, name, comment, 100);
        assertEquals(obj.getRelativeID(), 50);
        assertSame(obj.getName(), name);
        assertSame(obj.getComment(), comment);
        assertEquals(obj.getAttributes(), 100);
    }

    @Test
    public void test_hashCode() {
        DomainDisplayGroup obj1 = new DomainDisplayGroup(50, "name1", "comment1", 100);
        DomainDisplayGroup obj2 = new DomainDisplayGroup(51, "name1", "comment1", 100);
        DomainDisplayGroup obj3 = new DomainDisplayGroup(50, "name2", "comment1", 100);
        DomainDisplayGroup obj4 = new DomainDisplayGroup(50, "name1", "comment2", 100);
        DomainDisplayGroup obj5 = new DomainDisplayGroup(50, "name1", "comment1", 101);
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
    }

    @Test
    public void test_equals() {
        DomainDisplayGroup obj1 = new DomainDisplayGroup(50, "name1", "comment1", 100);
        DomainDisplayGroup obj2 = new DomainDisplayGroup(51, "name1", "comment1", 100);
        DomainDisplayGroup obj3 = new DomainDisplayGroup(50, "name2", "comment1", 100);
        DomainDisplayGroup obj4 = new DomainDisplayGroup(50, "name1", "comment2", 100);
        DomainDisplayGroup obj5 = new DomainDisplayGroup(50, "name1", "comment1", 101);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
        assertNotEquals(obj1, obj5);
    }

    @Test
    public void test_toString_null() {
        assertEquals(new DomainDisplayGroup(50, null, null, 100).toString(),
                "DomainDisplayGroup{relativeID: 50, name: null, comment: null, attributes: 100}");
    }

    @Test
    public void test_toString() {
        assertEquals(new DomainDisplayGroup(50, "name1", "comment1", 100).toString(),
                "DomainDisplayGroup{relativeID: 50, name: \"name1\", comment: \"comment1\", attributes: 100}");
    }
}
