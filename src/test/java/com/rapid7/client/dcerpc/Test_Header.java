package com.rapid7.client.dcerpc;

import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertEquals;

public class Test_Header {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullPDUType() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("pduType invalid: null");

        new Header(null, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
    }

    @Test
    public void constructorNullPFCFlags() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("pfcFlags invalid: null");

        new Header(PDUType.ACK, null);
    }

    @Test
    public void parsePacket()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void parsePacketAndMarshal()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);
        final byte[] headerBytes = header.marshal(10);
        final String headerHexStr = Hex.toHexString(headerBytes);

        assertEquals("0500070310000000100000000a000000", headerHexStr);
    }

    @Test
    public void parseMajorVersion()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(5, header.getMajorVersion());
    }

    @Test
    public void parseMinorVersion()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(0, header.getMinorVersion());
    }

    @Test
    public void parsePDUType()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(PDUType.ACK, header.getPDUType());
    }

    @Test
    public void parsePFCFlags()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT), header.getPFCFlags());
    }

    @Test
    public void parseNDR()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(0x10, header.getNDR());
    }

    @Test
    public void parseFragmentLength()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(testVectorBytes.length, header.getFragmentLength());
    }

    @Test
    public void parseAuthenticationVerifierLength()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(0, header.getAuthenticationVerifierLength());
    }

    @Test
    public void parseCallID()
        throws TransportException {
        final byte[] testVectorBytes = Hex.decode("0500070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        final Header header = new Header(testVectorBuffer);

        assertEquals(10, header.getCallID());
    }

    @Test
    public void badMajorVersion()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Version mismatch: 1.0 != 5.0");

        final byte[] testVectorBytes = Hex.decode("0100070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void badMinorVersion()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Version mismatch: 5.1 != 5.0");

        final byte[] testVectorBytes = Hex.decode("0501070310000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void badICR()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Integer and Character representation mismatch: 0");

        final byte[] testVectorBytes = Hex.decode("0500070300000000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void badFPR()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Floating-Point representation mismatch: 16");

        final byte[] testVectorBytes = Hex.decode("0500070310100000100000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void badFragmentLength()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Packet incomplete: 17 > 16");

        final byte[] testVectorBytes = Hex.decode("0500070310000000110000000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void badAuthLength()
        throws TransportException {
        thrown.expect(TransportException.class);
        thrown.expectMessage("Packet incomplete: 16 + 1 > 16");

        final byte[] testVectorBytes = Hex.decode("0500070310000000100001000a000000");
        final ByteBuffer testVectorBuffer = ByteBuffer.wrap(testVectorBytes);
        new Header(testVectorBuffer);
    }

    @Test
    public void getMajorVersion() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(5, header.getMajorVersion());
    }

    @Test
    public void getMinorVersion() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(0, header.getMinorVersion());
    }

    @Test
    public void getPDUType() {
        for (final PDUType pduType : PDUType.values()) {
            final Header header = new Header(pduType, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
            assertEquals(pduType, header.getPDUType());
        }

        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(PDUType.ACK, header.getPDUType());
    }

    @Test
    public void getPFCFlags() {
        for (final PFCFlag pfcFlags : PFCFlag.values()) {
            final Header header = new Header(PDUType.ACK, EnumSet.of(pfcFlags));
            assertEquals(EnumSet.of(pfcFlags), header.getPFCFlags());
        }

        final Header allOfHeader = new Header(PDUType.ACK, EnumSet.allOf(PFCFlag.class));
        assertEquals(EnumSet.allOf(PFCFlag.class), allOfHeader.getPFCFlags());

        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT), header.getPFCFlags());
    }

    @Test
    public void getNDR() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(0x10, header.getNDR());
    }

    @Test
    public void getFragmentLength() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(0, header.getFragmentLength());
    }

    @Test
    public void getFragmentLengthAfterMarshal() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        header.marshal(0);
        assertEquals(16, header.getFragmentLength());
    }

    @Test
    public void getAuthenticationVerifierLength() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(0, header.getAuthenticationVerifierLength());
    }

    @Test
    public void getCallID() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        assertEquals(0, header.getCallID());
    }

    @Test
    public void getCallIDAfterMarshal() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        header.marshal(20);
        assertEquals(20, header.getCallID());
    }

    @Test
    public void marshal() {
        final Header header = new Header(PDUType.ACK, EnumSet.of(PFCFlag.FIRST_FRAGMENT, PFCFlag.LAST_FRAGMENT));
        final byte[] headerBytes = header.marshal(10);
        final String headerHexStr = Hex.toHexString(headerBytes);

        assertEquals("0500070310000000100000000a000000", headerHexStr);
    }
}
