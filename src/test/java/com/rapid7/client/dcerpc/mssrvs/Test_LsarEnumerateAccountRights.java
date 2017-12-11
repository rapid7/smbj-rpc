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
package com.rapid7.client.dcerpc.mssrvs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountRightsRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountRightsResponse;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test_LsarEnumerateAccountRights {

    @Test
    public void lsar_SingleLsarEnumerateAccountRightsResponse() throws IOException {
        LsarEnumerateAccountRightsResponse response = new LsarEnumerateAccountRightsResponse();
        String hexString = "01000000000002000100000036003800040002001C000000000000001B00000053006500440075006D006D0079003100440075006D006D0079003200440075006D006D00790033003400350052006900670068007400000000000000";
        response.unmarshal(getPacketInput(hexString));
        //dummy Privilege
        RPCUnicodeString.NonNullTerminated[] expectedPrivs = {RPCUnicodeString.NonNullTerminated.of("SeDummy1Dummy2Dummy345Right")};
        RPCUnicodeString.NonNullTerminated[] privs = response.getPrivNames();

        assertTrue(Arrays.equals(expectedPrivs, privs));
    }

    @Test
    public void lsar_MultipleLsarEnumerateAccountRightsResponse() throws IOException {
        LsarEnumerateAccountRightsResponse response = new LsarEnumerateAccountRightsResponse();
        String hexString = "0300000000000200030000002E0030000400020036003800080002002E0030000C00020018000000000000001700000053006500440075006D006D007900440075006D006D007900440075006D006D00790031005200690067006800740000001C000000000000001B00000053006500440075006D006D0079003100440075006D006D0079003200440075006D006D00790033003400350052006900670068007400000018000000000000001700000053006500440075006D006D007900440075006D006D0079003100440075006D006D00790052006900670068007400000000000000";
        response.unmarshal(getPacketInput(hexString));
        //dummy Privileges
        RPCUnicodeString.NonNullTerminated[] expectedPrivs = {
                RPCUnicodeString.NonNullTerminated.of("SeDummyDummyDummy1Right"),
                RPCUnicodeString.NonNullTerminated.of("SeDummy1Dummy2Dummy345Right"),
                RPCUnicodeString.NonNullTerminated.of("SeDummyDummy1DummyRight")
        };
        RPCUnicodeString.NonNullTerminated[] privs = response.getPrivNames();

        assertTrue(Arrays.equals(expectedPrivs, privs));
    }

    @Test
    public void lsar_LsarEnumerateAccountRightsResponse() throws IOException {
        final byte[] handle = Hex.decode("000000003451f9262c047d43b9c38648900abf7c");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);
        //dummy SIDs
        // "S-1-5-1-501"
        RPCSID rpcsid = new RPCSID();
        rpcsid.setRevision((char) 1);
        rpcsid.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        rpcsid.setSubAuthority(new long[]{1, 501});
        LsarEnumerateAccountRightsRequest request = new LsarEnumerateAccountRightsRequest(handle, rpcsid);
        request.marshal(packetOut);

        assertEquals(Hex.toHexString(outputStream.toByteArray()), "000000003451f9262c047d43b9c38648900abf7c02000000010200000000000501000000f5010000");
    }

    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
