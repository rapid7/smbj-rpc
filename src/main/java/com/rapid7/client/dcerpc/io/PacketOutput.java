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
package com.rapid7.client.dcerpc.io;

import java.io.IOException;
import java.io.OutputStream;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;

public class PacketOutput extends PrimitiveOutput {
    private int referentID = 0x00020000;

    public PacketOutput(final OutputStream outputStream) {
        super(outputStream);
    }

    public <T extends Marshallable> T writeMarshallable(T marshallable) throws IOException {
        marshallable.marshalPreamble(this);
        marshallable.marshalEntity(this);
        marshallable.marshalDeferrals(this);
        // TODO Align should be called, but can require resetting the packet counts
        return marshallable;
    }

    public void writeIntRef(final Integer value) throws IOException {
        if (value != null) {
            writeReferentID();
            writeInt(value.intValue());
            align();
        } else {
            writeNull();
        }
    }

    public void writeLongRef(final Long value) throws IOException {
        if (value != null) {
            writeReferentID();
            writeLong(value.longValue());
            align();
        } else {
            writeNull();
        }
    }

    public void writeReferentID() throws IOException {
        final int referentID = this.referentID;
        this.referentID += 4;
        writeInt(referentID);
    }

    public void writeNull() throws IOException {
        writeInt(0);
    }

    public void writeEmptyArray(final int maximumCount) throws IOException {
        writeInt(maximumCount);
        writeInt(0);
        writeInt(0);
    }

    public void writeEmptyArrayRef(final int maximumCount) throws IOException {
        writeReferentID();
        writeEmptyArray(maximumCount);
        align();
    }

    public void writeStringBufferRef(final String string, final boolean nullTerminate) throws IOException {
        if (string != null) {
            writeReferentID();
            writeStringBuffer(string, nullTerminate);
        } else {
            writeNull();
        }
    }

    public void writeRPCUnicodeString(final String string, final boolean nullTerminate) throws IOException {
        writeStringBuffer(string, nullTerminate);
    }

    public void writeStringBuffer(final String string, final boolean nullTerminate) throws IOException {
        final int maximumBytes;
        final int currentBytes;

        if (string == null) {
            maximumBytes = 0;
            currentBytes = 0;
        } else {
            maximumBytes = 2 * string.length() + (nullTerminate ? 2 : 0);
            currentBytes = 2 * string.length() + (nullTerminate ? 2 : 0);
        }

        writeShort((short) currentBytes);
        writeShort((short) maximumBytes);

        if (string != null) {
            writeReferentID();
            writeString(string, nullTerminate);
        } else {
            writeNull();
        }
    }

    public void writeStringRef(final String string, final boolean nullTerminate) throws IOException {
        writeReferentID();
        writeString(string, nullTerminate);
    }

    public void writeString(final String string, final boolean nullTerminate) throws IOException {
        final int maximumChars;
        final int currentChars;

        maximumChars = string.length() + (nullTerminate ? 1 : 0);
        currentChars = string.length() + (nullTerminate ? 1 : 0);

        writeInt(maximumChars); //max_is (max size)
        writeInt(0); //min_is (offset)
        writeInt(currentChars); //size_is (actual size)

        writeChars(string);
        if (nullTerminate) {
            writeShort((short) 0);
        }
        align();
    }

    public void writeStringBuffer(final int maximumChars) throws IOException {
        final int maximumBytes = maximumChars << 1;
        final int currentBytes = 0;
        final int currentChars = 0;

        writeShort((short) currentBytes);
        writeShort((short) maximumBytes);
        writeReferentID();
        writeInt(maximumChars);
        writeInt(0);
        writeInt(currentChars);
    }

    public void writeStringBufferRef(final int maximumChars) throws IOException {
        writeReferentID();
        writeStringBuffer(maximumChars);
        align();
    }
}
