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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class PacketInput extends PrimitiveInput {
    public PacketInput(final InputStream inputStream) {
        super(inputStream);
    }

    /**
     * Read a non-null object which implements {@link Unmarshallable}.
     * This object *must* be considered a top level object; if it is not, consider calling
     * {@link Unmarshallable#unmarshalPreamble(PacketInput)}, {@link Unmarshallable#unmarshalEntity(PacketInput)},
     * and {@link Unmarshallable#unmarshalDeferrals(PacketInput)} separately at the appropriate locations.
     * @param unmarshallable A non-null {@link Unmarshallable} object.
     * @param <T> The class of the provided unmarshallable object.
     * @return The same input parameter. Useful for chaining.
     * @throws IOException On read failure.
     */
    public <T extends Unmarshallable> T readUnmarshallable(T unmarshallable) throws IOException {
        unmarshallable.unmarshalPreamble(this);
        unmarshallable.unmarshalEntity(this);
        unmarshallable.unmarshalDeferrals(this);
        return unmarshallable;
    }

    /**
     * Read a referent ID unique to this instance of {@link PacketInput}.
     * @return A referent ID unique to this instance of {@link PacketInput}.
     * @throws IOException On read failure.
     */
    public int readReferentID() throws IOException {
        // Currently only supports NDR20
        return readInt();
    }

    /**
     * Read and return length number of bytes.
     * @param length The number of bytes to read.
     * @return A byte[] populated with length bytes.
     * @throws EOFException If not enough bytes are available.
     * @throws IOException On read failure.
     */
    public byte[] readRawBytes(final int length) throws IOException {
        byte[] bytes = new byte[length];
        readRawBytes(bytes);

        return bytes;
    }

    /**
     * Read all bytes into the given buffer.
     * @param buf The buffer to read into.
     * @throws EOFException If not enough bytes are available to fill the buffer.
     * @throws IOException On read failure.
     */
    public void readRawBytes(final byte[] buf) throws IOException {
        readFully(buf, 0, buf.length);
    }

    /**
     * Read an unsigned integer which is to be used as an array size or offset.
     *
     * Due to the limitations of Java, we must used a signed integer, and array lengths can not
     * exceed the maximum value of an unsigned integer. Therefore, if the read unsigned integer
     * is greater than {@link Integer#MAX_VALUE}, this will throw an {@link UnmarshalException}.
     *
     * @param name The name of the entity being read. Used in the potential {@link UnmarshalException}.
     * @return The unsigned integer which is guaranteed to be valid as an array size or offset.
     * @throws UnmarshalException When the value exceeds {@link Integer#MAX_VALUE}.
     * @throws IOException On read failure.
     */
    public int readIndex(final String name) throws IOException {
        final long ret = readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("%s %d > %d", name, ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }
}
