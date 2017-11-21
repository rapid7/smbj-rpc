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

import com.hierynomus.protocol.commons.ByteArrayUtils;
import java.io.IOException;
import java.rmi.MarshalException;
import java.util.Arrays;
import java.util.Objects;
import org.bouncycastle.util.encoders.Hex;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <b>Alignment: 4</b> (Max[4, 4])<pre>
 *      unsigned char Revision;: 1
 *      unsigned char SubAuthorityCount;: 1
 *      RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;: 1
 *      [size_is(SubAuthorityCount)] unsigned long SubAuthority[];: 4 (Max[4, 4])</pre>
 * <a href="https://msdn.microsoft.com/en-us/library/cc230364.aspx">RPC_SID</a>:
 * <blockquote><pre>
 * The RPC_SID structure is an IDL representation of the SID type (as specified in  section 2.4.2) for use by RPC-based protocols.
 *      typedef struct _RPC_SID {
 *          unsigned char Revision;
 *          unsigned char SubAuthorityCount;
 *          RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
 *          [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
 *      } RPC_SID,
 *      *PRPC_SID,
 *      *PSID;
 *  Revision: An 8-bit unsigned integer that specifies the revision level of the SID. This value MUST be set to 0x01.
 *  SubAuthorityCount: An 8-bit unsigned integer that specifies the number of elements in the SubAuthority array. The maximum number of elements allowed is 15.
 *  IdentifierAuthority: An RPC_SID_IDENTIFIER_AUTHORITY structure that indicates the authority under which the SID was created. It describes the entity that created the SID. The Identifier Authority value {0,0,0,0,0,5} denotes SIDs created by the NT SID authority.
 *  SubAuthority: A variable length array of unsigned 32-bit integers that uniquely identifies a principal relative to the IdentifierAuthority. Its length is determined by SubAuthorityCount.
 * </pre></blockquote>
 */
public class RPCSID implements Unmarshallable, Marshallable {
    // <NDR: unsigned char> unsigned char Revision;
    private char revision;
    // <NDR: unsigned char> unsigned char SubAuthorityCount
    private char subAuthorityCount;
    // <NDR: fixed array> RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
   /*
    * typedef struct _RPC_SID_IDENTIFIER_AUTHORITY {
    *   byte Value[6];
    * } RPC_SID_IDENTIFIER_AUTHORITY;
    */
    private byte[] identifierAuthority;
    // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
    private long[] subAuthority;

    public char getRevision() {
        return revision;
    }

    public void setRevision(char revision) {
        this.revision = revision;
    }

    public char getSubAuthorityCount() {
        return subAuthorityCount;
    }

    public byte[] getIdentifierAuthority() {
        return identifierAuthority;
    }

    public void setIdentifierAuthority(byte[] identifierAuthority) {
        this.identifierAuthority = identifierAuthority;
    }

    public long[] getSubAuthority() {
        return subAuthority;
    }

    public void setSubAuthority(long[] subAuthority) {
        this.subAuthority = subAuthority;
        this.subAuthorityCount = (char) subAuthority.length;
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
        // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
        out.align(Alignment.FOUR);
        out.writeInt(this.subAuthority.length);
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        if (this.subAuthorityCount != this.subAuthority.length) {
            throw new MarshalException(String.format("SubAuthorityCount (%d) != SubAuthority[] length (%d)",
                    (int) this.subAuthorityCount, this.subAuthority.length));
        }
        // Structure alignment
        out.align(Alignment.FOUR);
        // <NDR: unsigned char> unsigned char Revision;
        // Alignment: 1 - Already aligned
        out.writeByte(this.revision);
        // <NDR: unsigned char> unsigned char SubAuthorityCount;
        // Alignment: 1 - Already aligned
        out.writeByte(this.subAuthorityCount);
        // <NDR: fixed array> RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
        // Alignment: 1 - Already aligned
        out.write(this.identifierAuthority, 0, 6);
        // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
        // Alignment: 4 - Already aligned, we wrote 8 bytes above
        for (long subAuthority : this.subAuthority) {
            // <NDR: unsigned long>
            // Alignment: 4 - Already aligned
            out.writeInt((int) subAuthority);
        }
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        // No deferrals
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
        in.align(Alignment.FOUR);
        in.fullySkipBytes(4); // We don't care about MaximumCount
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure alignment
        in.align(Alignment.FOUR);
        // <NDR: unsigned char> unsigned char Revision;
        // Alignment: 1 - Already aligned
        this.revision = in.readUnsignedByte();
        // <NDR: unsigned char> unsigned char SubAuthorityCount;
        // Alignment: 1 - Already aligned
        this.subAuthorityCount = in.readUnsignedByte();
        // <NDR: fixed array> RPC_SID_IDENTIFIER_AUTHORITY IdentifierAuthority;
        // Alignment: 1 - Already aligned
        this.identifierAuthority = new byte[6];
        in.readRawBytes(this.identifierAuthority);
        // <NDR: conformant array> [size_is(SubAuthorityCount)] unsigned long SubAuthority[];
        // Alignment: 4 - Already aligned, we read 8 bytes above
        this.subAuthority = new long[this.subAuthorityCount];
        for (int i = 0; i < this.subAuthority.length; i++) {
            // <NDR: unsigned long>
            // Alignment: 4 - Already aligned
            this.subAuthority[i] = in.readUnsignedInt();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // No deferrals
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getRevision(), getSubAuthorityCount());
        result = (31 * result) + Arrays.hashCode(getIdentifierAuthority());
        result = (31 * result) + Arrays.hashCode(getSubAuthority());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof RPCSID)) {
            return false;
        }
        RPCSID other = (RPCSID) obj;
        return getRevision() == other.getRevision()
                && getSubAuthorityCount() == other.getSubAuthorityCount()
                && Arrays.equals(getIdentifierAuthority(), other.getIdentifierAuthority())
                && Arrays.equals(getSubAuthority(), other.getSubAuthority());
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
                for (int i = identifierAuthority.length-1; i > 1; i--) {
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


    /**
     * @param sidString SID string. Must not be {@code null}.
     * @return A {@link RPCSID} parsed from the provided string.
     * @throws MalformedSIDException The provided SID is malformed.
     */
    public static RPCSID fromString(String sidString) throws MalformedSIDException {
        String[] split = sidString.toUpperCase().trim().split("-");
        if (split.length < 3)
            throw new MalformedSIDException("Illegal SID format: " + sidString);

        if (!split[0].equals("S"))
            throw new MalformedSIDException("SID must start with S:" + sidString);

        try {
            char revision = (char) Integer.parseInt(split[1]);

            String identifierAuthorityString = split[2];
            byte[] identifierAuthority = new byte[6];

            long identifierAuthorityValue;
            if (identifierAuthorityString.startsWith("0X")) {
                String bytes = identifierAuthorityString.substring(2,identifierAuthorityString.length());
                identifierAuthority = Hex.decode(bytes);

            } else {
                identifierAuthorityValue = Long.parseLong(identifierAuthorityString);
                identifierAuthority[0] = (byte) ((identifierAuthorityValue >> 40) & 0xFF);
                identifierAuthority[1] = (byte) ((identifierAuthorityValue >> 32) & 0xFF);
                identifierAuthority[2] = (byte) ((identifierAuthorityValue >> 24) & 0xFF);
                identifierAuthority[3] = (byte) ((identifierAuthorityValue >> 16) & 0xFF);
                identifierAuthority[4] = (byte) ((identifierAuthorityValue >> 8) & 0xFF);
                identifierAuthority[5] = (byte) (identifierAuthorityValue & 0xFF);
            }


            long[] subAuthorities = new long[split.length - 3];
            for (int i = 0; i < subAuthorities.length; i++) {
                subAuthorities[i] = Long.parseLong(split[i + 3]);
            }
            RPCSID rpcSid = new RPCSID();
            rpcSid.setRevision(revision);
            rpcSid.setIdentifierAuthority(identifierAuthority);
            rpcSid.setSubAuthority(subAuthorities);
            return rpcSid;
        } catch (NumberFormatException e) {
            throw new MalformedSIDException("Unable to parse SID token: " + e.getMessage());
        }
    }
}
