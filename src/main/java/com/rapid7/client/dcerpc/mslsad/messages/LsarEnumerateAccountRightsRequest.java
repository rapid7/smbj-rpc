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
import java.rmi.MarshalException;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.RPCSID;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc234411.aspx">LsarEnumerateAccountRights</a>
 * <blockquote><pre>The LsarEnumerateAccountRights method is invoked to retrieve a list of rights associated with an existing account.
 *
 *  NTSTATUS LsarEnumerateAccountRights(
 *      [in] LSAPR_HANDLE PolicyHandle,
 *      [in] PRPC_SID AccountSid,
 *      [out] PLSAPR_USER_RIGHT_SET UserRights
 *  );
 *
 *  PolicyHandle: An RPC context handle obtained from either LsarOpenPolicy or LsarOpenPolicy2.
 *  AccountSid: A SID of the account object that the caller is inquiring about.
 *  UserRights: Used to return a list of right names associated with the account.</pre></blockquote>
 */
public class LsarEnumerateAccountRightsRequest extends RequestCall<LsarEnumerateAccountRightsResponse> {
    private final static short OP_NUM = 36;

    // <NDR: fixed array> [in] LSAPR_HANDLE PolicyHandle
    private final ContextHandle policyHandle;
    // <NDR: struct> [in] PRPC_SID AccountSid
    private final RPCSID accountSid;

    public LsarEnumerateAccountRightsRequest(final ContextHandle policyHandle, final RPCSID accountSid) {
        super(OP_NUM);
        this.policyHandle = policyHandle;
        this.accountSid = accountSid;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] LSAPR_HANDLE PolicyHandle
        packetOut.writeMarshallable(this.policyHandle);
        // <NDR: struct> [in] PRPC_SID AccountSid
        packetOut.writeMarshallable(this.accountSid);
    }

    @Override
    public LsarEnumerateAccountRightsResponse getResponseObject() {
        return new LsarEnumerateAccountRightsResponse();
    }
}
