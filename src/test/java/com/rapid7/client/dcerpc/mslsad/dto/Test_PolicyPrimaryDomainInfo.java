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

package com.rapid7.client.dcerpc.mslsad.dto;

import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.dto.SID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertSame;

public class Test_PolicyPrimaryDomainInfo {

    @Test
    public void test_getters() {
        final String name = "Test";
        final SID sid = new SID((byte) 1, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{32});
        PolicyPrimaryDomainInfo obj = new PolicyPrimaryDomainInfo(name, sid);
        assertSame(obj.getName(), name);
        assertSame(obj.getSID(), sid);
    }

    @Test
    public void test_hashCode() {
        PolicyPrimaryDomainInfo obj1 = new PolicyPrimaryDomainInfo("Test", new SID((byte) 1, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{32}));
        PolicyPrimaryDomainInfo obj2 = new PolicyPrimaryDomainInfo("Test", new SID((byte) 2, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{32}));
        PolicyPrimaryDomainInfo obj3 = new PolicyPrimaryDomainInfo("Test", new SID((byte) 1, new byte[]{0, 0, 0, 0, 0, 6}, new long[]{32}));
        assertEquals(obj1.hashCode(), obj1.hashCode());
        assertNotEquals(obj1.hashCode(), obj2.hashCode());
        assertNotEquals(obj1.hashCode(), obj3.hashCode());
    }

    @Test
    public void test_equals() {
        PolicyPrimaryDomainInfo obj1 = new PolicyPrimaryDomainInfo("Test", new SID((byte) 1, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{32}));
        PolicyPrimaryDomainInfo obj2 = new PolicyPrimaryDomainInfo("Test", new SID((byte) 2, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{32}));
        PolicyPrimaryDomainInfo obj3 = new PolicyPrimaryDomainInfo("Test", new SID((byte) 1, new byte[]{0, 0, 0, 0, 0, 6}, new long[]{32}));
        assertEquals(obj1, obj1);
        assertNotEquals(obj1, null);
        assertNotEquals(obj1, obj2);
        assertNotEquals(obj1, obj3);
    }

    @Test
    public void test_toString() {
        PolicyPrimaryDomainInfo obj = new PolicyPrimaryDomainInfo("Test", new SID((byte) 1, new byte[]{0, 0, 0, 0, 0, 5}, new long[]{32}));
        assertEquals(obj.toString(), "PolicyPrimaryDomainInfo{name:\"Test\", sid:S-1-5-32}");
    }
}
