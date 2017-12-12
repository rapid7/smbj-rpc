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

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRPSIDArray;
import com.rapid7.client.dcerpc.objects.RPCSID;

public class Test_SamrGetAliasMembershipRequest {

    @Test
    public void getStub() throws IOException {
        RPCSID rpcSid1 = new RPCSID();
        RPCSID rpcSid2 = new RPCSID();
        SID sid1 = SID.fromString("S-1-5-32");
        SID sid2 = SID.fromString("S-1-5-21");
        rpcSid1.setRevision((char) sid1.getRevision());
        rpcSid1.setIdentifierAuthority(sid1.getIdentifierAuthority());
        rpcSid1.setSubAuthority(sid1.getSubAuthorities());
        rpcSid2.setRevision((char) sid2.getRevision());
        rpcSid2.setIdentifierAuthority(sid2.getIdentifierAuthority());
        rpcSid2.setSubAuthority(sid2.getSubAuthorities());
        SAMPRPSIDArray samprpsidArray = new SAMPRPSIDArray(rpcSid1, rpcSid2);
        byte[] handle = Hex.decode("000000005f32a420f68b2645b4e0e8467cc2e111");
        SamrGetAliasMembershipRequest request1 = new SamrGetAliasMembershipRequest(handle, samprpsidArray);
        assertEquals(
            "000000005f32a420f68b2645b4e0e8467cc2e11102000000000002000200000004000200080002000100000001010000000000052000000001000000010100000000000515000000",
            toHexString(request1.getStub()));
    }

    @Test
    public void getStubEmptyArray() throws IOException {
        byte[] handle = Hex.decode("000000005f32a420f68b2645b4e0e8467cc2e111");
        SamrGetAliasMembershipRequest request1 = new SamrGetAliasMembershipRequest(handle, new SAMPRPSIDArray());
        assertEquals("000000005f32a420f68b2645b4e0e8467cc2e111000000000000020000000000",
            toHexString(request1.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        SamrGetAliasMembershipRequest request1 = new SamrGetAliasMembershipRequest(new byte[20], null);
        assertThat(request1.getResponseObject(), instanceOf(SamrGetAliasMembershipResponse.class));
    }

    @Test
    public void getOpNum() {
        SamrGetAliasMembershipRequest request1 = new SamrGetAliasMembershipRequest(new byte[20], null);
        assertEquals(SamrGetAliasMembershipRequest.OP_NUM, request1.getOpNum());
    }
}
