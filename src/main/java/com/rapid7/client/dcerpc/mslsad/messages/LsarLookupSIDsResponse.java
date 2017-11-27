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
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedNames;
import java.io.IOException;

/**
 * See request documentation
 */

public class LsarLookupSIDsResponse extends RequestResponse {
    // <NDR: pointer[struct]> [out] PLSAPR_REFERENCED_DOMAIN_LIST* ReferencedDomains
    private LSAPRReferencedDomainList referencedDomains;
    // <NDR: struct> [in, out] PLSAPR_TRANSLATED_NAMES TranslatedNames
    private LSAPRTranslatedNames translatedNames;
    // <NDR: unsigned long> [in, out] unsigned long* MappedCount
    private int mappedCount;

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
        // <NDR: pointer[struct]> [out] PLSAPR_REFERENCED_DOMAIN_LIST* ReferencedDomains
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0) {
            referencedDomains = new LSAPRReferencedDomainList();
            packetIn.readUnmarshallable(referencedDomains);
        } else {
            referencedDomains = null;
        }
        // <NDR: struct> [in, out] PLSAPR_TRANSLATED_NAMES TranslatedNames
        translatedNames = new LSAPRTranslatedNames();
        packetIn.readUnmarshallable(translatedNames);
        // <NDR: unsigned long> [in, out] unsigned long* MappedCount
        packetIn.align(Alignment.FOUR);
        mappedCount = packetIn.readInt();
    }

    public LSAPRReferencedDomainList getReferencedDomains() {
        return referencedDomains;
    }

    public LSAPRTranslatedNames getTranslatedNames() {
        return translatedNames;
    }

    public int getMappedCount() {
        return mappedCount;
    }
}
