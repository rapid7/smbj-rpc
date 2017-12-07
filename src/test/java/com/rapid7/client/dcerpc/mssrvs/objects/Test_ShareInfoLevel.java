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

package com.rapid7.client.dcerpc.mssrvs.objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Test_ShareInfoLevel {
    @DataProvider
    public Object[][] data_getInfoLevel() {
        return new Object[][] {
                {ShareInfoLevel.LPSHARE_INFO_0, 0},
                {ShareInfoLevel.LPSHARE_INFO_1, 1},
                {ShareInfoLevel.LPSHARE_INFO_2, 2},
                {ShareInfoLevel.LPSHARE_INFO_501, 501},
                {ShareInfoLevel.LPSHARE_INFO_502, 502},
                {ShareInfoLevel.LPSHARE_INFO_503, 503},
        };
    }

    @Test(dataProvider = "data_getInfoLevel")
    public void test_getInfoLevel(ShareInfoLevel obj, int expected) {
        assertEquals(obj.getInfoLevel(), expected);
    }
}
