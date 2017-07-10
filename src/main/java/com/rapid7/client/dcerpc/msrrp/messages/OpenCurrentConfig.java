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
 * <b>3.1.5.25 OpenCurrentConfig (Opnum 27)</b><br>
 * <br>
 * The OpenCurrentConfig method is called by the client. In response, the server attempts to open a handle to the
 * HKEY_CURRENT_CONFIG predefined key.
 *
 * <pre>
 * error_status_t OpenCurrentConfig(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phKey
 * );
 * </pre>
 *
 * ServerName: This SHOULD be sent as NULL and MUST be ignored on receipt because the binding to the server is already
 * complete at this stage.<br>
 * <br>
 * samDesired: A bit field that describes the wanted security access for the key. It MUST be constructed from one or
 * more of the values that are specified in section 2.2.4.<br>
 * <br>
 * phKey: A handle to the root key, HKEY_CURRENT_CONFIG, as specified in section 3.1.1.<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in [MS-ERREF] section 2.2. The most common error codes are listed in the following table.
 * <table border="1">
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
 * If the registry server can no longer service registry requests because server shutdown has been initiated, the
 * server MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * The server attempts to open the root key, HKEY_CURRENT_CONFIG, and create a new valid context handle. The server
 * MUST store the context handle value in the handle table (HANDLETABLE) along with a mapping to the
 * HKEY_CURRENT_CONFIG key. The server MUST return the handle to that key in the phKey parameter. The server MUST
 * evaluate the security descriptor that is associated with the key against the requested access that is expressed in
 * the samDesired parameter to determine whether the caller has the authority to open this key.<br>
 * <br>
 * If the caller is permitted to open the key, the server MUST return 0 to indicate success and place a valid context
 * handle in the phKey parameter. If the caller does not have access, the server MUST return ERROR_ACCESS_DENIED (5).
 * The server MAY return other values depending on other failure cases; other values are implementation-specific.<br>
 * <br>
 * The server validates the value of the samDesired parameter set by the client. If the value of samDesired includes
 * flags set which are not listed in section 2.2.4, the server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 *
 * @see {@link <a href="https://msdn.microsoft.com/en-us/cc244951">3.1.5.25 OpenCurrentConfig (Opnum 27)</a>}
 */
public class OpenCurrentConfig {
    public final static short OP_NUM = 27;
}
