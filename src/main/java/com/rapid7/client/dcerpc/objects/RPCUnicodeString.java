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
package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * Represents a UTF-16 encoded unicode string in dcerpc.
 * <br>
 * <b>Alignment: 4</b><pre>
 *      unsigned short Length;: 2
 *      unsigned short MaximumLength;: 2
 *      [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;: 4 (Max[4, 1])</pre>
 * <br>
 * <a href="https://msdn.microsoft.com/en-us/library/cc230365.aspx">RPC_UNICODE_STRING</a>:
 * <blockquote><pre>
 *  The RPC_UNICODE_STRING structure specifies a Unicode string. This structure is defined in IDL as follows:
 *      typedef struct _RPC_UNICODE_STRING {
 *          unsigned short Length;
 *          unsigned short MaximumLength;
 *          [size_is(MaximumLength/2), length_is(Length/2)]
 *          WCHAR* Buffer;
 *      } RPC_UNICODE_STRING,
 *      *PRPC_UNICODE_STRING;
 *  Length: The length, in bytes, of the string pointed to by the Buffer member, not including the terminating null character if any. The length MUST be a multiple of 2. The length SHOULD equal the entire size of the Buffer, in which case there is no terminating null character. Any method that accesses this structure MUST use the Length specified instead of relying on the presence or absence of a null character.
 *  MaximumLength: The maximum size, in bytes, of the string pointed to by Buffer. The size MUST be a multiple of 2. If not, the size MUST be decremented by 1 prior to use. This value MUST not be less than Length.
 *  Buffer: A pointer to a string buffer. If MaximumLength is greater than zero, the buffer MUST contain a non-null value.
 * </pre></blockquote>
 * NOTE: This structure has purposely not been mapped 1-1 with the dcerpc struct.
 * There is no practical reason for a client to access the Length, MaximumLength, or raw byte[] of this
 * struct, and so this class only stores the UTF-16 Java String. Conversions are done during marshalling/unmarshalling.
 * <br>
 * An RPC_UNICODE_STRING can be null terminated. However this is abstracted away from the client and is only
 * used during marshalling or unmarshalling.
 * You should not provide a null terminator to your String when marshalling this object, and should
 * not expect one in return.
 * <br>
 * <b>Marshalling Usage:</b><pre>
 *      String myValue = "some string";
 *      RPC_UNICODE_STRING rpcUnicodeString = RPC_UNICODE_STRING.of(true, myValue);
 *      packetOut.writeMarshallable(rpcUnicodeString);</pre>
 * <b>Unmarshalling Usage:</b><pre>
 *      RPC_UNICODE_STRING rpcUnicodeString = RPC_UNICODE_STRING.of(true);
 *      packetIn.readUnmarshallable(rpcUnicodeString);
 *      String myValue = rpcUnicodeString.getValue();</pre>
 */
public abstract class RPCUnicodeString implements Unmarshallable, Marshallable {

    /**
     * An RPC_UNICODE_STRING which is expected to be null terminated during marshalling/unmarshalling.
     */
    public static class NullTerminated extends RPCUnicodeString {
        public static NullTerminated of(String value) {
            NullTerminated str = new NullTerminated();
            str.setValue(value);
            return str;
        }

        @Override
        boolean isNullTerminated() {
            return true;
        }
    }

    /**
     * An RPC_UNICODE_STRING which is not expected to be null terminated during marshalling/unmarshalling.
     */
    public static class NonNullTerminated extends RPCUnicodeString {
        public static NonNullTerminated of(String value) {
            NonNullTerminated str = new NonNullTerminated();
            str.setValue(value);
            return str;
        }

        @Override
        boolean isNullTerminated() {
            return false;
        }
    }

    private String value;

    abstract boolean isNullTerminated();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // No preamble. Conformant array of `WCHAR*` is a reference, and so preamble is not required.
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // Structure Alignment
        out.align(Alignment.FOUR);
        if (value == null) {
            // <NDR: unsigned short> unsigned short Length;
            // Alignment 2 - Already aligned
            out.writeShort((short) 0);
            // <NDR: unsigned short> unsigned short MaximumLength;
            // Alignment 2 - Already aligned
            out.writeShort((short) 0);
            // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
            // Alignment 4 - Already aligned
            out.writeNull();
        } else {
            // UTF-16 encoded string is 2 bytes per count point
            // Null terminator must also be considered
            final int byteLength = 2 * value.length() + (isNullTerminated() ? 2 : 0);
            // <NDR: unsigned short> unsigned short Length;
            // Alignment 2 - Already aligned
            out.writeShort((short) byteLength);
            // <NDR: unsigned short> unsigned short MaximumLength;
            // Alignment 2 - Already aligned
            out.writeShort((short) byteLength);
            // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
            // Alignment 4 - Already aligned
            out.writeReferentID();
        }
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (value != null) {
            final int codepoints = value.length() + (isNullTerminated() ? 1 : 0);
            // MaximumCount for conformant array
            out.align(Alignment.FOUR);
            out.writeInt(codepoints);
            // Offset for varying array
            // Alignment 4 - Already aligned
            out.writeInt(0);
            // ActualCount for varying array
            // Alignment 4 - Already aligned
            out.writeInt(codepoints);
            // Entities for conformant+varying array
            // Alignment 1 - Already aligned
            out.writeChars(value);
            if (isNullTerminated())
                out.writeShort((short) 0);
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
        in.readShort();
        // <NDR: unsigned short> unsigned short MaximumLength;
        // Alignment: 2 - Already aligned
        in.readShort();
        // <NDR: pointer> [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            // This is 0 cost - Compile time constants are internal objects
            value = "";
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (value != null) {
            //Preamble
            // <NDR: unsigned long> MaximumCount for conformant array - This is *not* the size of the array, so is not useful to us
            in.align(Alignment.FOUR);
            in.readInt();

            //Entity
            // <NDR: unsigned long> Offset for varying array
            // Alignment: 4 - Already aligned
            final int offset = in.readInt();
            // <NDR: unsigned long> ActualCount for varying array
            // Alignment: 4 - Already aligned
            final int actualCount = in.readInt();
            // If we expect a null terminator, then skip it when reading the string
            final int stringCount = (isNullTerminated() ? (actualCount - 1) : actualCount);

            //Deferrals
            // Entities for conformant array
            final StringBuilder result = new StringBuilder(stringCount);
            // Read prefix (if any)
            for (int i = 0; i < offset; i++) {
                // <NDR: unsigned short>
                // Alignment: 2 - Already aligned
                in.readShort();
            }
            // Read subset
            for (int i = 0; i < stringCount; i++) {
                // <NDR: unsigned short>
                // Alignment: 2 - Already aligned
                result.append((char) in.readShort());
            }
            // Read suffix (if any)
            for (int i = stringCount; i < actualCount; i++) {
                // <NDR: unsigned short>
                // Alignment: 2 - Already aligned
                in.readShort();
            }
            this.value = result.toString();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(isNullTerminated(), getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof RPCUnicodeString)) {
            return false;
        }
        RPCUnicodeString other = (RPCUnicodeString) obj;
        return Objects.equals(isNullTerminated(), other.isNullTerminated())
                && Objects.equals(getValue(), other.getValue());
    }

    @Override
    public String toString() {
        return String.format("RPC_UNICODE_STRING{value:%s, nullTerminated:%b}",
                getValue() == null ? "null" : String.format("\"%s\"", getValue()),
                isNullTerminated());
    }
}
