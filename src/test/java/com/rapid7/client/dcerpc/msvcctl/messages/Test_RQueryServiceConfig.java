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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_RQueryServiceConfig {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void parseRQueryServiceConfigResponse() throws IOException {
        /*
        ServiceConfigInfo expectedResponse = new ServiceConfigInfo(ServiceType.WIN32_SHARE_PROCESS, ServiceStartType.DEMAND_START, ServiceError.NORMAL, "something", "", 0, "RPCSS/", "NT AUTHORITY\\LocalService", "Remote Registry");
        RQueryServiceConfigWResponse response = new RQueryServiceConfigWResponse();
        response.fromHexString("200000000300000001000000000002000400020000000000080002000c000200100002000a000000000000000a00000073006f006d0065007400680069006e006700000001000000000000000100000000000000070000000000000007000000520050004300530053002f00000000001a000000000000001a0000004e005400200041005500540048004f0052004900540059005c004c006f00630061006c0053006500720076006900630065000000100000000000000010000000520065006d006f00740065002000520065006700690073007400720079000000dc00000000000000");
        assertEquals(expectedResponse, response.getServiceConfigInfo());
        assertTrue(SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()));
        */
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeQueryServiceConfig() throws IOException {
        byte[] testHandle = Hex.decode("000000000815c0569f05b143befdb8e2112c22d9");
        final RQueryServiceConfigWRequest request = new RQueryServiceConfigWRequest(testHandle, RQueryServiceConfigWRequest.MAX_BUFFER_SIZE);
        assertEquals(request.toHexString(), "000000000815c0569f05b143befdb8e2112c22d900200000");
    }


}
