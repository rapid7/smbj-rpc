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
import java.util.List;
import org.junit.Test;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRSIDInformation;
import com.rapid7.client.dcerpc.objects.MalformedSIDException;

public class Test_SamrGetMembersInAliasResponse {

    @Test
    public void SamrGetMembersInAliasResponse() throws IOException, MalformedSIDException {
        final SamrGetMembersInAliasResponse response = new SamrGetMembersInAliasResponse();
        response.fromHexString(
            "03000000000002000300000004000200080002000c00020005000000010500000000000515000000000000000000000000000000f401000005000000010500000000000515000000000000000000000000000000e903000005000000010500000000000515000000000000000000000000000000eb03000000000000");
        List<SAMPRSIDInformation> list = response.getSIDs();
        assertTrue(response.isSuccess());
        assertEquals(3, list.size());
        assertEquals(list.get(0).getSidPointer().toString(), "S-1-5-21-0-0-0-500");
        assertEquals(list.get(1).getSidPointer().toString(), "S-1-5-21-0-0-0-1001");
        assertEquals(list.get(2).getSidPointer().toString(), "S-1-5-21-0-0-0-1003");
    }
}
