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

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_MORE_ENTRIES;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_NO_MORE_ITEMS;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.mssamr.messages.SamrCloseHandleRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrCloseHandleResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Response;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateAliasesInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateGroupsInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateUsersInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenAliasRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenAliasResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenGroupRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenGroupResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationAliasRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationGroupRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationUserRequest;
import com.rapid7.client.dcerpc.mssamr.objects.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.objects.AliasInfo;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.objects.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRAliasGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRUserAllInformation;
import com.rapid7.client.dcerpc.mssamr.objects.GroupInfo;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.objects.UserHandle;
import com.rapid7.client.dcerpc.mssamr.objects.UserInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;
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
        final SamrOpenGroupRequest request = new SamrOpenGroupRequest(domainHandle, EnumSet.of(AccessMask.MAXIMUM_ALLOWED), groupRID);
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

    public List<DomainInfo> getDomainsForServer(final ServerHandle serverHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getDomainsForServer(serverHandle, bufferSize);
    }

    public List<DomainInfo> getDomainsForServer(final ServerHandle serverHandle, final int bufferSize)
            throws IOException {
        List<DomainInfo> domains = new ArrayList<>();
        return enumerate(serverHandle, domains, new EnumerationCallback() {
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateDomainsInSamServerRequest request = new SamrEnumerateDomainsInSamServerRequest(
                        serverHandle, enumContext, bufferSize);
                return transport.call(request);
            }
        });
    }

    public List<AliasInfo> getAliasesForDomain(final DomainHandle domainHandle)
            throws IOException {
        final int bufferSize = 0xffff;
        return getAliasesForDomain(domainHandle, bufferSize);
    }

    public List<AliasInfo> getAliasesForDomain(final DomainHandle domainHandle, final int bufferSize)
            throws IOException {
        List<AliasInfo> aliases = new ArrayList<>();
        return enumerate(domainHandle, aliases, new EnumerationCallback() {
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateAliasesInDomainRequest request = new SamrEnumerateAliasesInDomainRequest(
                        domainHandle, enumContext, bufferSize);
                return transport.call(request);
            }
        });
    }

    public SAMPRUserAllInformation getUserAllInformation(final UserHandle userHandle) throws IOException {
        SamrQueryInformationUserRequest.UserAllInformation request = new SamrQueryInformationUserRequest.UserAllInformation(userHandle);
        return transport.call(request).getUserInformation();
    }

    public SAMPRGroupGeneralInformation getGroupGeneralInformation(final GroupHandle groupHandle) throws IOException {
        SamrQueryInformationGroupRequest.GroupGeneralInformation request = new SamrQueryInformationGroupRequest.GroupGeneralInformation(groupHandle);
        return transport.call(request).getGroupInformation();
    }

    public SAMPRAliasGeneralInformation getAliasGeneralInformation(final AliasHandle aliasHandle) throws IOException {
        SamrQueryInformationAliasRequest.AliasGeneralInformation request = new SamrQueryInformationAliasRequest.AliasGeneralInformation(aliasHandle);
        return transport.call(request).getAliasInformation();
    }

    /**
     * Gets the group names for the provided domain. Max buffer size will be used.
     *
     * @param domainHandle The domain handle.
     * @return The enumerated groups.
     * @throws IOException On issue with communication or marshalling.
     */
    public List<GroupInfo> getGroupsForDomain(final DomainHandle domainHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getGroupsForDomain(domainHandle, bufferSize);
    }

    /**
     * Gets the group names for the provided domain. Multiple request may be sent based on the entries read and buffer
     * size.
     *
     * @param domainHandle The domain handle.
     * @param bufferSize The buffer size for each request.
     * @return The enumerated groups.
     * @throws IOException On issue with communication or marshalling.
     */
    public List<GroupInfo> getGroupsForDomain(final DomainHandle domainHandle, final int bufferSize)
            throws IOException {
        List<GroupInfo> groups = new ArrayList<>();
        return enumerate(domainHandle, groups, new EnumerationCallback() {
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateGroupsInDomainRequest request = new SamrEnumerateGroupsInDomainRequest(domainHandle,
                        enumContext, bufferSize);
                return transport.call(request);
            }
        });
    }

    /**
     * Gets the user names for the provided domain. Max buffer size will be used
     *
     * @param domainHandle The domain handle.
     * @param userAccountContorl The UserAccountControl flags that filters the returned users.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public List<UserInfo> getUsersForDomain(final DomainHandle domainHandle, final int userAccountContorl)
            throws IOException {
        final int bufferSize = 0xffff;
        return getUsersForDomain(domainHandle, userAccountContorl, bufferSize);
    }

    /**
     * Gets the user names for the provided domain. Multiple request may be sent based on the entries read and buffer
     * size.
     *
     * @param domainHandle The domain handle.
     * @param userAccountContorl The UserAccountControl flags that filters the returned users.
     * @param bufferSize The buffer size for each request.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public List<UserInfo> getUsersForDomain(final DomainHandle domainHandle, final int userAccountContorl,
            final int bufferSize) throws IOException {
        List<UserInfo> users = new ArrayList<>();
        return enumerate(domainHandle, users, new EnumerationCallback() {
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateUsersInDomainRequest request = new SamrEnumerateUsersInDomainRequest(domainHandle,
                        enumContext, userAccountContorl, bufferSize);
                return transport.call(request);
            }
        });
    }

    /**
     * Helper method for calling {@link SamrEnumerateRequest}(s) enumerating through the buffers for
     * {@link SamrEnumerateResponse}.
     */
    private <T> List<T> enumerate(ContextHandle handle, List<T> list, EnumerationCallback callback) throws IOException {
        for (int enumContext = 0;;) {
            SamrEnumerateResponse response = callback.request(handle, enumContext);
            final int returnCode = response.getReturnValue();
            enumContext = response.getResumeHandle();
            if (ERROR_MORE_ENTRIES.is(returnCode)) {
                list.addAll(response.getList());
            } else if (ERROR_NO_MORE_ITEMS.is(returnCode) || ERROR_SUCCESS.is(returnCode)) {
                list.addAll(response.getList());
                return Collections.unmodifiableList(list);
            } else {
                throw new RPCException("EnumDomainsInSamServer", returnCode);
            }
        }
    }

    /**
     * Anonymous function for calling the enumeration request.
     */
    private interface EnumerationCallback {
        SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException;
    }
}
