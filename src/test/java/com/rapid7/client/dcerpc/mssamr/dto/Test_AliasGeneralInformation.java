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

public class Test_AliasGeneralInformation {

    @Test
    public void test_getters() {
        String name = "name1";
        String adminComment = "adminComment1";
        AliasGeneralInformation obj = new AliasGeneralInformation(name, 100L, adminComment);
        assertSame(obj.getName(), name);
        assertEquals(obj.getMemberCount(), 100L);
        assertSame(obj.getAdminComment(), adminComment);
    }

    @Test
    public void test_hashCode() {
        AliasGeneralInformation obj1 = new AliasGeneralInformation("name1", 100L, "adminComment1");
        AliasGeneralInformation obj2 = new AliasGeneralInformation("name2", 100L, "adminComment1");
        AliasGeneralInformation obj3 = new AliasGeneralInformation("name1", 101L, "adminComment1");
        AliasGeneralInformation obj4 = new AliasGeneralInformation("name1", 100L, "adminComment2");
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
    }

    @Test
    public void test_equals() {
        AliasGeneralInformation obj1 = new AliasGeneralInformation("name1", 100L, "adminComment1");
        AliasGeneralInformation obj2 = new AliasGeneralInformation("name2", 100L, "adminComment1");
        AliasGeneralInformation obj3 = new AliasGeneralInformation("name1", 101L, "adminComment1");
        AliasGeneralInformation obj4 = new AliasGeneralInformation("name1", 100L, "adminComment2");
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
    }

    @Test
    public void test_toString_nulls() {
        assertEquals(new AliasGeneralInformation(null, 100L, null).toString(),
                "AliasGeneralInformation{name: null, memberCount: 100, adminComment: null}");
    }

    @Test
    public void test_toString() {
        assertEquals(new AliasGeneralInformation("name1", 100L, "adminComment1").toString(),
                "AliasGeneralInformation{name: \"name1\", memberCount: 100, adminComment: \"adminComment1\"}");
    }
}
