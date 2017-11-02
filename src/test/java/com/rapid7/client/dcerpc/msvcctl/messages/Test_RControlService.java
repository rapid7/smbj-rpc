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
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServicesAcceptedControls;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceStatusInfo;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_RControlService
{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void parseRQueryServiceStatusResponse()
            throws IOException {
        final RQueryServiceStatusResponse response = new RQueryServiceStatusResponse();
        response.fromHexString("2000000001000000000000000000000000000000000000000000000026040000");

        IServiceStatusInfo expectedResponse = new ServiceStatusInfo(
                ServiceType.WIN32_SHARE_PROCESS,
                ServiceStatusType.SERVICE_STOPPED,
                ServicesAcceptedControls.SERVICE_ACCEPT_NONE,
            0,
            0,
            0,
            0
        );
        assertTrue(SystemErrorCode.ERROR_SERVICE_NOT_ACTIVE.is(response.getReturnValue()));
        assertEquals(expectedResponse, response.getServiceStatusInfo());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeRControlServiceRequest()
            throws IOException {
        final ContextHandle testServiceHandle = new ContextHandle("00000000c631745ab255a2409443ae2da3216e40");
        final RControlServiceRequest request = new RControlServiceRequest(testServiceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);

        assertEquals(request.toHexString(), "00000000c631745ab255a2409443ae2da3216e4001000000");
    }


}
