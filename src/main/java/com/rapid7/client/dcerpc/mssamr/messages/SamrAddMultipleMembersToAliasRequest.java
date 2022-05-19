package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRPSIDArray;

/**
 * SamrAddMultipleMembersToAlias (Opnum 52)
 *
 * <blockquote><pre>The SamrAddMultipleMembersToAlias method adds multiple members to an alias.
 *
 * 	long SamrAddMultipleMembersToAlias(
 * 		[in] SAMPR_HANDLE AliasHandle,
 * 		[in] PSAMPR_PSID_ARRAY MembersBuffer
 * 	 );
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 * MembersBuffer: A structure containing a list of SIDs to add as members to the alias.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles
 * created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * The server MUST behave as with N successive message calls to SamrAddMemberToAlias, once for each SID value in MembersBuffer,
 * where MembersBuffer contains N elements. The server MUST ignore the processing error of a member value already being present in the member attribute and abort the request on any other processing error.</pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/6d981290-a203-4080-94b0-4a26de22d8c5">SamrAddMultipleMembersToAlias (Opnum 52)</a>
 */
public class SamrAddMultipleMembersToAliasRequest extends RequestCall<SamrAddMultipleMembersToAliasResponse> {
	public static final short OP_NUM = 52;
	// <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
	private AliasHandle aliasHandle;
	// <NDR: PRPC_SID> [in] MembersBuffer
	private SAMPRPSIDArray sids;

	public SAMPRPSIDArray getMemberIds() { return sids; }
	public AliasHandle getAliasHandle() { return aliasHandle; }
	public SamrAddMultipleMembersToAliasRequest(AliasHandle aliasHandle, SAMPRPSIDArray sids) {
		super(OP_NUM);
		this.aliasHandle = aliasHandle;
		this.sids = sids;
	}
	@Override
	public SamrAddMultipleMembersToAliasResponse getResponseObject() { return new SamrAddMultipleMembersToAliasResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] AliasHandle UserHandle
		packetOut.write(aliasHandle.getBytes());
		// <NDR: PRPC_SID> [in] MembersBuffer
		packetOut.writeMarshallable(this.sids);
	}
}
