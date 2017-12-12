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
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class Test_BaseRegQueryValueRequest {
    private final byte[] contextHandle = Hex.decode("000000000a665393f4666e49a68cd99f269d020f");
    private final BaseRegQueryValueRequest request = new BaseRegQueryValueRequest(contextHandle, RPCUnicodeString.NullTerminated.of("CurrentVersion"), 65536);

    @Test
    public void getOpNum() {
        assertEquals(17, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        // Remote Registry Service, QueryValue
        //      Operation: QueryValue (17)
        //      [Response in frame: 11403]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
        //              Handle: 000000000a665393f4666e49a68cd99f269d020f
        //              [Frame handle opened: 11204]
        //              [Frame handle closed: 11415]
        //      Pointer to Value Name (winreg_String)
        //          Value Name:
        //              Name Len: 30
        //              Name Size: 30
        //              Value Name
        //                  Referent ID: 0x00020000
        //                  Max Count: 15
        //                  Offset: 0
        //                  Actual Count: 15
        //                  Value Name: CurrentVersion
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Data (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 65536
        //          Offset: 0
        //          Actual Count: 0
        //      Pointer to Data Size (uint32)
        //          Referent ID: 0x0002000c
        //          Data Size: 65536
        //      Pointer to Data Length (uint32)
        //          Referent ID: 0x00020010
        //          Data Length: 0
        assertEquals("000000000a665393f4666e49a68cd99f269d020f1e001e00000002000f000000000000000f000000430075007200720065006e007400560065007200730069006f006e00000000000400020000000000080002000000010000000000000000000c000200000001001000020000000000", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(BaseRegQueryValueResponse.class));
    }
}
