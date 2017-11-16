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
import com.rapid7.client.dcerpc.objects.ContextHandle;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class Test_SamrQuerySecurityObjectRequest {
    @Test
    public void test_getters() {
        ContextHandle objectHandle = new ContextHandle();
        int securityInformation = 50;
        SamrQuerySecurityObjectRequest request = new SamrQuerySecurityObjectRequest(objectHandle, securityInformation);
        assertSame(request.getObjectHandle(), objectHandle);
        assertEquals(request.getSecurityInformation(), 50);
        assertNotNull(request.getResponseObject());
    }

    @Test
    public void test_marshall() throws IOException {
        String expectHex =
                // ContextHandle: {1, 2, 3, 4, ...}
                "0102030405060708090A0B0C0E0F101112131415" +
                // SecurityInformation: 50
                "32000000";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);

        ContextHandle objectHandle = new ContextHandle();
        objectHandle.setBytes(Hex.decode("0102030405060708090A0B0C0E0F101112131415"));
        int securityInformation = 50;
        SamrQuerySecurityObjectRequest request = new SamrQuerySecurityObjectRequest(objectHandle, securityInformation);
        request.marshal(out);

        assertEquals(Hex.toHexString(bout.toByteArray()), expectHex.toLowerCase());
    }
}
