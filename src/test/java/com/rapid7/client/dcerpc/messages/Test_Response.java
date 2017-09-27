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
package com.rapid7.client.dcerpc.messages;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.PFCFlag;
import com.rapid7.client.dcerpc.io.PacketInput;

public class Test_Response {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void getStub() {
        final Response response = new Response();
        assertNull(response.getStub());
    }

    @Test
    public void setStub() {
        final Response response = new Response();
        response.setStub(new byte[] { 0x00 });
        assertArrayEquals(new byte[] { 0x00 }, response.getStub());
    }

    @Test
    public void marshal()
        throws IOException {
        final Response response = new Response();

        response.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        response.setCallID(1);
        response.setStub(new byte[0]);

        assertEquals("050002031000000018000000010000000000000000000000", response.toHexString());
    }

    @Test
    public void unmarshal()
        throws IOException {
        final Response response = new Response();

        response.fromHexString("050002031000000018000000010000000000000000000000");

        assertEquals(5, response.getMajorVersion());
        assertEquals(0, response.getMinorVersion());
        assertEquals(PDUType.RESPONSE, response.getPDUType());
        assertEquals(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT), response.getPFCFlags());
        assertArrayEquals(new byte[] { 0x10, 0x00, 0x00, 0x00 }, response.getNDR());
        assertEquals(24, response.getFragLength());
        assertEquals(0, response.getAuthLength());
        assertEquals(1, response.getCallID());
        assertArrayEquals(new byte[0], response.getStub());
    }
}
