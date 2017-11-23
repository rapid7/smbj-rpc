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

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class Test_SamrGetGroupsForUserRequest {

    @Test
    public void getStub() throws IOException {
        byte[] handle = Hex.decode("000000005f32a420f68b2645b4e0e8467cc2e111");
        assertEquals("000000005f32a420f68b2645b4e0e8467cc2e111", toHexString(new SamrGetGroupsForUserRequest(handle).getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(new SamrGetGroupsForUserRequest(new byte[0]).getResponseObject(), instanceOf(SamrGetGroupsForUserResponse.class));
    }

    @Test
    public void getOpNum() {
        assertEquals(SamrGetGroupsForUserRequest.OP_NUM, new SamrGetGroupsForUserRequest(new byte[0]).getOpNum());
    }
}
