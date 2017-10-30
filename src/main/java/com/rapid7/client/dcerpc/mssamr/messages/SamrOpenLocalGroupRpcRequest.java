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
public class SamrOpenLocalGroupRpcRequest extends RequestCall<SamrOpenLocalGroupRpcResponse> {
    public final static short OP_NUM = 27;

    private final DomainHandle handle;
    private final int userRid;
    private final int desiredAccess;

    public SamrOpenLocalGroupRpcRequest(DomainHandle handle, int userRid) {
	// SAMR_ALIAS_ACCESS_LOOKUP_INFO(8)
	this(handle, userRid, 8);
    }

    public SamrOpenLocalGroupRpcRequest(DomainHandle handle, int userRid, int desiredAccess) {
        super(OP_NUM);
        this.handle = handle;
        this.userRid = userRid;
        this.desiredAccess = desiredAccess;
    }

    @Override
    public void marshal(PacketOutput packetOut)
        throws IOException {

	packetOut.write(handle.getBytes());
	packetOut.writeInt(desiredAccess);
        packetOut.writeInt(userRid);
    }

    @Override
    public SamrOpenLocalGroupRpcResponse getResponseObject() {
        return new SamrOpenLocalGroupRpcResponse();
    }

}
