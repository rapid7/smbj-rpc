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

package com.rapid7.client.dcerpc.mslsad.objects;

import java.util.ArrayList;
import java.util.List;

/**
 Using MSDN Reference: https://msdn.microsoft.com/en-ca/library/cc234495.aspx
 */
public class LookupNamesInfo {
    private List<LsaDomainInfo> domainList = new ArrayList<LsaDomainInfo>();
    private List<LsaTranslatedSid> translatedSids  = new ArrayList<LsaTranslatedSid>();
    private int count;

    public LookupNamesInfo() {
    }

    public void addDomain(final LsaDomainInfo domain){
        domainList.add(domain);
    }

    public void addTranslatedSid(final LsaTranslatedSid sid){
        translatedSids.add(sid);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LsaDomainInfo> getDomainList()
    {
        return domainList;
    }

    public List<LsaTranslatedSid> getTranslatedSids()
    {
        return translatedSids;
    }

    public int getCount()
    {
        return count;
    }
}
