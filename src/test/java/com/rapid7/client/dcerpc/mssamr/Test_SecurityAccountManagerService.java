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
package com.rapid7.client.dcerpc.mssamr;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithAttributes;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithName;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembersInGroupRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembersInGroupResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.objects.GroupMembership;
import com.rapid7.client.dcerpc.mssamr.dto.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.dto.UserHandle;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class Test_SecurityAccountManagerService {

    @Test
    public void getDomainsForServer() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        ServerHandle handle = new ServerHandle(new byte[20]);
        SamrEnumerateDomainsInSamServerResponse response = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        Mockito.when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.ordinal());
        List<DomainInfo> domains = new ArrayList<>();
        DomainInfo domainInfo1 = Mockito.mock(DomainInfo.class);
        Mockito.when(domainInfo1.getName()).thenReturn(RPCUnicodeString.NonNullTerminated.of("test123"));
        Mockito.when(domainInfo1.getRelativeId()).thenReturn(1);
        domains.add(domainInfo1);
        DomainInfo domainInfo2 = Mockito.mock(DomainInfo.class);
        Mockito.when(domainInfo2.getName()).thenReturn(RPCUnicodeString.NonNullTerminated.of(null));
        Mockito.when(domainInfo2.getRelativeId()).thenReturn(2);
        domains.add(domainInfo2);
        Mockito.when(response.getList()).thenReturn(domains);
        Mockito.when(transport.call(Mockito.any(SamrEnumerateDomainsInSamServerRequest.class))).thenReturn(response);
        MembershipWithName[] expected = new MembershipWithName[]{
                new MembershipWithName(1L, "test123"),
                new MembershipWithName(2L, null),
        };
        assertArrayEquals(expected, service.getDomainsForServer(handle));
    }

    @Test
    public void getDomainsForServerMoreEntries() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        ServerHandle handle = new ServerHandle(new byte[20]);
        SamrEnumerateDomainsInSamServerResponse response1 = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        SamrEnumerateDomainsInSamServerResponse response2 = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        Mockito.when(response1.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_ENTRIES.getValue());
        Mockito.when(response1.getResumeHandle()).thenReturn(1);
        Mockito.when(response2.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.getValue());
        Mockito.when(response2.getResumeHandle()).thenReturn(2);
        List<DomainInfo> domains1 = new ArrayList<>();
        List<DomainInfo> domains2 = new ArrayList<>();
        DomainInfo domainInfo1 = Mockito.mock(DomainInfo.class);
        Mockito.when(domainInfo1.getName()).thenReturn(RPCUnicodeString.NonNullTerminated.of("test123"));
        Mockito.when(domainInfo1.getRelativeId()).thenReturn(1);
        domains1.add(domainInfo1);
        DomainInfo domainInfo2 = Mockito.mock(DomainInfo.class);
        Mockito.when(domainInfo2.getName()).thenReturn(RPCUnicodeString.NonNullTerminated.of(null));
        Mockito.when(domainInfo2.getRelativeId()).thenReturn(2);
        domains2.add(domainInfo2);
        Mockito.when(response1.getList()).thenReturn(domains1);
        Mockito.when(response2.getList()).thenReturn(domains2);
        Mockito.when(transport.call(Mockito.any(SamrEnumerateDomainsInSamServerRequest.class))).thenReturn(response1)
            .thenReturn(response2);

        MembershipWithName[] expected = new MembershipWithName[]{
                new MembershipWithName(1L, "test123"),
                new MembershipWithName(2L, null),
        };
        assertArrayEquals(expected, service.getDomainsForServer(handle));
    }

    @Test
    public void test_getMembersForGroup() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        SamrGetMembersInGroupResponse response = Mockito.mock(SamrGetMembersInGroupResponse.class);
        Mockito.when(response.getReturnValue()).thenReturn(0);
        List<GroupMembership> users = new ArrayList<>();
        GroupMembership returnedMembership = new GroupMembership();
        returnedMembership.setAttributes(7);
        returnedMembership.setRelativeID(500);
        users.add(returnedMembership);
        Mockito.when(response.getList()).thenReturn(users);
        Mockito.when(transport.call(Mockito.any(SamrGetMembersInGroupRequest.class))).thenReturn(response);
        MembershipWithAttributes[] membership = service.getMembersForGroup(Mockito.mock(GroupHandle.class));
        assertEquals(1, membership.length);
        assertEquals(500, membership[0].getRelativeID());
        assertEquals(7, membership[0].getAttributes());
    }

    @Test
    public void getGroupsForUser() throws IOException
    {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        SamrGetGroupsForUserResponse response = Mockito.mock(SamrGetGroupsForUserResponse.class);
        Mockito.when(response.getReturnValue()).thenReturn(0);
        List<GroupMembership> users = new ArrayList<>();
        GroupMembership returnedMembership = new GroupMembership();
        returnedMembership.setAttributes(7);
        returnedMembership.setRelativeID(500);
        users.add(returnedMembership);
        Mockito.when(response.getGroups()).thenReturn(users);
        Mockito.when(transport.call(Mockito.any(SamrGetGroupsForUserRequest.class))).thenReturn(response);
        MembershipWithAttributes[] membership = service.getGroupsForUser(Mockito.mock(UserHandle.class));
        assertEquals(1, membership.length);
        assertEquals(500, membership[0].getRelativeID());
        assertEquals(7, membership[0].getAttributes());
    }
}
