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

    /**
     * Write a non-null object which implements {@link Marshallable}.
     * This object *must* be considered a top level object; if it is not, consider calling
     * {@link Marshallable#marshalPreamble(PacketOutput)}, {@link Marshallable#marshalEntity(PacketOutput)},
     * and {@link Marshallable#marshalDeferrals(PacketOutput)} separately at the appropriate locations.
     * @param marshallable A non-null {@link Marshallable} object.
     * @param <T> The class of the provided marshallable object.
     * @return The same input parameter. Useful for chaining.
     * @throws IOException On write failure.
     */
    public <T extends Marshallable> T writeMarshallable(final T marshallable) throws IOException {
        marshallable.marshalPreamble(this);
        marshallable.marshalEntity(this);
        marshallable.marshalDeferrals(this);
        return marshallable;
    }

    /**
     * Write a referent ID unique to this instance of {@link PacketOutput}.
     * @throws IOException On write failure.
     */
    public void writeReferentID() throws IOException {
        final int referentID = this.referentID;
        this.referentID += 4;
        writeInt(referentID);
    }

    /**
     * If the object is not null, write a referent ID unique to this instance of {@link PacketOutput}.
     * If the object is null, a null reference (0) will be written.
     * @param obj The object, which may be null.
     * @return True iff the object was not null.
     * @throws IOException On write failure.
     */
    public boolean writeReferentID(final Object obj) throws IOException {
        if (obj == null) {
            writeNull();
            return false;
        }
        writeReferentID();
        return true;
    }

    /**
     * Write a null referent ID
     * @throws IOException On write failure.
     */
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
     * Resulting Alignment: N+12
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
     * Required Alignment: 4
     * Resulting Alignment: N+20
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
}
