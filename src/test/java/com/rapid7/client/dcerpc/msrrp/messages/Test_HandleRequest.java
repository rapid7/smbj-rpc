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
import org.junit.Test;
import com.rapid7.client.dcerpc.messages.HandleResponse;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Test_HandleRequest {
    private final HandleRequest request = new HandleRequest(OpenLocalMachine.OP_NUM, 33554432);

    @Test
    public void getOpNum() {
        assertEquals(OpenLocalMachine.OP_NUM, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        // Remote Registry Service, OpenHKLM
        //      Operation: OpenHKLM (2)
        //      [Response in frame: 11176]
        //      NULL Pointer: Pointer to System Name (uint16)
        //      Access Mask: 0x02000000
        //          Generic rights: 0x00000000
        //          .... ..1. .... .... .... .... .... .... = Maximum allowed: Set
        //          .... .... 0... .... .... .... .... .... = Access SACL: Not set
        //          Standard rights: 0x00000000
        //          WINREG specific rights: 0x00000000
        assertEquals("0000000000000002", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(HandleResponse.class));
    }
}
