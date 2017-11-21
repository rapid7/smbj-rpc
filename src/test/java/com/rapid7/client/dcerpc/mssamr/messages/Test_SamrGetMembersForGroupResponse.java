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

public class Test_SamrGetMembersForGroupResponse {

    @Test
    public void unmarshall() throws IOException {
        SamrGetMembesInGroupResponse response = new SamrGetMembesInGroupResponse();
        response.fromHexString(
            "0000020004000000040002000800020004000000f4010000f5010000e9030000ea030000040000000700000007000000070000000700000000000000");
        assertEquals(0, response.getReturnValue());
        assertEquals(4, response.getList().size());
        assertEquals(500, response.getList().get(0).getRelativeID());
        assertEquals(0x0000007, response.getList().get(0).getAttributes());
        assertEquals(501, response.getList().get(1).getRelativeID());
        assertEquals(0x0000007, response.getList().get(1).getAttributes());
        assertEquals(1001, response.getList().get(2).getRelativeID());
        assertEquals(0x0000007, response.getList().get(2).getAttributes());
        assertEquals(1002, response.getList().get(3).getRelativeID());
        assertEquals(0x0000007, response.getList().get(3).getAttributes());
    }

}
