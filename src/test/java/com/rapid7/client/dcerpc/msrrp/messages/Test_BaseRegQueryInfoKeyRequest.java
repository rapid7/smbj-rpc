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
package com.rapid7.client.dcerpc.msrrp.messages;

import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Test_BaseRegQueryInfoKeyRequest {
    private final byte[] contextHandle = Hex.decode("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegQueryInfoKeyRequest request = new BaseRegQueryInfoKeyRequest(contextHandle);

    @Test
    public void getOpNum() {
        assertEquals(16, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        // Remote Registry Service, QueryInfoKey
        //      Operation: QueryInfoKey (16)
        //      [Response in frame: 11412]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Pointer to Classname (winreg_String)
        //          Classname:
        //              Name Len: 0
        //              Name Size: 0
        //              Classname
        //                  Referent ID: 0x00020000
        //                  Max Count: 0
        //                  Offset: 0
        //                  Actual Count: 0
        assertEquals("0000000032daf234b77c86409d29efe60d3266830000000000000200000000000000000000000000", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(BaseRegQueryInfoKeyResponse.class));
    }
}
