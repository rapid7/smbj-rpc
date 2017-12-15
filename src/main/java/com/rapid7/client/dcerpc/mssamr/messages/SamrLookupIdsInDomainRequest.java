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

/**
 * The SamrLookupIdsInDomain method translates a set of RIDs into account names.
 *
 * <pre>
 * long SamrLookupIdsInDomain(
 *   [in] SAMPR_HANDLE DomainHandle,
 *   [in, range(0,1000)] unsigned long Count,
 *   [in, size_is(1000), length_is(Count)]
 *     unsigned long* RelativeIds,
 *   [out] PSAMPR_RETURNED_USTRING_ARRAY Names,
 *   [out] PSAMPR_ULONG_ARRAY Use
 * );
 * </pre>
 * <p>
 * DomainHandle: An RPC context handle, as specified in section 2.2.3.2,
 * representing a domain object.
 * </p>
 *
 * <p>
 * Count: The number of elements in RelativeIds. The maximum value of 1,000 is
 * chosen to limit the amount of memory that the client can force the server to
 * allocate.
 * </p>
 *
 * <p>
 * RelativeIds: An array of RIDs that are to be mapped to account names.
 * </p>
 *
 * <p>
 * Names: A structure containing an array of account names that correspond to
 * the elements in RelativeIds.
 * </p>
 *
 * <p>
 * Use: A structure containing an array of SID_NAME_USE enumeration values that
 * describe the type of account for each entry in RelativeIds.
 * </p>
 *
 * @see <a href=
 *      "https://msdn.microsoft.com/en-us/library/cc245713.aspx">https://msdn.microsoft.com/en-us/library/cc245713.aspx</a>
 */
public class SamrLookupIdsInDomainRequest extends RequestCall<SamrLookupIdsInDomainResponse> {
    public static short OP_NUM = 18;
    private final byte[] handle;
    private final int count;
    private final long[] relativeIDs;

    public SamrLookupIdsInDomainRequest(final byte[] domainHandle, long ... rids) {
        super(OP_NUM);
        this.handle = domainHandle;
        this.count = rids.length;
        if (count > 1000)
            throw new IllegalArgumentException(
                "Count cannot exceed 1000 to limit the amount of memory client can force the server to allocate: "
                    + count);
        this.relativeIDs = rids;
    }

    @Override
    public void marshal(PacketOutput out) throws IOException {
        out.write(handle);
        out.writeInt(count);
        out.writeInt(1000);
        out.writeInt(0);
        out.writeInt(count);
        for (long rid : relativeIDs) {
            out.writeInt(rid);
        }
    }

    @Override
    public SamrLookupIdsInDomainResponse getResponseObject() {
        return new SamrLookupIdsInDomainResponse();
    }
}
