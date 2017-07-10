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

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Packet {
    private final ByteBuffer buffer;
    private int referentID = 0x00020000;

    protected Packet() {
        buffer = ByteBuffer.allocate(8192);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    protected Packet(final ByteBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("Buffer invalid: " + buffer);
        }

        this.buffer = buffer.slice();
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    protected byte[] serialize() {
        buffer.flip();
        final byte[] packetBytes = new byte[buffer.remaining()];
        buffer.get(packetBytes);
        return packetBytes;
    }

    protected void align() {
        final int aligned = 3 + byteCount() & ~3;
        while (aligned > byteCount()) {
            putByte((byte) 0);
        }
    }

    protected void putByte(final byte value) {
        buffer.put(value);
    }

    protected void putBytes(final byte[] src) {
        buffer.put(src);
    }

    protected void putShort(final short value) {
        buffer.putShort(value);
    }

    protected void putInt(final int value) {
        buffer.putInt(value);
    }

    protected void putIntRef(final Integer value) {
        if (value != null) {
            putReferentID();
            putInt(value.intValue());
            align();
        } else {
            putNull();
        }
    }

    protected void putLong(final long value) {
        buffer.putLong(value);
    }

    protected void putLongRef(final Long value) {
        if (value != null) {
            putReferentID();
            putLong(value.longValue());
            align();
        } else {
            putNull();
        }
    }

    protected void putReferentID() {
        final int referentID = this.referentID;
        this.referentID += 4;
        putInt(referentID);
    }

    protected void putNull() {
        putInt(0);
    }

    protected void putEmptyArray(final int maximumCount) {
        putInt(maximumCount);
        putInt(0);
        putInt(0);
    }

    protected void putEmptyArrayRef(final int maximumCount) {
        putReferentID();
        putEmptyArray(maximumCount);
        align();
    }

    protected void putString(final String string, final boolean nullTerminate) {
        final String text = string.toString();
        final byte[] textBytes;

        try {
            textBytes = text.getBytes("UTF-16LE");
        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException();
        }

        final int maximumBytes = 2 * string.length() + (nullTerminate ? 2 : 0);
        final int currentBytes = 2 * string.length() + (nullTerminate ? 2 : 0);
        final int maximumChars = string.length() + (nullTerminate ? 1 : 0);
        final int currentChars = string.length() + (nullTerminate ? 1 : 0);

        putShort((short) currentBytes);
        putShort((short) maximumBytes);
        putReferentID();
        putInt(maximumChars);
        putInt(0);
        putInt(currentChars);

        for (final int textByte : textBytes) {
            putByte((byte) textByte);
        }

        if (nullTerminate) {
            putShort((short) 0);
        }

        align();
    }

    protected void putStringRef(final String string, final boolean nullTerminate) {
        if (string != null) {
            putReferentID();
            putString(string, nullTerminate);
        } else {
            putNull();
        }
    }

    protected void putStringBuffer(final int maximumChars) {
        final int maximumBytes = maximumChars << 1;
        final int currentBytes = 0;
        final int currentChars = 0;

        putShort((short) currentBytes);
        putShort((short) maximumBytes);
        putReferentID();
        putInt(maximumChars);
        putInt(0);
        putInt(currentChars);
    }

    protected void putStringBufferRef(final int maximumChars) {
        putReferentID();
        putStringBuffer(maximumChars);
        align();
    }

    protected void putByte(final int index, final byte value) {
        buffer.put(index, value);
    }

    protected void putShort(final int index, final short value) {
        buffer.putShort(index, value);
    }

    protected void putInt(final int index, final int value) {
        buffer.putInt(index, value);
    }

    protected void putLong(final int index, final long value) {
        buffer.putLong(index, value);
    }

    protected byte getByte() {
        return buffer.get();
    }

    protected byte[] getBytes(final int length) {
        final byte[] bytes = new byte[length];
        buffer.get(bytes);
        return bytes;
    }

    protected void getBytes(final byte[] dst) {
        buffer.get(dst);
    }

    protected short getShort() {
        return buffer.getShort();
    }

    protected int getInt() {
        return buffer.getInt();
    }

    protected Integer getIntRef() {
        return 0 != getReferentID() ? getInt() : null;
    }

    protected long getLong() {
        return buffer.getLong();
    }

    protected Long getLongRef() {
        return 0 != getReferentID() ? getLong() : null;
    }

    protected int getReferentID() {
        return getInt();
    }

    protected byte[] getByteArray() {
        getInt();
        final int initialOffset = getInt();
        final int actualCount = getInt();
        final byte[] result = new byte[initialOffset + actualCount];

        for (int index = initialOffset; index < result.length; index++) {
            result[index] = getByte();
        }

        return result;
    }

    protected byte[] getByteArrayRef() {
        final byte[] result;
        if (0 != getReferentID()) {
            result = getByteArray();
            align();
        } else {
            result = null;
        }

        return result;
    }

    protected String getString(final boolean nullTerminated) {
        final StringBuffer result;

        getShort(); // Current byte length
        getShort(); // Maximum byte length

        if (0 != getReferentID()) {
            getInt();
            final int initialOffset = getInt();
            final int currentChars = getInt();

            result = new StringBuffer(currentChars);
            result.setLength(initialOffset);

            int currentOffset = initialOffset;
            while (currentOffset++ < currentChars) {
                final char currentChar = (char) getShort();
                if (nullTerminated && currentChar == 0) {
                    break;
                }
                result.append(currentChar);
            }

            while (currentOffset++ < currentChars) {
                getShort();
            }

            align();
        } else {
            result = null;
        }

        return result != null ? result.toString() : null;
    }

    protected String getStringRef(final boolean nullTerminated) {
        final String result;
        if (0 != getReferentID()) {
            result = getString(nullTerminated);
            align();
        } else {
            result = null;
        }

        return result;
    }

    protected byte getByte(final int index) {
        return buffer.get(index);
    }

    protected short getShort(final int index) {
        return buffer.getShort(index);
    }

    protected int getInt(final int index) {
        return buffer.getInt(index);
    }

    protected long getLong(final int index) {
        return buffer.getLong(index);
    }

    protected void skipBytes(final int position) {
        buffer.position(position + buffer.position());
    }

    protected int byteCount() {
        return buffer.position();
    }
}
