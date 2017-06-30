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

import static com.hierynomus.protocol.commons.EnumWithValue.EnumUtils.toEnumSet;
import static com.hierynomus.protocol.commons.EnumWithValue.EnumUtils.toLong;
import static com.hierynomus.protocol.commons.EnumWithValue.EnumUtils.valueOf;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import com.hierynomus.smbj.transport.TransportException;

/**
 * The common header fields, which appear in all PDU types, are as follows. The comment fields show the exact octet
 * alignment and octet length of each element.<br>
 *
 * <pre>
 * // start 8-octet aligned
 * u_int8  rpc_vers = 5;        // 00:01 RPC version
 * u_int8  rpc_vers_minor;      // 01:01 minor version
 * u_int8  PTYPE;               // 02:01 packet type
 * u_int8  pfc_flags;           // 03:01 flags (see PFC_... )
 * byte    packed_drep[4];      // 04:04 NDR data representation format label
 * u_int16 frag_length;         // 08:02 total length of fragment
 * u_int16 auth_length;         // 10:02 length of auth_value
 * u_int32 call_id;             // 12:04 call identifier
 * </pre>
 *
 * @see http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm
 */
public class Header extends Packet {
    protected Header(final PDUType pduType, final EnumSet<PFCFlag> pfcFlags) {
        super();

        if (pduType == null) {
            throw new IllegalArgumentException("pduType invalid: " + pduType);
        }

        if (pfcFlags == null) {
            throw new IllegalArgumentException("pfcFlags invalid: " + pfcFlags);
        }

        // The common header fields, which appear in all PDU types, are as follows.
        putByte((byte) 5); // --------------------- 00:01 Major version
        putByte((byte) 0); // --------------------- 01:01 Minor version
        putByte((byte) pduType.getValue()); // ---- 02:01 PDU type
        putByte((byte) toLong(pfcFlags)); // ------ 03:01 PFC flags
        // ---------------------------------------- 04:04 NDR data representation format label
        putByte((byte) 0x10); // ------------------ 04:01 Integer representation and Character representation
        putByte((byte) 0); // --------------------- 05:01 Floating-Point representation
        putShort((short) 0); // ------------------- 06:02 Reserved for Future Use
        putShort((short) 0); // ------------------- 08:02 Total length of fragment
        putShort((short) 0); // ------------------- 10:02 Length of auth_value
        putInt(0); // ----------------------------- 12:04 Call identifier
    }

    protected Header(final ByteBuffer packet) throws TransportException {
        super(packet);

        final byte majorVersion = getByte();
        final byte minorVersion = getByte();
        if (5 != majorVersion || 0 != minorVersion) {
            throw new TransportException(String.format("Version mismatch: %d.%d != 5.0", majorVersion, minorVersion));
        }

        skipBytes(2);

        final byte icRepr = getByte();
        if (icRepr != 0x10) {
            throw new TransportException(String.format("Integer and Character representation mismatch: %d", icRepr));
        }

        final byte fRepr = getByte();
        if (fRepr != 0) {
            throw new TransportException(String.format("Floating-Point representation mismatch: %d", icRepr));
        }

        skipBytes(2);

        final short fragmentLength = getShort();
        if (fragmentLength > packet.remaining()) {
            throw new TransportException(
                String.format("Packet incomplete: %d > %d", fragmentLength, packet.remaining()));
        }

        final short authLength = getShort();
        if (fragmentLength + authLength > packet.remaining()) {
            throw new TransportException(
                String.format("Packet incomplete: %d + %d > %d", fragmentLength, authLength, packet.remaining()));
        }

        skipBytes(4);
    }

    protected byte getMajorVersion() {
        return getByte(0);
    }

    protected byte getMinorVersion() {
        return getByte(1);
    }

    protected PDUType getPDUType() {
        return valueOf(getByte(2), PDUType.class, null);
    }

    protected EnumSet<PFCFlag> getPFCFlags() {
        return toEnumSet(getByte(3), PFCFlag.class);
    }

    protected int getNDR() {
        return getInt(4);
    }

    protected short getFragmentLength() {
        return getShort(8);
    }

    protected short getAuthenticationVerifierLength() {
        return getShort(10);
    }

    protected int getCallID() {
        return getInt(12);
    }

    public byte[] marshal(final int callID) {
        putShort(8, (short) byteCount()); // 08:02 Total length of fragment
        putInt(12, callID); // 12:04 Call identifier

        return super.serialize();
    }
}
