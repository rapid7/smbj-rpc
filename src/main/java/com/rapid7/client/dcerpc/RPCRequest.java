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
package com.rapid7.client.dcerpc;

import java.nio.ByteBuffer;
import java.util.EnumSet;
import com.hierynomus.smbj.transport.TransportException;

public class RPCRequest<T extends RPCResponse> extends Header {
    protected RPCRequest(final PDUType pduType, final EnumSet<PFCFlag> pfcFlags) {
        super(pduType, pfcFlags);
    }

    public T unmarshal(final byte[] responseBytes, final int callID)
        throws TransportException {
        final ByteBuffer response = ByteBuffer.wrap(responseBytes);
        final RPCResponse responseObj = new RPCResponse(response);
        if (callID != responseObj.getCallID()) {
            throw new TransportException(String.format("Call ID mismatch: %d != %d", callID, responseObj.getCallID()));
        }
        switch (responseObj.getPDUType()) {
            case REQUEST:
                return parsePDURequest(response);
            case PING:
                return parsePDUPing(response);
            case RESPONSE:
                return parsePDUResponse(response);
            case FAULT:
                return parsePDUFault(response);
            case WORKING:
                return parsePDUWorking(response);
            case NOCALL:
                return parsePDUNoCall(response);
            case REJECT:
                return parsePDUReject(response);
            case ACK:
                return parsePDUACK(response);
            case CL_CANCEL:
                return parsePDUCLCancel(response);
            case FACK:
                return parsePDUFACK(response);
            case CANCEL_ACK:
                return parsePDUCancelACK(response);
            case BIND:
                return parsePDUBind(response);
            case BIND_ACK:
                return parsePDUBindACK(response);
            case BIND_NAK:
                return parsePDUBindNAK(response);
            case ALTER_CONTEXT:
                return parsePDUAlterContext(response);
            case ALTER_CONTEXT_RESP:
                return parsePDUAlterContextResp(response);
            case SHUTDOWN:
                return parsePDUShutdown(response);
            case CO_CANCEL:
                return parsePDUCOCancel(response);
            case ORPHANED:
                return parsePDUOrphaned(response);
            default:
                throw new TransportException("Unsupported PDU type in response message: " + responseObj.getPDUType());
        }
    }

    protected T parsePDURequest(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.REQUEST);
    }

    protected T parsePDUPing(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.PING);
    }

    protected T parsePDUResponse(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.RESPONSE);
    }

    protected T parsePDUFault(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.FAULT);
    }

    protected T parsePDUWorking(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.WORKING);
    }

    protected T parsePDUNoCall(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.NOCALL);
    }

    protected T parsePDUReject(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.REJECT);
    }

    protected T parsePDUACK(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.ACK);
    }

    protected T parsePDUCLCancel(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.CL_CANCEL);
    }

    protected T parsePDUFACK(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.FACK);
    }

    protected T parsePDUCancelACK(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.CANCEL_ACK);
    }

    protected T parsePDUBind(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.BIND);
    }

    protected T parsePDUBindACK(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.BIND_ACK);
    }

    protected T parsePDUBindNAK(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.BIND_NAK);
    }

    protected T parsePDUAlterContext(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.ALTER_CONTEXT);
    }

    protected T parsePDUAlterContextResp(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.ALTER_CONTEXT_RESP);
    }

    protected T parsePDUShutdown(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.SHUTDOWN);
    }

    protected T parsePDUCOCancel(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.CO_CANCEL);
    }

    protected T parsePDUOrphaned(final ByteBuffer response)
        throws TransportException {
        throw new TransportException("Unsupported PDU type in response message: " + PDUType.ORPHANED);
    }
}
