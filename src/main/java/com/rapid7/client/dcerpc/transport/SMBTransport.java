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

import static com.hierynomus.protocol.commons.EnumWithValue.EnumUtils.toEnumSet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.EnumSet;
import com.hierynomus.protocol.transport.TransportException;
import com.rapid7.client.dcerpc.PFCFlag;
import com.rapid7.client.dcerpc.RPCRequest;
import com.rapid7.client.dcerpc.RPCResponse;
import com.rapid7.helper.smbj.share.NamedPipe;

public class SMBTransport implements RPCTransport {
    private final NamedPipe namedPipe;
    private int callID = 1;

    public SMBTransport(final NamedPipe namedPipe) {
        this.namedPipe = namedPipe;
    }

    @Override
    public synchronized <T extends RPCResponse> T transact(final RPCRequest<T> request)
        throws IOException {
        final int callID;
        synchronized (this) {
            callID = this.callID++;
        }

        // Need to support fragmentation on transmit.
        final byte[] requestBytes = request.marshal(callID);

        // Need to support fragmentation on receive.
        byte[] responseBytes = namedPipe.transact(requestBytes);

        // Parse out important message properties.
        ByteBuffer response = ByteBuffer.wrap(responseBytes);

        // The response byte structure is in little endian.
        response.order(ByteOrder.LITTLE_ENDIAN);

        // Allocate a complete message buffer using the allocation hint.
        // This could be a security vulnerability if the server decides to provide very large hints.
        final ByteArrayOutputStream completeMessage = new ByteArrayOutputStream(response.getInt(16));

        // Write the header of the first message into the complete message buffer.
        // This header might not have the LAST_FRAGMENT PFC flag set, but nothing downstream checks that right now.
        // This header might not have the correct fragment length for the complete message, but nothing downstream
        // checks that right now.
        completeMessage.write(responseBytes, 0, 24);

        for (;;) {
            // Verify basic fields.
            if (response.get(0) != 5 && response.get(1) != 0) {
                throw new TransportException(
                    String.format("Version mismatch: %d.%d != 5.0", response.get(0), response.get(0)));
            }
            if (response.get(4) != 0x10) {
                throw new TransportException(
                    String.format("Integer and Character representation mismatch: %d", response.get(4)));
            }
            if (response.get(5) != 0) {
                throw new TransportException(
                    String.format("Floating-Point representation mismatch: %d", response.get(5)));
            }
            if (response.get(12) != callID) {
                throw new TransportException(String.format("Call ID mismatch: %d != %d", callID, response.get(12)));
            }

            // Write the stub.
            completeMessage.write(responseBytes, 24, responseBytes.length - 24);

            // Check if this is the last fragment.
            final EnumSet<PFCFlag> pfcFlags = toEnumSet(response.get(3), PFCFlag.class);
            if (pfcFlags.contains(PFCFlag.LAST_FRAGMENT)) {
                break;
            }

            // Read the next fragment.
            // What if the last read/transact contained part of the next fragment?
            // That is not handled right now, but it also has not been observed to happen.
            responseBytes = namedPipe.read();
            response = ByteBuffer.wrap(responseBytes);
        }

        return request.unmarshal(completeMessage.toByteArray(), callID);
    }
}
