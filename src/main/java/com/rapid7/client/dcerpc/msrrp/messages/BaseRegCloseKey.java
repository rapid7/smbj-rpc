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

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;

/**
 * <b>3.1.5.6 BaseRegCloseKey (Opnum 5)</b><br>
 * <br>
 * The BaseRegCloseKey method is called by the client. In response, the server destroys (closes) the handle to the
 * specified registry key.
 *
 * <pre>
 * error_status_t BaseRegCloseKey(
 *    [in, out] PRPC_HKEY hKey
 * );
 * </pre>
 *
 * hKey: A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
 * section 3.1.5: OpenClassesRoot, OpenCurrentUser, OpenLocalMachine, OpenPerformanceData, OpenUsers, BaseRegCreateKey,
 * BaseRegOpenKey, OpenCurrentConfig, OpenPerformanceText, OpenPerformanceNlsText.<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common error codes
 * are listed in the following table.<br>
 * <br>
 * <table border="1" summary="">
 * <tr>
 * <td>Return value/code</td>
 * <td>Description</td>
 * </tr>
 * <tr>
 * <td>ERROR_INVALID_HANDLE (0x00000006)</td>
 * <td>The handle is invalid.</td>
 * </tr>
 * <tr>
 * <td>ERROR_BUSY (0x000000AA)</td>
 * <td>The requested resource is in use.</td>
 * </tr>
 * <tr>
 * <td>ERROR_WRITE_PROTECT (0x00000013)</td>
 * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer service
 * registry requests because server shutdown has been initiated.</td>
 * </tr>
 * <tr>
 * <td>ERROR_NOT_READY (0x00000015)</td>
 * <td>The service is not read. Calls can be repeated at a later time.</td>
 * </tr>
 * <tr>
 * <td>WAIT_TIMEOUT (0x00000102)</td>
 * <td>The wait operation timed out.</td>
 * </tr>
 * </table>
 * <br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * If the registry server can no longer service registry requests because server shutdown has been initiated, the server
 * MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * If the handle provided in the hKey parameter is not a valid open handle to a registry key, the server MUST fail the
 * method and return ERROR_INVALID_HANDLE. If the operation was unsuccessful, the server MUST NOT change the value of
 * the hKey parameter and return the original value to the client.<br>
 * <br>
 * If the registry server cannot obtain a lock on a registry request, the server MUST return ERROR_BUSY. The operation
 * SHOULD be repeated.<br>
 * <br>
 * The server MUST determine if the UPDATECOPY column of the entry for hKey in the HANDLETABLE is set to true. If
 * UPDATECOPY is set to true, the server MUST copy all subkeys and values of the key indicated by the hKey parameter
 * from the 32-bit key namespace into the 64-bit key namespace or from the 64-bit key namespace into the 32-bit key
 * namespace. Any values already in the target namespace are overwritten as part of the copy operation. Any errors
 * encountered during the copy operation are not returned to the client, and the result of the copy operation is
 * undefined.<br>
 * <br>
 * In response to this request from the client, for a successful operation, the server MUST return 0 to indicate success
 * and close the handle to the key that is specified by the hKey parameter in the client request. The server MUST also
 * set the value of the hKey parameter to NULL. The server MUST also remove the entry for hKey in the HANDLETABLE.<br>
 * <br>
 * The implementation of the handle close operation is server-specific. However, functionally, after a handle is closed,
 * the server MUST not allow the handle to refer to a given registry key until a new handle is created and opened for
 * that key using one of the open methods that are specified in section 3.1.5: OpenClassesRoot, OpenCurrentUser,
 * OpenLocalMachine, OpenPerformanceData, OpenUsers, BaseRegCreateKey, BaseRegOpenKey, OpenCurrentConfig,
 * OpenPerformanceText, OpenPerformanceNlsText.<br>
 * <br>
 * If the method is unsuccessful, the server MUST return a nonzero error code, as specified in Win32Error Codes in
 * [MS-ERREF].<br>
 * <br>
 * The server MUST return ERROR_BUSY if an internal lock cannot be obtained. This would happen under very high
 * contention rates or if the client is corrupted. The operation SHOULD be repeated.<br>
 * <br>
 * The server MAY return WAIT_TIMEOUT if the server load is high and it is unable to acquire locks on the registry
 * database.<br>
 * <br>
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, CloseKey
 *     Operation: CloseKey (5)
 *     [Response in frame: 11429]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenHKLM(&lt;...&gt;)
 *             Handle: 0000000032daf234b77c86409d29efe60d326683
 *             [Frame handle opened: 11176]
 *             [Frame handle closed: 11424]
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244928">3.1.5.6 BaseRegCloseKey (Opnum 5)</a>
 */
public class BaseRegCloseKey extends RequestCall<HandleResponse> {
    /**
     * A handle to a key that MUST have been opened previously by using one of the open methods: OpenClassesRoot,
     * OpenCurrentUser, OpenLocalMachine, OpenPerformanceData, OpenUsers, BaseRegCreateKey, BaseRegOpenKey,
     * OpenCurrentConfig, OpenPerformanceText, OpenPerformanceNlsText.
     */
    private final ContextHandle hKey;

    /**
     * The BaseRegCloseKey method is called by the client. In response, the server destroys (closes) the handle to the
     * specified registry key.
     *
     * @param hKey A handle to a key that MUST have been opened previously by using one of the open methods:
     *        OpenClassesRoot, OpenCurrentUser, OpenLocalMachine, OpenPerformanceData, OpenUsers, BaseRegCreateKey,
     *        BaseRegOpenKey, OpenCurrentConfig, OpenPerformanceText, OpenPerformanceNlsText.
     */
    public BaseRegCloseKey(final ContextHandle hKey) {
        super((short) 5);

        this.hKey = hKey;
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut)
        throws IOException {
        // Remote Registry Service, CloseKey
        //      Operation: CloseKey (5)
        //      [Response in frame: 11429]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        packetOut.write(hKey.getBytes());
    }
}
