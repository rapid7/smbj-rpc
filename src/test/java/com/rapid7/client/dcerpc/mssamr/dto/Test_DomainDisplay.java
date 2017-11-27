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

public class Test_DomainDisplay {

    @Test
    public void test_getters() {
        String name = "name1";
        String comment = "comment1";
        DomainDisplay obj = new DomainDisplay(50, name, comment);
        assertEquals(obj.getRelativeID(), 50);
        assertSame(obj.getName(), name);
        assertSame(obj.getComment(), comment);
    }

    @Test
    public void test_hashCode() {
        DomainDisplay obj1 = new DomainDisplay(50, "name1", "comment1");
        DomainDisplay obj2 = new DomainDisplay(51, "name1", "comment1");
        DomainDisplay obj3 = new DomainDisplay(50, "name2", "comment1");
        DomainDisplay obj4 = new DomainDisplay(50, "name1", "comment2");
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
    }

    @Test
    public void test_equals() {
        DomainDisplay obj1 = new DomainDisplay(50, "name1", "comment1");
        DomainDisplay obj2 = new DomainDisplay(51, "name1", "comment1");
        DomainDisplay obj3 = new DomainDisplay(50, "name2", "comment1");
        DomainDisplay obj4 = new DomainDisplay(50, "name1", "comment2");
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
    }

    @Test
    public void test_toString_null() {
        assertEquals(new DomainDisplay(50, null, null).toString(),
                "DomainDisplay{relativeID: 50, name: null, comment: null}");
    }

    @Test
    public void test_toString() {
        assertEquals(new DomainDisplay(50, "name1", "comment1").toString(),
                "DomainDisplay{relativeID: 50, name: \"name1\", comment: \"comment1\"}");
    }
}
