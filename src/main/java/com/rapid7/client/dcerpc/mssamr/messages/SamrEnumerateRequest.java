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

public abstract class SamrEnumerateRequest<T extends SamrEnumerateResponse> extends RequestCall<T> {

    private final byte[] handle;
    private final int enumContext;
    private final int maxLength;

    protected SamrEnumerateRequest(short opNum, byte[] handle,
            int enumerationContext, int preferredMaximumLength) {
        super(opNum);
        this.handle = handle;
        this.enumContext = enumerationContext;
        this.maxLength = preferredMaximumLength;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(handle);
        packetOut.writeInt(enumContext);
        packetOut.writeInt(maxLength);
    }
}
