package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCShortBlob;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * SamrSetDSRMPassword (Opnum 66)
 * <blockquote><pre>The SamrSetDSRMPassword method sets a local recovery password.
 *
 *      long SamrSetDSRMPassword(
		   [in] handle_t BindingHandle,
		   [in, unique] PRPC_UNICODE_STRING Unused,
		   [in] unsigned long UserId,
		   [in, unique] PENCRYPTED_NT_OWF_PASSWORD EncryptedNtOwfPassword
		 );
 *
 * BindingHandle: An RPC binding handle parameter, as specified in [C706] section 1.
 * Unused: A string value. This value is not used in the protocol and is ignored by the server.
 * UserId: A RID of a user account. See the message processing later in this section for details on restrictions on this value.
 * EncryptedNtOwfPassword: The NT hash of the new password (as presented by the client) encrypted according to the specification of ENCRYPTED_NT_OWF_PASSWORD, where the key is the UserId.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The client MUST be a member of the Administrators alias, which is an alias object with the security identifier (SID) S-1-5-32-544.
 *     2. On a non-DC configuration, the server MUST return an error code.
 *     3. The server MAY<65> enforce parameter checks on the UserId parameter.
 *     4. The server MAY<66> decrypt EncryptedNtOwfPassword using UserId as a key and use the result to store the password of a local recovery account.
 *     </pre></blockquote>
 *
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9bcad7d2-b8e1-4d28-a033-86040b7b3ce9">SamrSetDSRMPassword (Opnum 66)</a>
 */
public class SamrSetDSRMPasswordRequest extends RequestCall<SamrSetDSRMPasswordResponse> {
	public static final short OP_NUM = 66;
	// <NDR: fixed array> [in] BindingHandle DomainHandle
	private ContextHandle handle;
	// <NDR: struct> RPC_UNICODE_STRING unusedString;
	private RPCUnicodeString.NonNullTerminated unusedString;
	// <NDR: unsigned long> [in] UserId
	private int userId;
	// <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
	private RPCShortBlob encryptedNtOwfPassword;

	public SamrSetDSRMPasswordRequest(ContextHandle handle, int userId, RPCShortBlob encryptedNtOwfPassword) {
		super(OP_NUM);
		this.unusedString = new RPCUnicodeString.NonNullTerminated();
		//		unusedString = RPCUnicodeString.NonNullTerminated.of("test");
		this.handle = handle;
		this.userId = userId;
		this.encryptedNtOwfPassword = encryptedNtOwfPassword;
	}
	@Override
	public SamrSetDSRMPasswordResponse getResponseObject() { return new SamrSetDSRMPasswordResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: struct> RPC_UNICODE_STRING unusedString;
		packetOut.writeInt(0); //unused string
		// <NDR: unsigned long> [in] UserId
		packetOut.writeInt(userId);
		// <NDR: struct> RPC_SHORT_BLOB NtOwfPassword;
		packetOut.writeInt(123); //hashHandle
		//		encryptedNtOwfPassword.marshalDeferrals(packetOut);
		//		encryptedNtOwfPassword.marshalEntity(packetOut);
		for (int b : encryptedNtOwfPassword.getBuffer())
			packetOut.writeShort(b);
	}
}
