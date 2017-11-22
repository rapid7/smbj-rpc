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
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;

public class Test_SamrGetAliasMembershipResponse {

    @Test
    public void unmarshalEmptyResponse() throws IOException {
        SamrGetAliasMembershipResponse response = new SamrGetAliasMembershipResponse();
        response.fromHexString("00000000000002000000000000000000");
        assertTrue(response.getList().isEmpty());
        assertEquals(0, response.getReturnValue());
    }

    @Test
    public void unmarshalResponse() throws IOException {
        SamrGetAliasMembershipResponse response = new SamrGetAliasMembershipResponse();
        response.fromHexString("0100000000000200010000002102000000000000");
        assertEquals(1, response.getList().size());
        assertEquals(545, response.getList().get(0).intValue());
        assertEquals(0, response.getReturnValue());
    }

}
