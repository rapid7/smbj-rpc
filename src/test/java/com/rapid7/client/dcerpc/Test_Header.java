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
package com.rapid7.client.dcerpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Test_Header {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void getMajorVersion() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(5, header.getMajorVersion());
    }

    @Test
    public void getMinorVersion() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(0, header.getMinorVersion());
    }

    @Test
    public void getPDUType() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(PDUType.ACK, header.getPDUType());
    }

    @Test
    public void getPFCFlags() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT), header.getPFCFlags());
    }

    @Test
    public void getNDR() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertArrayEquals(new byte[]{0x10, 0x00, 0x00, 0x00}, header.getNDR());
    }

    @Test
    public void getFragLength() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(16, header.getFragLength());
    }

    @Test
    public void getAuthLength() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(0, header.getAuthLength());
    }

    @Test
    public void getCallID() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        assertEquals(10, header.getCallID());
    }

    @Test
    public void setMajorVersion() throws IOException {
        final Header header = new Header();
        header.setMajorVersion((byte) 0);
        assertEquals(0, header.getMajorVersion());
    }

    @Test
    public void setMinorVersion() throws IOException {
        final Header header = new Header();
        header.setMinorVersion((byte) 5);
        assertEquals(5, header.getMinorVersion());
    }

    @Test
    public void setPDUType() throws IOException {
        final Header header = new Header();
        header.setPDUType(PDUType.BIND);
        assertEquals(PDUType.BIND, header.getPDUType());
    }

    @Test
    public void setPFCFlags() throws IOException {
        final Header header = new Header();
        header.setPFCFlags(EnumSet.of(PFCFlag.MAYBE));
        assertEquals(EnumSet.of(PFCFlag.MAYBE), header.getPFCFlags());
    }

    @Test
    public void setNDR() throws IOException {
        final Header header = new Header();
        header.setNDR(new byte[]{0x00, 0x00, 0x00, 0x10});
        assertArrayEquals(new byte[]{0x00, 0x00, 0x00, 0x10}, header.getNDR());
    }

    @Test
    public void setFragLength() throws IOException {
        final Header header = new Header();
        header.setFragLength((short) 32);
        assertEquals(32, header.getFragLength());
    }

    @Test
    public void setAuthLength() throws IOException {
        final Header header = new Header();
        header.setAuthLength((short) 16);
        assertEquals(16, header.getAuthLength());
    }

    @Test
    public void setCallID() throws IOException {
        final Header header = new Header();
        header.setCallID(20);
        assertEquals(20, header.getCallID());
    }

    @Test
    public void unmarshalMarshal() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);

        header.marshal(packetOut);

        final byte[] packetOutputBytes = packetOutputStream.toByteArray();
        final String packetOutputHexString = Hex.toHexString(packetOutputBytes);

        assertEquals(HEADER_HEX_STRING, packetOutputHexString);
    }

    @Test
    public void unmarshal() throws IOException {
        final Header header = unmarshalHeader(HEADER_HEX_STRING);

        assertEquals(5, header.getMajorVersion());
        assertEquals(0, header.getMinorVersion());
        assertEquals(PDUType.ACK, header.getPDUType());
        assertEquals(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT), header.getPFCFlags());
        assertArrayEquals(new byte[]{0x10, 0x00, 0x00, 0x00}, header.getNDR());
        assertEquals(16, header.getFragLength());
        assertEquals(0, header.getAuthLength());
        assertEquals(10, header.getCallID());
    }

    @Test
    public void marshal() throws IOException {
        final Header header = new Header();
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);

        header.setPDUType(PDUType.ACK);
        header.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        header.setCallID(10);
        header.marshal(packetOut);

        final byte[] packetOutputBytes = packetOutputStream.toByteArray();
        final String packetOutputHexString = Hex.toHexString(packetOutputBytes);

        assertEquals(HEADER_HEX_STRING, packetOutputHexString);
    }

    @Test
    public void marshalNoPDUType() throws IOException {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid PDU type: null");

        final Header header = new Header();
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);

        header.marshal(packetOut);
    }

    @Test
    public void marshalNoPFCFlags() throws IOException {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Invalid PFC flag(s): null");

        final Header header = new Header();
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);

        header.setPDUType(PDUType.ACK);
        header.marshal(packetOut);
    }

    private Header unmarshalHeader(final String hexString) throws IOException {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        final PacketInput packetIn = new PacketInput(inputStream);
        final Header header = new Header();

        header.unmarshal(packetIn);

        return header;
    }

    private final static String HEADER_HEX_STRING = "0500070310000000100000000a000000";
}
