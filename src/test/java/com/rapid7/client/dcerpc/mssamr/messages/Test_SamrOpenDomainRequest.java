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

import java.io.IOException;
import java.util.EnumSet;
import org.junit.Test;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;
import com.rapid7.client.dcerpc.objects.MalformedSIDException;
import com.rapid7.client.dcerpc.objects.RPCSID;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Test_SamrOpenDomainRequest {
    @Test
    public void getOpNum() throws MalformedSIDException {
        assertEquals(SamrOpenDomainRequest.OP_NUM, createRequest().getOpNum());
    }

    @Test
    public void getStub() throws IOException, MalformedSIDException {
        assertEquals("00000000000000000000000000000000000000000000000201000000010100000000000520000000", toHexString(createRequest().getStub()));
    }

    @Test
    public void getResponseObject() throws IOException, MalformedSIDException {
        assertThat(createRequest().getResponseObject(), instanceOf(SamrOpenDomainResponse.class));
    }

    private SamrOpenDomainRequest createRequest() throws MalformedSIDException {
        RPCSID rpcsid = new RPCSID();
        rpcsid.setRevision((char) 1);
        rpcsid.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        rpcsid.setSubAuthority(new long[]{32});
        return new SamrOpenDomainRequest(new ServerHandle(), (int) AccessMask.MAXIMUM_ALLOWED.getValue(), rpcsid);
    }
}
