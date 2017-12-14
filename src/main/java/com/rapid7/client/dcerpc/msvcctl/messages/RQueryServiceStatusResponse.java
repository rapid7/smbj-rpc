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
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.msvcctl.objects.LPServiceStatus;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245952.aspx">RQueryServiceStatus</a>
 * <blockquote><pre>The RQueryServiceStatus method returns the current status of the specified service.
 *
 *      DWORD RQueryServiceStatus(
 *          [in] SC_RPC_HANDLE hService,
 *          [out] LPSERVICE_STATUS lpServiceStatus
 *      );
 *
 * hService: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the service record that MUST have been created previously using one of the open methods specified in section 3.1.4. The SERVICE_QUERY_STATUS access right MUST have been granted to the caller when the RPC context handle was created.
 * lpServiceStatus: Pointer to a SERVICE_STATUS (section 2.2.47) structure that contains the status information for the service.</pre></blockquote>
 */
public class RQueryServiceStatusResponse extends RequestResponse {
    // <NDR: struct> [out] LPSERVICE_STATUS lpServiceStatus
    private LPServiceStatus lpServiceStatus;

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        // <NDR: struct> [out] LPSERVICE_STATUS lpServiceStatus
        this.lpServiceStatus = new LPServiceStatus();
        packetIn.readUnmarshallable(this.lpServiceStatus);
    }

    public LPServiceStatus getLpServiceStatus() {
        return this.lpServiceStatus;
    }
}
