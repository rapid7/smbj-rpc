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

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class Test_DomainPasswordInfo {

    @Test
    public void test_getters() {
        DomainPasswordInfo obj = new DomainPasswordInfo(1, 2, 3, 4L, 5L);
        assertEquals(obj.getMinimumPasswordLength(), 1);
        assertEquals(obj.getPasswordHistoryLength(), 2);
        assertEquals(obj.getPasswordProperties(), 3);
        assertEquals(obj.getMaximumPasswordAge(), 4L);
        assertEquals(obj.getMinimumPasswordAge(), 5L);
    }

    @DataProvider
    public Object[][] data_isDomainPasswordComplex() {
        return new Object[][] {
                {0, false},
                {1, true},
                {3, true}
        };
    }

    @Test(dataProvider = "data_isDomainPasswordComplex")
    public void test_isDomainPasswordComplex(int passwordProperties, boolean expect) {
        DomainPasswordInfo obj = new DomainPasswordInfo(0, 0, passwordProperties, 0L, 0L);
        assertEquals(obj.isDomainPasswordComplex(), expect);
    }

    @DataProvider
    public Object[][] data_isDomainPasswordNoAnonChange() {
        return new Object[][] {
                {0, false},
                {2, true},
                {3, true}
        };
    }

    @Test(dataProvider = "data_isDomainPasswordNoAnonChange")
    public void test_isDomainPasswordNoAnonChange(int passwordProperties, boolean expect) {
        DomainPasswordInfo obj = new DomainPasswordInfo(0, 0, passwordProperties, 0L, 0L);
        assertEquals(obj.isDomainPasswordNoAnonChange(), expect);
    }

    @DataProvider
    public Object[][] data_isDomainPasswordStoredClearText() {
        return new Object[][] {
                {0, false},
                {16, true},
                {17, true}
        };
    }

    @Test(dataProvider = "data_isDomainPasswordStoredClearText")
    public void test_isDomainPasswordStoredClearText(int passwordProperties, boolean expect) {
        DomainPasswordInfo obj = new DomainPasswordInfo(0, 0, passwordProperties, 0L, 0L);
        assertEquals(obj.isDomainPasswordStoredClearText(), expect);
    }

    @Test
    public void test_hashCode() {
        DomainPasswordInfo obj1 = new DomainPasswordInfo(1, 2, 3, 4L, 5L);
        DomainPasswordInfo obj2 = new DomainPasswordInfo(1, 20, 3, 4L, 5L);
        DomainPasswordInfo obj3 = new DomainPasswordInfo(1, 2, 30, 4L, 5L);
        DomainPasswordInfo obj4 = new DomainPasswordInfo(1, 2, 3, 40L, 5L);
        DomainPasswordInfo obj5 = new DomainPasswordInfo(1, 2, 3, 4L, 50L);
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
        assertNotEquals(obj1.hashCode(), obj4.hashCode());
        assertNotEquals(obj1.hashCode(), obj5.hashCode());
    }

    @Test
    public void test_equals() {
        DomainPasswordInfo obj1 = new DomainPasswordInfo(1, 2, 3, 4L, 5L);
        DomainPasswordInfo obj2 = new DomainPasswordInfo(1, 20, 3, 4L, 5L);
        DomainPasswordInfo obj3 = new DomainPasswordInfo(1, 2, 30, 4L, 5L);
        DomainPasswordInfo obj4 = new DomainPasswordInfo(1, 2, 3, 40L, 5L);
        DomainPasswordInfo obj5 = new DomainPasswordInfo(1, 2, 3, 4L, 50L);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
        assertNotEquals(obj1, obj4);
        assertNotEquals(obj1, obj5);
    }

    @Test
    public void test_toString() {
        assertEquals(new DomainPasswordInfo(1, 2, 3, 4L, 5L).toString(),
                "DomainPasswordInformation{minimumPasswordLength: 1, passwordHistoryLength: 2, passwordProperties: 3, maximumPasswordAge: 4, minimumPasswordAge: 5, isDomainPasswordComplex: true, isDomainPasswordNoAnonChange: true, isDomainPasswordStoredClearText: false}");
    }
}
