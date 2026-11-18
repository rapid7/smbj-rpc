package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * SamrCreateUser2InDomain (50) - creates User in domain with specific account type (workstation, user..)
 * 
 * <pre>
 *  long SamrCreateUser2InDomain(
 *  [in] SAMPR_HANDLE DomainHandle,
 *  [in] PRPC_UNICODE_STRING Name,
 *  [in] unsigned long AccountType,
 *  [in] unsigned long DesiredAccess,
 *  [out] SAMPR_HANDLE* UserHandle,
 *  [out] unsigned long* GrantedAccess,
 *  [out] unsigned long* RelativeId
 *  );
 * </pre>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/a98d7fbb-1735-4fbf-b41a-ef363c899002">SamrCreateUser2InDomain (Opnum 50)</a>
 */
public class SamrCreateUser2InDomainRequest extends RequestCall<SamrCreateUser2InDomainResponse> {
	public static short OP_NUM =  50;
    private byte[] domainHandle;
    private RPCUnicodeString.NonNullTerminated name;
    private int accountType;
    private int desiredAccess;
	
	public byte[] getDomainHandle() { return domainHandle; }
	public RPCUnicodeString.NonNullTerminated getName() { return name; }
	public int getAccountType() { return accountType; }
	public int getDesiredAccess() { return desiredAccess; }
	
	public SamrCreateUser2InDomainRequest(final byte[] domainHandle, final RPCUnicodeString.NonNullTerminated name, final int accountType, final int desiredAccess) {
		super(OP_NUM);
		this.domainHandle = domainHandle;
		this.name = name;
		this.accountType = accountType;
		this.desiredAccess = desiredAccess;
	}
	@Override
	public SamrCreateUser2InDomainResponse getResponseObject() { 
		return new SamrCreateUser2InDomainResponse();
	}
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(this.domainHandle);
        name.marshalPreamble(packetOut);
        name.marshalEntity(packetOut);
        name.marshalDeferrals(packetOut);
        packetOut.align(Alignment.FOUR);
        packetOut.writeInt(accountType);
        packetOut.writeInt(desiredAccess);
	 }
}
