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
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServicesAcceptedControls;
import com.rapid7.client.dcerpc.msvcctl.objects.LPServiceStatus;

import static junit.framework.TestCase.assertTrue;
import static org.testng.Assert.assertEquals;

public class Test_RQueryServiceStatus {

    @Test
    public void parseRQueryServiceStatusResponse() throws IOException {
        RQueryServiceStatusResponse response = new RQueryServiceStatusResponse();
        response.fromHexString("200000000100000000000000000000000000000000000000d007000000000000");
        LPServiceStatus responseObj = response.getLpServiceStatus();
        assertTrue(SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()));
        assertEquals(responseObj.getDwServiceType(), ServiceType.WIN32_SHARE_PROCESS.getValue());
        assertEquals(responseObj.getDwCurrentState(), ServiceStatusType.SERVICE_STOPPED.getValue());
        assertEquals(responseObj.getDwControlsAccepted(), ServicesAcceptedControls.SERVICE_ACCEPT_NONE.getValue());
        assertEquals(responseObj.getDwWin32ExitCode(), 0);
        assertEquals(responseObj.getDwServiceSpecificExitCode(), 0);
        assertEquals(responseObj.getDwCheckPoint(), 0);
        assertEquals(responseObj.getDwWaitHint(), 2000);
    }

    @Test
    public void encodeQueryServiceStatus() throws IOException {
        byte[] testHandle = Hex.decode("0000000055ab1cbf80636842a6058144898e7da4");
        final RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(testHandle);
        assertEquals(request.toHexString(), "0000000055ab1cbf80636842a6058144898e7da4");
    }
}
