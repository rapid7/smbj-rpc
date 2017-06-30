package com.rapid7.client.dcerpc.messages;

import static org.junit.Assert.assertEquals;
import java.math.BigInteger;

import org.junit.Test;

import com.rapid7.client.dcerpc.Interface;

public class Test_Bind {
    private final Bind request = new Bind(Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);

    @Test
    public void request() {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Bind, Fragment: Single, FragLen: 72,
        // Call: 1
        // Version: 5
        // Version (minor): 0
        // Packet type: Bind (11)
        // Packet Flags: 0x03
        // Data Representation: 10000000 (Order: Little-endian, Char: ASCII, Float: IEEE)
        // Frag Length: 72
        // Auth Length: 0
        // Call ID: 1
        // Max Xmit Frag: 4096
        // Max Recv Frag: 4096
        // Assoc Group: 0x00000000
        // Num Ctx Items: 1
        // Ctx Item[1]: Context ID:0, WINREG, 32bit NDR
        final byte[] requestBytes = request.marshal(1);
        final String encodedRequest = String.format(String.format("%%0%dx", requestBytes.length << 1),
            new BigInteger(1, requestBytes));

        assertEquals(
            "05000b031000000048000000010000000010001000000000010000000000010001d08c334422f131aaaa90003800100301000000045d888aeb1cc9119fe808002b10486002000000",
            encodedRequest);
    }
}
