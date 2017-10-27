/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.EmptyResponse;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class Test_RStartService
{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void parseRStartServiceResponse()
            throws IOException {
        final EmptyResponse response = new EmptyResponse();
        response.fromHexString("00000000");
        assertEquals(SystemErrorCode.ERROR_SUCCESS, response.getReturnValue());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeRStartServiceRequest()
            throws IOException {
        ContextHandle dummyServiceHandle = new ContextHandle("000000008d71be1681207a429536b4d0fbb9b1d1");
        RStartServiceWRequest request = new RStartServiceWRequest(dummyServiceHandle);
        assertEquals(request.toHexString(), "000000008d71be1681207a429536b4d0fbb9b1d10000000000000000");
    }


}
