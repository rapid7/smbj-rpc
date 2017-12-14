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

package com.rapid7.client.dcerpc.msvcctl.dto;

import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_ServiceConfigInfo {

    @Test
    public void test_getters() {
        ServiceType serviceType = ServiceType.WIN32_SHARE_PROCESS;
        ServiceStartType serviceStartType = ServiceStartType.DEMAND_START;
        ServiceError serviceError = ServiceError.CRITICAL;
        String binaryName = "BinaryName1";
        String loadOrderGroup = "LoadOrderGroup1";
        int tagId = 55;
        String[] dependencies = new String[]{"S1", "S2"};
        String serviceStartName = "ServiceStartName1";
        String displayName = "DisplayName1";
        String password = "Password1";
        ServiceConfigInfo serviceConfigInfo = new ServiceConfigInfo(serviceType,
                serviceStartType, serviceError, binaryName, loadOrderGroup, tagId, dependencies,
                serviceStartName, displayName, password);
        assertSame(serviceConfigInfo.getServiceType(), serviceType);
        assertSame(serviceConfigInfo.getStartType(), serviceStartType);
        assertSame(serviceConfigInfo.getErrorControl(), serviceError);
        assertSame(serviceConfigInfo.getBinaryPathName(), binaryName);
        assertSame(serviceConfigInfo.getLoadOrderGroup(), loadOrderGroup);
        assertEquals(serviceConfigInfo.getTagId(), tagId);
        assertSame(serviceConfigInfo.getDependencies(), dependencies);
        assertSame(serviceConfigInfo.getServiceStartName(), serviceStartName);
        assertSame(serviceConfigInfo.getDisplayName(), displayName);
        assertSame(serviceConfigInfo.getPassword(), password);
    }

    @Test
    public void test_hashCode() {
        ServiceType serviceType1 = ServiceType.WIN32_SHARE_PROCESS;
        ServiceType serviceType2= ServiceType.FILE_SYSTEM_DRIVER;
        ServiceStartType serviceStartType1 = ServiceStartType.DEMAND_START;
        ServiceStartType serviceStartType2 = ServiceStartType.AUTO_START;
        ServiceError serviceError1 = ServiceError.CRITICAL;
        ServiceError serviceError2 = ServiceError.NORMAL;
        String binaryName1 = "BinaryName1";
        String binaryName2= "BinaryName2";
        String loadOrderGroup1 = "LoadOrderGroup1";
        String loadOrderGroup2 = "LoadOrderGroup2";
        int tagId1 = 55;
        int tagId2 = 56;
        String[] dependencies1 = new String[]{"S1", "S2"};
        String[] dependencies2 = new String[]{"S1", "S2", "S3"};
        String serviceStartName1 = "ServiceStartName1";
        String serviceStartName2 = "ServiceStartName2";
        String displayName1 = "DisplayName1";
        String displayName2 = "DisplayName2";
        String password1 = "Password1";
        String password2 = "Password2";
        ServiceConfigInfo serviceConfigInfo1 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo2 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo3 = new ServiceConfigInfo(serviceType2, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo4 = new ServiceConfigInfo(serviceType1, serviceStartType2, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo5 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError2, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo6 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName2, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo7 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup2, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo8 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId2, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo9 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies2, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo10 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName2, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo11 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName2, password2);
        assertEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo1.hashCode());
        assertEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo2.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo3.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo4.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo5.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo6.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo7.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo8.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo9.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo10.hashCode());
        assertNotEquals(serviceConfigInfo1.hashCode(), serviceConfigInfo11.hashCode());
    }

    @Test
    public void test_equals() {
        ServiceType serviceType1 = ServiceType.WIN32_SHARE_PROCESS;
        ServiceType serviceType2= ServiceType.FILE_SYSTEM_DRIVER;
        ServiceStartType serviceStartType1 = ServiceStartType.DEMAND_START;
        ServiceStartType serviceStartType2 = ServiceStartType.AUTO_START;
        ServiceError serviceError1 = ServiceError.CRITICAL;
        ServiceError serviceError2 = ServiceError.NORMAL;
        String binaryName1 = "BinaryName1";
        String binaryName2= "BinaryName2";
        String loadOrderGroup1 = "LoadOrderGroup1";
        String loadOrderGroup2 = "LoadOrderGroup2";
        int tagId1 = 55;
        int tagId2 = 56;
        String[] dependencies1 = new String[]{"S1", "S2"};
        String[] dependencies2 = new String[]{"S1", "S2", "S3"};
        String serviceStartName1 = "ServiceStartName1";
        String serviceStartName2 = "ServiceStartName2";
        String displayName1 = "DisplayName1";
        String displayName2 = "DisplayName2";
        String password1 = "Password1";
        String password2 = "Password2";
        ServiceConfigInfo serviceConfigInfo1 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo2 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo3 = new ServiceConfigInfo(serviceType2, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo4 = new ServiceConfigInfo(serviceType1, serviceStartType2, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo5 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError2, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo6 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName2, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo7 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup2, tagId1, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo8 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId2, dependencies1, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo9 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies2, serviceStartName1, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo10 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName2, displayName1, password1);
        ServiceConfigInfo serviceConfigInfo11 = new ServiceConfigInfo(serviceType1, serviceStartType1, serviceError1, binaryName1, loadOrderGroup1, tagId1, dependencies1, serviceStartName1, displayName2, password2);
        assertEquals(serviceConfigInfo1, serviceConfigInfo1);
        assertNotEquals(serviceConfigInfo1, null);
        assertNotEquals(serviceConfigInfo1, "test");
        assertEquals(serviceConfigInfo1, serviceConfigInfo2);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo3);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo4);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo5);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo6);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo7);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo8);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo9);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo10);
        assertNotEquals(serviceConfigInfo1, serviceConfigInfo11);
    }

    @Test
    public void test_toString() {
        ServiceType serviceType = ServiceType.WIN32_SHARE_PROCESS;
        ServiceStartType serviceStartType = ServiceStartType.DEMAND_START;
        ServiceError serviceError = ServiceError.CRITICAL;
        String binaryName = "BinaryName1";
        String loadOrderGroup = "LoadOrderGroup1";
        int tagId = 55;
        String[] dependencies = new String[]{"S1", "S2"};
        String serviceStartName = "ServiceStartName1";
        String displayName = "DisplayName1";
        String password = "Password1";
        ServiceConfigInfo serviceConfigInfo = new ServiceConfigInfo(serviceType,
                serviceStartType, serviceError, binaryName, loadOrderGroup, tagId, dependencies,
                serviceStartName, displayName, password);
        assertEquals(serviceConfigInfo.toString(), "ServiceConfigInfo{serviceType: WIN32_SHARE_PROCESS, startType: DEMAND_START, errorControl: CRITICAL, binaryPathName: \"BinaryName1\" loadOrderGroup: \"LoadOrderGroup1\", dependencies: [S1, S2], serviceStartName: \"ServiceStartName1\", displayName: \"DisplayName1\"}");
    }

    @Test
    public void test_toString_null() {
        ServiceConfigInfo serviceConfigInfo = new ServiceConfigInfo(null,
                null, null, null, null, 0, null,
                null, null, null);
        assertEquals(serviceConfigInfo.toString(), "ServiceConfigInfo{serviceType: null, startType: null, errorControl: null, binaryPathName: null loadOrderGroup: null, dependencies: null, serviceStartName: null, displayName: null}");
    }
}
