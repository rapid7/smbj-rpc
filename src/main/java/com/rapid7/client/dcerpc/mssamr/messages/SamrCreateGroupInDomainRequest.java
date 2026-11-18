package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * SamrCreateGroupInDomain (Opnum 10)
 * The SamrCreateGroupInDomain method creates a group object within a domain.
 * 
 * <pre>
 *   long SamrCreateGroupInDomain(
 *   [in] SAMPR_HANDLE DomainHandle,
 *   [in] PRPC_UNICODE_STRING Name,
 *   [in] unsigned long DesiredAccess,
 *   [out] SAMPR_HANDLE* GroupHandle,
 *   [out] unsigned long* RelativeId
 *   );
 * </pre>
 * 
 * DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 * Name: The value to use as the name of the group. Details on how this value maps to the data model are provided later in this section.
 * DesiredAccess: The access requested on the GroupHandle on output. See section 2.2.1.5 for a listing of possible values.
 * GroupHandle: An RPC context handle, as specified in section 2.2.3.2.
 * RelativeId: The RID of the newly created group.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * This method MUST be processed per the specifications in section 3.1.5.4.1, using a group type of GROUP_TYPE_SECURITY_ACCOUNT and using access mask values from section 2.2.1.5.
 * 
 *  @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/175c1cf9-4fa2-4837-9e5b-bb1f0f950bee">SamrCreateUser2InDomain (Opnum 50)</a>
 */
public class SamrCreateGroupInDomainRequest  extends RequestCall<SamrCreateGroupInDomainResponse> {
	public static short OP_NUM =  10;
    private byte[] domainHandle;
    private RPCUnicodeString.NonNullTerminated name;
    private int desiredAccess;
	
	public byte[] getDomainHandle() { return domainHandle; }
	public RPCUnicodeString.NonNullTerminated getName() { return name; }
	public int getDesiredAccess() { return desiredAccess; }
	
	public SamrCreateGroupInDomainRequest(final byte[] domainHandle, final RPCUnicodeString.NonNullTerminated name, final int desiredAccess) {
		super(OP_NUM);
		this.domainHandle = domainHandle;
		this.name = name;
		this.desiredAccess = desiredAccess;
	}
	@Override
	public SamrCreateGroupInDomainResponse getResponseObject() { 
		return new SamrCreateGroupInDomainResponse();
	}
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
