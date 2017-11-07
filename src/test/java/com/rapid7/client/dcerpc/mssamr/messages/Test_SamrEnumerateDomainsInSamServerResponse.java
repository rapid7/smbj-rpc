/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;

public class Test_SamrEnumerateDomainsInSamServerResponse {

    @Test
    public void unmarshall() throws IOException {
        SamrEnumerateDomainsInSamServerResponse response = new SamrEnumerateDomainsInSamServerResponse();
        response.fromHexString(
            "0200000000000200020000000400020002000000000000001400160008000200000000000e0010000c0002000b000000000000000a000000570032004b00310032002d00420049002d0043000800000000000000070000004200750069006c00740069006e0000000200000000000000");
        assertEquals(2, response.getNumEntries());
        assertEquals(2, response.getResumeHandle());
        assertEquals(0, response.getReturnValue());
    }
}
