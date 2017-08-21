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

public class Test_BaseRegEnumValue {
    private final ContextHandle contextHandle = new ContextHandle("000000000a665393f4666e49a68cd99f269d020f");
    private final BaseRegEnumValueRequest request1 = new BaseRegEnumValueRequest(contextHandle, 0, 32767, 65536);
    private final BaseRegEnumValueRequest request2 = new BaseRegEnumValueRequest(contextHandle, 1, 32767, 65536);

    @Test
    public void request1() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 108, Call: 10, Ctx: 0, [Resp: #11212]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 108
        //      Auth Length: 0
        //      Call ID: 10
        //      Alloc hint: 108
        //      Context ID: 0
        //      Opnum: 10
        //      [Response in frame: 11212]
        // Remote Registry Service, EnumValue
        //      Operation: EnumValue (10)
        //      [Response in frame: 11212]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
        //              Handle: 000000000a665393f4666e49a68cd99f269d020f
        //              [Frame handle opened: 11204]
        //              [Frame handle closed: 11415]
        //      Enum Index: 0
        //      Pointer to Name (winreg_ValNameBuf)
        //          Name
        //              Length: 0
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Value (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 65536
        //          Offset: 0
        //          Actual Count: 0
        //      Pointer to Size (uint32)
        //          Referent ID: 0x0002000c
        //          Size: 65536
        //      Pointer to Length (uint32)
        //          Referent ID: 0x00020010
        //          Length: 0
        final byte[] requestBytes = request1.marshal(10);
        final String encodedRequest = Hex.toHexString(requestBytes);

        assertEquals(
            "05000003100000006c0000000a0000006c00000000000a00000000000a665393f4666e49a68cd99f269d020f000000000000feff00000200ff7f000000000000000000000400020000000000080002000000010000000000000000000c000200000001001000020000000000",
            encodedRequest);
    }

    @Test
    public void request2() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 108, Call: 11, Ctx: 0, [Resp: #11216]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 108
        //      Auth Length: 0
        //      Call ID: 11
        //      Alloc hint: 108
        //      Context ID: 0
        //      Opnum: 10
        //      [Response in frame: 11216]
        // Remote Registry Service, EnumValue
        //      Operation: EnumValue (10)
        //      [Response in frame: 11216]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
        //              Handle: 000000000a665393f4666e49a68cd99f269d020f
        //              [Frame handle opened: 11204]
        //              [Frame handle closed: 11415]
        //      Enum Index: 1
        //      Pointer to Name (winreg_ValNameBuf)
        //          Name
        //              Length: 0
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Value (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 65536
        //          Offset: 0
        //          Actual Count: 0
        //      Pointer to Size (uint32)
        //          Referent ID: 0x0002000c
        //          Size: 65536
        //      Pointer to Length (uint32)
        //          Referent ID: 0x00020010
        //          Length: 0
        final byte[] requestBytes = request2.marshal(11);
        final String encodedRequest = Hex.toHexString(requestBytes);

        assertEquals(
            "05000003100000006c0000000b0000006c00000000000a00000000000a665393f4666e49a68cd99f269d020f010000000000feff00000200ff7f000000000000000000000400020000000000080002000000010000000000000000000c000200000001001000020000000000",
            encodedRequest);
    }

    @Test
    public void response1()
        throws TransportException, UnsupportedEncodingException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 136, Call: 10, Ctx: 0, [Req: #11206]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 136
        //      Auth Length: 0
        //      Call ID: 10
        //      Alloc hint: 112
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 10
        //      [Request in frame: 11206]
        //      [Time from request: 0.092563019 seconds]
        // Remote Registry Service, EnumValue
        //      Operation: EnumValue (10)
        //      [Request in frame: 11206]
        //      Pointer to Name (winreg_ValNameBuf)
        //          Name
        //              Length: 22
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 11
        //                  Name: 83
        //                  Name: 121
        //                  Name: 115
        //                  Name: 116
        //                  Name: 101
        //                  Name: 109
        //                  Name: 82
        //                  Name: 111
        //                  Name: 111
        //                  Name: 116
        //                  Name: 0
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Value (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 22
        //          Offset: 0
        //          Actual Count: 22
        //          Value: 67
        //          Value: 0
        //          Value: 58
        //          Value: 0
        //          Value: 92
        //          Value: 0
        //          Value: 87
        //          Value: 0
        //          Value: 105
        //          Value: 0
        //          Value: 110
        //          Value: 0
        //          Value: 100
        //          Value: 0
        //          Value: 111
        //          Value: 0
        //          Value: 119
        //          Value: 0
        //          Value: 115
        //          Value: 0
        //          Value: 0
        //          Value: 0
        //      Pointer to Size (uint32)
        //          Referent ID: 0x0002000c
        //          Size: 22
        //      Pointer to Length (uint32)
        //          Referent ID: 0x00020010
        //          Length: 22
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = Hex.decode(
            "0500020310000000880000000a00000070000000000000001600feff00000200ff7f0000000000000b000000530079007300740065006d0052006f006f0074000000000004000200010000000800020016000000000000001600000043003a005c00570069006e0064006f0077007300000000000c00020016000000100002001600000000000000");
        final BaseRegEnumValueResponse response = request1.unmarshal(responseBytes, 10);

        assertEquals("SystemRoot", response.getName());
        assertEquals(RegistryValueType.REG_SZ, response.getType());
        assertArrayEquals("C:\\Windows\0".getBytes("UTF-16LE"), response.getData());
        assertEquals(0, response.getReturnValue());
    }

    @Test
    public void response2()
        throws TransportException, UnsupportedEncodingException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 128, Call: 11, Ctx: 0, [Req: #11214]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 128
        //      Auth Length: 0
        //      Call ID: 11
        //      Alloc hint: 104
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 10
        //      [Request in frame: 11214]
        //      [Time from request: 0.092330418 seconds]
        // Remote Registry Service, EnumValue
        //      Operation: EnumValue (10)
        //      [Request in frame: 11214]
        //      Pointer to Name (winreg_ValNameBuf)
        //          Name
        //              Length: 24
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 12
        //                  Name: 66
        //                  Name: 117
        //                  Name: 105
        //                  Name: 108
        //                  Name: 100
        //                  Name: 66
        //                  Name: 114
        //                  Name: 97
        //                  Name: 110
        //                  Name: 99
        //                  Name: 104
        //                  Name: 0
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Value (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 16
        //          Offset: 0
        //          Actual Count: 16
        //          Value: 116
        //          Value: 0
        //          Value: 104
        //          Value: 0
        //          Value: 49
        //          Value: 0
        //          Value: 95
        //          Value: 0
        //          Value: 115
        //          Value: 0
        //          Value: 116
        //          Value: 0
        //          Value: 49
        //          Value: 0
        //          Value: 0
        //          Value: 0
        //      Pointer to Size (uint32)
        //          Referent ID: 0x0002000c
        //          Size: 16
        //      Pointer to Length (uint32)
        //          Referent ID: 0x00020010
        //          Length: 16
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = Hex.decode(
            "0500020310000000800000000b00000068000000000000001800feff00000200ff7f0000000000000c0000004200750069006c0064004200720061006e006300680000000400020001000000080002001000000000000000100000007400680031005f0073007400310000000c00020010000000100002001000000000000000");
        final BaseRegEnumValueResponse response = request2.unmarshal(responseBytes, 11);

        assertEquals("BuildBranch", response.getName());
        assertEquals(RegistryValueType.REG_SZ, response.getType());
        assertArrayEquals("th1_st1\0".getBytes("UTF-16LE"), response.getData());
        assertEquals(0, response.getReturnValue());
    }
}
