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
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245711.aspx">SamrLookupDomainInSamServer</a>
 * <blockquote><pre>The SamrLookupDomainInSamServer method obtains the SID of a domain object, given the object's name.
 *      long SamrLookupDomainInSamServer(
 *          [in] SAMPR_HANDLE ServerHandle,
 *          [in] PRPC_UNICODE_STRING Name,
 *          [out] PRPC_SID* DomainId
 *      );
 *  ServerHandle: An RPC context handle, as specified in section 2.2.3.2, representing a server object.
 *  Name: A UTF-16 encoded string.
 *  DomainId: A SID value of a domain that corresponds to the Name passed in. The match MUST be exact (no wildcard characters are permitted). See message processing later in this section for more details.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrLookupDomainInSamServerRequest extends RequestCall<SamrLookupDomainInSamServerResponse> {
    public static final short OP_NUM = 5;

    // <NDR: fixed array> [in] SAMPR_HANDLE ServerHandle
    private final byte[] serverHandle;
    // <NDR: struct> [in] PRPC_UNICODE_STRING Name,
    private final RPCUnicodeString.NonNullTerminated name;

    public SamrLookupDomainInSamServerRequest(final byte[] serverHandle, RPCUnicodeString.NonNullTerminated name) {
        super(OP_NUM);
        this.serverHandle = serverHandle;
        this.name = name;
    }

    public byte[] getServerHandle() {
        return serverHandle;
    }

    public RPCUnicodeString.NonNullTerminated getName() {
        return name;
    }

    @Override
    public SamrLookupDomainInSamServerResponse getResponseObject() {
        return new SamrLookupDomainInSamServerResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SAMPR_HANDLE ServerHandle
        packetOut.write(getServerHandle());
        // <NDR: struct> [in] PRPC_UNICODE_STRING Name
        packetOut.writeMarshallable(getName());
    }
}
