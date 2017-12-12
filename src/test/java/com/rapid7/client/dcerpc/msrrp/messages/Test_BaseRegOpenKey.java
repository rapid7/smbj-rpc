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

import static org.bouncycastle.util.encoders.Hex.toHexString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.objects.WChar;

public class Test_BaseRegOpenKey {
    private final byte[] contextHandle = Hex.decode("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegOpenKey request = new BaseRegOpenKey(contextHandle,
            RPCUnicodeString.NullTerminated.of("Software\\Microsoft\\Windows NT\\CurrentVersion"), 0,
            (int) EnumUtils.toLong(EnumSet.of(AccessMask.MAXIMUM_ALLOWED)));

    @Test
    public void getOpNum() {
        assertEquals(15, request.getOpNum());
    }

    @Test
    public void getStub() throws IOException {
        // Remote Registry Service, OpenKey
        //      Operation: OpenKey (15)
        //      [Response in frame: 11204]
        //      Pointer to Parent Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Parent Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Keyname: : Software\Microsoft\Windows NT\CurrentVersion
        //          Name Len: 90
        //          Name Size: 90
        //          Keyname: Software\Microsoft\Windows NT\CurrentVersion
        //              Referent ID: 0x00020000
        //              Max Count: 45
        //              Offset: 0
        //              Actual Count: 45
        //              Keyname: Software\Microsoft\Windows NT\CurrentVersion
        //      Options: 0x00000000: (No values set)
        //      Access Mask: 0x02000000
        //          Generic rights: 0x00000000
        //          .... ..1. .... .... .... .... .... .... = Maximum allowed: Set
        //          .... .... 0... .... .... .... .... .... = Access SACL: Not set
        //          Standard rights: 0x00000000
        //          WINREG specific rights: 0x00000000
        assertEquals("0000000032daf234b77c86409d29efe60d3266835a005a00000002002d000000000000002d00000053006f006600740077006100720065005c004d006900630072006f0073006f00660074005c00570069006e0064006f007700730020004e0054005c00430075007200720065006e007400560065007200730069006f006e00000000000000000000000002", toHexString(request.getStub()));
    }

    @Test
    public void getResponseObject() throws IOException {
        assertThat(request.getResponseObject(), instanceOf(HandleResponse.class));
    }
}
