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
package com.rapid7.client.dcerpc.msrrp.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public class RPCSecurityDescriptor implements Unmarshallable, Marshallable {
    private int size;
    private int maxSize;
    private int offset;
    private byte[] rawSecurityDescriptor;

    public void setSize(int size) {
        this.size = size;
    }

    public void setMaxSize(int size) {
        this.maxSize = size;
    }

    public RPCSecurityDescriptor() {

    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        in.readReferentID();
        in.readInt();
        in.readInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        in.align(Alignment.FOUR);
        maxSize = in.readInt();
        offset = in.readInt();
        size = in.readInt();
        // TODO: Return parsed object.
        rawSecurityDescriptor = in.readRawBytes(size);
    }

    @Override
    public void marshalPreamble(PacketOutput out) throws IOException {
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        out.writeReferentID();
        out.writeInt(maxSize);
        out.writeInt(size);
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        out.writeInt(maxSize);
        out.writeInt(offset);
        out.writeInt(size);
    }

    public byte[] getRawSecurityDescriptor() {
        return rawSecurityDescriptor;
    }
}
