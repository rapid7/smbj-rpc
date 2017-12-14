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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class Test_LPServiceStatus {

    @Test
    public void test_hashCode() {
        LPServiceStatus obj1 = new LPServiceStatus();
        LPServiceStatus obj2 = new LPServiceStatus();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwServiceType(10);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwServiceType(10);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwCurrentState(20);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwCurrentState(20);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwControlsAccepted(30);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwControlsAccepted(30);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwWin32ExitCode(40);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwWin32ExitCode(40);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwServiceSpecificExitCode(50);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwServiceSpecificExitCode(50);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwCheckPoint(60);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwCheckPoint(60);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setDwWaitHint(70);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setDwWaitHint(70);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        LPServiceStatus obj1 = new LPServiceStatus();
        LPServiceStatus obj2 = new LPServiceStatus();
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, "test");
        assertEquals(obj1, obj2);
        obj1.setDwServiceType(10);
        assertNotEquals(obj1, obj2);
        obj2.setDwServiceType(10);
        assertEquals(obj1, obj2);
        obj1.setDwCurrentState(20);
        assertNotEquals(obj1, obj2);
        obj2.setDwCurrentState(20);
        assertEquals(obj1, obj2);
        obj1.setDwControlsAccepted(30);
        assertNotEquals(obj1, obj2);
        obj2.setDwControlsAccepted(30);
        assertEquals(obj1, obj2);
        obj1.setDwWin32ExitCode(40);
        assertNotEquals(obj1, obj2);
        obj2.setDwWin32ExitCode(40);
        assertEquals(obj1, obj2);
        obj1.setDwServiceSpecificExitCode(50);
        assertNotEquals(obj1, obj2);
        obj2.setDwServiceSpecificExitCode(50);
        assertEquals(obj1, obj2);
        obj1.setDwCheckPoint(60);
        assertNotEquals(obj1, obj2);
        obj2.setDwCheckPoint(60);
        assertEquals(obj1, obj2);
        obj1.setDwWaitHint(70);
        assertNotEquals(obj1, obj2);
        obj2.setDwWaitHint(70);
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString() {
        LPServiceStatus lpServiceStatus = new LPServiceStatus();
        lpServiceStatus.setDwServiceType(1);
        lpServiceStatus.setDwCurrentState(2);
        lpServiceStatus.setDwControlsAccepted(3);
        lpServiceStatus.setDwWin32ExitCode(4);
        lpServiceStatus.setDwServiceSpecificExitCode(5);
        lpServiceStatus.setDwCheckPoint(6);
        lpServiceStatus.setDwWaitHint(7);
        assertEquals(lpServiceStatus.toString(), "LPSERVICE_STATUS{dwServiceType: 1, dwCurrentState: 2, dwControlsAccepted: 3, dwWin32ExitCode: 4, dwServiceSpecificExitCode: 5, dwCheckPoint: 6, dwWaitHint: 7}");
    }

    @Test
    public void test_toString_default() {
        assertEquals(new LPServiceStatus().toString(), "LPSERVICE_STATUS{dwServiceType: 0, dwCurrentState: 0, dwControlsAccepted: 0, dwWin32ExitCode: 0, dwServiceSpecificExitCode: 0, dwCheckPoint: 0, dwWaitHint: 0}");
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        LPServiceStatus lpServiceStatus = new LPServiceStatus();
        lpServiceStatus.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertEquals(lpServiceStatus.getDwServiceType(), 0);
        assertEquals(lpServiceStatus.getDwCurrentState(), 0);
        assertEquals(lpServiceStatus.getDwControlsAccepted(), 0);
        assertEquals(lpServiceStatus.getDwWin32ExitCode(), 0);
        assertEquals(lpServiceStatus.getDwServiceSpecificExitCode(), 0);
        assertEquals(lpServiceStatus.getDwCheckPoint(), 0);
        assertEquals(lpServiceStatus.getDwWaitHint(), 0);
    }

    @DataProvider
    public Object[][] data_unmarshalEntity() {
        String hex = "01000000 02000000 03000000 04000000 05000000 06000000 07000000";
        return new Object[][] {
                {hex, 0},
                // Alignments
                {"00000000 " + hex, 1},
                {"00000000 " + hex, 2},
                {"00000000 " + hex, 3}
        };
    }

    @Test(dataProvider = "data_unmarshalEntity")
    public void test_unmarshalEntity(String hex, int mark) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        in.fullySkipBytes(mark);
        LPServiceStatus lpServiceStatus = new LPServiceStatus();
        lpServiceStatus.unmarshalEntity(in);
        assertEquals(bin.available(), 0);
        assertEquals(lpServiceStatus.getDwServiceType(), 1);
        assertEquals(lpServiceStatus.getDwCurrentState(), 2);
        assertEquals(lpServiceStatus.getDwControlsAccepted(), 3);
        assertEquals(lpServiceStatus.getDwWin32ExitCode(), 4);
        assertEquals(lpServiceStatus.getDwServiceSpecificExitCode(), 5);
        assertEquals(lpServiceStatus.getDwCheckPoint(), 6);
        assertEquals(lpServiceStatus.getDwWaitHint(), 7);
    }

    @Test
    public void test_unmarshalDeferrals() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        LPServiceStatus lpServiceStatus = new LPServiceStatus();
        lpServiceStatus.unmarshalDeferrals(in);
        assertEquals(bin.available(), 0);
        assertEquals(lpServiceStatus.getDwServiceType(), 0);
        assertEquals(lpServiceStatus.getDwCurrentState(), 0);
        assertEquals(lpServiceStatus.getDwControlsAccepted(), 0);
        assertEquals(lpServiceStatus.getDwWin32ExitCode(), 0);
        assertEquals(lpServiceStatus.getDwServiceSpecificExitCode(), 0);
        assertEquals(lpServiceStatus.getDwCheckPoint(), 0);
        assertEquals(lpServiceStatus.getDwWaitHint(), 0);
    }
}
