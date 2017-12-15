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

import com.rapid7.client.dcerpc.dto.SIDUse;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_SIDNameUse {

    @DataProvider
    public Object[][] data_pairs() {
        return new Object[][] {
                {SIDUse.SID_TYPE_USER, (short) 1},
                {SIDUse.SID_TYPE_GROUP, (short) 2},
                {SIDUse.SID_TYPE_DOMAIN, (short) 3},
                {SIDUse.SID_TYPE_ALIAS, (short) 4},
                {SIDUse.SID_TYPE_WELL_KNOWN_GROUP, (short) 5},
                {SIDUse.SID_TYPE_DELETED_ACCOUNT, (short) 6},
                {SIDUse.SID_TYPE_INVALID, (short) 7},
                {SIDUse.SID_TYPE_UNKNOWN, (short) 8},
                {SIDUse.SID_TYPE_COMPUTER, (short) 9},
                {SIDUse.SID_TYPE_LABEL, (short) 10}
        };
    }

    @Test(dataProvider = "data_pairs")
    public void test_getValue(SIDUse use, short expected) {
        assertEquals(use.getValue(), expected);
    }

    @Test(dataProvider = "data_pairs")
    public void test_fromValue(SIDUse expected, short value) {
        assertSame(SIDUse.fromValue(value), expected);
    }

    @Test
    public void test_fromValue_unknown() {
        assertNull(SIDUse.fromValue((short) 255));
    }

}
