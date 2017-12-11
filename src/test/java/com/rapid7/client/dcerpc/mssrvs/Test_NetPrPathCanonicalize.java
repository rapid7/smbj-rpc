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
package com.rapid7.client.dcerpc.mssrvs;

import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssrvs.dto.NetprPathType;
import com.rapid7.client.dcerpc.mssrvs.messages.NetprPathCanonicalizeRequest;
import com.rapid7.client.dcerpc.mssrvs.messages.NetprPathCanonicalizeResponse;
import com.rapid7.client.dcerpc.objects.WChar;

import static org.junit.Assert.assertEquals;

public class Test_NetPrPathCanonicalize {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    @Test
    public void parseNetprPathCanonicalizeResponse() throws IOException {
        final NetprPathCanonicalizeResponse response = new NetprPathCanonicalizeResponse();
        response.fromHexString("c800000043003a005c0053006f006d0065007400680069006e00670000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000620000000000000");


        assertEquals("C:\\Something", response.getOutBuf());
        assertEquals(SystemErrorCode.ERROR_SUCCESS.getValue(), response.getReturnValue());
        assertEquals(NetprPathType.ITYPE_PATH_ABSD.getId(), response.getPathType());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeNetprPathCanonicalize() throws IOException {
        final NetprPathCanonicalizeRequest request = new NetprPathCanonicalizeRequest(
                WChar.NullTerminated.of("dummy"),
                WChar.NullTerminated.of("C:\\Linux\\..\\Windows\\Something\\.."),
                25,
                WChar.NullTerminated.of("C:\\"),
                0, 0);
        assertEquals(request.toHexString(), "00000200060000000000000006000000640075006d006d007900000021000000000000002100000043003a005c004c0069006e00750078005c002e002e005c00570069006e0064006f00770073005c0053006f006d0065007400680069006e0067005c002e002e00000000001900000004000000000000000400000043003a005c0000000000000000000000");
    }


}
