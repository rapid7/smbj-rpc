/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mslsad.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import java.io.IOException;

public class LsarLookupSidsWithAcctPrivRpcRequest extends RequestCall<LsarLookupSidsWithAcctPrivRpcResponse> {
    private final static short OP_NUM = 35;
    private final ContextHandle handle;
    private final String acctPrivilege;

    public LsarLookupSidsWithAcctPrivRpcRequest(final ContextHandle handle, final String acctPrivilege) {
	super(OP_NUM);
	this.handle = handle;
	this.acctPrivilege = acctPrivilege;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
	/*
	 * Rpc Info
	 * MajorVer:   05
	 * MinorVer:   00
	 * PacketType: 00 (Request)
	 * Flags:      03
	 * PackType:   10000000
	 * FragLen:    6A00
	 * AuthLen:    0000
	 * CallId:     05000000
	 * AllocHint:  52000000
	 * ContextId:  0000
	 * OpNum:      2300 (LsaLookupSidsWithAcctPrivWithAcctPriv)
	 *
	 * PolicyHnd:  00000000EE88616D65EFD711BCDDEA5AF8147838
	 * Ptr:        C88C1400
	 * UniHdr:     2600 2800 508D1400
	 * UniStr:     14000000 00000000 13000000
	 *             5300650053006500630075007200690074007900500072006900760069006C00650067006500
	 *             SeSecurityPrivilege
	 */

	// set the policy handle
	packetOut.write(handle.getBytes());
	packetOut.writeStringBufferRef(acctPrivilege, false);
    }

    @Override
    public LsarLookupSidsWithAcctPrivRpcResponse getResponseObject() {
	return new LsarLookupSidsWithAcctPrivRpcResponse();
    }

}
