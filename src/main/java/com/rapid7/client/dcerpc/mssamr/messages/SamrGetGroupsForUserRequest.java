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
 * The SamrGetGroupsForUser method obtains a listing of groups that a user is a member of.
 *
 * <pre>
 * long SamrGetGroupsForUser(
 *  [in] SAMPR_HANDLE UserHandle,
 *  [out] PSAMPR_GET_GROUPS_BUFFER* Groups
 * );
 * </pre>
 * <p><strong>UserHandle: </strong>An RPC context handle, as
 * specified in section <a href="https://msdn.microsoft.com/en-us/library/cc245544.aspx">2.2.3.2</a>,
 * representing a <a href="https://msdn.microsoft.com/en-us/library/cc245478.aspx#gt_e767a471-c3fa-4e4b-a40c-daeb08f82a17">user object</a>.</p>
 * <p><strong>Groups: </strong>An array of <a href="https://msdn.microsoft.com/en-us/library/cc245478.aspx#gt_df3d0b61-56cd-4dac-9402-982f1fedc41c">RIDs</a> of the groups that the
 * user referenced by <em>UserHandle</em> is a member of.</p>
 */
public class SamrGetGroupsForUserRequest extends RequestCall<SamrGetGroupsForUserResponse> {
    public static final short OP_NUM = 39;

    // <NDR: fixed array> [in] SAMPR_HANDLE UserHandle
    private final byte[] userHandle;

    public SamrGetGroupsForUserRequest(byte[] userHandle) {
        super(OP_NUM);
        this.userHandle = userHandle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(this.userHandle);
    }

    @Override
    public SamrGetGroupsForUserResponse getResponseObject() {
        return new SamrGetGroupsForUserResponse();
    }
}
