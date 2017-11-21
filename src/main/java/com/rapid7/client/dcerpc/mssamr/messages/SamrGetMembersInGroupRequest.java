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
import com.rapid7.client.dcerpc.mssamr.objects.GroupHandle;

/**
 * The SamrGetMembersInGroup method reads the members of a group.
 *
 * <pre>
 * long SamrGetMembersInGroup(
 *   [in] SAMPR_HANDLE GroupHandle,
 *   [out] PSAMPR_GET_MEMBERS_BUFFER* Members
 * );
 * </pre>
 * <p><strong>GroupHandle</strong>: An RPC context handle, as specified in section <a href="https://msdn.microsoft.com/en-us/library/cc245544.aspx">2.2.3.2</a>, representing a <a href="https://msdn.microsoft.com/en-us/library/cc245478.aspx#gt_7ce4771c-2043-49b8-85d3-0c60c7789f9a">group object.</a></p>
 * <p><strong>Members</strong>: A structure containing an array of <a href="https://msdn.microsoft.com/en-us/library/cc245478.aspx#gt_df3d0b61-56cd-4dac-9402-982f1fedc41c">RIDs</a>, as well as an array of attribute values.</p>
 */
public class SamrGetMembersInGroupRequest extends RequestCall<SamrGetMembersInGroupResponse> {

    public static final short OP_NUM = 25;
    private final GroupHandle handle;

    public SamrGetMembersInGroupRequest(GroupHandle handle) {
        super(OP_NUM);
        this.handle = handle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.writeMarshallable(handle);
    }

    @Override
    public SamrGetMembersInGroupResponse getResponseObject() {
        return new SamrGetMembersInGroupResponse();
    }
}
