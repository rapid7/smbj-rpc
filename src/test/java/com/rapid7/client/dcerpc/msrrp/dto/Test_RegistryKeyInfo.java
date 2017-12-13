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
}
