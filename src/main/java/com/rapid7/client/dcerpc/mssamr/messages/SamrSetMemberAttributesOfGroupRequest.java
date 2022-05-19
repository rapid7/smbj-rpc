package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;

/**
 * SamrSetMemberAttributesOfGroup (Opnum 26)
 * 
 * <blockquote><pre>The SamrSetMemberAttributesOfGroup method sets the attributes of a member relationship.
 * 	long SamrAddMemberToGroup(
 * 		[in] SAMPR_HANDLE GroupHandle,
 * 		[in] unsigned long MemberId,
 * 		[in] unsigned long Attributes
 * 		);
 *      
 * GroupHandle: An RPC context handle, as specified in section 2.2.3.2, representing a group object.
 * MemberId: A RID representing an account to remove from the group's membership list.
 * Attributes: The characteristics of the membership relationship. See section 2.2.1.10 for legal values and semantics.
 * 
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * On receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The server MUST return an error if GroupHandle.HandleType is not equal to "Group".
 *     2. GroupHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *     3. In a non-DC configuration, the MemberId parameter MUST be a member of the group referenced by GroupHandle.Object; otherwise, processing MUST be aborted and an error returned.
 *     4. For a message processing specification of the Attributes parameter, see section 3.1.5.14.7.
 * </pre></blockquote>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/a4db0c27-5540-4d2f-b111-08db1ee0b5a4">SamrSetMemberAttributesOfGroup (Opnum 26)</a>
 */

public class SamrSetMemberAttributesOfGroupRequest extends RequestCall<SamrSetMemberAttributesOfGroupResponse> {
    public static final short OP_NUM = 26;
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
    
    public SamrSetMemberAttributesOfGroupRequest(final GroupHandle groupHandle, int memberId, int attributes) {
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
	public SamrSetMemberAttributesOfGroupResponse getResponseObject() { 
		return new SamrSetMemberAttributesOfGroupResponse(); 
		}
}
