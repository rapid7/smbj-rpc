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
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DisplayInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayGroupBuffer;

/**
 * The {@link SamrEnumerateResponse} implementation for request {@link SamrQueryDisplayInformation2Request}.
 */
public abstract class SamrQueryDisplayInformation2Response<T extends Unmarshallable> extends RequestResponse {

    // <NDR: unsigned long> [out] unsigned long* TotalAvailable
    private int totalAvailable;
    // <NDR: unsigned long> [out] unsigned long* TotalReturned
    private int totalReturnedBytes;
    // <NDR: union> [out, switch_is(DisplayInformationClass)] PSAMPR_DISPLAY_INFO_BUFFER Buffer
    private T displayInformation;

    public abstract DisplayInformationClass getDisplayInformationClass();

    abstract T createDisplayInformation();

    public T getDisplayInformation() {
        return displayInformation;
    }

    public int getTotalAvailable() {
        return totalAvailable;
    }

    public int getTotalReturned() {
        return totalReturnedBytes;
    }

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        // <NDR: unsigned long> [out] unsigned long* TotalAvailable
        this.totalAvailable = packetIn.readInt();
        // <NDR: unsigned long> [out] unsigned long* TotalReturned
        // Alignment: 4 - Already aligned
        this.totalReturnedBytes = packetIn.readInt();
        // switch_is(DisplayInformationClass)
        // Alignment: 2 - Already aligned
        final int infoLevel = packetIn.readUnsignedShort();
        if (infoLevel != getDisplayInformationClass().getInfoLevel()) {
            throw new UnmarshalException(String.format(
                    "Incoming DISPLAY_INFORMATION_CLASS %d does not match expected: %d",
                    infoLevel, getDisplayInformationClass().getInfoLevel()));
        }
        this.displayInformation = createDisplayInformation();
        packetIn.readUnmarshallable(this.displayInformation);
    }

    public static class DomainDisplayGroup extends SamrQueryDisplayInformation2Response<SAMPRDomainDisplayGroupBuffer> {
        @Override
        public DisplayInformationClass getDisplayInformationClass() {
            return DisplayInformationClass.DomainDisplayGroup;
        }

        @Override
        SAMPRDomainDisplayGroupBuffer createDisplayInformation() {
            return new SAMPRDomainDisplayGroupBuffer();
        }
    }
}
