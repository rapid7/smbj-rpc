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

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssrvs.NetprPathType;
import java.io.IOException;

public class NetrPathCanonicalizeResponse extends RequestResponse
{
    private String canonicalizedPath;
    private int pathType;
    private int returnValue;

    public void unmarshal(final PacketInput packetIn)
        throws IOException {
        canonicalizedPath = packetIn.readChars();
        pathType = packetIn.readInt();
        returnValue = packetIn.readInt();
    }

    public String getCanonicalizedPath() {
        return canonicalizedPath;
    }

    public NetprPathType getPathType() {
        return NetprPathType.getid(pathType);
    }

    public SystemErrorCode getReturnValue() {
        return SystemErrorCode.getErrorCode(returnValue);
    }
}
