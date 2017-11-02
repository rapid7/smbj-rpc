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
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceConfigInfo;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_RChangeServiceConfig
{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void parseRChangeServiceConfigResponse_nullTag()
        throws IOException {
        final RChangeServiceConfigWResponse response = new RChangeServiceConfigWResponse();
        response.fromHexString("0000000000000000");

        assertEquals(0, response.getTagId());
        assertTrue(SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void parseRChangeServiceConfigResponse()
        throws IOException {
        final RChangeServiceConfigWResponse response = new RChangeServiceConfigWResponse();
        response.fromHexString("000002000100000057000000");

        assertEquals(1, response.getTagId());
        assertTrue(SystemErrorCode.ERROR_INVALID_PARAMETER.is(response.getReturnValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeRChangeServiceConfigRequest_nullParams()
        throws IOException {
        ContextHandle testHandle = new ContextHandle("000000004ba33dee35ec1246bd1a407779babf11");
        ServiceConfigInfo serviceConfigInfo = new ServiceConfigInfo(
                ServiceType.NO_CHANGE,
                ServiceStartType.DEMAND_START,
                ServiceError.NORMAL,
                null,
                null,
                0,
                null,
                null,
                null
        );
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(
                testHandle,
                serviceConfigInfo);
        assertEquals(request.toHexString(), "000000004ba33dee35ec1246bd1a407779babf11ffffffff0300000001000000000000000000000000000000000000000000000000000000000000000000000000000000");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeRChangeServiceConfigRequest()
            throws IOException {
        ContextHandle testHandle = new ContextHandle("00000000f3fdced6b714df4ba7c770d115f24601");
        ServiceConfigInfo serviceConfigInfo = new ServiceConfigInfo(
                ServiceType.NO_CHANGE,
                ServiceStartType.DEMAND_START,
                ServiceError.NORMAL,
                "Some binary path",
                "TestLOG",
                1,
                "abc",
                null,
                "TestDisplayName"
        );
        serviceConfigInfo.setPassword("Password");
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(testHandle, serviceConfigInfo);
        assertEquals(request.toHexString(), "00000000f3fdced6b714df4ba7c770d115f24601ffffffff03000000010000000000020011000000000000001100000053006f006d0065002000620069006e0061007200790020007000610074006800000000000400020008000000000000000800000054006500730074004c004f004700000008000200010000000c00020003000000616263000300000000000000100002000800000050617373776f72640800000014000200100000000000000010000000540065007300740044006900730070006c00610079004e0061006d0065000000");
    }
}
