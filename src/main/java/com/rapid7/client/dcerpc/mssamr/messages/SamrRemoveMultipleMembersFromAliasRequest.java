package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRPSIDArray;

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
public class SamrRemoveMultipleMembersFromAliasRequest extends RequestCall<SamrRemoveMultipleMembersFromAliasResponse> {
	public static final short OP_NUM = 53;
	// <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
	private AliasHandle aliasHandle;
	// <NDR: PRPC_SID> [in] MembersBuffer
	private SAMPRPSIDArray sids;

	public SAMPRPSIDArray getMemberIds() { return sids; }
	public AliasHandle getAliasHandle() { return aliasHandle; }
	public SamrRemoveMultipleMembersFromAliasRequest(AliasHandle aliasHandle, SAMPRPSIDArray sids) {
		super(OP_NUM);
		this.aliasHandle = aliasHandle;
		this.sids = sids;
	}
	@Override
	public SamrRemoveMultipleMembersFromAliasResponse getResponseObject() { return new SamrRemoveMultipleMembersFromAliasResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] AliasHandle UserHandle
		packetOut.write(aliasHandle.getBytes());
		// <NDR: PRPC_SID> [in] MembersBuffer
		packetOut.writeMarshallable(this.sids);
	}
}
