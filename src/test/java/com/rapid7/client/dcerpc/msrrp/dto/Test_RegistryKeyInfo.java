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

import com.rapid7.client.dcerpc.msrrp.dto.RegistryKeyInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Test_RegistryKeyInfo {
    @Test
    public void getSubKeys() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0xff, 0, 0, 0, 0, 0, 0, 0l);
        assertEquals(0xff, keyInfo.getSubKeys());
    }

    @Test
    public void getMaxSubKeyLen() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0xff, 0, 0, 0, 0, 0, 0l);
        assertEquals(0xff, keyInfo.getMaxSubKeyLen());
    }

    @Test
    public void getMaxClassLen() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0, 0xff, 0, 0, 0, 0, 0l);
        assertEquals(0xff, keyInfo.getMaxClassLen());
    }

    @Test
    public void getValues() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0, 0, 0xff, 0, 0, 0, 0l);
        assertEquals(0xff, keyInfo.getValues());
    }

    @Test
    public void getMaxValueNameLen() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0, 0, 0, 0xff, 0, 0, 0l);
        assertEquals(0xff, keyInfo.getMaxValueNameLen());
    }

    @Test
    public void getMaxValueLen() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0, 0, 0, 0, 0xff, 0, 0l);
        assertEquals(0xff, keyInfo.getMaxValueLen());
    }

    @Test
    public void getSecurityDescriptor() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0, 0, 0, 0, 0, 0xff, 0l);
        assertEquals(0xff, keyInfo.getSecurityDescriptor());
    }

    @Test
    public void getLastWriteTime() {
        final RegistryKeyInfo keyInfo = new RegistryKeyInfo(0, 0, 0, 0, 0, 0, 0, 0xffl);
        assertEquals(0xffl, keyInfo.getLastWriteTime());
    }

    @Test
    public void test_hashCode() {
        int subKeys1 = 1;
        int subKeys2 = 2;
        int maxSubKeyLen1 = 3;
        int maxSubKeyLen2 = 4;
        int maxClassLen1 = 5;
        int maxClassLen2 = 6;
        int values1 = 7;
        int values2 = 8;
        int maxValueNameLen1 = 9;
        int maxValueNameLen2 = 10;
        int maxValueLen1 = 11;
        int maxValueLen2 = 12;
        int securityDescriptor1 = 13;
        int securityDescriptor2 = 14;
        int lastWriteTime1 = 15;
        int lastWriteTime2 = 16;
        RegistryKeyInfo keyInfo1 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo2 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo3 = new RegistryKeyInfo(subKeys2, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo4 = new RegistryKeyInfo(subKeys1, maxSubKeyLen2, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo5 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen2, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo6 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values2, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo7 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen2, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo8 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen2, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo9 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor2, lastWriteTime1);
        RegistryKeyInfo keyInfo10 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime2);
        assertEquals(keyInfo1.hashCode(), keyInfo2.hashCode());
        assertEquals(keyInfo1.hashCode(), keyInfo2.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo3.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo4.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo5.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo6.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo7.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo8.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo9.hashCode());
        assertNotEquals(keyInfo1.hashCode(), keyInfo10.hashCode());
    }

    @Test
    public void test_equals() {
        int subKeys1 = 1;
        int subKeys2 = 2;
        int maxSubKeyLen1 = 3;
        int maxSubKeyLen2 = 4;
        int maxClassLen1 = 5;
        int maxClassLen2 = 6;
        int values1 = 7;
        int values2 = 8;
        int maxValueNameLen1 = 9;
        int maxValueNameLen2 = 10;
        int maxValueLen1 = 11;
        int maxValueLen2 = 12;
        int securityDescriptor1 = 13;
        int securityDescriptor2 = 14;
        int lastWriteTime1 = 15;
        int lastWriteTime2 = 16;
        RegistryKeyInfo keyInfo1 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo2 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo3 = new RegistryKeyInfo(subKeys2, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo4 = new RegistryKeyInfo(subKeys1, maxSubKeyLen2, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo5 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen2, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo6 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values2, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo7 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen2, maxValueLen1, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo8 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen2, securityDescriptor1, lastWriteTime1);
        RegistryKeyInfo keyInfo9 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor2, lastWriteTime1);
        RegistryKeyInfo keyInfo10 = new RegistryKeyInfo(subKeys1, maxSubKeyLen1, maxClassLen1, values1, maxValueNameLen1, maxValueLen1, securityDescriptor1, lastWriteTime2);
        assertEquals(keyInfo1, keyInfo1);
        assertNotEquals(keyInfo1, null);
        assertNotEquals(keyInfo1, "test");
        assertEquals(keyInfo1, keyInfo2);
        assertEquals(keyInfo1, keyInfo2);
        assertNotEquals(keyInfo1, keyInfo3);
        assertNotEquals(keyInfo1, keyInfo4);
        assertNotEquals(keyInfo1, keyInfo5);
        assertNotEquals(keyInfo1, keyInfo6);
        assertNotEquals(keyInfo1, keyInfo7);
        assertNotEquals(keyInfo1, keyInfo8);
        assertNotEquals(keyInfo1, keyInfo9);
        assertNotEquals(keyInfo1, keyInfo10);
    }

    @Test
    public void test_toString() {
        int subKeys = 1;
        int maxSubKeyLen = 3;
        int maxClassLen = 5;
        int values = 7;
        int maxValueNameLen = 9;
        int maxValueLen = 11;
        int securityDescriptor = 13;
        int lastWriteTime = 15;
        RegistryKeyInfo keyInfo = new RegistryKeyInfo(subKeys, maxSubKeyLen, maxClassLen, values, maxValueNameLen, maxValueLen, securityDescriptor, lastWriteTime);
        assertEquals(keyInfo.toString(), "RegistryKeyInfo{subKeys: 1, maxSubKeyLen: 3, maxClassLen: 5, values: 7, maxValueNameLen: 9, maxValueLen: 11, securityDescriptor: 13,lastWriteTime: 15}");
    }
}
