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
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.common.base.Strings;
import com.hierynomus.msdtyp.SecurityDescriptor;
import com.hierynomus.msdtyp.SecurityInformation;
import com.hierynomus.protocol.commons.buffer.Buffer;
import com.hierynomus.smb.SMBBuffer;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.mssamr.messages.SamrCloseHandleRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateAliasesInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateGroupsInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateUsersInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrLookupDomainInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrLookupNamesInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrLookupNamesInDomainResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenAliasRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenGroupRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryDisplayInformation2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryDisplayInformation2Response;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationAliasRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationDomain2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationGroupRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQueryInformationUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrQuerySecurityObjectRequest;
import com.rapid7.client.dcerpc.mssamr.objects.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.objects.AliasInfo;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.objects.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.objects.GroupInfo;
import com.rapid7.client.dcerpc.mssamr.objects.GroupMembership;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRAliasGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayGroup;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayGroupBuffer;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLockoutInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogOffInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRSRSecurityDescriptor;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRUserAllInformation;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.objects.UserHandle;
import com.rapid7.client.dcerpc.mssamr.objects.UserInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class SecurityAccountManagerService extends Service {
    private final static int MAXIMUM_ALLOWED = 33554432;

    public SecurityAccountManagerService(final RPCTransport transport) {
        super(transport);
    }

    public ServerHandle openServer(String serverName) throws IOException {
        final SamrConnect2Request request = new SamrConnect2Request(Strings.nullToEmpty(serverName), MAXIMUM_ALLOWED);
        return callExpectSuccess(request, "SamrConnect2").getHandle();
    }

    public DomainHandle openDomain(ServerHandle serverHandle, RPCSID domainId) throws IOException {
        final SamrOpenDomainRequest request = new SamrOpenDomainRequest(serverHandle, MAXIMUM_ALLOWED, domainId);
        return callExpectSuccess(request, "SamrOpenDomain").getHandle();
    }

    public GroupHandle openGroup(DomainHandle domainHandle, int groupId) throws IOException {
        final SamrOpenGroupRequest request = new SamrOpenGroupRequest(domainHandle, MAXIMUM_ALLOWED, groupId);
        return callExpectSuccess(request, "SamrOpenGroupRequest").getHandle();
    }

    public UserHandle openUser(DomainHandle domainHandle, int sid) throws IOException {
        final SamrOpenUserRequest request = new SamrOpenUserRequest(domainHandle, sid);
        return callExpectSuccess(request, "SamrOpenUserRequest").getHandle();
    }

    public AliasHandle openAlias(DomainHandle domainHandle, int sid) throws IOException {
        // AccessMask(0x0002000C)
        // SAMR Alias specific rights: 0x0000000c
        // - SAMR_ALIAS_ACCESS_LOOKUP_INFO is SET(8)
        // - SAMR_ALIAS_ACCESS_GET_MEMBERS is SET(4)
        final SamrOpenAliasRequest request = new SamrOpenAliasRequest(domainHandle, 0x0002000C, sid);
        return callExpectSuccess(request, "SamrOpenAlias").getHandle();
    }

    public void closeHandle(ContextHandle handle) throws IOException {
        final SamrCloseHandleRequest request = new SamrCloseHandleRequest(handle);
        callExpectSuccess(request, "SamrCloseHandle");
    }

    public List<DomainInfo> getDomainsForServer(final ServerHandle serverHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getDomainsForServer(serverHandle, bufferSize);
    }

    public List<DomainInfo> getDomainsForServer(final ServerHandle serverHandle, final int bufferSize)
            throws IOException {
        final List<DomainInfo> domains = new ArrayList<>();
        return enumerate(serverHandle, domains, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateDomainsInSamServer";
            }
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateDomainsInSamServerRequest request = new SamrEnumerateDomainsInSamServerRequest(
                        serverHandle, enumContext, bufferSize);
                return call(request);
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
            public String getName() {
                return "SamrEnumerateAliasesInDomain";
            }
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateAliasesInDomainRequest request = new SamrEnumerateAliasesInDomainRequest(
                        domainHandle, enumContext, bufferSize);
                return call(request);
            }
        });
    }

    public SAMPRUserAllInformation getUserAllInformation(final UserHandle userHandle) throws IOException {
        final SamrQueryInformationUserRequest.UserAllInformation request =
                new SamrQueryInformationUserRequest.UserAllInformation(userHandle);
        return callExpectSuccess(request, "SamrQueryInformationUser[21]").getUserInformation();
    }

    public SAMPRGroupGeneralInformation getGroupGeneralInformation(final GroupHandle groupHandle) throws IOException {
        final SamrQueryInformationGroupRequest.GroupGeneralInformation request =
                new SamrQueryInformationGroupRequest.GroupGeneralInformation(groupHandle);
        return callExpectSuccess(request, "SamrQueryInformationGroup[1]").getGroupInformation();
    }

    public SAMPRAliasGeneralInformation getAliasGeneralInformation(final AliasHandle aliasHandle) throws IOException {
        final SamrQueryInformationAliasRequest.AliasGeneralInformation request =
                new SamrQueryInformationAliasRequest.AliasGeneralInformation(aliasHandle);
        return callExpectSuccess(request, "SamrQueryInformationAlias[1]").getAliasInformation();
    }

    public SAMPRDomainPasswordInfo getDomainPasswordInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomainRequest.DomainPasswordInformation request =
                new SamrQueryInformationDomainRequest.DomainPasswordInformation(domainHandle);
        return callExpectSuccess(request, "SamrQueryInformationDomain[1]").getDomainInformation();
    }

    public SAMPRDomainLogOffInfo getDomainLogOffInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomainRequest.DomainLogOffInformation request =
                new SamrQueryInformationDomainRequest.DomainLogOffInformation(domainHandle);
        return callExpectSuccess(request, "SamrQueryInformationDomain[3]").getDomainInformation();
    }

    public SAMPRDomainLockoutInfo getDomainLockoutInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomain2Request.DomainLockoutInfo request =
                new SamrQueryInformationDomain2Request.DomainLockoutInfo(domainHandle);
        return callExpectSuccess(request, "SamrQueryInformationDomain2[12]").getDomainInformation();
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
        final List<GroupInfo> groups = new ArrayList<>();
        return enumerate(domainHandle, groups, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateGroupsInDomain";
            }
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateGroupsInDomainRequest request = new SamrEnumerateGroupsInDomainRequest(domainHandle,
                        enumContext, bufferSize);
                return call(request);
            }
        });
    }

    /**
     * Gets the user names for the provided domain. Max buffer size will be used
     *
     * @param domainHandle The domain handle.
     * @param userAccountControl The UserAccountControl flags that filters the returned users.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public List<UserInfo> getUsersForDomain(final DomainHandle domainHandle, final int userAccountControl)
            throws IOException {
        final int bufferSize = 0xffff;
        return getUsersForDomain(domainHandle, userAccountControl, bufferSize);
    }

    /**
     * Gets the user names for the provided domain. Multiple request may be sent based on the entries read and buffer
     * size.
     *
     * @param domainHandle The domain handle.
     * @param userAccountControl The UserAccountControl flags that filters the returned users.
     * @param bufferSize The buffer size for each request.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public List<UserInfo> getUsersForDomain(final DomainHandle domainHandle, final int userAccountControl,
            final int bufferSize) throws IOException {
        final List<UserInfo> users = new ArrayList<>();
        return enumerate(domainHandle, users, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateUsersInDomain";
            }
            @Override
            public SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException {
                final SamrEnumerateUsersInDomainRequest request = new SamrEnumerateUsersInDomainRequest(domainHandle,
                        enumContext, userAccountControl, bufferSize);
                return call(request);
            }
        });
    }

    public List<SAMPRDomainDisplayGroup> getDomainGroupInformationForDomain(final DomainHandle handle)
            throws IOException {
        // no limit.
        final int entryCount = 0xffffffff;
        final int maxLength = 0xffff;
        return getDomainGroupInformationForDomain(handle, entryCount, maxLength);
    }

    public List<SAMPRDomainDisplayGroup> getDomainGroupInformationForDomain(final DomainHandle handle,
            final int entryCount,
            final int maxLength) throws IOException {
        final List<SAMPRDomainDisplayGroup> groups = new ArrayList<>();
        int enumContext = 0;
        int totalReturnedBytes = 0;
        while (true) {
            final SamrQueryDisplayInformation2Request.DomainDisplayGroup request =
                    new SamrQueryDisplayInformation2Request.DomainDisplayGroup(handle, enumContext, entryCount, maxLength);
            final SamrQueryDisplayInformation2Response<SAMPRDomainDisplayGroupBuffer> response = call(request);

            List<SAMPRDomainDisplayGroup> buffer = response.getDisplayInformation().getEntries();
            if (buffer == null)
                buffer = new ArrayList<>();
            enumContext += buffer.size();
            totalReturnedBytes += response.getTotalReturned();
            int returnCode = response.getReturnValue();
            if (ERROR_MORE_ENTRIES.is(returnCode)) {
                groups.addAll(buffer);
            } else if (ERROR_NO_MORE_ITEMS.is(returnCode) || ERROR_SUCCESS.is(returnCode)
                    || totalReturnedBytes == response.getTotalAvailable()) {
                groups.addAll(buffer);
                return Collections.unmodifiableList(groups);
            } else {
                throw new RPCException("QueryDisplayInformation2", returnCode);
            }
        }
    }

    public SecurityDescriptor getSecurityObject(final ContextHandle objectHandle,
            final SecurityInformation ... securityInformation) throws IOException {
        int securityInformationValue = 0;
        if (securityInformation != null) {
            for (SecurityInformation v : securityInformation) {
                if (v == null)
                    continue;
                securityInformationValue |= v.getValue();
            }
        }
        final SamrQuerySecurityObjectRequest request = new SamrQuerySecurityObjectRequest(objectHandle, securityInformationValue);
        return parseSecurityDescriptor(callExpectSuccess(request, "SamrQuerySecurityObject").getSecurityDescriptor());
    }

    SecurityDescriptor parseSecurityDescriptor(SAMPRSRSecurityDescriptor securityDescriptor) throws IOException {
        if (securityDescriptor == null)
            return null;
        byte[] payload = securityDescriptor.getSecurityDescriptor();
        if (payload == null)
            return null;
        try {
            return SecurityDescriptor.read(new SMBBuffer(payload));
        } catch (Buffer.BufferException e) {
            throw new UnmarshalException(String.format("Failed to parse %s", SAMPRSRSecurityDescriptor.class.getSimpleName()), e);
        }
    }

    /**
     * Gets a list of {@link GroupMembership} information for the provided user handle.
     *
     * @param userHandle User handle. Must not be {@code null}.
     * @return A list of users group memberships
     * @throws IOException Throws if exception occurs on the RPC layer
     */
    public List<GroupMembership> getGroupsForUser(UserHandle userHandle) throws IOException {
        final SamrGetGroupsForUserRequest request = new SamrGetGroupsForUserRequest(userHandle);
        return callExpectSuccess(request, "SamrGetGroupsForUser").getGroups();
    }

    public RPCSID getSIDForDomain(ServerHandle serverHandle, String domainName) throws IOException {
        final SamrLookupDomainInSamServerRequest request = new SamrLookupDomainInSamServerRequest(serverHandle,
                RPCUnicodeString.NonNullTerminated.of(domainName));
        return callExpectSuccess(request, "SamrLookupDomainInSamServer").getDomainId();
    }
    
    public SamrLookupNamesInDomainResponse getNamesInDomain(DomainHandle domainHandle, String ... names)
            throws IOException {
        if (names == null)
            names = new String[0];
        final RPCUnicodeString.NonNullTerminated[] inNames = new RPCUnicodeString.NonNullTerminated[names.length];
        for (int i = 0; i < names.length; i++) {
            inNames[i] = RPCUnicodeString.NonNullTerminated.of(names[i]);
        }
        final SamrLookupNamesInDomainRequest request = new SamrLookupNamesInDomainRequest(domainHandle, inNames);
        return callExpectSuccess(request, "SamrLookupNamesInDomain");
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
                throw new RPCException(response.getClass().getName(), returnCode);
            }
        }
    }

    /**
     * Anonymous function for calling the enumeration request.
     */
    private interface EnumerationCallback {
        String getName();
        SamrEnumerateResponse request(ContextHandle handle, int enumContext) throws IOException;
    }
}
