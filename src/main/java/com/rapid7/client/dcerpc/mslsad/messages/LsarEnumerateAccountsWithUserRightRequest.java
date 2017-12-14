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
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <blockquote><pre>The LsarEnumerateAccountsWithUserRight method is invoked to return a list of account objects that have the user right equal to the passed-in value.
 *
 *      NTSTATUS LsarEnumerateAccountsWithUserRight(
 *          [in] LSAPR_HANDLE PolicyHandle,
 *          [in, unique] PRPC_UNICODE_STRING UserRight,
 *          [out] PLSAPR_ACCOUNT_ENUM_BUFFER EnumerationBuffer
 *      );
 *  PolicyHandle: An RPC context handle obtained from either LsarOpenPolicy or LsarOpenPolicy2.
 *  UserRight: The name of the right to use in enumeration.
 *  EnumerationBuffer: Used to return the list of account objects that have the specified right.</pre></blockquote>
 */
public class LsarEnumerateAccountsWithUserRightRequest extends RequestCall<LsarEnumerateAccountsWithUserRightResponse> {
    private final static short OP_NUM = 35;
    // <NDR: fixed array> [in] LSAPR_HANDLE PolicyHandle
    private final byte[] policyHandle;
    // <NDR: pointer[struct]> [in, unique] PRPC_UNICODE_STRING UserRight
    private final RPCUnicodeString.NonNullTerminated userRight;

    public LsarEnumerateAccountsWithUserRightRequest(final byte[] policyHandle,
            final RPCUnicodeString.NonNullTerminated userRight) {
        super(OP_NUM);
        this.policyHandle = policyHandle;
        this.userRight = userRight;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] LSAPR_HANDLE PolicyHandle
        packetOut.write(this.policyHandle);
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        if (packetOut.writeReferentID(this.userRight)) {
            packetOut.writeMarshallable(this.userRight);
        }
    }

    @Override
    public LsarEnumerateAccountsWithUserRightResponse getResponseObject() {
        return new LsarEnumerateAccountsWithUserRightResponse();
    }
}
