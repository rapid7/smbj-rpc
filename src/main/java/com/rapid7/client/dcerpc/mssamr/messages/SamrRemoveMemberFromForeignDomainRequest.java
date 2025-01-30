package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.DomainHandle;
import com.rapid7.client.dcerpc.objects.RPCSID;

/**
 * SamrRemoveMemberFromForeignDomain (Opnum 45)
 *
 * <blockquote><pre>The SamrRemoveMemberFromForeignDomain method removes a member from all aliases.
 *
 * 	long SamrRemoveMemberFromForeignDomain(
 * 		[in] SAMPR_HANDLE DomainHandle,
 * 		[in] PRPC_SID MemberId
 * 		);
 *
 * DomainHandle: An RPC context handle, as specified in section 2.2.3.2, representing a domain object.
 * MemberSid: The SID to remove from the membership.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The server MUST return an error if DomainHandle.HandleType is not equal to "Domain".
 *     2. DomainHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *     3. All database operations MUST occur in a single transaction.
 *     4. If the server is not a domain controller, for all alias objects in the domain referenced by DomainHandle.Object, the server MUST remove any member value that references the object with the objectSid attribute value of MemberSid.
 *     5. If the server is a domain controller, the server MUST return STATUS_SUCCESS without making any modifications to any alias objects.
 *     </pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/03afc843-584d-473b-834a-3f5a1ac86cce">SamrRemoveMemberFromForeignDomain (Opnum 45)</a>
 */
public class SamrRemoveMemberFromForeignDomainRequest extends RequestCall<SamrRemoveMemberFromAliasResponse> {
	public static final short OP_NUM = 45;
	// <NDR: fixed array> [in] SAMPR_HANDLE DomainHandle
	private DomainHandle domainHandle;
	// <NDR: PRPC_SID> [in] MemberId
	private RPCSID memberId;

	public RPCSID getMemberId() { return memberId; }
	public DomainHandle getAliasHandle() { return domainHandle; }
	public SamrRemoveMemberFromForeignDomainRequest(DomainHandle domainHandle, RPCSID memberId) {
		super(OP_NUM);
		this.domainHandle = domainHandle;
		this.memberId = memberId;
	}
	@Override
	public SamrRemoveMemberFromAliasResponse getResponseObject() { return new SamrRemoveMemberFromAliasResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] AliasHandle UserHandle
		packetOut.write(domainHandle.getBytes());
		// <NDR: PRPC_SID> [in] MemberId
		memberId.marshalPreamble(packetOut);
		memberId.marshalEntity(packetOut);
		memberId.marshalDeferrals(packetOut);
	}
}
