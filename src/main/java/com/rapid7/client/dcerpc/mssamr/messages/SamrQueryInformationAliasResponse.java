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

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.AliasInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRAliasGeneralInformation;

public abstract class SamrQueryInformationAliasResponse<T extends Unmarshallable> extends RequestResponse {
    private T aliasInformation;

    public T getAliasInformation() {
        return aliasInformation;
    }

    public abstract AliasInformationClass getAliasInformationClass();

    abstract T createAliasInformation();

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        if(packetIn.readReferentID() != 0) {
            final int infoLevel = packetIn.readUnsignedShort();
            if (infoLevel != getAliasInformationClass().getInfoLevel()) {
                throw new UnmarshalException(String.format(
                        "Incoming ALIAS_INFORMATION_CLASS %d does not match expected: %d",
                        infoLevel, getAliasInformationClass().getInfoLevel()));
            }
            this.aliasInformation = createAliasInformation();
            packetIn.readUnmarshallable(this.aliasInformation);
        } else {
            this.aliasInformation = null;
        }
    }

    public static class AliasGeneralInformation extends SamrQueryInformationAliasResponse<SAMPRAliasGeneralInformation> {
        @Override
        public AliasInformationClass getAliasInformationClass() {
            return AliasInformationClass.ALIAS_GENERALINFORMATION;
        }

        @Override
        SAMPRAliasGeneralInformation createAliasInformation() {
            return new SAMPRAliasGeneralInformation();
        }
    }
}
