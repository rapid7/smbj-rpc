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

import java.rmi.UnmarshalException;

/**
 * The SAMPR_GET_GROUPS_BUFFER structure represents the members of a group.
 *
 * <pre>
 * typedef struct _SAMPR_GET_GROUPS_BUFFER {
 *   unsigned long MembershipCount;
 *   [size_is(MembershipCount)] PGROUP_MEMBERSHIP Groups;
 * } SAMPR_GET_GROUPS_BUFFER,
 *  *PSAMPR_GET_GROUPS_BUFFER;
 * </pre>
 *
 * <p>MembershipCount: The number of elements in Groups. If zero, Groups MUST be ignored. If nonzero, Groups MUST point to at least MembershipCount * sizeof(GROUP_MEMBERSHIP) bytes of memory.</p>
 * <p>Groups: An array to hold information about the members of the group.</p>
 */
public class SAMPRGetGroupsBuffer extends SAMPREnumerationBuffer<GroupMembership> {

    @Override
    protected GroupMembership initEntity() throws UnmarshalException {
        return new GroupMembership();
    }
}
