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
import com.rapid7.client.dcerpc.msrrp.dto.RegistryValueType;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Test_BaseRegQueryValueResponse {
    @Test
    public void unmarshal() throws IOException {
        // Remote Registry Service, QueryValue
        //      Operation: QueryValue (17)
        //      [Request in frame: 11394]
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020000
        //          Type
        //      Pointer to Data (uint8)
        //          Referent ID: 0x00020004
        //          Max Count: 8
        //          Offset: 0
        //          Actual Count: 8
        //          Data: 54
        //          Data: 0
        //          Data: 46
        //          Data: 0
        //          Data: 51
        //          Data: 0
        //          Data: 0
        //          Data: 0
        //      Pointer to Data Size (uint32)
        //          Referent ID: 0x00020008
        //          Data Size: 8
        //      Pointer to Data Length (uint32)
        //          Referent ID: 0x0002000c
        //          Data Length: 8
        //      Windows Error: WERR_OK (0x00000000)
        final BaseRegQueryValueResponse response = new BaseRegQueryValueResponse();

        response.fromHexString("00000200010000000400020008000000000000000800000036002e003300000008000200080000000c0002000800000000000000");

        assertArrayEquals("6.3\0".getBytes("UTF-16LE"), response.getData().getArray());
        assertEquals(RegistryValueType.REG_SZ.getTypeID(), (int) response.getType());
        assertEquals(0, response.getReturnValue());
    }
}
