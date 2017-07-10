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
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

public class Test_BaseRegOpenKey {
    private final ContextHandle contextHandle = new ContextHandle("0000000032daf234b77c86409d29efe60d326683");
    private final BaseRegOpenKey request = new BaseRegOpenKey(contextHandle,
        "Software\\Microsoft\\Windows NT\\CurrentVersion", 0, EnumSet.of(AccessMask.MAXIMUM_ALLOWED));

    @Test
    public void request() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 164, Call: 9, Ctx: 0, [Resp: #11204]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Request (0)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 164
        //      Auth Length: 0
        //      Call ID: 9
        //      Alloc hint: 164
        //      Context ID: 0
        //      Opnum: 15
        //      [Response in frame: 11204]
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
        final byte[] requestBytes = request.marshal(9);
        final String encodedRequest = Hex.toHexString(requestBytes);

        assertEquals(
            "0500000310000000a400000009000000a400000000000f000000000032daf234b77c86409d29efe60d3266835a005a00000002002d000000000000002d00000053006f006600740077006100720065005c004d006900630072006f0073006f00660074005c00570069006e0064006f007700730020004e0054005c00430075007200720065006e007400560065007200730069006f006e00000000000000000000000002",
            encodedRequest);
    }

    @Test
    public void response()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 48, Call: 9, Ctx: 0, [Req: #11198]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 48
        //      Auth Length: 0
        //      Call ID: 9
        //      Alloc hint: 24
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 15
        //      [Request in frame: 11198]
        //      [Time from request: 0.093069590 seconds]
        // Remote Registry Service, OpenKey
        //      Operation: OpenKey (15)
        //      [Request in frame: 11198]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
        //              Handle: 000000000a665393f4666e49a68cd99f269d020f
        //              [Frame handle opened: 11204]
        //              [Frame handle closed: 11415]
        //      Windows Error: WERR_OK (0x00000000)
        final byte[] responseBytes = Hex.decode(
            "050002031000000030000000090000001800000000000000000000000a665393f4666e49a68cd99f269d020f00000000");
        final HandleResponse response = request.unmarshal(responseBytes, 9);

        assertEquals(new ContextHandle("000000000a665393f4666e49a68cd99f269d020f"), response.getHandle());
        assertEquals(0, response.getReturnValue());
    }
}
