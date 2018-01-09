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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245752.aspx">SamrOpenUser</a>
 * <blockquote><pre>The SamrOpenUser method obtains a handle to a user, given a RID.
 *
 *      long SamrOpenUser(
 *          [in] SAMPR_HANDLE DomainHandle,
 *          [in] unsigned long DesiredAccess,
 *          [in] unsigned long UserId,
 *          [out] SAMPR_HANDLE* UserHandle
 *      );
 *
 *  DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 *  DesiredAccess: An ACCESS_MASK that indicates the requested access for the returned handle. See section 2.2.1.7 for a list of user access values.
 *  UserId: A RID of a user account.
 *  UserHandle: An RPC context handle, as specified in section 2.2.3.2.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrOpenUserRequest extends RequestCall<HandleResponse> {
    public final static short OP_NUM = 34;

    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
    private final byte[] domainHandle;
    // <NDR: unsigned long> [in] unsigned long DesiredAccess
    private final int desiredAccess;
    // <NDR: unsigned long> [in] unsigned long UserId
    private final long userId;

    public SamrOpenUserRequest(final byte[] domainHandle, final int desiredAccess, final long userId) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
        this.desiredAccess = desiredAccess;
        this.userId = userId;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(this.domainHandle);
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        packetOut.writeInt(desiredAccess);
        // <NDR: unsigned long> [in] unsigned long UserId
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.userId);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
