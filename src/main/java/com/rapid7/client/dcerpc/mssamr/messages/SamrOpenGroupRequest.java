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
 *      long SamrOpenGroup(
 *          [in] SAMPR_HANDLE DomainHandle,
 *          [in] unsigned long DesiredAccess,
 *          [in] unsigned long GroupId,
 *          [out] SAMPR_HANDLE* GroupHandle
 *      );
 */
public class SamrOpenGroupRequest extends RequestCall<SamrOpenGroupResponse> {
    public final static short OP_NUM = 19;

    // <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
    private final DomainHandle domainHandle;
    // <NDR: unsigned long> [in] unsigned long DesiredAccess
    private final int desiredAccess;
    // <NDR: unsigned long> [in] unsigned long GroupId
    private final int groupRID;

    public SamrOpenGroupRequest(DomainHandle domainHandle, int desiredAccess, int groupRID) {
        super(OP_NUM);
        this.domainHandle = domainHandle;
        this.desiredAccess = desiredAccess;
        this.groupRID = groupRID;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.writeMarshallable(domainHandle);
        // No align necessary - Always 24 bytes written
        packetOut.writeInt(desiredAccess);
        // No align necessary - Always 4 bytes written
        packetOut.writeInt(groupRID);
    }

    @Override
    public SamrOpenGroupResponse getResponseObject() {
        return new SamrOpenGroupResponse();
    }
}
