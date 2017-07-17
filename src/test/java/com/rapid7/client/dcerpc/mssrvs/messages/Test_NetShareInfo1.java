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
import org.junit.Test;

public class Test_NetShareInfo1 {
    @Test(expected = IllegalArgumentException.class)
    public void commentIsNull() {
        new NetShareInfo1("test", 0, null);
    }

    @Test
    public void type() {
        final NetShareInfo1 share = new NetShareInfo1("test", 123456789, "comment");
        assertEquals(123456789, share.getType());
    }

    @Test
    public void comment() {
        final NetShareInfo1 share = new NetShareInfo1("test", 123456789, "comment");
        assertEquals("comment", share.getComment());
    }

    @Test
    public void testHashCode() {
        final NetShareInfo1 share1 = new NetShareInfo1("test1", 0, "comment1");
        final NetShareInfo1 share2 = new NetShareInfo1("test2", 0, "comment1");
        final NetShareInfo1 share3 = new NetShareInfo1("test2", 123456789, "comment1");
        final NetShareInfo1 share4 = new NetShareInfo1("test2", 123456789, "comment2");

        assertEquals(share1.hashCode(), share1.hashCode());
        assertNotEquals(share2.hashCode(), share1.hashCode());
        assertNotEquals(share3.hashCode(), share1.hashCode());
        assertNotEquals(share4.hashCode(), share1.hashCode());
    }

    @Test
    public void testEquals() {
        final NetShareInfo1 share1 = new NetShareInfo1("test1", 0, "comment1");
        final NetShareInfo1 share2 = new NetShareInfo1("test2", 0, "comment1");
        final NetShareInfo1 share3 = new NetShareInfo1("test2", 123456789, "comment1");
        final NetShareInfo1 share4 = new NetShareInfo1("test2", 123456789, "comment2");

        assertEquals(share1, share1);
        assertNotEquals(share2, share1);
        assertNotEquals(share3, share1);
        assertNotEquals(share4, share1);

        assertNotEquals(share1, share2);
        assertEquals(share2, share2);
        assertNotEquals(share3, share2);
        assertNotEquals(share4, share2);

        assertNotEquals(share1, share3);
        assertNotEquals(share2, share3);
        assertEquals(share3, share3);
        assertNotEquals(share4, share3);

        assertNotEquals(share1, share4);
        assertNotEquals(share2, share4);
        assertNotEquals(share3, share4);
        assertEquals(share4, share4);
    }
}
