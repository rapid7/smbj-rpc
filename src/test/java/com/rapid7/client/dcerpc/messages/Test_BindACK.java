package com.rapid7.client.dcerpc.messages;

import static org.junit.Assert.assertTrue;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.Interface;
import com.rapid7.client.dcerpc.RPCResponse;

public class Test_BindACK {
    private final Bind request = new Bind(Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);

    @Test
    public void response()
        throws TransportException {
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Bind_ack, Fragment: Single, FragLen: 68,
        // Call: 1
        // Version: 5
        // Version (minor): 0
        // Packet type: Bind_ack (12)
        // Packet Flags: 0x03
        // Data Representation: 10000000 (Order: Little-endian, Char: ASCII, Float: IEEE)
        // Frag Length: 68
        // Auth Length: 0
        // Call ID: 1
        // Max Xmit Frag: 4096
        // Max Recv Frag: 4096
        // Assoc Group: 0x000021a5
        // Scndry Addr len: 13
        // Scndry Addr: \PIPE\winreg
        // Num results: 1
        // Ctx Item[1]: Acceptance, 32bit NDR
        final byte[] responseBytes = Hex.decode(
            "05000c0310000000440000000100000000100010a52100000d005c504950455c77696e72656700000100000000000000045d888aeb1cc9119fe808002b10486002000000");
        final RPCResponse response = request.unmarshal(responseBytes, 1);

        assertTrue(response instanceof BindACK);
    }
}
