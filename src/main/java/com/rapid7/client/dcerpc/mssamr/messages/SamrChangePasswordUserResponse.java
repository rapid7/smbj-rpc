package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9699d8ca-e1a4-433c-a8c3-d7bebeb01476">SamrChangePasswordUser (Opnum 38)</a>">
 * <blockquote><pre>The SamrChangePasswordUser method changes the password of a user object.
 * 
 * long SamrChangePasswordUser(
*    [in] SAMPR_HANDLE UserHandle,
*    [in] unsigned char LmPresent,
*    [in, unique] PENCRYPTED_LM_OWF_PASSWORD OldLmEncryptedWithNewLm,
*    [in, unique] PENCRYPTED_LM_OWF_PASSWORD NewLmEncryptedWithOldLm,
*    [in] unsigned char NtPresent,
*    [in, unique] PENCRYPTED_NT_OWF_PASSWORD OldNtEncryptedWithNewNt,
*    [in, unique] PENCRYPTED_NT_OWF_PASSWORD NewNtEncryptedWithOldNt,
*    [in] unsigned char NtCrossEncryptionPresent,
*    [in, unique] PENCRYPTED_NT_OWF_PASSWORD NewNtEncryptedWithNewLm,
*    [in] unsigned char LmCrossEncryptionPresent,
*    [in, unique] PENCRYPTED_LM_OWF_PASSWORD NewLmEncryptedWithNewNt
*  );
* 
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/9699d8ca-e1a4-433c-a8c3-d7bebeb01476">SamrChangePasswordUser (Opnum 38)</a>">
 */
public class SamrChangePasswordUserResponse extends RequestResponse {

	@Override
	public void unmarshalResponse(PacketInput packetIn) throws IOException {
		//no response besides ERROR_STATUS_CODE 
	 }
	
}
