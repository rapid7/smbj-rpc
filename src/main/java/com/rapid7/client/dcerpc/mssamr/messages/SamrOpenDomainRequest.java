/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;

public class SamrOpenDomainRequest extends RequestCall<SamrOpenDomainResponse> {
    public final static short OP_NUM = 7;

    private final ServerHandle handle;
    private final SID sid;
    private final EnumSet<AccessMask> desiredAccess;

    public SamrOpenDomainRequest(ServerHandle handle, SID sid, EnumSet<AccessMask> desiredAccess) {
	super(OP_NUM);
	this.handle = handle;
	this.sid = sid;
	this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut)
	    throws IOException {
	packetOut.write(handle.getBytes());
	packetOut.writeInt((int) EnumUtils.toLong(desiredAccess));

	// SID sub authority count
	packetOut.writeInt(sid.getSubAuthorities().length);

	// SID start
	packetOut.writeByte(sid.getRevision());
	packetOut.writeByte((byte) (sid.getSubAuthorities().length));
	packetOut.write(sid.getSidIdentifierAuthority());
	for (int i = 0; i < sid.getSubAuthorities().length; i++) {
	    packetOut.writeInt((int) (sid.getSubAuthorities()[i])); // SubAuthority (variable * 4 bytes)
	}
	// SID end
    }

    @Override
    public SamrOpenDomainResponse getResponseObject() {
	return new SamrOpenDomainResponse();
    }
}
