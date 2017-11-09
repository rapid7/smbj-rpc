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

public class Test_EnumeratedDomains {
    @Test
    public void unmarshall() throws IOException {
        EnumeratedDomains domains = new EnumeratedDomains();
        String byteArray = new String(
                "020000000400020002000000000000001400160008000200000000000e0010000c0002000b000000000000000a000000770069006e0064006f00770073003100300030000800000000000000070000004200750069006c00740069006e000000");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Hex.decode(byteArray));
        PacketInput in = new PacketInput(inputStream);
        in.readUnmarshallable(domains);
        assertEquals(2, domains.getEntriesRead());
        assertEquals("windows100", domains.getEntries().get(0).getName());
        assertEquals("Builtin", domains.getEntries().get(1).getName());
    }
}
