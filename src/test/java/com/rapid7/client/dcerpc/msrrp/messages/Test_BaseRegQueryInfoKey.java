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
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

public class Test_BaseRegQueryInfoKey {
    private final ContextHandle contextHandle = new ContextHandle("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegQueryInfoKeyRequest request = new BaseRegQueryInfoKeyRequest(contextHandle);

    @Test
    public void request() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 64, Call: 37, Ctx: 0, [Resp: #11412]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 64
        //      Auth Length: 0
        //      Call ID: 37
        //      Alloc hint: 64
        //      Context ID: 0
        //      Opnum: 16
        //      [Response in frame: 11412]
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
        final byte[] requestBytes = request.marshal(37);
        final String encodedRequest = Hex.toHexString(requestBytes);

        assertEquals(
            "0500000310000000400000002500000040000000000010000000000032daf234b77c86409d29efe60d3266830000000000000200000000000000000000000000",
            encodedRequest);
    }

    @Test
    public void response()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 72, Call: 37, Ctx: 0, [Req: #11405]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 72
        //      Auth Length: 0
        //      Call ID: 37
        //      Alloc hint: 48
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 16
        //      [Request in frame: 11405]
        //      [Time from request: 0.094128491 seconds]
        // Remote Registry Service, QueryInfoKey
        //      Operation: QueryInfoKey (16)
        //      [Request in frame: 11405]
        //      Pointer to Classname (winreg_String)
        //          Classname:
        //              Name Len: 2
        //              Name Size: 0
        //              NULL Pointer: Classname
        //      Pointer to Num Subkeys (uint32)
        //          Num Subkeys: 6
        //      Pointer to Max Subkeylen (uint32)
        //          Max Subkeylen: 22
        //      Pointer to Max Classlen (uint32)
        //          Max Classlen: 0
        //      Pointer to Num Values (uint32)
        //          Num Values: 0
        //      Pointer to Max Valnamelen (uint32)
        //          Max Valnamelen: 0
        //      Pointer to Max Valbufsize (uint32)
        //          Max Valbufsize: 0
        //      Pointer to Secdescsize (uint32)
        //          Secdescsize: 164
        //      Pointer to Last Changed Time (NTTIME)
        //          Last Changed Time: Jun 21, 2017 12:50:30.686403000 EDT
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = Hex.decode(
            "0500020310000000480000002500000030000000000000000200000000000000060000001600000000000000000000000000000000000000a40000009e8b087eaeead20100000000");
        final BaseRegQueryInfoKeyResponse response = request.unmarshal(responseBytes, 37);

        assertEquals(6, response.getSubKeys());
        assertEquals(22, response.getMaxSubKeyLen());
        assertEquals(0, response.getMaxClassLen());
        assertEquals(0, response.getValues());
        assertEquals(0, response.getMaxValueNameLen());
        assertEquals(0, response.getMaxValueLen());
        assertEquals(164, response.getSecurityDescriptor());
        assertEquals(131425374306864030l, response.getLastWriteTime());
        assertEquals(0, response.getReturnValue());
    }
}
