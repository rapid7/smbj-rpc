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

import org.testng.annotations.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertSame;

public class Test_SID {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting non-null identifierAuthority")
    public void test_init_NullIdentifierAuthority() {
        new SID((byte) 200, null, new long[]{1, 2, 3});
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expected 6 entries in identifierAuthority, got: 5")
    public void test_init_IdentifierAuthorityWrongLength() {
        new SID((byte) 200, new byte[]{1, 2, 3, 4, 5}, new long[]{1, 2, 3});
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting non-null subAuthorities")
    public void test_init_NullSubAuthorities() {
        new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 6}, null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expected at least one entry in subAuthorities, got: 0")
    public void test_init_SubAuthoritiesWrongLength() {
        new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 6}, new long[]{});
    }

    @Test
    public void test_getters() {
        byte[] identifierAuthority = new byte[]{1, 2, 3, 4, 5, 6};
        long[] subAuthorities = new long[]{1, 2, 3};
        SID sid = new SID((byte) 200, identifierAuthority, subAuthorities);
        assertEquals(sid.getRevision(), (byte) 200);
        assertSame(sid.getIdentifierAuthority(), identifierAuthority);
        assertSame(sid.getSubAuthorities(), subAuthorities);
        assertEquals(sid.getRelativeID(), 3);
    }

    @Test
    public void test_resolveRelativeID() {
        SID sid1 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 6}, new long[]{1, 2, 3});
        SID sid2 = sid1.resolveRelativeID(500);
        // Ensure object is a clone
        assertNotSame(sid1.getIdentifierAuthority(), sid2.getIdentifierAuthority());
        assertNotSame(sid1.getSubAuthorities(), sid2.getSubAuthorities());
        assertNotEquals(sid1, sid2);
        // Check new object
        assertEquals(sid2.getRevision(), (byte) 200);
        assertEquals(sid2.getIdentifierAuthority(), new byte[]{1, 2, 3, 4, 5, 6});
        assertEquals(sid2.getSubAuthorities(), new long[] {1, 2, 3, 500});
        assertEquals(sid2.getRelativeID(), 500);
    }

    @Test
    public void test_hashCode() {
        SID sid1 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 6}, new long[]{1, 2, 3});
        SID sid2 = new SID((byte) 201, new byte[]{1, 2, 3, 4, 5, 6}, new long[]{1, 2, 3});
        SID sid3 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 5}, new long[]{1, 2, 3});
        SID sid4 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 5}, new long[]{1});
        assertEquals(sid1.hashCode(), sid1.hashCode());
        assertNotEquals(sid1.hashCode(), sid2.hashCode());
        assertNotEquals(sid1.hashCode(), sid3.hashCode());
        assertNotEquals(sid1.hashCode(), sid4.hashCode());
    }

    @Test
    public void test_equals() {
        SID sid1 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 6}, new long[]{1, 2, 3});
        SID sid2 = new SID((byte) 201, new byte[]{1, 2, 3, 4, 5, 6}, new long[]{1, 2, 3});
        SID sid3 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 5}, new long[]{1, 2, 3});
        SID sid4 = new SID((byte) 200, new byte[]{1, 2, 3, 4, 5, 5}, new long[]{1});
        assertEquals(sid1, sid1);
        assertNotEquals(sid1, sid2);
        assertNotEquals(sid1, sid3);
        assertNotEquals(sid1, sid4);
    }

    @Test(expectedExceptions = { SID.MalformedSIDStringException.class })
    public void test_fromString_Malformed() {
        SID.fromString("MALFORMED");
    }

    @Test(expectedExceptions = {SID.MalformedSIDStringException.class},
            expectedExceptionsMessageRegExp = "SID must start with S: -1-5")
    public void test_fromString_NoSPrefix() {
        SID.fromString("-1-5");
    }

    @Test(expectedExceptions = {SID.MalformedSIDStringException.class},
            expectedExceptionsMessageRegExp = "Illegal SID format: S-1-")
    public void test_fromString_NoIdentifierAuthority() {
        SID.fromString("S-1-");
    }

    @Test(expectedExceptions = {SID.MalformedSIDStringException.class},
            expectedExceptionsMessageRegExp = "Expecting at least one sub authority: S-1-5")
    public void test_fromString_test() {
        SID.fromString("S-1-5");
    }

    @Test
    public void test_fromString() {
        SID sid = SID.fromString("S-1-5-333-444-5");
        assertEquals(sid.getRevision(), 1);
        assertArrayEquals(sid.getIdentifierAuthority(), new byte[] { 0, 0, 0, 0, 0, 5 });
        assertArrayEquals(sid.getSubAuthorities(), new long[] { 333, 444, 5 });
    }

    @Test
    public void test_to_and_from_String() {
        String sid_string = "S-1-5-32";
        SID sid = SID.fromString(sid_string);
        assertEquals(sid_string, sid.toString());

        SID sid2 = new SID((byte) 200, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{2, 5, 7});
        assertEquals(SID.fromString(sid2.toString()), sid2);
    }
}
