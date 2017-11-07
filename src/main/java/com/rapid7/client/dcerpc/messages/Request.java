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
import java.util.Set;
import com.rapid7.client.dcerpc.Header;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.PFCFlag;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

/**
 * The IDL declaration of the request PDU is as follows:<br>
 *
 * <pre>
 * typedef struct {
 *    // start 8-octet aligned
 *    // common fields
 *    u_int8  rpc_vers = 5;       // 00:01 RPC version
 *    u_int8  rpc_vers_minor;     // 01:01 minor version
 *    u_int8  PTYPE = request ;   // 02:01 request PDU
 *    u_int8  pfc_flags;          // 03:01 flags
 *    byte    packed_drep[4];     // 04:04 NDR data rep format label
 *    u_int16 frag_length;        // 08:02 total length of fragment
 *    u_int16 auth_length;        // 10:02 length of auth_value
 *    u_int32  call_id;           // 12:04 call identifier
 *    // end common fields
 *    // needed on request, response, fault
 *    u_int32  alloc_hint;        // 16:04 allocation hint
 *    p_context_id_t p_cont_id    // 20:02 pres context, i.e. data rep
 *    u_int16 opnum;              // 22:02 operation # within the interface
 *    // optional field for request, only present if the PFC_OBJECT_UUID field is non-zero
 *    uuid_t  object;              // 24:16 object UID
 *    // stub data, 8-octet aligned
 *    //     .
 *    //     .
 *    //     .
 *    // optional authentication verifier
 *    // following fields present iff auth_length != 0
 *    auth_verifier_co_t   auth_verifier; // xx:yy
 * } rpcconn_request_hdr_t;
 * </pre>
 *
 * The request PDU is used for an initial call request. The p_cont_id field holds a presentation context identifier that
 * identifies the data representation. The opnum field identifies the operation being invoked within the interface.<br>
 * <br>
 * The PDU may also contain an object UUID. In this case the PFC_OBJECT_UUID flag is set in pfc_flags, and the PDU
 * includes the object field. If the PFC_OBJECT_UUID flag is not set, the PDU does not include the object field.<br>
 * <br>
 * The alloc_hint field is optionally used by the client to provide a hint to the receiver of the amount of buffer space
 * to allocate contiguously for fragmented requests. This is only a potential optimisation. The server must work
 * correctly regardless of the value passed. The value 0 (zero) is reserved to indicate that the transmitter is not
 * supplying any information.<br>
 * <br>
 * The minimum size of an rpcconn_request_hdr_t is 24 octets. If a non-nil object UUID or authentication and/or
 * integrity or privacy services are used, the size will be larger.<br>
 * <br>
 * The size of the stub data is calculated as follows:
 *
 * <pre>
 * stub_data_length = frag_length - fixed_header_length - auth_length;
 * if (pfc_flags &amp; PFC_OBJECT_UUID) {
 *     stub_data_length = stub_data_length - sizeof(uuid_t);
 * }
 * </pre>
 *
 * where the current value of fixed_header_length is 24 octets.
 *
 * @see <a href=http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm>CDE 1.1: Remote Procedure Call</a>
 */
public final class Request extends Header {
    private short opNum;
    private byte[] stub;

    public Request() {
        setPDUType(PDUType.REQUEST);
    }

    /**
     * @return The operation # within the interface.
     */
    public short getOpNum() {
        return opNum;
    }

    /**
     * @return The stub data.
     */
    public byte[] getStub() {
        return stub;
    }

    public void setOpNum(final short opNum) {
        this.opNum = opNum;
    }

    public void setStub(final byte[] stub) {
        this.stub = stub;
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        if (null == getStub()) {
            throw new IllegalStateException("Invalid stub: " + getStub());
        }
        final Set<PFCFlag> pfcFlags = getPFCFlags();
        setFragLength((short) ((pfcFlags.contains(PFCFlag.OBJECT_UUID) ? 40 : 24) + stub.length));
        super.marshal(packetOut);
        final byte[] stub = getStub();
        packetOut.writeInt(stub.length); // 16:04 Allocation hint
        packetOut.writeShort(0); // 20:02 Presentation context, i.e. data representation
        packetOut.writeShort(getOpNum()); // 22:02 Operation # within the interface
        packetOut.write(stub);
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        throw new UnsupportedOperationException("Unmarshal Not Implemented.");
    }
}
