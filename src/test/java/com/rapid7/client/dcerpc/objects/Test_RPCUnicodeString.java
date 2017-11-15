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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

public class Test_RPCUnicodeString {

    @Test
    public void test_of_NonNullTerminated() {
        RPCUnicodeString obj = RPCUnicodeString.NonNullTerminated.of("test123");
        assertEquals(obj.getValue(), "test123");
    }

    @Test
    public void test_of_NullTerminated() {
        RPCUnicodeString obj = RPCUnicodeString.NullTerminated.of("test123");
        assertEquals(obj.getValue(), "test123");
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
        RPCUnicodeString obj = create(nullTerminated);
        obj.setValue(value);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        obj.marshalPreamble(new PacketOutput(bout));
        assertEquals(bout.toByteArray(), new byte[0]);
    }

    @DataProvider
    public Object[][] data_marshal_entity() {
        return new Object[][] {
                {false, null, "", "0000000000000000"},
                {true, null, "", "0000000000000000"},
                {false, "testƟ123", "", "1000100000000200"},
                {true, "testƟ123", "", "1200120000000200"},
                // Alignment: 3
                {true, "testƟ123", "ff", "ff0000001200120000000200"},
                // Alignment: 2
                {true, "testƟ123", "ffff", "ffff00001200120000000200"},
                // Alignment: 1
                {true, "testƟ123", "ffffff", "ffffff001200120000000200"},
        };
    }

    @Test(dataProvider = "data_marshal_entity")
    public void test_marshal_entity(boolean nullTerminated, String value, String writeHex, String expectedHex) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.write(Hex.decode(writeHex));

        RPCUnicodeString obj = create(nullTerminated);
        obj.setValue(value);
        obj.marshalEntity(out);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectedHex);
    }

    @DataProvider
    public Object[][] data_marshal_deferrals() {
        return new Object[][] {
                {false, null, "", ""},
                {true, null, "", ""},
                // MaximumCount=8, Offset=0, ActualCount=8
                {false, "testƟ123", "", "08000000000000000800000074006500730074009f01310032003300"},
                // MaximumCount=9, Offset=0, ActualCount=9
                {true, "testƟ123", "", "09000000000000000900000074006500730074009f013100320033000000"},
                // Alignment: 3, MaximumCount=8, Offset=0, ActualCount=8
                {false, "testƟ123", "ff", "ff00000008000000000000000800000074006500730074009f01310032003300"},
                // Alignment: 2, MaximumCount=8, Offset=0, ActualCount=8
                {false, "testƟ123", "ffff", "ffff000008000000000000000800000074006500730074009f01310032003300"},
                // Alignment: 1, MaximumCount=8, Offset=0, ActualCount=8
                {false, "testƟ123", "ffffff", "ffffff0008000000000000000800000074006500730074009f01310032003300"},
        };
    }

    @Test(dataProvider = "data_marshal_deferrals")
    public void test_marshal_deferrals(boolean nullTerminated, String value, String writeHex, String expectedHex) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.write(Hex.decode(writeHex));

        RPCUnicodeString obj = create(nullTerminated);
        obj.setValue(value);
        obj.marshalDeferrals(out);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectedHex);
    }

    @Test(dataProvider = "data_marshal_preamble")
    public void test_unmarshal_preamble(boolean nullTerminated, String value) throws IOException {
        RPCUnicodeString obj = create(nullTerminated);
        obj.setValue(value);
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("1a2e"));
        obj.unmarshalPreamble(new PacketInput(bin));
        assertEquals(bin.available(), 2);
    }

    @DataProvider
    public Object[][] data_unmarshal_entity() {
        return new Object[][] {
                {false, "0000000000000000", 0, null},
                {true, "0000000000000000", 0, null},
                // We use an empty string as as placeholder to indicate the referent is non-null
                {false, "1000100000000200", 0, ""},
                {true, "1000100000000200", 0, ""},
                // Alignment: 3
                {true, "ffffffff1000100000000200", 1, ""},
                // Alignment: 2
                {true, "ffffffff1000100000000200", 2, ""},
                // Alignment: 1
                {true, "ffffffff1000100000000200", 3, ""}
        };
    }

    @Test(dataProvider = "data_unmarshal_entity")
    public void test_unmarshal_entity(boolean nullTerminated, String hex, int mark, String value) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        RPCUnicodeString obj = create(nullTerminated);
        obj.setValue(value);
        obj.unmarshalEntity(in);
        assertEquals(obj.isNullTerminated(), nullTerminated);
        assertEquals(obj.getValue(), value);
        assertEquals(bin.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshal_deferrals() {
        return new Object[][] {
                // not null terminated, no subset, MaximumCount=8, Offset=8, ActualCount=8
                {false, "08000000000000000800000074006500730074009f01310032003300", 0, "testƟ123"},
                // null terminated, no subset, MaximumCount=9, Offset=0, ActualCount=9
                {true, "09000000000000000900000074006500730074009f013100320033000000", 0, "testƟ123"},
                // not null terminated, lefthand subset MaximumCount=0, Offset=4, ActualCount=8
                {false, "000000000400000008000000740065007300740074006500730074009f01310032003300", 0, "testƟ123"},
                // null terminated, lefthand subset MaximumCount=0, Offset=4, ActualCount=9
                {true, "000000000400000009000000740065007300740074006500730074009f013100320033000000", 0, "testƟ123"},
                // Alignment=3, not null terminated, no subset, MaximumCount=8, Offset=8, ActualCount=8
                {false, "ffffffff08000000000000000800000074006500730074009f01310032003300", 1, "testƟ123"},
                // Alignment=2, not null terminated, no subset, MaximumCount=8, Offset=8, ActualCount=8
                {false, "ffffffff08000000000000000800000074006500730074009f01310032003300", 2, "testƟ123"},
                // Alignment=1, not null terminated, no subset, MaximumCount=8, Offset=8, ActualCount=8
                {false, "ffffffff08000000000000000800000074006500730074009f01310032003300", 3, "testƟ123"},

        };
    }

    @Test(dataProvider = "data_unmarshal_deferrals")
    public void test_unmarshal_deferrals(boolean nullTerminated, String hex, int mark, String value) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        RPCUnicodeString obj = create(nullTerminated);
        // Value must be non-null for deferrals to read the ref
        obj.setValue("");
        obj.unmarshalDeferrals(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.isNullTerminated(), nullTerminated);
        assertEquals(obj.getValue(), value);
    }

    @DataProvider
    public Object[][] data_unmarshal_deferrals_IndexTooLarge() {
        return new Object[][] {
                // MaximumCount=0, Offset=2147483648, ActualCount=8
                {"000000000000008008000000"},
                // MaximumCount=0, Offset=4, ActualCount=2147483648
                {"000000000400000000000080"},
        };
    }

    @Test(dataProvider = "data_unmarshal_deferrals_IndexTooLarge")
    public void test_unmarshal_deferrals_IndexTooLarge(String hex) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);

        RPCUnicodeString obj = new RPCUnicodeString.NullTerminated();
        // Value must be non-null for deferrals to read the ref
        obj.setValue("");
        IllegalArgumentException actual = null;
        try {
            obj.unmarshalDeferrals(in);
        } catch (IllegalArgumentException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "Value 2147483648 > 2147483647");
    }

    @Test
    public void test_hashCode_NotNullTerminated() {
        RPCUnicodeString obj1 = new RPCUnicodeString.NonNullTerminated();
        RPCUnicodeString obj2 = new RPCUnicodeString.NonNullTerminated();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setValue("testƟ123");
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setValue("testƟ123");
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_hashCode_NullTerminated() {

        RPCUnicodeString obj1 = new RPCUnicodeString.NullTerminated();
        RPCUnicodeString obj2 = new RPCUnicodeString.NullTerminated();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setValue("testƟ123");
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setValue("testƟ123");
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        RPCUnicodeString obj_nt1 = new RPCUnicodeString.NullTerminated();
        RPCUnicodeString obj_nt2 = new RPCUnicodeString.NullTerminated();
        RPCUnicodeString obj_ntn1 = new RPCUnicodeString.NonNullTerminated();
        RPCUnicodeString obj_ntn2 = new RPCUnicodeString.NonNullTerminated();
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
        RPCUnicodeString str = create(nullTerminated);
        str.setValue(value);
        assertEquals(str.toString(), expected);
    }

    private RPCUnicodeString create(boolean nullterminated) {
        return nullterminated ? new RPCUnicodeString.NullTerminated() : new RPCUnicodeString.NonNullTerminated();
    }
}
