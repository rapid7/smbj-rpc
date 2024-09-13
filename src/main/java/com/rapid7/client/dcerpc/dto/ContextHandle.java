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

package com.rapid7.client.dcerpc.dto;

import com.google.common.io.BaseEncoding;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a Windows <a href="https://msdn.microsoft.com/en-us/library/windows/desktop/aa373605(v=vs.85).aspx">Context Handle</a>
 */
public class ContextHandle {
    private final byte[] bytes;

    public ContextHandle(byte[] handle) {
        if (handle == null) {
            throw new IllegalArgumentException("Expecting non-null handle");
        } else if (handle.length != 20) {
            throw new IllegalArgumentException(String.format(
                    "Expecting 20 entries in handle, got: %d", handle.length));
        }
        this.bytes = handle;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.bytes);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!Objects.equals(getClass(), obj.getClass())) {
            return false;
        }
        return Arrays.equals(this.bytes, ((ContextHandle) obj).bytes);
    }

    @Override
    public String toString() {
        final StringBuilder handleStr = new StringBuilder();
        for (final byte handleByte : bytes) {
            handleStr.append(String.format("%02X", handleByte));
        }
        return handleStr.toString();
    }

    public static ContextHandle fromHex(final String hString) {
        byte[] bytes = new byte[20];
        if (hString == null || hString.length() > (bytes.length * 2)) {
            throw new IllegalArgumentException("hString is invalid: " + hString);
        }
        final byte[] handle = BaseEncoding.base16().decode(hString.toUpperCase());
        int srcPos = 0;
        int index = 0;
        while (index < handle.length) {
            if (handle[index++] == 0) {
                srcPos = index;
            }
        }
        final int dstPos = bytes.length - handle.length + srcPos;
        final int dstLen = handle.length - srcPos;
        System.arraycopy(handle, srcPos, bytes, dstPos, dstLen);
        return new ContextHandle(bytes);
    }
}
