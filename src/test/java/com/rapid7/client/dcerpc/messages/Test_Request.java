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
package com.rapid7.client.dcerpc.messages;

import java.io.IOException;
import java.util.EnumSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.PFCFlag;

import static org.junit.Assert.*;

public class Test_Request {

    @Test
    public void pduType() {
        final Request request = new Request();
        Assert.assertEquals(PDUType.REQUEST, request.getPDUType());
    }

    @Test
    public void getOpNum() {
        final Request request = new Request();
        Assert.assertEquals(0, request.getOpNum());
    }

    @Test
    public void getStub() {
        final Request request = new Request();
        Assert.assertNull(request.getStub());
    }

    @Test
    public void setOpNum() {
        final Request request = new Request();
        request.setOpNum((short) 1);
        Assert.assertEquals(1, request.getOpNum());
    }

    @Test
    public void setStub() {
        final Request request = new Request();
        request.setStub(new byte[]{0x00});
        Assert.assertArrayEquals(new byte[]{0x00}, request.getStub());
    }

    @Test
    public void marshal() throws IOException {
        final Request request = new Request();

        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        request.setStub(new byte[0]);

        Assert.assertEquals("050000031000000018000000000000000000000000000000", request.toHexString());
    }

    @Test
    public void marshalNullStub() throws IOException {
        try {
            final Request request = new Request();

            request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
            request.toHexString();
            Assert.fail();
        } catch (IllegalStateException ex) {
            Assert.assertEquals("Invalid stub: null", ex.getMessage());
        }
    }

    @Test
    public void unmarshal() throws IOException {
        try {
            new Request().unmarshal(null);
            Assert.fail();
        } catch (UnsupportedOperationException ex) {
            Assert.assertEquals("Unmarshal Not Implemented.", ex.getMessage());
        }
    }
}
