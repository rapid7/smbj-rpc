/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceManagerAccessLevel;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceConfigInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Test_RCreateService {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void encodeCreateServiceRequest() throws IOException {
        ContextHandle testHandle = new ContextHandle("00000000306786C75D720E489E4D20FF45B9D8A5");
        ServiceConfigInfo serviceConfigInfo = new ServiceConfigInfo(ServiceType.NO_CHANGE, ServiceStartType.DEMAND_START, ServiceError.NORMAL, null, null, 0, null, null, null);
        RCreateServiceWRequest request = new RCreateServiceWRequest(testHandle, serviceConfigInfo, ServiceManagerAccessLevel.ALL_ACCESS, "smbtest" );
        assertEquals(request.toHexString(), "00000000306786c75d720e489e4d20ff45b9d8a508000000000000000800000073006d00620074006500730074000000000000003f000f00ffffffff03000000010000000000000000000000000000000000000000000000000000000000000000000000");
    }
}
