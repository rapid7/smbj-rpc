package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;

/**
 * SamrDeleteAlias (Opnum 30)
 *
 * <blockquote><pre>
 *    The SamrDeleteAlias method removes an alias object.
 *
 *    long SamrDeleteAlias(
 *       [in, out] SAMPR_HANDLE* AliasHandle
 *       );
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing an alias object.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The server MUST return an error if AliasHandle.HandleType is not equal to "Alias".
 *     2. AliasHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *     3. All database operations MUST occur in a single transaction.
 *     4. Let A be the alias object referenced by AliasHandle.Object.
 *     5. If the RID of A's objectSid attribute value is less than 1000, an error MUST be returned.
 *     6. In the DC configuration, if A is a parent to another object, an error MUST be returned.<56>
 *     7. A MUST be removed from the database.
 *     8. The server MUST delete the SamContextHandle ADM element (section 3.1.1.10) represented by AliasHandle, and then MUST return 0 for the value of AliasHandle and a return code of STATUS_SUCCESS.
 *
 * @see  * <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/ecb88df1-84d1-48c8-b49c-d3f444943100">SamrDeleteAlias (Opnum 30)</a>
 */
public class SamrDeleteAliasResponse extends RequestResponse {
	// <NDR: fixed array> [in,out] SAMPR_HANDLE AliasHandle
	private AliasHandle aliasHandle;

	public AliasHandle getAliasHandle() { return aliasHandle; }
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		byte[] aliasHandleBytes = new byte[20];
		packetIn.readRawBytes(aliasHandleBytes);
		this.aliasHandle = new AliasHandle(aliasHandleBytes);
	}
}
