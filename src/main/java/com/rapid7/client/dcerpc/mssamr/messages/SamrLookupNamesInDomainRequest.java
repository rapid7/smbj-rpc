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
 * <a href="https://msdn.microsoft.com/en-us/library/cc245712.aspx">SamrLookupNamesInDomain</a>
 * <blockquote><pre>The SamrLookupNamesInDomain method translates a set of account names into a set of RIDs.
 *
 *      long SamrLookupNamesInDomain(
 *          [in] SAMPR_HANDLE DomainHandle,
 *          [in, range(0,1000)] unsigned long Count,
 *          [in, size_is(1000), length_is(Count)] RPC_UNICODE_STRING Names[*],
 *          [out] PSAMPR_ULONG_ARRAY RelativeIds,
 *          [out] PSAMPR_ULONG_ARRAY Use
 *      );
 *
 *  DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 *
 *  Count: The number of elements in Names. The maximum value of 1,000 is chosen to limit the amount of memory that the client can force the server to allocate.
 *
 *  Names: An array of strings that are to be mapped to RIDs.
 *
 *  RelativeIds: An array of RIDs of accounts that correspond to the elements in Names.
 *
 *  Use: An array of SID_NAME_USE enumeration values that describe the type of account for each entry in RelativeIds.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *  </pre></blockquote>
 *
 */
public class SamrLookupNamesInDomainRequest extends RequestCall<SamrLookupNamesInDomainResponse> {
    public static final short OP_NUM = 17;

    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
    private final byte[] domainHandle;
    // <NDR: unsigned long> [in, range(0,1000)] unsigned long Count
    // Derived from names
    // <NDR: conformant varying array> [in, size_is(1000), length_is(Count)] RPC_UNICODE_STRING Names[*]
    private final RPCUnicodeString.NonNullTerminated[] names;

    public SamrLookupNamesInDomainRequest(final byte[] domainHandle, final RPCUnicodeString.NonNullTerminated... names) {
        super(OP_NUM);
        if (domainHandle == null) {
            throw new IllegalArgumentException("domainHandle must not be null");
        }
        if (names == null) {
            throw new IllegalArgumentException("names must not be null");
        } else if (names.length > 1000) {
            throw new IllegalArgumentException(String.format(
                    "names must not contain more than 1000 elements, got: %d", names.length));
        }
        this.domainHandle = domainHandle;
        this.names = names;
    }

    public byte[] getDomainHandle() {
        return domainHandle;
    }

    public RPCUnicodeString.NonNullTerminated[] getNames() {
        return names;
    }

    @Override
    public SamrLookupNamesInDomainResponse getResponseObject() {
        return new SamrLookupNamesInDomainResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // [in] SAMPR_HANDLE DomainHandle
        // Alignment: 1 - Already aligned
        packetOut.write(this.domainHandle);
        // <NDR: unsigned long> [in, range(0,1000)] unsigned long Count
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        packetOut.writeInt(this.names.length);
        // MaximumCount: <NDR: conformant varying array> [in, size_is(1000), length_is(Count)] RPC_UNICODE_STRING Names[*]
        // Alignment: 4 - Already aligned
        packetOut.writeInt(1000);
        // Offset: <NDR: conformant varying array> [in, size_is(1000), length_is(Count)] RPC_UNICODE_STRING Names[*]
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
        // ActualLength: <NDR: conformant varying array> [in, size_is(1000), length_is(Count)] RPC_UNICODE_STRING Names[*]
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.names.length);
        // Entities: <NDR: conformant varying array> [in, size_is(1000), length_is(Count)] RPC_UNICODE_STRING Names[*]
        for (RPCUnicodeString.NonNullTerminated name : this.names) {
            name.marshalPreamble(packetOut);
        }
        for (RPCUnicodeString.NonNullTerminated name : this.names) {
            name.marshalEntity(packetOut);
        }
        for (RPCUnicodeString.NonNullTerminated name : this.names) {
            name.marshalDeferrals(packetOut);
        }
    }
}
