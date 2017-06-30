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

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;
import org.junit.Test;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

public class Test_BaseRegCloseKey {
    private final ContextHandle contextHandle = new ContextHandle("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegCloseKey request = new BaseRegCloseKey(contextHandle);

    @Test
    public void request() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 44, Call: 39, Ctx: 0, [Resp: #11429]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 44
        //      Auth Length: 0
        //      Call ID: 39
        //      Alloc hint: 44
        //      Context ID: 0
        //      Opnum: 5
        //      [Response in frame: 11429]
        // Remote Registry Service, CloseKey
        //      Operation: CloseKey (5)
        //      [Response in frame: 11429]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        final byte[] requestBytes = request.marshal(39);
        final String encodedRequest =
            String.format(String.format("%%0%dx", requestBytes.length << 1), new BigInteger(1, requestBytes));

        assertEquals("05000003100000002c000000270000002c000000000005000000000032daf234b77c86409d29efe60d326683",
            encodedRequest);
    }

    @Test
    public void response()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 48, Call: 39, Ctx: 0, [Req: #11424]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 48
        //      Auth Length: 0
        //      Call ID: 39
        //      Alloc hint: 24
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 5
        //      [Request in frame: 11424]
        //      [Time from request: 0.092186084 seconds]
        // Remote Registry Service, CloseKey
        //      Operation: CloseKey (5)
        //      [Request in frame: 11424]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle
        //              Handle: 0000000000000000000000000000000000000000
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = new BigInteger(
            "050002031000000030000000270000001800000000000000000000000000000000000000000000000000000000000000",
            16).toByteArray();
        final HandleResponse response = request.unmarshal(responseBytes, 39);

        assertEquals(new ContextHandle(), response.getHandle());
        assertEquals(0, response.getReturnValue());
    }
}
