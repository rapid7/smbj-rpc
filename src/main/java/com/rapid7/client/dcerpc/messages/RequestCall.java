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
package com.rapid7.client.dcerpc.messages;

import java.io.IOException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import com.rapid7.client.dcerpc.io.*;

public abstract class RequestCall<T extends RequestResponse> extends HexifyImpl implements Packet, Hexify {
    private final short opNum;

    public RequestCall(final short opNum) {
        this.opNum = opNum;
    }

    /**
     * @return The operation # within the interface.
     */
    public short getOpNum() {
        return opNum;
    }

    public byte[] getStub() throws IOException {
        final ByteArrayOutputStream stubOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(stubOutputStream);

        marshal(packetOut);

        return stubOutputStream.toByteArray();
    }

    public abstract T getResponseObject();

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        throw new UnsupportedOperationException("Unmarshal Not Implemented.");
    }
}
