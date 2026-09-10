package com.rapid7.client.dcerpc.mssamr.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssamr.dto.UserHandle;
import com.rapid7.client.dcerpc.mssamr.objects.SAMPRUserAllInformation;
import com.rapid7.client.dcerpc.mssamr.objects.UserInformationClass;

/**
 * <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/538222f7-1b89-4811-949a-0eac62e38dce">SamrSetInformationUser (Opnum 37)</a>
 * <blockquote><pre>The SamrSetInformationUser2 method updates attributes on a user object.
 *
 *      long SamrSetInformationUser2(
 *        [in] SAMPR_HANDLE UserHandle,
 *        [in] USER_INFORMATION_CLASS UserInformationClass,
 *        [in, switch_is(UserInformationClass)] 
 *          PSAMPR_USER_INFO_BUFFER Buffer
 *      );
 *      
 * UserHandle: An RPC context handle, as specified in section 2.2.3.2, representing a user object.
 * UserInformationClass: An enumeration indicating which attributes to update. See section 2.2.7.28 for a listing of possible values.
 * Buffer: The requested attributes and values to update. See section 2.2.7.29 for structure details.
 * This protocol asks the RPC runtime, via the strict_context_handle attribute, to reject the use of context handles created by a method of a different RPC interface than this one, as specified in [MS-RPCE] section 3.
 * Upon receiving this message, the server MUST process the data from the message subject to the following constraints:
 * 1.   The server MUST return an error if UserHandle.HandleType is not equal to "User".
 * 2.   UserHandle.GrantedAccess MUST have the required access specified in UserAllInformation (Common) (section 3.1.5.6.4.2).
 * 3.   The constraints in the following sections MUST be satisfied based on the UserInformationClass parameter. If there is no match in the table, the constraints of section 3.1.5.6.4.1 MUST be used.
 *    UserInformationClass	   				Constraint section
 *     UserAllInformation 								3.1.5.6.4.3
 *     UserInternal4Information   	 			3.1.5.6.4.4
 *		UserInternal4InformationNew   	 	3.1.5.6.4.5
 *     </pre></blockquote>
 * 
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/538222f7-1b89-4811-949a-0eac62e38dce">SamrSetInformationUser (Opnum 37)</a>
 * @see <a href= "https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-samr/99ee9f39-43e8-4bba-ac3a-82e0c0e0699e">SamrSetInformationUser2 (Opnum 58)</a>
 */

public abstract class SamrSetInformationUserRequest<T extends Marshallable> extends RequestCall<SamrSetInformationUserResponse> {
    public static final short OP_NUM = 37;
    // <NDR: fixed array> [in] SAMPR_HANDLE UserHandle
	private UserHandle userHandle;
    // <NDR: unsigned short> [in] USER_INFORMATION_CLASS UserInformationClass
    private T userInformation;

    public T getUserInformation() {
        return userInformation;
    }    
    
    public UserHandle getUserHandle() {
        return userHandle;
    }
    
    public abstract UserInformationClass getUserInformationClass();
    
    public SamrSetInformationUserRequest(final UserHandle userHandle,  T userInformation) {
        super(OP_NUM);
        this.userHandle = userHandle;
        this.userInformation = userInformation;
    }    
    
    @Override
    public SamrSetInformationUserResponse getResponseObject() {
        return new SamrSetInformationUserResponse();
    }
    
	@Override
	public void marshal(PacketOutput packetOut) throws IOException { 
	    // <NDR: fixed array> [in] SAMPR_HANDLE UserHandle
		packetOut.write(userHandle.getBytes());		
		packetOut.writeShort(getUserInformationClass().getInfoLevel()); 
		packetOut.writeShort(getUserInformationClass().getInfoLevel()); 
		userInformation.marshalPreamble(packetOut);		
		userInformation.marshalEntity(packetOut);		
		userInformation.marshalDeferrals(packetOut);		
	}
	
    public static class UserAllInformation extends SamrSetInformationUserRequest<SAMPRUserAllInformation> {
        public UserAllInformation(UserHandle userHandle, SAMPRUserAllInformation userInformation) {
            super(userHandle, userInformation);
        }

        @Override
        public UserInformationClass getUserInformationClass() {
            return UserInformationClass.USER_ALL_INFORMATION;
        }
    }

	
	
}
