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
package com.rapid7.client.dcerpc.messages;

import static com.rapid7.client.dcerpc.PDUType.BIND_ACK;
import static com.rapid7.client.dcerpc.PDUType.BIND_NAK;
import java.io.IOException;
import com.rapid7.client.dcerpc.Header;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;

/**
 * The IDL declaration of the bind_ack PDU is as follows:<br>
 *
 * <pre>
 * typedef struct {
 *    // start 8-octet aligned
 *    // common fields
 *    u_int8  rpc_vers = 5;       // 00:01 RPC version
 *    u_int8  rpc_vers_minor ;    // 01:01 minor version
 *    u_int8  PTYPE = bind_ack;   // 02:01 bind ack PDU
 *    u_int8  pfc_flags;          // 03:01 flags
 *    byte    packed_drep[4];     // 04:04 NDR data rep format label
 *    u_int16 frag_length;        // 08:02 total length of fragment
 *    u_int16 auth_length;        // 10:02 length of auth_value
 *    u_int32  call_id;           // 12:04 call identifier
 *    // end common fields
 *    u_int16 max_xmit_frag;      // 16:02 max transmit frag size
 *    u_int16 max_recv_frag;      // 18:02 max receive  frag size
 *    u_int32 assoc_group_id;     // 20:04 returned assoc_group_id
 *    port_any_t sec_addr;        // 24:yy optional secondary address
 *                                // ----- for process incarnation; local port
 *                                // ----- part of address only
 *    // restore 4-octet alignment
 *    u_int8 [size_is(align(4))] pad2;
 *    // presentation context result list, including hints
 *    p_result_list_t     p_result_list;    // variable size
 *    // optional authentication verifier
 *    // following fields present iff auth_length != 0
 *    auth_verifier_co_t   auth_verifier; // xx:yy
 * } rpcconn_bind_ack_hdr_t;
 * </pre>
 *
 * The bind_ack PDU is returned by the server when it accepts a bind request initiated by the client's bind PDU. It
 * contains the results of presentation context and fragment size negotiations. It may also contain a new association
 * group identifier if one was requested by the client.<br>
 * <br>
 * The max_xmit_frag and max_recv_frag fields contain the maximum transmit and receive fragment sizes as determined by
 * the server in response to the client's desired sizes.<br>
 * <br>
 * The p_result_list contains the results of the presentation context negotiation initiated by the client. It is
 * possible for a bind_ack not to contain any mutually supported syntaxes.<br>
 * <br>
 * If the client requested a new association group, assoc_group_id contains the identifier of the new association group
 * created by the server. Otherwise, it contains the identifier of the previously created association group requested by
 * the client. <br>
 * <br>
 * <br>
 * The IDL declaration of the bind_nak PDU is as follows:<br>
 *
 * <pre>
 * typedef struct {
 *    // start 8-octet aligned
 *    // common fields
 *    u_int8  rpc_vers = 5;       // 00:01 RPC version
 *    u_int8  rpc_vers_minor ;    // 01:01 minor version
 *    u_int8  PTYPE = bind_nak;   // 02:01 bind nak PDU
 *    u_int8  pfc_flags;          // 03:01 flags
 *    byte    packed_drep[4];     // 04:04 NDR data rep format label
 *    u_int16 frag_length;        // 08:02 total length of fragment
 *    u_int16 auth_length;        // 10:02 length of auth_value
 *    u_int32  call_id;           // 12:04 call identifier
 *    // end common fields
 *    p_reject_reason_t provider_reject_reason; // 16:02 presentation context reject
 *    p_rt_versions_supported_t versions; // 18:yy array of protocol versions supported
 * } rpcconn_bind_nak_hdr_t;
 * </pre>
 *
 * The bind_nak PDU is returned by the server when it rejects an association request initiated by the client's bind PDU.
 * The provider_reject_reason field holds the rejection reason code. When the reject reason is
 * protocol_version_not_supported, the versions field contains a list of run-time protocol versions supported by the
 * server.<br>
 * <br>
 * The bind_nak PDU never contains an authentication verifier.
 *
 * @see <a href=http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm>CDE 1.1: Remote Procedure Call</a>
 */
public final class BindResponse extends Header {
    private short maxXmitFrag;
    private short maxRecvFrag;

    public boolean isACK() {
        return BIND_ACK.equals(getPDUType());
    }

    public boolean isNAK() {
        return BIND_NAK.equals(getPDUType());
    }

    public short getMaxXmitFrag() {
        return maxXmitFrag;
    }

    public short getMaxRecvFrag() {
        return maxRecvFrag;
    }

    public void setMaxXmitFrag(final short maxXmitFrag) {
        this.maxXmitFrag = maxXmitFrag;
    }

    public void setMaxRecvFrag(final short maxRecvFrag) {
        this.maxRecvFrag = maxRecvFrag;
    }

    @Override
    public void marshal(final PacketOutput packetOut)
        throws IOException {
        // This method is used for unit tests and is not a complete implementation.
        switch (getPDUType()) {
        case BIND_ACK:
            setFragLength((short) 20);
            super.marshal(packetOut);
            packetOut.writeShort(maxXmitFrag);
            packetOut.writeShort(maxRecvFrag);
            break;
        case BIND_NAK:
            setFragLength((short) 16);
            super.marshal(packetOut);
            break;
        default:
            throw new IOException("Invalid PDU type: " + getPDUType());
        }
    }

    @Override
    public void unmarshal(final PacketInput packetIn)
        throws IOException {
        super.unmarshal(packetIn);

        switch (getPDUType()) {
        case BIND_ACK:
            maxXmitFrag = packetIn.readShort();
            maxRecvFrag = packetIn.readShort();
            packetIn.fullySkipBytes(getFragLength() - 20);
            break;
        case BIND_NAK:
            packetIn.fullySkipBytes(getFragLength() - 16);
            break;
        default:
            throw new IOException("Invalid PDU type: " + getPDUType());
        }
    }
}
