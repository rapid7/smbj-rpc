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
import com.rapid7.client.dcerpc.msrrp.dto.RegistryKey;
import com.rapid7.client.dcerpc.msrrp.dto.FileTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Test_RegistryKey {
    @Test(expected = IllegalArgumentException.class)
    public void constructorNullName() {
        new RegistryKey(null, new FileTime(116444736000000000l));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullLastWriteTime() {
        new RegistryKey("", null);
    }

    @Test
    public void getName() {
        final RegistryKey registryKey = new RegistryKey("test", new FileTime(116444736000000000l));
        assertEquals("test", registryKey.getName());
    }

    @Test
    public void getLastWriteTime() {
        final RegistryKey registryKey = new RegistryKey("test", new FileTime(116444736000000000l));
        assertEquals(new FileTime(116444736000000000l), registryKey.getLastWriteTime());
    }

    @Test
    public void testToString() {
        final RegistryKey registryKey1 = new RegistryKey("test", new FileTime(116444736000000000l));
        final RegistryKey registryKey2 = new RegistryKey("test", new FileTime(116444736000009999l));
        final RegistryKey registryKey3 = new RegistryKey("test", new FileTime(222222222222222222l));

        assertEquals("test {lastWriteTime=1970-01-01T00:00:00.000+0000 (116444736000000000)}", registryKey1.toString());
        assertEquals("test {lastWriteTime=1970-01-01T00:00:00.000+0000 (116444736000009999)}", registryKey2.toString());
        assertEquals("test {lastWriteTime=2305-03-13T15:30:22.222+0000 (222222222222222222)}", registryKey3.toString());
    }

    @Test
    public void testHashCode() {
        final RegistryKey registryKey1 = new RegistryKey("hello", new FileTime(116444736000000000l));
        final RegistryKey registryKey2 = new RegistryKey("hello", new FileTime(116444736000009999l));
        final RegistryKey registryKey3 = new RegistryKey("world", new FileTime(116444736000000000l));
        final RegistryKey registryKey4 = new RegistryKey("world", new FileTime(116444736000009999l));

        assertEquals(registryKey1.hashCode(), registryKey1.hashCode());
        assertNotEquals(registryKey1.hashCode(), registryKey2.hashCode());
        assertNotEquals(registryKey1.hashCode(), registryKey3.hashCode());
        assertNotEquals(registryKey1.hashCode(), registryKey4.hashCode());
    }

    @Test
    public void testEquals() {
        final RegistryKey registryKey1 = new RegistryKey("hello", new FileTime(116444736000000000l));
        final RegistryKey registryKey2 = new RegistryKey("hello", new FileTime(116444736000009999l));
        final RegistryKey registryKey3 = new RegistryKey("world", new FileTime(116444736000000000l));
        final RegistryKey registryKey4 = new RegistryKey("world", new FileTime(116444736000009999l));

        assertEquals(registryKey1, registryKey1);
        assertEquals(registryKey2, registryKey2);
        assertEquals(registryKey3, registryKey3);
        assertEquals(registryKey4, registryKey4);

        assertNotEquals(registryKey1, registryKey2);
        assertNotEquals(registryKey1, registryKey3);
        assertNotEquals(registryKey1, registryKey4);
        assertNotEquals(registryKey2, registryKey1);
        assertNotEquals(registryKey2, registryKey3);
        assertNotEquals(registryKey2, registryKey4);
        assertNotEquals(registryKey3, registryKey1);
        assertNotEquals(registryKey3, registryKey2);
        assertNotEquals(registryKey3, registryKey4);
        assertNotEquals(registryKey4, registryKey1);
        assertNotEquals(registryKey4, registryKey2);
        assertNotEquals(registryKey4, registryKey3);
    }
}
