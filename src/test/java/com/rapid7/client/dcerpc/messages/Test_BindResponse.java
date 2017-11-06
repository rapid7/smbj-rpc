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

import static org.junit.Assert.*;

public class Test_BindResponse {
    @Test
    public void ackResponse() throws IOException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Bind_ack, Fragment: Single, FragLen: 68,
        // Call: 1
        // Version: 5
        // Version (minor): 0
        // Packet type: Bind_ack (12)
        // Packet Flags: 0x03
        // Data Representation: 10000000 (Order: Little-endian, Char: ASCII, Float: IEEE)
        // Frag Length: 68
        // Auth Length: 0
        // Call ID: 1
        // Max Xmit Frag: 4096
        // Max Recv Frag: 4096
        // Assoc Group: 0x000021a5
        // Scndry Addr len: 13
        // Scndry Addr: \PIPE\winreg
        // Num results: 1
        // Ctx Item[1]: Acceptance, 32bit NDR
        final BindResponse response = new BindResponse();

        response.fromHexString("05000c0310000000440000000100000000100010a52100000d005c504950455c77696e72656700000100000000000000045d888aeb1cc9119fe808002b10486002000000");

        assertTrue(response.isACK());
        assertFalse(response.isNAK());
        assertEquals(4096, response.getMaxXmitFrag());
        assertEquals(4096, response.getMaxRecvFrag());
    }
}
