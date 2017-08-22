package com.rapid7.client.dcerpc;

import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class Test_RPCResponse {
    @Test
    public void parse()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new RPCResponse(testVectorBuffer);
    }
}
