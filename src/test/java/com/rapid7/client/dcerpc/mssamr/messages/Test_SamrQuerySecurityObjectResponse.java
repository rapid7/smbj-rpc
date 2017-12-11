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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class Test_SamrQuerySecurityObjectResponse {
    @Test
    public void test_getters_default() {
        SamrQuerySecurityObjectResponse response = new SamrQuerySecurityObjectResponse();
        assertNull(response.getSecurityDescriptor());
    }

    @Test
    public void test_unmarshal() throws IOException {

        String hex =
                // Reference: 2
                "02000000" +
                // SecurityDescriptor: Length: 2 Reference: 2
                "02000000 00000200" +
                // SecurityDescriptor: MaximumCount: 3 SecurityDescriptor: {1, 2}
                "02000000 01 02" +
                // Alignment, ReturnValue: 1
                "FFFF 01000000";

        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        SamrQuerySecurityObjectResponse response = new SamrQuerySecurityObjectResponse();
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
        assertNotNull(response.getSecurityDescriptor());
        assertEquals(response.getSecurityDescriptor().getSecurityDescriptor(), new byte[]{1, 2});
        assertEquals(response.getReturnValue(), 1);
    }

    @Test
    public void test_unmarshal_null() throws IOException {
        // Reference: 0, ReturnValue: 1
        String hex = "00000000 01000000";
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        SamrQuerySecurityObjectResponse response = new SamrQuerySecurityObjectResponse();
        response.unmarshal(in);
        assertEquals(bin.available(), 0);
        assertNull(response.getSecurityDescriptor());
        assertEquals(response.getReturnValue(), 1);
    }
}
