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
