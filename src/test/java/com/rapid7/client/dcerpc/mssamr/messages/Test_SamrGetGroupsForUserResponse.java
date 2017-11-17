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

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.mssamr.objects.GroupMembership;

public class Test_SamrGetGroupsForUserResponse {

    @Test
    public void unmarshall() throws IOException {
        SamrGetGroupsForUserResponse response = new SamrGetGroupsForUserResponse();
        response.fromHexString(
            "00000200010000000400020001000000010200000700000000000000");
        assertEquals(0, response.getReturnValue());
        assertEquals(1, response.getGroupMembership().size());
        GroupMembership group = response.getGroupMembership().get(0);
        assertEquals(513, group.getRelativeID());
        assertEquals(0x0000007, group.getAttributes());
    }

}
