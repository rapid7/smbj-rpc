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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;

/**
 * <blockquote><pre>The LsarClose method frees the resources held by a context handle that was opened earlier. After response, the context handle will no longer be usable, and any subsequent uses of this handle will fail.
 *
 *      NTSTATUS LsarClose(
 *          [in, out] LSAPR_HANDLE* ObjectHandle
 *      );
 *  ObjectHandle: The context handle to be freed. On response, it MUST be set to 0.</pre></blockquote>
 */
public class LsarCloseRequest extends RequestCall<LsarCloseResponse> {
    private final static short OP_NUM = 0;
    // <NDR: fixed array> [in, out] LSAPR_HANDLE* ObjectHandle
    private final ContextHandle objectHandle;

    public LsarCloseRequest(final ContextHandle objectHandle) {
        super(OP_NUM);
        this.objectHandle = objectHandle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in, out] LSAPR_HANDLE* ObjectHandle
        packetOut.writeMarshallable(this.objectHandle);
    }

    @Override
    public LsarCloseResponse getResponseObject() {
        return new LsarCloseResponse();
    }

}
