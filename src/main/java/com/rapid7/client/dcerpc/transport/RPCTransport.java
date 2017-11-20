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
package com.rapid7.client.dcerpc.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.mutable.MutableInt;
import com.rapid7.client.dcerpc.Interface;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.PFCFlag;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.Transport;
import com.rapid7.client.dcerpc.messages.*;
import com.rapid7.client.dcerpc.transport.exceptions.RPCFaultException;

public abstract class RPCTransport implements Transport {
    protected final static int DEFAULT_MAX_XMIT_FRAG = 16384;
    protected final static int DEFAULT_MAX_RECV_FRAG = 16384;
    private final AtomicInteger callID = new AtomicInteger();
    private int maxXmitFrag = DEFAULT_MAX_XMIT_FRAG;
    private int maxRecvFrag = DEFAULT_MAX_RECV_FRAG;

    public void bind(final Interface abstractSyntax, final Interface transferSyntax) throws IOException {
        final BindRequest request = new BindRequest(DEFAULT_MAX_XMIT_FRAG, DEFAULT_MAX_RECV_FRAG, abstractSyntax, transferSyntax);
        final ByteArrayOutputStream packetOutputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(packetOutputStream);

        request.setCallID(getCallID());
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        request.marshal(packetOut);

        final byte[] packetInBytes = new byte[getMaxXmitFrag()];
        final int packetInByteLength = transact(packetOutputStream.toByteArray(), packetInBytes);
        final ByteArrayInputStream packetInputStream = new ByteArrayInputStream(packetInBytes, 0, packetInByteLength);
        final PacketInput packetIn = new PacketInput(packetInputStream);
        final BindResponse response = new BindResponse();

        response.unmarshal(packetIn);

        if (!response.isACK()) {
            throw new IOException(String.format("BIND %s (%s) failed.", abstractSyntax.getName(), abstractSyntax.getRepr()));
        }

        setMaxXmitFrag(response.getMaxXmitFrag());
        setMaxRecvFrag(response.getMaxRecvFrag());
    }

    public <T extends RequestResponse> T call(final RequestCall<T> call) throws IOException {
        final ByteArrayOutputStream requestPacketOutputStream = new ByteArrayOutputStream();
        final PacketOutput requestPacketOut = new PacketOutput(requestPacketOutputStream);
        final Request request = new Request();

        request.setCallID(getCallID());
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        request.setOpNum(call.getOpNum());
        request.setStub(call.getStub());
        request.marshal(requestPacketOut);

        final byte[] packetOutBytes = requestPacketOutputStream.toByteArray();
        final byte[] packetInBytes = new byte[getMaxXmitFrag()];
        final MutableInt packetInByteLength = new MutableInt();

        packetInByteLength.setValue(transact(packetOutBytes, packetInBytes));

        final ByteArrayOutputStream responseStubOutputStream = new ByteArrayOutputStream();
        final Response response = new Response();

        for (; ; ) {
            final ByteArrayInputStream packetInputStream = new ByteArrayInputStream(packetInBytes, 0, packetInByteLength.getValue());
            final PacketInput packetIn = new PacketInput(packetInputStream);

            response.unmarshal(packetIn);
            responseStubOutputStream.write(response.getStub());

            final Set<PFCFlag> pfcFlags = response.getPFCFlags();
            if (pfcFlags.contains(PFCFlag.LAST_FRAGMENT)) {
                break;
            }

            packetInByteLength.setValue(read(packetInBytes));
        }
        final byte[] responseStub = responseStubOutputStream.toByteArray();
        final ByteArrayInputStream stubInputStream = new ByteArrayInputStream(responseStub);
        final PacketInput stubIn = new PacketInput(stubInputStream);
        // This is a request call - Expect a Response
        if (response.getPDUType() != PDUType.RESPONSE) {
            // PDUType.REJECT is unexpected in connection-oriented calls
            // but maps to the same 32bit fields, so we can catch it with RPCFaultException as well.
            if (response.getPDUType() == PDUType.FAULT || response.getPDUType() == PDUType.REJECT) {
                throw RPCFaultException.read(stubIn);
            }
            throw new IOException(String.format("Expected PDU %s but got: %s", PDUType.RESPONSE, response.getPDUType()));
        }
        final T result = call.getResponseObject();
        result.unmarshal(stubIn);
        return result;
    }

    protected int getCallID() {
        return callID.getAndIncrement();
    }

    protected int getMaxXmitFrag() {
        return maxXmitFrag;
    }

    protected int getMaxRecvFrag() {
        return maxRecvFrag;
    }

    protected void setMaxXmitFrag(final int maxXmitFrag) {
        this.maxXmitFrag = maxXmitFrag;
    }

    protected void setMaxRecvFrag(final int maxRecvFrag) {
        this.maxRecvFrag = maxRecvFrag;
    }
}
