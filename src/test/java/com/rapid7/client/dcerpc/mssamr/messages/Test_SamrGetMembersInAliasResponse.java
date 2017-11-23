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
package com.rapid7.client.dcerpc.mssamr.messages;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import com.rapid7.client.dcerpc.objects.MalformedSIDException;
import com.rapid7.client.dcerpc.objects.RPCSID;

public class Test_SamrGetMembersInAliasResponse {

    @Test
    public void SamrGetMembersInAliasResponse() throws IOException, MalformedSIDException {
        final SamrGetMembersInAliasResponse response = new SamrGetMembersInAliasResponse();
        response.fromHexString(
            "03000000000002000300000004000200080002000c00020005000000010500000000000515000000000000000000000000000000f401000005000000010500000000000515000000000000000000000000000000e903000005000000010500000000000515000000000000000000000000000000eb03000000000000");
        RPCSID[] list = response.getSids();
        assertTrue(response.isSuccess());

        assertEquals(3, list.length);
        assertEquals(list[0].toString(),
            "RPC_SID{Revision:1, SubAuthorityCount:5, IdentifierAuthority:[0, 0, 0, 0, 0, 5], SubAuthority: [21, 0, 0, 0, 500]}");
        assertEquals(list[1].toString(),
            "RPC_SID{Revision:1, SubAuthorityCount:5, IdentifierAuthority:[0, 0, 0, 0, 0, 5], SubAuthority: [21, 0, 0, 0, 1001]}");
        assertEquals(list[2].toString(),
            "RPC_SID{Revision:1, SubAuthorityCount:5, IdentifierAuthority:[0, 0, 0, 0, 0, 5], SubAuthority: [21, 0, 0, 0, 1003]}");
    }
}
