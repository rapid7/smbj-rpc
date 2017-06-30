/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msrrp;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class Test_RegistryValue {
    @Test(expected = IllegalArgumentException.class)
    public void constructorNullName() {
        new RegistryValue(null, RegistryValueType.REG_SZ, new byte[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullType() {
        new RegistryValue("test", null, new byte[] {});
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullData() {
        new RegistryValue("test", RegistryValueType.REG_SZ, null);
    }

    @Test
    public void getName() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {});
        assertEquals("test", value.getName());
    }

    @Test
    public void getType() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {});
        assertEquals(RegistryValueType.REG_SZ, value.getType());
    }

    @Test
    public void getData() {
        final byte[] originalData = new byte[] {'?', 0, 0, 0};
        final byte[] modifiedData = new byte[] {'?', 0};
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, originalData);
        assertArrayEquals(modifiedData, value.getData());
    }

    @Test
    public void regDWORD() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD,
            new byte[] {(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12});
        assertEquals("test (REG_DWORD) = 0x12345678", value.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void regDWORDInvalidDataLength() {
        new RegistryValue("test", RegistryValueType.REG_DWORD, new byte[] {});
    }

    @Test
    public void regDWORDBE() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN,
            new byte[] {(byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12});
        assertEquals("test (REG_DWORD_BIG_ENDIAN) = 0x78563412", value.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void regDWORDBEInvalidDataLength() {
        new RegistryValue("test", RegistryValueType.REG_DWORD_BIG_ENDIAN, new byte[] {});
    }

    @Test
    public void regQWORD() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_QWORD, new byte[] {(byte) 0xff,
            (byte) 0xde, (byte) 0xbc, (byte) 0x9a, (byte) 0x78, (byte) 0x56, (byte) 0x34, (byte) 0x12});
        assertEquals("test (REG_QWORD) = 0x123456789ABCDEFF", value.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void regQWORDInvalidDataLength() {
        new RegistryValue("test", RegistryValueType.REG_QWORD, new byte[] {});
    }

    @Test
    public void regSZEmpty() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {});
        assertEquals("test (REG_SZ)", value.toString());
    }

    @Test
    public void regSZNull() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {0, 0});
        assertEquals("test (REG_SZ)", value.toString());
    }

    @Test
    public void regSZQuestion() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {'?', 0});
        assertEquals("test (REG_SZ) = ?", value.toString());
    }

    @Test
    public void regSZQuestionNull() {
        final RegistryValue value = new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {'?', 0, 0, 0});
        assertEquals("test (REG_SZ) = ?", value.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void regSZInvalidDataLength() {
        new RegistryValue("test", RegistryValueType.REG_SZ, new byte[] {'?'});
    }
}
