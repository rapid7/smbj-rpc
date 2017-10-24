/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mslsad.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import java.io.IOException;

public class LsarClosePolicyRpcRequest extends RequestCall<LsarClosePolicyRpcResponse> {
    private final static short OP_NUM = 0;
    private final ContextHandle handle;

    public LsarClosePolicyRpcRequest(final ContextHandle handle) {
	super(OP_NUM);
	this.handle = handle;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
	// set the policy handle
	packetOut.write(handle.getBytes());
    }

    @Override
    public LsarClosePolicyRpcResponse getResponseObject() {
	return new LsarClosePolicyRpcResponse();
    }

}
