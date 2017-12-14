/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * <blockquote><pre>The RCloseServiceHandle method is called by the client. In response, the server releases the handle to the specified service or the SCM database.
 *
 *      DWORD RCloseServiceHandle(
 *          [in, out] LPSC_RPC_HANDLE hSCObject
 *      );
 *
 * hSCObject: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to a service record or to the SCM database that MUST have been created previously using one of the open methods specified in section 3.1.4.</pre></blockquote>
 */
public class RCloseServiceHandleRequest extends RequestCall<HandleResponse> {
    public static final short OP_NUM = 0;
    // <NDR: fixed array> [in, out] LPSC_RPC_HANDLE hSCObject
    private final byte[] hSCObject;

    public RCloseServiceHandleRequest(final byte[] hSCObject) {
        super(OP_NUM);
        this.hSCObject = hSCObject;
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in, out] LPSC_RPC_HANDLE hSCObject
        packetOut.write(hSCObject);
    }
}
