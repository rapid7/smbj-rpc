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

package com.rapid7.client.dcerpc.dto;

import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_ContextHandle {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting non-null handle")
    public void test_init_null() {
        new ContextHandle(null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting 20 entries in handle, got: 19")
    public void test_init_small() {
        new ContextHandle(new byte[19]);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting 20 entries in handle, got: 21")
    public void test_init_large() {
        new ContextHandle(new byte[21]);
    }

    @Test
    public void test_getBytes() {
        byte[] handle = new byte[20];
        ContextHandle obj = new ContextHandle(handle);
        assertSame(obj.getBytes(), handle);
    }

    @Test
    public void test_hashCode() {
        byte[] handle1 = new byte[20];
        ContextHandle obj1 = new ContextHandle(handle1);
        byte[] handle2 = new byte[20];
        handle2[5] = (byte) 100;
        ContextHandle obj2 = new ContextHandle(handle2);
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        byte[] handle1 = new byte[20];
        ContextHandle obj1 = new ContextHandle(handle1);
        byte[] handle2 = new byte[20];
        handle2[5] = (byte) 100;
        ContextHandle obj2 = new ContextHandle(handle2);
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, obj2);
    }

    @Test
    public void test_toString() {
        byte[] handle = Hex.decode("0102030405060708090A0B0C0D0E0F1011121314");
        assertEquals(new ContextHandle(handle).toString(), "0102030405060708090A0B0C0D0E0F1011121314");
    }
}
