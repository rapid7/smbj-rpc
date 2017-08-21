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
package com.rapid7.client.dcerpc.mssrvs.messages;

import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.hierynomus.protocol.transport.TransportException;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Test_NetrShareEnum {
    @Test
    public void requestNetShareInfo1() {
        final NetrShareEnumRequest request = new NetrShareEnumRequest(1, null);
        final byte[] requestBytes = request.marshal(1);
        final String requestStr = Hex.toHexString(requestBytes);

        assertEquals(
            "050000031000000038000000010000003800000000000f000000000001000000010000000000020000000000000000000000100000000000",
            requestStr);
    }

    @Test
    public void responseNetShareInfo1()
        throws TransportException {
        final NetrShareEnumRequest request = new NetrShareEnumRequest(1, null);
        final String response = "05000203100000001c01000001000000040100000000000001000000010000000000020003000000040002000300000008000200000000800c00020010000200000000801400020018000200030000801c000200070000000000000007000000410044004d0049004e002400000000000d000000000000000d000000520065006d006f00740065002000410064006d0069006e000000000003000000000000000300000043002400000000000e000000000000000e000000440065006600610075006c00740020007300680061007200650000000500000000000000050000004900500043002400000000000b000000000000000b000000520065006d006f0074006500200049005000430000000000030000000000000000000000";
        final byte[] responseBytes = Hex.decode(response);
        final NetrShareEnumResponse responseObj = request.unmarshal(responseBytes, 1);

        assertEquals(1, responseObj.getLevel());

        final List<NetShareInfo0> shares = responseObj.getShares();
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

        assertNull(responseObj.getResumeHandle());
        assertEquals(SystemErrorCode.ERROR_SUCCESS.getErrorCode(), responseObj.getReturnValue());
    }
}
