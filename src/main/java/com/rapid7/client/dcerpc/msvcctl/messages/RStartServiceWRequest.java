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
import com.rapid7.client.dcerpc.messages.EmptyResponse;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245957.aspx">RStartServiceW</a>
 * <blockquote><pre>The RStartServiceW method starts a specified service.
 *      DWORD RStartServiceW(
 *          [in] SC_RPC_HANDLE hService,
 *          [in, range(0, SC_MAX_ARGUMENTS)] DWORD argc,
 *          [in, unique, size_is(argc)] LPSTRING_PTRSW argv
 *      );
 *
 * hService: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the service record that MUST have been created previously using one of the open methods specified in section 3.1.4. The SERVICE_START access right MUST have been granted to the caller when the RPC context handle to the service record was created.
 * argc: The number of argument strings in the argv array. If argv is NULL, this parameter MAY be 0.
 * argv: A pointer to a buffer that contains an array of pointers to null-terminated UNICODE strings that are passed as arguments to the service.</pre></blockquote>
 */
public class RStartServiceWRequest extends RequestCall<EmptyResponse> {
    public final static short OP_NUM = 19;
    // <NDR: fixed array> [in] SC_RPC_HANDLE hService
    private final byte[] hService;

    public RStartServiceWRequest(final byte[] hService) {
        super(OP_NUM);
        this.hService = hService;
    }

    @Override
    public EmptyResponse getResponseObject() {
        return new EmptyResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SC_RPC_HANDLE hService
        packetOut.write(this.hService);
        // <NDR: pointer[conformant array]> [in, range(0, SC_MAX_ARGUMENTS)] DWORD argc
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        packetOut.writeNull(); //argc (not implemented)
        // <NDR: pointer[struct]> [in, unique, size_is(argc)] LPSTRING_PTRSW argv
        // Alignment: 4 - Already aligned
        packetOut.writeNull(); //argv (not implemented)
    }
}
