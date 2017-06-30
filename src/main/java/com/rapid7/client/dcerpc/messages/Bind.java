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
import java.util.EnumSet;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.Interface;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.PFCFlag;
import com.rapid7.client.dcerpc.RPCRequest;
import com.rapid7.client.dcerpc.RPCResponse;

/**
 * The IDL declaration of the bind PDU is as follows:<br>
 *
 * <pre>
 * typedef struct {
 *    // start 8-octet aligned
 *    // common fields
 *    u_int8  rpc_vers = 5;        // 00:01 RPC version
 *    u_int8  rpc_vers_minor;      // 01:01 minor version
 *    u_int8  PTYPE = bind;        // 02:01 bind PDU
 *    u_int8  pfc_flags;           // 03:01 flags
 *    byte    packed_drep[4];      // 04:04 NDR data rep format label
 *    u_int16 frag_length;         // 08:02 total length of fragment
 *    u_int16 auth_length;         // 10:02 length of auth_value
 *    u_int32  call_id;            // 12:04 call identifier
 *    // end common fields
 *    u_int16 max_xmit_frag;       // 16:02 max transmit frag size, bytes
 *    u_int16 max_recv_frag;       // 18:02 max receive  frag size, bytes
 *    u_int32 assoc_group_id;      // 20:04 incarnation of client-server assoc group
 *    // presentation context list
 *    p_cont_list_t  p_context_elem; // variable size
 *    // optional authentication verifier
 *    // following fields present iff auth_length != 0
 *    auth_verifier_co_t   auth_verifier;
 * } rpcconn_bind_hdr_t;
 * </pre>
 *
 * Example:<br>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Bind, Fragment: Single, FragLen: 72, Call: 1
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Bind (11)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000
 *         Byte order: Little-endian (1)
 *         Character: ASCII (0)
 *         Floating-point: IEEE (0)
 *     Frag Length: 72
 *     Auth Length: 0
 *     Call ID: 1
 *     Max Xmit Frag: 4096
 *     Max Recv Frag: 4096
 *     Assoc Group: 0x00000000
 *     Num Ctx Items: 1
 *     Ctx Item[1]: Context ID:0, WINREG, 32bit NDR
 *         Context ID: 0
 *         Num Trans Items: 1
 *         Abstract Syntax: WINREG V1.0
 *             Interface: WINREG UUID: 338cd001-2244-31f1-aaaa-900038001003
 *             Interface Ver: 1
 *             Interface Ver Minor: 0
 *         Transfer Syntax[1]: 32bit NDR V2
 *             Transfer Syntax: 32bit NDR UUID:8a885d04-1ceb-11c9-9fe8-08002b104860
 *             ver: 2
 * </pre>
 *
 * The bind PDU is used to initiate the presentation negotiation for the body data, and optionally, authentication. The
 * presentation negotiation follows the model of the OSI presentation layer.<br>
 * <br>
 * The PDU contains a priority-ordered list of supported presentation syntaxes, both abstract and transfer, and context
 * identifiers (local handles). (This differs from OSI, which does not specify any order for the list.) The abstract
 * and transfer syntaxes are represented as a record of interface UUID and interface version. (These may map one-to-one
 * into OSI object identifiers by providing suitable prefixes and changing the encoding.) Each supported data
 * representation, such as NDR, will be assigned an interface UUID, and will use that UUID as part of its transfer
 * syntax value. Each stub computes its abstract syntax value given its interface UUID and interface version. The
 * transfer syntax value for NDR is defined in Protocol Identifiers.<br>
 * <br>
 * If pfc_flags does not have PFC_LAST_FRAG set and rpc_vers_minor is 1, then the PDU has fragmented auth_verifier
 * data. The server will assemble the data concatenating sequentially each auth_verifier field until a PDU is sent with
 * PFC_LAST_FRAG flag set. This completed buffer is then used as auth_verifier data.<br>
 * <br>
 * The fields max_xmit_frag and max_recv_frag are used for fragment size negotiation as described in
 * Connection-oriented PDU Data Types.<br>
 * <br>
 * The assoc_group_id field contains either an association group identifier that was created during a previous bind
 * negotiation or 0 (zero) to indicate a request for a new group.<br>
 * <br>
 * This PDU shall not exceed the MustRecvFragSize, since no size negotiation has yet occurred. If the p_context_elem is
 * too long, the leading subset should be transmitted, and additional presentation context negotiation can occur in
 * subsequent alter_context PDUs, as needed, after a successful bind_ack.
 *
 * @see http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm
 */
public class Bind extends RPCRequest<RPCResponse> {
    public Bind(final Interface abstractSyntax, final Interface transferSyntax) {
        super(PDUType.BIND, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));

        putShort((short) 4096); // ------------------- 16:02 max transmit frag size, bytes
        putShort((short) 4096); // ------------------- 18:02 max receive frag size, bytes
        putInt(0); // -------------------------------- 20:04 Incarnation of client-server assoc group
        // ------------------------------------------- 24:<variable size> Presentation context list
        putByte((byte) 1); // ------------------------ 24:01 Number of items
        putByte((byte) 0); // ------------------------ 25:01 Alignment pad, m.b.z.
        putShort((short) 0); // ---------------------- 26:02 Alignment pad, m.b.z.
        // ------------------------------------------- 28:20 Presentation syntax
        putShort((short) 0); // ---------------------- 28:02 Context ID
        putByte((byte) 1); // ------------------------ 30:01 Number of item(s)
        putByte((byte) 0); // ------------------------ 31:01 Alignment pad, m.b.z.
        putBytes(abstractSyntax.getUUID()); // ------- 28:16 UUID
        putShort(abstractSyntax.getMajorVersion()); // 44:02 Major version
        putShort(abstractSyntax.getMinorVersion()); // 46:02 Minor version
        putBytes(transferSyntax.getUUID()); // ------- 48:16 UUID
        putShort(transferSyntax.getMajorVersion()); // 64:02 Major version
        putShort(transferSyntax.getMinorVersion()); // 66:02 Minor version
    }

    @Override
    public RPCResponse parsePDUBindACK(final ByteBuffer responseBuffer)
        throws TransportException {
        return new BindACK(responseBuffer);
    }

    @Override
    public RPCResponse parsePDUBindNAK(final ByteBuffer responseBuffer)
        throws TransportException {
        return new BindNAK(responseBuffer);
    }
}
