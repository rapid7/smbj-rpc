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

import com.hierynomus.protocol.commons.EnumWithValue;

/**
 * Both connectionless and connection-oriented PDU headers contain a 1-byte field that identifies the PDU type.<br>
 * <br>
 * See: http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm
 */
public enum PDUType implements EnumWithValue<PDUType> {
    /**
     * A client sends a request PDU when it wants to execute a remote operation.
     */
    REQUEST(0),
    /**
     * A client sends a ping PDU when it wants to inquire about an outstanding request.
     */
    PING(1),
    /**
     * A server sends a response PDU if an operation invoked by an idempotent, broadcast or at-most-once request
     * executes successfully.
     */
    RESPONSE(2),
    /**
     * A server sends a fault PDU if an operation incurs a fault while executing on the server side.
     */
    FAULT(3),
    /** A server sends a working PDU in reply to a ping PDU. */
    WORKING(4),
    /** A server sends a nocall PDU in reply to a ping PDU. */
    NOCALL(5),
    /** A server sends a reject PDU if an RPC request is rejected. */
    REJECT(6),
    /**
     * A client sends an ack PDU after it has received a response to an at-most-once request.
     */
    ACK(7),
    /** A client sends a cancel PDU when it has incurred a cancel fault. */
    CL_CANCEL(8),
    /**
     * Both clients and servers send fack PDUs. A client sends a fack PDU after it has received a fragment of a
     * multi-PDU response. A server sends a fack PDU after it has received a fragment of a multi-PDU request.
     */
    FACK(9),
    /** A server sends a cancel_ack PDU after it has received a cancel PDU. */
    CANCEL_ACK(10),
    /**
     * The bind PDU is used to initiate the presentation negotiation for the body data, and optionally, authentication.
     */
    BIND(11),
    /**
     * The bind_ack PDU is returned by the server when it accepts a bind request initiated by the client's bind PDU.
     */
    BIND_ACK(12),
    /**
     * The bind_nak PDU is returned by the server when it rejects an association request initiated by the client's bind
     * PDU.
     */
    BIND_NAK(13),
    /**
     * The alter_context PDU is used to request additional presentation negotiation for another interface and/or
     * version, or to negotiate a new security context, or both.
     */
    ALTER_CONTEXT(14),
    /**
     * The alter_context_response PDU is used to indicate the server's response to an alter_context request.
     */
    ALTER_CONTEXT_RESP(15),
    /**
     * The shutdown PDU is sent by the server to request that a client terminate the connection, freeing the related
     * resources.
     */
    SHUTDOWN(17),
    /** The cancel PDU is used to forward a cancel. */
    CO_CANCEL(18),
    /**
     * The orphaned PDU is used by a client to notify a server that it is aborting a request in progress that has not
     * been entirely transmitted yet, or that it is aborting a (possibly lengthy) response in progress.
     */
    ORPHANED(19);

    private final int value;

    private PDUType(final int value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }
}
