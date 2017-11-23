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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
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
    // <NDR: pointer[struct]> [out] PLSAPR_REFERENCED_DOMAIN_LIST* ReferencedDomains
    private LSAPRReferencedDomainList referencedDomains;
    // <NDR: struct> [in, out] PLSAPR_TRANSLATED_SIDS TranslatedSids
    private LSAPRTranslatedSIDs translatedSIDs;
    // <NDR: unsigned long> [in, out] unsigned long* MappedCount
    // Ignored

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
        // <NDR: pointer[struct]> [out] PLSAPR_REFERENCED_DOMAIN_LIST* ReferencedDomains
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0) { //LsaprReferencedDomainList is a pointer
            referencedDomains = new LSAPRReferencedDomainList();
            packetIn.readUnmarshallable(referencedDomains);
        }
        else {
            referencedDomains = null;
        }
        // <NDR: struct> [in, out] PLSAPR_TRANSLATED_SIDS TranslatedSids
        translatedSIDs = new LSAPRTranslatedSIDs();
        packetIn.readUnmarshallable(translatedSIDs);
        // <NDR: unsigned long> [in, out] unsigned long* MappedCount
        packetIn.align(Alignment.FOUR);
        packetIn.fullySkipBytes(4);
    }

    public LSAPRReferencedDomainList getReferencedDomains() {
        return referencedDomains;
    }

    public LSAPRTranslatedSIDs getTranslatedSIDs() {
        return translatedSIDs;
    }
}
