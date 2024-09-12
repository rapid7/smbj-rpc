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
package com.rapid7.client.dcerpc.io;

import com.google.common.io.BaseEncoding;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class HexifyImpl implements Hexify {
    @Override
    public String toHexString() throws IOException {
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);
        marshal(packetOut);
        final byte[] packetOutBytes = packetOutputStream.toByteArray();
        return BaseEncoding.base16().lowerCase().encode(packetOutBytes);
    }

    @Override
    public void fromHexString(final String hexIn) throws IOException {
        final byte[] packetInBytes = BaseEncoding.base16().decode(hexIn.replaceAll("\\s", "").toUpperCase());
        final ByteArrayInputStream packetInputStream = new ByteArrayInputStream(packetInBytes);
        final PacketInput packetIn = new PacketInput(packetInputStream);
        unmarshal(packetIn);
    }
}
