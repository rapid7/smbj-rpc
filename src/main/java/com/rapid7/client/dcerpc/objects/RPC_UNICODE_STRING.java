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
 *
 *      typedef struct _RPC_UNICODE_STRING {
 *          unsigned short Length;
 *          unsigned short MaximumLength;
 *          [size_is(MaximumLength/2), length_is(Length/2)]
 *          WCHAR* Buffer;
 *      } RPC_UNICODE_STRING,
 *      *PRPC_UNICODE_STRING;
 *
 * NOTE: This structure has purposely not been mapped 1-1 with the dcerpc struct.
 * There is no practical reason for a client to access the Length, MaximumLength, or raw byte[] of this
 * struct, and so this class only stores the UTF-16 Java String. Conversions are done during marshalling/unmarshalling.
 *
 * An RPC_UNICODE_STRING can be null terminated. However this is abstracted away from the client and is only
 * used during marshalling or unmarshalling.
 * You should not provide a null terminator to your String when marshalling this object, and should
 * not expect one in return.
 *
 * Marshalling Usage:
 *      String myValue = "some string";
 *      RPC_UNICODE_STRING rpcUnicodeString = RPC_UNICODE_STRING.of(true, myValue);
 *      packetOut.writeMarshallable(rpcUnicodeString);
 * Unmarshalling Usage:
 *      RPC_UNICODE_STRING rpcUnicodeString = RPC_UNICODE_STRING.of(true);
 *      packetIn.readUnmarshallable(rpcUnicodeString);
 *      String myValue = rpcUnicodeString.getValue();
 */
public abstract class RPC_UNICODE_STRING implements Unmarshallable, Marshallable {

    /**
     * Convenience method for construction of RPC_UNICODE_STRING.
     * @param nullTerminated Whether or not the RPC_UNICODE_STRING is null terminated.
     * @return A new RPC_UNICODE_STRING with an initial value of null.
     */
    public static RPC_UNICODE_STRING of(boolean nullTerminated) {
        return (nullTerminated ? new NullTerminated() : new NotNullTerminated());
    }

    /**
     * Convenience method for construction of RPC_UNICODE_STRING.
     * @param nullTerminated Whether or not the RPC_UNICODE_STRING is null terminated.
     * @param value The initial value of the RPC_UNICODE_STRING.
     * @return A new RPC_UNICODE_STRING with the provided initial value.
     */
    public static RPC_UNICODE_STRING of(boolean nullTerminated, String value) {
        RPC_UNICODE_STRING obj = of(nullTerminated);
        obj.setValue(value);
        return obj;
    }

    /**
     * An RPC_UNICODE_STRING which is expected to be null terminated during marshalling/unmarshalling.
     */
    static class NullTerminated extends RPC_UNICODE_STRING {
        @Override
        boolean isNullTerminated() {
            return true;
        }
    }

    /**
     * An RPC_UNICODE_STRING which is not expected to be null terminated during marshalling/unmarshalling.
     */
    static class NotNullTerminated extends RPC_UNICODE_STRING {
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
    public Alignment getAlignment() {
        /*
         * unsigned short Length: 2
         * unsigned short MaximumLength: 2
         * [size_is(MaximumLength/2), length_is(Length/2)] WCHAR* Buffer: 4
         */
        return Alignment.FOUR;
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // No preamble. Conformant array of `WCHAR*` is a reference, and so preamble is not required.
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        if (value == null) {
            out.writeShort((short) 0);
            out.writeShort((short) 0);
            out.writeNull();
        } else {
            // UTF-16 encoded string is 2 bytes per count point
            // Null terminator must also be considered
            final int byteLength = 2 * value.length() + (isNullTerminated() ? 2 : 0);
            out.writeShort((short) byteLength);
            out.writeShort((short) byteLength);
            out.writeReferentID();
        }
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (value != null) {
            final int codepoints = value.length() + (isNullTerminated() ? 1 : 0);
            //Preamble
            // MaximumCount for conformant array
            out.writeInt(codepoints);

            //Entity
            // Offset for varying array
            out.writeInt(0);
            // ActualCount for varying array
            out.writeInt(codepoints);

            //Deferrals
            // Entities for conformant+varying array
            out.writeChars(value);
            if (isNullTerminated())
                out.writeShort((short) 0);
            // Align the conformant+varying array
            out.align(Alignment.FOUR);
        }
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble. Conformant array of `WCHAR*` is a reference, and so preamble is not required.
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.readShort();
        in.readShort();
        if (in.readReferentID() != 0)
            // This is 0 cost - Compile time constants are internal objects
            value = "";
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (value != null) {
            //Preamble
            // MaximumCount for conformant array - This is *not* the size of the array, so is not useful to us
            in.readInt();

            //Entity
            // Offset for varying array
            final int offset = in.readInt();
            // ActualCount for varying array
            final int actualCount = in.readInt();
            // If we expect a null terminator, then skip it when reading the string
            final int stringCount = (isNullTerminated() ? (actualCount - 1) : actualCount);

            //Deferrals
            // Entities for conformant array
            final StringBuilder result = new StringBuilder(stringCount);
            // Read prefix (if any)
            for (int i = 0; i < offset; i++) {
                in.readShort();
            }
            // Read subset
            for (int i = 0; i < stringCount; i++) {
                result.append((char) in.readShort());
            }
            // Read suffix (if any)
            for (int i = stringCount; i < actualCount; i++) {
                in.readShort();
            }
            // Align the conformant+varying array
            in.align(Alignment.FOUR);
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
        } else if (! (obj instanceof RPC_UNICODE_STRING)) {
            return false;
        }
        RPC_UNICODE_STRING other = (RPC_UNICODE_STRING) obj;
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
