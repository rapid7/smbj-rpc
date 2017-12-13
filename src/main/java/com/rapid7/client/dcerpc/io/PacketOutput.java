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
        return marshallable;
    }

    public void writeIntRef(final Integer value) throws IOException {
        if (value != null) {
            writeReferentID();
            writeInt(value);
            align();
        } else {
            writeNull();
        }
    }

    public void writeLongRef(final Long value) throws IOException {
        if (value != null) {
            writeReferentID();
            writeLong(value);
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

    /**
     * Writes an empty conformant varying array with the given MaximumCount
     *      MaximumCount=maximumCount
     *      Offset=0
     *      ActualCount=0
     *
     * Required Alignment: 4
     * Resulting Alignment: 4
     *
     * NOTE: This is written as a top level object, and must not be used within an embedding structure.
     * NOTE: Like all actions in this class, existing stream alignment is assumed.
     *
     * @param maximumCount The MaximumCount
     * @throws IOException On write failure.
     */
    public void writeEmptyCVArray(final int maximumCount) throws IOException {
        // <NDR: unsigned long> MaximumCount
        writeInt(maximumCount);
        // <NDR: unsigned long> Offset
        // Alignment: 4 - Already aligned
        writeInt(0);
        // <NDR: unsigned long> ActualCount
        // Alignment: 4 - Already aligned
        writeInt(0);
    }

    /**
     * Writes an empty {@link com.rapid7.client.dcerpc.objects.RPCUnicodeString} with the
     * given number of UTF-16 characters (maximumChars).
     * This serves to allocate a buffer for the request call, without actually writing the buffer to the stream.
     *      Length=0
     *      MaximumLength=(maximumChars/2)
     *      MaximumCount=maximumChars
     *      Offset=0
     *      ActualCount=0
     *
     * Required Alignment: 2
     * Resulting Alignment: 4
     *
     * NOTE: This is written as a top level object, and must not be used within an embedding structure.
     * NOTE: Like all actions in this class, existing stream alignment is assumed.
     *
     * @param maximumChars The number of UTF-16 characters to allocate.
     * @throws IOException On write failure.
     */
    public void writeEmptyRPCUnicodeString(final int maximumChars) throws IOException {
        // <NDR: unsigned short> unsigned short Length;
        writeShort((short) 0); // Length
        // <NDR: unsigned short> unsigned short MaximumLength;
        // Alignment: 2 - Already aligned
        writeShort((short) maximumChars << 1); // MaximumLength
        // <NDR: pointer[conformant varying array]> [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment: 4 - Already aligned
        writeReferentID();
        // <NDR: unsigned long> MaximumCount: [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment: 4 - Already aligned
        writeInt(maximumChars);
        // <NDR: unsigned long> Offset: [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment: 4 - Already aligned
        writeInt(0);
        // <NDR: unsigned long> ActualCount: [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment: 4 - Already aligned
        writeInt(0);
        // No entries
    }

    public void writeEmptyArrayRef(final int maximumCount) throws IOException {
        writeReferentID();
        writeEmptyCVArray(maximumCount);
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
