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
package com.rapid7.client.dcerpc.mssrvs;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupAcctPrivsRpcRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupAcctPrivsRpcResponse;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_LsarLookupAccPrivs {

    @Test
    public void lsar_SingleAcctPrivsRpcResponse() throws IOException {
	LsarLookupAcctPrivsRpcResponse response = new LsarLookupAcctPrivsRpcResponse();
	String hexString = "01000000000002000100000036003800040002001C000000000000001B00000053006500440075006D006D0079003100440075006D006D0079003200440075006D006D00790033003400350052006900670068007400000000000000";
	response.unmarshal(getPacketInput(hexString));
	//dummy Privilege
	String[] expectedPrivs = { "SeDummy1Dummy2Dummy345Right" };
	String[] privs = response.getPrivNames();

	assertTrue(Arrays.equals(expectedPrivs, privs));
    }

    @Test
    public void lsar_MultipleAcctPrivsRpcResponse() throws IOException {
	LsarLookupAcctPrivsRpcResponse response = new LsarLookupAcctPrivsRpcResponse();
	String hexString = "0300000000000200030000002E0030000400020036003800080002002E0030000C00020018000000000000001700000053006500440075006D006D007900440075006D006D007900440075006D006D00790031005200690067006800740000001C000000000000001B00000053006500440075006D006D0079003100440075006D006D0079003200440075006D006D00790033003400350052006900670068007400000018000000000000001700000053006500440075006D006D007900440075006D006D0079003100440075006D006D00790052006900670068007400000000000000";
	response.unmarshal(getPacketInput(hexString));
	//dummy Privileges
	String[] expectedPrivs = { "SeDummyDummyDummy1Right", "SeDummy1Dummy2Dummy345Right",
		"SeDummyDummy1DummyRight" };
	String[] privs = response.getPrivNames();

	assertTrue(Arrays.equals(expectedPrivs, privs));
    }

    @Test
    public void lsar_LsarLookupAccPrivsReqeust() throws IOException {
	ContextHandle handle = new ContextHandle();
	final byte[] b = Hex.decode("000000003451f9262c047d43b9c38648900abf7c");
	handle.setBytes(b);

	final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	final PacketOutput packetOut = new PacketOutput(outputStream);
	//dummy SIDs
	LsarLookupAcctPrivsRpcRequest request = new LsarLookupAcctPrivsRpcRequest(handle,
		"S-1-5-1-501");
	request.marshal(packetOut);

	assertEquals(Hex.toHexString(outputStream.toByteArray()),
		"000000003451f9262c047d43b9c38648900abf7c02000000010200000000000501000000f5010000");
    }

    private PacketInput getPacketInput(final String hexString) {
	final byte[] inputStreamBytes = Hex.decode(hexString);
	final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
	return new PacketInput(inputStream);
    }
}
