/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;

public abstract class HexifyImpl implements Hexify {
    @Override
    public String toHexString()
        throws IOException {
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);
        marshal(packetOut);
        final byte[] packetOutBytes = packetOutputStream.toByteArray();
        return Hex.toHexString(packetOutBytes);
    }

    @Override
    public void fromHexString(final String hexIn)
        throws IOException {
        final byte[] packetInBytes = Hex.decode(hexIn);
        final ByteArrayInputStream packetInputStream = new ByteArrayInputStream(packetInBytes);
        final PacketInput packetIn = new PacketInput(packetInputStream);
        unmarshal(packetIn);
    }
}
