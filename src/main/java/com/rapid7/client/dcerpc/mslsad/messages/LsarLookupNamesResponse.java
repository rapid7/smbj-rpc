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
import java.io.IOException;

public class LsarLookupNamesResponse extends RequestResponse {
    private LookupNamesInfo lookupNamesInfo;
    private int returnValue;

    @Override
    public void unmarshal(final PacketInput packetIn)
            throws IOException {
        lookupNamesInfo = new LookupNamesInfo();
        readDomainList(packetIn);
        readSIDs(packetIn);
        packetIn.readInt(); //MappedCount
        returnValue = packetIn.readInt();

    }

    public LookupNamesInfo getLookupNamesInfo(){
        return lookupNamesInfo;
    }

    private void readDomainList(final PacketInput packetIn)
        throws IOException {
        int refId = packetIn.readInt();
        int entries = packetIn.readInt();
        int maxEntries = packetIn.readInt();

        for (int i =0 ; i < entries; i++){
            readDomainPointer(packetIn);
        }
    }

    private void readDomainPointer(final PacketInput packetIn)
            throws IOException {
        int numDomains = readArrayPointer(packetIn);
        for (int i =0 ; i < numDomains; i++){
            readDomain(packetIn);
        }
    }

    private void readDomain(final PacketInput packetIn)
            throws IOException {

    }

    private void readSIDs(final PacketInput packetIn)
            throws IOException {
        int entries = packetIn.readInt();
        readSIDPointer(packetIn);

    }

    private void readSIDPointer(final PacketInput packetIn)
            throws IOException {
        int numSIDs = readArrayPointer(packetIn);
        for (int i =0 ; i < numSIDs; i++){
            readSID(packetIn);
        }
    }

    private void readSID(final PacketInput packetIn)
            throws IOException {
        int sidType = packetIn.readInt();
        int relativeId = packetIn.readInt();
        int domainIndex = packetIn.readInt();
    }

    private int readArrayPointer(final PacketInput packetIn)
        throws IOException {
        int refId = packetIn.readReferentID();
        return packetIn.readInt();
    }

}
