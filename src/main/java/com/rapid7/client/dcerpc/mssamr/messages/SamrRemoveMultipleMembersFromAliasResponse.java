package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * SamrRemoveMultipleMembersFromAlias (Opnum 53)
 *
 * <blockquote><pre>The SamrRemoveMultipleMembersFromAlias method removes multiple members from an alias.
 *
 * 	long SamrRemoveMemberFromAlias(
 * 		[in] SAMPR_HANDLE AliasHandle,
 * 		[in] PSAMPR_PSID_ARRAY MembersBuffer
 * 		);
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 * MembersBuffer: A structure containing a list of SIDs to remove from the alias's membership list.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a
 * method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * The server MUST behave as with N successive message calls to SamrRemoveMemberFromAlias, once for each SID value in MembersBuffer,
 * where MembersBuffer contains N elements. The server MUST ignore the processing error triggered by a value not existing in the member
 * attribute's values and abort the request on any other processing error. </pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7b2455d2-3f13-44a7-9969-50c240562c42">SamrRemoveMultipleMembersFromAlias (Opnum 53)</a>
 */
public class SamrRemoveMultipleMembersFromAliasResponse extends RequestResponse {
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		//STATUS_ERROR_CODE
	}
}
