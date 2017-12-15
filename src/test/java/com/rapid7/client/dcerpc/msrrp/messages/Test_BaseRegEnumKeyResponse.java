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
package com.rapid7.client.dcerpc.msrrp.messages;

import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.junit.Assert.assertEquals;

public class Test_BaseRegEnumKeyResponse {
    @Test
    public void unmarshal() throws IOException {
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Request in frame: 11177]
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 24
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 12
        //                  Name: 66
        //                  Name: 67
        //                  Name: 68
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 2
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 1
        //                  Name: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: Jun 15, 2017 15:29:36.566813400 EDT
        //      Windows Error: WERR_OK (0x00000000)
        final BaseRegEnumKeyResponse response = new BaseRegEnumKeyResponse();

        response.fromHexString("180000020000020000010000000000000c000000420043004400300030003000300030003000300030000000040002000200feff08000200ff7f00000000000001000000000000000c00020026cd57b90de6d20100000000");

        assertEquals(RPCUnicodeString.NullTerminated.of("BCD00000000"), response.getLpNameOut());
        assertEquals(131420285765668134L, response.getLastWriteTime());
        assertEquals(0, response.getReturnValue());
    }
}
