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

package com.rapid7.client.dcerpc.objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_SIDNameUse {

    @DataProvider
    public Object[][] data_pairs() {
        return new Object[][] {
                {SIDNameUse.SID_TYPE_USER, (short) 1},
                {SIDNameUse.SID_TYPE_GROUP, (short) 2},
                {SIDNameUse.SID_TYPE_DOMAIN, (short) 3},
                {SIDNameUse.SID_TYPE_ALIAS, (short) 4},
                {SIDNameUse.SID_TYPE_WELL_KNOWN_GROUP, (short) 5},
                {SIDNameUse.SID_TYPE_DELETED_ACCOUNT, (short) 6},
                {SIDNameUse.SID_TYPE_INVALID, (short) 7},
                {SIDNameUse.SID_TYPE_UNKNOWN, (short) 8},
                {SIDNameUse.SID_TYPE_COMPUTER, (short) 9},
                {SIDNameUse.SID_TYPE_LABEL, (short) 10}
        };
    }

    @Test(dataProvider = "data_pairs")
    public void test_getValue(SIDNameUse use, short expected) {
        assertEquals(use.getValue(), expected);
    }

    @Test(dataProvider = "data_pairs")
    public void test_fromValue(SIDNameUse expected, short value) {
        assertSame(SIDNameUse.fromValue(value), expected);
    }

    @Test
    public void test_fromValue_unknown() {
        assertNull(SIDNameUse.fromValue((short) 255));
    }

}
