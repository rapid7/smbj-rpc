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
import java.util.List;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssamr.dto.AliasGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.dto.DomainDisplayGroup;
import com.rapid7.client.dcerpc.mssamr.dto.DomainHandle;
import com.rapid7.client.dcerpc.mssamr.dto.DomainLockoutInfo;
import com.rapid7.client.dcerpc.mssamr.dto.DomainLogoffInfo;
import com.rapid7.client.dcerpc.mssamr.dto.DomainPasswordInfo;
import com.rapid7.client.dcerpc.mssamr.dto.GroupGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.dto.LogonHours;
import com.rapid7.client.dcerpc.mssamr.dto.Membership;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithAttributes;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithName;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithNameAndUse;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithUse;
import com.rapid7.client.dcerpc.mssamr.dto.SecurityDescriptor;
import com.rapid7.client.dcerpc.mssamr.dto.ServerHandle;
import com.rapid7.client.dcerpc.mssamr.dto.UserAllInformation;
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
import com.rapid7.client.dcerpc.mssamr.messages.SamrGetMembersInGroupResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrLookupDomainInSamServerRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrLookupIdsInDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrLookupIdsInDomainResponse;
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
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogoffInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRLogonHours;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRPSIDArray;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRRIDEnumeration;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRSRSecurityDescriptor;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRUserAllInformation;
import com.rapid7.client.dcerpc.mssamr.objects.UserInfo;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString.NonNullTerminated;
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
                new SamrConnect2Request(parseWCharNT(serverName), MAXIMUM_ALLOWED);
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
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()))
            return true;
        else if (SystemErrorCode.STATUS_INVALID_HANDLE.is(response.getReturnValue()))
            return false;
        throw new RPCException("SamrCloseHandle", response.getReturnValue());
    }

    /**
     * Gets the domain RID and name pairs of domains hosted by the provided domain.
     * Max buffer size will be used.
     *
     * @param serverHandle A valid server handle obtained from {@link #openServer()}
     * @return A list of all domains hosted by the server side of this protocol.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithName[] getDomainsForServer(final ServerHandle serverHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getDomainsForServer(serverHandle, bufferSize);
    }

    /**
     * Gets the domain RID and name pairs of domains hosted by the provided domain.
     * Multiple request may be sent based on the entries read and buffer size.
     *
     * @param serverHandle A valid server handle obtained from {@link #openServer()}
     * @param bufferSize Maximum number of entries in each response. Since this method returns a list,
     *                   0xFFFF is suggested.
     * @return A list of all domains hosted by the server side of this protocol.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithName[] getDomainsForServer(final ServerHandle serverHandle, final int bufferSize)
            throws IOException {
        final List<DomainInfo> domainInfos = new ArrayList<>();
        final byte[] serverHandleBytes = parseHandle(serverHandle);
        enumerate(domainInfos, new EnumerationCallback() {
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
        return parseSAMPRRIDEnumerations(domainInfos);
    }

    /**
     * Gets the alias RID and name pairs of all aliases in the provided domain.
     * Max buffer size will be used.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @return A list of all aliases in the given domain.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithName[] getAliasesForDomain(final DomainHandle domainHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getAliasesForDomain(domainHandle, bufferSize);
    }

    /**
     * Gets the alias RID and name pairs of all aliases in the provided domain.
     * Multiple request may be sent based on the entries read and buffer size.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param bufferSize Maximum number of entries in each response. Since this method returns a list,
     *                   0xFFFF is suggested.
     * @return A list of all aliases in the given domain.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithName[] getAliasesForDomain(final DomainHandle domainHandle, final int bufferSize)
            throws IOException {
        final List<AliasInfo> aliases = new ArrayList<>();
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        enumerate(aliases, new EnumerationCallback() {
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
        return parseSAMPRRIDEnumerations(aliases);
    }

    /**
     * Gets the group RID and name pairs for the provided domain.
     * Max buffer size will be used.
     *
     * @param domainHandle The domain handle.
     * @return The enumerated groups.
     * @throws IOException On issue with communication or marshalling.
     */
    public MembershipWithName[] getGroupsForDomain(final DomainHandle domainHandle) throws IOException {
        final int bufferSize = 0xffff;
        return getGroupsForDomain(domainHandle, bufferSize);
    }

    /**
     * Gets the group RID and name pairs for the provided domain.
     * Multiple request may be sent based on the entries read and buffer size.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param bufferSize Maximum number of entries in each response. Since this method returns a list,
     *                   0xFFFF is suggested.
     * @return The enumerated groups.
     * @throws IOException On issue with communication or marshalling.
     */
    public MembershipWithName[] getGroupsForDomain(final DomainHandle domainHandle, final int bufferSize)
            throws IOException {
        final List<GroupInfo> groupInfos = new ArrayList<>();
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        enumerate(groupInfos, new EnumerationCallback() {
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
        return parseSAMPRRIDEnumerations(groupInfos);
    }

    /**
     * Gets the user RID and name pairs for the provided domain.
     * Max buffer size will be used.
     * All users will be returned (UserAccountControl=0)
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public MembershipWithName[] getUsersForDomain(final DomainHandle domainHandle)
            throws IOException {
        return getUsersForDomain(domainHandle, 0);
    }

    /**
     * Gets the user RID and name pairs for the provided domain.
     * Max buffer size will be used.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param userAccountControl The UserAccountControl flags that filters the returned users.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public MembershipWithName[] getUsersForDomain(final DomainHandle domainHandle, final int userAccountControl)
            throws IOException {
        final int bufferSize = 0xffff;
        return getUsersForDomain(domainHandle, userAccountControl, bufferSize);
    }

    /**
     * Gets the user RID and name pairs for the provided domain.
     * Multiple request may be sent based on the entries read and buffer size.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}.
     * @param userAccountControl The UserAccountControl flags that filters the returned users.
     * @param bufferSize Maximum number of entries in each response. Since this method returns a list,
     *                   0xFFFF is suggested.
     * @return The enumerated users.
     * @throws IOException On issue with communication or marshalling.
     */
    public MembershipWithName[] getUsersForDomain(final DomainHandle domainHandle, final int userAccountControl,
            final int bufferSize) throws IOException {
        final List<UserInfo> userInfos = new ArrayList<>();
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        enumerate(userInfos, new EnumerationCallback() {
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
        return parseSAMPRRIDEnumerations(userInfos);
    }

    /**
     * Retrieve information about a user using info level 21 (UserAllInformation).
     *
     * @param userHandle A valid user handle obtained from {@link #openUser(DomainHandle, long)}
     * @return User information using level 21 (UserAllInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public UserAllInformation getUserAllInformation(final UserHandle userHandle) throws IOException {
        final SamrQueryInformationUserRequest.UserAllInformation request =
                new SamrQueryInformationUserRequest.UserAllInformation(parseHandle(userHandle));
        final SAMPRUserAllInformation userInformation =
                callExpectSuccess(request, "SamrQueryInformationUser[21]").getUserInformation();
        if (userInformation == null)
            return null;
        try {
            return new UserAllInformation(
                    userInformation.getLastLogon(),
                    userInformation.getLastLogoff(),
                    userInformation.getPasswordLastSet(),
                    userInformation.getAccountExpires(),
                    userInformation.getPasswordCanChange(),
                    userInformation.getPasswordMustChange(),
                    parseRPCUnicodeString(userInformation.getUserName()),
                    parseRPCUnicodeString(userInformation.getFullName()),
                    parseRPCUnicodeString(userInformation.getHomeDirectory()),
                    parseRPCUnicodeString(userInformation.getHomeDirectoryDrive()),
                    parseRPCUnicodeString(userInformation.getScriptPath()),
                    parseRPCUnicodeString(userInformation.getProfilePath()),
                    parseRPCUnicodeString(userInformation.getAdminComment()),
                    parseRPCUnicodeString(userInformation.getWorkStations()),
                    parseRPCUnicodeString(userInformation.getUserComment()),
                    parseRPCUnicodeString(userInformation.getParameters()),
                    userInformation.getLmOwfPassword().getBuffer(),
                    userInformation.getNtOwfPassword().getBuffer(),
                    userInformation.getPrivateData().getValue(),
                    parseSAMPRSRSecurityDescriptor(userInformation.getSecurityDescriptor()),
                    userInformation.getUserId(),
                    userInformation.getPrimaryGroupId(),
                    userInformation.getUserAccountControl(),
                    userInformation.getWhichFields(),
                    parseSAMPRLogonHours(userInformation.getLogonHours()),
                    userInformation.getBadPasswordCount(),
                    userInformation.getLogonCount(),
                    userInformation.getCountryCode(),
                    userInformation.getCodePage(),
                    userInformation.getLmPasswordPresent() != 0,
                    userInformation.getNtPasswordPresent() != 0,
                    userInformation.getPasswordExpired() != 0,
                    userInformation.getPrivateDataSensitive() != 0
            );
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new UnmarshalException(e.getMessage(), e);
        }
    }

    /**
     * Retrieve information about a group using info level 1 (GroupGeneralInformation).
     * @param groupHandle A valid user handle obtained from {@link #openGroup(DomainHandle, long)}
     * @return Group information using level 1 (GroupGeneralInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public GroupGeneralInformation getGroupGeneralInformation(final GroupHandle groupHandle) throws IOException {
        final SamrQueryInformationGroupRequest.GroupGeneralInformation request =
                new SamrQueryInformationGroupRequest.GroupGeneralInformation(parseHandle(groupHandle));
        final SAMPRGroupGeneralInformation groupGeneralInformation =
                callExpectSuccess(request, "SamrQueryInformationGroup[1]").getGroupInformation();
        if (groupGeneralInformation == null)
            return null;
        return new GroupGeneralInformation(
                parseRPCUnicodeString(groupGeneralInformation.getName()),
                groupGeneralInformation.getAttributes(),
                groupGeneralInformation.getMemberCount(),
                parseRPCUnicodeString(groupGeneralInformation.getAdminComment()));
    }

    /**
     * Retrieve information about an alias using info level 1 (AliasGeneralInformation).
     * @param aliasHandle A valid user handle obtained from {@link #openAlias(DomainHandle, long)}
     * @return Alias information using level 1 (AliasGeneralInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public AliasGeneralInformation getAliasGeneralInformation(final AliasHandle aliasHandle) throws IOException {
        final SamrQueryInformationAliasRequest.AliasGeneralInformation request =
                new SamrQueryInformationAliasRequest.AliasGeneralInformation(parseHandle(aliasHandle));
        final SAMPRAliasGeneralInformation aliasGeneralInformation =
                callExpectSuccess(request, "SamrQueryInformationAlias[1]").getAliasInformation();
        if (aliasGeneralInformation == null)
            return null;
        return new AliasGeneralInformation(
                parseRPCUnicodeString(aliasGeneralInformation.getName()),
                aliasGeneralInformation.getMemberCount(),
                parseRPCUnicodeString(aliasGeneralInformation.getAdminComment()));
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
    public DomainPasswordInfo getDomainPasswordInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomainRequest.DomainPasswordInformation request =
                new SamrQueryInformationDomainRequest.DomainPasswordInformation(parseHandle(domainHandle));
        final SAMPRDomainPasswordInfo passwordInfo =
                callExpectSuccess(request, "SamrQueryInformationDomain[1]").getDomainInformation();
        if (passwordInfo == null)
            return null;
        return new DomainPasswordInfo(
                passwordInfo.getMinPasswordLength(),
                passwordInfo.getPasswordHistoryLength(),
                passwordInfo.getPasswordProperties(),
                passwordInfo.getMaxPasswordAge(),
                passwordInfo.getMinPasswordAge());
    }

    /**
     * Retrieve information about a domain using info level 3 (DomainLogOffInformation).
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @return Domain information using level 3 (DomainLogOffInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public DomainLogoffInfo getDomainLogOffInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomainRequest.DomainLogOffInformation request =
                new SamrQueryInformationDomainRequest.DomainLogOffInformation(parseHandle(domainHandle));
        final SAMPRDomainLogoffInfo samprDomainLogoffInfo =
                callExpectSuccess(request, "SamrQueryInformationDomain[3]").getDomainInformation();
        if (samprDomainLogoffInfo == null)
            return null;
        return new DomainLogoffInfo(samprDomainLogoffInfo.getForceLogoff());
    }

    /**
     * Retrieve information about a domain using info level 12 (DomainLockoutInformation).
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @return Domain information using level 12 (DomainLockoutInformation).
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public DomainLockoutInfo getDomainLockoutInfo(final DomainHandle domainHandle) throws IOException {
        final SamrQueryInformationDomain2Request.DomainLockoutInformation request =
                new SamrQueryInformationDomain2Request.DomainLockoutInformation(parseHandle(domainHandle));
        final SAMPRDomainLockoutInfo samprDomainLockoutInfo =
                callExpectSuccess(request, "SamrQueryInformationDomain2[12]").getDomainInformation();
        if (samprDomainLockoutInfo == null)
            return null;
        return new DomainLockoutInfo(samprDomainLockoutInfo.getLockoutDuration(),
                samprDomainLockoutInfo.getLockoutObservationWindow(), samprDomainLockoutInfo.getLockoutThreshold());
    }

    /**
     * Retrieve domain display information ({@link DomainDisplayGroup}) for all groups in the given domain.
     * Max buffer size will be used.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @return All groups in the given domain represented by {@link DomainDisplayGroup}.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public DomainDisplayGroup[] getDomainGroupInformationForDomain(final DomainHandle domainHandle)
            throws IOException {
        // no limit.
        final int entryCount = 0xffffffff;
        final int maxLength = 0xffff;
        return getDomainGroupInformationForDomain(domainHandle, entryCount, maxLength);
    }

    /**
     * Retrieve domain display information ({@link DomainDisplayGroup}) for all groups in the given domain.
     * Max buffer size will be used.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @param entryCount The number of accounts requested.
     * @param maxLength The requested maximum number of bytes to return in this request;
     *                  this value overrides entryCount if this value is reached before entryCount is reached.
     * @return All groups in the given domain represented by {@link DomainDisplayGroup}.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public DomainDisplayGroup[] getDomainGroupInformationForDomain(final DomainHandle domainHandle,
            final int entryCount, final int maxLength) throws IOException {
        final byte[] domainHandleBytes = parseHandle(domainHandle);
        final List<SAMPRDomainDisplayGroup> displayGroups = new ArrayList<>();
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
                displayGroups.addAll(buffer);
            } else if (ERROR_NO_MORE_ITEMS.is(returnCode) || ERROR_SUCCESS.is(returnCode)
                    || totalReturnedBytes == response.getTotalAvailable()) {
                displayGroups.addAll(buffer);
                break;
            } else {
                throw new RPCException("QueryDisplayInformation2", returnCode);
            }
        }
        final DomainDisplayGroup[] ret = new DomainDisplayGroup[displayGroups.size()];
        int i = 0;
        for (SAMPRDomainDisplayGroup displayGroup : displayGroups) {
            ret[i++] = new DomainDisplayGroup(displayGroup.getRid(),
                    parseRPCUnicodeString(displayGroup.getAccountName()),
                    parseRPCUnicodeString(displayGroup.getDescription()),
                    displayGroup.getAttributes());
        }
        return ret;
    }

    /**
     * Return the access control {@link SecurityDescriptor} for the given object.
     * This method requests all security information, which will include owner, group, SACL, and DACL security information.
     *
     * @param objectHandle The object to query. Typically one of:
     * {@link DomainHandle}, {@link UserHandle}, {@link GroupHandle}, {@link AliasHandle}.
     * @return The access control security descriptor for the given object.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public byte[] getSecurityObject(final ContextHandle objectHandle) throws IOException {
        return getSecurityObject(objectHandle, true, true, true, true);
    }

    /**
     * Return the access control {@link SecurityDescriptor} for the given object.
     * This method requests all security information, which will include owner, group, SACL, and DACL security information.
     *
     * @param objectHandle The object to query. Typically one of:
     * {@link DomainHandle}, {@link UserHandle}, {@link GroupHandle}, {@link AliasHandle}.
     * @param queryOwner Retrieve and populate the owner SID.
     * @param queryGroup Retrieve and populate the group SID.
     * @param queryDACL Retrieve and populate the DACL.
     * @param querySACL Retrieve and populate the SACL.
     * @return The access control security descriptor for the given object.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public byte[] getSecurityObject(final ContextHandle objectHandle,
            final boolean queryOwner, final boolean queryGroup,
            final boolean queryDACL, final boolean querySACL) throws IOException {
        int securityInformation = 0;
        if (queryOwner)
            securityInformation |= 0x01;
        if (queryGroup)
            securityInformation |= 0x02;
        if (queryDACL)
            securityInformation |= 0x04;
        if (querySACL)
            securityInformation |= 0x08;
        final SamrQuerySecurityObjectRequest request =
                new SamrQuerySecurityObjectRequest(parseHandle(objectHandle), securityInformation);
        return parseSAMPRSRSecurityDescriptor(callExpectSuccess(request, "SamrQuerySecurityObject").getSecurityDescriptor());
    }

    /**
     * Gets the SID of a given domain. An exception is thrown if it is not found.
     *
     * @param serverHandle A valid server handle obtained from {@link #openServer()}
     * @param domainName The name of the domain to get the {@link SID} for.
     * @return The SID for the given domain.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public SID getSIDForDomain(final ServerHandle serverHandle, final String domainName) throws IOException {
        final SamrLookupDomainInSamServerRequest request =
                new SamrLookupDomainInSamServerRequest(
                        parseHandle(serverHandle), RPCUnicodeString.NonNullTerminated.of(domainName));
        final RPCSID rpcsid = callExpectSuccess(request, "SamrLookupDomainInSamServer").getDomainId();
        return parseRPCSID(rpcsid);
    }

    /**
     * Gets an array of {@link MembershipWithUse} information for users/groups matching the given names in the provided
     * domain.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @param names A list of user/group names.
     * @return An array of user/group relativeIDs and their use; each entry corresponds 1-1 with the given name list.
     * If an entry is null, no result was found for that name.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithUse[] lookupNamesInDomain(final DomainHandle domainHandle, String ... names) throws IOException {
        final SamrLookupNamesInDomainRequest request =
                new SamrLookupNamesInDomainRequest(parseHandle(domainHandle), parseNonNullTerminatedStrings(names));
        final SamrLookupNamesInDomainResponse response =
                callExpect(request, "SamrLookupNamesInDomain",
                        SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_SOME_NOT_MAPPED, SystemErrorCode.STATUS_NONE_MAPPED);
        long[] relativeIds = response.getRelativeIds().getArray();
        // This is a pointer, it can be null
        if (relativeIds == null)
            relativeIds = new long[0];
        long[] uses = response.getUse().getArray();
        // This is a pointer, it can be null
        if (uses == null)
            uses = new long[0];
        if (relativeIds.length != uses.length) {
            throw new UnmarshalException(String.format(
                    "Expected RelativeIds.length == Uses.length but %d != %d", relativeIds.length, uses.length));
        }
        final MembershipWithUse[] memberships = new MembershipWithUse[relativeIds.length];
        for (int i = 0; i < memberships.length; i++) {
            if (relativeIds[i] == 0)
                memberships[i] = null;
            else
                memberships[i] = new MembershipWithUse(relativeIds[i], (int) uses[i]);
        }
        return memberships;
    }

    /**
     * Gets an array of {@link MembershipWithNameAndUse} information for users/groups matching the given relative
     * IDs in the provided domain.
     *
     * @param domainHandle A valid domain handle obtained from {@link #openDomain(ServerHandle, SID)}
     * @param rids A list of relative ids.
     * @return An array of RID, name, and their use; each entry corresponds 1-1 with the given RID list.
     * If an entry is null, no result was found for that RID.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithNameAndUse[] lookupRIDsInDomain(final DomainHandle domainHandle, long ... rids) throws IOException {
        final SamrLookupIdsInDomainRequest request = new SamrLookupIdsInDomainRequest(parseHandle(domainHandle), rids);
        final SamrLookupIdsInDomainResponse response = callExpect(request, "SamrLookupIdsInDomain",
                SystemErrorCode.ERROR_SUCCESS, SystemErrorCode.STATUS_SOME_NOT_MAPPED, SystemErrorCode.STATUS_NONE_MAPPED);

        final List<NonNullTerminated> names = response.getNames();
        long[] uses = response.getUses().getArray();
        if (uses == null)
            uses = new long[0];
        final MembershipWithNameAndUse[] members = new MembershipWithNameAndUse[names.size()];
        for (int i = 0; i < names.size(); i++) {
            final String name = parseRPCUnicodeString(names.get(i));
            if (name == null)
                members[i] = null;
            else
                members[i] = new MembershipWithNameAndUse(rids[i], name, (int) uses[i]);
        }
        return members;
    }


    /**
     * Gets a list of {@link MembershipWithAttributes} information for groups containing the provided user handle.
     *
     * @param userHandle A valid user handle obtained from {@link #openUser(DomainHandle, long)}
     * @return An array of groups with their relativeIDs and attributes that the user belongs to.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithAttributes[] getGroupsForUser(final UserHandle userHandle) throws IOException {
        final SamrGetGroupsForUserRequest request = new SamrGetGroupsForUserRequest(parseHandle(userHandle));
        final SamrGetGroupsForUserResponse response = callExpectSuccess(request, "GetGroupsForUser");
        return parseGroupMemberships(response.getGroups());
    }

    /**
     * Gets a list of {@link MembershipWithAttributes} information for the members of the provided group handle.
     *
     * @param groupHandle A valid group handle obtained from {@link #openGroup(DomainHandle, long)}
     * @return An array of user/group relativeIDs and their attributes that belong to the given group.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public MembershipWithAttributes[] getMembersForGroup(GroupHandle groupHandle) throws IOException {
        final SamrGetMembersInGroupRequest request = new SamrGetMembersInGroupRequest(parseHandle(groupHandle));
        final SamrGetMembersInGroupResponse response = callExpectSuccess(request, "GetMembersForGroup");
        return parseGroupMemberships(response.getList());
    }

    /**
     * Gets the union of all aliases that a given set of SIDs is a member of.
     *
     * @param domainHandle The domain handle.
     * @param sids A list of SIDs.
     * @return An array of alias relativeIDs to the provided SID.
     * @throws IOException Thrown if either a communication failure is encountered, or the call
     * returns an unsuccessful response.
     */
    public Membership[] getAliasMembership(final DomainHandle domainHandle, SID... sids) throws IOException {
        final RPCSID[] rpcSids = parseSIDs(sids);
        final SAMPRPSIDArray sidArray = new SAMPRPSIDArray(rpcSids);
        final SamrGetAliasMembershipRequest request =
                new SamrGetAliasMembershipRequest(parseHandle(domainHandle), sidArray);
        final SamrGetAliasMembershipResponse response = callExpectSuccess(request, "GetAliasMembership");
        long[] rids = response.getMembership().getArray();
        if (rids == null)
            rids = new long[0];
        final Membership[] ret = new Membership[rids.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new Membership(rids[i]);
        }
        return ret;
    }

    /**
     * Helper method for calling enumeration requests and enumerating through the buffers for
     * {@link SamrEnumerateResponse}.
     */
    private <T> void enumerate(final List<T> list, final EnumerationCallback callback) throws IOException {
        for (int enumContext = 0;;) {
            final SamrEnumerateResponse response = callback.request(enumContext);
            final int returnCode = response.getReturnValue();
            enumContext = response.getResumeHandle();
            //noinspection unchecked
            final List<T> responseList = response.getList();
            if (ERROR_MORE_ENTRIES.is(returnCode) || ERROR_NO_MORE_ITEMS.is(returnCode) || ERROR_SUCCESS.is(returnCode)) {
                if (responseList != null)
                    list.addAll(responseList);
                if (ERROR_MORE_ENTRIES.is(returnCode))
                    continue;
            } else {
                throw new RPCException(response.getClass().getName(), returnCode);
            }
            return;
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

    private LogonHours parseSAMPRLogonHours(final SAMPRLogonHours logonHours) {
        return new LogonHours(logonHours.getLogonHours());
    }

    private byte[] parseSAMPRSRSecurityDescriptor(final SAMPRSRSecurityDescriptor sd) {
        if (sd == null)
            return null;
        return sd.getSecurityDescriptor();
    }

    private MembershipWithName[] parseSAMPRRIDEnumerations(final List<? extends SAMPRRIDEnumeration> list) {
        final MembershipWithName[] memberships = new MembershipWithName[list.size()];
        int i = 0;
        for (final SAMPRRIDEnumeration info : list) {
            if (info == null)
                memberships[i++] = null;
            else
                memberships[i++] = new MembershipWithName(info.getRelativeId(), info.getName().getValue());
        }
        return memberships;
    }

    private MembershipWithAttributes[] parseGroupMemberships(final List<GroupMembership> groupMemberships) {
        if (groupMemberships == null)
            return new MembershipWithAttributes[0];
        final MembershipWithAttributes[] ret = new MembershipWithAttributes[groupMemberships.size()];
        for (int i = 0; i < ret.length; i++) {
            final GroupMembership groupMembership = groupMemberships.get(i);
            if (groupMembership == null)
                ret[i] = null;
            else
                ret[i] = new MembershipWithAttributes(groupMembership.getRelativeID(), groupMembership.getAttributes());
        }
        return ret;
    }
}
