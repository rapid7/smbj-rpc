package com.rapid7.client.dcerpc.mssamr.messages;


import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssamr.dto.UserHandle;

/**
 * SamrDeleteUserResponse
 * 
 * <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/4643a579-56ec-4c66-a1ef-4ab78dd21d73">SamrDeleteUser (Opnum 35)</a>
 * <blockquote><pre>The SamrDeleteUser method removes a user object.
 * 
 *    long SamrDeleteUser(
 *       [in, out] SAMPR_HANDLE* UserHandle
 *       );
 *       
 *       UserHandle: An RPC context handle, as specified in section 2.2.3.2, representing a user object.
 *       This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 *       Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 *       1.    The server MUST return an error if UserHandle.HandleType is not equal to "User".
 *       2.    UserHandle.GrantedAccess MUST have the required access specified in section 3.1.2.1. Otherwise, the server MUST return STATUS_ACCESS_DENIED.
 *       3.    Let U be the object referenced by UserHandle.Object.
 *       4.    All database operations MUST occur in a single transaction.
 *       5.    If the RID of U's objectSid attribute value is less than 1000, an error MUST be returned.
 *       6.    In the DC configuration, if U is a parent to another object, an error MUST be returned.
 *       7.    U MUST be removed from the database.
 *       8.    The server MUST delete the SamContextHandle ADM element (section 3.1.1.10) represented by UserHandle, and then MUST return 0 for the value of UserHandle and a return code of STATUS_SUCCESS. </pre></blockquote>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/4643a579-56ec-4c66-a1ef-4ab78dd21d73">SamrDeleteUser (Opnum 35)</a>
 */

public class SamrDeleteUserResponse extends RequestResponse {
	// <NDR: fixed array> [in,out] SAMPR_HANDLE UserHandle
	private UserHandle userHandle;
    public UserHandle getUserHandle() { return userHandle;  }

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        byte[] userHandleBytes = new byte[20];
        packetIn.readRawBytes(userHandleBytes);
		this.userHandle = new UserHandle(userHandleBytes);
    }
}
