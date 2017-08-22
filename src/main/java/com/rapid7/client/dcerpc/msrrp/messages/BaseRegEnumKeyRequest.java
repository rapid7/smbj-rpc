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

import com.rapid7.client.dcerpc.messages.Request;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;

/**
 * <b>3.1.5.10 BaseRegEnumKey (Opnum 9)</b><br>
 * <br>
 * The BaseRegEnumKey method is called by the client in order to enumerate a subkey. In response, the server returns a
 * requested subkey.<br>
 *
 * <pre>
 * error_status_t BaseRegEnumKey(
 *    [in] RPC_HKEY hKey,
 *    [in] DWORD dwIndex,
 *    [in] PRRP_UNICODE_STRING lpNameIn,
 *    [out] PRRP_UNICODE_STRING lpNameOut,
 *    [in, unique] PRRP_UNICODE_STRING lpClassIn,
 *    [out] PRPC_UNICODE_STRING* lplpClassOut,
 *    [in, out, unique] PFILETIME lpftLastWriteTime
 * );
 * </pre>
 *
 * hKey: A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
 * section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
 * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey},
 * {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.<br>
 * <br>
 * dwIndex: The index of the subkey to retrieve, as specified in section 3.1.1.1.<br>
 * <br>
 * lpNameIn: A pointer to a RRP_UNICODE_STRING structure that contains the name of the key to retrieve, as specified in
 * section 3.1.1.<br>
 * <br>
 * lpNameOut: A pointer to a RRP_UNICODE_STRING structure that receives the name of the retrieved key, as specified in
 * section 3.1.1.<br>
 * <br>
 * lpClassIn: A pointer to a RRP_UNICODE_STRING structure that contains the class of this key, as specified in section
 * 3.1.1.6. This parameter MAY be NULL. This string is optional, is not used by the registry, is saved, and can be
 * retrieved using BaseRegQueryInfoKey.<br>
 * <br>
 * lplpClassOut: A pointer to a PRPC_UNICODE_STRING structure that receives the class of the retrieved key, as specified
 * in section 3.1.1.6. This parameter MAY be NULL.<br>
 * <br>
 * lpftLastWriteTime: MUST be the time when the value was last written (set or created).<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common error codes
 * are listed in the following table.
 * <table border="1" summary="">
 * <tr>
 * <td>Return value/code</td>
 * <td>Description</td>
 * <tr>
 * <tr>
 * <td>ERROR_ACCESS_DENIED (0x00000005)</td>
 * <td>The caller does not have KEY_ENUMERATE_SUB_KEYS access rights.</td>
 * </tr>
 * <tr>
 * <td>ERROR_OUTOFMEMORY (0x0000000E)</td>
 * <td>Not enough storage is available to complete this operation.</td>
 * </tr>
 * <tr>
 * <td>ERROR_INVALID_PARAMETER (0x00000057)</td>
 * <td>A parameter is incorrect.</td>
 * </tr>
 * <tr>
 * <td>ERROR_NO_MORE_ITEMS (0x00000103)</td>
 * <td>No more data is available.</td>
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
 * If the registry server can no longer service registry requests because server shutdown has been initiated, the server
 * MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * If the dwIndex parameter is beyond the range of subkeys, the server MUST return ERROR_NO_MORE_ITEMS to indicate that
 * enumeration is complete.<br>
 * <br>
 * If the lplpClassOut parameter does not contain enough space for the class name, the server MUST return
 * ERROR_MORE_DATA.<br>
 * <br>
 * The server MUST first validate that the hKey parameter is currently an open handle which MUST have been opened
 * previously using one of the methods specified in section 3.1.5. If the hKey parameter is not an already opened
 * handle, the server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * The lpNameIn parameter specifies (in the MaxmimumLength member of the RRP_UNICODE_STRING structure) the length of the
 * buffer allocated by the RPC client. This string is transferred as an in parameter to the server. Its maximum length
 * is used to allocate the output Unicode string (lpNameOut) that transfers data back to the client.<br>
 * <br>
 * In response to this request from the client, for a successful operation, the server MUST return the subkey at the
 * index that is specified by the dwIndex parameter for the key that is specified by the hKey parameter.<br>
 * <br>
 * The server MUST copy the name of the retrieved subkey (as specified in section 3.1.1.1), including the terminating
 * null character, to the buffer that is pointed to by the lpNameOut parameter in the client request. The server MUST
 * not copy the full key hierarchy to the buffer. If a class is associated with the key, the server MUST copy this class
 * to the buffer that is pointed to by the lpClassOut parameter. The server MUST return the time a value was last
 * modified in the lpftLastWriteTime parameter.<br>
 * <br>
 * The caller MUST have KEY_ENUMERATE_SUB_KEYS access rights to invoke this method. For more information, see section
 * 2.2.4.<br>
 * <br>
 * The server MUST return 0 to indicate success or an appropriate error code (as specified in [MS-ERREF]) to indicate an
 * error.<br>
 * <br>
 * If the caller does not have access, the server MUST return ERROR_ACCESS_DENIED.<br>
 * <br>
 * <b>Example:</b>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Request, Fragment: Single, FragLen: 104, Call: 2, Ctx: 0, [Resp: #8852]
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Request (0)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000
 *     Frag Length: 104
 *     Auth Length: 0
 *     Call ID: 2
 *     Alloc hint: 104
 *     Context ID: 0
 *     Opnum: 9
 *     [Response in frame: 8852]
 * Remote Registry Service, EnumKey
 *     Operation: EnumKey (9)
 *     [Response in frame: 8852]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenHKLM(&lt;...&gt;)
 *             Handle: 000000004c3bf85db467534fb14529ea2e918e65
 *             [Frame handle opened: 8850]
 *     Enum Index: 0
 *     Pointer to Name (winreg_StringBuf)
 *         Name
 *             Length: 0
 *             Size: 512
 *             Pointer to Name (uint16)
 *                 Referent ID: 0x00020000
 *                 Max Count: 256
 *                 Offset: 0
 *                 Actual Count: 0
 *     Pointer to Keyclass (winreg_StringBuf)
 *         Referent ID: 0x00020004
 *         Keyclass
 *             Length: 0
 *             Size: 65534
 *             Pointer to Name (uint16)
 *                 Referent ID: 0x00020008
 *                 Max Count: 32767
 *                 Offset: 0
 *                 Actual Count: 0
 *     Pointer to Last Changed Time (NTTIME)
 *         Referent ID: 0x0002000c
 *         Last Changed Time: No time specified (0)
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244933">3.1.5.10 BaseRegEnumKey (Opnum 9)</a>
 */
public class BaseRegEnumKeyRequest extends Request<BaseRegEnumKeyResponse> {
    /**
     * The BaseRegEnumKey method is called by the client in order to enumerate a subkey. In response, the server returns
     * a requested subkey.
     *
     * @param hKey A handle to a key that MUST have been opened previously by using one of the open methods:
     *        {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine}, {@link OpenPerformanceData},
     *        {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
     *        {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     * @param index The index of the subkey to retrieve.
     * @param nameLen The maximum length of the subkey name to retrieve.
     * @param classLen The maximum length of the subkey class to retrieve.
     */
    public BaseRegEnumKeyRequest(final ContextHandle hKey, final int index, final int nameLen, final int classLen) {
        super((short) 9);
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Response in frame: 11178]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Enum Index: 0
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 0
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 0
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: No time specified (0)
        putBytes(hKey.getBytes());
        putInt(index);
        putStringBuffer(nameLen);
        putStringBufferRef(classLen);
        putLongRef(Long.valueOf(0));
    }

    @Override
    protected BaseRegEnumKeyResponse parsePDUResponse(final ByteBuffer responseBuffer)
        throws TransportException {
        return new BaseRegEnumKeyResponse(responseBuffer);
    }
}
