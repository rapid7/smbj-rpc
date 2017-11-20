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
package com.rapid7.client.dcerpc.msrrp.messages;

/**
 * <b>3.1.5.5 OpenUsers (Opnum 4)</b> <br>
 * <br>
 * The OpenUsers method is called by the client. In response, the server opens a handle to the HKEY_USERS predefined
 * key.
 *
 * <pre>
 * error_status_t OpenUsers(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phKey
 * );
 * </pre>
 *
 * ServerName: SHOULD be sent as NULL and MUST be ignored on receipt because the binding to the server is already
 * complete at this stage.<br>
 * <br>
 * samDesired: The bit field that describes the wanted security access for the key. It MUST be constructed from one or
 * more of the values that are specified in section 2.2.4.<br>
 * <br>
 * phKey: A pointer to an RPC context handle for the root key, HKEY_USERS, as specified in section 3.1.1. The handle is
 * found in the handle table (HANDLETABLE).<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in Win32Error Codes in [MS-ERREF]. The server SHOULD return without modification any error code
 * encountered in servicing the client request. The most common error codes are listed in the following table.
 * <table border="1" summary="">
 * <tr>
 * <td>Return value/code</td>
 * <td>Description</td>
 * </tr>
 * <tr>
 * <td>ERROR_ACCESS_DENIED (0x00000005)</td>
 * <td>Access is denied.</td>
 * </tr>
 * <tr>
 * <td>ERROR_WRITE_PROTECT (0x00000013)</td>
 * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer service
 * registry requests because server shutdown has been initiated.</td>
 * </tr>
 * </table>
 * <br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * If the registry server can no longer service registry requests because server shutdown has been initiated
 * (SHUTDOWNINPROGRESS is set to TRUE), the server MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * The server attempts to open the predefined key HKEY_USERS and return a handle to that key in the phKey parameter. The
 * server MUST evaluate the security descriptor that is associated with the key against the access requested in the
 * samDesired parameter.<br>
 * <br>
 * If the caller is permitted to open the key, the server MUST return 0 to indicate success, and create a new valid
 * context handle. The server MUST store the context handle value in the handle table (HANDLETABLE) along with a mapping
 * to the HKEY_USERS key. The server MUST place a handle value (see 3.1.1.9) in the phKey parameter. If the caller does
 * not have access, the server MUST return ERROR_ACCESS_DENIED (5). For more information about security descriptors, see
 * 3.1.1.10.<br>
 * <br>
 * The server MUST validate the value of the samDesired parameter set by the client. If the value of samDesired includes
 * flags set which are not listed in section 2.2.4, the server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * The server MUST disregard the samDesired parameter if the samDesired parameter set by the client has bit 0x2 set,
 * indicating permission to create a subkey. The server MUST not allow subkey creation in certain locations of the
 * registry hierarchy. These restrictions are detailed within the Server Operations section of the BaseRegCreateKey
 * method.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244957">3.1.5.5 OpenUsers (Opnum 4)</a>
 */
public class OpenUsers {
    public final static short OP_NUM = 4;
}
