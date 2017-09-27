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

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

public class Test_HandleResponse {
    @Test
    public void unmarshal()
        throws IOException {
        // Remote Registry Service, CloseKey
        //      Operation: CloseKey (5)
        //      [Request in frame: 11424]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle
        //              Handle: 0000000000000000000000000000000000000000
        //      Windows Error: WERR_OK (0x00000000)
        final HandleResponse response = new HandleResponse();

        response.fromHexString("000000000000000000000000000000000000000000000000");

        assertEquals(new ContextHandle(), response.getHandle());
        assertEquals(0, response.getReturnValue());
    }
}
