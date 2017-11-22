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
package com.rapid7.client.dcerpc.service.dto;

import java.util.Arrays;
import org.bouncycastle.util.encoders.Hex;

/**
 * Security Identifier (SID) as defined in https://msdn.microsoft.com/en-us/library/cc246018.aspx
 */
public class SID {
    final private byte revision;
    final private char subAuthorityCount;
    final private byte[] identifierAuthority;
    final private long[] subAuthority;

    public SID(byte revision, byte[] identifierAuthority, long[] subAuthority) {
        this.revision = revision;
        this.identifierAuthority = identifierAuthority;
        this.subAuthorityCount = (char) subAuthority.length;
        this.subAuthority = subAuthority;
    }

    /**
     *
     * @param rid The relative ID to append to subauthorities
     * @return A new child SID object with this relativeID
     */
    public SID resolveRelativeId(long rid){
        long[] newSubAuth = new long[subAuthority.length + 1];
        System.arraycopy(subAuthority, 0, newSubAuth, 0, subAuthority.length);
        newSubAuth[subAuthority.length] = rid;
        return new SID(this.revision, identifierAuthority, newSubAuth);
    }

    public byte getRevision() {
        return revision;
    }

    public char getSubAuthorityCount() {
        return subAuthorityCount;
    }

    public byte[] getIdentifierAuthority() {
        return identifierAuthority;
    }

    public long[] getSubAuthority() {
        return subAuthority;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("S-");
        b.append(revision & 0xFF).append("-");

        if (identifierAuthority == null || identifierAuthority.length < 2) {
            b.append("null");
        } else {
            if (identifierAuthority[0] != (byte) 0 || identifierAuthority[1] != (byte) 0) {
                b.append("0x");
                b.append(Hex.toHexString(identifierAuthority));
            } else {
                long shift = 0;
                long id = 0;
                for (int i = identifierAuthority.length - 1; i > 1; i--) {
                    id += (identifierAuthority[i] & 0xFFL) << shift;
                    shift += 8;
                }
                b.append(id);
            }
        }
        if (subAuthority == null) {
            b.append("-null");
        } else {
            for (int i = 0; i < subAuthority.length; i++)
                b.append("-").append(subAuthority[i] & 0xFFFFFFFFL);
        }

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SID sid = (SID) o;

        if (revision != sid.revision) return false;
        if (subAuthorityCount != sid.subAuthorityCount) return false;
        if (!Arrays.equals(identifierAuthority, sid.identifierAuthority)) return false;
        return Arrays.equals(subAuthority, sid.subAuthority);
    }

    @Override
    public int hashCode() {
        int result = (int) revision;
        result = 31 * result + (int) subAuthorityCount;
        result = 31 * result + Arrays.hashCode(identifierAuthority);
        result = 31 * result + Arrays.hashCode(subAuthority);
        return result;
    }
}
