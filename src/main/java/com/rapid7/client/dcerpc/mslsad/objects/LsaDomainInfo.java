package com.rapid7.client.dcerpc.mslsad.objects;

public class LsaDomainInfo
{
    private String name;
    private DomainSid sid;

    public LsaDomainInfo() {}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public DomainSid getSid()
    {
        return sid;
    }

    public void setSid(DomainSid sid)
    {
        this.sid = sid;
    }
}
