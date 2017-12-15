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
package com.rapid7.client.dcerpc.mssamr.objects;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

public class Test_SAMPRDisplayGroupBuffer {

    @Test
    public void unmarshall() throws IOException {
        SAMPRDomainDisplayGroupBuffer buffer = new SAMPRDomainDisplayGroupBuffer();
        String byteArray = "01000000000002000100000001000000010200000700000008000800040002001c001c00080002000400000000000000040000004e006f006e0065000e000000000000000e0000004f007200640069006e00610072007900200075007300650072007300";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Hex.decode(byteArray));
        PacketInput in = new PacketInput(inputStream);
        in.readUnmarshallable(buffer);
        assertEquals(1, buffer.getEntriesRead());
        SAMPRDomainDisplayGroup groupInfo = buffer.getEntries().get(0);
        assertEquals(1, groupInfo.getIndex());
        assertEquals(513, groupInfo.getRid());
        assertEquals(RPCUnicodeString.NonNullTerminated.of("None"), groupInfo.getAccountName());
        assertEquals(RPCUnicodeString.NonNullTerminated.of("Ordinary users"), groupInfo.getDescription());
        assertEquals(0x00000007, groupInfo.getAttributes());
    }

}
