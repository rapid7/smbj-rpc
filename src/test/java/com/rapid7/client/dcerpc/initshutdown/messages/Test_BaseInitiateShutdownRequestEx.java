/**
 * Copyright 2020, Vadim Frolov.
 * <p>
 * License: BSD-3-clause
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <p>
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * <p>
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.initshutdown.messages;

import java.io.IOException;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.initshutdown.dto.ShutdownReason;
import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.objects.RegUnicodeString;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class Test_BaseInitiateShutdownRequestEx {
    // Actual bytes in the request
    private final byte[] stub = Hex.decode("000000000000020000000000000000001e000000010000000a000680");
    private final BaseInitiateShutdownRequestEx request = new BaseInitiateShutdownRequestEx(null, RegUnicodeString.NullTerminated.of(null), 30, true, false, EnumSet.of(ShutdownReason.SHTDN_REASON_MAJOR_POWER, ShutdownReason.SHTDN_REASON_MINOR_POWER_SUPPLY, ShutdownReason.SHTDN_REASON_FLAG_PLANNED));

    @Test
    public void getOpNum() {
        assertEquals(2, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        assertEquals(toHexString(stub), toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(EmptyResponse.class));
    }
}
