package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.AliasHandle;
import com.rapid7.client.dcerpc.mssamr.objects.AliasInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRAliasAdminCommentInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRAliasNameInformation;

/**
 * <blockquote><pre>The SamrSetInformationAlias method updates attributes on a alias object.
 *       long SamrSetInformationAlias(
 *       [in] SAMPR_HANDLE AliasHandle,
 *       [in] ALIAS_INFORMATION_CLASS AliasInformationClass,
 *       [in, switch_is(AliasInformationClass)]
 *       PSAMPR_ALIAS_INFO_BUFFER Buffer
 *      );
 *
 * AliasHandle: An RPC context handle, as specified in section 2.2.3.2, representing a alias object.
 * AliasInformationClass: An enumeration indicating which attributes to update. See section 2.2.5.6 for a listing of possible values.
 * Buffer: The requested attributes and values to update. See section 2.2.5.7 for structure details.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a
 * different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *   1. The server MUST return an error if AliasHandle.HandleType is not equal to "Alias".
 *   2. AliasHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *   3. The following information levels MUST be processed by setting the database attribute on the alias object associated with AliasHandle.Object
 *   to the associated input field-name value using the mapping in section 3.1.5.14.10. All updates MUST be performed in the same transaction.
 *   	AliasInformationClass
 *   	- AliasNameInformation
 *   	- AliasAdminCommentInformation
 *   4. If AliasInformationClass does not meet the criteria of constraint 2, the server MUST return an error code.
 *     </pre></blockquote>

 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/ba787e4e-3a4b-47a2-aca3-c3ac2d2c511e">SamrSetInformationAlias (Opnum 29)</a>
 */
public abstract class SamrSetInformationAliasRequest<T extends Marshallable> extends RequestCall<SamrSetInformationAliasResponse> {
	public static final short OP_NUM = 29;
	// <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
	private AliasHandle aliasHandle;
	// <NDR: unsigned short> [in] Alias_INFORMATION_CLASS AliasInformationClass
	private T aliasInformation;

	public T getAliasInformation() { return aliasInformation; }
	public AliasHandle getAliasHandle() { return aliasHandle; }
	public abstract AliasInformationClass getAliasInformationClass();
	public SamrSetInformationAliasRequest(final AliasHandle aliasHandle, T aliasInformation) {
		super(OP_NUM);
		this.aliasHandle = aliasHandle;
		this.aliasInformation = aliasInformation;
	}
	@Override
	public SamrSetInformationAliasResponse getResponseObject() { return new SamrSetInformationAliasResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] SAMPR_HANDLE AliasHandle
		packetOut.write(aliasHandle.getBytes());
		packetOut.writeShort(getAliasInformationClass().getInfoLevel());
		packetOut.writeShort(getAliasInformationClass().getInfoLevel());
		aliasInformation.marshalPreamble(packetOut);
		aliasInformation.marshalEntity(packetOut);
		aliasInformation.marshalDeferrals(packetOut);
	}

	public static class AliasNameInformation extends SamrSetInformationAliasRequest<SAMPRAliasNameInformation> {
		public AliasNameInformation(AliasHandle aliasHandle, SAMPRAliasNameInformation aliasNameInformation) {
			super(aliasHandle, aliasNameInformation);
		}
		@Override
		public AliasInformationClass getAliasInformationClass() { return AliasInformationClass.ALIAS_NAME_INFORMATION; }
	}
	public static class AliasAdminCommentInformation extends SamrSetInformationAliasRequest<SAMPRAliasAdminCommentInformation> {
		public AliasAdminCommentInformation(AliasHandle aliasHandle, SAMPRAliasAdminCommentInformation aliasAdminCommentInformation) {
			super(aliasHandle, aliasAdminCommentInformation);
		}
		@Override
		public AliasInformationClass getAliasInformationClass() { return AliasInformationClass.ALIAS_ADMINCOMMENT_INFORMATION; }
	}
}
