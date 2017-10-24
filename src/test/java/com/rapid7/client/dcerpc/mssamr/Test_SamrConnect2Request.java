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
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mssamr.messages.SamrConnect2Request;

/**
 * 
 */
public class Test_SamrConnect2Request {
    private final SamrConnect2Request request1 = new SamrConnect2Request(null, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));
    private final SamrConnect2Request request2 = new SamrConnect2Request("", EnumSet.of(AccessMask.MAXIMUM_ALLOWED));

    @Test
    public void getOpNum() {
        assertEquals(SamrConnect2Request.OP_NUM, request1.getOpNum());
    }

    @Test
    public void getStub()
        throws IOException {
        assertEquals("000002000100000000000000010000000000000000000002", toHexString(request1.getStub()));
        assertEquals("000002000100000000000000010000000000000000000002", toHexString(request2.getStub()));
    }

    @Test
    public void getResponseObject()
        throws IOException {
        assertThat(request1.getResponseObject(), instanceOf(HandleResponse.class));
    }
}
