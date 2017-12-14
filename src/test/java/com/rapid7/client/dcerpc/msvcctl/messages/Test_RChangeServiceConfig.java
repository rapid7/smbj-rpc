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
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceConfigInfo;
import com.rapid7.client.dcerpc.objects.WChar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class Test_RChangeServiceConfig {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void parseRChangeServiceConfigResponse_nullTag() throws IOException {
        final RChangeServiceConfigWResponse response = new RChangeServiceConfigWResponse();
        response.fromHexString("0000000000000000");

        assertNull(response.getTagId());
        assertTrue(SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void parseRChangeServiceConfigResponse() throws IOException {
        final RChangeServiceConfigWResponse response = new RChangeServiceConfigWResponse();
        response.fromHexString("000002000100000057000000");

        assertEquals(1, (int) response.getTagId());
        assertTrue(SystemErrorCode.ERROR_INVALID_PARAMETER.is(response.getReturnValue()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeRChangeServiceConfigRequest_nullParams() throws IOException {
        byte[] testHandle = Hex.decode("000000004ba33dee35ec1246bd1a407779babf11");
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(testHandle,
                ServiceType.NO_CHANGE.getValue(), ServiceStartType.DEMAND_START.getValue(),
                ServiceError.NORMAL.getValue(), null, null, 0, null, null, null, null);
        assertEquals("000000004ba33dee35ec1246bd1a407779babf11ffffffff0300000001000000000000000000000000000000000000000000000000000000000000000000000000000000", request.toHexString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeRChangeServiceConfigRequest() throws IOException {
        byte[] testHandle = Hex.decode("00000000f3fdced6b714df4ba7c770d115f24601");
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(testHandle,
                ServiceType.NO_CHANGE.getValue(), ServiceStartType.DEMAND_START.getValue(),
                ServiceError.NORMAL.getValue(), WChar.NullTerminated.of("Some binary path"),
                WChar.NullTerminated.of("TestLOG"), 1, new String[]{"abc"}, WChar.NullTerminated.of("TestStartName"), "Password", WChar.NullTerminated.of("TestDisplayName"));
        assertEquals("00000000f3fdced6b714df4ba7c770d115f24601ffffffff03000000010000000000020011000000000000001100000053006f006d0065002000620069006e0061007200790020007000610074006800000000000400020008000000000000000800000054006500730074004c004f004700000008000200010000000c0002000a0000006100620063000000000000000a000000100002000e000000000000000e0000005400650073007400530074006100720074004e0061006d00650000001400020012000000500061007300730077006f0072006400000000001200000018000200100000000000000010000000540065007300740044006900730070006c00610079004e0061006d0065000000", request.toHexString());
    }
}
