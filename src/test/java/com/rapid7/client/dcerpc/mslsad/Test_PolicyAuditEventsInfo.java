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

package com.rapid7.client.dcerpc.mslsad;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Test_PolicyAuditEventsInfo {

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting non-null eventAuditingOptions")
    public void test_EventAuditingOptions_null() {
        new PolicyAuditEventsInfo(true, null);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class},
            expectedExceptionsMessageRegExp = "Expecting 9 elements in eventAuditingOptions, got: 8")
    public void test_EventAuditingOptions_WrongSize() {
        new PolicyAuditEventsInfo(true, new int[8]);
    }

    @DataProvider
    public Object[][] data_isAuditEnabled() {
        return new Object[][] {
                {true},
                {false}
        };
    }

    @Test(dataProvider = "data_isAuditEnabled")
    public void test_isAuditEnabled(boolean auditEnabled) {
        PolicyAuditEventsInfo obj = new PolicyAuditEventsInfo(auditEnabled, new int[9]);
        assertEquals(obj.isAuditEnabled(), auditEnabled);
    }

    @DataProvider
    public Object[][] data_getEventAuditingOptions() {
        return new Object[][] {
                {PolicyAuditEventType.AUDIT_CATEGORY_SYSTEM, 10},
                {PolicyAuditEventType.AUDIT_CATEGORY_LOGON, 20},
                {PolicyAuditEventType.AUDIT_CATEGORY_OBJECT_ACCESS, 30},
                {PolicyAuditEventType.AUDIT_CATEGORY_PRIVILEGE_USE, 40},
                {PolicyAuditEventType.AUDIT_CATEGORY_DETAILED_TRACKING, 50},
                {PolicyAuditEventType.AUDIT_CATEGORY_POLICY_CHANGE, 60},
                {PolicyAuditEventType.AUDIT_CATEGORY_ACCOUNT_MANAGEMENT, 70},
                {PolicyAuditEventType.AUDIT_CATEGORY_DIRECTORY_SERVICE_ACCESS, 80},
                {PolicyAuditEventType.AUDIT_CATEGORY_ACCOUNT_LOGON, 90},
        };
    }

    @Test(dataProvider = "data_getEventAuditingOptions")
    public void test_getEventAuditingOptions(PolicyAuditEventType type, int expectValue) {
        PolicyAuditEventsInfo obj = new PolicyAuditEventsInfo(true, new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90});
        assertEquals(obj.getEventAuditingOptions(type), expectValue);
    }
}
