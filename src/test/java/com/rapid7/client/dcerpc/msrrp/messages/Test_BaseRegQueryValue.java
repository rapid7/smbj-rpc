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

import com.rapid7.client.dcerpc.msrrp.RegistryValueType;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.hierynomus.protocol.transport.TransportException;
import java.io.UnsupportedEncodingException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Test_BaseRegQueryValue {
    private final ContextHandle contextHandle = new ContextHandle("000000000a665393f4666e49a68cd99f269d020f");
    private final BaseRegQueryValueRequest request = new BaseRegQueryValueRequest(contextHandle, "CurrentVersion",
        65536);

    @Test
    public void request() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 136, Call: 36, Ctx: 0, [Resp: #11403]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 136
        //      Auth Length: 0
        //      Call ID: 36
        //      Alloc hint: 136
        //      Context ID: 0
        //      Opnum: 17
        //      [Response in frame: 11403]
        // Remote Registry Service, QueryValue
        //      Operation: QueryValue (17)
        //      [Response in frame: 11403]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
        //              Handle: 000000000a665393f4666e49a68cd99f269d020f
        //              [Frame handle opened: 11204]
        //              [Frame handle closed: 11415]
        //      Pointer to Value Name (winreg_String)
        //          Value Name:
        //              Name Len: 30
        //              Name Size: 30
        //              Value Name
        //                  Referent ID: 0x00020000
        //                  Max Count: 15
        //                  Offset: 0
        //                  Actual Count: 15
        //                  Value Name: CurrentVersion
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Data (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 65536
        //          Offset: 0
        //          Actual Count: 0
        //      Pointer to Data Size (uint32)
        //          Referent ID: 0x0002000c
        //          Data Size: 65536
        //      Pointer to Data Length (uint32)
        //          Referent ID: 0x00020010
        //          Data Length: 0
        final byte[] requestBytes = request.marshal(36);
        final String encodedRequest = Hex.toHexString(requestBytes);

        assertEquals(
            "050000031000000088000000240000008800000000001100000000000a665393f4666e49a68cd99f269d020f1e001e00000002000f000000000000000f000000430075007200720065006e007400560065007200730069006f006e00000000000400020000000000080002000000010000000000000000000c000200000001001000020000000000",
            encodedRequest);
    }

    @Test
    public void response()
        throws TransportException, UnsupportedEncodingException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 76, Call: 36, Ctx: 0, [Req: #11394]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 76
        //      Auth Length: 0
        //      Call ID: 36
        //      Alloc hint: 52
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 17
        //      [Request in frame: 11394]
        //      [Time from request: 0.092484557 seconds]
        // Remote Registry Service, QueryValue
        //      Operation: QueryValue (17)
        //      [Request in frame: 11394]
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020000
        //          Type
        //      Pointer to Data (uint8)
        //          Referent ID: 0x00020004
        //          Max Count: 8
        //          Offset: 0
        //          Actual Count: 8
        //          Data: 54
        //          Data: 0
        //          Data: 46
        //          Data: 0
        //          Data: 51
        //          Data: 0
        //          Data: 0
        //          Data: 0
        //      Pointer to Data Size (uint32)
        //          Referent ID: 0x00020008
        //          Data Size: 8
        //      Pointer to Data Length (uint32)
        //          Referent ID: 0x0002000c
        //          Data Length: 8
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = Hex.decode(
            "05000203100000004c00000024000000340000000000000000000200010000000400020008000000000000000800000036002e003300000008000200080000000c0002000800000000000000");
        final BaseRegQueryValueResponse response = request.unmarshal(responseBytes, 36);

        assertArrayEquals("6.3\0".getBytes("UTF-16LE"), response.getData());
        assertEquals(RegistryValueType.REG_SZ, response.getType());
        assertEquals(0, response.getReturnValue());
    }
}
