package com.rapid7.client.dcerpc.transport;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Queue;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import com.rapid7.client.dcerpc.Interface;
import com.rapid7.client.dcerpc.PDUType;
import com.rapid7.client.dcerpc.PFCFlag;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.BindRequest;
import com.rapid7.client.dcerpc.messages.BindResponse;
import com.rapid7.client.dcerpc.messages.Request;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.messages.Response;

public class Test_RPCTransport {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void bindACK()
        throws IOException {
        final BindRequest request = new BindRequest(16384, 16384, Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);

        request.setPDUType(PDUType.BIND);
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));

        final String requestHexString = request.toHexString();
        final byte[] requestBytes = Hex.decode(requestHexString);

        final BindResponse response = new BindResponse();

        response.setPDUType(PDUType.BIND_ACK);
        response.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        response.setMaxXmitFrag((short) 2048);
        response.setMaxRecvFrag((short) 2048);

        final String responseHexString = response.toHexString();
        final byte[] responseBytes = Hex.decode(responseHexString);

        final RPCTransport transport = new TestRPCTransport() {
            @Override
            public int transact(final byte[] packetOut, final byte[] packetIn)
                throws IOException {
                assertArrayEquals(requestBytes, packetOut);
                assertArrayEquals(new byte[getMaxXmitFrag()], packetIn);
                System.arraycopy(responseBytes, 0, packetIn, 0, responseBytes.length);
                return responseBytes.length;
            }
        };

        assertNotEquals(2048, transport.getMaxXmitFrag());
        assertNotEquals(2048, transport.getMaxRecvFrag());

        transport.bind(Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);

        assertEquals(2048, transport.getMaxXmitFrag());
        assertEquals(2048, transport.getMaxRecvFrag());
    }

    @Test
    public void bindNAK()
        throws IOException {
        final BindRequest request = new BindRequest(16384, 16384, Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);

        request.setPDUType(PDUType.BIND);
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));

        final String requestHexString = request.toHexString();
        final byte[] requestBytes = Hex.decode(requestHexString);

        final BindResponse response = new BindResponse();

        response.setPDUType(PDUType.BIND_NAK);
        response.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));

        final String responseHexString = response.toHexString();
        final byte[] responseBytes = Hex.decode(responseHexString);

        final RPCTransport transport = new TestRPCTransport() {
            @Override
            public int transact(final byte[] packetOut, final byte[] packetIn)
                throws IOException {
                assertArrayEquals(requestBytes, packetOut);
                assertArrayEquals(new byte[getMaxXmitFrag()], packetIn);
                System.arraycopy(responseBytes, 0, packetIn, 0, responseBytes.length);
                return responseBytes.length;
            }
        };

        thrown.expect(IOException.class);
        thrown.expectMessage("BIND winreg interface (338cd001-2244-31f1-aaaa-900038001003:v1.0) failed.");

        transport.bind(Interface.WINREG_V1_0, Interface.NDR_32BIT_V2);
    }

    @Test
    public void call()
        throws IOException {
        final Request request = new Request();

        request.setOpNum((short) 1);
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        request.setStub(new byte[0]);

        final String requestHexString = request.toHexString();
        final byte[] requestBytes = Hex.decode(requestHexString);

        final Response response = new Response();

        response.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        response.setStub(new byte[0]);

        final String responseHexString = response.toHexString();
        final byte[] responseBytes = Hex.decode(responseHexString);

        final RPCTransport transport = new TestRPCTransport() {
            @Override
            public int transact(final byte[] packetOut, final byte[] packetIn)
                throws IOException {
                assertArrayEquals(requestBytes, packetOut);
                assertArrayEquals(new byte[getMaxXmitFrag()], packetIn);
                System.arraycopy(responseBytes, 0, packetIn, 0, responseBytes.length);
                return responseBytes.length;
            }
        };

        @SuppressWarnings("unchecked")
        final RequestCall<RequestResponse> requestCall = mock(RequestCall.class);
        final RequestResponse requestResponse = mock(RequestResponse.class);

        when(requestCall.getOpNum()).thenReturn((short) 1);
        when(requestCall.getStub()).thenReturn(new byte[0]);
        when(requestCall.getResponseObject()).thenReturn(requestResponse);

        final RequestResponse callResponse = transport.call(requestCall);

        assertEquals(requestResponse, callResponse);

        verify(requestCall, times(1)).getOpNum();
        verify(requestCall, times(1)).getStub();
        verify(requestCall, times(1)).getResponseObject();
        verify(requestResponse, times(1)).unmarshal(any(PacketInput.class));
        verifyNoMoreInteractions(requestCall, requestResponse);
    }

    @Test
    public void callWithStub()
        throws IOException {
        final Request request = new Request();

        request.setOpNum((short) 1);
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        request.setStub(new byte[0]);

        final String requestHexString = request.toHexString();
        final byte[] requestBytes = Hex.decode(requestHexString);

        final Response response = new Response();

        response.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        response.setStub(new byte[] { 0x67, 0x45, 0x23, 0x01 });

        final String responseHexString = response.toHexString();
        final byte[] responseBytes = Hex.decode(responseHexString);

        final RPCTransport transport = new TestRPCTransport() {
            @Override
            public int transact(final byte[] packetOut, final byte[] packetIn)
                throws IOException {
                assertArrayEquals(requestBytes, packetOut);
                assertArrayEquals(new byte[getMaxXmitFrag()], packetIn);
                System.arraycopy(responseBytes, 0, packetIn, 0, responseBytes.length);
                return responseBytes.length;
            }
        };

        @SuppressWarnings("unchecked")
        final RequestCall<RequestResponse> requestCall = mock(RequestCall.class);
        final RequestResponse requestResponse = mock(RequestResponse.class);

        when(requestCall.getOpNum()).thenReturn((short) 1);
        when(requestCall.getStub()).thenReturn(new byte[0]);
        when(requestCall.getResponseObject()).thenReturn(requestResponse);

        doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
                throws Throwable
            {
                final Object[] arguments = invocation.getArguments();
                final PacketInput packetIn = (PacketInput) arguments[0];
                assertEquals(19088743, packetIn.readInt());
                return null;
            }
        }).when(requestResponse).unmarshal(any(PacketInput.class));

        final RequestResponse callResponse = transport.call(requestCall);

        assertEquals(requestResponse, callResponse);

        verify(requestCall, times(1)).getOpNum();
        verify(requestCall, times(1)).getStub();
        verify(requestCall, times(1)).getResponseObject();
        verify(requestResponse, times(1)).unmarshal(any(PacketInput.class));
        verifyNoMoreInteractions(requestCall, requestResponse);
    }

    @Test
    public void callWithMultiStub()
        throws IOException {
        final Request request = new Request();

        request.setOpNum((short) 1);
        request.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        request.setStub(new byte[0]);

        final String requestHexString = request.toHexString();
        final byte[] requestBytes = Hex.decode(requestHexString);

        final Queue<byte[]> responses = new LinkedList<>();
        final Response response1 = new Response();
        final Response response2 = new Response();
        final Response response3 = new Response();

        response1.setPFCFlags(EnumSet.of(PFCFlag.FIRST_FRAGMENT));
        response2.setPFCFlags(EnumSet.noneOf(PFCFlag.class));
        response3.setPFCFlags(EnumSet.of(PFCFlag.LAST_FRAGMENT));
        response1.setStub(new byte[] { 0x67, 0x45, 0x23, 0x01 });
        response2.setStub(new byte[0]);
        response3.setStub(new byte[] { 0x01, 0x23, 0x45, 0x67 });

        final String response1HexString = response1.toHexString();
        final String response2HexString = response2.toHexString();
        final String response3HexString = response3.toHexString();
        final byte[] response1Bytes = Hex.decode(response1HexString);
        final byte[] response2Bytes = Hex.decode(response2HexString);
        final byte[] response3Bytes = Hex.decode(response3HexString);

        responses.add(response1Bytes);
        responses.add(response2Bytes);
        responses.add(response3Bytes);

        final RPCTransport transport = new TestRPCTransport() {
            @Override
            public int transact(final byte[] packetOut, final byte[] packetIn)
                throws IOException {
                assertArrayEquals(requestBytes, packetOut);
                assertArrayEquals(new byte[getMaxXmitFrag()], packetIn);
                return read(packetIn);
            }

            @Override
            public int read(final byte[] packetIn) {
                final byte[] responseBytes = responses.poll();
                System.arraycopy(responseBytes, 0, packetIn, 0, responseBytes.length);
                return responseBytes.length;
            }
        };

        @SuppressWarnings("unchecked")
        final RequestCall<RequestResponse> requestCall = mock(RequestCall.class);
        final RequestResponse requestResponse = mock(RequestResponse.class);

        when(requestCall.getOpNum()).thenReturn((short) 1);
        when(requestCall.getStub()).thenReturn(new byte[0]);
        when(requestCall.getResponseObject()).thenReturn(requestResponse);

        doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocation)
                throws Throwable
            {
                final Object[] arguments = invocation.getArguments();
		final PacketInput packetIn = (PacketInput) arguments[0];
		assertEquals(19088743, packetIn.readInt());
		assertEquals(1732584193, packetIn.readInt());
		return null;
            }
        }).when(requestResponse).unmarshal(any(PacketInput.class));

        final RequestResponse callResponse = transport.call(requestCall);

        assertEquals(requestResponse, callResponse);

        verify(requestCall, times(1)).getOpNum();
        verify(requestCall, times(1)).getStub();
        verify(requestCall, times(1)).getResponseObject();
        verify(requestResponse, times(1)).unmarshal(any(PacketInput.class));
        verifyNoMoreInteractions(requestCall, requestResponse);
    }

    @Test
    public void getCallID() {
        final RPCTransport transport = new TestRPCTransport();
        assertEquals(0, transport.getCallID());
        assertEquals(1, transport.getCallID());
        assertEquals(2, transport.getCallID());
    }

    @Test
    public void getMaxXmitFrag() {
        final RPCTransport transport = new TestRPCTransport();
        assertEquals(RPCTransport.DEFAULT_MAX_XMIT_FRAG, transport.getMaxXmitFrag());
    }

    @Test
    public void getMaxRecvFrag() {
        final RPCTransport transport = new TestRPCTransport();
        assertEquals(RPCTransport.DEFAULT_MAX_RECV_FRAG, transport.getMaxRecvFrag());
    }

    @Test
    public void setMaxXmitFrag() {
        final RPCTransport transport = new TestRPCTransport();
        assertNotEquals(4280, transport.getMaxXmitFrag());
        transport.setMaxXmitFrag(4280);
        assertEquals(4280, transport.getMaxXmitFrag());
    }

    @Test
    public void setMaxRecvFrag() {
        final RPCTransport transport = new TestRPCTransport();
        assertNotEquals(4280, transport.getMaxRecvFrag());
        transport.setMaxRecvFrag(4280);
        assertEquals(4280, transport.getMaxRecvFrag());
    }

    private class TestRPCTransport extends RPCTransport {
        @Override
        public int transact(final byte[] packetOut, final byte[] packetIn)
            throws IOException {
            return 0;
        }

        @Override
        public void write(final byte[] packetOut)
            throws IOException {
        }

        @Override
        public int read(final byte[] packetIn)
            throws IOException {
            return 0;
        }
    };
}
