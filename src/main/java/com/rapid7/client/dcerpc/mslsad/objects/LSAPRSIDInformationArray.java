/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mslsad.objects;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
   NDR Conformant Array
 */

public class LSAPRSIDInformationArray implements Marshallable
{

    private List<LSAPRSIDInformation> lsaprsidInformations;

    public LSAPRSIDInformationArray(){
        lsaprsidInformations = new ArrayList<>();
    }

    public void addLSAPRSIDInformation(LSAPRSIDInformation SIDInfo){
        lsaprsidInformations.add(SIDInfo);
    }

    @Override public void marshalPreamble(PacketOutput out)
        throws IOException
    {
        out.writeInt(lsaprsidInformations.size());
    }

    @Override public void marshalEntity(PacketOutput out)
        throws IOException
    {
        for (LSAPRSIDInformation lsaprsidInformation: lsaprsidInformations) {
            if (lsaprsidInformation != null) out.writeReferentID();
            else out.writeNull();
        }
    }

    @Override public void marshalDeferrals(PacketOutput out)
        throws IOException
    {
        for (LSAPRSIDInformation lsaprsidInformation: lsaprsidInformations) {
            out.writeMarshallable(lsaprsidInformation);
        }
    }
}
