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
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.objects.DisplayInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDisplayInformationBuffer;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRDomainDisplayGroupBuffer;

/**
 * The {@link SamrEnumerateResponse} implementation for request {@link SamrQueryDisplayInformation2Request}.
 */
public class SamrQueryDisplayInformation2Response<T extends Unmarshallable>
        extends RequestResponse
{
    private final DisplayInformationClass infoClass;
    private int totalAvailableBytes;
    private int totalReturnedBytes;
    private SAMPRDisplayInformationBuffer buffer;
    private int returnCode;

    public SamrQueryDisplayInformation2Response(DisplayInformationClass infoClass) {
        this.infoClass = infoClass;
    }

    public int getTotalAvailableBytes() {
        return totalAvailableBytes;
    }

    public int getTotalReturnedBytes() {
        return totalReturnedBytes;
    }

    public List<T> getList() {
        if (buffer == null)
            return null;
        return buffer.getEntries();
    }

    public int getReturnValue() {
        return returnCode;
    }

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        totalAvailableBytes = packetIn.readInt();
        totalReturnedBytes = packetIn.readInt();
        unmarshallBuffer(packetIn);
        returnCode = packetIn.readInt();
    }

    private void unmarshallBuffer(PacketInput packetIn) throws IOException {
        switch (infoClass) {
        case DomainDisplayGroup:
            buffer = new SAMPRDomainDisplayGroupBuffer();
            break;
        default:
            throw new UnmarshalException("Unsupported Display Information Class: " + infoClass.name());
        }
        packetIn.readUnmarshallable(buffer);
    }
}
