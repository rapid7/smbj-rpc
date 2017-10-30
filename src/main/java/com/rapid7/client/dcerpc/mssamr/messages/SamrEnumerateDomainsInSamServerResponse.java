/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DomainInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SamArray;

public class SamrEnumerateDomainsInSamServerResponse extends RequestResponse {
    private int resumeHandle;
    private SamArray domains;
    private int numEntries;

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        resumeHandle = packetIn.readInt();
        domains = packetIn.unmarshallObject(new SamArray(DomainInfo.class));
        numEntries = packetIn.readInt();
    }

    public List<DomainInfo> getDomainList() {
        return domains.getArray().getList();
    }

    public int getNumEntries() {
        return numEntries;
    }

    public int getResumeHandle() {
        return resumeHandle;
    }
}
