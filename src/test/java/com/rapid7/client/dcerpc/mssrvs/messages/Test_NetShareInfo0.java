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

public class Test_NetShareInfo0 {
    @Test(expected = IllegalArgumentException.class)
    public void nameIsNull() {
        new NetShareInfo0(null);
    }

    @Test
    public void name() {
        final NetShareInfo0 share = new NetShareInfo0("test");
        assertEquals("test", share.getName());
    }

    @Test
    public void testHashCode() {
        final NetShareInfo0 share1 = new NetShareInfo0("test1");
        final NetShareInfo0 share2 = new NetShareInfo0("test2");

        assertEquals(share1.hashCode(), share1.hashCode());
        assertNotEquals(share2.hashCode(), share1.hashCode());
    }

    @Test
    public void testEquals() {
        final NetShareInfo0 share1 = new NetShareInfo0("test1");
        final NetShareInfo0 share2 = new NetShareInfo0("test2");

        assertEquals(share1, share1);
        assertNotEquals(share1, share2);
        assertNotEquals(share2, share1);
        assertEquals(share2, share2);
    }
}
