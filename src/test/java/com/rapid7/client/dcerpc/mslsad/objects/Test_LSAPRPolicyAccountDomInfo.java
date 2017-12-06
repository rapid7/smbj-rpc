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
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_LSAPRPolicyAccountDomInfo {
    @Test
    public void test_getters_default() {
        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        assertNull(obj.getDomainName());
        assertNull(obj.getDomainSid());
    }

    @Test
    public void test_setters() {
        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        RPCUnicodeString.NonNullTerminated name = RPCUnicodeString.NonNullTerminated.of("test 123");
        obj.setDomainName(name);
        RPCSID sid = new RPCSID();
        obj.setDomainSid(sid);
        assertSame(obj.getDomainName(), name);
        assertSame(obj.getDomainSid(), sid);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        assertNull(obj.getDomainName());
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("010203"));
        obj.unmarshalPreamble(new PacketInput(bin));
        assertEquals(obj.getDomainName(), new RPCUnicodeString.NonNullTerminated());
        assertEquals(bin.available(), 3);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 0
                {"0200 0300 01000000 00000000", 0, null},
                // Alignment: 1, Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 0
                {"ffffffff 0200 0300 01000000 00000000", 3, null},
                // Alignment: 2, Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 0
                {"ffffffff 0200 0300 01000000 00000000", 2, null},
                // Alignment: 3, Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 0
                {"ffffffff 0200 0300 01000000 00000000", 1, null},
                // Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 2
                {"0200 0300 01000000 02000000", 0, new RPCSID()},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, RPCSID expectedSid) throws Exception {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.readFully(new byte[mark]);

        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        obj.setDomainName(new RPCUnicodeString.NonNullTerminated());
        obj.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getDomainName(), RPCUnicodeString.NonNullTerminated.of(""));
        assertEquals(obj.getDomainSid(), expectedSid);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        RPCUnicodeString.NonNullTerminated name1 = RPCUnicodeString.NonNullTerminated.of("test 123");
        RPCSID sid1 = new RPCSID();
        sid1.setRevision((char) 1);
        sid1.setIdentifierAuthority(new byte[]{0,0,0,0,0,5});
        sid1.setSubAuthority(new long[]{1,2,3,4});
        RPCUnicodeString.NonNullTerminated name2 = RPCUnicodeString.NonNullTerminated.of("test 1234");
        return new Object[][] {
                // Name MaximumCount: 9, Offset: 0, ActualCount: 8, Value: "test 123"
                // SID MaximumCount: 4, Revision: 1, SubAuthorityCount: 4, IdentifierAuthority:{0,0,0,0,0,5}, SubAuthority:{1,2,3,4}
                {"09000000 00000000 08000000 7400 6500 7300 7400 2000 3100 3200 3300"
                + "04000000 01 04 000000000005 01000000 02000000 03000000 04000000",
                new RPCSID(), name1, sid1},
                // Test Alignment:
                // Name MaximumCount: 9, Offset: 0, ActualCount: 9, Value: "test 1234"
                // SID MaximumCount: 4, Revision: 1, SubAuthorityCount: 4, IdentifierAuthority:{0,0,0,0,0,5}, SubAuthority:{1,2,3,4}
                {"09000000 00000000 09000000 7400 6500 7300 7400 2000 3100 3200 3300 3400 0000"
                + "04000000 01 04 000000000005 01000000 02000000 03000000 04000000",
                new RPCSID(), name2, sid1},
                // Test null sid pointer:
                // Name MaximumCount: 9, Offset: 0, ActualCount: 8, Value: "test 123"
                {"09000000 00000000 08000000 7400 6500 7300 7400 2000 3100 3200 3300",
                null, name1, null},
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, RPCSID sid, RPCUnicodeString.NonNullTerminated expectedName, RPCSID expectedSid) throws Exception {
        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        obj.setDomainName(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setDomainSid(sid);
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        obj.unmarshalDeferrals(new PacketInput(bin));
        assertEquals(bin.available(), 0);
        assertEquals(obj.getDomainName(), expectedName);
        assertEquals(obj.getDomainSid(), expectedSid);
    }

    @Test
    public void test_hashCode() {
        LSAPRPolicyAccountDomInfo obj1 = new LSAPRPolicyAccountDomInfo();
        LSAPRPolicyAccountDomInfo obj2 = new LSAPRPolicyAccountDomInfo();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDomainName(new RPCUnicodeString.NonNullTerminated());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDomainName(new RPCUnicodeString.NonNullTerminated());
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDomainSid(new RPCSID());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDomainSid(new RPCSID());
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        LSAPRPolicyAccountDomInfo obj1 = new LSAPRPolicyAccountDomInfo();
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        LSAPRPolicyAccountDomInfo obj2 = new LSAPRPolicyAccountDomInfo();
        assertEquals(obj1, obj2);
        obj1.setDomainName(new RPCUnicodeString.NonNullTerminated());
        assertNotEquals(obj1, obj2);
        obj2.setDomainName(new RPCUnicodeString.NonNullTerminated());
        assertEquals(obj1, obj2);
        obj1.setDomainSid(new RPCSID());
        assertNotEquals(obj1, obj2);
        obj2.setDomainSid(new RPCSID());
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        assertEquals(obj.toString(), "LSAPR_POLICY_ACCOUNT_DOM_INFO{DomainName:null, DomainSid:null}");
    }

    @Test
    public void test_toString() {
        LSAPRPolicyAccountDomInfo obj = new LSAPRPolicyAccountDomInfo();
        obj.setDomainName(RPCUnicodeString.NonNullTerminated.of("test 123"));
        RPCSID sid = new RPCSID();
        sid.setRevision((char) 1);
        sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
        sid.setSubAuthority(new long[]{1, 2, 3});
        obj.setDomainSid(sid);
        assertEquals(obj.toString(), "LSAPR_POLICY_ACCOUNT_DOM_INFO{DomainName:\"test 123\", DomainSid:RPC_SID{Revision:1, SubAuthorityCount:3, IdentifierAuthority:[1, 2, 3, 4, 5, 6], SubAuthority: [1, 2, 3]}}");
    }
}
