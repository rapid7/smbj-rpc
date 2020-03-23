/*
 * Copyright 2020, Vadim Frolov.
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
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * Represents a counted string of Unicode (UTF-16) characters.
 * <br>
 * <b>Alignment 4:</b><pre>
 *    unsigned short Length;: 2
 *    unsigned short MaximumLength;: 2
 *    [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;: 4</pre>
 *  <br>
 * <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-rsp/2746e0cc-37f3-4742-9fdb-2b986bd6a8e1">RegUnicodeString</a>:
 * <blockquote><pre>
 *  The RegUnicodeString structure specifies a Unicode string. This structure is defined in IDL as follows:
 *      typedef struct _REG_UNICODE_STRING {
 *          unsigned short Length;
 *          unsigned short MaximumLength;
 *          [size_is(MaximumLength/2), length_is(Length/2)] unsigned short* Buffer;
 *      } REG_UNICODE_STRING,
 *      *PREG_UNICODE_STRING;
 *  Length: The number of bytes actually used by the string. Because all UTF-16 characters occupy 2 bytes, this MUST be an even number in the range [0...65534]. The behavior for odd values is unspecified.
 *  MaximumLength: The number of bytes allocated for the string. This MUST be an even number in the range [Length...65534].
 *  Buffer:  The Unicode UTF-16 characters comprising the string described by the structure. Note that counted strings might be terminated by a 0x0000 character, by convention; if such a terminator is present, it SHOULD NOT count toward the Length (but MUST, of course, be included in the MaximumLength).
 * </pre></blockquote>
 * NOTE: This class is based on RPCUnicodeString.
 * <br>
 * An REG_UNICODE_STRING can be null terminated. However this is abstracted away from the client and is only
 * used during marshalling or unmarshalling.
 * You should not provide a null terminator to your String when marshalling this object, and should
 * not expect one in return.
 * <br>
 * <b>Marshalling Usage:</b><pre>
 *      String myValue = "some string";
 *      RegUnicodeString regUnicodeString = RegUnicodeString.NullTerminated.of(myValue);
 *      packetOut.writeMarshallable(regUnicodeString);</pre>
 * <b>Unmarshalling Usage:</b><pre>
 *      RegUnicodeString regUnicodeString = new RegUnicodeString.NullTerminated();
 *      packetIn.readUnmarshallable(regUnicodeString);
 *      String myValue = regUnicodeString.getValue();</pre>
 */
public abstract class RegUnicodeString implements Unmarshallable, Marshallable {

    /**
     * An REG_UNICODE_STRING which is expected to be null terminated during marshalling/unmarshalling.
     */
    public static class NullTerminated extends RegUnicodeString {
        /**
         * @param value The value; may be null.
         * @return A new {@link NullTerminated} {@link RegUnicodeString} with the provided value.
         */
        public static NullTerminated of(String value) {
            final NullTerminated str = new NullTerminated();
            str.setValue(value);
            return str;
        }

        @Override
        ShortChar createShortChar() {
            return new ShortChar.NullTerminated();
        }
    }

    /**
     * An REG_UNICODE_STRING which is not expected to be null terminated during marshalling/unmarshalling.
     */
    public static class NonNullTerminated extends RegUnicodeString {
        /**
         * @param value The value; may be null.
         * @return A new {@link NonNullTerminated} {@link RegUnicodeString} with the provided value.
         */
        public static NonNullTerminated of(String value) {
            final NonNullTerminated str = new NonNullTerminated();
            str.setValue(value);
            return str;
        }

        @Override
        ShortChar createShortChar() {
            return new ShortChar.NonNullTerminated();
        }
    }

    private ShortChar shortChar;

    abstract ShortChar createShortChar();

    /**
     * @return The {@link String} representation of this {@link RPCUnicodeString}.
     * May be null. Will never include a null terminator.
     */
    public String getValue() {
        if (this.shortChar == null) return null;
        return this.shortChar.getValue();
    }

    /**
     * @param value The {@link String} representation for this {@link RPCUnicodeString}.
     *              May be null. Must not include a null terminator.
     */
    public void setValue(String value) {
        if (value == null) {
            this.shortChar = null;
        } else {
            this.shortChar = createShortChar();
            this.shortChar.setValue(value);
        }
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // No preamble. Conformant array of `unsigned short*` is a reference, and so preamble is not required.
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment
        out.align(Alignment.FOUR);
        if (this.shortChar == null) {
            // <NDR: unsigned short> unsigned short Length;
            // Alignment 2 - Already aligned
            out.writeShort(0);
            // <NDR: unsigned short> unsigned short MaximumLength;
            // Alignment 2 - Already aligned
            out.writeShort(0);
        } else {
            // UTF-16 encoded string is 2 bytes per count point
            // Null terminator must also be considered
            final int byteLength = 2 * this.shortChar.getValue().length();
            final int maxLength = byteLength + (this.shortChar.isNullTerminated() ? 2 : 0);
            // <NDR: unsigned short> unsigned short Length;
            // Alignment 2 - Already aligned
            out.writeShort(byteLength);
            // <NDR: unsigned short> unsigned short MaximumLength;
            // Alignment 2 - Already aligned
            out.writeShort(maxLength);
        }
        // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment 4 - Already aligned
        out.writeReferentID(this.shortChar);
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (this.shortChar != null) {
            out.writeMarshallable(this.shortChar);
        }
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble. Conformant array of `WCHAR*` is a reference, and so preamble is not required.
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: unsigned short> unsigned short Length;
        // Alignment: 2 - Already aligned
        in.fullySkipBytes(2);
        // <NDR: unsigned short> unsigned short MaximumLength;
        // Alignment: 2 - Already aligned
        in.fullySkipBytes(2);
        // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0) this.shortChar = createShortChar();
        else this.shortChar = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (this.shortChar != null) {
            in.readUnmarshallable(this.shortChar);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortChar);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof RPCUnicodeString)) {
            return false;
        }
        return Objects.equals(shortChar, ((RegUnicodeString) obj).shortChar);
    }

    @Override
    public String toString() {
        return getValue() == null ? "null" : String.format("\"%s\"", getValue());
    }
}
