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
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

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
public class SAMPRGetMembersBuffer extends SAMPREnumerationBuffer<GroupMembership> {

    private List<GroupMembership> array;

    @Override
    public final List<GroupMembership> getEntries() {
        if (array == null)
            return null;
        return Collections.unmodifiableList(array);
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        /*
         * The GetMembersBuffer consists of two arrays, one for RIDs and one for
         * the attributes bitmasks, which are the two fields in the
         * GroupMembership struct.
         */
        super.unmarshalEntity(in);
        if (in.readReferentID() != 0) {
            if (getEntriesRead() > 0)
                array = new ArrayList<>(getEntriesRead());
            else
                array = Collections.emptyList();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        // Entries of domain_list
        if (array != null) {
            // MaximumCount
            in.align(Alignment.FOUR);
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                GroupMembership t = initEntity();
                t.setRelativeID(in.readInt());
                array.add(t);
            }
        }
        if (array != null) {
            // MaximumCount
            in.align(Alignment.FOUR);
            int count = in.readInt();
            for (GroupMembership group : array) {
                group.setAttributes(in.readInt());
            }
        }
    }

    @Override
    protected GroupMembership initEntity() throws UnmarshalException {
        return new GroupMembership();
    }
}
