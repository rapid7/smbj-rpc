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

import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mssamr.dto.DomainHandle;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Test_SamrOpenUser {
    // https://msdn.microsoft.com/en-us/library/cc980032.aspx
    private final SamrOpenUserRequest request = new SamrOpenUserRequest(new byte[20], 500); //ADMINISTRATOR(500)

    @Test
    public void getOpNum() {
        assertEquals(SamrOpenUserRequest.OP_NUM, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        assertEquals("00000000000000000000000000000000000000001b010200f4010000", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(HandleResponse.class));
    }

}
