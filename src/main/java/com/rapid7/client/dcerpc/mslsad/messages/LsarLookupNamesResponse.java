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
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRReferencedDomainList;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedSIDs;
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
    private LSAPRReferencedDomainList lsaprReferencedDomainList;
    private LSAPRTranslatedSIDs lsaprTranslatedSIDs;

    @Override
    public void unmarshalResponse(final PacketInput packetIn)
            throws IOException {

        //Top level parameters
        //1. Referenced Domain List pointer
        if (packetIn.readReferentID() != 0) //LsaprReferencedDomainList is a pointer
        {
            lsaprReferencedDomainList = new LSAPRReferencedDomainList();
            packetIn.readUnmarshallable(lsaprReferencedDomainList);
        } else { lsaprReferencedDomainList = null; }

        //2. Translated Sids
        lsaprTranslatedSIDs = new LSAPRTranslatedSIDs();
        packetIn.readUnmarshallable(lsaprTranslatedSIDs);

        //3. Mapped Count
        packetIn.readInt();
    }

    public LSAPRReferencedDomainList getLsaprReferencedDomainList() {
        return lsaprReferencedDomainList;
    }

    public void setLsaprReferencedDomainList(LSAPRReferencedDomainList lsaprReferencedDomainList) {
        this.lsaprReferencedDomainList = lsaprReferencedDomainList;
    }

    public LSAPRTranslatedSIDs getLsaprTranslatedSIDs() {
        return lsaprTranslatedSIDs;
    }

    public void setLsaprTranslatedSIDs(LSAPRTranslatedSIDs lsaprTranslatedSIDs) {
        this.lsaprTranslatedSIDs = lsaprTranslatedSIDs;
    }
}
