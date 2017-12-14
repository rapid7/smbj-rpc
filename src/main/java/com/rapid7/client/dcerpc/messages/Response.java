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
import com.rapid7.client.dcerpc.Header;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;

/**
 * The IDL declaration of the response PDU is as follows:<br>
 *
 * <pre>
 * typedef struct {
 *    // start 8-octet aligned
 *    // common fields
 *    u_int8  rpc_vers = 5;        // 00:01 RPC version
 *    u_int8  rpc_vers_minor;      // 01:01 minor version
 *    u_int8  PTYPE = response;    // 02:01 response PDU
 *    u_int8  pfc_flags;           // 03:01 flags
 *    byte    packed_drep[4];      // 04:04 NDR data rep format label
 *    u_int16 frag_length;         // 08:02 total length of fragment
 *    u_int16 auth_length;         // 10:02 length of auth_value
 *    u_int32 call_id;             // 12:04 call identifier
 *    // end common fields
 *    // needed for request, response, fault
 *    u_int32  alloc_hint;         // 16:04 allocation hint
 *    p_context_id_t p_cont_id;    // 20:02 pres context, i.e. data rep
 *    // needed for response or fault
 *    u_int8  cancel_count         // 22:01 cancel count
 *    u_int8  reserved;            // 23:01 reserved, m.b.z.
 *    // stub data here, 8-octet aligned
 *    //     .
 *    //     .
 *    //     .
 *    // optional authentication verifier
 *    // following fields present iff auth_length != 0
 *    auth_verifier_co_t   auth_verifier; // xx:yy
 * } rpcconn_response_hdr_t;
 * </pre>
 *
 * The response PDU is used to respond to an active call. The p_cont_id field holds a context identifier that identifies
 * the data representation. The cancel_count field holds a count of cancels received.<br>
 * <br>
 * The alloc_hint field is optionally used by the transmitter to provide a hint to the receiver of the amount of buffer
 * space to allocate contiguously for fragmented requests. This is only a potential optimisation. The receiver must work
 * correctly regardless of the value passed. The value 0 (zero) is reserved to indicate that the transmitter is not
 * supplying any information.
 *
 * @see <a href=http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm>CDE 1.1: Remote Procedure Call</a>
 */
public final class Response extends Header {
    private byte[] stub;

    public Response() {
        setPDUType(PDUType.RESPONSE);
    }

    public byte[] getStub() {
        return stub;
    }

    public void setStub(final byte[] stub) {
        this.stub = stub;
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        setFragLength((short) (24 + stub.length));
        super.marshal(packetOut);
        packetOut.writeInt(0);
        packetOut.writeShort(0);
        packetOut.writeByte(0);
        packetOut.align(Alignment.FOUR);
        packetOut.write(getStub());
        packetOut.write(new byte[getAuthLength()]);
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        super.unmarshal(packetIn);
        setStub(new byte[getFragLength() - getAuthLength() - 24]);
        packetIn.fullySkipBytes(8);
        packetIn.readFully(getStub());
        packetIn.fullySkipBytes(getAuthLength());
    }
}
