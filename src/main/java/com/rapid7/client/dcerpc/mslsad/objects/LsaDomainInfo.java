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
   Using MSDN Reference: https://msdn.microsoft.com/en-us/library/cc234452.aspx
 */
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
