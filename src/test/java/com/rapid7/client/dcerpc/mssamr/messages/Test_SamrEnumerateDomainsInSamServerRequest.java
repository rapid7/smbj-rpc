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

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.IOException;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;


public class Test_SamrEnumerateDomainsInSamServerRequest {
    @Test
    public void getStub() throws IOException {
        assertEquals("000000005f32a420f68b2645b4e0e8467cc2e11100000000ffff0000",
            toHexString(newRequest().getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(newRequest().getResponseObject(), instanceOf(SamrEnumerateDomainsInSamServerResponse.class));
    }

    @Test
    public void getOpNum() {
        assertEquals(SamrEnumerateDomainsInSamServerRequest.OP_NUM, newRequest().getOpNum());
    }

    private SamrEnumerateDomainsInSamServerRequest newRequest() {
        byte[] handle = Hex.decode("000000005f32a420f68b2645b4e0e8467cc2e111");
        SamrEnumerateDomainsInSamServerRequest request = new SamrEnumerateDomainsInSamServerRequest(
                handle, 0, 0xffff);
        return request;
    }
}
