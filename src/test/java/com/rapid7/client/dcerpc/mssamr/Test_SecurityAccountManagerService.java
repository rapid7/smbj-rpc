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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.mockito.Mockito;
import com.hierynomus.msdtyp.SID;
import com.hierynomus.msdtyp.SecurityDescriptor;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembersInGroupRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembesInGroupResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.objects.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.objects.GroupMembership;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRSRSecurityDescriptor;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.objects.UserHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class Test_SecurityAccountManagerService {

    @Test
    public void getDomainsForServer() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        ServerHandle handle = new ServerHandle();
        SamrEnumerateDomainsInSamServerResponse response = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        Mockito.when(response.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.ordinal());
        List<DomainInfo> domains = new ArrayList<>();
        domains.add(Mockito.mock(DomainInfo.class));
        domains.add(Mockito.mock(DomainInfo.class));
        Mockito.when(response.getList()).thenReturn(domains);
        Mockito.when(transport.call(Mockito.any(SamrEnumerateDomainsInSamServerRequest.class))).thenReturn(response);
        assertEquals(2, service.getDomainsForServer(handle).size());
    }

    @Test
    public void getDomainsForServerMoreEntries() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        ServerHandle handle = new ServerHandle();
        SamrEnumerateDomainsInSamServerResponse response1 = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        SamrEnumerateDomainsInSamServerResponse response2 = Mockito.mock(SamrEnumerateDomainsInSamServerResponse.class);
        Mockito.when(response1.getReturnValue()).thenReturn(SystemErrorCode.ERROR_MORE_ENTRIES.getValue());
        Mockito.when(response1.getResumeHandle()).thenReturn(1);
        Mockito.when(response2.getReturnValue()).thenReturn(SystemErrorCode.ERROR_SUCCESS.getValue());
        Mockito.when(response2.getResumeHandle()).thenReturn(2);
        List<DomainInfo> domains1 = new ArrayList<>();
        List<DomainInfo> domains2 = new ArrayList<>();
        domains1.add(Mockito.mock(DomainInfo.class));
        domains2.add(Mockito.mock(DomainInfo.class));
        Mockito.when(response1.getList()).thenReturn(domains1);
        Mockito.when(response2.getList()).thenReturn(domains2);
        Mockito.when(transport.call(Mockito.any(SamrEnumerateDomainsInSamServerRequest.class))).thenReturn(response1)
            .thenReturn(response2);
        assertEquals(2, service.getDomainsForServer(handle).size());
    }

    @Test
    public void test_parseSecurityDescriptor() throws IOException {
        String hex = "01000080140000002400000000000000000000000102000000000005200000002002000001020000000000052000000020020000";

        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        SAMPRSRSecurityDescriptor samprsrSecurityDescriptor = new SAMPRSRSecurityDescriptor();
        samprsrSecurityDescriptor.setSecurityDescriptor(Hex.decode(hex));

        SecurityDescriptor securityDescriptor = service.parseSecurityDescriptor(samprsrSecurityDescriptor);
        assertEquals(Collections.singleton(SecurityDescriptor.Control.SR), securityDescriptor.getControl());
        assertEquals(SID.fromString("S-1-5-32-544"), securityDescriptor.getOwnerSid());
        assertEquals(SID.fromString("S-1-5-32-544"), securityDescriptor.getGroupSid());
    }

    @Test
    public void test_parseSecurityDescriptor_nullSAMPRSRSecurityDescriptor() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        assertNull(service.parseSecurityDescriptor(null));
    }

    @Test
    public void test_parseSecurityDescriptor_nullPayload() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        assertNull(service.parseSecurityDescriptor(new SAMPRSRSecurityDescriptor()));
    }

    @Test
    public void test_getMembersForGroup() throws IOException {
        RPCTransport transport = Mockito.mock(RPCTransport.class);
        SecurityAccountManagerService service = new SecurityAccountManagerService(transport);
        SamrGetMembesInGroupResponse response = Mockito.mock(SamrGetMembesInGroupResponse.class);
        Mockito.when(response.getReturnValue()).thenReturn(0);
        List<GroupMembership> users = new ArrayList<>();
        GroupMembership returnedMembership = new GroupMembership();
        returnedMembership.setAttributes(7);
        returnedMembership.setRelativeID(500);
        users.add(returnedMembership);
        Mockito.when(response.getList()).thenReturn(users);
        Mockito.when(transport.call(Mockito.any(SamrGetMembersInGroupRequest.class))).thenReturn(response);
        List<Membership> membership = service.getMembersForGroup(Mockito.mock(GroupHandle.class));
        assertEquals(1, membership.size());
        assertEquals(500, membership.get(0).getRelativeID());
        assertEquals(7, membership.get(0).getAttributes());
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
        List<Membership> membership = service.getGroupsForUser(Mockito.mock(UserHandle.class));
        assertEquals(1, membership.size());
        assertEquals(500, membership.get(0).getRelativeID());
        assertEquals(7, membership.get(0).getAttributes());
    }
}
