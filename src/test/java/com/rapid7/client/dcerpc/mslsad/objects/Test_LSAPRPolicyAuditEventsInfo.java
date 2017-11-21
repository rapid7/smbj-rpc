/*
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
package com.rapid7.client.dcerpc.mslsad.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class Test_LSAPRPolicyAuditEventsInfo {
    @Test
    public void test_getters_default() {
        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        assertEquals(obj.getAuditingMode(), 0);
        assertNull(obj.getEventAuditingOptions());
        assertEquals(obj.getMaximumAuditEventCount(), 0);
    }

    @Test
    public void test_setters() {
        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        obj.setAuditingMode((char) 1);
        int[] eventAuditingOptions = new int[]{1, 2, 3};
        obj.setEventAuditingOptions(eventAuditingOptions);
        obj.setMaximumAuditEventCount(5);
        assertEquals(obj.getAuditingMode(), 1);
        assertSame(obj.getEventAuditingOptions(), eventAuditingOptions);
        assertEquals(obj.getMaximumAuditEventCount(), 5);
    }

    @Test
    public void test_unmarhsalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("0102"));
        new LSAPRPolicyAuditEventsInfo().unmarshalPreamble(new PacketInput(bin));
        assertEquals(bin.available(), 2);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // isAuditingEnabled=false, alignment=3b, referentId=2, MaximumAuditEventCount=3
                {"00 777777 02000000 03000000",
                        0, (char) 0, new int[]{0, 0, 0}, 3},
                // isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=3
                {"01 777777 00000000 00000000",
                        0, (char) 1, null, 0},
                // alignment: 3, isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=3
                {"ffffffff 01 777777 00000000 00000000",
                        1, (char) 1, null, 0},
                // alignment: 2, isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=3
                {"ffffffff 01 777777 00000000 00000000",
                        2, (char) 1, null, 0},
                // alignment: 1, isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=3
                {"ffffffff 01 777777 00000000 00000000",
                        3, (char) 1, null, 0}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarsalEntity(String hex, int mark, char auditingMode, int[] eventAuditingOptions, int maximumAuditEventCount) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.readFully(new byte[mark]);

        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        obj.unmarshalEntity(in);
        assertEquals(obj.getAuditingMode(), auditingMode);
        assertEquals(obj.getEventAuditingOptions(), eventAuditingOptions);
        assertEquals(obj.getMaximumAuditEventCount(), maximumAuditEventCount);
    }

    @Test
    public void test_unmarshallEntity_InvalidMaximumAuditEventCount() throws IOException {
        // isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=1
        String hex = "01 777777 00000000 01000000";
        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        IllegalArgumentException actual = null;
        try {
            obj.unmarshalEntity(new PacketInput(new ByteArrayInputStream(Hex.decode(hex))));
        } catch (IllegalArgumentException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "If the MaximumAuditingEventCount field has a value other than 0, EventAuditingOptions MUST NOT be NULL.");
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // MaximumCount=3 EventAuditingOptions={1, 2, 3}
                {"03000000 01000000 02000000 03000000",
                        0, 3, new int[3], new int[]{1, 2, 3}},
                // MaximumCount=3 EventAuditingOptions={1, 2, 3}
                {"03000000 01000000 02000000 03000000",
                        0, 2, new int[2], new int[]{1, 2}},
                // Alignment=3 MaximumCount=3 EventAuditingOptions={1, 2, 3}
                {"ffffffff 03000000 01000000 02000000 03000000",
                        1, 2, new int[2], new int[]{1, 2}},
                // Alignment=2 MaximumCount=3 EventAuditingOptions={1, 2, 3}
                {"ffffffff 03000000 01000000 02000000 03000000",
                        2, 2, new int[2], new int[]{1, 2}},
                // Alignment=1 MaximumCount=3 EventAuditingOptions={1, 2, 3}
                {"ffffffff 03000000 01000000 02000000 03000000",
                        3, 2, new int[2], new int[]{1, 2}},
                // Empty
                {"", 0, 3, null, null},
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, int maximumAuditEventCount, int[] eventAuditingOptions, int[] expectedEventAuditingOptions) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.readFully(new byte[mark]);

        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        obj.setAuditingMode((char) 2);
        obj.setMaximumAuditEventCount(maximumAuditEventCount);
        obj.setEventAuditingOptions(eventAuditingOptions);
        obj.unmarshalDeferrals(in);
        assertEquals(obj.getAuditingMode(), (char) 2);
        assertEquals(obj.getEventAuditingOptions(), expectedEventAuditingOptions);
        assertEquals(obj.getMaximumAuditEventCount(), maximumAuditEventCount);
    }

    @Test
    public void test_ummarshalDeferrals_InvalidMaximumCount() throws IOException {
        // MaximumCount=4
        String hex = "04000000";
        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        obj.setMaximumAuditEventCount(5);
        obj.setEventAuditingOptions(new int[3]);
        IllegalArgumentException actual = null;
        try {
            obj.unmarshalDeferrals(new PacketInput(new ByteArrayInputStream(Hex.decode(hex))));
        } catch (IllegalArgumentException e) {
            actual = e;
        }
        assertNotNull(actual);
        assertEquals(actual.getMessage(), "Expected MaximumAuditingEventCount (4) >= EventAuditingOptions.MaximumCount (5)");
    }

    @Test
    public void test_hashCode() {
        LSAPRPolicyAuditEventsInfo obj1 = new LSAPRPolicyAuditEventsInfo();
        LSAPRPolicyAuditEventsInfo obj2 = new LSAPRPolicyAuditEventsInfo();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setAuditingMode((char) 2);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setAuditingMode((char) 2);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setEventAuditingOptions(new int[]{1, 2, 3});
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setEventAuditingOptions(new int[]{1, 2, 3});
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setMaximumAuditEventCount(5);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setMaximumAuditEventCount(5);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        LSAPRPolicyAuditEventsInfo obj1 = new LSAPRPolicyAuditEventsInfo();
        assertNotEquals(obj1, null);
        assertEquals(obj1, obj1);
        LSAPRPolicyAuditEventsInfo obj2 = new LSAPRPolicyAuditEventsInfo();
        assertEquals(obj1, obj2);
        obj1.setAuditingMode((char) 2);
        assertNotEquals(obj1, obj2);
        obj2.setAuditingMode((char) 2);
        assertEquals(obj1, obj2);
        obj1.setEventAuditingOptions(new int[]{1, 2, 3});
        assertNotEquals(obj1, obj2);
        obj2.setEventAuditingOptions(new int[]{1, 2, 3});
        assertEquals(obj1, obj2);
        obj1.setMaximumAuditEventCount(5);
        assertNotEquals(obj1, obj2);
        obj2.setMaximumAuditEventCount(5);
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_default() {
        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        assertEquals(obj.toString(), "LSAPR_POLICY_AUDIT_EVENTS_INFO{AuditingMode:0, EventAuditingOptions:null, MaximumAuditEventCount:0}");
    }

    @Test
    public void test_toString() {
        LSAPRPolicyAuditEventsInfo obj = new LSAPRPolicyAuditEventsInfo();
        obj.setAuditingMode((char) 2);
        obj.setEventAuditingOptions(new int[]{1, 2, 3});
        obj.setMaximumAuditEventCount(5);
        assertEquals(obj.toString(), "LSAPR_POLICY_AUDIT_EVENTS_INFO{AuditingMode:2, EventAuditingOptions:[1, 2, 3], MaximumAuditEventCount:5}");
    }
}
