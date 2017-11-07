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

import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.hierynomus.protocol.commons.EnumWithValue;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.mssamr.messages.*;
import com.rapid7.client.dcerpc.mssamr.objects.*;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.RPC_UNICODE_STRING;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class SecurityAccountManagerService {
    private final RPCTransport transport;

    public SecurityAccountManagerService(RPCTransport transport) {
        this.transport = transport;
    }

    public ServerHandle openServerHandle(String serverName) throws IOException {
        final SamrConnect2Request request = new SamrConnect2Request(serverName, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final SamrConnect2Response response = transport.call(request);
        return response.getHandle();
    }

    public DomainHandle openDomain(ServerHandle serverHandle, SID sid) throws IOException {
        final SamrOpenDomainRequest request = new SamrOpenDomainRequest(serverHandle, sid, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final SamrOpenDomainResponse response = transport.call(request);
        return response.getHandle();
    }

    public GroupHandle openGroup(DomainHandle domainHandle, int groupRID) throws IOException {
        final SamrOpenGroupRequest request = new SamrOpenGroupRequest(domainHandle, (int) AccessMask.MAXIMUM_ALLOWED.getValue(), groupRID);
        final SamrOpenGroupResponse response = transport.call(request);
        return response.getHandle();
    }

    public UserHandle openUser(DomainHandle domainHandle, int sid) throws IOException {
        final SamrOpenUserRequest request = new SamrOpenUserRequest(domainHandle, sid);
        final SamrOpenUserResponse response = transport.call(request);
        return response.getHandle();
    }

    public AliasHandle openAlias(DomainHandle domainHandle, int sid) throws IOException {
        // AccessMask(0x0002000C)
        // SAMR Alias specific rights: 0x0000000c
        // - SAMR_ALIAS_ACCESS_LOOKUP_INFO is SET(8)
        // - SAMR_ALIAS_ACCESS_GET_MEMBERS is SET(4)
        final SamrOpenAliasRequest request = new SamrOpenAliasRequest(domainHandle, sid, 0x0002000C);
        final SamrOpenAliasResponse response = transport.call(request);
        return response.getHandle();
    }

    public void closeHandle(ContextHandle handle) throws IOException {
        final SamrCloseHandleRequest request = new SamrCloseHandleRequest(handle);
        final SamrCloseHandleResponse response = transport.call(request);

        if (response.getReturnValue() != 0)
            throw new IOException("Failed to close handle: " + new String(handle.getBytes()));
    }
}
