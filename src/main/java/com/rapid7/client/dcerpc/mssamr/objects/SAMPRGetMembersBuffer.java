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
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantIntegerArray;

/**
 * The SAMPR_GET_MEMBERS_BUFFER structure represents the membership of a group.
 *
 * <pre>
 * typedef struct _SAMPR_GET_MEMBERS_BUFFER {
 *   unsigned long MemberCount;
 *   [size_is(MemberCount)] unsigned long* Members;
 *   [size_is(MemberCount)] unsigned long* Attributes;
 * } SAMPR_GET_MEMBERS_BUFFER,
 *  *PSAMPR_GET_MEMBERS_BUFFER;
 * </pre>
 * <p><strong>MemberCount</strong>: The number of elements in Members and Attributes. If zero, Members and Attributes MUST be ignored. If nonzero, Members and Attributes MUST point to at least MemberCount * sizeof(unsigned long) bytes of memory.</p>
 * <p><strong>Members</strong>: An array of <a href="https://msdn.microsoft.com/en-us/library/cc245478.aspx#gt_df3d0b61-56cd-4dac-9402-982f1fedc41c">RIDs</a>.</p>
 * <p><strong>Attributes</strong>: Characteristics about the membership, represented as a bitmask. Values are defined in section <a href="https://msdn.microsoft.com/en-us/library/cc245512.aspx">2.2.1.10</a>.</p>
 */
public class SAMPRGetMembersBuffer implements Unmarshallable {

    private int memberCount;
    private RPCConformantIntegerArray members;
    private RPCConformantIntegerArray attributes;

    private List<GroupMembership> array;

    public List<GroupMembership> getGroupMembership() {
        return array;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        memberCount = in.readInt();
        if (in.readReferentID() != 0) {
            members = new RPCConformantIntegerArray(new Integer[memberCount]);
        } else {
            members = null;
        }
        if (in.readReferentID() != 0) {
            attributes = new RPCConformantIntegerArray(new Integer[memberCount]);
        } else {
            attributes = null;
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        if (members != null)
            in.readUnmarshallable(members);
        if (attributes != null)
            in.readUnmarshallable(attributes);
        array = new ArrayList<>(memberCount);
        for (int i = 0; i < memberCount; i++) {
            GroupMembership member = new GroupMembership();
            member.setRelativeID(members.getArray()[i]);
            member.setAttributes(attributes.getArray()[i]);
            array.add(member);
        }
    }
}
