/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.objects.DomainHandle;

/**
 *
 */
public class SamrOpenUserRequest extends RequestCall<SamrOpenUserResponse> {
    public final static short OP_NUM = 34;

    private final DomainHandle handle;
    private final int userRid;

    public SamrOpenUserRequest(DomainHandle handle, int userRid) {
        super(OP_NUM);
        this.handle = handle;
        this.userRid = userRid;
    }

    @Override
    public void marshal(PacketOutput packetOut)
        throws IOException {
	packetOut.write(handle.getBytes());
	// Generic rights: 0x00000000
	// Standard rights: 0x00020000
	// SAMR User specific rights: 0x0000011b
	// Samr User Access Get
	// - Groups: SAMR_USER_ACCESS_GET_GROUPS is SET
	// - Attributes: SAMR_USER_ACCESS_GET_ATTRIBUTES is SET
	// - Logoninfo: SAMR_USER_ACCESS_GET_LOGONINFO is SET
        packetOut.writeInt(0x2011B);
        packetOut.writeInt(userRid);
    }

    @Override
    public SamrOpenUserResponse getResponseObject() {
        return new SamrOpenUserResponse();
    }

}
