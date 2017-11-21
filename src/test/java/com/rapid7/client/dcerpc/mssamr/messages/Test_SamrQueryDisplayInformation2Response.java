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
import static org.junit.Assert.assertNull;

import java.io.IOException;
import org.junit.Test;

public class Test_SamrQueryDisplayInformation2Response {

    @Test
    public void unmarshallGroupDisplayInfo() throws IOException {
        SamrQueryDisplayInformation2Response.DomainDisplayGroup response
                = new SamrQueryDisplayInformation2Response.DomainDisplayGroup();
        response.fromHexString(
            "5400000054000000 03000000 00000000 00000000 01000000");
        assertEquals(84, response.getTotalAvailable());
        assertEquals(84, response.getTotalReturned());
        assertEquals(response.getDisplayInformation().getEntriesRead(), 0);
        assertNull(response.getDisplayInformation().getEntries());
        assertEquals(response.getReturnValue(), 1);
    }

}
