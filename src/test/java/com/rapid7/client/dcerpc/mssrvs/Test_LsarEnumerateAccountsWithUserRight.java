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
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountsWithUserRightRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarEnumerateAccountsWithUserRightResponse;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Test_LsarEnumerateAccountsWithUserRight {

    @Test
    public void lsar_SingleLsarEnumerateAccountsWithUserRightResponse() throws IOException {
        LsarEnumerateAccountsWithUserRightResponse response = new LsarEnumerateAccountsWithUserRightResponse();
        String hexString = "0100000000000200010000000400020005000000010500000000000500000000000000000000000000000000f501000000000000";
        response.unmarshal(getPacketInput(hexString));

        //dummy SID
        // "S-1-5-0-0-0-0-501"
        RPCSID expectedSid = new RPCSID();
        expectedSid.setRevision((char) 1);
        expectedSid.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        expectedSid.setSubAuthority(new long[]{0, 0, 0, 0, 501});
        RPCSID[] sids = response.getSids();

        assertEquals(expectedSid, sids[0]);

    }

    @Test
    public void lsar_SingleLsarEnumerateAccountsWithUserRightResponseErrorCase() throws IOException {
        //UnknownRight
        LsarEnumerateAccountsWithUserRightResponse response = new LsarEnumerateAccountsWithUserRightResponse();
        //STATUS_NO_SUCH_PRIVILEGE (0xc0000060)
        String hexString = "0000000000000000600000c0";
        response.unmarshal(getPacketInput(hexString));

        assertNull(response.getSids());
    }

    @Test
    public void lsar_MultipleLsarEnumerateAccountsWithUserRightResponse() throws IOException {
        LsarEnumerateAccountsWithUserRightResponse response = new LsarEnumerateAccountsWithUserRightResponse();
        String hexString = "04000000000002000400000004000200080002000c0002001000020002000000010200000000000501000000f50100000200000001020000000000050200000021020000020000000102000000000005030000002002000005000000010500000000000504000000000000000000000000000000f501000000000000";

        response.unmarshal(getPacketInput(hexString));

        //dummy SIDs
        // "S-1-5-1-501", "S-1-5-2-545", "S-1-5-3-544", "S-1-5-4-0-0-0-501"
        RPCSID sid1 = new RPCSID();
        sid1.setRevision((char) 1);
        sid1.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid1.setSubAuthority(new long[]{1, 501});
        RPCSID sid2 = new RPCSID();
        sid2.setRevision((char) 1);
        sid2.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid2.setSubAuthority(new long[]{2, 545});
        RPCSID sid3 = new RPCSID();
        sid3.setRevision((char) 1);
        sid3.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid3.setSubAuthority(new long[]{3, 544});
        RPCSID sid4 = new RPCSID();
        sid4.setRevision((char) 1);
        sid4.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid4.setSubAuthority(new long[]{4, 0, 0, 0, 501});
        RPCSID[] expectedSids = {sid1, sid2, sid3, sid4};
        RPCSID[] sids = response.getSids();

        for (int i = 0; i < expectedSids.length; i++) {
            assertEquals(expectedSids[i], sids[i]);
        }
    }

    @Test
    public void lsar_LsarEnumerateAccountsWithUserRightRequest() throws IOException {
        final byte[] handle = Hex.decode("00000000e65642fb8690d346879621aa30ed8486");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);

        LsarEnumerateAccountsWithUserRightRequest request = new LsarEnumerateAccountsWithUserRightRequest(handle,
                RPCUnicodeString.NonNullTerminated.of("SeDummyDummyDummy1Right"));
        request.marshal(packetOut);

        assertEquals(Hex.toHexString(outputStream.toByteArray()), "00000000e65642fb8690d346879621aa30ed8486000002002e002e000400020017000000000000001700000053006500440075006d006d007900440075006d006d007900440075006d006d007900310052006900670068007400");
    }

    @Test
    public void LsarEnumerateAccountsWithUserRightRequest_NoRight() throws IOException {
        final byte[] handle = Hex.decode("000000000041fa2f0270204bafe49cd42e5b4584");

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PacketOutput packetOut = new PacketOutput(outputStream);

        //UnknownRight
        LsarEnumerateAccountsWithUserRightRequest request = new LsarEnumerateAccountsWithUserRightRequest(handle, RPCUnicodeString.NonNullTerminated.of("UnknownRight"));
        request.marshal(packetOut);

        assertEquals(Hex.toHexString(outputStream.toByteArray()), "000000000041fa2f0270204bafe49cd42e5b45840000020018001800040002000c000000000000000c00000055006e006b006e006f0077006e0052006900670068007400");

    }

    private PacketInput getPacketInput(final String hexString) {
        final byte[] inputStreamBytes = Hex.decode(hexString);
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(inputStreamBytes);
        return new PacketInput(inputStream);
    }
}
