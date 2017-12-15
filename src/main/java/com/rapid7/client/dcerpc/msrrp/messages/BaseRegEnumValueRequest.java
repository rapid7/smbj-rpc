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
 * <b>3.1.5.11 BaseRegEnumValue (Opnum 10)</b><br>
 * <br>
 * The BaseRegEnumValue method is called by the client. In response, the server enumerates the value at the specified
 * index for the specified registry key.
 *
 * <pre>
 * error_status_t BaseRegEnumValue(
 *    [in] RPC_HKEY hKey,
 *    [in] DWORD dwIndex,
 *    [in] PRRP_UNICODE_STRING lpValueNameIn,
 *    [out] PRPC_UNICODE_STRING lpValueNameOut,
 *    [in, out, unique] LPDWORD lpType,
 *    [in, out, unique, size_is(lpcbData?*lpcbData:0), length_is(lpcbLen?*lpcbLen:0), range(0, 0x4000000)]
 *      LPBYTE lpData,
 *    [in, out, unique] LPDWORD lpcbData,
 *    [in, out, unique] LPDWORD lpcbLen
 * );
 * </pre>
 *
 * hKey: A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
 * section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
 * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
 * {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.<br>
 * <br>
 * dwIndex: MUST be the index of the value to be retrieved, as specified in section 3.1.1.5.<br>
 * <br>
 * lpValueNameIn: A pointer to a RRP_UNICODE_STRING structure that contains the value name to be retrieved, as specified
 * in section 3.1.1. This can be used by the server to determine the maximum length for the output name parameter and to
 * allocate space accordingly. The content is ignored, and only the maximum length is significant.<br>
 * <br>
 * lpValueNameOut: A pointer to a RPC_UNICODE_STRING structure that receives the retrieved value name, as specified in
 * section 3.1.1.<br>
 * <br>
 * lpType: A pointer to a buffer that receives the REG_VALUE_TYPE of the value. This parameter MAY be NULL.<br>
 * <br>
 * lpData: A pointer to a buffer that MUST receive the data of the value entry. This parameter MAY be NULL.<br>
 * <br>
 * lpcbData: A pointer to a variable that MUST contain the size of the buffer that is pointed to by lpData. MUST NOT be
 * NULL if lpData is present.<br>
 * <br>
 * lpcbLen: MUST specify the number of bytes to transmit to the client.<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common error codes
 * are listed in the following table.
 * <table border="1" summary="">
 * <tr>
 * <td>ERROR_ACCESS_DENIED (0x00000005)</td>
 * <td>The caller does not have KEY_QUERY_VALUE access rights.</td>
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
 * <td>ERROR_INSUFFICIENT_BUFFER (0x0000007A)</td>
 * <td>The data area passed to a system call is too small.</td>
 * </tr>
 * <tr>
 * <td>ERROR_MORE_DATA (0x000000EA)</td>
 * <td>More data is available.</td>
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
 * </table>
 * <br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * If the registry server can no longer service registry requests because server shutdown has been initiated
 * (SHUTDOWNINPROGRESS is set to TRUE), the server MUST return ERROR_WRITE_PROTECT.<br>
 * <br>
 * The server MUST first validate that the hKey parameter is currently an open handle which MUST have been opened
 * previously using one of the methods specified in section 3.1.5. If the hKey parameter is not an already opened
 * handle, the server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * In response to this request from the client, for a successful operation, the server MUST return the value and data at
 * the index that is specified by the dwIndex parameter for the key that is specified by the hKey parameter in the
 * client request.<br>
 * <br>
 * Only the maximum length field of the lpValueNameIn is used to determine the buffer length to be allocated by the
 * service. Specify a string with a zero length but maximum length set to the largest buffer size needed to hold the
 * value names.<br>
 * <br>
 * The server MUST return the value name (as specified in section 3.1.1.5) in the lpValueNameOut parameter and the type
 * of the value in the lpType parameter. The type of the value MUST be one of the values that are specified by
 * REG_VALUE_TYPE in section 3.1.1.5.<br>
 * <br>
 * If the request contains a pointer to a buffer in the lpData parameter, the server MUST return the data of the value
 * entry, if present. The lpcbData parameter represents the size of this buffer. If the size is sufficient to hold the
 * data, the server MUST return the number of BYTES that are returned in the lpData parameter. If the size is
 * insufficient to hold the data of the value entry, the server MUST return 122 (ERROR_INSUFFICIENT_BUFFER) to indicate
 * that the buffer was insufficient.<br>
 * <br>
 * The caller MUST have KEY_QUERY_VALUE access rights to invoke this method. For more information, see section
 * 2.2.4.<br>
 * <br>
 * The server MUST return 0 to indicate success, or an appropriate error code (as specified in [MS-ERREF]) to indicate
 * an error.<br>
 * <br>
 * If the caller does not have access, the server MUST return ERROR_ACCESS_DENIED.<br>
 * <br>
 * If the output buffer is too small to contain the value, the server MUST return ERROR_MORE_DATA. The call SHOULD be
 * repeated with a larger output buffer.<br>
 * <br>
 * If the input index is beyond the number of values for a key, the server MUST return ERROR_NO_MORE_ITEMS. This signals
 * the end of enumeration to the caller.<br>
 * <br>
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, EnumValue
 *     Operation: EnumValue (10)
 *     [Response in frame: 8873]
 *     Pointer to Handle (policy_handle)
 *         Policy Handle: OpenKey(Software\Microsoft\Windows NT\CurrentVersion)
 *             Handle: 00000000423134d80b5bb84684c022c1344f8731
 *             [Frame handle opened: 8870]
 *     Enum Index: 0
 *     Pointer to Name (winreg_ValNameBuf)
 *         Name
 *     Pointer to Type (winreg_Type)
 *         Referent ID: 0x00020004
 *         Type
 *     Pointer to Value (uint8)
 *         Referent ID: 0x00020008
 *         Max Count: 65536
 *         Offset: 0
 *         Actual Count: 0
 *     Pointer to Size (uint32)
 *         Referent ID: 0x0002000c
 *         Size: 65536
 *     Pointer to Length (uint32)
 *         Referent ID: 0x00020010
 *         Length: 0
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244934">3.1.5.11 BaseRegEnumValue (Opnum 10)</a>
 */
public class BaseRegEnumValueRequest extends RequestCall<BaseRegEnumValueResponse> {
    /**
     * A handle to a key that MUST have been opened previously by using one of the open methods:
     * {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine}, {@link OpenPerformanceData},
     * {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
     * {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     */
    private final byte[] hKey;
    /**
     * The index of the value to be retrieved.
     */
    private final int index;
    /**
     * The maximum length of the value name to be retrieved.
     */
    private final int valueNameLen;
    /**
     * The maximum length of the value data to be retrieved.
     */
    private final int dataLen;

    /**
     * The BaseRegEnumValue method is called by the client. In response, the server enumerates the value at the
     * specified index for the specified registry key.
     *
     * @param hKey         A handle to a key that MUST have been opened previously by using one of the open methods:
     *                     {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine}, {@link OpenPerformanceData},
     *                     {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
     *                     {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     * @param index        The index of the value to be retrieved.
     * @param valueNameLen The maximum length of the value name to be retrieved.
     * @param dataLen      The maximum length of the value data to be retrieved.
     */
    public BaseRegEnumValueRequest(final byte[] hKey, final int index, final int valueNameLen, final int dataLen) {
        super((short) 10);
        this.hKey = hKey;
        this.index = index;
        this.valueNameLen = valueNameLen;
        this.dataLen = dataLen;
    }

    @Override
    public BaseRegEnumValueResponse getResponseObject() {
        return new BaseRegEnumValueResponse();
    }

    @Override
    public void marshal(final PacketOutput packetOut) throws IOException {
        // Remote Registry Service, OpenKey
        //      Operation: OpenKey (15)
        //      [Response in frame: 11204]
        //      Pointer to Parent Handle (policy_handle)
        //          Policy Handle: OpenHKLM(<...>)
        //              Parent Handle: 0000000032daf234b77c86409d29efe60d326683
        //              [Frame handle opened: 11176]
        //              [Frame handle closed: 11424]
        //      Keyname: : Software\Microsoft\Windows NT\CurrentVersion
        //          Name Len: 90
        //          Name Size: 90
        //          Keyname: Software\Microsoft\Windows NT\CurrentVersion
        //              Referent ID: 0x00020000
        //              Max Count: 45
        //              Offset: 0
        //              Actual Count: 45
        //              Keyname: Software\Microsoft\Windows NT\CurrentVersion
        //      Options: 0x00000000: (No values set)
        //      Access Mask: 0x02000000
        //          Generic rights: 0x00000000
        //          .... ..1. .... .... .... .... .... .... = Maximum allowed: Set
        //          .... .... 0... .... .... .... .... .... = Access SACL: Not set
        //          Standard rights: 0x00000000
        //          WINREG specific rights: 0x00000000
        // <NDR: fixed array> [in] RPC_HKEY hKey
        packetOut.write(this.hKey);
        // <NDR: unsigned long> [in] DWORD dwIndex
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.index);
        // <NDR: conformant varying array> [in] PRRP_UNICODE_STRING lpValueNameIn
        // Alignment: 4 - Already aligned
        packetOut.writeEmptyRPCUnicodeString(this.valueNameLen);
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpType
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
        // <NDR: pointer[conformant varying array]> [in, out, unique, size_is(lpcbData?*lpcbData:0), length_is(lpcbLen?*lpcbLen:0), range(0, 0x4000000)] LPBYTE lpData,
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeEmptyCVArray(this.dataLen);
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpcbData
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.dataLen);
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpcbLen
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
    }
}
