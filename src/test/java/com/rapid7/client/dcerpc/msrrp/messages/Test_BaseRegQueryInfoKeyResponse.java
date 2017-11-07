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

import static org.junit.Assert.assertEquals;

public class Test_BaseRegQueryInfoKeyResponse {
    @Test
    public void unmarshal() throws IOException {
        // Remote Registry Service, QueryInfoKey
        //      Operation: QueryInfoKey (16)
        //      [Request in frame: 11405]
        //      Pointer to Classname (winreg_String)
        //          Classname:
        //              Name Len: 2
        //              Name Size: 0
        //              NULL Pointer: Classname
        //      Pointer to Num Subkeys (uint32)
        //          Num Subkeys: 6
        //      Pointer to Max Subkeylen (uint32)
        //          Max Subkeylen: 22
        //      Pointer to Max Classlen (uint32)
        //          Max Classlen: 0
        //      Pointer to Num Values (uint32)
        //          Num Values: 0
        //      Pointer to Max Valnamelen (uint32)
        //          Max Valnamelen: 0
        //      Pointer to Max Valbufsize (uint32)
        //          Max Valbufsize: 0
        //      Pointer to Secdescsize (uint32)
        //          Secdescsize: 164
        //      Pointer to Last Changed Time (NTTIME)
        //          Last Changed Time: Jun 21, 2017 12:50:30.686403000 EDT
        //      Windows Error: WERR_OK (0x00000000)
        final BaseRegQueryInfoKeyResponse response = new BaseRegQueryInfoKeyResponse();

        response.fromHexString("0200000000000000060000001600000000000000000000000000000000000000a40000009e8b087eaeead20100000000");

        assertEquals(6, response.getSubKeys());
        assertEquals(22, response.getMaxSubKeyLen());
        assertEquals(0, response.getMaxClassLen());
        assertEquals(0, response.getValues());
        assertEquals(0, response.getMaxValueNameLen());
        assertEquals(0, response.getMaxValueLen());
        assertEquals(164, response.getSecurityDescriptor());
        assertEquals(131425374306864030l, response.getLastWriteTime());
        assertEquals(0, response.getReturnValue());
    }
}
