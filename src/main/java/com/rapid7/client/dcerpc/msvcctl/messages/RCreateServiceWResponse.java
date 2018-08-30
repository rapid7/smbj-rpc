package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;

import java.io.IOException;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245923.aspx">RCreateServiceW</a>
 * <blockquote><pre>The RCreateServiceW method creates the service record in the SCM database.
 *
 *      DWORD RCreateServiceW(
 *          [in] SC_RPC_HANDLE hSCManager,
 *          [in, string, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpServiceName
 *          [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpDisplayName
 *          [in] DWORD dwDesiredAccess,
 *          [in] DWORD dwServiceType,
 *          [in] DWORD dwStartType,
 *          [in] DWORD dwErrorControl,
 *          [in, string, unique, range(0, SC_MAX_PATH_LENGTH)] wchar_t* lpBinaryPathName,
 *          [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpLoadOrderGroup,
 *          [in, out, unique] LPDWORD lpdwTagId,
 *          [in, unique, size_is(dwDependSize)] LPBYTE lpDependencies,
 *          [in, range(0, SC_MAX_DEPEND_SIZE)] DWORD dwDependSize,
 *          [in, string, unique, range(0, SC_MAX_ACCOUNT_NAME_LENGTH)] wchar_t* lpServiceStartName,
 *          [in, unique, size_is(dwPwSize)] LPBYTE lpPassword,
 *          [in, range(0, SC_MAX_PWD_SIZE)] DWORD dwPwSize,
 *          [out] LPSC_RPC_HANDLE lpServiceHandle
 *      );
 *
 * hSCManager: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the SCM database created using one of the open methods specified in section 3.1.4. The SC_MANAGER_CREATE_SERVICE access right MUST have been granted to the caller when the RPC context handle was created.
 * lpServiceName: A pointer to a null-terminated UNICODE string that specifies the name of the service to install. This MUST not be NULL. The forward slash, back slash, comma, and space characters are illegal in service names.
 * lpDisplayName: A pointer to a null-terminated UNICODE string that contains the display name by which user interface programs identify the service.
 * dwDesiredAccess: A value that specifies the access to the service. This MUST be one of the values as specified in section 3.1.4.
 * dwServiceType: A value that specifies the type of service.
 * dwStartType: A Start value for the service record (section 3.1.1) that specifies when to start the service. This MUST be one of the following values.
 * dwErrorControl: An ErrorControl value for the service record (section 3.1.1) that specifies the severity of the error if the service fails to start and determines the action that the SCM takes. MUST be one of the following values.
 * lpBinaryPathName: An ImagePath value for the service record (section 3.1.1) as a pointer to a null-terminated UNICODE string name. The pointer contains the fully qualified path to the service binary file. The path MAY include arguments. If the path contains a space, it MUST be quoted so that it is correctly interpreted. For example, "d:\\my share\\myservice.exe" is specified as "\"d:\\my share\\myservice.exe\"".
 * lpLoadOrderGroup: A Group value for the service record (section 3.1.1) as a pointer to a null-terminated UNICODE string that names the load-ordering group of which this service is a member.
 *      Specify NULL or an empty string if the service does not belong to a load-ordering group.
 * lpdwTagId: A Tag value for the service record (section 3.1.1) as a pointer to a variable that receives a tag value. The value is unique to the group specified in the lpLoadOrderGroup parameter.
 * lpDependencies: DependOnService and DependOnGroup values for the service record (section 3.1.1) as a pointer to an array of null-separated names of services or load ordering groups that MUST start before this service. The array is doubly null-terminated. Load ordering group names are prefixed with a "+" character (to distinguish them from service names). If the pointer is NULL or if it points to an empty string, the service has no dependencies. Cyclic dependency between services is not allowed. The character set is Unicode. Dependency on a service means that this service can only run if the service it depends on is running. Dependency on a group means that this service can run if at least one member of the group is running after an attempt to start all members of the group.
 * dwDependSize: The size, in bytes, of the string specified by the lpDependencies parameter.
 * lpServiceStartName: An ObjectName value for the service record (section 3.1.1) as a pointer to a null-terminated UNICODE string that specifies the name of the account under which the service runs.
 * lpPassword: A Password value for the service record (section 3.1.1) as a pointer to a null-terminated UNICODE string that contains the password of the account whose name was specified by the lpServiceStartName parameter.
 * dwPwSize: The size, in bytes, of the password specified by the lpPassword parameter.
 * lpServiceHandle: An LPSC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the newly created service record.</pre></blockquote>
 */
public class RCreateServiceWResponse extends RequestResponse {

    private Integer tagId;
    private byte[] handle;

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpdwTagId
        if (packetIn.readReferentID() != 0) {
            this.tagId = packetIn.readInt();
        } else {
            this.tagId = null;
        }

        this.handle = new byte[20];
        packetIn.readRawBytes(this.handle);
    }

    public Integer getTagId() {
        return tagId;
    }


    public byte[] getHandle() {
        return handle;
    }
}
