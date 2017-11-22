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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class Test_EventAuditOptions {

    @DataProvider
    public Object[][] data_is() {
        EventAuditOptions[] values = EventAuditOptions.values();
        Object[][] ret = new Object[values.length][];
        for (int i = 0; i < values.length; i++) {
            ret[i] = new Object[]{values[i]};
        }
        return ret;
    }

    @Test(dataProvider = "data_is")
    public void test_is(EventAuditOptions eventAuditOptions) {
        assertTrue(eventAuditOptions.is(eventAuditOptions.getValue()));
        assertFalse(eventAuditOptions.is((byte) (eventAuditOptions.getValue()-1)));
    }

    @DataProvider
    public Object[][] data_isSet() {
        return new Object[][] {
                // POLICY_AUDIT_EVENT_FAILURE
                {EventAuditOptions.POLICY_AUDIT_EVENT_FAILURE, 0x00000002, true},
                {EventAuditOptions.POLICY_AUDIT_EVENT_FAILURE, 0x00000007, true},
                {EventAuditOptions.POLICY_AUDIT_EVENT_FAILURE, 0x00000001, false},
                {EventAuditOptions.POLICY_AUDIT_EVENT_FAILURE, 0x00000000, false},
                // POLICY_AUDIT_EVENT_SUCCESS
                {EventAuditOptions.POLICY_AUDIT_EVENT_SUCCESS, 0x00000001, true},
                {EventAuditOptions.POLICY_AUDIT_EVENT_SUCCESS, 0x00000003, true},
                {EventAuditOptions.POLICY_AUDIT_EVENT_SUCCESS, 0x00000004, false},
                {EventAuditOptions.POLICY_AUDIT_EVENT_SUCCESS, 0x00000000, false},
                // POLICY_AUDIT_EVENT_SUCCESS
                {EventAuditOptions.POLICY_AUDIT_EVENT_NONE, 0x00000004, true},
                {EventAuditOptions.POLICY_AUDIT_EVENT_NONE, 0x00000007, true},
                {EventAuditOptions.POLICY_AUDIT_EVENT_NONE, 0x00000003, false},
                {EventAuditOptions.POLICY_AUDIT_EVENT_NONE, 0x00000000, false},
                // POLICY_AUDIT_EVENT_SUCCESS
                {EventAuditOptions.POLICY_AUDIT_EVENT_UNCHANGED, 0x00000000, false},
                {EventAuditOptions.POLICY_AUDIT_EVENT_UNCHANGED, 0x00000001, false},
        };
    }

    @Test(dataProvider = "data_isSet")
    public void test_isSet(EventAuditOptions eventAuditOptions, int value, boolean expect) {
        assertEquals(eventAuditOptions.isSet(value), expect);
    }
}
