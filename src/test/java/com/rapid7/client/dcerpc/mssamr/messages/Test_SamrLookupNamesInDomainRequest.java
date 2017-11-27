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
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class Test_SamrLookupNamesInDomainRequest {
    @Test
    public void test_getters() {
        byte[] domainHandle = new byte[20];
        RPCUnicodeString.NonNullTerminated[] names = new RPCUnicodeString.NonNullTerminated[2];

        SamrLookupNamesInDomainRequest obj = new SamrLookupNamesInDomainRequest(domainHandle, names);
        assertSame(obj.getDomainHandle(), domainHandle);
        assertSame(obj.getNames(), names);
        assertNotNull(obj.getResponseObject());
        assertEquals(obj.getOpNum(), SamrLookupNamesInDomainRequest.OP_NUM);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "domainHandle must not be null")
    public void test_DomainHandleNull() {
        new SamrLookupNamesInDomainRequest(null, new RPCUnicodeString.NonNullTerminated[2]);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "names must not be null")
    public void test_NamesNull() {
        new SamrLookupNamesInDomainRequest(new byte[20], null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "names must not contain more than 1000 elements, got: 1001")
    public void test_NamesTooLong() {
        new SamrLookupNamesInDomainRequest(new byte[20], new RPCUnicodeString.NonNullTerminated[1001]);
    }

    @Test
    public void test_marshall() throws IOException {
        String expectHex =
                // DomainHandle
                "0102030405060708090a0b0c0d0e0f1011121314" +
                // Count: 3
                "03000000" +
                // MaximumCount: 1000
                "e8030000" +
                // Offset: 0
                "00000000" +
                // Actual Count: 3
                "03000000" +
                // RPCUnicodeString.NonNullTerminated entity: {Length:26,MaximumLength:26,Reference:Non-zero}
                "1a001a0000000200" +
                // RPCUnicodeString.NonNullTerminated entity: {Length:0,MaximumLength:0,Reference:0}
                "0000000000000000" +
                // RPCUnicodeString.NonNullTerminated entity: {Length:14,MaximumLength:14,Reference:Non-zero}
                "0e000e0004000200" +
                // RPCUnicodeString.NonNullTerminated deferral: {MaximumCount:13,Offset:0,ActualCount:13,Value:Administrator}
                "0d000000000000000d000000410064006d0069006e006900730074007200610074006f007200" +
                // Alignment: 2b
                "0000" +
                // RPCUnicodeString.NonNullTerminated deferral: {MaximumCount:7,Offset:0,ActualCount:7,Value:Test123}
                "0700000000000000070000005400650073007400310032003300";
        byte[] domainHandle = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        RPCUnicodeString.NonNullTerminated[] names = new RPCUnicodeString.NonNullTerminated[] {
            RPCUnicodeString.NonNullTerminated.of("Administrator"),
            RPCUnicodeString.NonNullTerminated.of(null),
            RPCUnicodeString.NonNullTerminated.of("Test123")
        };

        SamrLookupNamesInDomainRequest obj = new SamrLookupNamesInDomainRequest(domainHandle, names);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        obj.marshal(out);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectHex);
    }
}
