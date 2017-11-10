/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.objects.EnumeratedDomains;

/**
 * <pre>
 * long SamrEnumerateDomainsInSamServer(
 *   [in] SAMPR_HANDLE ServerHandle,
 *   [in, out] unsigned long* EnumerationContext,
 *   [out] PSAMPR_ENUMERATION_BUFFER* Buffer,
 *   [in] unsigned long PreferedMaximumLength,
 *   [out] unsigned long* CountReturned
 * );
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-ca/library/cc245755.aspx">
 *       https://msdn.microsoft.com/en-ca/library/cc245755.aspx</a>
 */
public class SamrEnumerateDomainsInSamServerResponse extends RequestResponse {
    private int resumeHandle;
    private EnumeratedDomains domains;
    private int numEntries;
    private int returnCode;

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        // <NDR: unsigned long> [in, out] unsigned long* EnumerationContext,
        // Alignment: 4 - Already aligned
        resumeHandle = packetIn.readInt();
        // <NDR: pointer> [out] PSAMPR_ENUMERATION_BUFFER* Buffer,
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0) {
            domains = new EnumeratedDomains();
            packetIn.readUnmarshallable(domains);
        }
        // <NDR: unsigned long> [out] unsigned long* CountReturned
        packetIn.align(Alignment.FOUR);
        numEntries = packetIn.readInt();
        returnCode = packetIn.readInt();
    }

    public List<DomainInfo> getDomainList() {
        return domains.getEntries();
    }

    public int getNumEntries() {
        return numEntries;
    }

    public int getResumeHandle() {
        return resumeHandle;
    }

    public int getReturnValue() {
        return returnCode;
    }
}
