package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * SamrCreateAliasInDomain (Opnum 14) The SamrCreateAliasInDomain method creates an alias.
 *
 * <pre>
 long SamrCreateAliasInDomain(
   [in] SAMPR_HANDLE DomainHandle,
   [in] PRPC_UNICODE_STRING AccountName,
   [in] unsigned long DesiredAccess,
   [out] SAMPR_HANDLE* AliasHandle,
   [out] unsigned long* RelativeId
 );
 * </pre>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7e505875-e44f-4f9d-922b-7ff3871c752d">SamrCreateAliasInDomain (Opnum 14)</a>
 */
public class SamrCreateAliasInDomainRequest extends RequestCall<SamrCreateAliasInDomainResponse> {
	public static short OP_NUM = 14;
	private byte[] domainHandle;
	private RPCUnicodeString.NonNullTerminated name;
	private int desiredAccess;

	public byte[] getDomainHandle() { return domainHandle; }
	public RPCUnicodeString.NonNullTerminated getName() { return name; }
	public int getDesiredAccess() { return desiredAccess; }
	public SamrCreateAliasInDomainRequest(final byte[] domainHandle, final RPCUnicodeString.NonNullTerminated name, final int desiredAccess) {
		super(OP_NUM);
		this.domainHandle = domainHandle;
		this.name = name;
		this.desiredAccess = desiredAccess;
	}
	@Override
	public SamrCreateAliasInDomainResponse getResponseObject() { return new SamrCreateAliasInDomainResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		packetOut.write(this.domainHandle);
		name.marshalPreamble(packetOut);
		name.marshalEntity(packetOut);
		name.marshalDeferrals(packetOut);
		packetOut.align(Alignment.FOUR);
		packetOut.writeInt(desiredAccess);
	}
}
