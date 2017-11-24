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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.mssamr.dto.GroupAttributes;

/**
 * The GROUP_MEMBERSHIP structure holds information on a group membership.
 *
 * <pre>
 * typedef struct _GROUP_MEMBERSHIP {
 *   unsigned long RelativeId;
 *   unsigned long Attributes;
 * } GROUP_MEMBERSHIP,
 *  *PGROUP_MEMBERSHIP;
 * </pre>
 *
 * <p>RelativeId: A RID that represents one membership value.</p>
 * <p>Attributes: Characteristics about the membership represented as a bitmask. Values are defined in
 *    section <a href="https://msdn.microsoft.com/en-us/library/cc245538.aspx"> 2.2.1.10</a>.
 *    See {@link GroupAttributes}.
 * </p>
 */
public class GroupMembership implements Unmarshallable {

    private int rid;
    private int attributes;

    public int getRelativeID() {
        return rid;
    }

    public int getAttributes() {
        return attributes;
    }

    public void setRelativeID(final int rid) {
        this.rid = rid;
    }

    public void setAttributes(final int attributes) {
        this.attributes = attributes;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        rid = in.readInt();
        attributes = in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    }
}
