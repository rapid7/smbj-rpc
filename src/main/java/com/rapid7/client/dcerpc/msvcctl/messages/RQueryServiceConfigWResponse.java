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
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.msvcctl.objects.LPQueryServiceConfigW;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245948.aspx">RQueryServiceConfigW</a>
 * <blockquote><pre>The RQueryServiceConfigW method returns the configuration parameters of the specified service.
 *
 *      DWORD RQueryServiceConfigW(
 *          [in] SC_RPC_HANDLE hService,
 *          [out] LPQUERY_SERVICE_CONFIGW lpServiceConfig,
 *          [in, range(0, 1024*8)] DWORD cbBufSize,
 *          [out] LPBOUNDED_DWORD_8K pcbBytesNeeded
 *      );</pre></blockquote>
 */
public class RQueryServiceConfigWResponse extends RequestResponse {
    // <NDR: struct> [out] LPQUERY_SERVICE_CONFIGW lpServiceConfig
    private LPQueryServiceConfigW lpServiceConfig;
    // <NDR: unsigned long> [out] LPBOUNDED_DWORD_8K pcbBytesNeeded
    private int pcbBytesNeeded;

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        // <NDR: struct> [out] LPQUERY_SERVICE_CONFIGW lpServiceConfig
        this.lpServiceConfig = new LPQueryServiceConfigW();
        packetIn.readUnmarshallable(this.lpServiceConfig);
        // <NDR: unsigned long> [out] LPBOUNDED_DWORD_8K pcbBytesNeeded
        packetIn.align(Alignment.FOUR);
        this.pcbBytesNeeded = packetIn.readInt();
    }

    public LPQueryServiceConfigW getLpServiceConfig() {
        return lpServiceConfig;
    }

    public int getPcbBytesNeeded() {
        return pcbBytesNeeded;
    }
}
