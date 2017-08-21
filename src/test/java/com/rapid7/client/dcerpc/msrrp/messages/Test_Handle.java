/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msrrp.messages;

import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.transport.TransportException;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Test_Handle {
    private final HandleRequest request = new HandleRequest(OpenLocalMachine.OP_NUM,
        EnumSet.of(AccessMask.MAXIMUM_ALLOWED));

    @Test
    public void request() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 32, Call: 1, Ctx: 0, [Resp: #11176]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 32
        //      Auth Length: 0
        //      Call ID: 1
        //      Alloc hint: 32
        //      Context ID: 0
        //      Opnum: 2
        //      [Response in frame: 11176]
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
        final byte[] requestBytes = request.marshal(1);
        final String encodedRequest = Hex.toHexString(requestBytes);

        assertEquals("0500000310000000200000000100000020000000000002000000000000000002", encodedRequest);
    }

    @Test
    public void response()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 48, Call: 1, Ctx: 0, [Req: #11174]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 48
        //      Auth Length: 0
        //      Call ID: 1
        //      Alloc hint: 24
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 2
        //      [Request in frame: 11174]
        //      [Time from request: 0.095388051 seconds]
        // Remote Registry Service, OpenHKLM
        //      Operation: OpenHKLM (2)
        //      [Request in frame: 11174]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = Hex.decode(
            "0500020310000000300000000100000018000000000000000000000032daf234b77c86409d29efe60d32668300000000");
        final HandleResponse response = request.unmarshal(responseBytes, 1);

        assertEquals(new ContextHandle("0000000032daf234b77c86409d29efe60d326683"), response.getHandle());
        assertEquals(0, response.getReturnValue());
    }
}
