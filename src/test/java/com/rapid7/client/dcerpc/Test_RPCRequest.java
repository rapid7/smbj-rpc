package com.rapid7.client.dcerpc;

import com.hierynomus.protocol.transport.TransportException;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.spy;

public class Test_RPCRequest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void badCallID()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Call ID mismatch: 0 != 10");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500000310000000100000000a000000");

        request.unmarshal(testVectorBytes, 0);
    }

    @Test
    public void parseREQUEST()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: REQUEST");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500000310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parsePING()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: PING");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500010310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseRESPONSE()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: RESPONSE");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500020310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseFAULT()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: FAULT");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500030310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseWORKING()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: WORKING");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500040310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseNOCALL()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: NOCALL");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500050310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseREJECT()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: REJECT");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500060310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseACK()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: ACK");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseCL_CANCEL()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: CL_CANCEL");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500080310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseFACK()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: FACK");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500090310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseCANCEL_ACK()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: CANCEL_ACK");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("05000a0310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseBIND()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: BIND");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("05000b0310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseBIND_ACK()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: BIND_ACK");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("05000c0310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseBIND_NAK()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: BIND_NAK");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("05000d0310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseALTER_CONTEXT()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: ALTER_CONTEXT");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("05000e0310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseALTER_CONTEXT_RESP()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: ALTER_CONTEXT_RESP");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("05000f0310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseSHUTDOWN()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: SHUTDOWN");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500110310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseCO_CANCEL()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: CO_CANCEL");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500120310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }

    @Test
    public void parseORPHANED()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Unsupported PDU type in response message: ORPHANED");

        final RPCRequest<RPCResponse> request = spy(
            new RPCRequest<RPCResponse>(PDUType.ACK, EnumSet.of(PFCFlag.MAYBE)));
        final byte[] testVectorBytes = Hex.decode("0500130310000000100000000a000000");

        request.unmarshal(testVectorBytes, 10);
    }
}
