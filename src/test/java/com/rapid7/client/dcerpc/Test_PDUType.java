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

public class Test_PDUType {
    @Test
    public void request() {
        assertEquals(0, PDUType.REQUEST.getValue());
    }

    @Test
    public void ping() {
        assertEquals(1, PDUType.PING.getValue());
    }

    @Test
    public void response() {
        assertEquals(2, PDUType.RESPONSE.getValue());
    }

    @Test
    public void fault() {
        assertEquals(3, PDUType.FAULT.getValue());
    }

    @Test
    public void working() {
        assertEquals(4, PDUType.WORKING.getValue());
    }

    @Test
    public void nocall() {
        assertEquals(5, PDUType.NOCALL.getValue());
    }

    @Test
    public void reject() {
        assertEquals(6, PDUType.REJECT.getValue());
    }

    @Test
    public void ack() {
        assertEquals(7, PDUType.ACK.getValue());
    }

    @Test
    public void cl_cancel() {
        assertEquals(8, PDUType.CL_CANCEL.getValue());
    }

    @Test
    public void fack() {
        assertEquals(9, PDUType.FACK.getValue());
    }

    @Test
    public void cancel_ack() {
        assertEquals(10, PDUType.CANCEL_ACK.getValue());
    }

    @Test
    public void bind() {
        assertEquals(11, PDUType.BIND.getValue());
    }

    @Test
    public void bind_ack() {
        assertEquals(12, PDUType.BIND_ACK.getValue());
    }

    @Test
    public void bind_nak() {
        assertEquals(13, PDUType.BIND_NAK.getValue());
    }

    @Test
    public void alter_context() {
        assertEquals(14, PDUType.ALTER_CONTEXT.getValue());
    }

    @Test
    public void alter_context_resp() {
        assertEquals(15, PDUType.ALTER_CONTEXT_RESP.getValue());
    }

    @Test
    public void shutdown() {
        assertEquals(17, PDUType.SHUTDOWN.getValue());
    }

    @Test
    public void co_cancel() {
        assertEquals(18, PDUType.CO_CANCEL.getValue());
    }

    @Test
    public void orphaned() {
        assertEquals(19, PDUType.ORPHANED.getValue());
    }
}
