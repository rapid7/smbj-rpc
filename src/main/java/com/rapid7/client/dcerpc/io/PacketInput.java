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
import java.io.InputStream;
import java.math.BigInteger;
import org.bouncycastle.util.encoders.Hex;
import com.google.common.primitives.Longs;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class PacketInput extends PrimitiveInput {
    public PacketInput(final InputStream inputStream) {
        super(inputStream);
    }

    public <T extends Unmarshallable> T readUnmarshallable(T unmarshallable) throws IOException {
        unmarshallable.unmarshalPreamble(this);
        unmarshallable.unmarshalEntity(this);
        unmarshallable.unmarshalDeferrals(this);
        // TODO Align should be called, but can require resetting the packet counts
        return unmarshallable;
    }

    public Integer readIntRef() throws IOException {
        return 0 != readReferentID() ? readInt() : null;
    }

    public Long readLongRef() throws IOException {
        return 0 != readReferentID() ? readLong() : null;
    }

    public int readReferentID() throws IOException {
        return readInt();
    }

    public byte[] readByteArray() throws IOException {
        readInt();
        final int initialOffset = readInt();
        final int actualCount = readInt();
        final byte[] result = new byte[initialOffset + actualCount];

        for (int index = initialOffset; index < result.length; index++) {
            result[index] = readByte();
        }

        return result;
    }

    public byte[] readByteArrayRef() throws IOException {
        final byte[] result;
        if (0 != readReferentID()) {
            result = readByteArray();
            align();
        } else {
            result = null;
        }

        return result;
    }

    public byte[] readRawBytes(int length) throws IOException {
        byte[] bytes = new byte[length];
        readRawBytes(bytes);

        return bytes;
    }

    public void readRawBytes(byte[] buf) throws IOException {
        readFully(buf, 0, buf.length);
    }

    public String readString(final boolean nullTerminated) throws IOException {
        final StringBuffer result;

        readInt();
        final int initialOffset = readInt();
        final int currentChars = readInt();

        result = new StringBuffer(currentChars);
        result.setLength(initialOffset);

        int currentOffset = 0;
        while (currentOffset++ < currentChars) {
            final char currentChar = (char) readShort();
            if (nullTerminated && currentChar == 0) {
                break;
            }
            result.append(currentChar);
        }

        while (currentOffset++ < currentChars) {
            readShort();
        }

        align();

        return result.toString();
    }

    public String readStringRef(final boolean nullTerminated) throws IOException {
        final String result;

        if (0 != readReferentID()) {
            result = readString(nullTerminated);
            align();
        } else {
            result = null;
        }

        return result != null ? result.toString() : null;
    }

    public String readRPCUnicodeString(final boolean nullTerminated) throws IOException {
        return readStringBuf(nullTerminated);
    }

    public String readStringBuf(final boolean nullTerminated) throws IOException {
        readShort(); // Current byte length
        readShort(); // Maximum byte length

        return readStringRef(nullTerminated);
    }

    public String readStringBufRef(final boolean nullTerminated) throws IOException {
        final String result;
        if (0 != readReferentID()) {
            result = readStringBuf(nullTerminated);
            align();
        } else {
            result = null;
        }

        return result;
    }

    /**
     * See <a href="http://technet.microsoft.com/en-us/library/cc753858(WS.10).aspx>Technet Article</a>
     * <p>
     * When creating PSOs, the values of these attributes are in I8 format, which stores time in the intervals of -100
     * nanoseconds. (Schema: attributeSyntax = 2.5.5.16 (I8).) To set these attributes to appropriate values, convert
     * time values in minutes, hours, or days to time values in the intervals of 100 nanoseconds, and then precede the
     * resultant values with a negative sign.
     * <p>
     * <table>
     * <col width="25%"/> <col width="75%"/> <thead>
     * <tr>
     * <th>Time Unit</th>
     * <th>Multiplication Factor</th>
     * </tr>
     * </thead> <tbody>
     * <tr>
     * <td>m minutes</td>
     * <td>-60*(10^7) = - 600000000</td>
     * </tr>
     * <tr>
     * <td>h hours</td>
     * <td>-60*60* (10^7) = -36000000000</td>
     * </tr>
     * <tr>
     * <td>d days</td>
     * <td>-24*60*60*(10^7) = -864000000000</td>
     * </tr>
     * </tbody>
     * </table>
     * <p>
     * For example, if you want to set the msDS-MaximumPasswordAge to 10 days, multiply 10 by -864000000000 and apply the
     * resulting I8 value to the msDS-MaximumPasswordAge attribute (in this example, -8640000000000). If you want to set
     * msDS-LockoutDuration to 30 minutes, multiply 30 by -600000000 to get the corresponding I8 value (in this example,
     * -18000000000).
     * <p>
     * INTER_NEVER = {@value}, the constant storing the literal value for an infinite duration (never expire).
     */

    /**
     * Interval values are stored negative, in 100 ns  values. To get the interval in seconds,
     * we must multiply by 10000000 (100ns/sec) and get the absolute value.
     */
    public int readInterval() throws IOException {
        final byte[] rawBytes = Longs.toByteArray(readLong());
        String s = Hex.toHexString(rawBytes);
        System.out.println(s);

        final BigInteger interval = new BigInteger(rawBytes);

        // -1, infinite duration (never expire)
        if (interval.longValue() == -1) {
            return (int) interval.longValue();
        }

        System.out.println(interval.longValue());
        int result = (int) (interval.abs().longValue() / 10000000);
        return result;
        // return (int) (interval.longValue() / 10000000);

    }
}
