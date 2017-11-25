/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.dto;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import com.rapid7.client.dcerpc.dto.ace.ACE;

/**
 * This class represents an access control list <a href="https://msdn.microsoft.com/en-us/library/cc230297.aspx">ACL</a>
 */
public class ACL {
    // An unsigned 8-bit value that specifies the revision of the ACL.
    // The only two legitimate forms of ACLs supported for on-the-wire management
    // or manipulation are type 2 and type 4. No other form is valid for manipulation on the wire.
    private final byte revision;
    private final ACE[] aces;

    public ACL(final byte revision, final ACE[] aces) {
        this.revision = revision;
        this.aces = aces;
    }

    public byte getRevision() {
        return revision;
    }

    public ACE[] getACEs() {
        return aces;
    }

    @Override
    public int hashCode() {
        int ret = getRevision();
        return (ret * 31) + Arrays.hashCode(getACEs());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ACL)) {
            return false;
        }
        final ACL other = (ACL) obj;
        return Objects.equals(getRevision(), other.getRevision())
                && Arrays.equals(getACEs(), other.getACEs());
    }

    @Override
    public String toString() {
        return String.format("ACL{revision: %d, aces: %s}",
                getRevision(), Arrays.toString(getACEs()));
    }

    public static ACL read(final ByteBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("Expecting non-null buffer");
        }
        final byte revision = buffer.get();
        if (revision != 2 && revision != 4) {
            throw new IllegalArgumentException(String.format("Expected AclRevision 2 or 4, got: %d", revision));
        }
        // Sbz1 (1 byte): An unsigned 8-bit value. This field is reserved and MUST be set to zero.
        final byte sbz1 = buffer.get();
        if (sbz1 != 0) {
            throw new IllegalArgumentException(String.format("Expected Sbz1 == 0, got: %d", sbz1));
        }
        // AclSize (2 bytes): An unsigned 16-bit integer that specifies the size, in bytes, of the complete ACL, including all ACEs.
        buffer.getShort();
        // AceCount (2 bytes): An unsigned 16-bit integer that specifies the count of the number of ACE records in the ACL.
        final int aceCount = (int) buffer.getShort();
        // Sbz2 (2 bytes): An unsigned 16-bit integer. This field is reserved and MUST be set to zero.
        final short sbz2 = buffer.getShort();
        if (sbz2 != 0) {
            throw new IllegalArgumentException(String.format("Expected Sbz2 == 0, got: %d", sbz2));
        }
        final ACE[] aces = new ACE[aceCount];
        for (int i = 0; i < aces.length; i++) {
            aces[i] = ACE.read(buffer);
        }
        return new ACL(revision, aces);
    }
}
