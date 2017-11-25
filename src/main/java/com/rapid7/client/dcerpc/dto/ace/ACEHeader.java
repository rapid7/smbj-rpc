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

package com.rapid7.client.dcerpc.dto.ace;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * This class represents an ACE Header <a href="https://msdn.microsoft.com/en-us/library/cc230296.aspx">ACE_HEADER</a>
 */
public class ACEHeader {
    private final byte flags;
    private final ACEType type;
    private final int size;

    public ACEHeader(final ACEType type, final byte flags, final int size) {
        this.flags = flags;
        this.type = type;
        this.size = size;
    }

    ACEHeader(final ByteBuffer buffer) {
        // AceType (1 byte): An unsigned 8-bit integer that specifies the ACE types. This field MUST be one of the following values.
        this.type = ACEType.fromValue(buffer.get());
        // AceFlags (1 byte): An unsigned 8-bit integer that specifies a set of ACE type-specific control flags
        this.flags = buffer.get();
        // AceSize (2 bytes): An unsigned 16-bit integer that specifies the size, in bytes, of the ACE.
        this.size = (int) buffer.getShort();
    }

    public byte getFlags() {
        return flags;
    }

    public ACEType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFlags(), getType(), getSize());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ACEHeader)) {
            return false;
        }
        final ACEHeader other = (ACEHeader) obj;
        return getFlags() == other.getFlags()
                && getType() == other.getType()
                && getSize() == other.getSize();
    }

    @Override
    public String toString() {
        return String.format("ACEHeader{flags: 0x%02X, type: %s, size: %d}",
                getFlags(), getType(), getSize());
    }
}
