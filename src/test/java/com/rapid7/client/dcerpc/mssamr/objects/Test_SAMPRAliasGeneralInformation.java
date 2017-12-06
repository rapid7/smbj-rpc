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

package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_SAMPRAliasGeneralInformation {

    @Test
    public void test_getters() {
        SAMPRAliasGeneralInformation obj = new SAMPRAliasGeneralInformation();
        assertNull(obj.getName());
        assertEquals(obj.getMemberCount(), 0L);
        assertNull(obj.getAdminComment());
    }

    @Test
    public void test_setters() {
        SAMPRAliasGeneralInformation obj = new SAMPRAliasGeneralInformation();
        RPCUnicodeString.NonNullTerminated name = RPCUnicodeString.NonNullTerminated.of("Name");
        obj.setName(name);
        obj.setMemberCount(100L);
        RPCUnicodeString.NonNullTerminated adminComment = RPCUnicodeString.NonNullTerminated.of("AdminComment");
        obj.setAdminComment(adminComment);
        assertSame(obj.getName(), name);
        assertEquals(obj.getMemberCount(), 100L);
        assertSame(obj.getAdminComment(), adminComment);
    }

    @Test
    public void test_unmarshalPremable() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        SAMPRAliasGeneralInformation obj = new SAMPRAliasGeneralInformation();
        obj.unmarshalPreamble(in);
        assertEquals(obj.getName(), new RPCUnicodeString.NonNullTerminated());
        assertEquals(obj.getMemberCount(), 0L);
        assertEquals(obj.getAdminComment(), new RPCUnicodeString.NonNullTerminated());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        // Name: Non-Null, MemberCount: 4294967295 AdminComment: Non-Null
        String hex1 = "1000100000000200 FFFFFFFF 1000100000000200";
        SAMPRAliasGeneralInformation expect1 = new SAMPRAliasGeneralInformation();
        expect1.setName(RPCUnicodeString.NonNullTerminated.of(""));
        expect1.setMemberCount(4294967295L);
        expect1.setAdminComment(RPCUnicodeString.NonNullTerminated.of(""));
        // Name: Null, MemberCount: 0 AdminComment: Null
        String hex2 = "0000000000000000 00000000 0000000000000000";
        SAMPRAliasGeneralInformation expect2 = new SAMPRAliasGeneralInformation();
        expect2.setName(RPCUnicodeString.NonNullTerminated.of(null));
        expect2.setMemberCount(0L);
        expect2.setAdminComment(RPCUnicodeString.NonNullTerminated.of(null));
        // Alignment: 4b Name: Non-Null, MemberCount: 4294967295 AdminComment: Non-Null
        String hex3 = "00000000" + hex1;
        return new Object[][]{
                {hex1, 0, expect1},
                {hex2, 0, expect2},
                // Alignment
                {hex3, 1, expect1},
                {hex3, 2, expect1},
                {hex3, 3, expect1}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, SAMPRAliasGeneralInformation expected) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        SAMPRAliasGeneralInformation obj = new SAMPRAliasGeneralInformation();
        obj.setName(new RPCUnicodeString.NonNullTerminated());
        obj.setAdminComment(new RPCUnicodeString.NonNullTerminated());
        obj.unmarshalEntity(in);
        assertEquals(obj, expected);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        String hex1 =
                // UserName: testƟ121
                "08000000000000000800000074006500730074009f01310032003100 " +
                // FullName: testƟ122
                "08000000000000000800000074006500730074009f01310032003200 ";
        SAMPRAliasGeneralInformation obj1 = new SAMPRAliasGeneralInformation();
        obj1.setName(RPCUnicodeString.NonNullTerminated.of(""));
        obj1.setMemberCount(100L);
        obj1.setAdminComment(RPCUnicodeString.NonNullTerminated.of(""));
        SAMPRAliasGeneralInformation expected1 = new SAMPRAliasGeneralInformation();
        expected1.setName(RPCUnicodeString.NonNullTerminated.of("testƟ121"));
        expected1.setMemberCount(100L);
        expected1.setAdminComment(RPCUnicodeString.NonNullTerminated.of("testƟ122"));
        String hex2 = "";
        SAMPRAliasGeneralInformation obj2 = new SAMPRAliasGeneralInformation();
        obj2.setName(RPCUnicodeString.NonNullTerminated.of(null));
        obj2.setMemberCount(100L);
        obj2.setAdminComment(RPCUnicodeString.NonNullTerminated.of(null));
        SAMPRAliasGeneralInformation expected2 = new SAMPRAliasGeneralInformation();
        expected2.setName(RPCUnicodeString.NonNullTerminated.of(null));
        expected2.setMemberCount(100L);
        expected2.setAdminComment(RPCUnicodeString.NonNullTerminated.of(null));
        String hex3 = "00000000" + hex1;
        return new Object[][] {
                {hex1, 0, obj1, expected1},
                {hex2, 0, obj2, expected2},
                // Alignment
                {hex3, 1, obj1, expected1},
                {hex3, 2, obj1, expected1},
                {hex3, 3, obj1, expected1}
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark,
            SAMPRAliasGeneralInformation obj, SAMPRAliasGeneralInformation expected) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        obj.unmarshalDeferrals(in);
        assertEquals(obj, expected);
    }

    @Test
    public void test_hashCode() {
        SAMPRAliasGeneralInformation obj1 = new SAMPRAliasGeneralInformation();
        SAMPRAliasGeneralInformation obj2 = new SAMPRAliasGeneralInformation();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setName(RPCUnicodeString.NonNullTerminated.of("Name"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setName(RPCUnicodeString.NonNullTerminated.of("Name"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setMemberCount(100L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setMemberCount(100L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setAdminComment(RPCUnicodeString.NonNullTerminated.of("AdminComment"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setAdminComment(RPCUnicodeString.NonNullTerminated.of("AdminComment"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        SAMPRAliasGeneralInformation obj1 = new SAMPRAliasGeneralInformation();
        SAMPRAliasGeneralInformation obj2 = new SAMPRAliasGeneralInformation();
        assertEquals(obj1, obj2);
        obj1.setName(RPCUnicodeString.NonNullTerminated.of("Name"));
        assertNotEquals(obj1, obj2);
        obj2.setName(RPCUnicodeString.NonNullTerminated.of("Name"));
        assertEquals(obj1, obj2);
        obj1.setMemberCount(100L);
        assertNotEquals(obj1, obj2);
        obj2.setMemberCount(100L);
        assertEquals(obj1, obj2);
        obj1.setAdminComment(RPCUnicodeString.NonNullTerminated.of("AdminComment"));
        assertNotEquals(obj1, obj2);
        obj2.setAdminComment(RPCUnicodeString.NonNullTerminated.of("AdminComment"));
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_defaults() {
        assertEquals(new SAMPRAliasGeneralInformation().toString(), "SAMPR_ALIAS_GENERAL_INFORMATION{Name:null,MemberCount:0,AdminComment:null}");
    }

    @Test
    public void test_toString() {
        SAMPRAliasGeneralInformation obj = new SAMPRAliasGeneralInformation();
        obj.setName(RPCUnicodeString.NonNullTerminated.of("Name"));
        obj.setMemberCount(100L);
        obj.setAdminComment(RPCUnicodeString.NonNullTerminated.of("AdminComment"));
        assertEquals(obj.toString(), "SAMPR_ALIAS_GENERAL_INFORMATION{Name:\"Name\",MemberCount:100,AdminComment:\"AdminComment\"}");
    }
}
