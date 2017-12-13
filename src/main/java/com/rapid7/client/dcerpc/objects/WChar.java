/*
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
package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * Represents a UTF-16 encoded unicode string as a character array.
 * <b>Alignment: 4</b>
 *
 * <b>Marshalling Usage:</b><pre>
 *      String myValue = "some string";
 *      WChar wChar = WChar.NonNullTerminated.of(myValue);
 *      packetOut.writeMarshallable(wChar);</pre>
 * <b>Unmarshalling Usage:</b><pre>
 *      WChar wChar = new WChar.NonNullTerminated();
 *      packetIn.readUnmarshallable(wChar);
 *      String myValue = wChar.getValue();</pre>
 */
public abstract class WChar implements Unmarshallable, Marshallable {

    /**
     * An WCHAR which is expected to be null terminated during marshalling/unmarshalling.
     */
    public static class NullTerminated extends WChar {
        /**
         * @param value The value, must not be null.
         * @return A new {@link NullTerminated} {@link WChar} with the provided value.
         */
        public static NullTerminated of(String value) {
            final NullTerminated ret = new NullTerminated();
            ret.setValue(value);
            return ret;
        }

        @Override
        public boolean isNullTerminated() {
            return true;
        }
    }

    /**
     * An WCHAR which is not expected to be null terminated during marshalling/unmarshalling.
     */
    public static class NonNullTerminated extends WChar {
        /**
         * @param value The value, must not be null.
         * @return A new {@link NonNullTerminated} {@link WChar} with the provided value.
         */
        public static NonNullTerminated of(String value) {
            final NonNullTerminated ret = new NonNullTerminated();
            ret.setValue(value);
            return ret;
        }

        @Override
        public boolean isNullTerminated() {
            return false;
        }
    }

    private String value = "";
    // Stored for unmarshalling purposes only
    private int offset;
    private int actualCount;

    public abstract boolean isNullTerminated();

    /**
     * @return The non-null String representation of this {@link WChar}.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * @param value The non-null String representation for this {@link WChar}.
     */
    public void setValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Expected non-null value");
        }
        this.value = value;
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // MaximumCount for conformant array
        out.align(Alignment.FOUR);
        out.writeInt(getCodePoints());
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment: 4
        out.align(Alignment.FOUR);
        // Offset for varying array
        // Alignment 4 - Already aligned
        out.writeInt(0);
        // ActualCount for varying array
        // Alignment 4 - Already aligned
        out.writeInt(getCodePoints());
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        // Entities for conformant+varying array
        // Alignment 1 - Already aligned
        out.writeChars(value);
        if (isNullTerminated())
            out.writeShort(0);
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // MaximumCount for conformant array
        in.align(Alignment.FOUR);
        in.fullySkipBytes(4);
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // Offset for varying array
        // Alignment 4 - Already aligned
        this.offset = readIndex("Offset", in);
        // ActualCount for varying array
        // Alignment 4 - Already aligned
        this.actualCount = readIndex("ActualCount", in);
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // Read prefix (if any)
        in.align(Alignment.TWO);
        in.fullySkipBytes(2 * offset);
        // Entities for conformant array
        // If we expect a null terminator, then skip it when reading the string
        final int length;
        final boolean nullTerminated;
        if (isNullTerminated() && this.actualCount > 0) {
            length = this.actualCount - 1;
            nullTerminated = true;
        }
        else {
            length = this.actualCount;
            nullTerminated = false;
        }
        final StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // <NDR: unsigned short>
            // Alignment: 2 - Already aligned
            sb.append(in.readChar());
        }
        this.value = sb.toString();
        // Skip null terminator (if any)
        // Alignment: 2 - Already aligned
        if (nullTerminated)
            in.fullySkipBytes(2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isNullTerminated(), getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof WChar)) {
            return false;
        }
        final WChar other = (WChar) obj;
        return isNullTerminated() == other.isNullTerminated()
                && Objects.equals(getValue(), other.getValue());
    }

    @Override
    public String toString() {
        return getValue() == null ? "null" : String.format("\"%s\"", getValue());
    }

    private int getCodePoints() {
        return getValue().length() + (isNullTerminated() ? 1 : 0);
    }

    private int readIndex(String name, PacketInput in) throws IOException {
        final long ret = in.readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("%s %d > %d", name, ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }
}
