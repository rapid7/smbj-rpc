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
package com.rapid7.client.dcerpc.mslsad.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

public class Test_LSAPR_POLICY_AUDIT_EVENTS_INFO {
    @Test
    public void test_getters_default() {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        assertEquals(obj.getAlignment(), Alignment.FOUR);
        assertFalse(obj.isAuditingMode());
        assertNull(obj.getEventAuditingOptions());
        assertEquals(obj.getMaximumAuditEventCount(), 0);
    }

    @Test
    public void test_setters() {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        obj.setAuditingMode(true);
        int[] eventAuditingOptions = new int[]{1, 2, 3};
        obj.setEventAuditingOptions(eventAuditingOptions);
        obj.setMaximumAuditEventCount(5);
        assertTrue(obj.isAuditingMode());
        assertSame(obj.getEventAuditingOptions(), eventAuditingOptions);
        assertEquals(obj.getMaximumAuditEventCount(), 5);
    }

    @Test
    public void test_hashCode() {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        assertEquals(obj.hashCode(), 1188757);
        obj.setAuditingMode(true);
        assertEquals(obj.hashCode(), 1182991);
        obj.setEventAuditingOptions(new int[]{1, 2, 3});
        assertEquals(obj.hashCode(), 2138318);
        obj.setMaximumAuditEventCount(5);
        assertEquals(obj.hashCode(), 2138323);
    }

    @Test
    public void test_equals() {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj1 = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        assertNotEquals(obj1, null);
        assertEquals(obj1, obj1);
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj2 = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        assertEquals(obj1, obj2);
        obj1.setAuditingMode(true);
        assertNotEquals(obj1, obj2);
        obj2.setAuditingMode(true);
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
    public void test_unmarhsalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode("0102"));
        new LSAPR_POLICY_AUDIT_EVENTS_INFO().unmarshalPreamble(new PacketInput(bin));
        assertEquals(bin.available(), 2);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // isAuditingEnabled=false, alignment=3b, referentId=2, MaximumAuditEventCount=3
                {"00 777777 02000000 03000000",
                    false, new int[]{0, 0, 0}, 3},
                // isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=3
                {"01 777777 00000000 00000000",
                        true, null, 0}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarsalEntity(String hex, boolean isAuditingEnabled, int[] eventAuditingOptions, int maximumAuditEventCount) throws IOException {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        obj.unmarshalEntity(new PacketInput(new ByteArrayInputStream(Hex.decode(hex))));
        assertEquals(obj.isAuditingMode(), isAuditingEnabled);
        assertEquals(obj.getEventAuditingOptions(), eventAuditingOptions);
        assertEquals(obj.getMaximumAuditEventCount(), maximumAuditEventCount);
    }

    @Test
    public void test_unmarshallEntity_InvalidMaximumAuditEventCount() throws IOException {
        // isAuditingEnabled=true, alignment=3b, referentId=0, MaximumAuditEventCount=1
        String hex = "01 777777 00000000 01000000";
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
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
                    3, new int[3], new int[]{1, 2, 3}},
                // MaximumCount=3 EventAuditingOptions={1, 2, 3}
                {"03000000 01000000 02000000 03000000",
                        2, new int[2], new int[]{1, 2}},
                // Empty
                {"", 3, null, null},
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int maximumAuditEventCount, int[] eventAuditingOptions, int[] expectedEventAuditingOptions) throws IOException {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        obj.setAuditingMode(true);
        obj.setMaximumAuditEventCount(maximumAuditEventCount);
        obj.setEventAuditingOptions(eventAuditingOptions);
        obj.unmarshalDeferrals(new PacketInput(new ByteArrayInputStream(Hex.decode(hex))));
        assertTrue(obj.isAuditingMode());
        assertEquals(obj.getEventAuditingOptions(), expectedEventAuditingOptions);
        assertEquals(obj.getMaximumAuditEventCount(), maximumAuditEventCount);
    }

    @Test
    public void test_ummarshalDeferrals_InvalidMaximumCount() throws IOException {
        // MaximumCount=4
        String hex = "04000000";
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
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
    public void test_toString_default() {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        assertEquals(obj.toString(), "LSAPR_POLICY_AUDIT_EVENTS_INFO{AuditingMode:false, EventAuditingOptions:null, MaximumAuditEventCount:0}");
    }

    @Test
    public void test_toString() {
        LSAPR_POLICY_AUDIT_EVENTS_INFO obj = new LSAPR_POLICY_AUDIT_EVENTS_INFO();
        obj.setAuditingMode(true);
        obj.setEventAuditingOptions(new int[]{1, 2, 3});
        obj.setMaximumAuditEventCount(5);
        assertEquals(obj.toString(), "LSAPR_POLICY_AUDIT_EVENTS_INFO{AuditingMode:true, EventAuditingOptions:[1, 2, 3], MaximumAuditEventCount:5}");
    }
}
