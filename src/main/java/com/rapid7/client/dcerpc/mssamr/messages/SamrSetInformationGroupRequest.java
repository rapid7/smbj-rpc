package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;
import com.rapid7.client.dcerpc.mssamr.objects.GroupInformationClass;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupAdminCommentInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupAttributeInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupGeneralInformation;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRGroupNameInformation;

/**
 * <blockquote><pre>The SamrSetInformationGroup method updates attributes on a group object.
 *       long SamrSetInformationGroup(
 *       [in] SAMPR_HANDLE GroupHandle,
 *       [in] GROUP_INFORMATION_CLASS GroupInformationClass,
 *       [in, switch_is(GroupInformationClass)]
 *       PSAMPR_GROUP_INFO_BUFFER Buffer
 *      );
 *
 * GroupHandle: An RPC context handle, as specified in section 2.2.3.2, representing a group object.
 * GroupInformationClass: An enumeration indicating which attributes to update. See section 2.2.5.6 for a listing of possible values.
 * Buffer: The requested attributes and values to update. See section 2.2.5.7 for structure details.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *   1. The server MUST return an error if GroupHandle.HandleType is not equal to "Group".
 *   2. GroupHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *   3. The following information levels MUST be processed by setting the database attribute on the group object associated with GroupHandle.Object to the associated input field-name value using the mapping in section 3.1.5.14.9. All updates MUST be performed in the same transaction.
 *   	GroupInformationClass
 *   	GroupNameInformation
 *   	GroupAttributeInformation
 *   	GroupAdminCommentInformation
 *   4. If GroupInformationClass does not meet the criteria of constraint 2, the server MUST return an error code.
 *     </pre></blockquote>
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/e66db19f-600a-481b-bc4e-23953433255d">SamrSetInformationGroup (Opnum 21)</a>
 */
public abstract class SamrSetInformationGroupRequest<T extends Marshallable> extends RequestCall<SamrSetInformationGroupResponse> {
	public static final short OP_NUM = 21;
	// <NDR: fixed array> [in] SAMPR_HANDLE GroupHandle
	private GroupHandle groupHandle;
	// <NDR: unsigned short> [in] Group_INFORMATION_CLASS GroupInformationClass
	private T groupInformation;

	public T getGroupInformation() { return groupInformation; }
	public GroupHandle getGroupHandle() { return groupHandle; }
	public abstract GroupInformationClass getGroupInformationClass();
	public SamrSetInformationGroupRequest(final GroupHandle groupHandle, T groupInformation) {
		super(OP_NUM);
		this.groupHandle = groupHandle;
		this.groupInformation = groupInformation;
	}
	@Override
	public SamrSetInformationGroupResponse getResponseObject() { return new SamrSetInformationGroupResponse(); }
	@Override
	public void marshal(PacketOutput packetOut) throws IOException {
		// <NDR: fixed array> [in] SAMPR_HANDLE GroupHandle
		packetOut.write(groupHandle.getBytes());
		packetOut.writeShort(getGroupInformationClass().getInfoLevel());
		packetOut.writeShort(getGroupInformationClass().getInfoLevel());
		groupInformation.marshalPreamble(packetOut);
		groupInformation.marshalEntity(packetOut);
		groupInformation.marshalDeferrals(packetOut);
	}

	public static class GroupGeneralInformation extends SamrSetInformationGroupRequest<SAMPRGroupGeneralInformation> {
		public GroupGeneralInformation(GroupHandle groupHandle, SAMPRGroupGeneralInformation groupGeneralInformation) {
			super(groupHandle, groupGeneralInformation);
		}
		@Override
		public GroupInformationClass getGroupInformationClass() { return GroupInformationClass.GROUP_GENERAL_INFORMATION; }
	}
	public static class GroupNameInformation extends SamrSetInformationGroupRequest<SAMPRGroupNameInformation> {
		public GroupNameInformation(GroupHandle groupHandle, SAMPRGroupNameInformation groupNameInformation) {
			super(groupHandle, groupNameInformation);
		}
		@Override
		public GroupInformationClass getGroupInformationClass() { return GroupInformationClass.GROUP_NAME_INFORMATION; }
	}
	public static class GroupAttributeInformation extends SamrSetInformationGroupRequest<SAMPRGroupAttributeInformation> {
		public GroupAttributeInformation(GroupHandle groupHandle, SAMPRGroupAttributeInformation groupAttributeInformation) {
			super(groupHandle, groupAttributeInformation);
		}
		@Override
		public GroupInformationClass getGroupInformationClass() { return GroupInformationClass.GROUP_ATTRIBUTE_INFORMATION; }
	}
	public static class GroupAdminCommentInformation extends SamrSetInformationGroupRequest<SAMPRGroupAdminCommentInformation> {
		public GroupAdminCommentInformation(GroupHandle groupHandle, SAMPRGroupAdminCommentInformation groupAdminCommentInformation) {
			super(groupHandle, groupAdminCommentInformation);
		}
		@Override
		public GroupInformationClass getGroupInformationClass() { return GroupInformationClass.GROUP_ADMINCOMMENT_INFORMATION; }
	}
}
