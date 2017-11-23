/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245810.aspx">SamrGetMembersInAlias</a>
 *
 * <blockquote>
 *
 * <pre>
 * The SamrGetMembersInAlias method obtains attributes from an alias object.
 *      long SamrGetMembersInAlias(
 *       [in] SAMPR_HANDLE AliasHandle,
 *       [out] PSAMPR_PSID_ARRAY_OUT Members
 *      );
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 * Members: A structure containing an array of SIDs that represent the membership list of the alias referenced by AliasHandle.
 *
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a
 * different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *      1.The server MUST return an error if AliasHandle.HandleType is not equal to "Alias".
 *      2.AliasHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *      3.On output, Members.Count MUST be equal to the number of values in the member attribute, and Members.Sids MUST have Member.Count number of elements.
 *        Each element MUST contain the objectSid value of the object referenced in the member attribute.
 *
 * </pre>
 *
 * </blockquote>
 *
 */
public class SamrGetMembersInAliasRequest extends RequestCall<SamrGetMembersInAliasResponse> {
    public static final short OP_NUM = 33;

    private final byte[] aliasHandle;

    public SamrGetMembersInAliasRequest(byte[] aliasHandle) {
        super(OP_NUM);
        this.aliasHandle = aliasHandle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(aliasHandle);
    }

    @Override
    public SamrGetMembersInAliasResponse getResponseObject() {
        return new SamrGetMembersInAliasResponse();
    }
}
