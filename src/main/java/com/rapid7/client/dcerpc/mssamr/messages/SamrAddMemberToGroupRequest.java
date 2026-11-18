package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;

/**
 * SamrAddMemberToGroup (Opnum 22)
 * 
 * <blockquote><pre>The SamrSetInformationDomain method updates attributes on a domain object.
 * 	long SamrAddMemberToGroup(
 * 		[in] SAMPR_HANDLE GroupHandle,
 * 		[in] unsigned long MemberId,
 * 		[in] unsigned long Attributes
 * 		);
 *      
 * GroupHandle: An RPC context handle, as specified in section 2.2.3.2, representing a group object.
 * MemberId: A RID representing an account to remove from the group's membership list.
 * Attributes: The characteristics of the membership relationship. See section 2.2.1.10 for legal values and semantics.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 * 		1. The server MUST return an error if GroupHandle.HandleType is not equal to "Group".
 * 		2. GroupHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 * 		3. All database operations MUST occur in a single transaction.
 * 		4. Let G be the group referenced by GroupHandle.Object.
 * 		5. Let TargetSid be the SID composed by making the MemberId a suffix to the domain prefix of G's objectSid.
 * 		6. If there is no object whose objectSid attribute is TargetSid, the server MUST return STATUS_NO_SUCH_USER.
 * 		7. If G's member attribute already has as a dsname value that references the object whose objectSid is TargetSid, the server MUST return an error.
 * 		8. G's member attribute MUST be updated to add a dsname value that references the object with the objectSid value TargetSid.
 * 		9. The message processing specified in section 3.1.5.14.7 for the Attributes parameter MUST be adhered to.
 *     </pre></blockquote>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/3c70fec3-6a2e-48ef-bd4e-aa2b3a1cd96a">SamrAddMemberToGroup (Opnum 22)</a>
 */

public class SamrAddMemberToGroupRequest extends RequestCall<SamrAddMemberToGroupResponse> {
    public static final short OP_NUM = 22;
    // <NDR: fixed array> [in] SAMPR_HANDLE GroupHandle
	private GroupHandle groupHandle;
    // <NDR: unsigned long> [in] MemberId
    private int memberId;
    // <NDR: unsigned long> [in] Attributes
    private int attributes;

    public int getMemberId() {
        return memberId;
    }
    public int getAttributes() {
        return attributes;
    }
    
    public GroupHandle getGroupHandle() {
        return groupHandle;
    }
    
    public SamrAddMemberToGroupRequest(final GroupHandle groupHandle, int memberId, int attributes) {
        super(OP_NUM);
        this.groupHandle = groupHandle;
        this.memberId = memberId;
        this.attributes = attributes;
    }
    
	@Override
	public void marshal(PacketOutput packetOut) throws IOException { 
	    // <NDR: fixed array> [in] GroupHandle UserHandle
		packetOut.write(groupHandle.getBytes());		
	    // <NDR: unsigned long> [in] MemberId
        packetOut.writeInt(this.memberId);	
	    // <NDR: unsigned long> [in] Attributes
        packetOut.writeInt(this.attributes);
	}
	@Override
	public SamrAddMemberToGroupResponse getResponseObject() { 
		return new SamrAddMemberToGroupResponse(); 
		}
}
