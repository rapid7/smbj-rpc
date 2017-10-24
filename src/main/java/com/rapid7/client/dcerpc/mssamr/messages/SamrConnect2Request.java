/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import java.util.EnumSet;
import com.google.common.base.Strings;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * 
 */
public class SamrConnect2Request extends RequestCall<SamrConnect2Response> {

    public final static short OP_NUM = 57;
    private final String serverName;
    private final EnumSet<AccessMask> desiredAccess;

    public SamrConnect2Request(String serverName, final EnumSet<AccessMask> desiredAccess) {
        super(OP_NUM);
        this.serverName = Strings.nullToEmpty(serverName);
        this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut)
        throws IOException {
        packetOut.writeStringRef(serverName, true);
        packetOut.writeInt((int) EnumUtils.toLong(desiredAccess));
    }

    @Override
    public SamrConnect2Response getResponseObject() {
        return new SamrConnect2Response();
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
