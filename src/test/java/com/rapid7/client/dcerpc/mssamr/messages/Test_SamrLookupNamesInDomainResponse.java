/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.testng.annotations.Test;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRULongArray;

import static org.testng.Assert.assertEquals;

public class Test_SamrLookupNamesInDomainResponse {
    @Test
    public void test_getters() {
        SamrLookupNamesInDomainResponse obj = new SamrLookupNamesInDomainResponse();
        assertEquals(obj.getRelativeIds(), null);
        assertEquals(obj.getUse(), null);
        assertEquals(obj.getReturnValue(), 0);
    }


    @Test
    public void test_unmarshal() throws IOException {
        String hex =
                // RelativeIds: {Count: 3, Reference: 1}
                "03000000 01000000" +
                // RelativeIds: {MaximumCount: 2, Array: {1, 2147483648L, 3},
                "02000000 01000000 00000080 03000000" +
                // Use: {Count: 3, Reference: 2}
                "03000000 02000000" +
                // Use: {MaximumCount: 2, Array: {4, 5, 6},
                "02000000 04000000 05000000 06000000" +
                // Return Value
                "01000000";

        ByteArrayInputStream bin = new ByteArrayInputStream(Hex.decode(hex));
        PacketInput in = new PacketInput(bin);
        SamrLookupNamesInDomainResponse obj = new SamrLookupNamesInDomainResponse();
        obj.unmarshal(in);

        assertEquals(bin.available(), 0);
        SAMPRULongArray relativeIds = new SAMPRULongArray();
        relativeIds.setArray(new long[]{1, 2147483648L, 3});
        assertEquals(obj.getRelativeIds(), relativeIds);
        SAMPRULongArray use = new SAMPRULongArray();
        use.setArray(new long[]{4, 5, 6});
        assertEquals(obj.getUse(), use);
        assertEquals(obj.getReturnValue(), 1);
    }
}
