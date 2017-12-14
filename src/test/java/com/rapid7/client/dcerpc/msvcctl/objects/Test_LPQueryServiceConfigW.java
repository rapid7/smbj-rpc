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

package com.rapid7.client.dcerpc.msvcctl.objects;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.WChar;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

public class Test_LPQueryServiceConfigW {

    @Test
    public void test_hashCode() {
        LPQueryServiceConfigW obj1 = new LPQueryServiceConfigW();
        obj1.setDwServiceType(1);
        obj1.setDwStartType(2);
        obj1.setDwErrorControl(3);
        obj1.setLpBinaryPathName(WChar.NullTerminated.of(""));
        obj1.setLpLoadOrderGroup(WChar.NullTerminated.of(""));
        obj1.setDwTagId(4);
        obj1.setLpDependencies(new String[0]);
        obj1.setLpServiceStartName(WChar.NullTerminated.of(""));
        obj1.setLpDisplayName(WChar.NullTerminated.of(""));

        LPQueryServiceConfigW obj2 = new LPQueryServiceConfigW();
        obj2.setDwServiceType(1);
        obj2.setDwStartType(2);
        obj2.setDwErrorControl(3);
        obj2.setLpBinaryPathName(WChar.NullTerminated.of(""));
        obj2.setLpLoadOrderGroup(WChar.NullTerminated.of(""));
        obj2.setDwTagId(4);
        obj2.setLpDependencies(new String[0]);
        obj2.setLpServiceStartName(WChar.NullTerminated.of(""));
        obj2.setLpDisplayName(WChar.NullTerminated.of(""));

        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwServiceType(10);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwServiceType(10);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwStartType(20);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwStartType(20);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwErrorControl(30);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwErrorControl(30);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLpBinaryPathName(WChar.NullTerminated.of("BinaryPathName1"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLpBinaryPathName(WChar.NullTerminated.of("BinaryPathName1"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLpLoadOrderGroup(WChar.NullTerminated.of("LoadOrderGroup1"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLpLoadOrderGroup(WChar.NullTerminated.of("LoadOrderGroup1"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwTagId(40);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwTagId(40);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLpDependencies(new String[]{"S1", "S2"});
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLpDependencies(new String[]{"S1", "S2"});
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLpServiceStartName(WChar.NullTerminated.of("ServiceStartName1"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLpServiceStartName(WChar.NullTerminated.of("ServiceStartName1"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLpDisplayName(WChar.NullTerminated.of("DisplayName1"));
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLpDisplayName(WChar.NullTerminated.of("DisplayName1"));
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        LPQueryServiceConfigW obj1 = new LPQueryServiceConfigW();
        obj1.setDwServiceType(1);
        obj1.setDwStartType(2);
        obj1.setDwErrorControl(3);
        obj1.setLpBinaryPathName(WChar.NullTerminated.of(""));
        obj1.setLpLoadOrderGroup(WChar.NullTerminated.of(""));
        obj1.setDwTagId(4);
        obj1.setLpDependencies(new String[0]);
        obj1.setLpServiceStartName(WChar.NullTerminated.of(""));
        obj1.setLpDisplayName(WChar.NullTerminated.of(""));

        LPQueryServiceConfigW obj2 = new LPQueryServiceConfigW();
        obj2.setDwServiceType(1);
        obj2.setDwStartType(2);
        obj2.setDwErrorControl(3);
        obj2.setLpBinaryPathName(WChar.NullTerminated.of(""));
        obj2.setLpLoadOrderGroup(WChar.NullTerminated.of(""));
        obj2.setDwTagId(4);
        obj2.setLpDependencies(new String[0]);
        obj2.setLpServiceStartName(WChar.NullTerminated.of(""));
        obj2.setLpDisplayName(WChar.NullTerminated.of(""));

        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");

        assertEquals(obj1, obj2);
        obj1.setDwServiceType(10);
        assertNotEquals(obj1, obj2);
        obj2.setDwServiceType(10);
        assertEquals(obj1, obj2);
        obj1.setDwStartType(20);
        assertNotEquals(obj1, obj2);
        obj2.setDwStartType(20);
        assertEquals(obj1, obj2);
        obj1.setDwErrorControl(30);
        assertNotEquals(obj1, obj2);
        obj2.setDwErrorControl(30);
        assertEquals(obj1, obj2);
        obj1.setLpBinaryPathName(WChar.NullTerminated.of("BinaryPathName1"));
        assertNotEquals(obj1, obj2);
        obj2.setLpBinaryPathName(WChar.NullTerminated.of("BinaryPathName1"));
        assertEquals(obj1, obj2);
        obj1.setLpLoadOrderGroup(WChar.NullTerminated.of("LoadOrderGroup1"));
        assertNotEquals(obj1, obj2);
        obj2.setLpLoadOrderGroup(WChar.NullTerminated.of("LoadOrderGroup1"));
        assertEquals(obj1, obj2);
        obj1.setDwTagId(40);
        assertNotEquals(obj1, obj2);
        obj2.setDwTagId(40);
        assertEquals(obj1, obj2);
        obj1.setLpDependencies(new String[]{"S1", "S2"});
        assertNotEquals(obj1, obj2);
        obj2.setLpDependencies(new String[]{"S1", "S2"});
        assertEquals(obj1, obj2);
        obj1.setLpServiceStartName(WChar.NullTerminated.of("ServiceStartName1"));
        assertNotEquals(obj1, obj2);
        obj2.setLpServiceStartName(WChar.NullTerminated.of("ServiceStartName1"));
        assertEquals(obj1, obj2);
        obj1.setLpDisplayName(WChar.NullTerminated.of("DisplayName1"));
        assertNotEquals(obj1, obj2);
        obj2.setLpDisplayName(WChar.NullTerminated.of("DisplayName1"));
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString() {
        LPQueryServiceConfigW obj = new LPQueryServiceConfigW();
        obj.setDwServiceType(1);
        obj.setDwStartType(2);
        obj.setDwErrorControl(3);
        obj.setLpBinaryPathName(WChar.NullTerminated.of("BinaryPathName1"));
        obj.setLpLoadOrderGroup(WChar.NullTerminated.of("LoadOrderGroup1"));
        obj.setDwTagId(4);
        obj.setLpDependencies(new String[]{"S1", "S2"});
        obj.setLpServiceStartName(WChar.NullTerminated.of("ServiceStartName1"));
        obj.setLpDisplayName(WChar.NullTerminated.of("DisplayName1"));
        assertEquals(obj.toString(), "QUERY_SERVICE_CONFIGW{dwServiceType: 1, dwStartType: 2, dwErrorControl: 3, lpBinaryPathName: \"BinaryPathName1\", lpLoadOrderGroup: \"LoadOrderGroup1\", dwTagId: 4, lpDependencies: [S1, S2], lpServiceStartName: \"ServiceStartName1\", lpDisplayName: \"DisplayName1\"}");
    }

    @Test
    public void test_toString_default() {
        assertEquals(new LPQueryServiceConfigW().toString(), "QUERY_SERVICE_CONFIGW{dwServiceType: 0, dwStartType: 0, dwErrorControl: 0, lpBinaryPathName: null, lpLoadOrderGroup: null, dwTagId: 0, lpDependencies: null, lpServiceStartName: null, lpDisplayName: null}");
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        LPQueryServiceConfigW lpQueryServiceConfigW = new LPQueryServiceConfigW();
        lpQueryServiceConfigW.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertEquals(lpQueryServiceConfigW.getDwServiceType(), 0);
        assertEquals(lpQueryServiceConfigW.getDwStartType(), 0);
        assertEquals(lpQueryServiceConfigW.getDwErrorControl(), 0);
        assertNull(lpQueryServiceConfigW.getLpBinaryPathName());
        assertNull(lpQueryServiceConfigW.getLpLoadOrderGroup());
        assertEquals(lpQueryServiceConfigW.getDwTagId(), 0);
        assertNull(lpQueryServiceConfigW.getLpDependencies());
        assertNull(lpQueryServiceConfigW.getLpServiceStartName());
        assertNull(lpQueryServiceConfigW.getLpDisplayName());
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        String hex1 =   // dwServiceType: 1, dwStartType: 2, dwErrorControl: 3
                        "01000000 02000000 03000000 " +
                        // lpBinaryPathName[pointer: 2] lpLoadOrderGroup[pointer: 2]
                        "02000000 02000000 " +
                        // tagId: 4 lpDependencies[pointer: 2]
                        "04000000 02000000 " +
                        // lpServiceStartName[pointer: 2] lpDisplayName[pointer: 2]
                        "02000000 02000000";
        LPQueryServiceConfigW expect1 = new LPQueryServiceConfigW();
        expect1.setDwServiceType(1);
        expect1.setDwStartType(2);
        expect1.setDwErrorControl(3);
        expect1.setLpBinaryPathName(WChar.NullTerminated.of(""));
        expect1.setLpLoadOrderGroup(WChar.NullTerminated.of(""));
        expect1.setDwTagId(4);
        expect1.setLpDependencies(new String[0]);
        expect1.setLpServiceStartName(WChar.NullTerminated.of(""));
        expect1.setLpDisplayName(WChar.NullTerminated.of(""));

        String hex2 =   // dwServiceType: 1, dwStartType: 2, dwErrorControl: 3
                        "01000000 02000000 03000000 " +
                        // lpBinaryPathName[pointer: 2] lpLoadOrderGroup[pointer: 2]
                        "00000000 00000000 " +
                        // tagId: 4 lpDependencies[pointer: 2]
                        "04000000 00000000 " +
                        // lpServiceStartName[pointer: 2] lpDisplayName[pointer: 2]
                        "00000000 00000000";
        LPQueryServiceConfigW expect2 = new LPQueryServiceConfigW();
        expect2.setDwServiceType(1);
        expect2.setDwStartType(2);
        expect2.setDwErrorControl(3);
        expect2.setLpBinaryPathName(null);
        expect2.setLpLoadOrderGroup(null);
        expect2.setDwTagId(4);
        expect2.setLpDependencies(null);
        expect2.setLpServiceStartName(null);
        expect2.setLpDisplayName(null);
        return new Object[][] {
                {hex1, 0, expect1},
                {hex2, 0, expect2},
                // Alignments
                {"00000000" + hex1, 1, expect1},
                {"00000000" + hex1, 2, expect1},
                {"00000000" + hex1, 3, expect1}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark, LPQueryServiceConfigW expect) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        LPQueryServiceConfigW lpQueryServiceConfigW = new LPQueryServiceConfigW();
        lpQueryServiceConfigW.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(lpQueryServiceConfigW, expect);
    }

    @DataProvider
    public Object[][] data_unmarshalDeferrals() {
        String hex1 =   // LpBinaryPathName[MaximumCount=9, Offset=0, ActualCount=9, Value=testƟ123]
                        "09000000000000000900000074006500730074009f013100320033000000" +
                        // Alignment: 2
                        "0000" +
                        // LpLoadOrderGroup[MaximumCount=9, Offset=0, ActualCount=9, Value=testƟ124]
                        "09000000000000000900000074006500730074009f013100320034000000" +
                        // Alignment: 2
                        "0000" +
                        // LpDependencies[MaximumCount=7, Offset=0, ActualCount=7, Value=s1/s2/]
                        "070000000000000007000000730031002F00730032002F000000" +
                        // Alignment: 2
                        "0000" +
                        // LpServiceStartName [MaximumCount=9, Offset=0, ActualCount=9, Value=testƟ125]
                        "09000000000000000900000074006500730074009f013100320035000000" +
                        // Alignment: 2
                        "0000" +
                        // LpServiceDisplayName [MaximumCount=9, Offset=0, ActualCount=9, Value=testƟ126]
                        "09000000000000000900000074006500730074009f013100320036000000";
        LPQueryServiceConfigW obj1 = new LPQueryServiceConfigW();
        obj1.setDwServiceType(1);
        obj1.setDwStartType(2);
        obj1.setDwErrorControl(3);
        obj1.setLpBinaryPathName(WChar.NullTerminated.of(""));
        obj1.setLpLoadOrderGroup(WChar.NullTerminated.of(""));
        obj1.setDwTagId(4);
        obj1.setLpDependencies(new String[0]);
        obj1.setLpServiceStartName(WChar.NullTerminated.of(""));
        obj1.setLpDisplayName(WChar.NullTerminated.of(""));

        LPQueryServiceConfigW expect1 = new LPQueryServiceConfigW();
        expect1.setDwServiceType(1);
        expect1.setDwStartType(2);
        expect1.setDwErrorControl(3);
        expect1.setLpBinaryPathName(WChar.NullTerminated.of("testƟ123"));
        expect1.setLpLoadOrderGroup(WChar.NullTerminated.of("testƟ124"));
        expect1.setDwTagId(4);
        expect1.setLpDependencies(new String[]{"s1", "s2"});
        expect1.setLpServiceStartName(WChar.NullTerminated.of("testƟ125"));
        expect1.setLpDisplayName(WChar.NullTerminated.of("testƟ126"));

        String hex2 = "";
        LPQueryServiceConfigW obj2 = new LPQueryServiceConfigW();
        obj2.setDwServiceType(1);
        obj2.setDwStartType(2);
        obj2.setDwErrorControl(3);
        obj2.setLpBinaryPathName(null);
        obj2.setLpLoadOrderGroup(null);
        obj2.setDwTagId(4);
        obj2.setLpDependencies(null);
        obj2.setLpServiceStartName(null);
        obj2.setLpDisplayName(null);

        LPQueryServiceConfigW expect2 = new LPQueryServiceConfigW();
        expect2.setDwServiceType(1);
        expect2.setDwStartType(2);
        expect2.setDwErrorControl(3);
        expect2.setLpBinaryPathName(null);
        expect2.setLpLoadOrderGroup(null);
        expect2.setDwTagId(4);
        expect2.setLpDependencies(null);
        expect2.setLpServiceStartName(null);
        expect2.setLpDisplayName(null);
        return new Object[][] {
                {hex1, 0, obj1, expect1},
                {hex2, 0, obj2, expect2},
                // Alignments
                {"00000000" + hex1, 1, obj1, expect1},
                {"00000000" + hex1, 2, obj1, expect1},
                {"00000000" + hex1, 3, obj1, expect1}
        };
    }

    @Test(dataProvider = "data_unmarshalDeferrals")
    public void test_unmarshalDeferrals(String hex, int mark, LPQueryServiceConfigW obj, LPQueryServiceConfigW expect) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        obj.unmarshalDeferrals(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj, expect);
    }
}
