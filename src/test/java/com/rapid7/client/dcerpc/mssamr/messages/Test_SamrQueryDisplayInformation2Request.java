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

public class Test_SamrQueryDisplayInformation2Request {

    @Test
    public void getStub() throws IOException {
        SamrQueryDisplayInformation2Request.DomainDisplayGroup request = newRequest(Hex.decode("000000002ea469b822b6074c94fe3d35f5597406"));
        assertEquals("000000002ea469b822b6074c94fe3d35f55974060300000000000000ffffffffffff0000",
            toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(newRequest(new byte[0]).getResponseObject(), instanceOf(SamrQueryDisplayInformation2Response.class));
    }

    @Test
    public void getOpNum() {
        assertEquals(SamrQueryDisplayInformation2Request.OP_NUM, newRequest(new byte[0]).getOpNum());
    }

    private SamrQueryDisplayInformation2Request.DomainDisplayGroup newRequest(byte[] handle) {
        return new SamrQueryDisplayInformation2Request.DomainDisplayGroup(handle,0, 0xffffffff, 0xffff);
    }
}
