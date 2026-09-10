package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * SamrRemoveMemberFromAlias (Opnum 32)
 *
 * <blockquote><pre>The SamrRemoveMemberFromAlias method removes a member from an alias.
 *
 * 	long SamrRemoveMemberFromGroup(
 * 		[in] SAMPR_HANDLE GroupHandle,
 * 		[in] PRPC_SID MemberId
 * 		);
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 * MemberId: The SID of an account to remove from the alias.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The server MUST return an error if AliasHandle.HandleType is not equal to "Alias".
 *     2. AliasHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *     3. All database operations MUST occur in a single transaction.
 *     4. Let A be the alias object referenced by the AliasHandle.Object.
 *     5. If A's member attribute does not have a dsname value that references the object whose objectSid is MemberId, the server MUST return an error.
 *     6. A's member attribute MUST be updated to remove a dsname value that references the object with the objectSid value MemberId.
 *     </pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7e994029-67a2-44fd-8439-7640fe5a376b">SamrRemoveMemberFromAlias (Opnum 32)</a>
 */
public class SamrRemoveMemberFromAliasResponse extends RequestResponse {
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		//STATUS_ERROR_CODE
	}
}
