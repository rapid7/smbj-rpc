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

import java.nio.ByteBuffer;
import com.hierynomus.protocol.transport.TransportException;
import com.rapid7.client.dcerpc.RPCResponse;

/**
 * The IDL declaration of the bind_nak PDU is as follows:<br>
 * <br>
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
 * @see http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm
 */
public class BindNAK extends RPCResponse {
    public BindNAK(final ByteBuffer responseBuffer)
        throws TransportException {
        super(responseBuffer);
    }
}
