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
 * <a href="https://msdn.microsoft.com/en-us/library/cc245921.aspx">RControlService</a>
 * <blockquote><pre>The RControlService method receives a control code for a specific service handle, as specified by the client.
 *
 *      DWORD RControlService(
 *          [in] SC_RPC_HANDLE hService,
 *          [in] DWORD dwControl,
 *          [out] LPSERVICE_STATUS lpServiceStatus
 *      );
 *
 * hService: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the service record that MUST have been created previously using one of the open methods specified in section 3.1.4.
 * dwControl: Requested control code.
 * lpServiceStatus: Pointer to a SERVICE_STATUS (section 2.2.47) structure that receives the latest service status information. The returned information reflects the most recent status that the service reported to the SCM.</pre></blockquote>
 */
public class RControlServiceRequest extends RequestCall<RQueryServiceStatusResponse> {
    public final static short OP_NUM = 1;
    // <NDR: fixed array> [in] SC_RPC_HANDLE hService
    private final byte[] serviceHandle;
    // <NDR: fixed array> [in] DWORD dwControl
    private final int operation;

    public RControlServiceRequest(final byte[] handle, final int operation) {
        super(OP_NUM);
        this.serviceHandle = handle;
        this.operation = operation;
    }

    @Override
    public RQueryServiceStatusResponse getResponseObject() {
        return new RQueryServiceStatusResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SC_RPC_HANDLE hService
        packetOut.write(serviceHandle);
        // <NDR: fixed array> [in] DWORD dwControl
        // Alignment: 4 - Already aligned
        packetOut.writeInt(operation);
    }
}
