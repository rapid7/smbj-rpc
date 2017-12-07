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
package com.rapid7.client.dcerpc.mssrvs;

import java.io.IOException;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareEnumResponse;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfoContainer;
import com.rapid7.client.dcerpc.objects.WChar;
import com.rapid7.client.dcerpc.transport.RPCTransport;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.testng.Assert.assertEquals;

public class Test_ServerService {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void getShares() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final NetrShareEnumResponse response = mock(NetrShareEnumResponse.class);

        when(transport.call((RequestCall<NetrShareEnumResponse>) any())).thenReturn(response);
        when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.getValue());
        ShareEnumStruct.ShareEnumStruct0 shareEnumStruct0 = mock(ShareEnumStruct.ShareEnumStruct0.class);
        ShareInfoContainer.ShareInfo0Container shareInfo0Container = mock(ShareInfoContainer.ShareInfo0Container.class);
        when(shareEnumStruct0.getShareInfoContainer()).thenReturn(shareInfo0Container);
        ShareInfo0[] shareInfo0s = new ShareInfo0[1];
        ShareInfo0 shareInfo0 = new ShareInfo0();
        shareInfo0.setNetName(WChar.NullTerminated.of("test1"));
        shareInfo0s[0] = shareInfo0;
        when(shareInfo0Container.getBuffer()).thenReturn(shareInfo0s);
        when(response.getShareEnumStruct()).thenReturn(shareEnumStruct0);

        final ServerService serverService = new ServerService(transport);
        final List<NetShareInfo0> shares = serverService.getShares0();
        final NetShareInfo0 share0 = shares.get(0);

        assertEquals(1, shares.size());
        assertEquals("test1", share0.getNetName());
    }
/*
    @SuppressWarnings("unchecked")
    @Test
    public void getSharesTruncated() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final NetrShareEnumResponse response = mock(NetrShareEnumResponse.class);

        when(transport.call((RequestCall<NetrShareEnumResponse>) any())).thenReturn(response);
        when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_DATA.getValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.getValue());
        when(response.getResumeHandle()).thenReturn(1);
        when(response.getShares()).thenReturn(Lists.newArrayList(new NetShareInfo0("test1"))).thenReturn(Lists.newArrayList(new NetShareInfo0("test2")));

        final ServerService serverService = new ServerService(transport);
        final List<NetShareInfo0> shares = serverService.getShares();
        final NetShareInfo0 share0 = shares.get(0);
        final NetShareInfo0 share1 = shares.get(1);

        assertEquals(2, shares.size());
        assertEquals("test1", share0.getName());
        assertEquals("test2", share1.getName());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getSharesAccessDenied() throws IOException {
        thrown.expect(RPCException.class);
        thrown.expectMessage("NetrShareEnum returned error code: 5 (ERROR_ACCESS_DENIED)");

        final RPCTransport transport = mock(RPCTransport.class);
        final NetrShareEnumResponse response = mock(NetrShareEnumResponse.class);

        when(transport.call((RequestCall<NetrShareEnumResponse>) any())).thenReturn(response);
        when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_ACCESS_DENIED.getValue());

        final ServerService serverService = new ServerService(transport);
        serverService.getShares();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getSharesMoreDataEmptyShares() throws IOException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("NetrShareEnum shares empty.");

        final RPCTransport transport = mock(RPCTransport.class);
        final NetrShareEnumResponse response = mock(NetrShareEnumResponse.class);

        when(transport.call((RequestCall<NetrShareEnumResponse>) any())).thenReturn(response);
        when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_DATA.getValue());
        when(response.getResumeHandle()).thenReturn(0);
        when(response.getShares()).thenReturn(new LinkedList<NetShareInfo0>());

        final ServerService serverService = new ServerService(transport);
        serverService.getShares();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getSharesMoreDataBadResume() throws IOException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("NetrShareEnum resume handle not updated");

        final RPCTransport transport = mock(RPCTransport.class);
        final NetrShareEnumResponse response = mock(NetrShareEnumResponse.class);

        when(transport.call((RequestCall<NetrShareEnumResponse>) any())).thenReturn(response);
        when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_DATA.getValue());
        when(response.getResumeHandle()).thenReturn(0).thenReturn(0);
        when(response.getShares()).thenReturn(Lists.newArrayList(new NetShareInfo0("test")));

        final ServerService serverService = new ServerService(transport);
        serverService.getShares();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getSharesMoreDataNullResume() throws IOException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("NetrShareEnum resume handle null.");

        final RPCTransport transport = mock(RPCTransport.class);
        final NetrShareEnumResponse response = mock(NetrShareEnumResponse.class);

        when(transport.call((RequestCall<NetrShareEnumResponse>) any())).thenReturn(response);
        when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_DATA.getValue());
        when(response.getResumeHandle()).thenReturn(null);
        when(response.getShares()).thenReturn(Lists.newArrayList(new NetShareInfo0("test")));

        final ServerService serverService = new ServerService(transport);
        serverService.getShares();
    }*/
}
