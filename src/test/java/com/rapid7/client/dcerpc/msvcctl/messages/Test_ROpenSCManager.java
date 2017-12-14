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
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.ServiceControlManagerService;
import com.rapid7.client.dcerpc.objects.WChar;

import static org.testng.Assert.assertEquals;

public class Test_ROpenSCManager {
    @SuppressWarnings("unchecked")
    @Test
    public void parseROpenSCManagerResponse() throws IOException {
        final HandleResponse response = new HandleResponse();
        response.fromHexString("000000003a2177b63ee8844398f35fc12e3a8fc500000000");
        byte[] managerHandle = Hex.decode("000000003a2177b63ee8844398f35fc12e3a8fc5");
        assertEquals(SystemErrorCode.ERROR_SUCCESS.getValue(), response.getReturnValue());
        assertEquals(managerHandle, response.getHandle());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeROpenSCManagerRequest_nullDatabase() throws IOException {
        final ROpenSCManagerWRequest request = new ROpenSCManagerWRequest(WChar.NullTerminated.of("test"), null, ServiceControlManagerService.FULL_ACCESS);
        assertEquals(request.toHexString(), "00000200050000000000000005000000740065007300740000000000000000003f000f00");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeROpenSCManagerRequest() throws IOException {
        final ROpenSCManagerWRequest request = new ROpenSCManagerWRequest(WChar.NullTerminated.of("test"), WChar.NullTerminated.of("testdb"), ServiceControlManagerService.FULL_ACCESS);
        assertEquals(request.toHexString(), "0000020005000000000000000500000074006500730074000000000004000200070000000000000007000000740065007300740064006200000000003f000f00");
    }


}
