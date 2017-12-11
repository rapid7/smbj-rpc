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

package com.rapid7.client.dcerpc.mssamr.dto;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_LogonHours {

    @DataProvider
    public Object[][] data_UnitsPerWeek_fromLogonHoursLength() {
        return new Object[][] {
                {1, LogonHours.UnitsPerWeek.DAYS},
                {21, LogonHours.UnitsPerWeek.HOURS},
                {1260, LogonHours.UnitsPerWeek.MINUTES},
        };
    }

    @Test(dataProvider = "data_UnitsPerWeek_fromLogonHoursLength")
    public void test_UnitsPerWeek_fromLogonHoursLength(int length, LogonHours.UnitsPerWeek expected) {
        assertSame(LogonHours.UnitsPerWeek.fromLogonHoursLength(length), expected);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid logonHours length: 255")
    public void test_UnitsPerWeek_fromLogonHoursLength_Invalid() {
        LogonHours.UnitsPerWeek.fromLogonHoursLength(255);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting non-null logonHours")
    public void test_init_Null() {
        new LogonHours(null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Invalid logonHours length: 255")
    public void test_init_InvalidLength() {
        new LogonHours(new byte[255]);
    }

    @DataProvider
    public Object[][] data_getters() {
        return new Object[][] {
                {new byte[1], LogonHours.UnitsPerWeek.DAYS},
                {new byte[21], LogonHours.UnitsPerWeek.HOURS},
                {new byte[1260], LogonHours.UnitsPerWeek.MINUTES},
        };
    }

    @Test(dataProvider = "data_getters")
    public void test_getters(byte[] logonHours, LogonHours.UnitsPerWeek expectedUnitsPerWeek) {
        LogonHours obj = new LogonHours(logonHours);
        assertSame(obj.getUnitsPerWeek(), expectedUnitsPerWeek);
        assertSame(obj.getLogonHours(), logonHours);
    }

    @Test
    public void test_hashCode() {
        LogonHours obj1 = new LogonHours(new byte[]{127});
        LogonHours obj2 = new LogonHours(new byte[]{50});
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        LogonHours obj1 = new LogonHours(new byte[]{127});
        LogonHours obj2 = new LogonHours(new byte[]{50});
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, obj2);
    }

    @Test
    public void test_toString() {
        assertEquals(new LogonHours(new byte[21]).toString(), "LogonHours{unitsPerWeek: HOURS, size(logonHours): 21");
    }
}
