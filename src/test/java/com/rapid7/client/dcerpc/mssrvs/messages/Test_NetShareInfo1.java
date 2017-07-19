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
package com.rapid7.client.dcerpc.mssrvs.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class Test_NetShareInfo1 {
    @Test
    public void getType() {
        final NetShareInfo1 share = new NetShareInfo1("", 123456789, null);
        assertEquals(123456789, share.getType());
    }

    @Test
    public void getComment() {
        final NetShareInfo1 share = new NetShareInfo1("", 0, "comment");
        assertEquals("comment", share.getComment());
    }

    @Test
    public void getCommentIsNull() {
        final NetShareInfo1 share = new NetShareInfo1("", 0, null);
        assertNull("comment", share.getComment());
    }

    @Test
    public void testToString() {
        final NetShareInfo1 share1 = new NetShareInfo1("share", 1, null);
        final NetShareInfo1 share2 = new NetShareInfo1("share", 1, "comment");
        assertEquals("name=share, type=1, comment=null", share1.toString());
        assertEquals("name=share, type=1, comment=comment", share2.toString());
    }

    @Test
    public void testHashCode() {
        final NetShareInfo1 share1 = new NetShareInfo1("share", 0, null);
        final NetShareInfo1 share2 = new NetShareInfo1("", 0, null);
        final NetShareInfo1 share3 = new NetShareInfo1("", 123456789, null);
        final NetShareInfo1 share4 = new NetShareInfo1("", 123456789, "comment1");
        final NetShareInfo1 share5 = new NetShareInfo1("", 123456789, "comment2");

        assertEquals(share1.hashCode(), share1.hashCode());
        assertNotEquals(share2.hashCode(), share1.hashCode());
        assertNotEquals(share3.hashCode(), share1.hashCode());
        assertNotEquals(share4.hashCode(), share1.hashCode());
        assertNotEquals(share5.hashCode(), share1.hashCode());
    }

    @Test
    public void testEquals() {
        final NetShareInfo1 share1 = new NetShareInfo1("share", 0, null);
        final NetShareInfo1 share2 = new NetShareInfo1("", 0, null);
        final NetShareInfo1 share3 = new NetShareInfo1("", 123456789, null);
        final NetShareInfo1 share4 = new NetShareInfo1("", 123456789, "comment1");
        final NetShareInfo1 share5 = new NetShareInfo1("", 123456789, "comment2");

        assertEquals(share1, share1);
        assertNotEquals(share1, share2);
        assertNotEquals(share1, share3);
        assertNotEquals(share1, share4);
        assertNotEquals(share1, share5);

        assertNotEquals(share2, share1);
        assertEquals(share2, share2);
        assertNotEquals(share2, share3);
        assertNotEquals(share2, share4);
        assertNotEquals(share2, share5);

        assertNotEquals(share3, share1);
        assertNotEquals(share3, share2);
        assertEquals(share3, share3);
        assertNotEquals(share3, share4);
        assertNotEquals(share3, share5);

        assertNotEquals(share4, share1);
        assertNotEquals(share4, share2);
        assertNotEquals(share4, share3);
        assertEquals(share4, share4);
        assertNotEquals(share4, share5);

        assertNotEquals(share5, share1);
        assertNotEquals(share5, share2);
        assertNotEquals(share5, share3);
        assertNotEquals(share5, share4);
        assertEquals(share5, share5);
    }
}
