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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantVaryingBuffer;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>3.1.5.17 BaseRegQueryValue (Opnum 17)</b> <br>
 * <br>
 * The BaseRegQueryValue method is called by the client. In response, the server returns the data that is associated
 * with the named value of a specified registry open key. If a value name is not specified, the server returns the data
 * that is associated with the default value of the specified registry open key.
 *
 * <pre>
 * error_status_t BaseRegQueryValue(
 *    [in] RPC_HKEY hKey,
 *    [in] PRRP_UNICODE_STRING lpValueName,
 *    [in, out, unique] LPDWORD lpType,
 *    [in, out, unique, size_is(lpcbData ? *lpcbData :0), length_is(lpcbLen ? *lpcbLen : 0), range(0, 0x4000000)]
 *      LPBYTE lpData,
 *    [in, out, unique] LPDWORD lpcbData,
 *    [in, out, unique] LPDWORD lpcbLen
 * );
 * </pre>
 *
 * hKey: On input, a handle to a key that MUST have been opened previously by using one of the open methods that are
 * specified in section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
 * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
 * {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.<br>
 * <br>
 * lpValueName: On input, the client sets lpValueName to a pointer to a RRP_UNICODE_STRING structure that MUST contain
 * the name of the value, as specified in section 3.1.1. If the client sets lpValueName to NULL, the server MUST fail
 * this method and return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * lpType: On input, the client sets lpType to a pointer to a variable to receive the type code of a value entry. On
 * output, the server MUST set this parameter to NULL if the value specified by the lpValueName parameter is not found.
 * If the client sets lpType to NULL, the server MUST fail this method and return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * lpData: On input, the client sets lpData to a pointer to a buffer to receive the data of the value entry.<br>
 * <br>
 * lpcbData: A pointer to a variable that, on input, contains the size in bytes of the buffer that is pointed to by the
 * lpData parameter. On output, the variable receives the number of bytes that are returned in lpData. This length
 * variable MUST be set to 0 by the server if the client provides NULL for the lpData parameter.<br>
 * <br>
 * If the client sets lpcbData to NULL, the server MUST fail this method and return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * lpcbLen: A pointer to a variable that contains the number of bytes to transmit to the client. On input, the client
 * MUST allocate the memory for this parameter and the pointer value of this parameter MUST not be NULL. On output, the
 * server MUST set this parameter to the size (in bytes) of the buffer pointed to by the lpData parameter. If the client
 * sets lpcbLen to NULL, the server MUST fail this method and return ERROR_INVALID_PARAMETER.<br>
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
 * <td>ERROR_FILE_NOT_FOUND (0x00000002)</td>
 * <td>The value specified by lpValueName was not found. If lpValueName was not specified, the default value has not
 * been defined.</td>
 * </tr>
 * <tr>
 * <td>ERROR_WRITE_PROTECT (0x00000013)</td>
 * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer service
 * registry requests because server shutdown has been initiated.</td>
 * </tr>
 * <tr>
 * <td>ERROR_MORE_DATA (0x000000EA)</td>
 * <td>The data to be returned is larger than the buffer provided.</td>
 * </tr>
 * </table>
 * <br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * If the registry server can no longer service registry requests because server shutdown has been initiated
 * (SHUTDOWNINPROGRESS is set to TRUE), the server MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * In response to this request from the client, for a successful operation, the server MUST return the data that is
 * associated with the value that is specified by the lpValueName parameter for the key that is specified by the hKey
 * parameter.<br>
 * <br>
 * If, on input, the lpValueName parameter in the client request is an empty string, the server MUST return the data
 * that is associated with the default value, as specified in section 3.1.1.5.<br>
 * <br>
 * The server MUST return, on output, a pointer to a variable to specify the value type in the lpType parameter. The
 * value of lpType MUST be one of the values that is specified by REG_VALUE_TYPES (as specified in section 3.1.1.5), or
 * it MUST be NULL.<br>
 * <br>
 * If the client sets the lpValueName parameter to NULL, the server MUST fail the method and return
 * ERROR_INVALID_PARAMETER.<br>
 * <br>
 * If the client sets the lpData parameter to NULL on input, the server assumes the client request is to determine the
 * actual size of the data contained in the value indicated by lpValueName, such that an adequate-sized buffer can be
 * provided by the client in a subsequent call to BaseRegQueryValue. If the client sets the lpData parameter to NULL on
 * input, the server MUST return ERROR_SUCCESS and return the actual size of the data of the value indicated by
 * lpValueName in the lpcbData parameter.<br>
 * <br>
 * The server MUST return, on output, the data that is associated with the specified value in the buffer that is pointed
 * to by the lpData parameter. If the size, in bytes, of the data that is associated with the specified value is too
 * large to fit in the buffer pointed to by the lpData parameter with size specified by the lpcbData parameter, the
 * server MUST return ERROR_MORE_DATA. The server MUST, on output, update the value of the variable pointed to by the
 * lpcbData parameter to the actual size of the data associated with the specified value. This enables the client to
 * determine the correct size of the lpData parameter in a subsequent call to BaseRegQueryValue.<br>
 * <br>
 * The server, on output, MUST return (in the value that is pointed to by the lpcbData parameter) the size in bytes of
 * the data that is returned in the lpData parameter. If the lpData parameter is NULL, the server SHOULD set the value
 * of the lpcbData parameter to NULL.<br>
 * <br>
 * If the server operation is not successful, the server MUST set the value of the variable pointed to by lpcbLen to
 * 0.<br>
 * <br>
 * The caller MUST have KEY_QUERY_VALUE access rights to invoke this method. For more information, see section
 * 2.2.4.<br>
 * <br>
 * If the caller does not have access, the server MUST return ERROR_ACCESS_DENIED.<br>
 * <br>
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, QueryValue
 *     Operation: QueryValue (17)
 *     [Response in frame: 11403]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
 *             Handle: 000000000a665393f4666e49a68cd99f269d020f
 *             [Frame handle opened: 11204]
 *             [Frame handle closed: 11415]
 *     Pointer to Value Name (winreg_String)
 *         Value Name:
 *             Name Len: 30
 *             Name Size: 30
 *             Value Name
 *                 Referent ID: 0x00020000
 *                 Max Count: 15
 *                 Offset: 0
 *                 Actual Count: 15
 *                 Value Name: CurrentVersion
 *     Pointer to Type (winreg_Type)
 *         Referent ID: 0x00020004
 *         Type
 *     Pointer to Data (uint8)
 *         Referent ID: 0x00020008
 *         Max Count: 65536
 *         Offset: 0
 *         Actual Count: 0
 *     Pointer to Data Size (uint32)
 *         Referent ID: 0x0002000c
 *         Data Size: 65536
 *     Pointer to Data Length (uint32)
 *         Referent ID: 0x00020010
 *         Data Length: 0
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244942">3.1.5.17 BaseRegQueryValue (Opnum 17)</a>
 */
public class BaseRegQueryValueRequest extends RequestCall<BaseRegQueryValueResponse> {
    /**
     * A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
     * section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
     * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey},
     * {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     */
    private final byte[] hKey;
    /**
     * The name of the value to be queried.
     */
    private final RPCUnicodeString.NullTerminated valueName;
    /**
     * The maximum number of bytes to accept for the value data.
     */
    private final int dataLen;

    /**
     * The BaseRegQueryValue method is called by the client. In response, the server returns the data that is associated
     * with the named value of a specified registry open key. If a value name is not specified, the server returns the
     * data that is associated with the default value of the specified registry open key.
     *
     * @param hKey      A handle to a key that MUST have been opened previously by using one of the open methods that are
     *                  specified in section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
     *                  {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey},
     *                  {@link OpenCurrentConfig}, {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     * @param valueName The name of the value to be queried.
     * @param dataLen   The maximum number of bytes to accept for the value data.
     */
    public BaseRegQueryValueRequest(final byte[] hKey, final RPCUnicodeString.NullTerminated valueName, final int dataLen) {
        super((short) 17);
        this.hKey = hKey;
        this.valueName = valueName;
        this.dataLen = dataLen;
    }

    @Override
    public BaseRegQueryValueResponse getResponseObject() {
        return new BaseRegQueryValueResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        // Remote Registry Service, QueryValue
        //      Operation: QueryValue (17)
        //      [Response in frame: 11403]
        //      Pointer to Handle (policy_handle)
        //          Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
        //              Handle: 000000000a665393f4666e49a68cd99f269d020f
        //              [Frame handle opened: 11204]
        //              [Frame handle closed: 11415]
        //      Pointer to Value Name (winreg_String)
        //          Value Name:
        //              Name Len: 30
        //              Name Size: 30
        //              Value Name
        //                  Referent ID: 0x00020000
        //                  Max Count: 15
        //                  Offset: 0
        //                  Actual Count: 15
        //                  Value Name: CurrentVersion
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Data (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 65536
        //          Offset: 0
        //          Actual Count: 0
        //      Pointer to Data Size (uint32)
        //          Referent ID: 0x0002000c
        //          Data Size: 65536
        //      Pointer to Data Length (uint32)
        //          Referent ID: 0x00020010
        //          Data Length: 0
        packetOut.write(hKey);
        packetOut.writeMarshallable(this.valueName);
        // Alignment: 4
        packetOut.align(Alignment.FOUR);
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        packetOut.writeEmptyCVArray(this.dataLen);
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.dataLen);
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
    }
}
