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
 * <b>3.1.5.29 OpenPerformanceNlsText (Opnum 33)</b><br>
 * <br>
 * The OpenPerformanceNlsText method is called by the client. In response, the server opens a handle to the
 * HKEY_PERFORMANCE_NLSTEXT predefined key. The HKEY_PERFORMANCE_NLSTEXT predefined key is used to retrieve performance
 * information from a registry server using only the BaseRegQueryInfoKey, BaseRegQueryValue, BaseRegEnumValues and
 * BaseRegCloseKey methods.
 *
 * <pre>
 * error_status_t OpenPerformanceNlsText(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phKey
 * );
 * </pre>
 *
 * ServerName: This SHOULD be sent as NULL and MUST be ignored on receipt because the binding to the server is already
 * complete at this stage.<br>
 * <br>
 * samDesired: This SHOULD be sent as 0 and MUST be ignored on receipt.<br>
 * <br>
 * phKey: A pointer to a variable that receives a handle to the root key HKEY_PERFORMANCE_NLSTEXT, as specified in
 * section 3.1.1.9.<br>
 * <br>
 * Return Values: This method MUST always return a 0 (ERROR_SUCCESS), even in case of errors.
 * <table border="1" summary="">
 * <tr>
 * <td>Return value/code</td>
 * <td>Description</td>
 * </tr>
 * <tr>
 * <td>ERROR_SUCCESS (0)</td>
 * <td>Always returned.</td>
 * </tr>
 * </table>
 * <br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * The server MUST always return 0, even in case of errors.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244955">3.1.5.29 OpenPerformanceNlsText (Opnum 33)</a>
 */
public class OpenPerformanceNlsText {
    public final static short OP_NUM = 33;
}
