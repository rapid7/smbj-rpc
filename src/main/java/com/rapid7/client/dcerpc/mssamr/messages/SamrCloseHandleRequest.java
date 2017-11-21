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
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245722.aspx">SamrCloseHandle</a>
 * <blockquote><pre>The SamrCloseHandle method closes (that is, releases server-side resources used by) any context handle obtained from this RPC interface.
 *
 *      long SamrCloseHandle(
 *          [in, out] SAMPR_HANDLE* SamHandle
 *      );
 *
 *  SamHandle: An RPC context handle, as specified in section 2.2.3.2, representing any context handle returned from this interface.
 *
 *  This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.</pre></blockquote>
 */
public class SamrCloseHandleRequest extends RequestCall<SamrCloseHandleResponse> {
    public static final short OP_NUM = 1;

    // <NDR: fixed array> [in, out] SAMPR_HANDLE* SamHandle
    private final ContextHandle handle;

    public SamrCloseHandleRequest(ContextHandle handle) {
        super(OP_NUM);
        this.handle = handle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in, out] SAMPR_HANDLE* SamHandle
        packetOut.writeMarshallable(this.handle);
    }

    @Override
    public SamrCloseHandleResponse getResponseObject() {
        return new SamrCloseHandleResponse();
    }
}
