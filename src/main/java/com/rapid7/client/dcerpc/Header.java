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
package com.rapid7.client.dcerpc;

import java.io.IOException;
import java.util.Set;
import com.rapid7.client.dcerpc.io.*;

import static com.hierynomus.protocol.commons.EnumWithValue.EnumUtils.*;

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
 * @see <a href=http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm>CDE 1.1: Remote Procedure Call</a>
 */
public class Header extends HexifyImpl implements Packet, Hexify {
    private byte majorVersion = 5;
    private byte minorVersion = 0;
    private PDUType pduType;
    private Set<PFCFlag> pfcFlags;
    private byte[] ndr = {0x10, 0x00, 0x00, 0x00};
    private short fragLength = 16;
    private short authLength = 0;
    private int callID = 0;

    public byte getMajorVersion() {
        return majorVersion;
    }

    public byte getMinorVersion() {
        return minorVersion;
    }

    public PDUType getPDUType() {
        return pduType;
    }

    public Set<PFCFlag> getPFCFlags() {
        return pfcFlags;
    }

    public byte[] getNDR() {
        return ndr;
    }

    public int getFragLength() {
        return fragLength;
    }

    public int getAuthLength() {
        return authLength;
    }

    public int getCallID() {
        return callID;
    }

    public void setMajorVersion(final byte majorVersion) {
        this.majorVersion = majorVersion;
    }

    public void setMinorVersion(final byte minorVersion) {
        this.minorVersion = minorVersion;
    }

    public void setPDUType(final PDUType pduType) {
        this.pduType = pduType;
    }

    public void setPFCFlags(final Set<PFCFlag> pfcFlags) {
        this.pfcFlags = pfcFlags;
    }

    public void setNDR(final byte[] ndr) {
        this.ndr = ndr;
    }

    public void setFragLength(final short fragLength) {
        this.fragLength = fragLength;
    }

    public void setAuthLength(final short authLength) {
        this.authLength = authLength;
    }

    public void setCallID(final int callID) {
        this.callID = callID;
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        if (null == getPDUType()) {
            throw new IllegalStateException("Invalid PDU type: " + getPDUType());
        }
        if (null == getPFCFlags()) {
            throw new IllegalStateException("Invalid PFC flag(s): " + getPFCFlags());
        }
        packetOut.writeByte(getMajorVersion());
        packetOut.writeByte(getMinorVersion());
        packetOut.writeByte((byte) getPDUType().getValue());
        packetOut.writeByte((byte) toLong(getPFCFlags()));
        packetOut.write(getNDR());
        packetOut.writeShort(getFragLength());
        packetOut.writeShort(0);
        packetOut.writeInt(getCallID());
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        setMajorVersion(packetIn.readByte());
        setMinorVersion(packetIn.readByte());

        if (5 != getMajorVersion() || 0 != getMinorVersion()) {
            throw new IOException(String.format("Version mismatch: %d.%d != 5.0", getMajorVersion(), getMinorVersion()));
        }

        final byte pduTypePrimitive = packetIn.readByte();
        final PDUType pduType = valueOf(pduTypePrimitive, PDUType.class, null);
        if (pduType == null) {
            throw new IOException(String.format("PDU type invalid: %d", pduType));
        }

        setPDUType(pduType);
        setPFCFlags(toEnumSet(packetIn.readByte(), PFCFlag.class));

        final byte[] ndr = new byte[4];

        packetIn.readFully(ndr);

        if (ndr[0] != 0x10) {
            throw new IOException(String.format("Integer and Character representation mismatch: %d", ndr[0]));
        }

        if (ndr[1] != 0) {
            throw new IOException(String.format("Floating-Point representation mismatch: %d", ndr[1]));
        }

        setNDR(ndr);
        setFragLength(packetIn.readShort());
        setAuthLength(packetIn.readShort());
        setCallID(packetIn.readInt());
    }
}
