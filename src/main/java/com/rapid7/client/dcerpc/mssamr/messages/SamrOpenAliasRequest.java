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
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245751.aspx">SamrOpenAlias</a>
 *
 * <blockquote><pre>The SamrOpenAlias method obtains a handle to an alias, given a RID.
 *
 *      long SamrOpenAlias(
 *          [in] SAMPR_HANDLE DomainHandle,
 *          [in] unsigned long DesiredAccess,
 *          [in] unsigned long AliasId,
 *          [out] SAMPR_HANDLE* AliasHandle
 *      );
 *
 *  DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 *  DesiredAccess: An ACCESS_MASK that indicates the requested access for the returned handle. See section 2.2.1.6 for a list of alias access values.
 *  AliasId: A RID of an alias.
 *  AliasHandle: An RPC context handle, as specified in section 2.2.3.2.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrOpenAliasRequest extends RequestCall<SamrOpenAliasResponse> {
    public final static short OP_NUM = 27;

    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
    private final DomainHandle domainHandle;
    // <NDR: unsigned long> [in] unsigned long AliasId
    private final int desiredAccess;
    // <NDR: unsigned long> [in] unsigned long DesiredAccess
    private final int userRid;

    public SamrOpenAliasRequest(DomainHandle domainHandle, int desiredAccess, int userRid) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
        this.userRid = userRid;
        this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
        packetOut.writeMarshallable(this.domainHandle);
        // <NDR: unsigned long> [in] unsigned long AliasId
        // Alignment: 4 - Already aligned
        packetOut.writeInt(desiredAccess);
        // <NDR: unsigned long> [in] unsigned long DesiredAccess
        // Alignment: 4 - Already aligned
        packetOut.writeInt(userRid);
    }

    @Override
    public SamrOpenAliasResponse getResponseObject() {
        return new SamrOpenAliasResponse();
    }

}
