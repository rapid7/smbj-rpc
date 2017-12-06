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

public class Test_LSAPRPolicyPrimaryDomInfo {
    @Test
    public void test_getters_default() {
        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        assertNull(obj.getName());
        assertNull(obj.getSid());
    }

    @Test
    public void test_setters() {
        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        RPCUnicodeString.NonNullTerminated name = RPCUnicodeString.NonNullTerminated.of("test 123");
        obj.setName(name);
        RPCSID sid = new RPCSID();
        obj.setSid(sid);
        assertSame(obj.getName(), name);
        assertSame(obj.getSid(), sid);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        assertNull(obj.getName());
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("010203"));
        obj.unmarshalPreamble(new PacketInput(bin));
        assertEquals(obj.getName(), new RPCUnicodeString.NonNullTerminated());
        assertEquals(bin.available(), 3);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 0
                {"0200 0300 01000000 00000000", 0, null},
                // Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 2
                {"0200 0300 01000000 02000000", 0, new RPCSID()},
                // Alignment: 3, Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 2
                {"ffffffff 0200 0300 01000000 02000000", 1, new RPCSID()},
                // Alignment: 2, Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 2
                {"ffffffff 0200 0300 01000000 02000000", 2, new RPCSID()},
                // Alignment: 1, Name Length: 2, Name MaximumLength: 3, Name Reference: 1, SID Reference: 2
                {"ffffffff 0200 0300 01000000 02000000", 3, new RPCSID()},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, RPCSID expectedSid) throws Exception {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.readFully(new byte[mark]);

        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        obj.setName(new RPCUnicodeString.NonNullTerminated());
        obj.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getName(), RPCUnicodeString.NonNullTerminated.of(""));
        assertEquals(obj.getSid(), expectedSid);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        RPCUnicodeString name1 = RPCUnicodeString.NonNullTerminated.of("test 123");
        RPCSID sid1 = new RPCSID();
        sid1.setRevision((char) 1);
        sid1.setIdentifierAuthority(new byte[]{0,0,0,0,0,5});
        sid1.setSubAuthority(new long[]{1,2,3,4});
        RPCUnicodeString name2 = RPCUnicodeString.NonNullTerminated.of("test 1234");
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
    public void test_unmarshalDeferrals(String hex, RPCSID sid, RPCUnicodeString expectedName, RPCSID expectedSid) throws Exception {
        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        obj.setName(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setSid(sid);
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        obj.unmarshalDeferrals(new PacketInput(bin));
        assertEquals(bin.available(), 0);
        assertEquals(obj.getName(), expectedName);
        assertEquals(obj.getSid(), expectedSid);
    }

    @Test
    public void test_hashCode() {
        LSAPRPolicyPrimaryDomInfo obj1 = new LSAPRPolicyPrimaryDomInfo();
        LSAPRPolicyPrimaryDomInfo obj2 = new LSAPRPolicyPrimaryDomInfo();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setName(new RPCUnicodeString.NonNullTerminated());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setName(new RPCUnicodeString.NonNullTerminated());
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setSid(new RPCSID());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setSid(new RPCSID());
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        LSAPRPolicyPrimaryDomInfo obj1 = new LSAPRPolicyPrimaryDomInfo();
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        LSAPRPolicyPrimaryDomInfo obj2 = new LSAPRPolicyPrimaryDomInfo();
        assertEquals(obj1, obj2);
        obj1.setName(new RPCUnicodeString.NonNullTerminated());
        assertNotEquals(obj1, obj2);
        obj2.setName(new RPCUnicodeString.NonNullTerminated());
        assertEquals(obj1, obj2);
        obj1.setSid(new RPCSID());
        assertNotEquals(obj1, obj2);
        obj2.setSid(new RPCSID());
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        assertEquals(obj.toString(), "LSAPR_POLICY_PRIMARY_DOM_INFO{Name:null, Sid:null}");
    }

    @Test
    public void test_toString() {
        LSAPRPolicyPrimaryDomInfo obj = new LSAPRPolicyPrimaryDomInfo();
        obj.setName(RPCUnicodeString.NonNullTerminated.of("test 123"));
        RPCSID sid = new RPCSID();
        sid.setRevision((char) 1);
        sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
        sid.setSubAuthority(new long[]{1, 2, 3});
        obj.setSid(sid);
        assertEquals(obj.toString(), "LSAPR_POLICY_PRIMARY_DOM_INFO{Name:\"test 123\", Sid:RPC_SID{Revision:1, SubAuthorityCount:3, IdentifierAuthority:[1, 2, 3, 4, 5, 6], SubAuthority: [1, 2, 3]}}");
    }
}
