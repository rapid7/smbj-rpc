/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
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
                "00000200020000000400020002000000000000001400160008000200000000000e0010000c0002000b000000000000000a000000770069006e0064006f00770073003100300030000800000000000000070000004200750069006c00740069006e000000");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Hex.decode(byteArray));
        PacketInput in = new PacketInput(inputStream);
        in.readUnmarshallable(domains);
        assertEquals(2, domains.getEntriesRead());
        assertEquals("windows100", domains.getEntries().get(0).getName());
        assertEquals("Builtin", domains.getEntries().get(1).getName());

    }
}
