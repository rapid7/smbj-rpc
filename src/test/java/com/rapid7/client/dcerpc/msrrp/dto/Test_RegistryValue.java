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

import java.io.IOException;
import org.junit.Test;

import com.rapid7.client.dcerpc.msrrp.dto.RegistryValue;
import com.rapid7.client.dcerpc.msrrp.dto.RegistryValueType;

import static org.junit.Assert.*;

public class Test_RegistryValue {
    @Test(expected = IllegalArgumentException.class)
    public void constructorNullName() throws IOException {
        new RegistryValue(null, RegistryValueType.REG_SZ, new byte[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullType() throws IOException {
        new RegistryValue("test", null, new byte[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullData() throws IOException {
        new RegistryValue("test", RegistryValueType.REG_SZ, null);
    }

    @Test
    public void getName() throws IOException {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[]{});
        assertEquals("test", value.getName());
    }

    @Test
    public void getType() throws IOException {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[]{});
        assertEquals(RegistryValueType.REG_SZ, value.getType());
    }

    @Test(expected = IOException.class)
    public void REG_SZ_badLength1() throws IOException {
        final byte[] data = new byte[]{'?'};
        new RegistryValue("test", RegistryValueType.REG_SZ, data);
    }

    @Test(expected = IOException.class)
    public void REG_SZ_badLength2() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0};
        new RegistryValue("test", RegistryValueType.REG_SZ, data);
    }

    @Test
    public void REG_SZ_getData() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertArrayEquals(data, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_SZ_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        value.getDataAsInt();
    }

    @Test(expected = IllegalStateException.class)
    public void REG_SZ_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        value.getDataAsLong();
    }

    @Test
    public void REG_SZ_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("00111111000000000000000000000000", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_SZ_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("3F000000", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_SZ_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_SZ_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("?", value.getDataAsStr());
    }

    @Test
    public void REG_SZ_toString() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("test (REG_SZ) = ? (0x3F000000)", value.toString());
    }

    @Test
    public void REG_SZ_toString_empty() throws IOException {
        final byte[] data = new byte[]{};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("test (REG_SZ)", value.toString());
    }

    @Test
    public void REG_SZ_toString_null1() throws IOException {
        final byte[] data = new byte[]{0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("test (REG_SZ)", value.toString());
    }

    @Test
    public void REG_SZ_toString_null2() throws IOException {
        final byte[] data = new byte[]{0, 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, data);
        assertEquals("test (REG_SZ)", value.toString());
    }

    @Test(expected = IOException.class)
    public void REG_EXPAND_SZ_badLength1() throws IOException {
        final byte[] data = new byte[]{'?'};
        new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
    }

    @Test(expected = IOException.class)
    public void REG_EXPAND_SZ_badLength2() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0};
        new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
    }

    @Test
    public void REG_EXPAND_SZ_getData() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertArrayEquals(data, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_EXPAND_SZ_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        value.getDataAsInt();
    }

    @Test(expected = IllegalStateException.class)
    public void REG_EXPAND_SZ_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        value.getDataAsLong();
    }

    @Test
    public void REG_EXPAND_SZ_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("00111111000000000000000000000000", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_EXPAND_SZ_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("3F000000", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_EXPAND_SZ_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_EXPAND_SZ_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("?", value.getDataAsStr());
    }

    @Test
    public void REG_EXPAND_SZ_toString() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("test (REG_EXPAND_SZ) = ? (0x3F000000)", value.toString());
    }

    @Test
    public void REG_EXPAND_SZ_toString_empty() throws IOException {
        final byte[] data = new byte[]{};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("test (REG_EXPAND_SZ)", value.toString());
    }

    @Test
    public void REG_EXPAND_SZ_toString_null1() throws IOException {
        final byte[] data = new byte[]{0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("test (REG_EXPAND_SZ)", value.toString());
    }

    @Test
    public void REG_EXPAND_SZ_toString_null2() throws IOException {
        final byte[] data = new byte[]{0, 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_EXPAND_SZ, data);
        assertEquals("test (REG_EXPAND_SZ)", value.toString());
    }

    @Test(expected = IOException.class)
    public void REG_LINK_badLength1() throws IOException {
        final byte[] data = new byte[]{'?'};
        new RegistryValue("test", RegistryValueType.REG_LINK, data);
    }

    @Test(expected = IOException.class)
    public void REG_LINK_badLength2() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0};
        new RegistryValue("test", RegistryValueType.REG_LINK, data);
    }

    @Test
    public void REG_LINK_getData() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertArrayEquals(data, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_LINK_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        value.getDataAsInt();
    }

    @Test(expected = IllegalStateException.class)
    public void REG_LINK_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        value.getDataAsLong();
    }

    @Test
    public void REG_LINK_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("00111111000000000000000000000000", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_LINK_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("3F000000", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_LINK_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_LINK_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("?", value.getDataAsStr());
    }

    @Test
    public void REG_LINK_toString() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("test (REG_LINK) = ? (0x3F000000)", value.toString());
    }

    @Test
    public void REG_LINK_toString_empty() throws IOException {
        final byte[] data = new byte[]{};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("test (REG_LINK)", value.toString());
    }

    @Test
    public void REG_LINK_toString_null1() throws IOException {
        final byte[] data = new byte[]{0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("test (REG_LINK)", value.toString());
    }

    @Test
    public void REG_LINK_toString_null2() throws IOException {
        final byte[] data = new byte[]{0, 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_LINK, data);
        assertEquals("test (REG_LINK)", value.toString());
    }

    @Test(expected = IOException.class)
    public void REG_MULTI_SZ_badLength1() throws IOException {
        final byte[] data = new byte[]{'?'};
        new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
    }

    @Test(expected = IOException.class)
    public void REG_MULTI_SZ_badLength2() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0};
        new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
    }

    @Test
    public void REG_MULTI_SZ_getData() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertArrayEquals(data, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_MULTI_SZ_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        value.getDataAsInt();
    }

    @Test(expected = IllegalStateException.class)
    public void REG_MULTI_SZ_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        value.getDataAsLong();
    }

    @Test
    public void REG_MULTI_SZ_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("00111111000000000000000000000000", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_MULTI_SZ_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("3F000000", value.getDataAsHexStr());
    }

    @Test
    public void REG_MULTI_SZ_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertArrayEquals(new String[]{"?"}, value.getDataAsMultiStr());
    }

    @Test
    public void REG_MULTI_SZ_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("{\"?\"}", value.getDataAsStr());
    }

    @Test
    public void REG_MULTI_SZ_toString() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("test (REG_MULTI_SZ) = {\"?\"} (0x3F000000)", value.toString());
    }

    @Test
    public void REG_MULTI_SZ_toString_empty() throws IOException {
        final byte[] data = new byte[]{};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("test (REG_MULTI_SZ)", value.toString());
    }

    @Test
    public void REG_MULTI_SZ_toString_null1() throws IOException {
        final byte[] data = new byte[]{0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("test (REG_MULTI_SZ)", value.toString());
    }

    @Test
    public void REG_MULTI_SZ_toString_null2() throws IOException {
        final byte[] data = new byte[]{0, 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("test (REG_MULTI_SZ) = {\"\"} (0x00000000)", value.toString());
    }

    @Test
    public void REG_MULTI_SZ_toString_multiple() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0, '?', 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("test (REG_MULTI_SZ) = {\"?\", \"?\"} (0x3F0000003F000000)", value.toString());
    }

    @Test
    public void REG_MULTI_SZ_toString_multipleWithNull() throws IOException {
        final byte[] data = new byte[]{'?', 0, 0, 0, '?', 0, 0, 0, 0, 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_MULTI_SZ, data);
        assertEquals("test (REG_MULTI_SZ) = {\"?\", \"?\"} (0x3F0000003F0000000000)", value.toString());
    }

    @Test(expected = IOException.class)
    public void REG_DWORD_badLength1() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff};
        new RegistryValue("test", RegistryValueType.REG_DWORD, data);
    }

    @Test(expected = IOException.class)
    public void REG_DWORD_badLength2() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        new RegistryValue("test", RegistryValueType.REG_DWORD, data);
    }

    @Test
    public void REG_DWORD_getData() throws IOException {
        final byte[] originalData = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final byte[] modifiedData = new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, originalData);
        assertArrayEquals(modifiedData, value.getData());
    }

    @Test
    public void REG_DWORD_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        assertEquals(0x12345678, value.getDataAsInt());
    }

    @Test
    public void REG_DWORD_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        assertEquals(0x12345678, value.getDataAsLong());
    }

    @Test
    public void REG_DWORD_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        assertEquals("00010010001101000101011001111000", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_DWORD_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        assertEquals("12345678", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_DWORD_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_DWORD_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        assertEquals("305419896", value.getDataAsStr());
    }

    @Test
    public void REG_DWORD_toString() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD, data);
        assertEquals("test (REG_DWORD) = 305419896 (0x12345678)", value.toString());
    }

    @Test(expected = IOException.class)
    public void REG_DWORD_BIG_ENDIAN_badLengt1() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff};
        new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
    }

    @Test(expected = IOException.class)
    public void REG_DWORD_BIG_ENDIAN_badLength2() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getData() throws IOException {
        final byte[] originalData = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final byte[] modifiedData = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, originalData);
        assertArrayEquals(modifiedData, value.getData());
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        assertEquals(0x78563412, value.getDataAsInt());
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        assertEquals(0x78563412, value.getDataAsLong());
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        assertEquals("01111000010101100011010000010010", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        assertEquals("78563412", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_DWORD_BIG_ENDIAN_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        assertEquals("2018915346", value.getDataAsStr());
    }

    @Test
    public void REG_DWORD_BIG_ENDIAN_toString() throws IOException {
        final byte[] data = new byte[]{(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, data);
        assertEquals("test (REG_DWORD_BIG_ENDIAN) = 2018915346 (0x78563412)", value.toString());
    }

    @Test(expected = IOException.class)
    public void REG_QWORD_badLengt1() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        new RegistryValue("test", RegistryValueType.REG_QWORD, data);
    }

    @Test(expected = IOException.class)
    public void REG_QWORD_badLength2() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        new RegistryValue("test", RegistryValueType.REG_QWORD, data);
    }

    @Test
    public void REG_QWORD_getData() throws IOException {
        final byte[] originalData = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final byte[] modifiedData = new byte[]{(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78, (byte) 0x9a, (byte) 0xbc, (byte) 0xde, (byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, originalData);
        assertArrayEquals(modifiedData, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_QWORD_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        value.getDataAsInt();
    }

    @Test
    public void REG_QWORD_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        assertEquals(0x123456789abcdeffl, value.getDataAsLong());
    }

    @Test
    public void REG_QWORD_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        assertEquals("0001001000110100010101100111100010011010101111001101111011111111", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_QWORD_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        assertEquals("123456789ABCDEFF", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_QWORD_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_QWORD_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        assertEquals("1311768467463790335", value.getDataAsStr());
    }

    @Test
    public void REG_QWORD_toString() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff, (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, data);
        assertEquals("test (REG_QWORD) = 1311768467463790335 (0x123456789ABCDEFF)", value.toString());
    }

    @Test
    public void REG_BINARY_getData() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        assertArrayEquals(data, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_BINARY_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        value.getDataAsInt();
    }

    @Test(expected = IllegalStateException.class)
    public void REG_BINARY_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        value.getDataAsLong();
    }

    @Test
    public void REG_BINARY_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        assertEquals("11111111", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_BINARY_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        assertEquals("FF", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_BINARY_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_BINARY_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        assertEquals("11111111", value.getDataAsStr());
    }

    @Test
    public void REG_BINARY_toString() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_BINARY, data);
        assertEquals("test (REG_BINARY) = 11111111 (0xFF)", value.toString());
    }

    @Test
    public void REG_NONE_getData() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        assertArrayEquals(data, value.getData());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_NONE_getDataAsInt() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        value.getDataAsInt();
    }

    @Test(expected = IllegalStateException.class)
    public void REG_NONE_getDataAsLong() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        value.getDataAsLong();
    }

    @Test
    public void REG_NONE_getDataAsBinaryStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        assertEquals("11111111", value.getDataAsBinaryStr());
    }

    @Test
    public void REG_NONE_getDataAsHexStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        assertEquals("FF", value.getDataAsHexStr());
    }

    @Test(expected = IllegalStateException.class)
    public void REG_NONE_getDataAsMultiStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        value.getDataAsMultiStr();
    }

    @Test
    public void REG_NONE_getDataAsStr() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        assertEquals("11111111", value.getDataAsStr());
    }

    @Test
    public void REG_NONE_toString() throws IOException {
        final byte[] data = new byte[]{(byte) 0xff};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_NONE, data);
        assertEquals("test (REG_NONE) = 11111111 (0xFF)", value.toString());
    }

    @Test
    public void testHashCode() throws IOException {
        final RegistryValue value1 = new RegistryValue("test1", RegistryValueType.REG_NONE, new byte[]{0});
        final RegistryValue value2 = new RegistryValue("test1", RegistryValueType.REG_NONE, new byte[]{1});
        final RegistryValue value3 = new RegistryValue("test2", RegistryValueType.REG_NONE, new byte[]{0});
        final RegistryValue value4 = new RegistryValue("test2", RegistryValueType.REG_BINARY, new byte[]{0});

        assertEquals(value1.hashCode(), value1.hashCode());
        assertNotEquals(value1.hashCode(), value2.hashCode());
        assertNotEquals(value1.hashCode(), value3.hashCode());
        assertNotEquals(value1.hashCode(), value4.hashCode());
    }

    @Test
    public void testEquals() throws IOException {
        final RegistryValue value1 = new RegistryValue("test1", RegistryValueType.REG_NONE, new byte[]{0});
        final RegistryValue value2 = new RegistryValue("test1", RegistryValueType.REG_NONE, new byte[]{1});
        final RegistryValue value3 = new RegistryValue("test2", RegistryValueType.REG_NONE, new byte[]{0});
        final RegistryValue value4 = new RegistryValue("test2", RegistryValueType.REG_BINARY, new byte[]{0});

        assertEquals(value1, value1);
        assertNotEquals(value1, value2);
        assertNotEquals(value1, value3);
        assertNotEquals(value1, value4);

        assertNotEquals(value2, value1);
        assertEquals(value2, value2);
        assertNotEquals(value2, value3);
        assertNotEquals(value2, value4);

        assertNotEquals(value3, value1);
        assertNotEquals(value3, value2);
        assertEquals(value3, value3);
        assertNotEquals(value3, value4);

        assertNotEquals(value4, value1);
        assertNotEquals(value4, value2);
        assertNotEquals(value4, value3);
        assertEquals(value4, value4);
    }
}
