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
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.GroupMembership;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGetMembersBuffer;

public class SamrGetMembersInGroupResponse extends RequestResponse {
    private SAMPRGetMembersBuffer buffer;

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        if (packetIn.readReferentID() != 0) {
            this.buffer = new SAMPRGetMembersBuffer();
            packetIn.readUnmarshallable(this.buffer);
        } else {
            this.buffer = null;
        }
    }

    public List<GroupMembership> getList() {
        return buffer.getGroupMembership();
    }
}
