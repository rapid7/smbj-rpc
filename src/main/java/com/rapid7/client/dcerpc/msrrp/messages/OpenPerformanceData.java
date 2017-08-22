/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msrrp.messages;

/**
 * <b>3.1.5.4 OpenPerformanceData (Opnum 3)</b><br>
 * <br>
 * The OpenPerformanceData method is called by the client. In response, the server opens a handle to the
 * HKEY_PERFORMANCE_DATA predefined key. The HKEY_PERFORMANCE_DATA predefined key is used to retrieve performance
 * information from a registry server using only the BaseRegQueryInfoKey, BaseRegQueryValue, BaseRegEnumValues and
 * BaseRegCloseKey methods.
 *
 * <pre>
 * error_status_t OpenPerformanceData(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phKey
 * );
 * </pre>
 *
 * ServerName: SHOULD be sent as NULL and MUST be ignored on receipt because the binding to the server is already
 * complete at this stage.<br>
 * <br>
 * samDesired: SHOULD be sent as 0 and MUST be ignored on receipt.<br>
 * <br>
 * phKey: A pointer to an RPC context handle for the root key, HKEY_PERFORMANCE_DATA, as specified in section 3.1.1. The
 * handle is found in the handle table (HANDLETABLE).<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in Win32Error Codes in [MS-ERREF]. The server SHOULD return without modification any other error code
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
 * The server attempts to open the root key, HKEY_PERFORMANCE_DATA, and return a handle to that key in the phKey
 * parameter. The server MUST evaluate the security descriptor that is associated with the key
 * HKEY_LOCAL_MACHINE\SOFTWARE\MICROSOFT\WINDOWS NT\CURRENTVERSION\PERFLIB against a requested access of MAXIMUM_ALLOWED
 * (see [MS-DTYP] (section 2.4.3)) to determine whether the caller can open this key.<br>
 * <br>
 * If the caller is permitted to open the key, the server MUST return 0 to indicate success, and create a new valid
 * context handle. The server MUST store the context handle value in the handle table (HANDLETABLE) along with a mapping
 * to the HKEY_PERFORMANCE_DATA key. The server MUST place the handle value (see 3.1.1.9) in the phKey parameter. If the
 * caller does not have access, the server MUST return ERROR_ACCESS_DENIED (5).
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244954">3.1.5.4 OpenPerformanceData (Opnum 3)</a>
 */
public class OpenPerformanceData {
    public final static short OP_NUM = 3;
}
