package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.UserHandle;

/**
 * <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9699d8ca-e1a4-433c-a8c3-d7bebeb01476">SamrChangePasswordUser (Opnum 38)</a>">
 * <blockquote><pre>The SamrChangePasswordUser method changes the password of a user object.
 *
 *    long SamrChangePasswordUser(
 *   [in] SAMPR_HANDLE UserHandle,
 *   [in] unsigned char LmPresent,
 *   [in, unique] PENCRYPTED_LM_OWF_PASSWORD OldLmEncryptedWithNewLm,
 *   [in, unique] PENCRYPTED_LM_OWF_PASSWORD NewLmEncryptedWithOldLm,
 *   [in] unsigned char NtPresent,
 *   [in, unique] PENCRYPTED_NT_OWF_PASSWORD OldNtEncryptedWithNewNt,
 *   [in, unique] PENCRYPTED_NT_OWF_PASSWORD NewNtEncryptedWithOldNt,
 *   [in] unsigned char NtCrossEncryptionPresent,
 *   [in, unique] PENCRYPTED_NT_OWF_PASSWORD NewNtEncryptedWithNewLm,
 *   [in] unsigned char LmCrossEncryptionPresent,
 *   [in, unique] PENCRYPTED_LM_OWF_PASSWORD NewLmEncryptedWithNewNt
 * );
 *
 * UserHandle: An RPC context handle, as specified in section 2.2.3.2, representing a user object.
 * LmPresent: If this parameter is zero, the OldLmEncryptedWithNewLm and NewLmEncryptedWithOldLm fields MUST be ignored by the server; otherwise these fields MUST be processed.
 * OldLmEncryptedWithNewLm: The LM hash of the target user's existing password (as presented by the client) encrypted according to the specification of ENCRYPTED_LM_OWF_PASSWORD (section 2.2.3.3), where the key is the LM hash of the new password for the target user (as presented by the client in the NewLmEncryptedWithOldLm parameter).
 * NewLmEncryptedWithOldLm: The LM hash of the target user's new password (as presented by the client) encrypted according to the specification of ENCRYPTED_LM_OWF_PASSWORD, where the key is the LM hash of the existing password for the target user (as presented by the client in the OldLmEncryptedWithNewLm parameter).
 * NtPresent: If this parameter is zero, OldNtEncryptedWithNewNt and NewNtEncryptedWithOldNt MUST be ignored by the server; otherwise these fields MUST be processed.
 * OldNtEncryptedWithNewNt: The NT hash of the target user's existing password (as presented by the client) encrypted according to the specification of ENCRYPTED_NT_OWF_PASSWORD (section 2.2.3.3), where the key is the NT hash of the new password for the target user (as presented by the client).
 * NewNtEncryptedWithOldNt: The NT hash of the target user's new password (as presented by the client) encrypted according to the specification of ENCRYPTED_NT_OWF_PASSWORD, where the key is the NT hash of the existing password for the target user (as presented by the client).
 * NtCrossEncryptionPresent: If this parameter is zero, NewNtEncryptedWithNewLm MUST be ignored; otherwise, this field MUST be processed.
 * NewNtEncryptedWithNewLm: The NT hash of the target user's new password (as presented by the client) encrypted according to the specification of ENCRYPTED_NT_OWF_PASSWORD, where the key is the LM hash of the new password for the target user (as presented by the client).
 * LmCrossEncryptionPresent: If this parameter is zero, NewLmEncryptedWithNewNt MUST be ignored; otherwise, this field MUST be processed.
 * NewLmEncryptedWithNewNt: The LM hash of the target user's new password (as presented by the client) encrypted according to the specification of ENCRYPTED_LM_OWF_PASSWORD, where the key is the NT hash of the new password for the target user (as presented by the client).
 * The processing for this method is quite complex. To aid comprehension, a brief, non-normative description of the method's intent follows.
 * The method requires that the client presents both the NT and the LM hash of the new password (and will fail otherwise). However, because the old password might not be stored in either the NT or LM hash format on the receiver, and thus the new hash values cannot be decrypted using the old hash values, the method allows for the new NT and LM hashes to be "cross-encrypted" using the new LM or NT hash value (instead of the old values). As such, there are three combinations that can lead to successful processing, which are listed below.
 * 1.    NtPresent is nonzero, LmPresent is nonzero, and both the LM and NT hashes are present in the database. No "cross-encryption" is required. The cross-encryption–related parameters are ignored.
 * 2.    LmPresent is nonzero, NtCrossEncryptionPresent is nonzero, and the LM hash is present in the database. This combination is used when the NT hash is not stored at the server; the client can send the NT hash encrypted with the new LM hash instead. The NT-hash–related parameters are ignored.
 * 3.    NtPresent is nonzero, LmCrossEncryptionPresent is nonzero, and the NT hash is present in the database. This combination is used when the LM hash is not stored at the server; the client can send the LM hash encrypted with the new NT hash instead. The LM-hash–related parameters are ignored.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints applied in the presented order:
 * 1.  All database operations MUST occur in a single transaction.
 * 2.  The constraints in section 3.1.5.14.5 MUST be satisfied.
 * 3.  If LmPresent is nonzero and NewLmEncryptedWithOldLm or OldLmEncryptedWithNewLm is "NULL", the server MUST return an error.
 * 4.  If NtPresent is nonzero and NewNtEncryptedWithOldNt or OldNtEncryptedWithNewNt is "NULL", the server MUST return an error.
 * 5. If NtCrossEncryptionPresent is nonzero and NewNtEncryptedWithNewLm is "NULL", the server MUST return an error.
 * 6.  If LmCrossEncryptionPresent is nonzero and NewLmEncryptedWithNewNt is "NULL", the server MUST return an error.
 * 7.  If LmPresent and NtPresent are zero, the server MUST return an error.
 * 8.  Let U be the user account referenced by UserHandle.Object.
 * 9.  Let Stored-LM-Hash be the value of the dBCSPwd attribute from the database decrypted using the algorithm specified in section 2.2.11.1, using U's RelativeId (an unsigned integer) as the key. If the dBCSPwd attribute does not exist, let Stored-LM-Hash be "NULL".
 * 10.Let Stored-NT-Hash be the value of the unicodePwd attribute from the database decrypted using the algorithm specified in section 2.2.11.1, using U's RelativeId (an unsigned integer) as the key. If the unicodePwd attribute does not exist, let Stored-NT-Hash be "NULL".
 * 11.If LmPresent is nonzero and Stored-LM-Hash is not NULL, let Presented-New-LM-Hash be NewLmEncryptedWithOldLm, decrypted as specified in section 2.2.11.1, using Stored-LM-Hash as the key; and let Presented-Old-LM-Hash be OldLmEncryptedWithNewLm, decrypted as specified in section 2.2.11.1, using Presented-New-LM-Hash as the key. The values are not referenced below if LmPresent is zero.
 * 12. If NtPresent is nonzero and Stored-NT-Hash is not NULL, let Presented-New-NT-Hash be NewNtEncryptedWithOldNt, decrypted as specified in section 2.2.11.1, using Stored-NT-Hash as the key; and let Presented-Old-NT-Hash be OldNtEncryptedWithNewNt, decrypted as specified in section 2.2.11.1, using Presented-New-NT-Hash as the key. The values are not referenced below if NtPresent is zero.
 *  13.If all of the following conditions are true, the server MUST abort processing and return the error status STATUS_LM_CROSS_ENCRYPTION_REQUIRED:
 * 	a)  NtPresent is nonzero.
 *  	b) LmPresent is zero.
 *  	c)   LmCrossEncryptionPresent is zero.
 *  	d)   Stored-NT-Hash is non-NULL and equals Presented-Old-NT-Hash.
 *  14.If all of the following conditions are true, the server MUST abort processing and return the error status STATUS_NT_CROSS_ENCRYPTION_REQUIRED.
 *     a)  NtPresent is nonzero.
 *     b)  LmPresent is nonzero.
 *     c)  NtCrossEncryptionPresent is zero.
 *     d)  Stored-NT-Hash is NULL.
 *     e)  Stored-LM-Hash is non-NULL and equals Presented-Old-LM-Hash.
 * 15. Exactly one of the three following conditions MUST be true; otherwise, the server MUST satisfy the constraints in section 3.1.5.14.6 and then return STATUS_WRONG_PASSWORD.
 *      a) LmPresent is nonzero, Stored-LM-Hash is non-NULL and equals Presented-Old-LM-Hash, NtPresent is nonzero, Stored-NT-Hash is non-NULL, and Stored-NT-Hash equals Presented-Old-NT-Hash.
 *      b) LmPresent is nonzero, Stored-LM-Hash is non-NULL and equals Presented-Old-LM-Hash, NtPresent is zero, and Stored-NT-Hash is NULL.
 *      c) NtPresent is nonzero, Stored-NT-Hash is non-NULL and equals Presented-Old-NT-Hash, LmPresent is zero, and Stored-LM-Hash is NULL.
 * 16. If LmPresent is nonzero, the dBCSPwd attribute MUST be updated with Presented-New-LM-Hash.
 * 17. If LmPresent is zero and LmCrossEncryptionPresent is nonzero, the dBCSPwd attribute MUST be updated with the value of NewLmEncryptedWithNewNt, decrypted using the algorithm specified in section 2.2.11.1, using Presented-New-NT-Hash as the decryption key.
 * 18. If NtPresent is nonzero, the unicodePwd attribute MUST be updated with Presented-New-NT-Hash.
 * 19. If NtPresent is zero and NtCrossEncryptionPresent is nonzero, the unicodePwd attribute MUST be updated with the value of NewNtEncryptedWithNewLm, decrypted using the algorithm specified in section 2.2.11.1, using Presented-New-LM-Hash as the decryption key.
 * 20. On database error, the server MUST return the data error; on general processing error, the server MUST return STATUS_WRONG_PASSWORD; otherwise, return STATUS_SUCCESS.
 * </pre></blockquote>
*
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9699d8ca-e1a4-433c-a8c3-d7bebeb01476">SamrChangePasswordUser (Opnum 38)</a>">
 */
public class SamrChangePasswordUserRequest extends RequestCall<SamrChangePasswordUserResponse> {
	public static final short OP_NUM = 38;
	// <NDR: fixed array> [in, out] SAMPR_HANDLE UserHandle
	private UserHandle userHandle;
	private int lmPresent;
	private byte[] oldLmEncryptedWithNewLm;
	private byte[] newLmEncryptedWithOldLm;
	private int ntPresent;
	private byte[] oldNtEncryptedWithNewNt;
	private byte[] newNtEncryptedWithOldNt;
	private int ntCrossEncryptionPresent;
	private byte[] newNtEncryptedWithNewLm;
	private int lmCrossEncryptionPresent;
	private byte[] newLmEncryptedWithNewNt;

	public UserHandle getUserHandle() { return userHandle; }
	public SamrChangePasswordUserRequest(UserHandle userHandle, int lmPresent, byte[] oldLmEncryptedWithNewLm, byte[] newLmEncryptedWithOldLm, int ntPresent, byte[] oldNtEncryptedWithNewNt, byte[] newNtEncryptedWithOldNt, int ntCrossEncryptionPresent, byte[] newNtEncryptedWithNewLm, int lmCrossEncryptionPresent, byte[] newLmEncryptedWithNewNt) {
		super(OP_NUM);
		this.userHandle = userHandle;
		this.lmPresent = lmPresent;
		this.oldLmEncryptedWithNewLm = oldLmEncryptedWithNewLm;
		this.newLmEncryptedWithOldLm = newLmEncryptedWithOldLm;
		this.ntPresent = ntPresent;
		this.oldNtEncryptedWithNewNt = oldNtEncryptedWithNewNt;
		this.newNtEncryptedWithOldNt = newNtEncryptedWithOldNt;
		this.ntCrossEncryptionPresent = ntCrossEncryptionPresent;
		this.newNtEncryptedWithNewLm = newNtEncryptedWithNewLm;
		this.lmCrossEncryptionPresent = lmCrossEncryptionPresent;
		this.newLmEncryptedWithNewNt = newLmEncryptedWithNewNt;
	}
	@Override
	public SamrChangePasswordUserResponse getResponseObject() { return new SamrChangePasswordUserResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		lmPresent = 0;
		ntPresent = 1;
		ntCrossEncryptionPresent = 0;
		lmCrossEncryptionPresent = 1;
		int notNullPointer = 1;
		packetOut.write(getUserHandle().getBytes());
		packetOut.writeInt(lmPresent);
		packetOut.writeInt(notNullPointer);
		packetOut.write(oldLmEncryptedWithNewLm);
		packetOut.writeInt(notNullPointer);
		packetOut.write(newLmEncryptedWithOldLm);
		packetOut.writeInt(ntPresent);
		packetOut.writeInt(notNullPointer);
		packetOut.write(oldNtEncryptedWithNewNt);
		packetOut.writeInt(notNullPointer);
		packetOut.write(newNtEncryptedWithOldNt);
		packetOut.writeInt(ntCrossEncryptionPresent);
		packetOut.writeInt(notNullPointer);
		packetOut.write(newNtEncryptedWithNewLm);
		packetOut.writeInt(lmCrossEncryptionPresent);
		packetOut.writeInt(notNullPointer);
		packetOut.write(newLmEncryptedWithNewNt);
	}
}
