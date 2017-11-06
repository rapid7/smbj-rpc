/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssrvs.messages;

import org.junit.Test;

import static org.junit.Assert.*;

public class Test_NetShareInfo2 {
    @Test
    public void getPermissions() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 123456789, 0, 0, null, null);
        assertEquals(123456789, share.getPermissions());
    }

    @Test
    public void getMaximumUsers() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 0, 123456789, 0, null, null);
        assertEquals(123456789, share.getMaximumUsers());
    }

    @Test
    public void getCurrentUsers() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 0, 0, 123456789, null, null);
        assertEquals(123456789, share.getCurrentUsers());
    }

    @Test
    public void getPath() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 0, 0, 0, "C:", null);
        assertEquals("C:", share.getPath());
    }

    @Test
    public void getPathIsNull() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 0, 0, 0, null, null);
        assertNull(share.getPath());
    }

    @Test
    public void getPassword() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 0, 0, 0, null, "password");
        assertEquals("password", share.getPassword());
    }

    @Test
    public void getPasswordIsNull() {
        final NetShareInfo2 share = new NetShareInfo2("", 0, null, 0, 0, 0, null, null);
        assertNull(share.getPassword());
    }

    @Test
    public void testToString() {
        final NetShareInfo2 share1 = new NetShareInfo2("share", 1, null, 2, 3, 4, null, null);
        final NetShareInfo2 share2 = new NetShareInfo2("share", 1, "comment", 2, 3, 4, "C:", "password");
        assertEquals("name=share, type=1, comment=null, permissions=2, maximumUsers=3, currentUsers=4, path=null, password=null", share1.toString());
        assertEquals("name=share, type=1, comment=comment, permissions=2, maximumUsers=3, currentUsers=4, path=C:, password=password", share2.toString());
    }

    @Test
    public void testHashCode() {
        final NetShareInfo2 share1 = new NetShareInfo2("share", 0, null, 0, 0, 0, null, null);
        final NetShareInfo2 share2 = new NetShareInfo2("", 123456789, null, 0, 0, 0, null, null);
        final NetShareInfo2 share3 = new NetShareInfo2("", 0, "comment", 0, 0, 0, null, null);
        final NetShareInfo2 share4 = new NetShareInfo2("", 0, null, 123456789, 0, 0, null, null);
        final NetShareInfo2 share5 = new NetShareInfo2("", 0, null, 0, 123456789, 0, null, null);
        final NetShareInfo2 share6 = new NetShareInfo2("", 0, null, 0, 0, 123456789, null, null);
        final NetShareInfo2 share7 = new NetShareInfo2("", 0, null, 0, 0, 0, null, null);
        final NetShareInfo2 share8 = new NetShareInfo2("", 0, null, 0, 0, 0, "C:", null);
        final NetShareInfo2 share9 = new NetShareInfo2("", 0, null, 0, 0, 0, "D:", null);
        final NetShareInfo2 share10 = new NetShareInfo2("", 0, null, 0, 0, 0, null, "password1");
        final NetShareInfo2 share11 = new NetShareInfo2("", 0, null, 0, 0, 0, null, "password2");

        assertEquals(share1.hashCode(), share1.hashCode());
        assertNotEquals(share2.hashCode(), share1.hashCode());
        assertNotEquals(share3.hashCode(), share1.hashCode());
        assertNotEquals(share4.hashCode(), share1.hashCode());
        assertNotEquals(share5.hashCode(), share1.hashCode());
        assertNotEquals(share6.hashCode(), share1.hashCode());
        assertNotEquals(share7.hashCode(), share1.hashCode());
        assertNotEquals(share8.hashCode(), share1.hashCode());
        assertNotEquals(share9.hashCode(), share1.hashCode());
        assertNotEquals(share10.hashCode(), share1.hashCode());
        assertNotEquals(share11.hashCode(), share1.hashCode());
    }

    @Test
    public void testEquals() {
        final NetShareInfo2 share1 = new NetShareInfo2("share", 0, null, 0, 0, 0, null, null);
        final NetShareInfo2 share2 = new NetShareInfo2("", 123456789, null, 0, 0, 0, null, null);
        final NetShareInfo2 share3 = new NetShareInfo2("", 0, "comment", 0, 0, 0, null, null);
        final NetShareInfo2 share4 = new NetShareInfo2("", 0, null, 123456789, 0, 0, null, null);
        final NetShareInfo2 share5 = new NetShareInfo2("", 0, null, 0, 123456789, 0, null, null);
        final NetShareInfo2 share6 = new NetShareInfo2("", 0, null, 0, 0, 123456789, null, null);
        final NetShareInfo2 share7 = new NetShareInfo2("", 0, null, 0, 0, 0, null, null);
        final NetShareInfo2 share8 = new NetShareInfo2("", 0, null, 0, 0, 0, "C:", null);
        final NetShareInfo2 share9 = new NetShareInfo2("", 0, null, 0, 0, 0, "D:", null);
        final NetShareInfo2 share10 = new NetShareInfo2("", 0, null, 0, 0, 0, null, "password1");
        final NetShareInfo2 share11 = new NetShareInfo2("", 0, null, 0, 0, 0, null, "password2");

        assertEquals(share1, share1);
        assertNotEquals(share1, share2);
        assertNotEquals(share1, share3);
        assertNotEquals(share1, share4);
        assertNotEquals(share1, share5);
        assertNotEquals(share1, share6);
        assertNotEquals(share1, share7);
        assertNotEquals(share1, share8);
        assertNotEquals(share1, share9);
        assertNotEquals(share1, share10);
        assertNotEquals(share1, share11);

        assertNotEquals(share2, share1);
        assertEquals(share2, share2);
        assertNotEquals(share2, share3);
        assertNotEquals(share2, share4);
        assertNotEquals(share2, share5);
        assertNotEquals(share2, share6);
        assertNotEquals(share2, share7);
        assertNotEquals(share2, share8);
        assertNotEquals(share2, share9);
        assertNotEquals(share2, share10);
        assertNotEquals(share2, share11);

        assertNotEquals(share3, share1);
        assertNotEquals(share3, share2);
        assertEquals(share3, share3);
        assertNotEquals(share3, share4);
        assertNotEquals(share3, share5);
        assertNotEquals(share3, share6);
        assertNotEquals(share3, share7);
        assertNotEquals(share3, share8);
        assertNotEquals(share3, share9);
        assertNotEquals(share3, share10);
        assertNotEquals(share3, share11);

        assertNotEquals(share4, share1);
        assertNotEquals(share4, share2);
        assertNotEquals(share4, share3);
        assertEquals(share4, share4);
        assertNotEquals(share4, share5);
        assertNotEquals(share4, share6);
        assertNotEquals(share4, share7);
        assertNotEquals(share4, share8);
        assertNotEquals(share4, share9);
        assertNotEquals(share4, share10);
        assertNotEquals(share4, share11);

        assertNotEquals(share5, share1);
        assertNotEquals(share5, share2);
        assertNotEquals(share5, share3);
        assertNotEquals(share5, share4);
        assertEquals(share5, share5);
        assertNotEquals(share5, share6);
        assertNotEquals(share5, share7);
        assertNotEquals(share5, share8);
        assertNotEquals(share5, share9);
        assertNotEquals(share5, share10);
        assertNotEquals(share5, share11);

        assertNotEquals(share6, share1);
        assertNotEquals(share6, share2);
        assertNotEquals(share6, share3);
        assertNotEquals(share6, share4);
        assertNotEquals(share6, share5);
        assertEquals(share6, share6);
        assertNotEquals(share6, share7);
        assertNotEquals(share6, share8);
        assertNotEquals(share6, share9);
        assertNotEquals(share6, share10);
        assertNotEquals(share6, share11);

        assertNotEquals(share7, share1);
        assertNotEquals(share7, share2);
        assertNotEquals(share7, share3);
        assertNotEquals(share7, share4);
        assertNotEquals(share7, share5);
        assertNotEquals(share7, share6);
        assertEquals(share7, share7);
        assertNotEquals(share7, share8);
        assertNotEquals(share7, share9);
        assertNotEquals(share7, share10);
        assertNotEquals(share7, share11);

        assertNotEquals(share8, share1);
        assertNotEquals(share8, share2);
        assertNotEquals(share8, share3);
        assertNotEquals(share8, share4);
        assertNotEquals(share8, share5);
        assertNotEquals(share8, share6);
        assertNotEquals(share8, share7);
        assertEquals(share8, share8);
        assertNotEquals(share8, share9);
        assertNotEquals(share8, share10);
        assertNotEquals(share8, share11);

        assertNotEquals(share9, share1);
        assertNotEquals(share9, share2);
        assertNotEquals(share9, share3);
        assertNotEquals(share9, share4);
        assertNotEquals(share9, share5);
        assertNotEquals(share9, share6);
        assertNotEquals(share9, share7);
        assertNotEquals(share9, share8);
        assertEquals(share9, share9);
        assertNotEquals(share9, share10);
        assertNotEquals(share9, share11);

        assertNotEquals(share10, share1);
        assertNotEquals(share10, share2);
        assertNotEquals(share10, share3);
        assertNotEquals(share10, share4);
        assertNotEquals(share10, share5);
        assertNotEquals(share10, share6);
        assertNotEquals(share10, share7);
        assertNotEquals(share10, share8);
        assertNotEquals(share10, share9);
        assertEquals(share10, share10);
        assertNotEquals(share10, share11);

        assertNotEquals(share11, share1);
        assertNotEquals(share11, share2);
        assertNotEquals(share11, share3);
        assertNotEquals(share11, share4);
        assertNotEquals(share11, share5);
        assertNotEquals(share11, share6);
        assertNotEquals(share11, share7);
        assertNotEquals(share11, share8);
        assertNotEquals(share11, share9);
        assertNotEquals(share11, share10);
        assertEquals(share11, share11);
    }
}
