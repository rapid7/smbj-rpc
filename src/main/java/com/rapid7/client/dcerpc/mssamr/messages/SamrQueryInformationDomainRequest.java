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
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;

//https://msdn.microsoft.com/en-us/library/cc245779.aspx
public class SamrQueryInformationDomainRequest extends RequestCall<SamrQueryInformationDomainResponse> {
    public final static short OP_NUM = 8;

    public static final short DOMAIN_PASSWORD_INFORMATION = 1;
    public static final short DOMAIN_LOGOFF_INFORMATION = 3;

    private final DomainHandle handle;
    private final short domainInformationClass;

    public SamrQueryInformationDomainRequest(final DomainHandle handle, final short domainInformationClass) {
        super(OP_NUM);
        this.handle = handle;
        this.domainInformationClass = domainInformationClass;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(handle.getBytes());
        packetOut.writeShort(domainInformationClass);
    }

    @Override
    public SamrQueryInformationDomainResponse getResponseObject() {
        return new SamrQueryInformationDomainResponse();
    }
}
