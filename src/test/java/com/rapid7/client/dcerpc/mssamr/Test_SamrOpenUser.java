/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr;

import java.io.IOException;
import org.junit.Test;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenUserResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;


public class Test_SamrOpenUser {
    // https://msdn.microsoft.com/en-us/library/cc980032.aspx
    private final SamrOpenUserRequest request = new SamrOpenUserRequest(new DomainHandle(),
	    500); //ADMINISTRATOR(500)

    @Test
    public void getOpNum() {
        assertEquals(SamrOpenUserRequest.OP_NUM, request.getOpNum());
    }

    @Test
    public void getStub()
        throws IOException {
        assertEquals("00000000000000000000000000000000000000001b010200f4010000",
            toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject()
        throws IOException {
        assertThat(request.getResponseObject(), instanceOf(SamrOpenUserResponse.class));
    }

}
