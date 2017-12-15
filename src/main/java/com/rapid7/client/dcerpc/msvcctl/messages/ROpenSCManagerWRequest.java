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
import com.rapid7.client.dcerpc.msvcctl.ServiceControlManagerService;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245942.aspx">ROpenSCManagerW</a>
 * <blockquote><pre>The ROpenSCManagerW method establishes a connection to server and opens the SCM database on the specified server.
 *
 *      DWORD ROpenSCManagerW(
 *          [in, string, unique, range(0, SC_MAX_COMPUTER_NAME_LENGTH)] SVCCTL_HANDLEW lpMachineName,
 *          [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpDatabaseName,
 *          [in] DWORD dwDesiredAccess,
 *          [out] LPSC_RPC_HANDLE lpScHandle
 *      );
 *
 * lpMachineName: An SVCCTL_HANDLEW (section 2.2.3) data type that defines the pointer to a null-terminated UNICODE string that specifies the server's machine name.
 * lpDatabaseName: A pointer to a null-terminated UNICODE string that specifies the name of the SCM database to open. The parameter MUST be set to NULL, "ServicesActive", or "ServicesFailed".
 * dwDesiredAccess: A value that specifies the access to the database. This MUST be one of the values as specified in section 3.1.4.
 *      The client MUST also have the SC_MANAGER_CONNECT access right.
 * lpScHandle: An LPSC_RPC_HANDLE data type that defines the handle to the newly opened SCM database.</pre></blockquote>
 */
public class ROpenSCManagerWRequest extends RequestCall<HandleResponse> {
    public static final short OP_NUM = 15;
    private static final WChar.NullTerminated EMPTY = WChar.NullTerminated.of("");
    // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_COMPUTER_NAME_LENGTH)] SVCCTL_HANDLEW lpMachineName
    private final WChar.NullTerminated lpMachineName;
    // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpDatabaseName
    private final WChar.NullTerminated lpDatabaseName;
    // <NDR: unsigned long> [in] DWORD dwDesiredAccess
    private final int dwDesiredAccess;

    public ROpenSCManagerWRequest() {
        this(EMPTY, null, ServiceControlManagerService.FULL_ACCESS);
    }

    public ROpenSCManagerWRequest(final WChar.NullTerminated lpMachineName,
            final WChar.NullTerminated lpDatabaseName, final int dwDesiredAccess) {
        super(OP_NUM);
        this.lpMachineName = lpMachineName;
        this.lpDatabaseName = lpDatabaseName;
        this.dwDesiredAccess = dwDesiredAccess;

    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_COMPUTER_NAME_LENGTH)] SVCCTL_HANDLEW lpMachineName
        if (packetOut.writeReferentID(this.lpMachineName)) {
            packetOut.writeMarshallable(this.lpMachineName);
            // Alignment for lpDatabaseName
            packetOut.align(Alignment.FOUR);
        }
        // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpDatabaseName
        if (packetOut.writeReferentID(this.lpDatabaseName)) {
            packetOut.writeMarshallable(this.lpDatabaseName);
            // Alignment for dwDesiredAccess
            packetOut.align(Alignment.FOUR);
        }
        packetOut.writeInt(this.dwDesiredAccess);
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }
}
