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
 * <a href="https://msdn.microsoft.com/en-us/library/cc245718.aspx">SamrQuerySecurityObject</a>
 * <blockquote><pre>The SamrQuerySecurityObject method queries the access control on a server, domain, user, group, or alias object.
 *      long SamrQuerySecurityObject(
 *          [in] SAMPR_HANDLE ObjectHandle,
 *          [in] SECURITY_INFORMATION SecurityInformation,
 *          [out] PSAMPR_SR_SECURITY_DESCRIPTOR* SecurityDescriptor
 *      );
 *  ObjectHandle: An RPC context handle, as specified in section 2.2.3.2, representing a server, domain, user, group, or alias object.
 *  SecurityInformation: A bit field that specifies which fields of SecurityDescriptor the client is requesting to be returned.
 *      The SECURITY_INFORMATION type is defined in [MS-DTYP] section 2.4.7. The following bits are valid; all other bits MUST be zero when sent and ignored on receipt.
 *
 *      OWNER_SECURITY_INFORMATION          If this bit is set, the client requests that the Owner member be returned.
 *      0x00000001                          If this bit is not set, the client requests that the Owner member not be returned.
 *
 *      GROUP_SECURITY_INFORMATION          If this bit is set, the client requests that the Group member be returned.
 *      0x00000002                          If this bit is not set, the client requests that the Group member not be returned.
 *
 *      DACL_SECURITY_INFORMATION           If this bit is set, the client requests that the DACL be returned.
 *      0x00000004                          If this bit is not set, the client requests that the DACL not be returned.
 *
 *      SACL_SECURITY_INFORMATION           If this bit is set, the client requests that the SACL be returned.
 *      0x00000008                          If this bit is not set, the client requests that the SACL not be returned.
 *
 *  SecurityDescriptor: A security descriptor expressing accesses that are specific to the ObjectHandle and the owner and group of the object. [MS-DTYP] section 2.4.6 contains the specification for a valid security descriptor.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrQuerySecurityObjectRequest extends RequestCall<SamrQuerySecurityObjectResponse> {
    public static final short OP_NUM = 3;

    // <NDR: struct> [in] SAMPR_HANDLE ObjectHandle,
    private final byte[] objectHandle;
    // <NDR: unsigned long> [in] SECURITY_INFORMATION SecurityInformation,
    // This is a bitmask, so can store as an int
    private final int securityInformation;

    public SamrQuerySecurityObjectRequest(final byte[] objectHandle, final int securityInformation) {
        super(OP_NUM);
        this.objectHandle = objectHandle;
        this.securityInformation = securityInformation;
    }

    public byte[] getObjectHandle() {
        return objectHandle;
    }

    public int getSecurityInformation() {
        return securityInformation;
    }

    @Override
    public SamrQuerySecurityObjectResponse getResponseObject() {
        return new SamrQuerySecurityObjectResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: struct> [in] SAMPR_HANDLE ObjectHandle,
        packetOut.write(getObjectHandle());
        // <NDR: unsigned long> [in] SECURITY_INFORMATION SecurityInformation,
        // Alignment: 4 - Already aligned, we wrote 20 bytes above
        packetOut.writeInt(getSecurityInformation());
    }
}
