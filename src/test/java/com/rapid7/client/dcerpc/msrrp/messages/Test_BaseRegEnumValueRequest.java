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

public class Test_BaseRegEnumValueRequest {
    private final byte[] contextHandle = Hex.decode("000000000a665393f4666e49a68cd99f269d020f");
    private final BaseRegEnumValueRequest request = new BaseRegEnumValueRequest(contextHandle, 0, 32767, 65536);

    @Test
    public void getOpNum() {
        assertEquals(10, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
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
        assertEquals("000000000a665393f4666e49a68cd99f269d020f000000000000feff00000200ff7f000000000000000000000400020000000000080002000000010000000000000000000c000200000001001000020000000000", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(BaseRegEnumValueResponse.class));
    }
}
