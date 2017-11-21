/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.RPCSID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class Test_SamrLookupDomainInSamServerResponse {

    @Test
    public void test_getters() {
        SamrLookupDomainInSamServerResponse obj = new SamrLookupDomainInSamServerResponse();
        assertNull(obj.getDomainId());
        assertEquals(obj.getReturnValue(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshal() {
        RPCSID domainId = new RPCSID();
        domainId.setRevision((char) 25);
        domainId.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
        domainId.setSubAuthority(new long[]{5, 2684354560L});

        return new Object[][] {
                // Reference: 0, ReturnValue: 1
                {"00000000 01000000", null},
                // Reference: 1 DomainId: {MaximumCount: 0, Revision:25,SubAuthorityCount:2,IdentifierAuthority:{1...6},SubAuthority:{5, 2684354560}, ReturnValue; 1
                {"01000000 00000000190201020304050605000000000000a0 01000000", domainId}
        };
    }

    @Test(dataProvider = "data_unmarshal")
    public void test_unmarshal(String hex, RPCSID expectDomainId) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        SamrLookupDomainInSamServerResponse obj = new SamrLookupDomainInSamServerResponse();
        obj.unmarshal(in);

        assertEquals(bin.available(), 0);
        assertEquals(obj.getDomainId(), expectDomainId);
        assertEquals(obj.getReturnValue(), 1);
    }
}
