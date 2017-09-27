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
package com.rapid7.client.dcerpc.msrrp.messages;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

public class Test_BaseRegCloseKey {
    private final ContextHandle contextHandle = new ContextHandle("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegCloseKey request = new BaseRegCloseKey(contextHandle);

    @Test
    public void getOpNum() {
        assertEquals(5, request.getOpNum());
    }

    @Test
    public void getStub()
        throws IOException {
        // Remote Registry Service, CloseKey
        //      Operation: CloseKey (5)
        //      [Response in frame: 11429]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        assertEquals("0000000032daf234b77c86409d29efe60d326683", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject()
        throws IOException {
        assertThat(request.getResponseObject(), instanceOf(HandleResponse.class));
    }
}
