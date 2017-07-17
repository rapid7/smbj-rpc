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
package com.rapid7.client.dcerpc.mssrvs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.RPCRequest;
import com.rapid7.client.dcerpc.RPCResponse;
import com.rapid7.client.dcerpc.mssrvs.messages.NetShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.messages.NetShareInfo1;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareEnumResponse;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class Test_ServerService {
    @Test
    public void getShares()
        throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final String response1Str = "05000203100000009000000001000000780000000000000001000000010000000000020001000000040002000100000008000200000000800c000200070000000000000007000000410044004d0049004e002400000000000d000000000000000d000000520065006d006f00740065002000410064006d0069006e0000000000030000001000020005000000ea000000";
        final String response2Str = "05000203100000008800000002000000700000000000000001000000010000000000020001000000040002000100000008000200000000800c00020003000000000000000300000043002400000000000e000000000000000e000000440065006600610075006c0074002000730068006100720065000000020000001000020009000000ea000000";
        final String response3Str = "05000203100000008800000003000000700000000000000001000000010000000000020001000000040002000100000008000200030000800c0002000500000000000000050000004900500043002400000000000b000000000000000b000000520065006d006f007400650020004900500043000000000001000000100002000000000000000000";
        final RPCResponse response1 = new NetrShareEnumResponse(ByteBuffer.wrap(Hex.decode(response1Str)));
        final RPCResponse response2 = new NetrShareEnumResponse(ByteBuffer.wrap(Hex.decode(response2Str)));
        final RPCResponse response3 = new NetrShareEnumResponse(ByteBuffer.wrap(Hex.decode(response3Str)));
        final RPCRequest<RPCResponse> request = any();

        when(transport.transact(request)).thenReturn(response1).thenReturn(response2).thenReturn(response3);

        final ServerService serverService = new ServerService(transport);
        final List<NetShareInfo0> shares = serverService.getShares();
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
    }
}
