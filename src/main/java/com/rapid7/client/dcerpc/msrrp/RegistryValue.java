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
package com.rapid7.client.dcerpc.msrrp;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;

public class RegistryValue {
    private final String name;
    private final RegistryValueType type;
    private final byte[] data;

    public RegistryValue(final String name, final RegistryValueType type, final byte[] data) {
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
                    throw new IllegalArgumentException(
                        String.format("Data type %s is invalid with length %d: %s," + type, data.length,
                            String.format(String.format("0x%%0%dX", data.length << 1), new BigInteger(1, data))));
                }
                break;
            case REG_QWORD:
                if (data.length != 8) {
                    throw new IllegalArgumentException(
                        String.format("Data type %s is invalid with length %d: %s," + type, data.length,
                            String.format(String.format("0x%%0%dX", data.length << 1), new BigInteger(1, data))));
                }
                break;
            case REG_SZ:
                if ((data.length & 1) != 0) {
                    throw new IllegalArgumentException(
                        String.format("Data type %s is invalid with length %d: %s," + type, data.length,
                            String.format(String.format("0x%%0%dX", data.length << 1), new BigInteger(1, data))));
                }
            default:
                break;
        }

        switch (type) {
            case REG_DWORD:
            case REG_QWORD:
                this.data = Arrays.copyOf(data, data.length);
                ArrayUtils.reverse(this.data);
                break;
            case REG_SZ:
                final ByteBuffer dataBuffer = ByteBuffer.wrap(data);
                int consumeBytes = 0;
                while (dataBuffer.hasRemaining()) {
                    final short shortValue = dataBuffer.getShort();
                    if (shortValue == 0) {
                        break;
                    }
                    consumeBytes += 2;
                }

                this.data = Arrays.copyOf(data, consumeBytes);
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

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append(name);
        result.append(" ");
        result.append("(");
        result.append(type);
        result.append(")");

        String value = "";

        switch (type) {
            case REG_BINARY:
                final StringBuilder text = new StringBuilder();
                for (final byte byteValue : data) {
                    for (int bitIndex = 7; bitIndex >= 0; bitIndex--) {
                        final int bitValue = byteValue >> bitIndex & 0x1;
                        text.append(bitValue);
                    }
                }
                value = text.toString();
                break;
            case REG_DWORD:
            case REG_DWORD_BIG_ENDIAN:
            case REG_QWORD:
                value = String.format(String.format("0x%%0%dX", data.length << 1), new BigInteger(1, data));
                break;
            case REG_EXPAND_SZ:
            case REG_MULTI_SZ:
            case REG_SZ:
                try {
                    value = new String(data, 0, data.length, "UTF-16LE");
                }
                catch (final UnsupportedEncodingException exception) {
                    result.append(" ! ");
                    result.append(exception.getLocalizedMessage());
                }
                break;
            case REG_FULL_RESOURCE_DESCRIPTOR:
            case REG_LINK:
            case REG_NONE:
            case REG_RESOURCE_LIST:
            case REG_RESOURCE_REQUIREMENTS_LIST:
            default:
                break;

        }

        if (!value.isEmpty()) {
            result.append(" = ");
            result.append(value);
        }

        return result.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, Arrays.hashCode(data));
    }

    @Override
    public boolean equals(final Object anObject) {
        return anObject instanceof RegistryValue && Objects.equals(name, ((RegistryValue) anObject).name) &&
            Objects.equals(type, ((RegistryValue) anObject).type) &&
            Arrays.equals(data, ((RegistryValue) anObject).data);
    }
}
