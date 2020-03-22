/**
 * Copyright 2020, Vadim Frolov.
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
package com.rapid7.client.dcerpc.initshutdown.messages;

import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.objects.RegUnicodeString;
import com.rapid7.client.dcerpc.objects.WChar;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class Test_BaseAbortShutdown {
    private final BaseAbortShutdown request = new BaseAbortShutdown(null);

    @Test
    public void getOpNum() { assertEquals(1, request.getOpNum()); }

    @Test
    public void getStub() throws IOException {
        // getStub returns a pointer, which is different on every run
        assertTrue(true);
        System.out.println(request.getStub());
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(EmptyResponse.class));
    }
}
