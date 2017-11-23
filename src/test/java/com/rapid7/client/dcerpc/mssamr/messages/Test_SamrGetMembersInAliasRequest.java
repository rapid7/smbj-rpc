/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.messages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Test_SamrGetMembersInAliasRequest {

    @DataProvider
    public Object[][] data_requests() {
        byte[] handle = Hex.decode("000000006a2faac1903cb042b9de41d5cf38cc74");
        return new Object[][] { { new SamrGetMembersInAliasRequest(handle) } };
    }

    @Test(dataProvider = "data_requests")
    public void test_getOpNum(SamrGetMembersInAliasRequest request) throws IOException {
        assertEquals(request.getOpNum(), SamrGetMembersInAliasRequest.OP_NUM);
    }

    @Test(dataProvider = "data_requests")
    public void test_getResponseObject(SamrGetMembersInAliasRequest request) {
        assertTrue(request.getResponseObject() instanceof SamrGetMembersInAliasResponse);
    }

}
