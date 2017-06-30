package com.rapid7.client.dcerpc;

import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.junit.Test;

import com.hierynomus.smbj.transport.TransportException;

public class Test_RPCResponse {
    @Test
    public void parse() throws TransportException {
        final byte[] testVectorBytes = new BigInteger("0500070310000000100000000a000000", 16).toByteArray();
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new RPCResponse(testVectorBuffer);
    }
}
