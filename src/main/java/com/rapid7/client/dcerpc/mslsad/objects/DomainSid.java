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

/**
  Using MSDN reference: https://msdn.microsoft.com/en-us/library/cc230364.aspx
 */

package com.rapid7.client.dcerpc.mslsad.objects;


public class DomainSid
{
    private short revision;
    private byte[] identifierAuthority;
    private int[] subAuthorities;

    public DomainSid(short revision, byte[] identifierAuthority, int[] subAuthorities)
    {
        this.revision = revision;
        this.identifierAuthority = identifierAuthority;
        this.subAuthorities = subAuthorities;
    }

    public short getRevision()
    {
        return revision;
    }

    public byte[] getIdentifierAuthority()
    {
        return identifierAuthority;
    }

    public int[] getSubAuthorities()
    {
        return subAuthorities;
    }

    public String toString()
    {
        // SIDs are in S-revnum-idauth-subauth1-subauth2-...-subauthn format
        StringBuilder buf = new StringBuilder(20 * (subAuthorities.length + 1));
        buf.append("S-");
        buf.append(revision);
        buf.append('-');

        long authID = 0;
        for (int i = 0; i < identifierAuthority.length; i++)
        {
            authID = (authID << 8) + identifierAuthority[i];
        }
        buf.append(authID);

        for (int i = 0; i < subAuthorities.length; i++)
        {
            buf.append('-');
            buf.append(Long.toString(subAuthorities[i] & 0xFFFFFFFFL));
        }

        return buf.toString();
    }
}
