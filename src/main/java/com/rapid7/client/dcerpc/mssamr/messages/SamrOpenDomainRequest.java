/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msdtyp.SID;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.ServerHandle;

/**
 * 
 */
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
    /////////////////////////////////////////////////////////////////////////
    // Public methods
    /////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    // Non-public methods
    /////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////
    // Non-public fields
    /////////////////////////////////////////////////////////////////////////

}
