/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssrvs.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssrvs.NetrOpCode;
import java.io.IOException;

public class NetrPathCanonicalizeRequest extends RequestCall<NetrPathCanonicalizeResponse>
{
    final String serverName;
    final String pathName;
    final String prefix;
    final int outBufLen;
    final int pathType;
    final int flags;

    //private final static int MAX_BUFFER_SIZE = 64000;

    public NetrPathCanonicalizeRequest(String serverName, String pathName, int outBufLen, String prefix, int pathType, int flags) {
        super(NetrOpCode.NetprPathCanonicalize.getOpCode());
        this.serverName = serverName;
        this.pathName = pathName;
        this.prefix = prefix;
        this.outBufLen = outBufLen;
        this.pathType = pathType;
        this.flags = flags;
    }

    public void marshal(final PacketOutput stubOut)
        throws IOException
    {

    }

    @Override
    public NetrPathCanonicalizeResponse getResponseObject() {
        return new NetrPathCanonicalizeResponse();
    }
}
