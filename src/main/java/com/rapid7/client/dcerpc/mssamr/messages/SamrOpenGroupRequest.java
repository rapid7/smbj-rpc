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
 * <a href="https://msdn.microsoft.com/en-us/library/cc245750.aspx">SamrOpenGroup</a>
 * <blockquote><pre>The SamrOpenGroup method obtains a handle to a group, given a RID.
 *
 *      long SamrOpenGroup(
 *          [in] SAMPR_HANDLE DomainHandle,
 *          [in] unsigned long DesiredAccess,
 *          [in] unsigned long GroupId,
 *          [out] SAMPR_HANDLE* GroupHandle
 *      );
 *
 *  DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 *  DesiredAccess: An ACCESS_MASK that indicates the requested access for the returned handle. See section 2.2.1.5 for a list of group access values.
 *  GroupId: A RID of a group.
 *  GroupHandle: An RPC context handle, as specified in section 2.2.3.2.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrOpenGroupRequest extends RequestCall<HandleResponse> {
    public final static short OP_NUM = 19;

    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
    private final byte[] domainHandle;
    // <NDR: unsigned long> [in] unsigned long DesiredAccess
    private final int desiredAccess;
    // <NDR: unsigned long> [in] unsigned long GroupId
    private final long groupId;

    public SamrOpenGroupRequest(byte[] domainHandle, int desiredAccess, long groupId) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
        this.desiredAccess = desiredAccess;
        this.groupId = groupId;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
        packetOut.write(this.domainHandle);
        // <NDR: unsigned long> [in] unsigned long DesiredAccess
        // No align necessary - Always 20 bytes written
        packetOut.writeInt(this.desiredAccess);
        // <NDR: unsigned long> [in] unsigned long GroupId
        // No align necessary - Always 4 bytes written
        packetOut.writeInt(this.groupId);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
