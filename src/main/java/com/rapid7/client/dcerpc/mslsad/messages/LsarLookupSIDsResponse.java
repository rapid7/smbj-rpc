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
    private int returnValue;
    private int mappedCount;
    private LSAPRReferencedDomainList lsaprReferencedDomainList;
    private LSAPRTranslatedNames lsaprTranslatedNames;

    @Override
    public void unmarshalResponse(final PacketInput packetIn)
            throws IOException {

        if (packetIn.readReferentID() != 0) {
            lsaprReferencedDomainList = new LSAPRReferencedDomainList();
            packetIn.readUnmarshallable(lsaprReferencedDomainList);
        }

        lsaprTranslatedNames = new LSAPRTranslatedNames();
        packetIn.readUnmarshallable(lsaprTranslatedNames);
        //Mapped Count
        packetIn.align(Alignment.FOUR);
        mappedCount = packetIn.readInt();
    }

    public int getReturnValue() {
        return returnValue;
    }

    public int getMappedCount()
    {
        return mappedCount;
    }

    public LSAPRReferencedDomainList getLsaprReferencedDomainList()
    {
        return lsaprReferencedDomainList;
    }

    public LSAPRTranslatedNames getLsaprTranslatedNames()
    {
        return lsaprTranslatedNames;
    }
}
