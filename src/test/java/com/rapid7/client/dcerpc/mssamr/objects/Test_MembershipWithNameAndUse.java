/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.objects;

import org.junit.Test;
import com.rapid7.client.dcerpc.mssamr.dto.MembershipWithNameAndUse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test_MembershipWithNameAndUse {

    private final MembershipWithNameAndUse member1 = new MembershipWithNameAndUse(1, "test1", 4);
    private final MembershipWithNameAndUse member2 = new MembershipWithNameAndUse(1, "test1", 1);
    private final MembershipWithNameAndUse member3 = new MembershipWithNameAndUse(1, "test1", 1);
    private final MembershipWithNameAndUse member4 = new MembershipWithNameAndUse(1, "test2", 1);
    private final MembershipWithNameAndUse member5 = new MembershipWithNameAndUse(2, "test2", 1);

    @Test
    public void equals() {
        assertTrue(member2.equals(member3));
        assertFalse(member1.equals(member2));
        assertFalse(member1.equals(member3));
        assertFalse(member1.equals(member4));
        assertFalse(member1.equals(member5));
    }

    @Test
    public void testHashCode() {
        assertTrue(member2.hashCode() == member3.hashCode());
        assertFalse(member1.hashCode() == member2.hashCode());
        assertFalse(member1.hashCode() == member3.hashCode());
        assertFalse(member1.hashCode() == member4.hashCode());
        assertFalse(member1.hashCode() == member5.hashCode());
    }
}
