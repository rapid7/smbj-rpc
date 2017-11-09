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

package com.rapid7.client.dcerpc.mslsad.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.objects.RPC_SID;
import com.rapid7.client.dcerpc.objects.RPC_UNICODE_STRING;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_LSAPR_POLICY_ACCOUNT_DOM_INFO {
    @Test
    public void test_getters_default() {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        assertEquals(obj.getAlignment(), Alignment.FOUR);
        assertNull(obj.getDomainName());
        assertNull(obj.getDomainSid());
    }

    @Test
    public void test_setters() {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        RPC_UNICODE_STRING name = RPC_UNICODE_STRING.of(false, "test 123");
        obj.setDomainName(name);
        RPC_SID sid = new RPC_SID();
        obj.setDomainSid(sid);
        assertSame(obj.getDomainName(), name);
        assertSame(obj.getDomainSid(), sid);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        assertNull(obj.getDomainName());
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("010203"));
        obj.unmarshalPreamble(new PacketInput(bin));
        assertEquals(obj.getDomainName(), RPC_UNICODE_STRING.of(false));
        assertEquals(bin.available(), 3);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 0
                {"0200 0300 01000000 00000000", RPC_UNICODE_STRING.of(false, ""), null},
                // Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 2
                {"0200 0300 01000000 02000000", RPC_UNICODE_STRING.of(false, ""), new RPC_SID()},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, RPC_UNICODE_STRING expectedName, RPC_SID expectedSid) throws Exception {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        obj.setDomainName(RPC_UNICODE_STRING.of(false));
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        obj.unmarshalEntity(new PacketInput(bin));
        assertEquals(bin.available(), 0);
        assertEquals(obj.getDomainName(), expectedName);
        assertEquals(obj.getDomainSid(), expectedSid);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        RPC_UNICODE_STRING name1 = RPC_UNICODE_STRING.of(false, "test 123");
        RPC_SID sid1 = new RPC_SID();
        sid1.setRevision((char) 1);
        sid1.setSubAuthorityCount((char) 4);
        sid1.setIdentifierAuthority(new byte[]{0,0,0,0,0,5});
        sid1.setSubAuthority(new long[]{1,2,3,4});
        RPC_UNICODE_STRING name2 = RPC_UNICODE_STRING.of(false, "test 1234");
        return new Object[][] {
                // Name MaximumCount: 9, Offset: 0, ActualCount: 8, Value: "test 123"
                // SID MaximumCount: 4, Revision: 1, SubAuthorityCount: 4, IdentifierAuthority:{0,0,0,0,0,5}, SubAuthority:{1,2,3,4}
                {"09000000 00000000 08000000 7400 6500 7300 7400 2000 3100 3200 3300"
                + "04000000 01 04 000000000005 01000000 02000000 03000000 04000000",
                new RPC_SID(), name1, sid1},
                // Test Alignment:
                // Name MaximumCount: 9, Offset: 0, ActualCount: 9, Value: "test 1234"
                // SID MaximumCount: 4, Revision: 1, SubAuthorityCount: 4, IdentifierAuthority:{0,0,0,0,0,5}, SubAuthority:{1,2,3,4}
                {"09000000 00000000 09000000 7400 6500 7300 7400 2000 3100 3200 3300 3400 0000"
                + "04000000 01 04 000000000005 01000000 02000000 03000000 04000000",
                new RPC_SID(), name2, sid1},
                // Test null sid pointer:
                // Name MaximumCount: 9, Offset: 0, ActualCount: 8, Value: "test 123"
                {"09000000 00000000 08000000 7400 6500 7300 7400 2000 3100 3200 3300",
                null, name1, null},
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, RPC_SID sid, RPC_UNICODE_STRING expectedName, RPC_SID expectedSid) throws Exception {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        obj.setDomainName(RPC_UNICODE_STRING.of(false, ""));
        obj.setDomainSid(sid);
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        obj.unmarshalDeferrals(new PacketInput(bin));
        assertEquals(bin.available(), 0);
        assertEquals(obj.getDomainName(), expectedName);
        assertEquals(obj.getDomainSid(), expectedSid);
    }

    @Test
    public void test_toString_default() {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        assertEquals(obj.toString(), "LSAPR_POLICY_ACCOUNT_DOM_INFO{DomainName:null, DomainSid:null}");
    }

    @Test
    public void test_toString() {
        LSAPR_POLICY_ACCOUNT_DOM_INFO obj = new LSAPR_POLICY_ACCOUNT_DOM_INFO();
        obj.setDomainName(RPC_UNICODE_STRING.of(false, "test 123"));
        RPC_SID sid = new RPC_SID();
        sid.setRevision((char) 1);
        sid.setSubAuthorityCount((char) 4);
        sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
        sid.setSubAuthority(new long[]{1, 2, 3});
        obj.setDomainSid(sid);
        assertEquals(obj.toString(), "LSAPR_POLICY_ACCOUNT_DOM_INFO{DomainName:RPC_UNICODE_STRING{value:\"test 123\", nullTerminated:false}, DomainSid:RPC_SID{Revision:1, SubAuthorityCount:4, IdentifierAuthority:[1, 2, 3, 4, 5, 6], SubAuthority: [1, 2, 3]}}");
    }
}
