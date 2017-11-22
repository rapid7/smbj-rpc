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

package com.rapid7.client.dcerpc.serviceobjects;

import org.bouncycastle.util.encoders.Hex;

public class SID {

    public static class MalformedSIDException extends IllegalArgumentException {
        public MalformedSIDException(String msg) {
            super(msg);
        }
    }

    /**
     * @param sidString SID string. Must not be {@code null}.
     * @return A {@link SID} parsed from the provided string.
     * @throws MalformedSIDException The provided SID is malformed.
     */
    public static SID fromString(String sidString) throws MalformedSIDException {
        final String[] split = sidString.toUpperCase().trim().split("-");
        if (split.length < 3)
            throw new MalformedSIDException("Illegal SID format: " + sidString);

        if (!split[0].equals("S"))
            throw new MalformedSIDException("SID must start with S:" + sidString);

        try {
            final char revision = (char) Integer.parseInt(split[1]);
            final String identifierAuthorityString = split[2];
            final byte[] identifierAuthority;
            if (identifierAuthorityString.toUpperCase().startsWith("0X")) {
                final String bytes = identifierAuthorityString.substring(2, identifierAuthorityString.length());
                identifierAuthority = Hex.decode(bytes);
            } else {
                final long identifierAuthorityValue = Long.parseLong(identifierAuthorityString);
                identifierAuthority = new byte[] {
                        (byte) ((identifierAuthorityValue >> 40) & 0xFF),
                        (byte) ((identifierAuthorityValue >> 32) & 0xFF),
                        (byte) ((identifierAuthorityValue >> 24) & 0xFF),
                        (byte) ((identifierAuthorityValue >> 16) & 0xFF),
                        (byte) ((identifierAuthorityValue >> 8) & 0xFF),
                        (byte) (identifierAuthorityValue & 0xFF)
                };
            }
            final long[] subAuthorities = new long[split.length - 3];
            for (int i = 0; i < subAuthorities.length; i++) {
                subAuthorities[i] = Long.parseLong(split[i + 3]);
            }
            return new SID(revision, identifierAuthority, subAuthorities);
        } catch (NumberFormatException e) {
            throw new MalformedSIDException("Unable to parse SID token: " + e.getMessage());
        }
    }

    private final char revision;
    private final byte[] identifierAuthority;
    private final long[] subAuthority;

    public SID(final char revision, final byte[] identifierAuthority, final long[] subAuthority) {
        if (identifierAuthority == null) {
            throw new IllegalArgumentException("Expecting non-null identifierAuthority");
        }
        else if (identifierAuthority.length != 6) {
            throw new IllegalArgumentException(String.format(
                    "Expected 6 entries in identifierAuthority, got: %d", identifierAuthority.length));
        }
        this.revision = revision;
        this.identifierAuthority = identifierAuthority;
        this.subAuthority = subAuthority != null ? subAuthority : new long[0];
    }
    public char getRevision() {
        return revision;
    }

    public byte[] getIdentifierAuthority() {
        return identifierAuthority;
    }

    public long[] getSubAuthority() {
        return subAuthority;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder("S-");
        b.append((int)revision).append("-");
        if (identifierAuthority[0] != (byte) 0 || identifierAuthority[1] != (byte) 0) {
            b.append("0x");
            b.append(Hex.toHexString(identifierAuthority));
        } else {
            long shift = 0;
            long id = 0;
            for (int i = identifierAuthority.length-1; i > 1; i--) {
                id += (identifierAuthority[i] & 0xFFL) << shift;
                shift += 8;
            }
            b.append(id);
        }
        for (long part : getSubAuthority())
            b.append("-").append(part & 0xFFFFFFFFL);
        return b.toString();
    }
}
