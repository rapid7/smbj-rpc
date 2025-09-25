package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;

/**
 * SamrRemoveMemberFromGroup (Opnum 24)
 * 
 * <blockquote><pre>The SamrSetInformationDomain method updates attributes on a domain object.
 *
 * 	long SamrRemoveMemberFromGroup(
 * 		[in] SAMPR_HANDLE GroupHandle,
 * 		[in] unsigned long MemberId
 * 		);
 *      
 * GroupHandle: An RPC context handle, as specified in section 2.2.3.2, representing a group object.
 * MemberId: A RID representing an account to remove from the group's membership list.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *     1. The server MUST return an error if GroupHandle.HandleType is not equal to "Group".
 *     2. GroupHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *     3. All database operations MUST occur in a single transaction.
 *     4. Let G be the group referenced by the GroupHandle.Object.
 *     5. Let TargetSid be the SID composed by making the MemberId a suffix to the domain prefix of G's objectSid.
 *     6. If G's member attribute does not have a dsname value that references the object whose objectSid is TargetSid, the server MUST return an error.
 *     7. G's member attribute MUST be updated to remove a dsname value that references the object with the objectSid value TargetSid.
 *     </pre></blockquote>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/613ff663-5863-4430-8923-8f1edc05310b">SamrRemoveMemberFromGroup (Opnum 24)</a>
 */

public class SamrRemoveMemberFromGroupRequest extends RequestCall<SamrRemoveMemberFromGroupResponse> {
    public static final short OP_NUM = 24;
    // <NDR: fixed array> [in] SAMPR_HANDLE GroupHandle
	private GroupHandle groupHandle;
    // <NDR: unsigned long> [in] MemberId
    private int memberId;

    public int getMemberId() {
        return memberId;
    }
    
    public GroupHandle getGroupHandle() {
        return groupHandle;
    }
    
    public SamrRemoveMemberFromGroupRequest(GroupHandle groupHandle, int memberId) {
        super(OP_NUM);
        this.groupHandle = groupHandle;
        this.memberId = memberId;
    }
	@Override
	
	public SamrRemoveMemberFromGroupResponse getResponseObject() { 
		return  new SamrRemoveMemberFromGroupResponse();
	}
    
	@Override
	public void marshal(PacketOutput packetOut) throws IOException { 
	    // <NDR: fixed array> [in] GroupHandle UserHandle
		packetOut.write(groupHandle.getBytes());		
	    // <NDR: unsigned long> [in] MemberId
        packetOut.writeInt(this.memberId);
	}
}
