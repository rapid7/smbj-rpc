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

import java.util.Arrays;
import java.util.Objects;
import org.bouncycastle.util.encoders.Hex;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc230371.aspx">SID</a>
 * <br>
 * <blockquote><pre>A security identifier (SID) uniquely identifies a security principal. Each security principal has a unique SID that is issued by a security agent. The agent can be a Windows local system or domain. The agent generates the SID when the security principal is created. The SID can be represented as a character string or as a structure. When represented as strings, for example in documentation or logs, SIDs are expressed as follows:
 *
 *      S-1-IdentifierAuthority-SubAuthority1-SubAuthority2-...-SubAuthorityn
 *
 *  The top-level issuer is the authority. Each issuer specifies, in an implementation-specific manner, how many integers identify the next issuer.
 *  A newly created account store is assigned a 96-bit identifier (a cryptographic strength (pseudo) random number).
 *  A newly created security principal in an account store is assigned a 32-bit identifier that is unique within the store.
 *  The last item in the series of SubAuthority values is known as the relative identifier (RID). Differences in the RID are what distinguish the different SIDs generated within a domain.
 *  Consumers of SIDs SHOULD NOT rely on anything more than that the SID has the appropriate structure.</pre></blockquote>
 */
public class SID {

    private final byte revision;
    private final byte[] identifierAuthority;
    private final long[] subAuthorities;
    private int type;

    public SID(final byte revision, final byte[] identifierAuthority, final long[] subAuthorities) {
        if (identifierAuthority == null) {
            throw new IllegalArgumentException("Expecting non-null identifierAuthority");
        }
        else if (identifierAuthority.length != 6) {
            throw new IllegalArgumentException(String.format(
                    "Expected 6 entries in identifierAuthority, got: %d", identifierAuthority.length));
        }
        if (subAuthorities == null) {
            throw new IllegalArgumentException("Expecting non-null subAuthorities");
        }
        this.revision = revision;
        this.identifierAuthority = identifierAuthority;
        this.subAuthorities = subAuthorities;
    }

    public byte getRevision() {
        return revision;
    }

    public byte[] getIdentifierAuthority() {
        return identifierAuthority;
    }

    /**
     * @return All subauthorities. At least one
     */
    public long[] getSubAuthorities() {
        return subAuthorities;
    }

    /**
     * @return The last subauthority, which is the relative identifier (RID).
     *         May be {@code null} if no sub-authority exists.
     *
     * The RID is typically only useful in situations where this SID represents an issuer (i.e. domain).
     */
    public Long getRelativeID() {
        if (subAuthorities.length == 0)
            return null;
        return subAuthorities[subAuthorities.length-1];
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * @param relativeID The relative identifier (RID) used to construct a new {@link SID} object.
     * @return A new {@link SID} object as a clone of this object, with the provided relativeID appended to the subauthorities.
     * Use of this method is typically only useful in situations where this SID represents an issuer (i.e. domain).
     */
    public SID resolveRelativeID(final long relativeID) {
        final byte[] identifierAuthority = Arrays.copyOf(this.identifierAuthority, this.identifierAuthority.length);
        final long[] subAuthorities = Arrays.copyOf(this.subAuthorities, this.subAuthorities.length+1);
        subAuthorities[subAuthorities.length-1] = relativeID;
        return new SID(this.revision, identifierAuthority, subAuthorities);
    }

    @Override
    public int hashCode() {
        int ret = Objects.hashCode(getRevision());
        ret = (31 * ret) + Arrays.hashCode(getIdentifierAuthority());
        ret = (31 * ret) + Arrays.hashCode(getSubAuthorities());
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof SID)) {
            return false;
        }
        final SID other = (SID) obj;
        return Objects.equals(getRevision(), other.getRevision())
                && Arrays.equals(getIdentifierAuthority(), other.getIdentifierAuthority())
                && Arrays.equals(getSubAuthorities(), other.getSubAuthorities());
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder("S-");
        b.append(revision & 0xFF).append("-");
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
        for (long part : getSubAuthorities())
            b.append("-").append(part & 0xFFFFFFFFL);
        return b.toString();
    }

    /**
     * Thrown in the event that we failed to parse the SID from a given String.
     */
    public static class MalformedSIDStringException extends IllegalArgumentException {
        MalformedSIDStringException(String reason, String sidStr) {
            super(String.format("%s: %s", reason, sidStr));
        }
    }

    /**
     * @param sidString SID string. Must not be {@code null}.
     * @return A {@link SID} parsed from the provided string.
     * @throws MalformedSIDStringException The provided SID is malformed.
     */
    public static SID fromString(String sidString) throws MalformedSIDStringException {
        final String[] split = sidString.toUpperCase().trim().split("-");
        if (split.length < 3)
            throw new MalformedSIDStringException("Illegal SID format", sidString);

        if (!split[0].equals("S"))
            throw new MalformedSIDStringException("SID must start with S", sidString);

        try {
            final byte revision = (byte) Integer.parseInt(split[1]);
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
            throw new MalformedSIDStringException("Unable to parse SID token: " + e.getMessage(), sidString);
        }
    }
}
