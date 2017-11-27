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

import com.rapid7.client.dcerpc.dto.ContextHandle;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.testng.AssertJUnit.assertEquals;

public class Test_RCloseHandle {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void encodeCloseHandleRequest() throws IOException {
        ContextHandle handle = new ContextHandle(Hex.decode("000000001CCD2628477770489D015EEE8CCFFB01"));
        RCloseServiceHandleRequest request = new RCloseServiceHandleRequest(handle);
        assertEquals(request.toHexString(), "000000001ccd2628477770489d015eee8ccffb01" );
    }
}
