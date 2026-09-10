package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * SamrRidToSid (Opnum 65)
 *
 * <blockquote><pre>The SamrRidToSid method obtains the SID of an account, given a RID.
 *
 * 	long SamrRidToSid(
 * 		[in] SAMPR_HANDLE ObjectHandle,
 * 		[in] unsigned long Rid,
 * 		[out] PRPC_SID* Sid
 * 	);
 *
 * ObjectHandle: An RPC context handle, as specified in section 2.2.3.2. The message processing shown later in this section contains details on which types of ObjectHandle are accepted by the server.
 * Rid: A RID of an account.
 * Sid: The SID of the account referenced by Rid.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The ObjectHandle.HandleType MUST be "Domain", "User", "Group", or "Alias".
 *     2. The output parameter Sid MUST be set to a SID whose domain SID prefix is equal to the domain SID prefix of the objectSid attribute of the object identified by ObjectHandle, and whose RID suffix is equal to the Rid parameter.
 *     </pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/00ff8192-a4f6-45ba-9f65-917e46b6a693">SamrRidToSid (Opnum 65)</a>
 */
public class SamrRidToSidRequest extends RequestCall<SamrRidToSidResponse> {
	public static final short OP_NUM = 65;
	// <NDR: fixed array> [in] SAMPR_HANDLE ObjectHandle
	private ContextHandle objectHandle;
	// <NDR: unsigned long> [in] Rid
	private int rid;

	public int getRid() { return rid; }
	public ContextHandle getObjectHandle() { return objectHandle; }
	public SamrRidToSidRequest(ContextHandle aliasHandle, int memberId) {
		super(OP_NUM);
		this.objectHandle = aliasHandle;
		this.rid = memberId;
	}
	@Override
	public SamrRidToSidResponse getResponseObject() { return new SamrRidToSidResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] ObjectHandle
		packetOut.write(objectHandle.getBytes());
		// <NDR: unsigned long> [in] Rid
		packetOut.writeInt(this.rid);
	}
}
