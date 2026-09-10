package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.dto.UserHandle;

/**
 * SamrCreateUser2InDomain (50)
 * creates User in domain with specific account type (workstation, user..)
 *
 * <pre>
 *  long SamrCreateUser2InDomain(
 *  [in] SAMPR_HANDLE DomainHandle,
 *  [in] PRPC_UNICODE_STRING Name,
 *  [in] unsigned long AccountType,
 *  [in] unsigned long DesiredAccess,
 *  [out] SAMPR_HANDLE* UserHandle,
 *  [out] unsigned long* GrantedAccess,
 *  [out] unsigned long* RelativeId
 *  );
 * </pre>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/a98d7fbb-1735-4fbf-b41a-ef363c899002">SamrCreateUser2InDomain (Opnum 50)</a>
 */
public class SamrCreateUser2InDomainResponse extends RequestResponse {
	//  [out] SAMPR_HANDLE* UserHandle
	private UserHandle userHandle;
	//  [out] unsigned long* GrantedAccess
	private int grantedAccess;
	//  [out] unsigned long* RelativeId
	private int relativeId;

	public UserHandle getUserHandle() { return userHandle; }
	public int getGrantedAccess() { return grantedAccess; }
	public int getRelativeId() { return relativeId; }
	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		byte[] userHandleBytes = new byte[20];
		packetIn.readRawBytes(userHandleBytes);
		this.userHandle = new UserHandle(userHandleBytes);
		this.grantedAccess = packetIn.readInt();
		this.relativeId = packetIn.readInt();
	}
	@Override
	public String toString() {
		return String.format(
				"SamrCreateUser2InDomainResponse {UserHandle:'%s', grantedAccess:'%d', relativeId:'%d'}",
				getUserHandle(), getGrantedAccess(), getRelativeId()
		);
	}
}
