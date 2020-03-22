/**
 * Copyright 2020, Vadim Frolov.
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
package com.rapid7.client.dcerpc.initshutdown;

import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class Test_ShutdownService {
    @Test(expectedExceptions = {IllegalArgumentException.class},
        expectedExceptionsMessageRegExp = "Expecting non-null transport")
    public void constructorNullTransport() {
        new ShutdownService(null);
    }

    @Test
    public void isRequestCorrect() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final EmptyResponse response = mock(EmptyResponse.class);
        final ShutdownService service = new ShutdownService(transport);

        when(transport.call(any(RequestCall.class))).thenReturn(response);
        when(response.getReturnValue()).thenReturn(ERROR_SUCCESS.getValue());

        final String msg = "Bye-Bye";
        final int timeout = 130;
        final boolean forceAppsClosed = true;
        final boolean reboot = true;

        final int returnCode = service.shutdown(msg, timeout, forceAppsClosed, reboot);
        assertEquals(returnCode, ERROR_SUCCESS.getValue());

        verify(transport, times(1)).call(any(RequestCall.class));
        verify(response, times(2)).getReturnValue();
        verifyNoMoreInteractions(transport, response);
    }
}
