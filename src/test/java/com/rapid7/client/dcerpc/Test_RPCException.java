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
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;

import static org.junit.Assert.*;

public class Test_RPCException {
    @Test
    public void getReturnValue_unknownErrorCode() {
        final RPCException rpcException = new RPCException("test", -1);
        assertEquals(-1, rpcException.getReturnValue());
    }

    @Test
    public void getReturnValue() {
        final RPCException rpcException = new RPCException("test", SystemErrorCode.ERROR_SUCCESS.getErrorCode());
        assertEquals(SystemErrorCode.ERROR_SUCCESS.getErrorCode(), rpcException.getReturnValue());
    }

    @Test
    public void getErrorCode_unknownErrorCode() {
        final RPCException rpcException = new RPCException("test", -1);
        assertNull(rpcException.getErrorCode());
    }

    @Test
    public void getErrorCode() {
        final RPCException rpcException = new RPCException("test", SystemErrorCode.ERROR_SUCCESS.getErrorCode());
        assertEquals(SystemErrorCode.ERROR_SUCCESS, rpcException.getErrorCode());
    }

    @Test
    public void hasErrorCode_unknownErrorCode() {
        final RPCException rpcException = new RPCException("test", -1);
        assertFalse(rpcException.hasErrorCode());
    }

    @Test
    public void hasErrorCode() {
        final RPCException rpcException = new RPCException("test", SystemErrorCode.ERROR_SUCCESS.getErrorCode());
        assertTrue(rpcException.hasErrorCode());
    }
}
