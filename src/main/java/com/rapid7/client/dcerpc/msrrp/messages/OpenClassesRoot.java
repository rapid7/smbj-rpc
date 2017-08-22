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
 * <b>3.1.5.1 OpenClassesRoot (Opnum 0)</b><br>
 * <br>
 * The OpenClassesRoot method is called by the client. In response, the server opens the HKEY_CLASSES_ROOT predefined
 * key.
 *
 * <pre>
 * error_status_t OpenClassesRoot(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phKey
 * );
 * </pre>
 *
 * ServerName: The server name. The ServerName SHOULD be sent as NULL, and MUST be ignored when it is received because
 * binding to the server is already complete at this stage.<br>
 * <br>
 * samDesired: A bit field that describes the requested security access for the key. It MUST be constructed from one or
 * more of the values specified in section 2.2.4.<br>
 * <br>
 * phKey: A pointer to an RPC context handle for the root key, HKEY_CLASSES_ROOT, as specified in section 3.1.1. The
 * handle is found in the handle table (HANDLETABLE).<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in [MS-ERREF] section 2.2. The most common error codes are listed in the following table.
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
 * The server then determines which key namespace to operate on (KEYS32 or KEYS64) by inspecting the value of the
 * samDesired parameter.<br>
 * <br>
 * If server does not support the 64-bit key namespace 3.1.1.4, the server MUST operate on the 32-bit key namespace
 * (KEYS32).<br>
 * <br>
 * If the server is a 64-bit registry server and supports both the 32-bit and 64-bit key namespaces, as defined in
 * section 3.1.1.4, the server MUST first check if both the KEY_WOW64_64KEY and KEY_WOW64_32KEY bits are set in the
 * samDesired parameter. If both the KEY_WOW64_64KEY and KEY_WOW64_32KEY are set, the server SHOULD fail the method
 * and return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * Next, the server checks if the KEY_WOW64_32KEY is set in the samDesired parameter. If the KEY_WOW64_32KEY is set in
 * the samDesired parameter, the server MUST open the root key, HKEY_CLASSES_ROOT, in the 32-bit key namespace (KEYS32).
 * If the KEY_WOW64_32KEY is not set in the samDesired parameter, the server MUST open the root key, HKEY_CLASSES_ROOT,
 * in the 64-bit key namespace (KEYS64). If the root key is to be opened in the 32-bit key namespace, the server MUST
 * open the root key in the 32-bit key namespace. The 32-bit key namespace for HKEY_CLASSES_ROOT is stored as a subkey
 * in the 64-bit key namespace in HKEY_CLASSES_ROOT\Wow6432Node.<br>
 * <br>
 * The server MUST validate the value of the samDesired parameter set by the client. If the value of samDesired includes
 * flags set that are not listed in section 2.2.4, the server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * The server attempts to open the root key, HKEY_CLASSES_ROOT, and return a handle to that key in the phKey parameter.
 * The server MUST evaluate the security descriptor that is associated with the key against the requested access that is
 * expressed in the samDesired parameter to determine whether the caller can open this key.<br>
 * <br>
 * If the caller is permitted to open the key, the server MUST return 0 to indicate success and create a new valid
 * context handle. The server MUST store the context handle value in the handle table (HANDLETABLE) along with a mapping
 * to the HKEY_CLASSES_ROOT key. The server MUST place the context handle in the phKey parameter. If the caller does not
 * have access, the server MUST return ERROR_ACCESS_DENIED (5). The server MAY return other values, depending on other
 * failure cases; other values are implementation-specific.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244950">3.1.5.1 OpenClassesRoot (Opnum 0)</a>
 */
public class OpenClassesRoot {
    public final static short OP_NUM = 0;
}
