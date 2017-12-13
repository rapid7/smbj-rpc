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
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msvcctl.dto.IServiceConfigInfo;

public class RChangeServiceConfigWRequest extends RequestCall<RChangeServiceConfigWResponse> {
    private final static short OP_NUM = 11;
    private byte[] serviceHandle;
    private IServiceConfigInfo serviceConfigInfo;

    public RChangeServiceConfigWRequest(
        final byte[] handle,
        final IServiceConfigInfo serviceConfigInfo) {
        super(OP_NUM);
        this.serviceHandle = handle;
        this.serviceConfigInfo = serviceConfigInfo;
    }

    @Override
    public RChangeServiceConfigWResponse getResponseObject() {
        return new RChangeServiceConfigWResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle);
        packetOut.writeInt(serviceConfigInfo.getServiceType().getValue());
        packetOut.writeInt(serviceConfigInfo.getStartType().getValue());
        packetOut.writeInt(serviceConfigInfo.getErrorControl().getValue());
        if (serviceConfigInfo.getBinaryPathName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getBinaryPathName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getLoadOrderGroup() != null)
            packetOut.writeStringRef(serviceConfigInfo.getLoadOrderGroup(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getTagId() != 0) packetOut.writeIntRef(serviceConfigInfo.getTagId());
        else packetOut.writeNull();

        if (serviceConfigInfo.getDependencies() != null) {
            packetOut.writeReferentID();
            // Count the number of bytes required for the array of dependencies
            // This is better than allocated a new byte[] to hold everything.
            // At the very least we have a null terminator at the end
            int byteCount = 2;
            //final String[] dependencies = serviceConfigInfo.getDependencies();
            final String[] dependencies = serviceConfigInfo.getDependencies();
            for (final String dependency : dependencies) {
                // Number of UTF-16 bytes including null terminator
                byteCount += ((dependency == null) ? 0 : dependency.length() * 2) + 2;
            }
            // MaximumCount for Conformant Array
            packetOut.writeInt(byteCount);
            // Entries
            for (final String dependency : dependencies) {
                // This is better than allocating a new char[]
                for (int i = 0; i < dependency.length(); i++) {
                    // UTF-16 little endian
                    packetOut.writeChar(dependency.charAt(i));
                }
                // Each entry is null terminated
                packetOut.writeChar(0);
            }
            // Array is doubly null terminated
            packetOut.writeChar(0);
            // Alignment: 4
            packetOut.align(Alignment.FOUR);
            packetOut.writeInt(byteCount);
        } else {
            packetOut.writeNull();
            //dependency size
            packetOut.writeInt(0);
        }

        if (serviceConfigInfo.getServiceStartName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getServiceStartName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getPassword() != null) {
            packetOut.writeReferentID();
            final String password = serviceConfigInfo.getPassword();
            final int byteCount = (password.length() * 2) + 2;
            packetOut.writeInt(byteCount);
            for (int i = 0; i < password.length(); i++) {
                // UTF-16 little endian
                packetOut.writeChar(password.charAt(i));
            }
            // Null terminated
            packetOut.writeChar(0);
            //password size
            packetOut.align(Alignment.FOUR);
            packetOut.writeInt(byteCount);
        } else {
            packetOut.writeNull();
            //password size
            packetOut.writeInt(0);
        }

        if (serviceConfigInfo.getDisplayName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getDisplayName(), true);
        else packetOut.writeNull();
    }

    byte[] convertDependencies(final String[] dependencies) {
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        for (final String dependency : serviceConfigInfo.getDependencies()) {
            for (final char c : dependency.toCharArray()) {
                // UTF-16 little endian
                bout.write(c & 0x00FF);
                bout.write(c & 0xFF00);
            }
            bout.write(0);
            bout.write(0);
        }
        bout.write(0);
        bout.write(0);
        return bout.toByteArray();
    }
}
