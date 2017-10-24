/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.util.EnumSet;
import org.junit.Test;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenDomainResponse;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;

public class Test_SamrOpenDomainRequest {
    private final SamrOpenDomainRequest request = new SamrOpenDomainRequest(new ServerHandle(),
        SID.fromString("S-1-5-32"), EnumSet.of(AccessMask.MAXIMUM_ALLOWED));

    @Test
    public void getOpNum() {
        assertEquals(SamrOpenDomainRequest.OP_NUM, request.getOpNum());
    }

    @Test
    public void getStub()
        throws IOException {
        assertEquals("00000000000000000000000000000000000000000000000201000000010100000000000520000000",
            toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject()
        throws IOException {
        assertThat(request.getResponseObject(), instanceOf(SamrOpenDomainResponse.class));
    }
}
