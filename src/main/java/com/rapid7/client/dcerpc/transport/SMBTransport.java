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
package com.rapid7.client.dcerpc.transport;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.Transport;
import com.rapid7.helper.smbj.share.NamedPipe;

public class SMBTransport extends RPCTransport {
    private final NamedPipe namedPipe;

    public SMBTransport(final NamedPipe namedPipe) {
        this.namedPipe = namedPipe;
    }

    @Override
    public int transact(final byte[] packetOut, final byte[] packetIn)
        throws IOException {
        final byte[] packetInBytes = namedPipe.transact(packetOut);
        System.arraycopy(packetInBytes, 0, packetIn, 0, packetInBytes.length);
        return packetInBytes.length;
    }

    @Override
    public void write(final byte[] packetOut)
        throws IOException {
        namedPipe.write(packetOut);
    }

    @Override
    public int read(final byte[] packetIn)
        throws IOException {
        final byte[] packetInBytes = namedPipe.read();
        System.arraycopy(packetInBytes, 0, packetIn, 0, packetInBytes.length);
        return packetInBytes.length;
    }
}
