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
package com.rapid7.client.dcerpc.messages;

import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.Interface;

import static org.junit.Assert.assertEquals;

public class Test_BindRequest {
    @Test
    public void request() throws IOException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Bind, Fragment: Single, FragLen: 72,
        // Call: 1
        // Version: 5
        // Version (minor): 0
        // Packet type: Bind (11)
        // Packet Flags: 0x03
        // Data Representation: 10000000 (Order: Little-endian, Char: ASCII, Float: IEEE)
        // Frag Length: 72
        // Auth Length: 0
        // Call ID: 1
        // Max Xmit Frag: 4096
        // Max Recv Frag: 4096
        // Assoc Group: 0x00000000
        // Num Ctx Items: 1
        // Ctx Item[1]: Context ID:0, WINREG, 32bit NDR
        final BindRequest request = new BindRequest(4096, 4096, Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);
        request.setCallID(1);
        assertEquals("05000b031000000048000000010000000010001000000000010000000000010001d08c334422f131aaaa90003800100301000000045d888aeb1cc9119fe808002b10486002000000", request.toHexString());
    }
}
