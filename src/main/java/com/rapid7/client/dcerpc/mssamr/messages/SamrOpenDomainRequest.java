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
import com.rapid7.client.dcerpc.objects.RPCSID;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245748.aspx">SamrOpenDomain</a>
 * <blockquote><pre>The SamrOpenDomain method obtains a handle to a domain object, given a SID.
 *      long SamrOpenDomain(
 *          [in] SAMPR_HANDLE ServerHandle,
 *          [in] unsigned long DesiredAccess,
 *          [in] PRPC_SID DomainId,
 *          [out] SAMPR_HANDLE* DomainHandle
 *      );
 *  ServerHandle: An RPC context handle, as specified in section 2.2.3.2, representing a server object.
 *  DesiredAccess: An ACCESS_MASK. See section 2.2.1.4 for a list of domain access values.
 *  DomainId: A SID value of a domain hosted by the server side of this protocol.
 *  DomainHandle: An RPC context handle, as specified in section 2.2.3.2.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrOpenDomainRequest extends RequestCall<HandleResponse> {
    public final static short OP_NUM = 7;

    // <NDR: fixed array> [in] SAMPR_HANDLE ServerHandle
    private final byte[] handle;
    // <NDR: unsigned long> [in] unsigned long DesiredAccess
    private final int desiredAccess;
    // <NDR: struct> [in] PRPC_SID DomainId
    private final RPCSID domainId;

    public SamrOpenDomainRequest(byte[] handle, int desiredAccess, RPCSID domainId) {
        super(OP_NUM);
        this.handle = handle;
        this.desiredAccess = desiredAccess;
        this.domainId = domainId;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SAMPR_HANDLE ServerHandle
        packetOut.write(this.handle);
        // <NDR: unsigned long> [in] unsigned long DesiredAccess
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        packetOut.writeInt(this.desiredAccess);
        // <NDR: struct> [in] PRPC_SID DomainId
        packetOut.writeMarshallable(this.domainId);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
