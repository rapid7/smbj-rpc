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
package com.rapid7.client.dcerpc.io;

import java.io.IOException;
import java.io.InputStream;

public class PacketInput extends PrimitiveInput {
    public PacketInput(final InputStream inputStream) {
        super(inputStream);
    }

    public Integer readIntRef()
            throws IOException {
        return 0 != readReferentID() ? readInt() : null;
    }

    public Long readLongRef()
            throws IOException {
        return 0 != readReferentID() ? readLong() : null;
    }

    public int readReferentID()
            throws IOException {
        return readInt();
    }

    public byte[] readByteArray()
            throws IOException {
        readInt();
        final int initialOffset = readInt();
        final int actualCount = readInt();
        final byte[] result = new byte[initialOffset + actualCount];

        for (int index = initialOffset; index < result.length; index++) {
            result[index] = readByte();
        }

        return result;
    }

    public byte[] readByteArrayRef()
            throws IOException {
        final byte[] result;
        if (0 != readReferentID()) {
            result = readByteArray();
            align();
        } else {
            result = null;
        }

        return result;
    }

    public byte[] readRawBytes(int length)
            throws IOException {
        byte[] bytes = new byte[length];
        readRawBytes(bytes);

        return bytes;
    }

    public void readRawBytes(byte[] buf)
            throws IOException {
        readFully(buf, 0, buf.length);
    }

    public String readString(final boolean nullTerminated)
            throws IOException {
        final StringBuffer result;

        readInt();
        final int initialOffset = readInt();
        final int currentChars = readInt();

        result = new StringBuffer(currentChars);
        result.setLength(initialOffset);

        int currentOffset = 0;
        while (currentOffset++ < currentChars) {
            final char currentChar = (char) readShort();
            if (nullTerminated && currentChar == 0) {
                break;
            }
            result.append(currentChar);
        }

        while (currentOffset++ < currentChars) {
            readShort();
        }

        align();

        return result.toString();
    }

    public String readStringRef(final boolean nullTerminated)
            throws IOException {
        final String result;

        if (0 != readReferentID()) {
            result = readString(nullTerminated);
            align();
        } else {
            result = null;
        }

        return result != null ? result.toString() : null;
    }

    public String readStringBuf(final boolean nullTerminated)
            throws IOException {
        readShort(); // Current byte length
        readShort(); // Maximum byte length

        return readStringRef(nullTerminated);
    }

    public String readStringBufRef(final boolean nullTerminated)
            throws IOException {
        final String result;
        if (0 != readReferentID()) {
            result = readStringBuf(nullTerminated);
            align();
        } else {
            result = null;
        }

        return result;
    }

    public <T extends Unmarshallable<T>> T unmarshallObject(T t) throws IOException {
        return t.unmarshall(this);
    }
}
