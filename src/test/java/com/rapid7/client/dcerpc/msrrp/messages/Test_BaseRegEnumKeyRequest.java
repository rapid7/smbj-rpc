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

import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Test_BaseRegEnumKeyRequest {
    private final byte[] contextHandle = Hex.decode("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegEnumKeyRequest request = new BaseRegEnumKeyRequest(contextHandle, 0, 256, 32767);

    @Test
    public void getOpNum() {
        assertEquals(9, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
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
        assertEquals("0000000032daf234b77c86409d29efe60d326683000000000000000200000200000100000000000000000000040002000000feff08000200ff7f000000000000000000000c0002000000000000000000", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(BaseRegEnumKeyResponse.class));
    }
}
