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
package com.rapid7.client.dcerpc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test_PFCFlag {
    @Test
    public void first_fragment() {
        assertEquals(0x01, PFCFlag.FIRST_FRAGMENT.getValue());
    }

    @Test
    public void last_fragment() {
        assertEquals(0x02, PFCFlag.LAST_FRAGMENT.getValue());
    }

    @Test
    public void pending_cancel() {
        assertEquals(0x04, PFCFlag.PENDING_CANCEL.getValue());
    }

    @Test
    public void concurrent_multiplexing() {
        assertEquals(0x10, PFCFlag.CONCURRENT_MULTIPLEXING.getValue());
    }

    @Test
    public void did_not_execute() {
        assertEquals(0x20, PFCFlag.DID_NOT_EXECUTE.getValue());
    }

    @Test
    public void maybe() {
        assertEquals(0x40, PFCFlag.MAYBE.getValue());
    }

    @Test
    public void object_uuid() {
        assertEquals(0x80, PFCFlag.OBJECT_UUID.getValue());
    }
}
