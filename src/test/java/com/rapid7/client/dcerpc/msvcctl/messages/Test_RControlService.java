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
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceControl;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServicesAcceptedControls;
import com.rapid7.client.dcerpc.msvcctl.objects.LPServiceStatus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class Test_RControlService {

    @Test
    public void parseRQueryServiceStatusResponse() throws IOException {
        final RQueryServiceStatusResponse response = new RQueryServiceStatusResponse();
        response.fromHexString("2000000001000000000000000000000000000000000000000000000026040000");
        final LPServiceStatus responseObj = response.getLpServiceStatus();

        assertTrue(SystemErrorCode.ERROR_SERVICE_NOT_ACTIVE.is(response.getReturnValue()));
        assertEquals(responseObj.getDwServiceType(), ServiceType.WIN32_SHARE_PROCESS.getValue());
        assertEquals(responseObj.getDwCurrentState(), ServiceStatusType.SERVICE_STOPPED.getValue());
        assertEquals(responseObj.getDwControlsAccepted(), ServicesAcceptedControls.SERVICE_ACCEPT_NONE.getValue());
        assertEquals(responseObj.getDwCheckPoint(), 0);
        assertEquals(responseObj.getDwWin32ExitCode(), 0);
        assertEquals(responseObj.getDwServiceSpecificExitCode(), 0);
        assertEquals(responseObj.getDwWaitHint(), 0);
    }

    @Test
    public void encodeRControlServiceRequest() throws IOException {
        final byte[] testServiceHandle = Hex.decode("00000000c631745ab255a2409443ae2da3216e40");
        final RControlServiceRequest request = new RControlServiceRequest(testServiceHandle, ServiceControl.STOP.getValue());

        assertEquals(request.toHexString(), "00000000c631745ab255a2409443ae2da3216e4001000000");
    }


}
