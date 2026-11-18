package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.GroupHandle;

/**
 * <blockquote><pre>
 *    The SamrDeleteGroup method removes a group object.
 *       
 *    long SamrDeleteGroup(
 *       [in, out] SAMPR_HANDLE* GroupHandle
 *       );
 *       
 *   GroupHandle: An RPC context handle, as specified in section 2.2.3.2, representing a group object.
 *   This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *   Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *   
 *       1. The server MUST return an error if GroupHandle.HandleType is not equal to "Group".
 *       2. GroupHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *       3. All database operations MUST occur in a single transaction.
 *       4. Let G be the group referenced by the GroupHandle.Object.
 *       5. If the RID of G's objectSid attribute is less than 1000, an error MUST be returned.
 *       6. In the non-DC configuration, if G has any values in the member attribute, an error MUST be returned.
 *       7. If any user in the same domain as G has, as its primaryGroupId attribute, the RID of G's objectSid attribute, an error MUST be returned.
 *       8. In the DC configuration, if G is a parent to another object, an error MUST be returned.<55>
 *       9. G MUST be removed from the database. 
 *       10. The server MUST delete the SamContextHandle ADM element (section 3.1.1.10) represented by GroupHandle, and then MUST return 0 for the value of GroupHandle and a return code of STATUS_SUCCESS.
 *       
 * @see  * <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/664ed55f-198a-4775-b9d4-398131dba577">SamrDeleteGroup (Opnum 23)</a>
 */
public class SamrDeleteGroupRequest extends RequestCall<SamrDeleteGroupResponse> {
    public static final short OP_NUM = 23;
    // <NDR: fixed array> [in, out] SAMPR_HANDLE GroupHandle
	private GroupHandle groupHandle;
	
	public GroupHandle getGroupHandle() { return groupHandle; }
	
	public SamrDeleteGroupRequest(GroupHandle groupHandle) {
        super(OP_NUM);
		this.groupHandle = groupHandle;
	}

	@Override
	public SamrDeleteGroupResponse getResponseObject() { 
		return  new SamrDeleteGroupResponse();
	}
	
	@Override
	public void marshal(PacketOutput packetOut) throws IOException { 
	    // <NDR: fixed array> [in, out] SAMPR_HANDLE GroupHandle
        packetOut.write(getGroupHandle().getBytes());
	 }
}
