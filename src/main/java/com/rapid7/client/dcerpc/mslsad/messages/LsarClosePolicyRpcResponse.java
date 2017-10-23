/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mslsad.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import java.io.IOException;

public class LsarClosePolicyRpcResponse extends RequestResponse {
    private byte[] handle;

    public byte[] getPolicyHandle() {
	return handle;
    }

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
	/*
	 * Rpc Info
	 *
	 * MajorVer: 05
	 * MinorVer: 00
	 * PacketType: 02 (Response)
	 * Flags: 03
	 * PackType: 10000000
	 * FragLen: 3000
	 * AuthLen: 0000
	 * CallId: 01000000
	 * AllocHint: 18000000
	 * ContextId: 0000
	 * CancelCount: 00
	 * Rsvd: 00
	 * PolicyHnd: 00000000A6327DA8A113D7119878C915DD98180A
	 * Status: 00000000
	 */

	handle = packetIn.readRawBytes(20);
    }

}
