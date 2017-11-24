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

public class Test_SecurityInformationQueryType {

    @DataProvider
    public Object[][] data_getValue() {
        return new Object[][] {
                {SecurityInformationQueryType.OWNER_SECURITY_INFORMATION, (byte) 1},
                {SecurityInformationQueryType.GROUP_SECURITY_INFORMATION, (byte) 2},
                {SecurityInformationQueryType.DACL_SECURITY_INFORMATION, (byte) 4},
                {SecurityInformationQueryType.SACL_SECURITY_INFORMATION, (byte) 8},
        };
    }

    @Test(dataProvider = "data_getValue")
    public void test_getValue(SecurityInformationQueryType type, byte expected) {
        assertEquals(type.getValue(), expected);
    }

    @DataProvider
    public Object[][] data_isSet() {
        return new Object[][] {
                {SecurityInformationQueryType.OWNER_SECURITY_INFORMATION, 0, false},
                {SecurityInformationQueryType.OWNER_SECURITY_INFORMATION, 1, true},
                {SecurityInformationQueryType.OWNER_SECURITY_INFORMATION, 3, true},
                {SecurityInformationQueryType.OWNER_SECURITY_INFORMATION, 257, true},
                {SecurityInformationQueryType.GROUP_SECURITY_INFORMATION, 0, false},
                {SecurityInformationQueryType.GROUP_SECURITY_INFORMATION, 2, true},
                {SecurityInformationQueryType.GROUP_SECURITY_INFORMATION, 3, true},
                {SecurityInformationQueryType.GROUP_SECURITY_INFORMATION, 258, true},
                {SecurityInformationQueryType.DACL_SECURITY_INFORMATION, 0, false},
                {SecurityInformationQueryType.DACL_SECURITY_INFORMATION, 4, true},
                {SecurityInformationQueryType.DACL_SECURITY_INFORMATION, 5, true},
                {SecurityInformationQueryType.DACL_SECURITY_INFORMATION, 260, true},
                {SecurityInformationQueryType.SACL_SECURITY_INFORMATION, 0, false},
                {SecurityInformationQueryType.SACL_SECURITY_INFORMATION, 8, true},
                {SecurityInformationQueryType.SACL_SECURITY_INFORMATION, 9, true},
                {SecurityInformationQueryType.SACL_SECURITY_INFORMATION, 264, true},
        };
    }

    @Test(dataProvider = "data_isSet")
    public void test_isSet(SecurityInformationQueryType type, int value, boolean expected) {
        assertEquals(type.isSet(value), expected);
    }
}
