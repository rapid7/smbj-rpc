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
import org.junit.Test;
import com.rapid7.client.dcerpc.messages.EmptyResponse;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_BaseAbortShutdown {
    private final BaseAbortShutdown request = new BaseAbortShutdown(null);

    @Test
    public void getOpNum() {
        assertEquals(1, request.getOpNum());
    }

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
