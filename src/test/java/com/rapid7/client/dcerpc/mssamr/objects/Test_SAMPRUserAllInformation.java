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
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.RPCShortBlob;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

public class Test_SAMPRUserAllInformation {

    @Test
    public void test_getters_default() {
        SAMPRUserAllInformation obj = new SAMPRUserAllInformation();
        assertEquals(obj.getLastLogon(), 0L);
        assertEquals(obj.getLastLogoff(), 0L);
        assertEquals(obj.getPasswordLastSet(), 0L);
        assertEquals(obj.getAccountExpires(), 0L);
        assertEquals(obj.getPasswordCanChange(), 0L);
        assertEquals(obj.getPasswordMustChange(), 0L);
        assertNull(obj.getUserName());
        assertNull(obj.getFullName());
        assertNull(obj.getHomeDirectory());
        assertNull(obj.getHomeDirectoryDrive());
        assertNull(obj.getScriptPath());
        assertNull(obj.getProfilePath());
        assertNull(obj.getAdminComment());
        assertNull(obj.getWorkStations());
        assertNull(obj.getUserComment());
        assertNull(obj.getParameters());
        assertNull(obj.getLmOwfPassword());
        assertNull(obj.getNtOwfPassword());
        assertNull(obj.getPrivateData());
        assertEquals(obj.getUserId(), 0L);
        assertEquals(obj.getPrimaryGroupId(), 0L);
        assertEquals(obj.getUserAccountControl(), 0L);
        assertEquals(obj.getWhichFields(), 0L);
        assertNull(obj.getLogonHours());
        assertEquals(obj.getBadPasswordCount(), 0);
        assertEquals(obj.getLogonCount(), 0);
        assertEquals(obj.getCountryCode(), 0);
        assertEquals(obj.getCodePage(), 0);
        assertEquals(obj.getLmPasswordPresent(), (char) 0);
        assertEquals(obj.getNtPasswordPresent(), (char) 0);
        assertEquals(obj.getPasswordExpired(), (char) 0);
        assertEquals(obj.getPrivateDataSensitive(), (char) 0);
    }

    @Test
    public void test_setters() {
        SAMPRUserAllInformation obj = new SAMPRUserAllInformation();
        obj.setLastLogon(1L);
        assertEquals(obj.getLastLogon(), 1L);
        obj.setLastLogoff(2L);
        assertEquals(obj.getLastLogoff(), 2L);
        obj.setPasswordLastSet(3L);
        assertEquals(obj.getPasswordLastSet(), 3L);
        obj.setAccountExpires(4L);
        assertEquals(obj.getAccountExpires(), 4L);
        obj.setPasswordCanChange(5L);
        assertEquals(obj.getPasswordCanChange(), 5L);
        obj.setPasswordMustChange(6L);
        assertEquals(obj.getPasswordMustChange(), 6L);
        RPCUnicodeString.NonNullTerminated userName = RPCUnicodeString.NonNullTerminated.of("UserName");
        obj.setUserName(userName);
        assertSame(obj.getUserName(), userName);
        RPCUnicodeString.NonNullTerminated fullName = RPCUnicodeString.NonNullTerminated.of("FullName");
        obj.setFullName(fullName);
        assertSame(obj.getFullName(), fullName);
        RPCUnicodeString.NonNullTerminated homeDirectory = RPCUnicodeString.NonNullTerminated.of("HomeDirectory");
        obj.setHomeDirectory(homeDirectory);
        assertSame(obj.getHomeDirectory(), homeDirectory);
        RPCUnicodeString.NonNullTerminated homeDirectoryDrive = RPCUnicodeString.NonNullTerminated.of("HomeDirectoryDrive");
        obj.setHomeDirectoryDrive(homeDirectoryDrive);
        assertSame(obj.getHomeDirectoryDrive(), homeDirectoryDrive);
        RPCUnicodeString.NonNullTerminated scriptPath = RPCUnicodeString.NonNullTerminated.of("ScriptPath");
        obj.setScriptPath(scriptPath);
        assertSame(obj.getScriptPath(), scriptPath);
        RPCUnicodeString.NonNullTerminated profilePath = RPCUnicodeString.NonNullTerminated.of("ProfilePath");
        obj.setProfilePath(profilePath);
        assertSame(obj.getProfilePath(), profilePath);
        RPCUnicodeString.NonNullTerminated adminComment = RPCUnicodeString.NonNullTerminated.of("AdminComment");
        obj.setAdminComment(adminComment);
        assertSame(obj.getAdminComment(), adminComment);
        RPCUnicodeString.NonNullTerminated workstations = RPCUnicodeString.NonNullTerminated.of("Workstations");
        obj.setWorkStations(workstations);
        assertSame(obj.getWorkStations(), workstations);
        RPCUnicodeString.NonNullTerminated userComment = RPCUnicodeString.NonNullTerminated.of("UserComment");
        obj.setUserComment(userComment);
        assertSame(obj.getUserComment(), userComment);
        RPCUnicodeString.NonNullTerminated parameters = RPCUnicodeString.NonNullTerminated.of("Parameters");
        obj.setParameters(parameters);
        assertSame(obj.getParameters(), parameters);
        RPCShortBlob lmOwfPassword = new RPCShortBlob();
        lmOwfPassword.setBuffer(new int[]{1, 2});
        obj.setLmOwfPassword(lmOwfPassword);
        assertSame(obj.getLmOwfPassword(), lmOwfPassword);
        RPCShortBlob ntOwfPassword = new RPCShortBlob();
        ntOwfPassword.setBuffer(new int[]{3, 4});
        obj.setNtOwfPassword(ntOwfPassword);
        assertSame(obj.getNtOwfPassword(), ntOwfPassword);
        RPCUnicodeString.NonNullTerminated privateData = RPCUnicodeString.NonNullTerminated.of("PrivateData");
        obj.setPrivateData(privateData);
        assertSame(obj.getPrivateData(), privateData);
        SAMPRSRSecurityDescriptor securityDescriptor = new SAMPRSRSecurityDescriptor();
        securityDescriptor.setSecurityDescriptor(new byte[]{5, 6, 7});
        obj.setSecurityDescriptor(securityDescriptor);
        assertSame(obj.getSecurityDescriptor(), securityDescriptor);
        obj.setUserId(7L);
        assertEquals(obj.getUserId(), 7L);
        obj.setPrimaryGroupId(8L);
        assertEquals(obj.getPrimaryGroupId(), 8L);
        obj.setUserAccountControl(9L);
        assertEquals(obj.getUserAccountControl(), 9L);
        obj.setWhichFields(10L);
        assertEquals(obj.getWhichFields(), 10L);
        SAMPRLogonHours samprLogonHours = new SAMPRLogonHours();
        samprLogonHours.setUnitsPerWeek((short) 50);
        samprLogonHours.setLogonHours(new byte[50]);
        obj.setLogonHours(samprLogonHours);
        assertSame(obj.getLogonHours(), samprLogonHours);
        obj.setBadPasswordCount(11);
        assertEquals(obj.getBadPasswordCount(), 11);
        obj.setLogonCount(12);
        assertEquals(obj.getLogonCount(), 12);
        obj.setCountryCode(13);
        assertEquals(obj.getCountryCode(), 13);
        obj.setCodePage(14);
        assertEquals(obj.getCodePage(), 14);
        obj.setLmPasswordPresent((char) 15);
        assertEquals(obj.getLmPasswordPresent(), (char) 15);
        obj.setNtPasswordPresent((char) 16);
        assertEquals(obj.getNtPasswordPresent(), (char) 16);
        obj.setPasswordExpired((char) 17);
        assertEquals(obj.getPasswordExpired(), (char) 17);
        obj.setPrivateDataSensitive((char) 18);
        assertEquals(obj.getPrivateDataSensitive(), (char) 18);
    }

    @Test
    public void test_unmarshalPreamble() throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
        PacketInput in = new PacketInput(bin);
        SAMPRUserAllInformation obj = new SAMPRUserAllInformation();
        obj.unmarshalPreamble(in);
        assertEquals(bin.available(), 0);
        assertEquals(obj.getLastLogon(), 0L);
        assertEquals(obj.getLastLogoff(), 0L);
        assertEquals(obj.getPasswordLastSet(), 0L);
        assertEquals(obj.getAccountExpires(), 0L);
        assertEquals(obj.getPasswordCanChange(), 0L);
        assertEquals(obj.getPasswordMustChange(), 0L);
        assertNotNull(obj.getUserName());
        assertNotNull(obj.getFullName());
        assertNotNull(obj.getHomeDirectory());
        assertNotNull(obj.getHomeDirectoryDrive());
        assertNotNull(obj.getScriptPath());
        assertNotNull(obj.getProfilePath());
        assertNotNull(obj.getAdminComment());
        assertNotNull(obj.getWorkStations());
        assertNotNull(obj.getUserComment());
        assertNotNull(obj.getParameters());
        assertNotNull(obj.getLmOwfPassword());
        assertNotNull(obj.getNtOwfPassword());
        assertNotNull(obj.getPrivateData());
        assertEquals(obj.getUserId(), 0L);
        assertEquals(obj.getPrimaryGroupId(), 0L);
        assertEquals(obj.getUserAccountControl(), 0L);
        assertEquals(obj.getWhichFields(), 0L);
        assertNotNull(obj.getLogonHours());
        assertEquals(obj.getBadPasswordCount(), 0);
        assertEquals(obj.getLogonCount(), 0);
        assertEquals(obj.getCountryCode(), 0);
        assertEquals(obj.getCodePage(), 0);
        assertEquals(obj.getLmPasswordPresent(), (char) 0);
        assertEquals(obj.getNtPasswordPresent(), (char) 0);
        assertEquals(obj.getPasswordExpired(), (char) 0);
        assertEquals(obj.getPrivateDataSensitive(), (char) 0);
    }

    @Test
    public void test_unmarshalEntity() throws IOException {
        String hex =
                // LastLogon: 1, LastLogoff: 2, PasswordLastSet: 3
                "0100000000000000 0200000000000000 0300000000000000 " +
                // AccountExpires: 4, PasswordCanChange: 5, PasswordMustChange: 6
                "0400000000000000 0500000000000000 0600000000000000 " +
                // UserName: testƟ121
                "1000100000000200 " +
                // FullName: testƟ122
                "1000100000000200 " +
                // HomeDirectory: testƟ123
                "1000100000000200 " +
                // HomeDirectoryDrive: testƟ124
                "1000100000000200 " +
                // ScriptPath: testƟ125
                "1000100000000200 " +
                // ProfilePath: testƟ126
                "1000100000000200 " +
                // AdminComment: testƟ127
                "1000100000000200 " +
                // WorkStations: testƟ128
                "1000100000000200 " +
                // UserComment: testƟ129
                "1000100000000200 " +
                // Parameters: testƟ130
                "1000100000000200 " +
                // LmOwfPassword: Length: 3, MaximumLength: 3, Reference: 2
                "0300 0300 00000200" +
                // NtOwfPassword: Length: 4, MaximumLength: 4, Reference: 2
                "0400 0400 00000200" +
                // PrivateData: testƟ131
                "1000100000000200 " +
                // SecurityDescriptor: Length: 2 Reference: 2
                "02000000 00000200" +
                // UserId: 7, PrimaryGroupId: 8, UserAccountControl: 9, WhichFields: 10
                "07000000 08000000 09000000 0A000000" +
                // LogonHours: UnitsPerWeek: 7, Alignment: 2b, LogonHoursRef: 1
                "0700 FFFF 01000000" +
                // BadPasswordCount: 11, LogonCount: 12, CountryCode: 13, CodePage: 14
                "0B00 0C00 0D00 0E00" +
                // LmPasswordPresent: 15, NtPasswordPresent: 16, PasswordExpired: 17, PrivateDataSensitive: 18
                "0F 10 11 12";
        SAMPRUserAllInformation expectedObj = createEntity();

        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        SAMPRUserAllInformation obj = new SAMPRUserAllInformation();
        // Convenience, instead of initializing everything up front
        obj.unmarshalPreamble(new PacketInput(new ByteArrayInputStream(new byte[0])));
        obj.unmarshalEntity(in);

        assertEquals(obj, expectedObj);
    }

    @Test
    public void test_unmarshalDeferrals() throws IOException {
        // Populate the initial entity with what we expect
        SAMPRUserAllInformation expectedObj = createEntity();
        expectedObj.getUserName().setValue("testƟ121");
        expectedObj.getFullName().setValue("testƟ122");
        expectedObj.getHomeDirectory().setValue("testƟ123");
        expectedObj.getHomeDirectoryDrive().setValue("testƟ124");
        expectedObj.getScriptPath().setValue("testƟ125");
        expectedObj.getProfilePath().setValue("testƟ126");
        expectedObj.getAdminComment().setValue("testƟ127");
        expectedObj.getWorkStations().setValue("testƟ128");
        expectedObj.getUserComment().setValue("testƟ129");
        expectedObj.getParameters().setValue("testƟ130");
        expectedObj.getLmOwfPassword().setBuffer(new int[]{1, 2, 65535});
        expectedObj.getNtOwfPassword().setBuffer(new int[]{1, 2, 3, 5});
        expectedObj.getPrivateData().setValue("testƟ131");
        expectedObj.getSecurityDescriptor().setSecurityDescriptor(new byte[]{1, 2});
        expectedObj.getLogonHours().setLogonHours(new byte[]{3});

        String hex =
                // UserName: testƟ121
                "08000000000000000800000074006500730074009f01310032003100 " +
                // FullName: testƟ122
                "08000000000000000800000074006500730074009f01310032003200 " +
                // HomeDirectory: testƟ123
                "08000000000000000800000074006500730074009f01310032003300 " +
                // HomeDirectoryDrive: testƟ124
                "08000000000000000800000074006500730074009f01310032003400 " +
                // ScriptPath: testƟ125
                "08000000000000000800000074006500730074009f01310032003500 " +
                // ProfilePath: testƟ126
                "08000000000000000800000074006500730074009f01310032003600 " +
                // AdminComment: testƟ127
                "08000000000000000800000074006500730074009f01310032003700 " +
                // WorkStations: testƟ128
                "08000000000000000800000074006500730074009f01310032003800 " +
                // UserComment: testƟ129
                "08000000000000000800000074006500730074009f01310032003900 " +
                // Parameters: testƟ130
                "08000000000000000800000074006500730074009f01310033003000 " +
                // LmOwfPassword: MaximumCount: 3, Offset: 0, ActualCount: 3, Buffer:{1, 2, 65535}
                "03000000 00000000 03000000 0100 0200 ffff" +
                // Alignment: 2b
                "0000" +
                // NtOwfPassword: MaximumCount: 4, Offset: 0, ActualCount: 4, Buffer:{1, 2, 3, 5}
                "04000000 00000000 04000000 0100 0200 0300 0500" +
                // PrivateData: testƟ131
                "08000000000000000800000074006500730074009f01310033003100 " +
                // SecurityDescriptor: MaximumCount: 3 SecurityDescriptor: {1, 2}
                "02000000 01 02" +
                // Alignment: 2b
                "0000" +
                // LogonHours: MaximumCount: 0, Offset: 0, ActualCount: 0, LogonHours: {3}
                "00000000 00000000 00000000 03";

        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        // Initial entity to populate
        SAMPRUserAllInformation obj = createEntity();
        obj.unmarshalDeferrals(in);
        assertEquals(obj, expectedObj);
    }

    @Test
    public void test_hashCode() {
        SAMPRUserAllInformation obj1 = new SAMPRUserAllInformation();
        SAMPRUserAllInformation obj2 = new SAMPRUserAllInformation();
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLastLogon(1L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLastLogon(1L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLastLogoff(2L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLastLogoff(2L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPasswordLastSet(3L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPasswordLastSet(3L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setAccountExpires(4L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setAccountExpires(4L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPasswordCanChange(5L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPasswordCanChange(5L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPasswordMustChange(6L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPasswordMustChange(6L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated userName = RPCUnicodeString.NonNullTerminated.of("UserName");
        obj1.setUserName(userName);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setUserName(userName);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated fullName = RPCUnicodeString.NonNullTerminated.of("FullName");
        obj1.setFullName(fullName);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setFullName(fullName);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated homeDirectory = RPCUnicodeString.NonNullTerminated.of("HomeDirectory");
        obj1.setHomeDirectory(homeDirectory);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setHomeDirectory(homeDirectory);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated homeDirectoryDrive = RPCUnicodeString.NonNullTerminated.of("HomeDirectoryDrive");
        obj1.setHomeDirectoryDrive(homeDirectoryDrive);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setHomeDirectoryDrive(homeDirectoryDrive);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated scriptPath = RPCUnicodeString.NonNullTerminated.of("ScriptPath");
        obj1.setScriptPath(scriptPath);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setScriptPath(scriptPath);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated profilePath = RPCUnicodeString.NonNullTerminated.of("ProfilePath");
        obj1.setProfilePath(profilePath);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setProfilePath(profilePath);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated adminComment = RPCUnicodeString.NonNullTerminated.of("AdminComment");
        obj1.setAdminComment(adminComment);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setAdminComment(adminComment);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated workstations = RPCUnicodeString.NonNullTerminated.of("Workstations");
        obj1.setWorkStations(workstations);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setWorkStations(workstations);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated userComment = RPCUnicodeString.NonNullTerminated.of("UserComment");
        obj1.setUserComment(userComment);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setUserComment(userComment);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated parameters = RPCUnicodeString.NonNullTerminated.of("Parameters");
        obj1.setParameters(parameters);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setParameters(parameters);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCShortBlob lmOwfPassword = new RPCShortBlob();
        lmOwfPassword.setBuffer(new int[]{1, 2});
        obj1.setLmOwfPassword(lmOwfPassword);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLmOwfPassword(lmOwfPassword);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCShortBlob ntOwfPassword = new RPCShortBlob();
        ntOwfPassword.setBuffer(new int[]{3, 4});
        obj1.setNtOwfPassword(ntOwfPassword);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setNtOwfPassword(ntOwfPassword);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        RPCUnicodeString.NonNullTerminated privateData = RPCUnicodeString.NonNullTerminated.of("PrivateData");
        obj1.setPrivateData(privateData);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPrivateData(privateData);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        SAMPRSRSecurityDescriptor securityDescriptor = new SAMPRSRSecurityDescriptor();
        securityDescriptor.setSecurityDescriptor(new byte[]{5, 6, 7});
        obj1.setSecurityDescriptor(securityDescriptor);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setSecurityDescriptor(securityDescriptor);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setUserId(7L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setUserId(7L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPrimaryGroupId(8L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPrimaryGroupId(8L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setUserAccountControl(9L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setUserAccountControl(9L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setWhichFields(10L);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setWhichFields(10L);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        SAMPRLogonHours samprLogonHours = new SAMPRLogonHours();
        samprLogonHours.setUnitsPerWeek((short) 50);
        samprLogonHours.setLogonHours(new byte[50]);
        obj1.setLogonHours(samprLogonHours);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLogonHours(samprLogonHours);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setBadPasswordCount(11);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setBadPasswordCount(11);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLogonCount(12);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLogonCount(12);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setCountryCode(13);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setCountryCode(13);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setCodePage(14);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setCodePage(14);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setLmPasswordPresent((char) 15);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setLmPasswordPresent((char) 15);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setNtPasswordPresent((char) 16);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setNtPasswordPresent((char) 16);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPasswordExpired((char) 17);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPasswordExpired((char) 17);
        assertEquals(obj1.hashCode(), obj2.hashCode());
        obj1.setPrivateDataSensitive((char) 18);
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        obj2.setPrivateDataSensitive((char) 18);
        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void test_equals() {
        SAMPRUserAllInformation obj1 = new SAMPRUserAllInformation();
        SAMPRUserAllInformation obj2 = new SAMPRUserAllInformation();
        assertEquals(obj1, obj2);
        obj1.setLastLogon(1L);
        assertNotEquals(obj1, obj2);
        obj2.setLastLogon(1L);
        assertEquals(obj1, obj2);
        obj1.setLastLogoff(2L);
        assertNotEquals(obj1, obj2);
        obj2.setLastLogoff(2L);
        assertEquals(obj1, obj2);
        obj1.setPasswordLastSet(3L);
        assertNotEquals(obj1, obj2);
        obj2.setPasswordLastSet(3L);
        assertEquals(obj1, obj2);
        obj1.setAccountExpires(4L);
        assertNotEquals(obj1, obj2);
        obj2.setAccountExpires(4L);
        assertEquals(obj1, obj2);
        obj1.setPasswordCanChange(5L);
        assertNotEquals(obj1, obj2);
        obj2.setPasswordCanChange(5L);
        assertEquals(obj1, obj2);
        obj1.setPasswordMustChange(6L);
        assertNotEquals(obj1, obj2);
        obj2.setPasswordMustChange(6L);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated userName = RPCUnicodeString.NonNullTerminated.of("UserName");
        obj1.setUserName(userName);
        assertNotEquals(obj1, obj2);
        obj2.setUserName(userName);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated fullName = RPCUnicodeString.NonNullTerminated.of("FullName");
        obj1.setFullName(fullName);
        assertNotEquals(obj1, obj2);
        obj2.setFullName(fullName);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated homeDirectory = RPCUnicodeString.NonNullTerminated.of("HomeDirectory");
        obj1.setHomeDirectory(homeDirectory);
        assertNotEquals(obj1, obj2);
        obj2.setHomeDirectory(homeDirectory);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated homeDirectoryDrive = RPCUnicodeString.NonNullTerminated.of("HomeDirectoryDrive");
        obj1.setHomeDirectoryDrive(homeDirectoryDrive);
        assertNotEquals(obj1, obj2);
        obj2.setHomeDirectoryDrive(homeDirectoryDrive);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated scriptPath = RPCUnicodeString.NonNullTerminated.of("ScriptPath");
        obj1.setScriptPath(scriptPath);
        assertNotEquals(obj1, obj2);
        obj2.setScriptPath(scriptPath);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated profilePath = RPCUnicodeString.NonNullTerminated.of("ProfilePath");
        obj1.setProfilePath(profilePath);
        assertNotEquals(obj1, obj2);
        obj2.setProfilePath(profilePath);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated adminComment = RPCUnicodeString.NonNullTerminated.of("AdminComment");
        obj1.setAdminComment(adminComment);
        assertNotEquals(obj1, obj2);
        obj2.setAdminComment(adminComment);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated workstations = RPCUnicodeString.NonNullTerminated.of("Workstations");
        obj1.setWorkStations(workstations);
        assertNotEquals(obj1, obj2);
        obj2.setWorkStations(workstations);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated userComment = RPCUnicodeString.NonNullTerminated.of("UserComment");
        obj1.setUserComment(userComment);
        assertNotEquals(obj1, obj2);
        obj2.setUserComment(userComment);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated parameters = RPCUnicodeString.NonNullTerminated.of("Parameters");
        obj1.setParameters(parameters);
        assertNotEquals(obj1, obj2);
        obj2.setParameters(parameters);
        assertEquals(obj1, obj2);
        RPCShortBlob lmOwfPassword = new RPCShortBlob();
        lmOwfPassword.setBuffer(new int[]{1, 2});
        obj1.setLmOwfPassword(lmOwfPassword);
        assertNotEquals(obj1, obj2);
        obj2.setLmOwfPassword(lmOwfPassword);
        assertEquals(obj1, obj2);
        RPCShortBlob ntOwfPassword = new RPCShortBlob();
        ntOwfPassword.setBuffer(new int[]{3, 4});
        obj1.setNtOwfPassword(ntOwfPassword);
        assertNotEquals(obj1, obj2);
        obj2.setNtOwfPassword(ntOwfPassword);
        assertEquals(obj1, obj2);
        RPCUnicodeString.NonNullTerminated privateData = RPCUnicodeString.NonNullTerminated.of("PrivateData");
        obj1.setPrivateData(privateData);
        assertNotEquals(obj1, obj2);
        obj2.setPrivateData(privateData);
        assertEquals(obj1, obj2);
        SAMPRSRSecurityDescriptor securityDescriptor = new SAMPRSRSecurityDescriptor();
        securityDescriptor.setSecurityDescriptor(new byte[]{5, 6, 7});
        obj1.setSecurityDescriptor(securityDescriptor);
        assertNotEquals(obj1, obj2);
        obj2.setSecurityDescriptor(securityDescriptor);
        assertEquals(obj1, obj2);
        obj1.setUserId(7L);
        assertNotEquals(obj1, obj2);
        obj2.setUserId(7L);
        assertEquals(obj1, obj2);
        obj1.setPrimaryGroupId(8L);
        assertNotEquals(obj1, obj2);
        obj2.setPrimaryGroupId(8L);
        assertEquals(obj1, obj2);
        obj1.setUserAccountControl(9L);
        assertNotEquals(obj1, obj2);
        obj2.setUserAccountControl(9L);
        assertEquals(obj1, obj2);
        obj1.setWhichFields(10L);
        assertNotEquals(obj1, obj2);
        obj2.setWhichFields(10L);
        assertEquals(obj1, obj2);
        SAMPRLogonHours samprLogonHours = new SAMPRLogonHours();
        samprLogonHours.setUnitsPerWeek((short) 50);
        samprLogonHours.setLogonHours(new byte[50]);
        obj1.setLogonHours(samprLogonHours);
        assertNotEquals(obj1, obj2);
        obj2.setLogonHours(samprLogonHours);
        assertEquals(obj1, obj2);
        obj1.setBadPasswordCount(11);
        assertNotEquals(obj1, obj2);
        obj2.setBadPasswordCount(11);
        assertEquals(obj1, obj2);
        obj1.setLogonCount(12);
        assertNotEquals(obj1, obj2);
        obj2.setLogonCount(12);
        assertEquals(obj1, obj2);
        obj1.setCountryCode(13);
        assertNotEquals(obj1, obj2);
        obj2.setCountryCode(13);
        assertEquals(obj1, obj2);
        obj1.setCodePage(14);
        assertNotEquals(obj1, obj2);
        obj2.setCodePage(14);
        assertEquals(obj1, obj2);
        obj1.setLmPasswordPresent((char) 15);
        assertNotEquals(obj1, obj2);
        obj2.setLmPasswordPresent((char) 15);
        assertEquals(obj1, obj2);
        obj1.setNtPasswordPresent((char) 16);
        assertNotEquals(obj1, obj2);
        obj2.setNtPasswordPresent((char) 16);
        assertEquals(obj1, obj2);
        obj1.setPasswordExpired((char) 17);
        assertNotEquals(obj1, obj2);
        obj2.setPasswordExpired((char) 17);
        assertEquals(obj1, obj2);
        obj1.setPrivateDataSensitive((char) 18);
        assertNotEquals(obj1, obj2);
        obj2.setPrivateDataSensitive((char) 18);
        assertEquals(obj1, obj2);
    }

    @Test
    public void test_toString_defaults() {
        assertEquals(new SAMPRUserAllInformation().toString(), "SAMPR_USER_ALL_INFORMATION{UserId:0, PrimaryGroupId:0, UserName:null, FullName:null}");
    }

    @Test
    public void test_toString() {
        SAMPRUserAllInformation obj = new SAMPRUserAllInformation();
        obj.setUserId(50L);
        obj.setPrimaryGroupId(100L);
        obj.setUserName(RPCUnicodeString.NonNullTerminated.of("UserName1"));
        obj.setFullName(RPCUnicodeString.NonNullTerminated.of("FullName1"));
        assertEquals(obj.toString(), "SAMPR_USER_ALL_INFORMATION{UserId:50, PrimaryGroupId:100, UserName:\"UserName1\", FullName:\"FullName1\"}");
    }

    private SAMPRUserAllInformation createEntity() {
        SAMPRUserAllInformation obj = new SAMPRUserAllInformation();
        obj.setLastLogon(1L);
        obj.setLastLogoff(2L);
        obj.setPasswordLastSet(3L);
        obj.setAccountExpires(4L);
        obj.setPasswordCanChange(5L);
        obj.setPasswordMustChange(6L);
        obj.setUserName(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setFullName(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setHomeDirectory(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setHomeDirectoryDrive(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setScriptPath(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setProfilePath(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setAdminComment(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setWorkStations(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setUserComment(RPCUnicodeString.NonNullTerminated.of(""));
        obj.setParameters(RPCUnicodeString.NonNullTerminated.of(""));
        RPCShortBlob lmOwfPassword = new RPCShortBlob();
        lmOwfPassword.setBuffer(new int[3]);
        obj.setLmOwfPassword(lmOwfPassword);
        RPCShortBlob ntOwfPassword = new RPCShortBlob();
        ntOwfPassword.setBuffer(new int[4]);
        obj.setNtOwfPassword(ntOwfPassword);
        obj.setPrivateData(RPCUnicodeString.NonNullTerminated.of(""));
        SAMPRSRSecurityDescriptor securityDescriptor = new SAMPRSRSecurityDescriptor();
        securityDescriptor.setSecurityDescriptor(new byte[2]);
        obj.setSecurityDescriptor(securityDescriptor);
        obj.setUserId(7L);
        obj.setPrimaryGroupId(8L);
        obj.setUserAccountControl(9L);
        obj.setWhichFields(10L);
        SAMPRLogonHours logonHours = new SAMPRLogonHours();
        logonHours.setUnitsPerWeek((short) 7);
        logonHours.setLogonHours(new byte[1]);
        obj.setLogonHours(logonHours);
        obj.setBadPasswordCount(11);
        obj.setLogonCount(12);
        obj.setCountryCode(13);
        obj.setCodePage(14);
        obj.setLmPasswordPresent((char) 15);
        obj.setNtPasswordPresent((char) 16);
        obj.setPasswordExpired((char) 17);
        obj.setPrivateDataSensitive((char) 18);
        return obj;
    }
}
