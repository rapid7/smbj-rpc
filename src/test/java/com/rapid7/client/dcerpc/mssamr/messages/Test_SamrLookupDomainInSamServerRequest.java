/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class Test_SamrLookupDomainInSamServerRequest {

    @Test
    public void test_getters() {
        ServerHandle serverHandle = new ServerHandle();
        RPCUnicodeString.NonNullTerminated name = new RPCUnicodeString.NonNullTerminated();
        SamrLookupDomainInSamServerRequest obj = new SamrLookupDomainInSamServerRequest(serverHandle, name);
        assertSame(obj.getServerHandle(), serverHandle);
        assertSame(obj.getName(), name);
        assertNotNull(obj.getResponseObject());
    }

    @Test
    public void test_marshal() throws IOException {
        String expectHex =
                // ServerHandle: {1...20}
                "0102030405060708090a0b0c0d0e0f1011121314" +
                // RPCUnicodeString.NonNullTerminated entity: {Length:14,MaximumLength:14,Reference:Non-zero}
                "0e000e0000000200" +
                // RPCUnicodeString.NonNullTerminated deferral: {MaximumCount:7,Offset:0,ActualCount:7,Value:test123}
                "0700000000000000070000007400650073007400310032003300";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);

        ServerHandle serverHandle = new ServerHandle();
        serverHandle.setBytes(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20});
        RPCUnicodeString.NonNullTerminated name = RPCUnicodeString.NonNullTerminated.of("test123");
        SamrLookupDomainInSamServerRequest obj = new SamrLookupDomainInSamServerRequest(serverHandle, name);
        obj.marshal(out);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectHex);
    }
}
