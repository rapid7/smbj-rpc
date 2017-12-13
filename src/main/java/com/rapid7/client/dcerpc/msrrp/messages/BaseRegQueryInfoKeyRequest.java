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

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;

/**
 * <b>3.1.5.16 BaseRegQueryInfoKey (Opnum 16)</b><br>
 * <br>
 * The BaseRegQueryInfoKey method is called by the client. In response, the server returns relevant information on the
 * key that corresponds to the specified key handle.
 *
 * <pre>
 * error_status_t BaseRegQueryInfoKey(
 *    [in] RPC_HKEY hKey,
 *    [in] PRRP_UNICODE_STRING lpClassIn,
 *    [out] PRPC_UNICODE_STRING lpClassOut,
 *    [out] LPDWORD lpcSubKeys,
 *    [out] LPDWORD lpcbMaxSubKeyLen,
 *    [out] LPDWORD lpcbMaxClassLen,
 *    [out] LPDWORD lpcValues,
 *    [out] LPDWORD lpcbMaxValueNameLen,
 *    [out] LPDWORD lpcbMaxValueLen,
 *    [out] LPDWORD lpcbSecurityDescriptor,
 *    [out] PFILETIME lpftLastWriteTime
 * );
 * </pre>
 *
 * hKey: A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
 * section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
 * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
 * {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.<br>
 * <br>
 * lpClassIn: A pointer to a RRP_UNICODE_STRING structure that contains the class of the key to be retrieved, as
 * specified in section 3.1.1.6. This string is optional; it is saved but is not used by the registry.<br>
 * <br>
 * lpClassOut: A pointer to a RPC_UNICODE_STRING structure that receives the class of this key, as specified in section
 * 3.1.1.6.<br>
 * <br>
 * lpcSubKeys: A pointer to a DWORD that MUST receive the count of the subkeys of the specified key.<br>
 * <br>
 * lpcbMaxSubKeyLen: A pointer to a DWORD that receives the size of the key's subkey with the longest name, or a greater
 * size, as the number of TCHAR elements.<br>
 * <br>
 * TCHAR elements are defined as follows:<br>
 * #ifdef UNICODE<br>
 * #typedef WCHAR TCHAR;<br>
 * #endif<br>
 * <br>
 * lpcbMaxClassLen: A pointer to a DWORD that receives the size of the longest string that specifies a subkey class, in
 * Unicode characters.<br>
 * <br>
 * lpcValues: A pointer to a DWORD that receives the number of values that are associated with the key.<br>
 * <br>
 * lpcbMaxValueNameLen: A pointer to a DWORD that receives the size of the key's longest value name, or a greater size,
 * as the number of TCHAR elements.<br>
 * <br>
 * lpcbMaxValueLen: A pointer to a DWORD that receives the size in bytes of the longest data component in the key's
 * values.<br>
 * <br>
 * lpcbSecurityDescriptor: A pointer to a DWORD that receives the size in bytes of the key's SECURITY_DESCRIPTOR.<br>
 * <br>
 * lpftLastWriteTime: A pointer to a FILETIME structure that receives the last write time.<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common error codes
 * are listed in the following table.
 * <table border="1" summary="">
 * <tr>
 * <td>Return value/code</td>
 * <td>Description</td>
 * </tr>
 * <tr>
 * <td>ERROR_ACCESS_DENIED (0x00000005)</td>
 * <td>The caller does not have KEY_QUERY_VALUE access rights.</td>
 * </tr>
 * <tr>
 * <td>ERROR_INVALID_PARAMETER (0x00000057)</td>
 * <td>A parameter is incorrect.</td>
 * </tr>
 * <tr>
 * <td>ERROR_WRITE_PROTECT (0x00000013)</td>
 * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer service
 * registry requests because server shutdown has been initiated.</td>
 * </tr>
 * <tr>
 * <td>ERROR_MORE_DATA (0x000000EA)</td>
 * <td>The size of the buffer is not large enough to hold the requested data.</td>
 * </tr>
 * </table>
 * <br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * If the registry server can no longer service registry requests because server shutdown has been initiated
 * (SHUTDOWNINPROGRESS is set to TRUE), the server MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * In response to this request from the client, for a successful operation, the server returns information for the
 * specified registry key.<br>
 * <br>
 * The server MUST return the class that is associated with the key in the lpClassOut parameter. The key's class can be
 * NULL.<br>
 * <br>
 * The server MUST return a pointer to the variable that contains the number of subkeys for the specified key in the
 * lpcSubkeys parameter. If there are no subkeys under the key indicated by hKey, the server MUST set this value to
 * 0.<br>
 * <br>
 * The server MUST return a pointer to the variable that contains the number of values associated with the key in the
 * lpcValues parameter. If there are no values under the key indicated by hKey, the server MUST set this value to 0.<br>
 * <br>
 * The server MUST return a pointer to the variable that contains the size (as the number of TCHAR elements) of the
 * key's longest value name in the lpcbMaxValueNameLen parameter. This size MUST NOT include the terminating null
 * character. If there are no values under the key indicated by hKey, the server MUST set this value to 0.<br>
 * <br>
 * The server MUST return a pointer to the variable that contains the size in bytes of the longest data component in the
 * key's values in the lpcbMaxValueLen parameter. If there are no subkeys under the key indicated by hKey, the server
 * MUST set this value to 0.<br>
 * <br>
 * The server MUST return a pointer to the variable that contains the size in bytes of the key's SECURITY_DESCRIPTOR in
 * the lpcbSecurityDescriptor parameter.<br>
 * <br>
 * The server MUST return a pointer to the FILETIME structure that specifies the last modification time of the key in
 * the lpftLastWriteTime parameter.<br>
 * <br>
 * The caller MUST have KEY_QUERY_VALUE access rights to invoke this method. For more information, see section
 * 2.2.4.<br>
 * <br>
 * The server MUST return 0 to indicate success or an appropriate error code (as specified in [MS-ERREF]) to indicate an
 * error.<br>
 * <br>
 * If the lpClassOut parameter does not contain enough space for the class name, the server MUST return
 * ERROR_MORE_DATA.<br>
 * <br>
 * If any one of the parameters lpcSubKeys, lpcbMaxSubKeyLen, lpcValues, lpcbMaxValueNameLen, lpcbMaxValueLen, or
 * lpftLastWriteTime is NULL the server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * If the caller does not have access, the server MUST return ERROR_ACCESS_DENIED.<br>
 * <br>
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, QueryInfoKey
 *     Operation: QueryInfoKey (16)
 *     [Response in frame: 11412]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenHKLM(&lt;...&gt;)
 *             Handle: 0000000032daf234b77c86409d29efe60d326683
 *             [Frame handle opened: 11176]
 *             [Frame handle closed: 11424]
 *     Pointer to Classname (winreg_String)
 *         Classname:
 *             Name Len: 0
 *             Name Size: 0
 *             Classname
 *                 Referent ID: 0x00020000
 *                 Max Count: 0
 *                 Offset: 0
 *                 Actual Count: 0
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244940">3.1.5.16 BaseRegQueryInfoKey (Opnum 16)</a>
 */
public class BaseRegQueryInfoKeyRequest extends RequestCall<BaseRegQueryInfoKeyResponse> {
    /**
     * A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
     * section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
     * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey},
     * {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     */
    private final byte[] hKey;

    /**
     * The BaseRegQueryInfoKey method is called by the client. In response, the server returns relevant information on
     * the key that corresponds to the specified key handle.
     *
     * @param hKey A handle to a key that MUST have been opened previously by using one of the open methods that are
     *             specified in section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
     *             {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey},
     *             {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     */
    public BaseRegQueryInfoKeyRequest(final byte[] hKey) {
        super((short) 16);

        this.hKey = hKey;
    }

    @Override
    public BaseRegQueryInfoKeyResponse getResponseObject() {
        return new BaseRegQueryInfoKeyResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        // Remote Registry Service, QueryInfoKey
        //      Operation: QueryInfoKey (16)
        //      [Response in frame: 11412]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Pointer to Classname (winreg_String)
        //          Classname:
        //              Name Len: 0
        //              Name Size: 0
        //              Classname
        //                  Referent ID: 0x00020000
        //                  Max Count: 0
        //                  Offset: 0
        //                  Actual Count: 0
        // <NDR: fixed array> [in] RPC_HKEY hKey
        packetOut.write(hKey);
        // <NDR: struct> [in] PRRP_UNICODE_STRING lpClassIn
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        packetOut.writeEmptyRPCUnicodeString(0);
    }
}
