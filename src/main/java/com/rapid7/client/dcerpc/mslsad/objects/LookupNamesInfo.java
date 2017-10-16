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

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.hierynomus.msdtyp.SID;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 Using MSDN Reference: https://msdn.microsoft.com/en-ca/library/cc234495.aspx
 */
public class LookupNamesInfo extends RequestResponse
{
    private List<LSADomainInfo> domainList = new ArrayList<>();
    private List<LSATranslatedSID> translatedSIDs  = new ArrayList<>();
    private int count;

    public LookupNamesInfo() {
    }

    public void addDomain(final LSADomainInfo domain){
        domainList.add(domain);
    }

    public void addTranslatedSID(final LSATranslatedSID SID){
        translatedSIDs.add(SID);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LSADomainInfo> getDomainList()
    {
        return domainList;
    }

    public List<LSATranslatedSID> getTranslatedSIDs()
    {
        return translatedSIDs;
    }

    public int getCount()
    {
        return count;
    }

    @Override
    public void unmarshal(PacketInput packetIn)
        throws IOException {
        readDomainList(packetIn);
        readSIDs(packetIn);
        setCount(packetIn.readInt()); //MappedCount
    }

    private void readDomainList(final PacketInput packetIn)
        throws IOException {
        packetIn.readReferentID(); //refId
        int entries = packetIn.readInt();

        for (int i = 0; i < entries; i++)
        {
            packetIn.readReferentID(); //referents for domains
        }
        packetIn.readInt(); //maxEntries

        readDomains(packetIn);
    }

    private void readDomains(final PacketInput packetIn)
        throws IOException {
        int maxCount = packetIn.readInt();
        for (int i = 0; i < maxCount; i++){
            readNameAndSID(packetIn);
        }
    }

    private void readNameAndSID(final PacketInput packetIn)
        throws IOException {
        LSADomainInfo domain = new LSADomainInfo();
        packetIn.readShort(); //length
        packetIn.readShort(); //size
        packetIn.readReferentID(); //name string referent
        packetIn.readReferentID(); //SID referent
        String name = packetIn.readString(true);
        domain.setName(name);

        packetIn.readInt(); //SIDCount
        byte revision = packetIn.readByte();
        byte subAuthorityCount = packetIn.readByte();
        byte[] identifierAuthority = packetIn.readBytes(6);
        long[] subAuthorities = new long[subAuthorityCount];
        for (int index = 0; index < subAuthorityCount; index ++ ){
            subAuthorities[index] = packetIn.readInt();
        }
        SID SID = new SID(revision, identifierAuthority, subAuthorities);
        domain.setSID(SID);
        addDomain(domain);
    }

    private void readSIDs(final PacketInput packetIn)
        throws IOException {
        int entries = packetIn.readInt();
        for (int count = 0; count < entries; count ++){
            packetIn.readReferentID();
        }
        packetIn.readInt(); //max count
        for (int count = 0; count < entries; count ++){
            readSID(packetIn);
        }
    }

    private void readSID(final PacketInput packetIn)
        throws IOException {
        int SIDType = packetIn.readInt();
        int relativeId = packetIn.readInt();
        int domainIndex = packetIn.readInt();
        LSATranslatedSID translatedSID = new LSATranslatedSID(SIDType, relativeId, domainIndex);
        addTranslatedSID(translatedSID);
    }
}
