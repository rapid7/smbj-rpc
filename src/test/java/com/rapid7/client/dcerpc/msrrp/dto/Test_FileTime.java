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

import java.util.Date;
import org.junit.Test;
import com.rapid7.client.dcerpc.msrrp.dto.FileTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Test_FileTime {
    @Test(expected = IllegalArgumentException.class)
    public void constructorBadWindowsTime() {
        new FileTime(0);
    }

    @Test
    public void getTime1() {
        final FileTime fileTime = new FileTime(131420285736730000L);
        final Date date = new Date(1497554973673L);

        assertEquals(date, fileTime.getTime());
    }

    @Test
    public void getTime2() {
        final FileTime fileTime = new FileTime(131420285736739999L);
        final Date date = new Date(1497554973673L);

        assertEquals(date, fileTime.getTime());
    }

    @Test
    public void getWindowsTime() {
        final FileTime fileTime = new FileTime(131420285736739999L);

        assertEquals(131420285736739999L, fileTime.getWindowsTime());
    }

    @Test
    public void testToString1() {
        final FileTime fileTime = new FileTime(131420285736730000L);

        assertEquals("2017-06-15T19:29:33.673+0000 (131420285736730000)", fileTime.toString());
    }

    @Test
    public void testToString2() {
        final FileTime fileTime = new FileTime(131420285736739999L);

        assertEquals("2017-06-15T19:29:33.673+0000 (131420285736739999)", fileTime.toString());
    }

    @Test
    public void testHashCode() {
        final FileTime fileTime1 = new FileTime(131420285736730000L);
        final FileTime fileTime2 = new FileTime(131420285736739999L);
        final FileTime fileTime3 = new FileTime(131420285736740000L);

        assertEquals(fileTime1.hashCode(), fileTime1.hashCode());
        assertNotEquals(fileTime1.hashCode(), fileTime2.hashCode());
        assertNotEquals(fileTime1.hashCode(), fileTime3.hashCode());
    }

    @Test
    public void testEquals() {
        final FileTime fileTime1 = new FileTime(131420285736730000L);
        final FileTime fileTime2 = new FileTime(131420285736739999L);
        final FileTime fileTime3 = new FileTime(131420285736740000L);

        assertEquals(fileTime1, fileTime1);
        assertNotEquals(fileTime1, fileTime2);
        assertNotEquals(fileTime1, fileTime3);
        assertNotEquals(fileTime2, fileTime1);
        assertNotEquals(fileTime2, fileTime3);
        assertNotEquals(fileTime3, fileTime1);
        assertNotEquals(fileTime3, fileTime2);
    }
}
