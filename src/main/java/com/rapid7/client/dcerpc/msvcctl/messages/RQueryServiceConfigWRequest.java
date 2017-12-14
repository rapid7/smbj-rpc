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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245948.aspx">RQueryServiceConfigW</a>
 * <blockquote><pre>The RQueryServiceConfigW method returns the configuration parameters of the specified service.
 *
 *      DWORD RQueryServiceConfigW(
 *          [in] SC_RPC_HANDLE hService,
 *          [out] LPQUERY_SERVICE_CONFIGW lpServiceConfig,
 *          [in, range(0, 1024*8)] DWORD cbBufSize,
 *          [out] LPBOUNDED_DWORD_8K pcbBytesNeeded
 *      );
 *
 * hService: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the service record that MUST have been created previously, using one of the open methods specified in section 3.1.4. The SERVICE_QUERY_CONFIG access right MUST have been granted to the caller when the RPC context handle was created.
 * lpServiceConfig: A pointer to a buffer that contains the QUERY_SERVICE_CONFIGW (section 2.2.15) structure.
 * cbBufSize: The size, in bytes, of the lpServiceConfig parameter.
 * pcbBytesNeeded: An LPBOUNDED_DWORD_8K (section 2.2.8) data type that defines the pointer to a variable that contains the number of bytes needed to return all the configuration information if the method fails.</pre></blockquote>
 */
public class RQueryServiceConfigWRequest extends RequestCall<RQueryServiceConfigWResponse> {
    public static final int MAX_BUFFER_SIZE = 8192;
    public static final short OP_NUM = 17;
    // <NDR: fixed array> [in] SC_RPC_HANDLE hService
    private final byte[] hService;
    // <NDR: unsigned long> [in, range(0, 1024*8)] DWORD cbBufSize
    private final int cbBufSize;

    public RQueryServiceConfigWRequest(final byte[] hService, int cbBufSize) {
        super(OP_NUM);
        this.hService = hService;
        this.cbBufSize = cbBufSize;
    }

    @Override
    public RQueryServiceConfigWResponse getResponseObject() {
        return new RQueryServiceConfigWResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SC_RPC_HANDLE hService
        packetOut.write(hService);
        // <NDR: unsigned long> [in, range(0, 1024*8)] DWORD cbBufSize
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        packetOut.writeInt(cbBufSize);
    }
}
