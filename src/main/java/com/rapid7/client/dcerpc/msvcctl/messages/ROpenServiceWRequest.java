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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245944.aspx">ROpenService</a>
 * <blockquote><pre>The ROpenServiceW method creates an RPC context handle to an existing service record.
 *
 *      DWORD ROpenServiceW(
 *          [in] SC_RPC_HANDLE hSCManager,
 *          [in, string, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpServiceName,
 *          [in] DWORD dwDesiredAccess,
 *          [out] LPSC_RPC_HANDLE lpServiceHandle
 *      );
 *
 * hSCManager: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the SCM database, created using one of the open methods specified in section 3.1.4.
 * lpServiceName: A pointer to a null-terminated UNICODE string that specifies the ServiceName of the service record.
 *      The forward slash, back slash, comma, and space characters are illegal in service names.
 * dwDesiredAccess: A value that specifies the access right. This MUST be one of the values as specified in section 3.1.4.
 * lpServiceHandle: An LPSC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the found service record.</pre></blockquote>
 */
public class ROpenServiceWRequest extends RequestCall<HandleResponse> {
    public final static short OP_NUM = 16;
    // <NDR: fixed array> [in] SC_RPC_HANDLE hSCManager
    private final byte[] hSCManager;
    // <NDR: struct> [in, string, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpServiceName
    private final WChar.NullTerminated lpServiceName;
    // <NDR: unsigned long> [in] DWORD dwDesiredAccess
    private final int dwDesiredAccess;

    public ROpenServiceWRequest(final byte[] hSCManager, final WChar.NullTerminated lpServiceName, final int dwDesiredAccess) {
        super(OP_NUM);
        this.hSCManager = hSCManager;
        this.lpServiceName = lpServiceName;
        this.dwDesiredAccess = dwDesiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SC_RPC_HANDLE hSCManager
        packetOut.write(this.hSCManager);
        // <NDR: struct> [in, string, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpServiceName
        // Not actually a pointer despite what doc says
        packetOut.writeMarshallable(this.lpServiceName);
        packetOut.align(Alignment.FOUR);
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.dwDesiredAccess);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
