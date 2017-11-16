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
import java.rmi.UnmarshalException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class Test_SAMPRSRSecurityDescriptor {

    @Test
    public void test_getters_default() {
        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();
        assertEquals(obj.getSecurityDescriptor(), null);
    }

    @Test
    public void test_setters() {
        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();
        byte[] securityDescriptor = new byte[5];
        obj.setSecurityDescriptor(securityDescriptor);
        assertSame(obj.getSecurityDescriptor(), securityDescriptor);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);

        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();
        obj.unmarshalPreamble(in);

        assertEquals(obj.getSecurityDescriptor(), null);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Length: 1, Reference: 0
                {"01000000 00000000", 0, null},
                // Length: 0, Reference: 2
                {"00000000 00000200", 0, null},

                // Length: 2, Reference: 2
                {"01000000 00000200", 0, new byte[1]},
                // Length: 2, Reference: 2
                {"02000000 00000200", 0, new byte[2]},

                // Alignment
                {"FFFFFFFF 02000000 00000200", 1, new byte[2]},
                {"FFFFFFFF 02000000 00000200", 2, new byte[2]},
                {"FFFFFFFF 02000000 00000200", 3, new byte[2]}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, byte[] expectedSecurityDescriptor) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();
        obj.unmarshalEntity(in);

        assertEquals(obj.getSecurityDescriptor(), expectedSecurityDescriptor);
        assertEquals(bin.available(), 0);
    }

    @Test
    public void test_unmarshalEntity_IndexTooLarge() throws IOException {
        // Length: 2147483648, Reference: 0
        String hex = "00000080 00000000";
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();

        UnmarshalException actual = null;
        try {
            obj.unmarshalEntity(in);
        } catch (UnmarshalException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "Length 2147483648 > 2147483647");
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // Null
                {"", 0, null, null},
                // MaximumCount: 1 SecurityDescriptor: {1, 2, -127}
                {"01000000 01 7F 80", 0, new byte[3], new byte[]{1, Byte.MAX_VALUE, Byte.MIN_VALUE}},

                // Alignment
                {"FFFFFFFF 01000000 01 7F 80", 1, new byte[3], new byte[]{1, Byte.MAX_VALUE, Byte.MIN_VALUE}},
                {"FFFFFFFF 01000000 01 7F 80", 2, new byte[3], new byte[]{1, Byte.MAX_VALUE, Byte.MIN_VALUE}},
                {"FFFFFFFF 01000000 01 7F 80", 3, new byte[3], new byte[]{1, Byte.MAX_VALUE, Byte.MIN_VALUE}},

        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, byte[] securityDescriptor, byte[] expectedSecurityDescriptor) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();
        obj.setSecurityDescriptor(securityDescriptor);
        obj.unmarshalDeferrals(in);

        assertEquals(obj.getSecurityDescriptor(), expectedSecurityDescriptor);
        assertEquals(bin.available(), 0);
    }

    @Test
    public void test_hashCode() {
        SAMPRSRSecurityDescriptor obj1 = new SAMPRSRSecurityDescriptor();
        SAMPRSRSecurityDescriptor obj2 = new SAMPRSRSecurityDescriptor();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setSecurityDescriptor(new byte[5]);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setSecurityDescriptor(new byte[5]);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        SAMPRSRSecurityDescriptor obj1 = new SAMPRSRSecurityDescriptor();
        SAMPRSRSecurityDescriptor obj2 = new SAMPRSRSecurityDescriptor();
        assertEquals(obj1, obj2);
        obj1.setSecurityDescriptor(new byte[5]);
        assertNotEquals(obj1, obj2);
        obj2.setSecurityDescriptor(new byte[5]);
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_defaults() {
        assertEquals(new SAMPRSRSecurityDescriptor().toString(), "SAMPR_SR_SECURITY_DESCRIPTOR{size_of(SecurityDescriptor):null}");
    }

    @Test
    public void test_toString() {
        SAMPRSRSecurityDescriptor obj = new SAMPRSRSecurityDescriptor();
        obj.setSecurityDescriptor(new byte[]{1, 2, 3});
        assertEquals(obj.toString(), "SAMPR_SR_SECURITY_DESCRIPTOR{size_of(SecurityDescriptor):3}");
    }
}
