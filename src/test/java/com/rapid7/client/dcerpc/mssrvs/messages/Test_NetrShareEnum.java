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
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Test_NetrShareEnum {
    private final NetrShareEnumRequest request = new NetrShareEnumRequest(1, null);

    @Test
    public void getOpNum() {
        assertEquals(15, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        assertEquals("0000000001000000010000000000020000000000000000000000100000000000", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        final NetrShareEnumResponse response = request.getResponseObject();
        final ByteArrayInputStream packetInputStream = new ByteArrayInputStream(Hex.decode("01000000010000000000020003000000040002000300000008000200000000800c00020010000200000000801400020018000200030000801c000200070000000000000007000000410044004d0049004e002400000000000d000000000000000d000000520065006d006f00740065002000410064006d0069006e000000000003000000000000000300000043002400000000000e000000000000000e000000440065006600610075006c00740020007300680061007200650000000500000000000000050000004900500043002400000000000b000000000000000b000000520065006d006f0074006500200049005000430000000000030000000000000000000000"));
        final PacketInput packetIn = new PacketInput(packetInputStream);

        response.unmarshal(packetIn);

        assertEquals(1, response.getLevel());

        final List<NetShareInfo0> shares = response.getShares();
        final NetShareInfo1 share0 = (NetShareInfo1) shares.get(0);
        final NetShareInfo1 share1 = (NetShareInfo1) shares.get(1);
        final NetShareInfo1 share2 = (NetShareInfo1) shares.get(2);

        assertEquals(3, shares.size());
        assertEquals("ADMIN$", share0.getName());
        assertEquals(-2147483648, share0.getType());
        assertEquals("Remote Admin", share0.getComment());
        assertEquals("C$", share1.getName());
        assertEquals(-2147483648, share1.getType());
        assertEquals("Default share", share1.getComment());
        assertEquals("IPC$", share2.getName());
        assertEquals(-2147483645, share2.getType());
        assertEquals("Remote IPC", share2.getComment());

        assertNull(response.getResumeHandle());
        assertEquals(SystemErrorCode.ERROR_SUCCESS.getValue(), response.getReturnValue());
    }
}
