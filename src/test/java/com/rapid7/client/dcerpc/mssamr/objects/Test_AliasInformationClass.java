/**
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
 */
package com.rapid7.client.dcerpc.mssamr.objects;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Test_AliasInformationClass {
    @DataProvider
    public Object[][] data_getInfoLevel() {
        return new Object[][] { { AliasInformationClass.ALIAS_GENERAL_INFORMATION, 1 } };
    }

    @Test(dataProvider = "data_getInfoLevel")
    public void test_getInfoLevel(AliasInformationClass obj, int expected) {
        assertEquals(obj.getInfoLevel(), expected);
    }
}
