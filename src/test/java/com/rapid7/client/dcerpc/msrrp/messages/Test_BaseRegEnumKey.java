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
import com.rapid7.client.dcerpc.objects.FileTime;

public class Test_BaseRegEnumKey {
    private final ContextHandle contextHandle = new ContextHandle("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegEnumKeyRequest request1 = new BaseRegEnumKeyRequest(contextHandle, 0, 256, 32767);
    private final BaseRegEnumKeyRequest request2 = new BaseRegEnumKeyRequest(contextHandle, 1, 256, 32767);

    @Test
    public void request1() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 104, Call: 2, Ctx: 0, [Resp: #11178]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 104
        //      Auth Length: 0
        //      Call ID: 2
        //      Alloc hint: 104
        //      Context ID: 0
        //      Opnum: 9
        //      [Response in frame: 11178]
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Response in frame: 11178]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Enum Index: 0
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 0
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 0
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: No time specified (0)
        final byte[] requestBytes = request1.marshal(2);
        final String encodedRequest =
            String.format(String.format("%%0%dx", requestBytes.length << 1), new BigInteger(1, requestBytes));

        assertEquals(
            "0500000310000000680000000200000068000000000009000000000032daf234b77c86409d29efe60d326683000000000000000200000200000100000000000000000000040002000000feff08000200ff7f000000000000000000000c0002000000000000000000",
            encodedRequest);
    }

    @Test
    public void request2() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 104, Call: 3, Ctx: 0, [Resp: #11182]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 104
        //      Auth Length: 0
        //      Call ID: 3
        //      Alloc hint: 104
        //      Context ID: 0
        //      Opnum: 9
        //      [Response in frame: 11182]
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Response in frame: 11182]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Enum Index: 1
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 0
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 0
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: No time specified (0)
        final byte[] requestBytes = request2.marshal(3);
        final String encodedRequest =
            String.format(String.format("%%0%dx", requestBytes.length << 1), new BigInteger(1, requestBytes));

        assertEquals(
            "0500000310000000680000000300000068000000000009000000000032daf234b77c86409d29efe60d326683010000000000000200000200000100000000000000000000040002000000feff08000200ff7f000000000000000000000c0002000000000000000000",
            encodedRequest);
    }

    @Test
    public void response1()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 112, Call: 2, Ctx: 0, [Req: #11177]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 112
        //      Auth Length: 0
        //      Call ID: 2
        //      Alloc hint: 88
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 9
        //      [Request in frame: 11177]
        //      [Time from request: 0.092870335 seconds]
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Request in frame: 11177]
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 24
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 12
        //                  Name: 66
        //                  Name: 67
        //                  Name: 68
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 2
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 1
        //                  Name: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: Jun 15, 2017 15:29:36.566813400 EDT
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = new BigInteger(
            "050002031000000070000000020000005800000000000000180000020000020000010000000000000c000000420043004400300030003000300030003000300030000000040002000200feff08000200ff7f00000000000001000000000000000c00020026cd57b90de6d20100000000",
            16).toByteArray();
        final BaseRegEnumKeyResponse response = request1.unmarshal(responseBytes, 2);

        assertEquals("BCD00000000", response.getName());
        assertEquals(new FileTime(131420285765668134L), response.getLastWriteTime());
        assertEquals(0, response.getReturnValue());
    }

    @Test
    public void response2()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 108, Call: 3, Ctx: 0, [Req: #11181]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 108
        //      Auth Length: 0
        //      Call ID: 3
        //      Alloc hint: 84
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 9
        //      [Request in frame: 11181]
        //      [Time from request: 0.092954195 seconds]
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Request in frame: 11181]
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 18
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 9
        //                  Name: 72
        //                  Name: 65
        //                  Name: 82
        //                  Name: 68
        //                  Name: 87
        //                  Name: 65
        //                  Name: 82
        //                  Name: 69
        //                  Name: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 2
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 1
        //                  Name: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: Jun 15, 2017 15:29:33.673089000 EDT
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = new BigInteger(
            "05000203100000006c00000003000000540000000000000012000002000002000001000000000000090000004800410052004400570041005200450000000000040002000200feff08000200ff7f00000000000001000000000000000c0002000a419eb70de6d20100000000",
            16).toByteArray();
        final BaseRegEnumKeyResponse response = request2.unmarshal(responseBytes, 3);

        assertEquals("HARDWARE", response.getName());
        assertEquals(new FileTime(131420285736730890L), response.getLastWriteTime());
        assertEquals(0, response.getReturnValue());
    }
}
