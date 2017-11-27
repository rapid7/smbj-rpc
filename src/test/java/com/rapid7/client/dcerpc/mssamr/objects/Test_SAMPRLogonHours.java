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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_SAMPRLogonHours {

    @Test
    public void test_getters_default() {
        SAMPRLogonHours obj = new SAMPRLogonHours();
        assertEquals(obj.getUnitsPerWeek(), (short) 0);
        assertEquals(obj.getLogonHours(), null);
    }

    @Test
    public void test_setters() {
        SAMPRLogonHours obj = new SAMPRLogonHours();
        obj.setUnitsPerWeek((short) 50);
        byte[] logonHours = new byte[]{1, 2, 3};
        obj.setLogonHours(logonHours);
        assertEquals(obj.getUnitsPerWeek(), (short) 50);
        assertSame(obj.getLogonHours(), logonHours);
    }


    @Test
    public void test_hashCode() {
        SAMPRLogonHours obj1 = new SAMPRLogonHours();
        SAMPRLogonHours obj2 = new SAMPRLogonHours();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setUnitsPerWeek((short) 50);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setUnitsPerWeek((short) 50);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLogonHours(new byte[]{1, 2, 3});
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLogonHours(new byte[]{1, 2, 3});
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        SAMPRLogonHours obj1 = new SAMPRLogonHours();
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test 123");
        SAMPRLogonHours obj2 = new SAMPRLogonHours();
        assertEquals(obj1, obj2);
        obj1.setUnitsPerWeek((short) 50);
        assertNotEquals(obj1, obj2);
        obj2.setUnitsPerWeek((short) 50);
        assertEquals(obj1, obj2);
        obj1.setLogonHours(new byte[]{1, 2, 3});
        assertNotEquals(obj1, obj2);
        obj2.setLogonHours(new byte[]{1, 2, 3});
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bout = new ByteArrayInputStream("".getBytes());
        SAMPRLogonHours obj = new SAMPRLogonHours();
        obj.unmarshalPreamble(new PacketInput(bout));
        assertEquals(obj.getUnitsPerWeek(), (short) 0);
        assertEquals(obj.getLogonHours(), null);
        assertEquals(bout.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        return new Object[][] {
                // Expected UnitsPerWeek
                // Weeks -> UnitsPerWeek: 1, Alignment: 2b, LogonHoursRef: 1
                {"0100 FFFF 01000000", 0, (short) 1, new char[1]},
                // Days -> UnitsPerWeek: 7, Alignment: 2b, LogonHoursRef: 1
                {"0700 FFFF 01000000", 0, (short) 7, new char[1]},
                // Hours -> UnitsPerWeek: 168, Alignment: 2b, LogonHoursRef: 1
                {"A800 FFFF 01000000", 0, (short) 168, new char[21]},
                // Hours -> UnitsPerWeek: 168, Alignment: 2b, LogonHoursRef: 1
                {"6027 FFFF 01000000", 0, (short) 10080, new char[1260]},

                // Null LogonHours
                // UnitsPerweek: 0, Alignment: 2b, LogonHoursRef: 0
                {"0000 FFFF 00000000", 0, (short) 0, null},

                // Unexpected UnitsPerWeek
                // UnitsPerWeek: 0, Alignment: 2b, LogonHoursRef: 1
                {"0000 FFFF 01000000", 0, (short) 0, new char[0]},
                // UnitsPerWeek: 8, Alignment: 2b, LogonHoursRef: 1
                {"0800 FFFF 01000000", 0, (short) 8, new char[1]},
                // UnitsPerWeek: 167, Alignment: 2b, LogonHoursRef: 1
                {"A900 FFFF 01000000", 0, (short) 169, new char[22]},
                // UnitsPerWeek: 167, Alignment: 2b, LogonHoursRef: 1
                {"6127 FFFF 01000000", 0, (short) 10081, new char[1261]},

                // Alignments
                // Days -> UnitsPerWeek: 7, Alignment: 2b, LogonHoursRef: 1
                {"ffffffff 0700 FFFF 01000000", 1, (short) 7, new char[1]},
                {"ffffffff 0700 FFFF 01000000", 2, (short) 7, new char[1]},
                {"ffffffff 0700 FFFF 01000000", 3, (short) 7, new char[1]},
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, short expectedUnitsPerWeek, char[] expectedLogonHours) throws IOException {
        ByteArrayInputStream bout = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bout);
        in.fullySkipBytes(mark);
        SAMPRLogonHours obj = new SAMPRLogonHours();
        obj.unmarshalEntity(in);
        assertEquals(obj.getUnitsPerWeek(), expectedUnitsPerWeek);
        // Short-circuit some of the equality here - It's expensive for large arrays
        if (expectedLogonHours != null) {
            assertEquals(obj.getLogonHours().length, expectedLogonHours.length);
            if (expectedLogonHours.length > 0)
                // Just assume the rest of the array is initialized
                assertEquals(obj.getLogonHours()[0], (short) 0);
        } else {
            assertNull(obj.getLogonHours());
        }
        assertEquals(bout.available(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        return new Object[][] {
                // Null
                {"", 0, null, null},

                // MaximumCount: 1, Offset: 2, ActualCount: 3
                {"01000000 02000000 03000000", 0, new byte[0], new byte[0]},
                // MaximumCount: 0, Offset: 0, ActualCount: 0, LogonHours: {1, 2, 3}
                {"00000000 00000000 00000000 01 02 FF", 0, new byte[3], new byte[]{1, 2, -1}},

                // Alignments
                // MaximumCount: 1, Offset: 2, ActualCount: 3
                {"00000000 01000000 02000000 03000000", 1, new byte[0], new byte[0]},
                // MaximumCount: 1, Offset: 2, ActualCount: 3
                {"00000000 01000000 02000000 03000000", 2, new byte[0], new byte[0]},
                // MaximumCount: 1, Offset: 2, ActualCount: 3
                {"00000000 01000000 02000000 03000000", 3, new byte[0], new byte[0]}
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, byte[] logonHours, byte[] expectedLogonHours) throws IOException {
        ByteArrayInputStream bout = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bout);
        in.fullySkipBytes(mark);
        SAMPRLogonHours obj = new SAMPRLogonHours();
        obj.setLogonHours(logonHours);
        obj.unmarshalDeferrals(in);
        assertEquals(obj.getUnitsPerWeek(), (short) 0);
        assertEquals(obj.getLogonHours(), expectedLogonHours);
        assertEquals(bout.available(), 0);
    }

    @Test
    public void test_toString_default() {
        assertEquals(new SAMPRLogonHours().toString(), "SAMPR_LOGON_HOURS{UnitsPerWeek:0,size(LogonHours):null}");
    }

    @Test
    public void test_toString() {
        SAMPRLogonHours obj = new SAMPRLogonHours();
        obj.setUnitsPerWeek((short) 50);
        obj.setLogonHours(new byte[]{1, 2, 3});
        assertEquals(obj.toString(), "SAMPR_LOGON_HOURS{UnitsPerWeek:50,size(LogonHours):3}");
    }
}
