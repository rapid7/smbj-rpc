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

import static org.junit.Assert.assertArrayEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.MarshalException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

public class Test_RPCSID {

    @Test(expectedExceptions = {NullPointerException.class})
    public void test_marshalPreamble_null() throws IOException {
        new RPCSID().marshalPreamble(new PacketOutput(new ByteArrayOutputStream()));
    }

    @DataProvider
    public Object[][] data_marshalPreamble() {
        return new Object[][] {
                // Alignment: 0
                {"", "02000000"},
                // Alignment: 3
                {"ff", "ff00000002000000"},
                // Alignment: 2
                {"ffff", "ffff000002000000"},
                // Alignment: 1
                {"ffffff", "ffffff0002000000"},
        };
    }

    @Test(dataProvider = "data_marshalPreamble")
    public void test_marshalPreamble(String writeHex, String expectHex) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.write(Hex.decode(writeHex));

        RPCSID rpc_sid = new RPCSID();
        rpc_sid.setSubAuthority(new long[]{5, 10});
        rpc_sid.marshalPreamble(out);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectHex);
    }

    @Test(expectedExceptions = {NullPointerException.class})
    public void test_marshalEntity_null() throws IOException {
        new RPCSID().marshalEntity(new PacketOutput(new ByteArrayOutputStream()));
    }

    @DataProvider
    public Object[][] data_marshalEntity() {
        return new Object[][] {
                // Alignment: 0
                {"", "1902010203040506050000000a000000"},
                // Alignment: 1
                {"ff", "ff0000001902010203040506050000000a000000"},
                // Alignment: 2
                {"ffff", "ffff00001902010203040506050000000a000000"},
                // Alignment: 3
                {"ffffff", "ffffff001902010203040506050000000a000000"}
        };
    }

    @Test(dataProvider = "data_marshalEntity")
    public void test_marshalEntity(String writeHex, String expectHex) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PacketOutput out = new PacketOutput(bout);
        out.write(Hex.decode(writeHex));

        RPCSID rpc_sid = new RPCSID();
        rpc_sid.setRevision((char) 25);
        rpc_sid.setSubAuthorityCount((char) 2);
        rpc_sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
        rpc_sid.setSubAuthority(new long[]{5, 10});
        rpc_sid.marshalEntity(out);
        assertEquals(Hex.toHexString(bout.toByteArray()), expectHex);
    }

    @Test
    public void test_marshalEntity_SubAuthorityCountInvalid() throws IOException {
        RPCSID rpc_sid = new RPCSID();
        rpc_sid.setRevision((char) 25);
        rpc_sid.setSubAuthorityCount((char) 30);
        rpc_sid.setIdentifierAuthority(new byte[]{1, 2, 3, 4, 5, 6});
        rpc_sid.setSubAuthority(new long[]{5, 10});
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MarshalException actual = null;
        try {
            rpc_sid.marshalEntity(new PacketOutput(outputStream));
        } catch (MarshalException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "SubAuthorityCount (30) != SubAuthority[] length (2)");
    }

    @Test
    public void test_marshalDeferrals() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        new RPCSID().marshalDeferrals(new PacketOutput(outputStream));
        assertEquals(outputStream.toByteArray(), new byte[0]);
    }

    @DataProvider
    public Object[][] data_unmarshalPremable() {
        return new Object[][] {
                {"02000000", 0},
                {"ffffffff02000000", 1},
                {"ffffffff02000000", 2},
                {"ffffffff02000000", 3}
        };
    }

    @Test(dataProvider = "data_unmarshalPremable")
    public void test_unmarshalPremable(String hex, int mark) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        RPCSID rpc_sid = new RPCSID();
        rpc_sid.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertEquals(rpc_sid.getSubAuthority(), null);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Alignment: 0
                {"190201020304050605000000000000a0", 0},
                // Alignment: 3
                {"ffffffff190201020304050605000000000000a0", 1},
                // Alignment: 2
                {"ffffffff190201020304050605000000000000a0", 2},
                // Alignment: 1
                {"ffffffff190201020304050605000000000000a0", 3},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);

        RPCSID rpc_sid = new RPCSID();
        rpc_sid.setSubAuthority(new long[2]);
        rpc_sid.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(rpc_sid.getRevision(), (char) 25);
        assertEquals(rpc_sid.getSubAuthorityCount(), (char) 2);
        assertEquals(rpc_sid.getIdentifierAuthority(), new byte[]{1, 2, 3, 4, 5, 6});
        assertEquals(rpc_sid.getSubAuthority(), new long[]{5L, 2684354560L});
    }

    @Test
    public void test_unmarshalDeferrals() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("191e"));
        assertEquals(bin.available(), 2);
        new RPCSID().unmarshalDeferrals(new PacketInput(bin));
        assertEquals(bin.available(), 2);
    }

    @Test
    public void test_getters() {
        RPCSID rpc_sid = new RPCSID();
        assertEquals(rpc_sid.getRevision(), 0);
        assertEquals(rpc_sid.getSubAuthorityCount(), 0);
        assertNull(rpc_sid.getIdentifierAuthority());
        assertNull(rpc_sid.getSubAuthority());
    }

    @Test
    public void test_setters() {
        RPCSID rpc_sid = new RPCSID();
        rpc_sid.setRevision((char) 200);
        rpc_sid.setSubAuthorityCount((char) 5);
        byte[] identifierAuthority = new byte[]{1, 2};
        rpc_sid.setIdentifierAuthority(identifierAuthority);
        long[] subAuthority = new long[]{2, 5, 7};
        rpc_sid.setSubAuthority(subAuthority);
        assertEquals(rpc_sid.getRevision(), (char) 200);
        assertEquals(rpc_sid.getSubAuthorityCount(), (char) 5);
        assertSame(rpc_sid.getIdentifierAuthority(), identifierAuthority);
        assertSame(rpc_sid.getSubAuthority(), subAuthority);
    }

    @Test
    public void test_hashCode() {
        RPCSID rpc_sid1 = new RPCSID();
        RPCSID rpc_sid2 = new RPCSID();
        assertEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid1.setRevision((char) 200);
        assertNotEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid2.setRevision((char) 200);
        assertEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid1.setSubAuthorityCount((char) 5);
        assertNotEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid2.setSubAuthorityCount((char) 5);
        assertEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid1.setIdentifierAuthority(new byte[]{1, 2});
        assertNotEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid2.setIdentifierAuthority(new byte[]{1, 2});
        assertEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid1.setSubAuthority(new long[]{2, 5, 7});
        assertNotEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
        rpc_sid2.setSubAuthority(new long[]{2, 5, 7});
        assertEquals(rpc_sid1.hashCode(), rpc_sid2.hashCode());
    }

    @Test
    public void test_equals() {
        RPCSID sid1 = new RPCSID();
        RPCSID sid2 = new RPCSID();
        assertEquals(sid1, sid2);
        sid1.setRevision((char) 200);
        assertNotEquals(sid1, sid2);
        sid2.setRevision((char) 200);
        assertEquals(sid1, sid2);
        sid1.setSubAuthorityCount((char) 2);
        assertNotEquals(sid1, sid2);
        sid2.setSubAuthorityCount((char) 2);
        assertEquals(sid1, sid2);
        sid1.setIdentifierAuthority(new byte[]{1, 2});
        assertNotEquals(sid1, sid2);
        sid2.setIdentifierAuthority(new byte[]{1, 2});
        assertEquals(sid1, sid2);
        sid1.setSubAuthority(new long[]{4, 5, 6});
        assertNotEquals(sid1, sid2);
        sid2.setSubAuthority(new long[]{4, 5, 6});
        assertEquals(sid1, sid2);
    }

    @Test
    public void test_toString() {
        RPCSID rpc_sid = new RPCSID();
        assertEquals(rpc_sid.toString(), "RPC_SID{Revision:0, SubAuthorityCount:0, IdentifierAuthority:null, SubAuthority: null}");
        rpc_sid.setRevision((char) 200);
        rpc_sid.setSubAuthorityCount((char) 5);
        rpc_sid.setIdentifierAuthority(new byte[]{1, 2});
        rpc_sid.setSubAuthority(new long[]{2, 5, 7});
        assertEquals(rpc_sid.toString(), "RPC_SID{Revision:200, SubAuthorityCount:5, IdentifierAuthority:[1, 2], SubAuthority: [2, 5, 7]}");
    }

    @Test
    public void test_fromString() throws MalformedSIDException {
        RPCSID rpc_sid = RPCSID.fromString("S-1-5-32");
        assertEquals(rpc_sid.getRevision(), 1);
        assertEquals(rpc_sid.getSubAuthorityCount(), 1);
        assertArrayEquals(rpc_sid.getIdentifierAuthority(), new byte[] { 0, 0, 0, 0, 0, 5 });
        assertArrayEquals(rpc_sid.getSubAuthority(), new long[] { 32 });
        rpc_sid = RPCSID.fromString("S-1-5-333-444-5");
        assertEquals(rpc_sid.getRevision(), 1);
        assertEquals(rpc_sid.getSubAuthorityCount(), 3);
        assertArrayEquals(rpc_sid.getIdentifierAuthority(), new byte[] { 0, 0, 0, 0, 0, 5 });
        assertArrayEquals(rpc_sid.getSubAuthority(), new long[] { 333, 444, 5 });
    }

    @Test(expectedExceptions = { MalformedSIDException.class })
    public void test_fromStringMalformed() throws MalformedSIDException {
        RPCSID.fromString("MALFORMED");
    }
}
