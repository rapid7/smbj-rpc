/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;

public class Test_SamrEnumerateDomainsInSamServerRequest {
    private final SamrEnumerateDomainsInSamServerRequest request1 = new SamrEnumerateDomainsInSamServerRequest(
            new ServerHandle("000000005f32a420f68b2645b4e0e8467cc2e111"), 0, 0xffff);

    @Test
    public void getStub() throws IOException {
        assertEquals("000000005f32a420f68b2645b4e0e8467cc2e11100000000ffff0000",
            toHexString(request1.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request1.getResponseObject(), instanceOf(SamrEnumerateDomainsInSamServerResponse.class));
    }

    @Test
    public void getOpNum() {
        assertEquals(SamrEnumerateDomainsInSamServerRequest.OP_NUM, request1.getOpNum());
    }
}
