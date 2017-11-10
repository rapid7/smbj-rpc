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
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mslsad.objects.DOMAIN_INFORMATION_CLASS;
import com.rapid7.client.dcerpc.mslsad.objects.SAMPR_DOMAIN_LOGOFF_INFO;
import com.rapid7.client.dcerpc.mslsad.objects.SAMPR_DOMAIN_PASSWORD_INFO;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;

//https://msdn.microsoft.com/en-us/library/cc245779.aspx
public abstract class SamrQueryInformationDomainRequest<T extends Unmarshallable>
        extends RequestCall<SamrQueryInformationDomainResponse<T>> {
    public final static short OP_NUM = 8;
    private final DomainHandle handle;
    private final DOMAIN_INFORMATION_CLASS infoLevel;

    public SamrQueryInformationDomainRequest(final DomainHandle handle, final DOMAIN_INFORMATION_CLASS infoLevel) {
        super(OP_NUM);
        this.handle = handle;
        this.infoLevel = infoLevel;
    }

    abstract T newDomainInformation();

    @Override
    public SamrQueryInformationDomainResponse<T> getResponseObject() {
        return new SamrQueryInformationDomainResponse<T>(newDomainInformation(), infoLevel);

    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(handle.getBytes());
        packetOut.writeShort(infoLevel.getInfoLevel());
    }

    public static class DomainPasswordInformation extends SamrQueryInformationDomainRequest<SAMPR_DOMAIN_PASSWORD_INFO> {
        public DomainPasswordInformation(final DomainHandle handle) {
            super(handle, DOMAIN_INFORMATION_CLASS.DOMAIN_PASSWORD_INFORMATION);
        }

        @Override
        SAMPR_DOMAIN_PASSWORD_INFO newDomainInformation() {
            return new SAMPR_DOMAIN_PASSWORD_INFO();
        }
    }

    public static class DomainLogOffInformation extends SamrQueryInformationDomainRequest<SAMPR_DOMAIN_LOGOFF_INFO> {
        public DomainLogOffInformation(final DomainHandle handle) {
            super(handle, DOMAIN_INFORMATION_CLASS.DOMAIN_LOGOFF_INFORMATION);
        }

        @Override
        SAMPR_DOMAIN_LOGOFF_INFO newDomainInformation() {
            return new SAMPR_DOMAIN_LOGOFF_INFO();
        }
    }

}
