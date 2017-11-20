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
package com.rapid7.client.dcerpc.mslsad.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

public class LsarClosePolicyRpcResponse extends RequestResponse {
    private byte[] handle;

    public byte[] getPolicyHandle() {
        return handle;
    }

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
      /*
       * Rpc Info
       *
       * MajorVer: 05
       * MinorVer: 00
       * PacketType: 02 (Response)
       * Flags: 03
       * PackType: 10000000
       * FragLen: 3000
       * AuthLen: 0000
       * CallId: 01000000
       * AllocHint: 18000000
       * ContextId: 0000
       * CancelCount: 00
       * Rsvd: 00
       * PolicyHnd: 00000000A6327DA8A113D7119878C915DD98180A
       * Status: 00000000
       */
        handle = packetIn.readRawBytes(20);
    }

}
