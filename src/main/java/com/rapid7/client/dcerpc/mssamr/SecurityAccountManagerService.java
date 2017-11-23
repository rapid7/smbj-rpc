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
import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.dto.DomainHandle;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.dto.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.dto.UserHandle;
import com.rapid7.client.dcerpc.mssamr.messages.SamrCloseHandleRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Request;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateAliasesInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateDomainsInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateGroupsInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrEnumerateUsersInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetAliasMembershipRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetAliasMembershipResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetGroupsForUserResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembersInAliasRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembersInGroupRequest;
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
import com.rapid7.client.dcerpc.mssamr.objects.AliasInfo;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
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
import com.rapid7.client.dcerpc.mssamr.objects.UserInfo;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_MORE_ENTRIES;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_NO_MORE_ITEMS;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;

public class SecurityAccountManagerService extends Service {
    private final static int MAXIMUM_ALLOWED = 33554432;

    /**
     * Create a new {@link SecurityAccountManagerService} backed by the provided {@link RPCTransport}
     * which should be bound to the lsarpc interface.
     * @param transport The {@link RPCTransport} bound to the samr interface.
     */
    public SecurityAccountManagerService(final RPCTransport transport) {
        super(transport);
    }

    /**
     * Open a new {@link ServerHandle}.
     * @return A new {@link ServerHandle} for the given server identified by serverName.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public ServerHandle openServer() throws IOException {
        return openServer("");
    }

    /**
     * Open a new {@link ServerHandle} using the provided NETBIOS name of the server.
     * @param serverName NETBIOS name of the server. Most targets ignore this value so an empty string is suggested.
     * @return A new {@link ServerHandle} for the given server identified by serverName.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public ServerHandle openServer(String serverName) throws IOException {
        final SamrConnect2Request request =
                new SamrConnect2Request(Strings.nullToEmpty(serverName), MAXIMUM_ALLOWED);
        return parseServerHandle(callExpectSuccess(request, "SamrConnect2"));
    }

    /**
     * Open a new {@link DomainHandle} against a valid domain identified by the provided {@link SID}.
     * @param serverHandle A valid server handle obtained from {@link #openServer()}
     * @param domainId A valid {@link SID} which identifies the domain.
     *                 Use {@link #getDomainsForServer(ServerHandle)} if you need to discover them.
     * @return A new {@link DomainHandle} for the resolved domain.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public DomainHandle openDomain(final ServerHandle serverHandle, final SID domainId) throws IOException {
        final SamrOpenDomainRequest request =
                new SamrOpenDomainRequest(parseHandle(serverHandle), MAXIMUM_ALLOWED, parseSID(domainId));
        return parseDomainHandle(callExpectSuccess(request, "SamrOpenDomain"));
    }

    /**
     * Open a new {@link GroupHandle} against a valid group identified by both the
     * provided {@link DomainHandle} and groupRID.
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param groupRID A relative identifier for the group.
     * @return A new {@link GroupHandle} for the resolved group.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public GroupHandle openGroup(final DomainHandle domainHandle, final long groupRID) throws IOException {
        final SamrOpenGroupRequest request =
                new SamrOpenGroupRequest(parseHandle(domainHandle), MAXIMUM_ALLOWED, groupRID);
        return parseGroupHandle(callExpectSuccess(request, "SamrOpenGroupRequest"));
    }

    /**
     * Open a new {@link UserHandle} against a valid user identified by both the
     * provided {@link DomainHandle} and userRID.
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param userRID A relative identifier for the group.
     * @return A new {@link UserHandle} for the resolved user.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public UserHandle openUser(final DomainHandle domainHandle, final long userRID) throws IOException {
        final SamrOpenUserRequest request =
                new SamrOpenUserRequest(parseHandle(domainHandle), userRID);
        return parseUserHandle(callExpectSuccess(request, "SamrOpenUserRequest"));
    }

    /**
     * Open a new {@link AliasHandle} against a valid user identified by both the
     * provided {@link DomainHandle} and aliasRID.
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param aliasRID A relative identifier for the group.
     * @return A new {@link AliasHandle} for the resolved alias.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public AliasHandle openAlias(final DomainHandle domainHandle, final long aliasRID) throws IOException {
        // AccessMask(0x0002000C)
        // SAMR Alias specific rights: 0x0000000c
        // - SAMR_ALIAS_ACCESS_LOOKUP_INFO is SET(8)
        // - SAMR_ALIAS_ACCESS_GET_MEMBERS is SET(4)
        final SamrOpenAliasRequest request =
                new SamrOpenAliasRequest(parseHandle(domainHandle), 0x0002000C, aliasRID);
        return parseAliasHandle(callExpectSuccess(request, "SamrOpenAlias"));
    }

    /**
     * Close a valid {@link ContextHandle}: {@link ServerHandle}, {@link GroupHandle}, {@link UserHandle}, {@link AliasHandle}.
     * If the handle has already been closed or is otherwise invalid it will be ignored.
     * @param handle The handle to close.
     * @return True if the provided handle was closed successfully.
     * False if the handle was invalid (i.e. already closed). Throws {@link RPCException} otherwise.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns a response value other than ERROR_SUCCESS or STATUS_INVALID_HANDLE.
     */
    public boolean closeHandle(final ContextHandle handle) throws IOException {
        final SamrCloseHandleRequest request =
                new SamrCloseHandleRequest(parseHandle(handle));
        final HandleResponse response = call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue())) {
            return true;
        } else if (SystemErrorCode.STATUS_INVALID_HANDLE.is(response.getReturnValue())) {
            return false;
        }
        throw new RPCException("SamrCloseHandle", response.getReturnValue());
    }

    /**
     * Return a list of all domains hosted by the server side of this protocol.
     * @param serverHandle A valid server handle obtained from {@link #openServer()}
     * @return A list of all domains hosted by the server side of this protocol.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public List<DomainInfo> getDomainsForServer(final ServerHandle serverHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getDomainsForServer(serverHandle, bufferSize);
    }

    /**
     * Return a list of all domains hosted by the server side of this protocol.
     * @param serverHandle A valid server handle obtained from {@link #openServer()}
     * @param bufferSize Maximum number of entries in each response. Since this method returns a list,
     *                   0xFFFF is suggested.
     * @return A list of all domains hosted by the server side of this protocol.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public List<DomainInfo> getDomainsForServer(final ServerHandle serverHandle, final int bufferSize)
            throws IOException {
        final List<DomainInfo> domains = new ArrayList<>();
        final byte[] serverHandleBytes = parseHandle(serverHandle);
        return enumerate(domains, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateDomainsInSamServer";
            }
            @Override
            public SamrEnumerateResponse request(final int enumContext) throws IOException {
                final SamrEnumerateDomainsInSamServerRequest request =
                        new SamrEnumerateDomainsInSamServerRequest(serverHandleBytes, enumContext, bufferSize);
                return call(request);
            }
        });
    }

    /**
     * Return a list of all aliases in the given domain.
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @return A list of all aliases in the given domain.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public List<AliasInfo> getAliasesForDomain(final DomainHandle domainHandle)
            throws IOException {
        final int bufferSize = 0xffff;
        return getAliasesForDomain(domainHandle, bufferSize);
    }

    /**
     * Return a list of all aliases in the given domain.
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param bufferSize Maximum number of entries in each response. Since this method returns a list,
     *                   0xFFFF is suggested.
     * @return A list of all aliases in the given domain.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public List<AliasInfo> getAliasesForDomain(final DomainHandle domainHandle, final int bufferSize)
            throws IOException {
        final List<AliasInfo> aliases = new ArrayList<>();
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        return enumerate(aliases, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateAliasesInDomain";
            }
            @Override
            public SamrEnumerateResponse request(final int enumContext) throws IOException {
                final SamrEnumerateAliasesInDomainRequest request =
                        new SamrEnumerateAliasesInDomainRequest(domainHandleBytes, enumContext, bufferSize);
                return call(request);
            }
        });
    }

    /**
     * Retrieve information about a user using info level 21 (UserAllInformation).
     * @param userHandle A valid user handle obtained from {@link #openUser(DomainHandle, long)}
     * @return User information using level 21 (UserAllInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SAMPRUserAllInformation getUserAllInformation(final UserHandle userHandle) throws IOException {
        final SamrQueryInformationUserRequest.UserAllInformation request =
                new SamrQueryInformationUserRequest.UserAllInformation(parseHandle(userHandle));
        return callExpectSuccess(request, "SamrQueryInformationUser[21]").getUserInformation();
    }

    /**
     * Retrieve information about a group using info level 1 (GroupGeneralInformation).
     * @param groupHandle A valid user handle obtained from {@link #openGroup(DomainHandle, long)}
     * @return Group information using level 1 (GroupGeneralInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SAMPRGroupGeneralInformation getGroupGeneralInformation(final GroupHandle groupHandle) throws IOException {
        final SamrQueryInformationGroupRequest.GroupGeneralInformation request =
                new SamrQueryInformationGroupRequest.GroupGeneralInformation(parseHandle(groupHandle));
        return callExpectSuccess(request, "SamrQueryInformationGroup[1]").getGroupInformation();
    }

    /**
     * Retrieve information about an alias using info level 1 (AliasGeneralInformation).
     * @param aliasHandle A valid user handle obtained from {@link #openAlias(DomainHandle, long)}
     * @return Alias information using level 1 (AliasGeneralInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SAMPRAliasGeneralInformation getAliasGeneralInformation(final AliasHandle aliasHandle) throws IOException {
        final SamrQueryInformationAliasRequest.AliasGeneralInformation request =
                new SamrQueryInformationAliasRequest.AliasGeneralInformation(parseHandle(aliasHandle));
        return callExpectSuccess(request, "SamrQueryInformationAlias[1]").getAliasInformation();
    }

    /**
     * Retrieve all members of the provided alias represented as {@link SID}s.
     * @param aliasHandle A valid user handle obtained from {@link #openAlias(DomainHandle, long)}
     * @return All members of the provided alias represented as {@link SID}s.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SID[] getMembersInAlias(final AliasHandle aliasHandle) throws IOException {
        final SamrGetMembersInAliasRequest request = new SamrGetMembersInAliasRequest(parseHandle(aliasHandle));
        final RPCSID[] rpcsids = callExpectSuccess(request, "SamrGetMembersInAlias").getSids();
        return parseRPCSIDs(rpcsids);
    }

    /**
     * Retrieve information about a domain using info level 1 (DomainPasswordInformation).
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @return Domain information using level 1 (DomainPasswordInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SAMPRDomainPasswordInfo getDomainPasswordInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomainRequest.DomainPasswordInformation request =
                new SamrQueryInformationDomainRequest.DomainPasswordInformation(parseHandle(domainHandle));
        return callExpectSuccess(request, "SamrQueryInformationDomain[1]").getDomainInformation();
    }

    /**
     * Retrieve information about a domain using info level 3 (DomainLogOffInformation).
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @return Domain information using level 3 (DomainLogOffInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SAMPRDomainLogOffInfo getDomainLogOffInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomainRequest.DomainLogOffInformation request =
                new SamrQueryInformationDomainRequest.DomainLogOffInformation(parseHandle(domainHandle));
        return callExpectSuccess(request, "SamrQueryInformationDomain[3]").getDomainInformation();
    }

    /**
     * Retrieve information about a domain using info level 12 (DomainLockoutInformation).
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @return Domain information using level 12 (DomainLockoutInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SAMPRDomainLockoutInfo getDomainLockoutInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomain2Request.DomainLockoutInformation request =
                new SamrQueryInformationDomain2Request.DomainLockoutInformation(parseHandle(domainHandle));
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
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        return enumerate(groups, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateGroupsInDomain";
            }
            @Override
            public SamrEnumerateResponse request(int enumContext) throws IOException {
                final SamrEnumerateGroupsInDomainRequest request = new SamrEnumerateGroupsInDomainRequest(
                    domainHandleBytes, enumContext, bufferSize);
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
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        return enumerate(users, new EnumerationCallback() {
            @Override
            public String getName() {
                return "SamrEnumerateUsersInDomain";
            }
            @Override
            public SamrEnumerateResponse request(int enumContext) throws IOException {
                final SamrEnumerateUsersInDomainRequest request = new SamrEnumerateUsersInDomainRequest(
                        domainHandleBytes, enumContext, userAccountControl, bufferSize);
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

    public List<SAMPRDomainDisplayGroup> getDomainGroupInformationForDomain(final DomainHandle domainHandle,
            final int entryCount,
            final int maxLength) throws IOException {
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        final List<SAMPRDomainDisplayGroup> groups = new ArrayList<>();
        int enumContext = 0;
        int totalReturnedBytes = 0;
        while (true) {
            final SamrQueryDisplayInformation2Request.DomainDisplayGroup request =
                    new SamrQueryDisplayInformation2Request.DomainDisplayGroup(
                            domainHandleBytes, enumContext, entryCount, maxLength);
            final SamrQueryDisplayInformation2Response<SAMPRDomainDisplayGroupBuffer> response = call(request);
            List<SAMPRDomainDisplayGroup> buffer = response.getDisplayInformation().getEntries();
            if (buffer == null)
                buffer = new ArrayList<>();
            enumContext += buffer.size();
            totalReturnedBytes += response.getTotalReturned();
            final int returnCode = response.getReturnValue();
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
        final SamrQuerySecurityObjectRequest request =
                new SamrQuerySecurityObjectRequest(parseHandle(objectHandle), securityInformationValue);
        return parseSecurityDescriptor(callExpectSuccess(request, "SamrQuerySecurityObject").getSecurityDescriptor());
    }

    SecurityDescriptor parseSecurityDescriptor(final SAMPRSRSecurityDescriptor securityDescriptor) throws IOException {
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

    public SID getSIDForDomain(final ServerHandle serverHandle, final String domainName) throws IOException {
        final SamrLookupDomainInSamServerRequest request =
                new SamrLookupDomainInSamServerRequest(
                        parseHandle(serverHandle), RPCUnicodeString.NonNullTerminated.of(domainName));
        final RPCSID rpcsid = callExpectSuccess(request, "SamrLookupDomainInSamServer").getDomainId();
        return parseRPCSID(rpcsid);
    }

    public SamrLookupNamesInDomainResponse getNamesInDomain(final DomainHandle domainHandle, String ... names)
            throws IOException {
        if (names == null)
            names = new String[0];
        final RPCUnicodeString.NonNullTerminated[] inNames = new RPCUnicodeString.NonNullTerminated[names.length];
        for (int i = 0; i < names.length; i++) {
            inNames[i] = RPCUnicodeString.NonNullTerminated.of(names[i]);
        }
        final SamrLookupNamesInDomainRequest request =
                new SamrLookupNamesInDomainRequest(parseHandle(domainHandle), inNames);
        return callExpectSuccess(request, "SamrLookupNamesInDomain");
    }

    /**
     * Gets a list of {@link Membership} information for groups containing the provided user handle.
     *
     * @param userHandle User handle. Must not be {@code null}.
     */
    public List<Membership> getGroupsForUser(final UserHandle userHandle) throws IOException {
        final SamrGetGroupsForUserRequest request = new SamrGetGroupsForUserRequest(parseHandle(userHandle));
        final SamrGetGroupsForUserResponse response = callExpectSuccess(request, "GetGroupsForUser");
        final List<Membership> groups = new ArrayList<>();
        final List<GroupMembership> returnedGroups = response.getGroups();
        for (GroupMembership returnedGroup : returnedGroups) {
            groups.add(new Membership(returnedGroup.getRelativeID(), returnedGroup.getAttributes()));
        }
        return groups;
    }

    /**
     * Gets a list of {@link Membership} information for the members of the provided group handle.
     *
     * @param groupHandle Group handle. Must not be {@code null}.
     */
    public List<Membership> getMembersForGroup(GroupHandle groupHandle)
            throws IOException {
        final SamrGetMembersInGroupRequest request = new SamrGetMembersInGroupRequest(parseHandle(groupHandle));
        final List<GroupMembership> returnedGroups = callExpectSuccess(request, "GetMembersForGroup").getList();
        final List<Membership> groups = new ArrayList<>();
        for (GroupMembership returnedGroup : returnedGroups) {
            groups.add(new Membership(returnedGroup.getRelativeID(), returnedGroup.getAttributes()));
        }
        return groups;
    }

    /**
     * Gets the union of all aliases that a given set of SIDs is a member of.
     *
     * @param domainHandle The domain handle.
     * @param sids A list of SIDs.
     * @return An array of alias relativeIDs to the provided SID.
     * @throws IOException
     */
    public Integer[] getAliasMembership(final DomainHandle domainHandle, SID... sids) throws IOException {
        final SamrGetAliasMembershipRequest request =
                new SamrGetAliasMembershipRequest(parseHandle(domainHandle), parseSIDs(sids));
        final SamrGetAliasMembershipResponse response = callExpectSuccess(request, "GetAliasMembership");
        return response.getList();
    }

    /**
     * Helper method for calling enumeration requests and enumerating through the buffers for
     * {@link SamrEnumerateResponse}.
     */
    private <T> List<T> enumerate(final List<T> list, final EnumerationCallback callback)
            throws IOException {
        for (int enumContext = 0;;) {
            final SamrEnumerateResponse response = callback.request(enumContext);
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
        SamrEnumerateResponse request(int enumContext) throws IOException;
    }

    private ServerHandle parseServerHandle(final HandleResponse response) {
        return new ServerHandle(response.getHandle());
    }

    private DomainHandle parseDomainHandle(final HandleResponse response) {
        return new DomainHandle(response.getHandle());
    }

    private GroupHandle parseGroupHandle(final HandleResponse response) {
        return new GroupHandle(response.getHandle());
    }

    private UserHandle parseUserHandle(final HandleResponse response) {
        return new UserHandle(response.getHandle());
    }

    private AliasHandle parseAliasHandle(final HandleResponse response) {
        return new AliasHandle(response.getHandle());
    }
}
