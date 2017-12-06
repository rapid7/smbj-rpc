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

package com.rapid7.client.dcerpc.objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class Test_WChar {

    @Test
    public void test_NullTerminated_of() {
        String str = "test";
        WChar.NullTerminated obj = WChar.NullTerminated.of(str);
        assertSame(obj.getValue(), str);
        assertTrue(obj.isNullTerminated());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expected non-null value")
    public void test_NullTerminated_of_null() {
        WChar.NullTerminated.of(null);
    }

    @Test
    public void test_NonNullTerminated_of() {
        String str = "test";
        WChar.NonNullTerminated obj = WChar.NonNullTerminated.of(str);
        assertSame(obj.getValue(), str);
        assertFalse(obj.isNullTerminated());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expected non-null value")
    public void test_NonNullTerminated_of_null() {
        WChar.NonNullTerminated.of(null);
    }

    @DataProvider
    public Object[][] data_isNullTerminated() {
        return new Object[][] {
                {false},
                {true}
        };
    }

    @Test(dataProvider = "data_isNullTerminated")
    public void test_init_default(boolean nullTerminated) {
        WChar wChar = create(nullTerminated);
        assertSame(wChar.getValue(), "");
        assertEquals(wChar.isNullTerminated(), nullTerminated);
    }

    @Test(dataProvider = "data_isNullTerminated",
            expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expected non-null value")
    public void test_setValue_null(boolean nullTerminated) {
        WChar wChar = create(nullTerminated);
        wChar.setValue(null);
    }

    @Test(dataProvider = "data_isNullTerminated")
    public void test_setValue(boolean nullTerminated) {
        WChar wChar = create(nullTerminated);
        String value = "test";
        wChar.setValue(value);
        assertSame(wChar.getValue(), value);
    }

    @Test
    public void test_hashCode() {
        WChar obj_nt1 = new WChar.NullTerminated();
        WChar obj_nt2 = new WChar.NullTerminated();
        obj_nt2.setValue("test");
        WChar obj_ntn1 = new WChar.NonNullTerminated();
        WChar obj_ntn2 = new WChar.NonNullTerminated();
        obj_ntn2.setValue("test");
        assertEquals(obj_nt1.hashCode(), obj_nt1.hashCode());
        assertNotEquals(obj_nt1.hashCode(), obj_nt2.hashCode());
        assertNotEquals(obj_nt1.hashCode(), obj_ntn1.hashCode());
        assertNotEquals(obj_nt1.hashCode(), obj_ntn2.hashCode());
    }

    @Test
    public void test_equals() {
        WChar obj_nt1 = new WChar.NullTerminated();
        WChar obj_nt2 = new WChar.NullTerminated();
        obj_nt2.setValue("test");
        WChar obj_ntn1 = new WChar.NonNullTerminated();
        WChar obj_ntn2 = new WChar.NonNullTerminated();
        obj_ntn2.setValue("test");
        assertEquals(obj_nt1, obj_nt1);
        assertNotEquals(obj_nt1, obj_nt2);
        assertNotEquals(obj_nt1, obj_ntn1);
        assertNotEquals(obj_nt1, obj_ntn2);
    }

    @Test(dataProvider = "data_isNullTerminated")
    public void test_toString_default(boolean nullTerminated) {
        WChar obj = create(nullTerminated);
        assertEquals(obj.toString(), "\"\"");
    }

    @Test(dataProvider = "data_isNullTerminated")
    public void test_toString(boolean nullTerminated) {
        WChar obj = create(nullTerminated);
        String str = "testƟ123";
        obj.setValue(str);
        assertEquals(obj.toString(), "\"testƟ123\"");
    }

    // Marshalling logic is tested by Test_RPCUnicodeString

    private WChar create(boolean nullTerminated) {
        return nullTerminated ? new WChar.NullTerminated() : new WChar.NonNullTerminated();
    }
}
