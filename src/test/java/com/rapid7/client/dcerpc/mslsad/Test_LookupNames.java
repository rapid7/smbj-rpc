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

package com.rapid7.client.dcerpc.mslsad;

import static org.testng.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.mslsad.dto.LSAPLookupLevel;
import com.rapid7.client.dcerpc.mslsad.dto.PolicyHandle;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupNamesResponse;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRReferencedDomainList;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedSIDs;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class Test_LookupNames {

    @SuppressWarnings("unchecked")
    @Test
    public void parseLookupNamesResponse()
        throws IOException
    {
        final LsarLookupNamesResponse response = new LsarLookupNamesResponse();
        response.fromHexString(
            "00000200010000000400020020000000010000001a001c00080002000c0002000e000000000000000d0000005700310030002d0045004e0054002d005800360034002d005500000004000000010400000000000515000000a43cb4affe0503bd73de0f3501000000100002000100000001000000f4010000000000000100000000000000");
        LSAPRReferencedDomainList lsaprReferencedDomainList = response.getReferencedDomains();
        LSAPRTranslatedSIDs lsaprTranslatedSIDs = response.getTranslatedSIDs();

        RPCSID expectSid = new RPCSID();
        expectSid.setRevision((char) 1);
        expectSid.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        expectSid.setSubAuthority(new long[]{21, 2947824804L, 3171091966L, 890232435});
        assertEquals(lsaprReferencedDomainList.getDomains()[0].getSid(), expectSid);
        assertEquals(lsaprTranslatedSIDs.getSIDs()[0].getRelativeId(), 500);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeLookupNamesRequest() throws IOException {
        final byte[] fakePolicyHandle = Hex.decode("000000008e3039708fdd9f488f9665426d0d9c57");
        final RPCUnicodeString.NonNullTerminated[] names = {RPCUnicodeString.NonNullTerminated.of("Administrator"), RPCUnicodeString.NonNullTerminated.of("Administrator2")};
        final LsarLookupNamesRequest request = new LsarLookupNamesRequest(fakePolicyHandle, names, LSAPLookupLevel.LSAP_LOOKUP_WKSTA.getValue());
        assertEquals(request.toHexString(), "000000008e3039708fdd9f488f9665426d0d9c5702000000020000001a001a00000002001c001c00040002000d000000000000000d000000410064006d0069006e006900730074007200610074006f00720000000e000000000000000e000000410064006d0069006e006900730074007200610074006f007200320000000000000000000100000000000000");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void encodeLookupNamesRequest2() throws IOException {
        final byte[] fakePolicyHandle = Hex.decode("000000008e3039708fdd9f488f9665426d0d9c57");
        final RPCUnicodeString.NonNullTerminated[] names = {RPCUnicodeString.NonNullTerminated.of("Administrator"), RPCUnicodeString.NonNullTerminated.of("Administrator2")};
        final LsarLookupNamesRequest request = new LsarLookupNamesRequest(fakePolicyHandle, names, LSAPLookupLevel.LSAP_LOOKUP_TDL.getValue());
        assertEquals(request.toHexString(),
            "000000008e3039708fdd9f488f9665426d0d9c5702000000020000001a001a00000002001c001c00040002000d000000000000000d000000410064006d0069006e006900730074007200610074006f00720000000e000000000000000e000000410064006d0069006e006900730074007200610074006f007200320000000000000000000300000000000000");
    }

    //This test is to verify that the Service correctly sets invalid SIDs to null from a valid response
    @Test
    public void test_lookupSIDsForNames() throws IOException {
        final RPCTransport transport = mock(RPCTransport.class);
        final PolicyHandle fakePolicyHandle = new PolicyHandle("000000008e3039708fdd".getBytes());
        final LocalSecurityAuthorityService localSecurityAuthorityService = new LocalSecurityAuthorityService(transport);

        final LsarLookupNamesResponse response = new LsarLookupNamesResponse();

        response.fromHexString(
                "00000200010000000400020020000000010000001a001c00080002000c0002000e000000000000000d0000005700310030002d0045004e0054002d005800360034002d005500000004000000010400000000000515000000a43cb4affe0503bd73de0f3504000000100002000400000001000000f40100000000000001000000e90300000000000001000000f5010000000000000800000000000000ffffffff0300000007010000");

        when(transport.call(any(LsarLookupNamesRequest.class))).thenReturn(response);

        SID[] SIDs = localSecurityAuthorityService.lookupSIDsForNames(fakePolicyHandle, (String[]) null);

        SID expectedDomainSID = new SID(
                (byte) 1,
                new byte[]{0,0,0,0,0,5},
                new long[]{21, 2947824804L, 3171091966L, 890232435L});

        SID[] expectedSIDs = new SID[4];
        expectedSIDs[0] = expectedDomainSID.resolveRelativeID(500);
        expectedSIDs[1] = expectedDomainSID.resolveRelativeID(1001);
        expectedSIDs[2] = expectedDomainSID.resolveRelativeID(501);
        expectedSIDs[3] = null;

        assertEquals(SIDs, expectedSIDs);
    }
}
