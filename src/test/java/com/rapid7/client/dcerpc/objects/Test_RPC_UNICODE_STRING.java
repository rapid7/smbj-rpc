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
package com.rapid7.client.dcerpc.objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

public class Test_RPC_UNICODE_STRING {

    @DataProvider
    public Object[][] data_of_null() {
        return new Object[][] {
                {false, RPC_UNICODE_STRING.NotNullTerminated.class},
                {true, RPC_UNICODE_STRING.NullTerminated.class}
        };
    }

    @Test(dataProvider = "data_of_null")
    public void test_of_null(boolean nullTerminated, Class<?> clazz) {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated);
        assertEquals(obj.getClass(), clazz);
        assertEquals(obj.isNullTerminated(), nullTerminated);
        assertNull(obj.getValue());
        assertEquals(obj.getAlignment(), Alignment.FOUR);
    }

    @DataProvider
    public Object[][] data_of() {
        return new Object[][] {
                {false, null, RPC_UNICODE_STRING.NotNullTerminated.class},
                {false, "test123", RPC_UNICODE_STRING.NotNullTerminated.class},
                {true, null, RPC_UNICODE_STRING.NullTerminated.class},
                {true, "test123", RPC_UNICODE_STRING.NullTerminated.class},
        };
    }

    @Test(dataProvider = "data_of")
    public void test_of(boolean nullTerminated, String value, Class<?> clazz) {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated, value);
        assertEquals(obj.getClass(), clazz);
        assertEquals(obj.isNullTerminated(), nullTerminated);
        assertEquals(obj.getValue(), value);
        assertEquals(obj.getAlignment(), Alignment.FOUR);
    }

    @DataProvider
    public Object[][] data_marshal_preamble() {
        return new Object[][] {
                {false, null},
                {false, "test123"},
                {true, null},
                {true, "test123"},
        };
    }

    @Test(dataProvider = "data_marshal_preamble")
    public void test_marshal_preamble(boolean nullTerminated, String value) throws IOException {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated, value);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.marshalPreamble(new PacketOutput(bout));
        assertEquals(bout.toByteArray(), new byte[0]);
    }

    @DataProvider
    public Object[][] data_marshal_entity() {
        return new Object[][] {
                {false, null, "0000000000000000"},
                {true, null, "0000000000000000"},
                {false, "testƟ123", "1000100000000200"},
                {true, "testƟ123", "1200120000000200"},
        };
    }

    @Test(dataProvider = "data_marshal_entity")
    public void test_marshal_entity(boolean nullTerminated, String value, String expectedHex) throws IOException {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated, value);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.marshalEntity(new PacketOutput(bout));
        assertEquals(Hex.toHexString(bout.toByteArray()), expectedHex);
    }

    @DataProvider
    public Object[][] data_marshal_deferrals() {
        return new Object[][] {
                {false, null, ""},
                {true, null, ""},
                // MaximumCount=8, Offset=0, ActualCount=8, Alignment=0
                {false, "testƟ123", "08000000000000000800000074006500730074009f01310032003300"},
                // MaximumCount=9, Offset=0, ActualCount=9, Alignment=2b
                {true, "testƟ123", "09000000000000000900000074006500730074009f0131003200330000000000"},
        };
    }

    @Test(dataProvider = "data_marshal_deferrals")
    public void test_marshal_deferrals(boolean nullTerminated, String value, String expectedHex) throws IOException {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated, value);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.marshalDeferrals(new PacketOutput(bout));
        assertEquals(Hex.toHexString(bout.toByteArray()), expectedHex);
    }

    @Test(dataProvider = "data_marshal_preamble")
    public void test_unmarshal_preamble(boolean nullTerminated, String value) throws IOException {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated, value);
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("1a2e"));
        obj.unmarshalPreamble(new PacketInput(bin));
        assertEquals(bin.available(), 2);
    }

    @DataProvider
    public Object[][] data_unmarshal_entity() {
        return new Object[][] {
                {false, "0000000000000000", null},
                {true, "0000000000000000", null},
                // We use an empty string as as placeholder to indicate the referent is non-null
                {false, "1000100000000200", ""},
                {true, "1000100000000200", ""}
        };
    }

    @Test(dataProvider = "data_unmarshal_entity")
    public void test_unmarshal_entity(boolean nullTerminated, String hex, String value) throws IOException {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated);
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        obj.unmarshalEntity(in);
        assertEquals(obj.isNullTerminated(), nullTerminated);
        assertEquals(obj.getValue(), value);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshal_deferrals() {
        return new Object[][] {
                // not null terminated, no subset, MaximumCount=8, Offset=8, ActualCount=8, Alignment=0b
                {false, "08000000000000000800000074006500730074009f01310032003300", "testƟ123"},
                // null terminated, no subset, MaximumCount=9, Offset=0, ActualCount=9, Alignment=2b
                {true, "09000000000000000900000074006500730074009f0131003200330000000000", "testƟ123"},
                // not null terminated, lefthand subset MaximumCount=0, Offset=4, ActualCount=8, Alignment=0b(testtestƟ123)
                {false, "000000000400000008000000740065007300740074006500730074009f01310032003300", "testƟ123"},
                // null terminated, lefthand subset MaximumCount=0, Offset=4, ActualCount=9, Alignment=2b (testtestƟ123)
                {true, "000000000400000009000000740065007300740074006500730074009f0131003200330000000000", "testƟ123"},
        };
    }

    @Test(dataProvider = "data_unmarshal_deferrals")
    public void test_unmarshal_deferrals(boolean nullTerminated, String hex, String value) throws IOException {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(nullTerminated);
        // Value must be non-null for deferrals to read the ref
        obj.setValue("");
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        obj.unmarshalDeferrals(in);
        assertEquals(obj.isNullTerminated(), nullTerminated);
        assertEquals(obj.getValue(), value);
        assertEquals(bin.available(), 0);
    }


    @Test
    public void test_hashCode_NotNullTerminated() {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(false);
        assertEquals(obj.hashCode(), 39308);
        obj.setValue("testƟ123");
        assertEquals(obj.hashCode(), -1136940719);
    }

    @Test
    public void test_hashCode_NullTerminated() {
        RPC_UNICODE_STRING obj = RPC_UNICODE_STRING.of(true);
        assertEquals(obj.hashCode(), 39122);
        obj.setValue("testƟ123");
        assertEquals(obj.hashCode(), -1136940905);
    }

    @Test
    public void test_equals() {
        RPC_UNICODE_STRING obj_nt1 = RPC_UNICODE_STRING.of(true);
        RPC_UNICODE_STRING obj_nt2 = RPC_UNICODE_STRING.of(true);
        RPC_UNICODE_STRING obj_ntn1 = RPC_UNICODE_STRING.of(false);
        RPC_UNICODE_STRING obj_ntn2 = RPC_UNICODE_STRING.of(false);
        assertEquals(obj_nt1, obj_nt2);
        assertEquals(obj_ntn1, obj_ntn2);
        assertNotEquals(obj_nt1, obj_ntn1);
        obj_nt2.setValue("test123");
        obj_ntn2.setValue("test123");
        assertNotEquals(obj_nt1, obj_nt2);
        assertNotEquals(obj_ntn1, obj_ntn2);
        obj_nt1.setValue("test123");
        obj_ntn1.setValue("test123");
        assertEquals(obj_nt1, obj_nt2);
        assertEquals(obj_ntn1, obj_ntn2);
    }

    @DataProvider
    public Object[][] data_toString() {
        return new Object[][] {
                {false, "test", "RPC_UNICODE_STRING{value:\"test\", nullTerminated:false}"},
                {true, "test", "RPC_UNICODE_STRING{value:\"test\", nullTerminated:true}"},
                {true, null, "RPC_UNICODE_STRING{value:null, nullTerminated:true}"}
        };
    }

    @Test(dataProvider = "data_toString")
    public void test_toString(boolean nullTerminated, String value, String expected) {
        RPC_UNICODE_STRING str = RPC_UNICODE_STRING.of(nullTerminated, value);
        assertEquals(str.toString(), expected);
    }
}
