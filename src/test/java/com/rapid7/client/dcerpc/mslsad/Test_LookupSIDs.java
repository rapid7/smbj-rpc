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

import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSIDsRequest;
import com.rapid7.client.dcerpc.mslsad.messages.LsarLookupSIDsResponse;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRReferencedDomainList;
import com.rapid7.client.dcerpc.mslsad.objects.LSAPRTranslatedName;
import com.rapid7.client.dcerpc.objects.RPCSID;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class Test_LookupSIDs
{
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseLookupSIDsResponse() throws IOException {
        final LsarLookupSIDsResponse response = new LsarLookupSIDsResponse();
        response.fromHexString(
            "00000200010000000400020020000000010000001a001c00080002000c0002000e000000000000000d0000005700310030002d0045004e0054002d005800360034002d005500000004000000010400000000000515000000a43cb4affe0503bd73de0f3504000000100002000400000001000000080008001400020000000000010000001a001a001800020000000000010000000a000a001c000200000000000300000000000000000000000000000004000000000000000400000075007300650072000d000000000000000d000000410064006d0069006e006900730074007200610074006f00720000000500000000000000050000004700750065007300740000000400000000000000");
        LSAPRReferencedDomainList lsaprReferencedDomainList = response.getLsaprReferencedDomainList();
        LSAPRTranslatedName[] lsaprTranslatedNames = response.getLsaprTranslatedNames().getlsaprTranslatedNameArray();

        RPCSID expectSid = new RPCSID();
        expectSid.setRevision((char) 1);
        expectSid.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        expectSid.setSubAuthority(new long[]{21, 2947824804L, 3171091966L, 890232435});
        assertEquals(lsaprReferencedDomainList.getLsaprTrustInformations()[0].getSid(), expectSid);

        String [] expectedMappings = new String[]{"user", "Administrator", "Guest", null};
        for (int i = 0; i < lsaprTranslatedNames.length; i++){
            assertEquals(lsaprTranslatedNames[i].getName().getValue(), expectedMappings[i]);
        }
    }

    @Test
    public void encodeLookupSIDsRequest() throws IOException {
        final byte[] fakePolicyHandle = Hex.decode("000000003a668348d29edc4db807b15d0cbf8324");
        /*
         * "S-1-5-21-2947824804-3171091966-890232435-501",
         * "S-1-5-21-2947824804-3171091966-890232435-1001",
         * "S-1-5-21-2947824804-3171091966-890232435-500"
         */
        RPCSID sid1 = new RPCSID();
        sid1.setRevision((char) 1);
        sid1.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid1.setSubAuthority(new long[]{21, 2947824804L, 3171091966L, 890232435, 501});
        RPCSID sid2 = new RPCSID();
        sid2.setRevision((char) 1);
        sid2.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid2.setSubAuthority(new long[]{21, 2947824804L, 3171091966L, 890232435, 1001});
        RPCSID sid3 = new RPCSID();
        sid3.setRevision((char) 1);
        sid3.setIdentifierAuthority(new byte[]{0, 0, 0, 0, 0, 5});
        sid3.setSubAuthority(new long[]{21, 2947824804L, 3171091966L, 890232435, 500});
        final RPCSID[] rpcsids = {sid1, sid2, sid3};
        final LsarLookupSIDsRequest request = new LsarLookupSIDsRequest(fakePolicyHandle, rpcsids);
        assertEquals(request.toHexString(), "000000003a668348d29edc4db807b15d0cbf832403000000000002000300000004000200080002000c00020005000000010500000000000515000000a43cb4affe0503bd73de0f35f501000005000000010500000000000515000000a43cb4affe0503bd73de0f35e903000005000000010500000000000515000000a43cb4affe0503bd73de0f35f401000000000000000000000100000000000000");
    }
}
