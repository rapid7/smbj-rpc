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
package com.rapid7.client.dcerpc.msrrp.dto;

import org.junit.Test;

import com.rapid7.client.dcerpc.msrrp.dto.RegistryValueType;

import static com.rapid7.client.dcerpc.msrrp.dto.RegistryValueType.*;
import static org.junit.Assert.*;

public class Test_RegistryValueType {
    @Test
    public void REG_NONE_getTypeID() {
        assertEquals(0, REG_NONE.getTypeID());
    }

    @Test
    public void REG_NONE_is() {
        assertTrue(REG_NONE.is(0));
        assertFalse(REG_NONE.is(-1));
    }

    @Test
    public void REG_NONE_getRegistryValueType() {
        assertEquals(REG_NONE, RegistryValueType.getRegistryValueType(0));
    }

    @Test
    public void REG_SZ_getTypeID() {
        assertEquals(1, REG_SZ.getTypeID());
    }

    @Test
    public void REG_SZ_is() {
        assertTrue(REG_SZ.is(1));
        assertFalse(REG_SZ.is(-1));
    }

    @Test
    public void REG_SZ_getRegistryValueType() {
        assertEquals(REG_SZ, RegistryValueType.getRegistryValueType(1));
    }

    @Test
    public void REG_EXPAND_SZ_getTypeID() {
        assertEquals(2, REG_EXPAND_SZ.getTypeID());
    }

    @Test
    public void REG_EXPAND_SZ_is() {
        assertTrue(REG_EXPAND_SZ.is(2));
        assertFalse(REG_EXPAND_SZ.is(-1));
    }

    @Test
    public void REG_EXPAND_SZ_getRegistryValueType() {
        assertEquals(REG_EXPAND_SZ, RegistryValueType.getRegistryValueType(2));
    }

    @Test
    public void REG_BINARY_getTypeID() {
        assertEquals(3, REG_BINARY.getTypeID());
    }

    @Test
    public void REG_BINARY_is() {
        assertTrue(REG_BINARY.is(3));
        assertFalse(REG_BINARY.is(-1));
    }

    @Test
    public void REG_BINARY_getRegistryValueType() {
        assertEquals(REG_BINARY, RegistryValueType.getRegistryValueType(3));
    }

    @Test
    public void REG_DWORD_getTypeID() {
        assertEquals(4, REG_DWORD.getTypeID());
    }

    @Test
    public void REG_DWORD_is() {
        assertTrue(REG_DWORD.is(4));
        assertFalse(REG_DWORD.is(-1));
    }

    @Test
    public void REG_DWORD_getRegistryValueType() {
        assertEquals(REG_DWORD, RegistryValueType.getRegistryValueType(4));
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getTypeID() {
        assertEquals(5, REG_DWORD_BIG_ENDIAN.getTypeID());
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_is() {
        assertTrue(REG_DWORD_BIG_ENDIAN.is(5));
        assertFalse(REG_DWORD_BIG_ENDIAN.is(-1));
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getRegistryValueType() {
        assertEquals(REG_DWORD_BIG_ENDIAN, RegistryValueType.getRegistryValueType(5));
    }

    @Test
    public void REG_LINK_getTypeID() {
        assertEquals(6, REG_LINK.getTypeID());
    }

    @Test
    public void REG_LINK_is() {
        assertTrue(REG_LINK.is(6));
        assertFalse(REG_LINK.is(-1));
    }

    @Test
    public void REG_LINK_getRegistryValueType() {
        assertEquals(REG_LINK, RegistryValueType.getRegistryValueType(6));
    }

    @Test
    public void REG_MULTI_SZ_getTypeID() {
        assertEquals(7, REG_MULTI_SZ.getTypeID());
    }

    @Test
    public void REG_MULTI_SZ_is() {
        assertTrue(REG_MULTI_SZ.is(7));
        assertFalse(REG_MULTI_SZ.is(-1));
    }

    @Test
    public void REG_MULTI_SZ_getRegistryValueType() {
        assertEquals(REG_MULTI_SZ, RegistryValueType.getRegistryValueType(7));
    }

    @Test
    public void REG_RESOURCE_LIST_getTypeID() {
        assertEquals(8, REG_RESOURCE_LIST.getTypeID());
    }

    @Test
    public void REG_RESOURCE_LIST_is() {
        assertTrue(REG_RESOURCE_LIST.is(8));
        assertFalse(REG_RESOURCE_LIST.is(-1));
    }

    @Test
    public void REG_RESOURCE_LIST_getRegistryValueType() {
        assertEquals(REG_RESOURCE_LIST, RegistryValueType.getRegistryValueType(8));
    }

    @Test
    public void REG_FULL_RESOURCE_DESCRIPTOR_getTypeID() {
        assertEquals(9, REG_FULL_RESOURCE_DESCRIPTOR.getTypeID());
    }

    @Test
    public void REG_FULL_RESOURCE_DESCRIPTOR_is() {
        assertTrue(REG_FULL_RESOURCE_DESCRIPTOR.is(9));
        assertFalse(REG_FULL_RESOURCE_DESCRIPTOR.is(-1));
    }

    @Test
    public void REG_FULL_RESOURCE_DESCRIPTOR_getRegistryValueType() {
        assertEquals(REG_FULL_RESOURCE_DESCRIPTOR, RegistryValueType.getRegistryValueType(9));
    }

    @Test
    public void REG_RESOURCE_REQUIREMENTS_LIST_getTypeID() {
        assertEquals(10, REG_RESOURCE_REQUIREMENTS_LIST.getTypeID());
    }

    @Test
    public void REG_RESOURCE_REQUIREMENTS_LIST_is() {
        assertTrue(REG_RESOURCE_REQUIREMENTS_LIST.is(10));
        assertFalse(REG_RESOURCE_REQUIREMENTS_LIST.is(-1));
    }

    @Test
    public void REG_RESOURCE_REQUIREMENTS_LIST_getRegistryValueType() {
        assertEquals(REG_RESOURCE_REQUIREMENTS_LIST, RegistryValueType.getRegistryValueType(10));
    }

    @Test
    public void REG_QWORD_getTypeID() {
        assertEquals(11, REG_QWORD.getTypeID());
    }

    @Test
    public void REG_QWORD_is() {
        assertTrue(REG_QWORD.is(11));
        assertFalse(REG_QWORD.is(-1));
    }

    @Test
    public void REG_QWORD_getRegistryValueType() {
        assertEquals(REG_QWORD, RegistryValueType.getRegistryValueType(11));
    }
}
