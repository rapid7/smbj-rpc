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
package com.rapid7.client.dcerpc.msrrp.dto;

import org.junit.Test;
import com.rapid7.client.dcerpc.msrrp.dto.RegistryHive;
import com.rapid7.client.dcerpc.msrrp.messages.HandleRequest;

import static com.rapid7.client.dcerpc.msrrp.dto.RegistryHive.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Test_RegistryHive {
    @Test
    public void HKEY_CLASSES_ROOT_getFullName() {
        assertEquals(HKEY_CLASSES_ROOT.getFullName(), "HKEY_CLASSES_ROOT");
    }

    @Test
    public void HKEY_CLASSES_ROOT_getShortName() {
        assertEquals(HKEY_CLASSES_ROOT.getShortName(), "HKCR");
    }

    @Test
    public void HKEY_CLASSES_ROOT_getOpName() {
        assertEquals(HKEY_CLASSES_ROOT.getOpName(), "OpenClassesRoot");
    }

    @Test
    public void HKEY_CLASSES_ROOT_getOpNum() {
        assertEquals(HKEY_CLASSES_ROOT.getOpNum(), 0);
    }

    @Test
    public void HKEY_CLASSES_ROOT_getRequest() {
        final HandleRequest request = HKEY_CLASSES_ROOT.getRequest(33554432);
        assertEquals(0, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_CLASSES_ROOT_getByFullName() {
        assertEquals(HKEY_CLASSES_ROOT, RegistryHive.getRegistryHiveByName("HKEY_CLASSES_ROOT"));
    }

    @Test
    public void HKEY_CLASSES_ROOT_getByShortName() {
        assertEquals(HKEY_CLASSES_ROOT, RegistryHive.getRegistryHiveByName("HKCR"));
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getFullName() {
        assertEquals(HKEY_CURRENT_CONFIG.getFullName(), "HKEY_CURRENT_CONFIG");
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getShortName() {
        assertEquals(HKEY_CURRENT_CONFIG.getShortName(), "HKCC");
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getOpName() {
        assertEquals(HKEY_CURRENT_CONFIG.getOpName(), "OpenCurrentConfig");
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getOpNum() {
        assertEquals(HKEY_CURRENT_CONFIG.getOpNum(), 27);
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getRequest() {
        final HandleRequest request = HKEY_CURRENT_CONFIG.getRequest(33554432);
        assertEquals(27, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getByFullName() {
        assertEquals(HKEY_CURRENT_CONFIG, RegistryHive.getRegistryHiveByName("HKEY_CURRENT_CONFIG"));
    }

    @Test
    public void HKEY_CURRENT_CONFIG_getByShortName() {
        assertEquals(HKEY_CURRENT_CONFIG, RegistryHive.getRegistryHiveByName("HKCC"));
    }

    @Test
    public void HKEY_CURRENT_USER_getFullName() {
        assertEquals(HKEY_CURRENT_USER.getFullName(), "HKEY_CURRENT_USER");
    }

    @Test
    public void HKEY_CURRENT_USER_getShortName() {
        assertEquals(HKEY_CURRENT_USER.getShortName(), "HKCU");
    }

    @Test
    public void HKEY_CURRENT_USER_getOpName() {
        assertEquals(HKEY_CURRENT_USER.getOpName(), "OpenCurrentUser");
    }

    @Test
    public void HKEY_CURRENT_USER_getOpNum() {
        assertEquals(HKEY_CURRENT_USER.getOpNum(), 1);
    }

    @Test
    public void HKEY_CURRENT_USER_getRequest() {
        final HandleRequest request = HKEY_CURRENT_USER.getRequest(33554432);
        assertEquals(1, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_CURRENT_USER_getByFullName() {
        assertEquals(HKEY_CURRENT_USER, RegistryHive.getRegistryHiveByName("HKEY_CURRENT_USER"));
    }

    @Test
    public void HKEY_CURRENT_USER_getByShortName() {
        assertEquals(HKEY_CURRENT_USER, RegistryHive.getRegistryHiveByName("HKCU"));
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getFullName() {
        assertEquals(HKEY_LOCAL_MACHINE.getFullName(), "HKEY_LOCAL_MACHINE");
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getShortName() {
        assertEquals(HKEY_LOCAL_MACHINE.getShortName(), "HKLM");
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getOpName() {
        assertEquals(HKEY_LOCAL_MACHINE.getOpName(), "OpenLocalMachine");
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getOpNum() {
        assertEquals(HKEY_LOCAL_MACHINE.getOpNum(), 2);
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getRequest() {
        final HandleRequest request = HKEY_LOCAL_MACHINE.getRequest(33554432);
        assertEquals(2, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getByFullName() {
        assertEquals(HKEY_LOCAL_MACHINE, RegistryHive.getRegistryHiveByName("HKEY_LOCAL_MACHINE"));
    }

    @Test
    public void HKEY_LOCAL_MACHINE_getByShortName() {
        assertEquals(HKEY_LOCAL_MACHINE, RegistryHive.getRegistryHiveByName("HKLM"));
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getFullName() {
        assertEquals(HKEY_PERFORMANCE_DATA.getFullName(), "HKEY_PERFORMANCE_DATA");
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getShortName() {
        assertEquals(HKEY_PERFORMANCE_DATA.getShortName(), "");
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getOpName() {
        assertEquals(HKEY_PERFORMANCE_DATA.getOpName(), "OpenPerformanceData");
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getOpNum() {
        assertEquals(HKEY_PERFORMANCE_DATA.getOpNum(), 3);
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getRequest() {
        final HandleRequest request = HKEY_PERFORMANCE_DATA.getRequest(33554432);
        assertEquals(3, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getByFullName() {
        assertEquals(HKEY_PERFORMANCE_DATA, RegistryHive.getRegistryHiveByName("HKEY_PERFORMANCE_DATA"));
    }

    @Test
    public void HKEY_PERFORMANCE_DATA_getByShortName() {
        assertNull(RegistryHive.getRegistryHiveByName(""));
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getFullName() {
        assertEquals(HKEY_PERFORMANCE_NLSTEXT.getFullName(), "HKEY_PERFORMANCE_NLSTEXT");
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getShortName() {
        assertEquals(HKEY_PERFORMANCE_NLSTEXT.getShortName(), "");
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getOpName() {
        assertEquals(HKEY_PERFORMANCE_NLSTEXT.getOpName(), "OpenPerformanceNlsText");
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getOpNum() {
        assertEquals(HKEY_PERFORMANCE_NLSTEXT.getOpNum(), 33);
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getRequest() {
        final HandleRequest request = HKEY_PERFORMANCE_NLSTEXT.getRequest(33554432);
        assertEquals(33, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getByFullName() {
        assertEquals(HKEY_PERFORMANCE_NLSTEXT, RegistryHive.getRegistryHiveByName("HKEY_PERFORMANCE_NLSTEXT"));
    }

    @Test
    public void HKEY_PERFORMANCE_NLSTEXT_getByShortName() {
        assertNull(RegistryHive.getRegistryHiveByName(""));
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getFullName() {
        assertEquals(HKEY_PERFORMANCE_TEXT.getFullName(), "HKEY_PERFORMANCE_TEXT");
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getShortName() {
        assertEquals(HKEY_PERFORMANCE_TEXT.getShortName(), "");
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getOpName() {
        assertEquals(HKEY_PERFORMANCE_TEXT.getOpName(), "OpenPerformanceText");
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getOpNum() {
        assertEquals(HKEY_PERFORMANCE_TEXT.getOpNum(), 32);
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getRequest() {
        final HandleRequest request = HKEY_PERFORMANCE_TEXT.getRequest(33554432);
        assertEquals(32, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getByFullName() {
        assertEquals(HKEY_PERFORMANCE_TEXT, RegistryHive.getRegistryHiveByName("HKEY_PERFORMANCE_TEXT"));
    }

    @Test
    public void HKEY_PERFORMANCE_TEXT_getByShortName() {
        assertNull(RegistryHive.getRegistryHiveByName(""));
    }

    @Test
    public void HKEY_USERS_getFullName() {
        assertEquals(HKEY_USERS.getFullName(), "HKEY_USERS");
    }

    @Test
    public void HKEY_USERS_getShortName() {
        assertEquals(HKEY_USERS.getShortName(), "HKU");
    }

    @Test
    public void HKEY_USERS_getOpName() {
        assertEquals(HKEY_USERS.getOpName(), "OpenUsers");
    }

    @Test
    public void HKEY_USERS_getOpNum() {
        assertEquals(HKEY_USERS.getOpNum(), 4);
    }

    @Test
    public void HKEY_USERS_getRequest() {
        final HandleRequest request = HKEY_USERS.getRequest(33554432);
        assertEquals(4, request.getOpNum());
        assertEquals(33554432, request.getAccessMask());
    }

    @Test
    public void HKEY_USERS_getByFullName() {
        assertEquals(HKEY_USERS, RegistryHive.getRegistryHiveByName("HKEY_USERS"));
    }

    @Test
    public void HKEY_USERS_getByShortName() {
        assertEquals(HKEY_USERS, RegistryHive.getRegistryHiveByName("HKU"));
    }
}
