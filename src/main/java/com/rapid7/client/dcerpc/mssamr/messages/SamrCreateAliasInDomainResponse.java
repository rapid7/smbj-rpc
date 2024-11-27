package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;

/**
 * SamrCreateAliasInDomain (Opnum 14) The SamrCreateAliasInDomain method creates an alias.
 *
 * <pre>
 long SamrCreateAliasInDomain(
   [in] SAMPR_HANDLE DomainHandle,
   [in] PRPC_UNICODE_STRING AccountName,
   [in] unsigned long DesiredAccess,
   [out] SAMPR_HANDLE* AliasHandle,
   [out] unsigned long* RelativeId
 );
 * </pre>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/7e505875-e44f-4f9d-922b-7ff3871c752d">SamrCreateAliasInDomain (Opnum 14)</a>
 */
public class SamrCreateAliasInDomainResponse extends RequestResponse {
	//  [out] SAMPR_HANDLE* AliasHandle
	private AliasHandle aliasHandle;
	//  [out] unsigned long* RelativeId
	private int relativeId;

	public AliasHandle getAliasHandle() { return aliasHandle; }
	public int getRelativeId() { return relativeId; }
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		byte[] aliasHandleBytes = new byte[20];
		packetIn.readRawBytes(aliasHandleBytes);
		this.aliasHandle = new AliasHandle(aliasHandleBytes);
		this.relativeId = packetIn.readInt();
	}
	@Override
	public String toString() {
		return String.format(
				"SamrCreateAliasInDomainResponse {AliasHandle:'%s', relativeId:'%d'}",
				getAliasHandle(), getRelativeId()
		);
	}
}
