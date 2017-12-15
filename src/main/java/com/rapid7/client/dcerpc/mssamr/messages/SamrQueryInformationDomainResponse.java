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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mslsad.objects.DomainInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLockoutInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainLogoffInfo;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainPasswordInfo;

public abstract class SamrQueryInformationDomainResponse<T extends Unmarshallable> extends RequestResponse {
    private T domainInformation;

    public T getDomainInformation() {
        return domainInformation;
    }

    public abstract DomainInformationClass getDomainInformationClass();

    abstract T createDomainInformation();

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        if (packetIn.readReferentID() != 0) {
            final int infoLevel = packetIn.readShort();
            if (infoLevel != getDomainInformationClass().getInfoLevel()) {
                throw new IllegalArgumentException(
                    String.format("Incoming DOMAIN_INFORMATION_CLASS %d does not match expected: %d", infoLevel,
                            getDomainInformationClass().getInfoLevel()));
            }
            this.domainInformation = createDomainInformation();
            packetIn.readUnmarshallable(this.domainInformation);
        } else {
            this.domainInformation = null;
        }
    }

    public static class DomainPasswordInformation extends SamrQueryInformationDomainResponse<SAMPRDomainPasswordInfo> {
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_PASSWORD_INFORMATION;
        }

        @Override
        SAMPRDomainPasswordInfo createDomainInformation() {
            return new SAMPRDomainPasswordInfo();
        }
    }

    public static class DomainLogOffInformation extends SamrQueryInformationDomainResponse<SAMPRDomainLogoffInfo> {
        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_LOGOFF_INFORMATION;
        }

        @Override
        SAMPRDomainLogoffInfo createDomainInformation() {
            return new SAMPRDomainLogoffInfo();
        }
    }

    public static class DomainLockoutInformation extends SamrQueryInformationDomainResponse<SAMPRDomainLockoutInfo> {

        @Override
        public DomainInformationClass getDomainInformationClass() {
            return DomainInformationClass.DOMAIN_LOCKOUT_INFORMATION;
        }

        @Override
        SAMPRDomainLockoutInfo createDomainInformation() {
            return new SAMPRDomainLockoutInfo();
        }
    }
}
