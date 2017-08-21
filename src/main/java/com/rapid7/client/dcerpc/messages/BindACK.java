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

import com.rapid7.client.dcerpc.RPCResponse;
import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;

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
 *                                  * for process incarnation; local port
 *                                  * part of address only
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
 * the client.
 *
 * @see http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm
 */
public class BindACK extends RPCResponse {
    public BindACK(final ByteBuffer responseBuffer)
        throws TransportException {
        super(responseBuffer);
    }
}
