/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */
package com.rapid7.client.dcerpc.msrrp.dto;

import javax.activation.UnsupportedDataTypeException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;

public class RegistryValue {
    private final String name;
    private final RegistryValueType type;
    private final byte[] data;

    public RegistryValue(final String name, final RegistryValueType type, final byte[] data) throws IOException {
        if (name == null) {
            throw new IllegalArgumentException("Name is invalid: " + name);
        }

        if (type == null) {
            throw new IllegalArgumentException("Type is invalid: " + type);
        }

        if (data == null) {
            throw new IllegalArgumentException("Data is invalid: " + data);
        }

        this.name = name;
        this.type = type;

        switch (type) {
            case REG_DWORD:
            case REG_DWORD_BIG_ENDIAN:
                if (data.length != 4) {
                    throw new IOException(String.format("Data type %s is invalid with length %d: 0x%s,", type, data.length, Hex.toHexString(data).toUpperCase()));
                }
                break;
            case REG_QWORD:
                if (data.length != 8) {
                    throw new IOException(String.format("Data type %s is invalid with length %d: 0x%s,", type, data.length, Hex.toHexString(data).toUpperCase()));
                }
                break;
            case REG_EXPAND_SZ:
            case REG_LINK:
            case REG_SZ:
            case REG_MULTI_SZ:
                if ((data.length & 1) != 0) {
                    throw new IOException(String.format("Data type %s is invalid with length %d: 0x%s,", type, data.length, Hex.toHexString(data).toUpperCase()));
                }
            default:
        }

        switch (type) {
            case REG_DWORD:
            case REG_QWORD:
                this.data = Arrays.copyOf(data, data.length);
                ArrayUtils.reverse(this.data);
                break;
            default:
                this.data = Arrays.copyOf(data, data.length);
        }
    }

    public String getName() {
        return name;
    }

    public RegistryValueType getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    public int getDataAsInt() {
        switch (type) {
            case REG_DWORD:
            case REG_DWORD_BIG_ENDIAN:
                return new BigInteger(1, data).intValue();
            default:
                throw new IllegalStateException();
        }
    }

    public long getDataAsLong() {
        switch (type) {
            case REG_DWORD:
            case REG_DWORD_BIG_ENDIAN:
            case REG_QWORD:
                return new BigInteger(1, data).longValue();
            default:
                throw new IllegalStateException();
        }
    }

    public String getDataAsBinaryStr() {
        final StringBuilder text = new StringBuilder();
        for (final byte byteValue : data) {
            for (int bitIndex = 7; bitIndex >= 0; bitIndex--) {
                final int bitValue = byteValue >> bitIndex & 0x1;
                text.append(bitValue);
            }
        }
        return text.toString();
    }

    public String getDataAsHexStr() {
        return Hex.toHexString(data).toUpperCase();
    }

    public String[] getDataAsMultiStr() throws UnsupportedEncodingException {
        switch (type) {
            case REG_MULTI_SZ:
                final StringBuilder element = new StringBuilder();
                final List<String> elements = new LinkedList<>();
                final ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                while (dataBuffer.hasRemaining()) {
                    final short shortValue = dataBuffer.getShort();
                    if (shortValue == 0) {
                        elements.add(element.toString());
                        element.setLength(0);
                    } else {
                        element.append((char) shortValue);
                    }
                }
                if (0 < element.length()) {
                    elements.add(element.toString());
                    element.setLength(0);
                }
                if (0 < elements.size()) {
                    final int nullIndex = elements.size() - 1;
                    if (elements.get(nullIndex).isEmpty()) {
                        elements.remove(nullIndex);
                    }
                }
                return elements.toArray(new String[elements.size()]);
            default:
                throw new IllegalStateException();
        }
    }

    public String getDataAsStr() throws UnsupportedEncodingException, UnsupportedDataTypeException {
        final StringBuilder repr = new StringBuilder();
        switch (type) {
            case REG_BINARY:
            case REG_NONE:
                repr.append(getDataAsBinaryStr());
                break;
            case REG_DWORD:
            case REG_DWORD_BIG_ENDIAN:
                repr.append(getDataAsInt());
                break;
            case REG_QWORD:
                repr.append(getDataAsLong());
                break;
            case REG_EXPAND_SZ:
            case REG_LINK:
            case REG_SZ:
                final ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                dataBuffer.order(ByteOrder.LITTLE_ENDIAN);
                while (dataBuffer.hasRemaining()) {
                    final short shortValue = dataBuffer.getShort();
                    if (shortValue == 0) {
                        break;
                    }
                    repr.append((char) shortValue);
                }
                break;
            case REG_MULTI_SZ:
                final StringBuilder multiRepr = new StringBuilder();
                for (final String element : getDataAsMultiStr()) {
                    if (0 < multiRepr.length()) {
                        multiRepr.append(", ");
                    }
                    multiRepr.append("\"");
                    multiRepr.append(element.replace("\"", "\\\""));
                    multiRepr.append("\"");
                }
                if (0 < multiRepr.length()) {
                    repr.append("{");
                    repr.append(multiRepr.toString());
                    repr.append("}");
                }
                break;
            case REG_FULL_RESOURCE_DESCRIPTOR:
            case REG_RESOURCE_LIST:
            case REG_RESOURCE_REQUIREMENTS_LIST:
            default:
                throw new UnsupportedDataTypeException();
        }
        return repr.toString();
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(name);
        result.append(" ");
        result.append("(");
        result.append(type);
        result.append(")");

        try {
            final String value = getDataAsStr();
            if (!value.isEmpty()) {
                result.append(" = ");
                result.append(getDataAsStr());
                result.append(" (0x");
                result.append(getDataAsHexStr());
                result.append(")");
            }
        } catch (final UnsupportedEncodingException | UnsupportedDataTypeException exception) {
            result.append(" ! ");
            result.append(exception.getClass().getName());
            result.append("::");
            result.append(exception.getLocalizedMessage());
        }

        return result.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, Arrays.hashCode(data));
    }

    @Override
    public boolean equals(final Object anObject) {
        return anObject instanceof RegistryValue && Objects.equals(name, ((RegistryValue) anObject).name) && Objects.equals(type, ((RegistryValue) anObject).type) && Arrays.equals(data, ((RegistryValue) anObject).data);
    }
}
