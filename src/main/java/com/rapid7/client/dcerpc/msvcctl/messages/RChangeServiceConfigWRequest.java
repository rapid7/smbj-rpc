/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msvcctl.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc245938.aspx">RChangeServiceConfigW</a>
 * <blockquote><pre>The RChangeServiceConfigW method changes a service's configuration parameters in the SCM database.
 *
 *      DWORD RChangeServiceConfigW(
 *          [in] SC_RPC_HANDLE hService,
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
 *          [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpDisplayName
 *      );
 *
 * hService: An SC_RPC_HANDLE (section 2.2.4) data type that defines the handle to the service record that MUST have been created previously, using one of the open methods specified in section 3.1.4. The SERVICE_CHANGE_CONFIG access right MUST have been granted to the caller when the RPC context handle to the service record was created.
 * dwServiceType: A Type value for the service record (section 3.1.1) that specifies the type of service. This MUST be one of the following values.
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
 * lpDisplayName: A DisplayName value for the service record (section 3.1.1) as a pointer to a null-terminated UNICODE string that contains the display name that applications can use to identify the service for its users.</pre></blockquote>
 */
public class RChangeServiceConfigWRequest extends RequestCall<RChangeServiceConfigWResponse> {
    private final static short OP_NUM = 11;
    private final byte[] hService;
    private final int dwServiceType;
    private final int dwStartType;
    private final int dwErrorControl;
    private final WChar.NullTerminated lpBinaryPathName;
    private final WChar.NullTerminated lpLoadOrderGroup;
    private final Integer lpDwTagId;
    private final String[] lpDependencies;
    private final WChar.NullTerminated lpServiceStartName;
    private final String lpPassword;
    private final WChar.NullTerminated lpDisplayName;

    public RChangeServiceConfigWRequest(final byte[] hService, final int dwServiceType, final int dwStartType,
            final int dwErrorControl, final WChar.NullTerminated lpBinaryPathName,
            final WChar.NullTerminated lpLoadOrderGroup, final Integer lpDwTagId, final String[] lpDependencies,
            final WChar.NullTerminated lpServiceStartName, final String lpPassword,
            final WChar.NullTerminated lpDisplayName) {
        super(OP_NUM);
        this.hService = hService;
        this.dwServiceType = dwServiceType;
        this.dwStartType = dwStartType;
        this.dwErrorControl = dwErrorControl;
        this.lpBinaryPathName = lpBinaryPathName;
        this.lpLoadOrderGroup = lpLoadOrderGroup;
        this.lpDwTagId = lpDwTagId;
        this.lpDependencies = lpDependencies;
        this.lpServiceStartName = lpServiceStartName;
        this.lpPassword = lpPassword;
        this.lpDisplayName = lpDisplayName;
    }

    @Override
    public RChangeServiceConfigWResponse getResponseObject() {
        return new RChangeServiceConfigWResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: fixed array> [in] SC_RPC_HANDLE hService
        packetOut.write(this.hService);
        // <NDR: unsigned long> [in] DWORD dwServiceType
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.dwServiceType);
        // <NDR: unsigned long> [in] DWORD dwStartType
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.dwStartType);
        // <NDR: unsigned long> [in] DWORD dwErrorControl
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.dwErrorControl);
        // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_PATH_LENGTH)] wchar_t* lpBinaryPathName
        // Alignment: 4 - Already aligned
        if (packetOut.writeReferentID(this.lpBinaryPathName)) {
            packetOut.writeMarshallable(this.lpBinaryPathName);
            // Alignment for lpLoadOrderGroup
            packetOut.align(Alignment.FOUR);
        }
        // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpLoadOrderGroup
        if (packetOut.writeReferentID(this.lpLoadOrderGroup)) {
            packetOut.writeMarshallable(this.lpLoadOrderGroup);
            // Alignment for lpdwTagId
            packetOut.align(Alignment.FOUR);
        }
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpdwTagId
        if (this.lpDwTagId != null && this.lpDwTagId != 0) {
            packetOut.writeReferentID();
            packetOut.writeInt(this.lpDwTagId);
        } else {
            packetOut.writeNull();
        }
        // <NDR: pointer[conformant array]> [in, unique, size_is(dwDependSize)] LPBYTE lpDependencies
        if (packetOut.writeReferentID(this.lpDependencies)) {
            // Count the number of bytes required for the array of dependencies
            // This is better than allocating a new byte[] to hold everything.
            // At the very least we have a null terminator at the end
            int byteCount = 2;
            for (final String dependency : this.lpDependencies) {
                // Number of UTF-16 bytes including null terminator
                byteCount += ((dependency == null) ? 0 : dependency.length() * 2) + 2;
            }
            // MaximumCount for Conformant Array
            packetOut.writeInt(byteCount);
            // Entries
            for (final String dependency : this.lpDependencies) {
                if (dependency != null) {
                    // This is better than allocating a new char[]
                    for (int i = 0; i < dependency.length(); i++) {
                        // UTF-16 little endian
                        packetOut.writeChar(dependency.charAt(i));
                    }
                }
                // Each entry is null terminated
                packetOut.writeChar(0);
            }
            // Array is doubly null terminated
            packetOut.writeChar(0);
            // <NDR: unsigned long> [in, range(0, SC_MAX_DEPEND_SIZE)] DWORD dwDependSize
            packetOut.align(Alignment.FOUR);
            packetOut.writeInt(byteCount);
        } else {
            // <NDR: unsigned long> [in, range(0, SC_MAX_DEPEND_SIZE)] DWORD dwDependSize
            packetOut.writeInt(0);
        }
        // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_ACCOUNT_NAME_LENGTH)] wchar_t* lpServiceStartName
        if (packetOut.writeReferentID(this.lpServiceStartName)) {
            packetOut.writeMarshallable(this.lpServiceStartName);
            // Alignment for lpPassword
            packetOut.align(Alignment.FOUR);
        }
        // <NDR: conformant array> [in, unique, size_is(dwPwSize)] LPBYTE lpPassword
        if (packetOut.writeReferentID(this.lpPassword)) {
            final int byteCount = (this.lpPassword.length() * 2) + 2;
            packetOut.writeInt(byteCount);
            for (int i = 0; i < this.lpPassword.length(); i++) {
                // UTF-16 little endian
                packetOut.writeChar(this.lpPassword.charAt(i));
            }
            // Null terminated
            packetOut.writeChar(0);
            // <NDR: unsigned long> [in, range(0, SC_MAX_PWD_SIZE)] DWORD dwPwSize
            packetOut.align(Alignment.FOUR);
            packetOut.writeInt(byteCount);
        } else {
            // <NDR: unsigned long> [in, range(0, SC_MAX_PWD_SIZE)] DWORD dwPwSize
            packetOut.writeInt(0);
        }
        // <NDR: pointer[struct]> [in, string, unique, range(0, SC_MAX_NAME_LENGTH)] wchar_t* lpDisplayName
        if (packetOut.writeReferentID(this.lpDisplayName)) {
            packetOut.writeMarshallable(this.lpDisplayName);
        }
    }
}
