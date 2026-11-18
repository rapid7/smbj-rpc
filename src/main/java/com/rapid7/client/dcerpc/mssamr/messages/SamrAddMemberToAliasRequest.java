package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;
import com.rapid7.client.dcerpc.objects.RPCSID;

/**
 * SamrAddMemberToAlias (Opnum 31)
 *
 * <blockquote><pre>The SamrSetInformationDomain method updates attributes on a domain object.
 * 	long SamrAddMemberToGroup(
 * 		[in] SAMPR_HANDLE GroupHandle,
 * 		[in] PRPC_SID MemberId
 * 		);
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 * MemberId: The SID of an account to add to the alias.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The server MUST return an error if AliasHandle.HandleType is not equal to "Alias".
 *     2. AliasHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1 Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *     3. All database operations MUST occur in a single transaction.
 *     4. Let A be the alias referenced by AliasHandle.Object.
 *     5. If the domain prefix of MemberId is the same domain prefix as the account domain and there is no object whose objectSid attribute is MemberId, the server MUST return an error.
 *     6. If A's member attribute already has a dsname value that references the object whose objectSid is MemberId, the server MUST return an error.
 *     7. A's member attribute MUST be updated to add a dsname value that references the object with the objectSid value MemberId.
 *     </pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9a5d2c35-e84b-4e59-b7b0-96c6fa0fc8d7">SamrAddMemberToAlias (Opnum 31)</a>
 */
public class SamrAddMemberToAliasRequest extends RequestCall<SamrAddMemberToAliasResponse> {
	public static final short OP_NUM = 31;
	// <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
	private AliasHandle aliasHandle;
	// <NDR: PRPC_SID> [in] MemberId
	private RPCSID memberId;

	public RPCSID getMemberId() { return memberId; }
	public AliasHandle getAliasHandle() { return aliasHandle; }
	public SamrAddMemberToAliasRequest(final AliasHandle aliasHandle, RPCSID memberId) {
		super(OP_NUM);
		this.aliasHandle = aliasHandle;
		this.memberId = memberId;
	}
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] AliasHandle UserHandle
		packetOut.write(aliasHandle.getBytes());
		// <NDR: PRPC_SID> [in] MemberId
		memberId.marshalPreamble(packetOut);
		memberId.marshalEntity(packetOut);
		memberId.marshalDeferrals(packetOut);
	}
	@Override
	public SamrAddMemberToAliasResponse getResponseObject() { return new SamrAddMemberToAliasResponse(); }
}
