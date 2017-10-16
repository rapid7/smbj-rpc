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
package com.rapid7.client.dcerpc.mslsad.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mslsad.objects.LookupNamesInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LsaDomainInfo;
import com.rapid7.client.dcerpc.mslsad.objects.LsaTranslatedSid;
import com.hierynomus.msdtyp.SID;
import java.io.IOException;

/**
 * Local Security Authority, lsa_LookupNames
 *   Operation: lsa_LookupNames (14)
 *   Pointer to Domains (lsa_RefDomainList)
 *     Domains
 *       Cound: 1
 *       Pointer to Domains (lsa_DomainInfo)
 *          Name (String)
 *          Sid (dom_sid2)
 *            Revision:
 *            Authority:
 *            Subauthorities:
 *       Max Count: 32
 *   Pointer to Sids (lsa_TransSidArray)
 *     Sids
 *       Count: 1
 *       Pointer to Sids (lsa_TranslatedSid)
 *         Sid Type: SID_NAME_USER (1)
 *         Rid: 500
 *         Sid Index: 0
 *       Max Count: 1
 *   Count: 1
 *   Windows Error: WERR_OK (0x00000000)
 *
 */

public class LsarLookupNamesResponse extends RequestResponse {
    private LookupNamesInfo lookupNamesInfo;
    private int returnValue;

    @Override
    public void unmarshal(final PacketInput packetIn)
            throws IOException {
        lookupNamesInfo = new LookupNamesInfo();
        readDomainList(packetIn);
        readSIDs(packetIn);
        lookupNamesInfo.setCount(packetIn.readInt()); //MappedCount
        returnValue = packetIn.readInt();

    }

    public LookupNamesInfo getLookupNamesInfo(){
        return lookupNamesInfo;
    }

    private void readDomainList(final PacketInput packetIn)
        throws IOException
    {
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
            readNameAndSid(packetIn);
        }
    }

    private void readNameAndSid(final PacketInput packetIn)
        throws IOException {
        LsaDomainInfo domain = new LsaDomainInfo();
        packetIn.readShort(); //length
        packetIn.readShort(); //size
        packetIn.readReferentID(); //name string referent
        packetIn.readReferentID(); //sid referent
        String name = packetIn.readString(true);
        domain.setName(name);

        packetIn.readInt(); //sidCount
        byte revision = packetIn.readByte();
        byte subAuthorityCount = packetIn.readByte();
        byte[] identifierAuthority = packetIn.readBytes(6);
        long[] subAuthorities = new long[subAuthorityCount];
        for (int index = 0; index < subAuthorityCount; index ++ ){
            subAuthorities[index] = packetIn.readInt();
        }
        SID sid = new SID(revision, identifierAuthority, subAuthorities);
        domain.setSid(sid);
        lookupNamesInfo.addDomain(domain);
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
        int sidType = packetIn.readInt();
        int relativeId = packetIn.readInt();
        int domainIndex = packetIn.readInt();
        LsaTranslatedSid translatedSid = new LsaTranslatedSid(sidType, relativeId, domainIndex);
        lookupNamesInfo.addTranslatedSid(translatedSid);
    }

}
