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

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Test_SamrQueryInformationAliasRequest {

    @DataProvider
    public Object[][] data_requests() {
        byte[] handle = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        return new Object[][] {
                {new SamrQueryInformationAliasRequest.AliasGeneralInformation(handle)}
        };
    }

    @Test(dataProvider = "data_requests")
    public void test_getOpNum(SamrQueryInformationAliasRequest request) {
        assertEquals(request.getOpNum(), SamrQueryInformationAliasRequest.OP_NUM);
    }

    @Test(dataProvider = "data_requests")
    public void test_getResponseObject(SamrQueryInformationAliasRequest request) {
        assertNotNull(request.getResponseObject());
    }

    @Test(dataProvider = "data_requests")
    public void test_marshall(SamrQueryInformationAliasRequest request) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        request.marshal(out);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        PacketInput in = new PacketInput(bin);
        assertEquals(in.readRawBytes(20), request.getAliasHandle());
        assertEquals(in.readUnsignedShort(), request.getAliasInformationClass().getInfoLevel());
        assertEquals(bin.available(), 0);
    }
}
