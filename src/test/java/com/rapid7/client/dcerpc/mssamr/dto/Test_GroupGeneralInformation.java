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

public class Test_GroupGeneralInformation {

    @Test
    public void test_getters() {
        String name = "name1";
        String adminComment = "adminComment1";
        GroupGeneralInformation obj = new GroupGeneralInformation(name, 50, 100L, adminComment);
        assertSame(obj.getName(), name);
        assertEquals(obj.getAttributes(), 50);
        assertEquals(obj.getMemberCount(), 100L);
        assertSame(obj.getAdminComment(), adminComment);
    }

    @Test
    public void test_hashCode() {
        GroupGeneralInformation obj1 = new GroupGeneralInformation("name1", 50, 100L, "adminComment1");
        GroupGeneralInformation obj2 = new GroupGeneralInformation("name2", 50, 100L, "adminComment1");
        GroupGeneralInformation obj3 = new GroupGeneralInformation("name1", 51, 100L, "adminComment1");
        GroupGeneralInformation obj4 = new GroupGeneralInformation("name1", 50, 101L, "adminComment1");
        GroupGeneralInformation obj5 = new GroupGeneralInformation("name1", 50, 100L, "adminComment2");
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
    }

    @Test
    public void test_equals() {
        GroupGeneralInformation obj1 = new GroupGeneralInformation("name1", 50, 100L, "adminComment1");
        GroupGeneralInformation obj2 = new GroupGeneralInformation("name2", 50, 100L, "adminComment1");
        GroupGeneralInformation obj3 = new GroupGeneralInformation("name1", 51, 100L, "adminComment1");
        GroupGeneralInformation obj4 = new GroupGeneralInformation("name1", 50, 101L, "adminComment1");
        GroupGeneralInformation obj5 = new GroupGeneralInformation("name1", 50, 100L, "adminComment2");
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
        assertNotEquals(obj1, obj5);
    }

    @Test
    public void test_toString_nulls() {
        assertEquals(new GroupGeneralInformation(null, 50, 100L, null).toString(),
                "GroupGeneralInformation{name: null, attributes: 50, memberCount: 100, adminComment: null}");
    }

    @Test
    public void test_toString() {
        assertEquals(new GroupGeneralInformation("name1", 50, 100L, "adminComment1").toString(),
                "GroupGeneralInformation{name: \"name1\", attributes: 50, memberCount: 100, adminComment: \"adminComment1\"}");
    }
}
