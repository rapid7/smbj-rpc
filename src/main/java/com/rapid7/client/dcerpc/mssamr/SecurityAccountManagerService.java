/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr;

import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Response;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenLocalGroupRpcRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenLocalGroupRpcResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserResponse;
import com.rapid7.client.dcerpc.mssamr.objects.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.objects.UserHandle;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class SecurityAccountManagerService {
    private final RPCTransport transport;

    public SecurityAccountManagerService(RPCTransport transport) {
        this.transport = transport;
    }

    public ServerHandle getServerHandle(String serverName)
        throws IOException {
        final SamrConnect2Request request = new SamrConnect2Request(serverName, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final SamrConnect2Response response = transport.call(request);
        return response.getHandle();
    }

    public DomainHandle openDomain(String serverName, SID sid)
        throws IOException {
        final ServerHandle handle = getServerHandle(serverName);
        final SamrOpenDomainRequest request = new SamrOpenDomainRequest(handle, sid,
            EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final SamrOpenDomainResponse response = transport.call(request);
        return response.getHandle();
    }

    public DomainHandle openDomain(ServerHandle serverHandle, SID sid)
        throws IOException {
        final SamrOpenDomainRequest request = new SamrOpenDomainRequest(serverHandle, sid,
            EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
        final SamrOpenDomainResponse response = transport.call(request);
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
	final SamrOpenLocalGroupRpcRequest request = new SamrOpenLocalGroupRpcRequest(domainHandle, sid, 0x0002000C);
	final SamrOpenLocalGroupRpcResponse response = transport.call(request);
	return response.getHandle();
    }
}
