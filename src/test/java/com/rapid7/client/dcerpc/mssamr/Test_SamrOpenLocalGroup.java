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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenLocalGroupRpcRequest;
import com.rapid7.client.dcerpc.mssamr.messages.SamrOpenLocalGroupRpcResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;


public class Test_SamrOpenLocalGroup {
    // https://msdn.microsoft.com/en-us/library/cc980032.aspx
    private final SamrOpenLocalGroupRpcRequest request = new SamrOpenLocalGroupRpcRequest(new DomainHandle(),
	    500, 0x0002000C);

    @Test
    public void getOpNum() {
        assertEquals(SamrOpenLocalGroupRpcRequest.OP_NUM, request.getOpNum());
    }

    @Test
    public void getStub()
        throws IOException {
        assertEquals("00000000000000000000000000000000000000000c000200f4010000",
            toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject()
        throws IOException {
        assertThat(request.getResponseObject(), instanceOf(SamrOpenLocalGroupRpcResponse.class));
    }

}
