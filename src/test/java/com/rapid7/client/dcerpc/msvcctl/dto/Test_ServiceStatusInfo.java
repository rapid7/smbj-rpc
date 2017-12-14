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

import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServicesAcceptedControls;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_ServiceStatusInfo {

    @Test
    public void test_getters() {
        ServiceType serviceType = ServiceType.WIN32_SHARE_PROCESS;
        ServiceStatusType serviceStatusType = ServiceStatusType.SERVICE_PAUSE_PENDING;
        ServicesAcceptedControls servicesAcceptedControls = ServicesAcceptedControls.SERVICE_ACCEPT_NETBINDCHANGE;
        int win32ExitCode = 9;
        int serviceSpecificExitCode = 255;
        int checkPoint = 30;
        int waitHint = 2000;
        ServiceStatusInfo serviceStatusInfo = new ServiceStatusInfo(serviceType, serviceStatusType,
                servicesAcceptedControls, win32ExitCode, serviceSpecificExitCode,  checkPoint, waitHint);
        assertSame(serviceStatusInfo.getServiceType(), serviceType);
        assertSame(serviceStatusInfo.getCurrentState(), serviceStatusType);
        assertSame(serviceStatusInfo.getControlsAccepted(), servicesAcceptedControls);
        assertEquals(serviceStatusInfo.getWin32ExitCode(), win32ExitCode);
        assertEquals(serviceStatusInfo.getServiceSpecificExitCode(), serviceSpecificExitCode);
        assertEquals(serviceStatusInfo.getCheckPoint(), checkPoint);
        assertEquals(serviceStatusInfo.getWaitHint(), waitHint);
    }

    @Test
    public void test_hashCode() {
        ServiceType serviceType1 = ServiceType.WIN32_SHARE_PROCESS;
        ServiceType serviceType2 = ServiceType.FILE_SYSTEM_DRIVER;
        ServiceStatusType serviceStatusType1 = ServiceStatusType.SERVICE_PAUSE_PENDING;
        ServiceStatusType serviceStatusType2 = ServiceStatusType.SERVICE_STOPPED;
        ServicesAcceptedControls servicesAcceptedControls1 = ServicesAcceptedControls.SERVICE_ACCEPT_NETBINDCHANGE;
        ServicesAcceptedControls servicesAcceptedControls2 = ServicesAcceptedControls.SERVICE_ACCEPT_PAUSE_CONTINUE;
        int win32ExitCode1 = 9;
        int win32ExitCode2 = 10;
        int serviceSpecificExitCode1 = 255;
        int serviceSpecificExitCode2 = 256;
        int checkPoint1 = 30;
        int checkPoint2 = 31;
        int waitHint1 = 2000;
        int waitHint2 = 2001;
        ServiceStatusInfo serviceStatusInfo1 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo2 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo3 = new ServiceStatusInfo(serviceType2, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo4 = new ServiceStatusInfo(serviceType1, serviceStatusType2, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo5 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls2, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo6 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode2, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo7 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode2,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo8 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint2, waitHint1);
        ServiceStatusInfo serviceStatusInfo9 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint2);
        assertEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo1.hashCode());
        assertEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo2.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo3.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo4.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo5.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo6.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo7.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo8.hashCode());
        assertNotEquals(serviceStatusInfo1.hashCode(), serviceStatusInfo9.hashCode());
    }

    @Test
    public void test_equals() {
        ServiceType serviceType1 = ServiceType.WIN32_SHARE_PROCESS;
        ServiceType serviceType2 = ServiceType.FILE_SYSTEM_DRIVER;
        ServiceStatusType serviceStatusType1 = ServiceStatusType.SERVICE_PAUSE_PENDING;
        ServiceStatusType serviceStatusType2 = ServiceStatusType.SERVICE_STOPPED;
        ServicesAcceptedControls servicesAcceptedControls1 = ServicesAcceptedControls.SERVICE_ACCEPT_NETBINDCHANGE;
        ServicesAcceptedControls servicesAcceptedControls2 = ServicesAcceptedControls.SERVICE_ACCEPT_PAUSE_CONTINUE;
        int win32ExitCode1 = 9;
        int win32ExitCode2 = 10;
        int serviceSpecificExitCode1 = 255;
        int serviceSpecificExitCode2 = 256;
        int checkPoint1 = 30;
        int checkPoint2 = 31;
        int waitHint1 = 2000;
        int waitHint2 = 2001;
        ServiceStatusInfo serviceStatusInfo1 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo2 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo3 = new ServiceStatusInfo(serviceType2, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo4 = new ServiceStatusInfo(serviceType1, serviceStatusType2, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo5 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls2, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo6 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode2, serviceSpecificExitCode1,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo7 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode2,  checkPoint1, waitHint1);
        ServiceStatusInfo serviceStatusInfo8 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint2, waitHint1);
        ServiceStatusInfo serviceStatusInfo9 = new ServiceStatusInfo(serviceType1, serviceStatusType1, servicesAcceptedControls1, win32ExitCode1, serviceSpecificExitCode1,  checkPoint1, waitHint2);
        assertEquals(serviceStatusInfo1, serviceStatusInfo1);
        assertNotEquals(serviceStatusInfo1, null);
        assertNotEquals(serviceStatusInfo1, "test");
        assertEquals(serviceStatusInfo1, serviceStatusInfo2);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo3);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo4);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo5);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo6);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo7);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo8);
        assertNotEquals(serviceStatusInfo1, serviceStatusInfo9);
    }

    @Test
    public void test_toString() {
        ServiceType serviceType = ServiceType.WIN32_SHARE_PROCESS;
        ServiceStatusType serviceStatusType = ServiceStatusType.SERVICE_PAUSE_PENDING;
        ServicesAcceptedControls servicesAcceptedControls = ServicesAcceptedControls.SERVICE_ACCEPT_NETBINDCHANGE;
        int win32ExitCode = 9;
        int serviceSpecificExitCode = 255;
        int checkPoint = 30;
        int waitHint = 2000;
        ServiceStatusInfo serviceStatusInfo = new ServiceStatusInfo(serviceType, serviceStatusType,
                servicesAcceptedControls, win32ExitCode, serviceSpecificExitCode,  checkPoint, waitHint);
        assertEquals(serviceStatusInfo.toString(), "ServiceStatusInfo{serviceType: WIN32_SHARE_PROCESS, currentState: SERVICE_PAUSE_PENDING, controlsAccepted: SERVICE_ACCEPT_NETBINDCHANGE, win32ExitCode: 9, serviceSpecificExitCode: 255, checkPoint: 30, waitHint: 2000}");
    }

    @Test
    public void test_null() {
        ServiceStatusInfo serviceStatusInfo = new ServiceStatusInfo(null, null, null,
                0, 0, 0, 0);
        assertEquals(serviceStatusInfo.toString(), "ServiceStatusInfo{serviceType: null, currentState: null, controlsAccepted: null, win32ExitCode: 0, serviceSpecificExitCode: 0, checkPoint: 0, waitHint: 0}");
    }
}
