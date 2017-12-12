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
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>3.1.5.15 BaseRegOpenKey (Opnum 15)</b><br>
 * <br>
 * The BaseRegOpenKey method is called by the client. In response, the server opens a specified key for access and
 * returns a handle to it.
 *
 * <pre>
 * error_status_t BaseRegOpenKey(
 *    [in] RPC_HKEY hKey,
 *    [in] PRRP_UNICODE_STRING lpSubKey,
 *    [in] DWORD dwOptions,
 *    [in] REGSAM samDesired,
 *    [out] PRPC_HKEY phkResult
 * );
 * </pre>
 *
 * hKey: A handle to a key that MUST have been opened previously by using one of the open methods that are specified in
 * section 3.1.5: {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine},
 * {@link OpenPerformanceData}, {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
 * {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.<br>
 * <br>
 * lpSubKey: A pointer to a RRP_UNICODE_STRING structure that MUST contain the name of a key to open. This parameter is
 * always relative to the key that is specified by the hKey parameter and is a pointer to a null-terminated string that
 * contains the name of the subkey to open, as specified in section 3.1.1. This key MUST be an existing subkey of the
 * key that is identified by the hKey parameter.<br>
 * <br>
 * dwOptions: Registry key options. MUST be one of the values specified in Key Types (section 3.1.1.2).<br>
 * <br>
 * samDesired: A bit field that describes the requested security access for the handle to the key that is being opened.
 * It MUST be constructed from one or more of the values that are specified in section 2.2.4.<br>
 * <br>
 * phkResult: A pointer to the handle of the open key. The server MUST return a NULL for phkResult in case of
 * failure.<br>
 * <br>
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code,
 * as specified in the server operation details that follow. Server conditions MAY also result in a nonzero error code
 * as specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF].<br>
 * <br>
 * <b>Server Operations</b><br>
 * <br>
 * In response to this request from the client, for a successful operation, the server MUST open the registry key that
 * is specified by the lpSubKey parameter. In the event of success, the server MUST create a handle to the new key for
 * this request and return the handle value in the phkResult parameter.<br>
 * <br>
 * If hKey is not an open handle to a key on the server, the server MUST fail the method and return
 * ERROR_INVALID_HANDLE.<br>
 * <br>
 * The server then determines which key namespace to operate on (KEYS32 or KEYS64) by inspecting the value of the
 * samDesired parameter. If the server does not support the 64-bit key namespace (see section 3.1.1.4), the server MUST
 * operate on the 32-bit key namespace (KEYS32).<br>
 * <br>
 * If the server is a 64-bit registry server and supports both the 32-bit and 64-bit key namespaces, as defined in
 * section 3.1.1.4, the server MUST first check if both the KEY_WOW64_64KEY and KEY_WOW64_32KEY bits are set in the
 * samDesired parameter. If both KEY_WOW64_64KEY and KEY_WOW64_32KEY are set, the server SHOULD fail the method and
 * return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * The server then checks to see if the key specified by the hKEY parameter is a key that can only be operated on in the
 * 64-bit key namespace (KEYS64). See section 3.1.1.4.<br>
 * <br>
 * If the key specified by the hKey parameter is a key that can only be operated on in the 64-bit key namespace
 * (KEYS64), the server MUST ignore the KEY_WOW64_64KEY and KEY_WOW64_32KEY bits in the samDesired parameter and operate
 * on and create or open the key in the 64-bit namespace (KEYS64).<br>
 * <br>
 * If the key specified by lpSubKey has a KEYTYPE of symbolic link and the client has not set REG_OPTION_OPEN_LINK in
 * the dwOptions parameter, the server MUST return a handle to the key that is the target of the symbolic link (see
 * section 3.1.1.11). The server first checks for a value of the key indicated by lpSubKey named "SymbolicLinkValue". If
 * a value named SymbolicLinkValue is not found, the server MUST fail the method and return ERROR_INVALID_PARAMETER. If
 * the target of the symbolic link does not exist, the server MUST fail the method and return
 * ERROR_INVALID_PARAMETER.<br>
 * <br>
 * If the key specified by lpSubKey has a KEYTYPE of symbolic link and the client has set REG_OPTION_OPEN_LINK in the
 * dwOptions parameter, the server returns a handle to the key that is the source of the symbolic link.<br>
 * <br>
 * If the key specified by lpSubKey has a KEYTYPE of not volatile, and the client has not set the dwOptions parameter to
 * 0x0000000o to indicate not volatile, the server MUST ignore this condition.<br>
 * <br>
 * If the key specified by lpSubKey has a KEYTYPE of volatile, and the client has not set the dwOptions parameter to
 * 0x00000001 to indicate volatile, the server MUST ignore this condition.<br>
 * <br>
 * If the lpSubKey parameter is a pointer to an empty WCHAR array, the method returns a new handle to the same key
 * indicated by the hKey parameter.<br>
 * <br>
 * If lpSubKey is set to NULL by the client, the server MUST fail this method and return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * Next, the server checks if the KEY_WOW64_32KEY is set in the samDesired parameter. If the KEY_WOW64_32KEY is set in
 * the samDesired parameter, the server MUST create the key in the 32-bit key namespace (KEYS32). If the KEY_WOW64_32KEY
 * is not set in the samDesired parameter, the server MUST create the key in the 64-bit key namespace (KEYS64).<br>
 * <br>
 * Next, the server MUST determine if the key path indicated by hKey and lpSubKey refer to a path that is within the
 * subset of registry paths that can support both 64-bit and 32-bit key namespaces (section 3.1.1.4). If the key path
 * indicated by hKey and lpSubKey are within the subset of registry paths that can support both 64-bit and 32-bit key
 * namespaces, the server MUST open the registry key within the appropriate path in the 64-bit key namespace. For
 * example, if hKey refers to HKEY_LOCAL_MACHINE\Software and the value of the lpSubKey parameter is "TEST_KEY" and the
 * server MUST operate on the 32-bit key namespace, then the server MUST open the
 * HKEY_LOCAL_MACHINE\Software\Wow6432Node\TEST_KEY key.<br>
 * <br>
 * The server MUST first validate that the key specified by lpSubKey is a child key of the key specified by hKey. If the
 * key specified by lpSubKey is not a subkey of the key specified by hKey, the server MUST set phkResult to NULL and
 * return ERROR_FILE_NOT_FOUND.<br>
 * <br>
 * The server MUST validate that the client has access to open the key using the security descriptor of the immediate
 * parent key of the key indicated by lpSubKey. The server MUST NOT use the samDesired parameter set by the client to
 * determine access permission. If the value of samDesired includes flags set that are not listed in section 2.2.4, the
 * server MUST return ERROR_INVALID_PARAMETER.<br>
 * <br>
 * If the caller is permitted to open the key, the server MUST return 0 to indicate success, create a new valid context
 * handle, insert it into the handle table (HANDLETABLE), and place the handle value (see 3.1.1.9) in the phKeyResult
 * parameter. If the caller does not have access, the server MUST return ERROR_ACCESS_DENIED (5).<br>
 * <br>
 * The server MUST return 0 to indicate success or an appropriate error code (as specified in Win32Error Codes in
 * [MS-ERREF] or error codes specified in section 2.2.7) to indicate an error.<br>
 * <br>
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, OpenKey
 *     Operation: OpenKey (15)
 *     [Response in frame: 11204]
 *     Pointer to Parent Handle (policy_handle)
 *         Policy Handle: OpenHKLM(&lt;...&gt;)
 *             Parent Handle: 0000000032daf234b77c86409d29efe60d326683
 *             [Frame handle opened: 11176]
 *             [Frame handle closed: 11424]
 *     Keyname: : Software\Microsoft\Windows NT\CurrentVersion
 *         Name Len: 90
 *         Name Size: 90
 *         Keyname: Software\Microsoft\Windows NT\CurrentVersion
 *             Referent ID: 0x00020000
 *             Max Count: 45
 *             Offset: 0
 *             Actual Count: 45
 *             Keyname: Software\Microsoft\Windows NT\CurrentVersion
 *     Options: 0x00000000: (No values set)
 *     Access Mask: 0x02000000
 *         Generic rights: 0x00000000
 *         .... ..1. .... .... .... .... .... .... = Maximum allowed: Set
 *         .... .... 0... .... .... .... .... .... = Access SACL: Not set
 *         Standard rights: 0x00000000
 *         WINREG specific rights: 0x00000000
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/cc244939">3.1.5.15 BaseRegOpenKey (Opnum 15)</a>
 */
public class BaseRegOpenKey extends RequestCall<HandleResponse> {
    /**
     * A handle to a key that MUST have been opened previously by using one of the open methods:
     * {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine}, {@link OpenPerformanceData},
     * {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
     * {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     */
    private final byte[] hKey;
    /**
     * The name of a key to open.
     */
    private final RPCUnicodeString.NullTerminated subKey;
    /**
     * Registry key options. The user rights are represented as a bit field. In addition to the standard user rights, as
     * specified in [MS-DTYP] section 2.4.3, the Windows Remote Registry Protocol SHOULD support the following user
     * rights.
     * <table border="1" summary="">
     * <tr>
     * <td>Value</td>
     * <td>Meaning</td>
     * </tr>
     * <tr>
     * <td>0x00000000</td>
     * <td>This key is not volatile. The key and all its values MUST be persisted to the backing store and is preserved
     * when the registry server loses context due to a system restart, reboot, or shut down process.</td>
     * </tr>
     * <tr>
     * <td>0x00000001</td>
     * <td>This key is volatile. The key with all its subkeys and values MUST NOT be preserved when the registry server
     * loses context due to a system restart, reboot, or shut down process.</td>
     * </tr>
     * <tr>
     * <td>0x00000002</td>
     * <td>This key is a symbolic link to another key. The server stores the target of the symbolic link in an
     * implementation-specific format.</td>
     * </tr>
     * </table>
     */
    private final int options;
    /**
     * A bit field that describes the requested security access for the handle to the key that is being opened.
     * <table border="1" summary="">
     * <tr>
     * <td>Value</td>
     * <td>Meaning</td>
     * </tr>
     * <tr>
     * <td>KEY_QUERY_VALUE (0x00000001)</td>
     * <td>When set, specifies access to query the values of a registry key.</td>
     * </tr>
     * <tr>
     * <td>KEY_SET_VALUE (0x00000002)</td>
     * <td>When set, specifies access to create, delete, or set a registry value.</td>
     * </tr>
     * <tr>
     * <td>KEY_CREATE_SUB_KEY (0x00000004)</td>
     * <td>When set, specifies access to create a subkey of a registry key. Subkeys directly underneath the
     * HKEY_LOCAL_MACHINE and HKEY_USERS predefined keys cannot be created even if this bit is set.</td>
     * </tr>
     * <tr>
     * <td>KEY_ENUMERATE_SUB_KEYS (0x00000008)</td>
     * <td>When set, specifies access to enumerate the subkeys of a registry key.</td>
     * </tr>
     * <tr>
     * <td>KEY_CREATE_LINK (0x00000020)</td>
     * <td>When set, specifies access to create a symbolic link to another key.</td>
     * </tr>
     * <tr>
     * <td>KEY_WOW64_64KEY (0x00000100)</td>
     * <td>When set, indicates that a registry server on a 64-bit operating system operates on the 64-bit key
     * namespace.</td>
     * </tr>
     * <tr>
     * <td>KEY_WOW64_32KEY (0x00000200)</td>
     * <td>When set, indicates that a registry server on a 64-bit operating system operates on the 32-bit key
     * namespace.</td>
     * </tr>
     * </table>
     */
    private final int accessMask;

    /**
     * The BaseRegOpenKey method is called by the client. In response, the server opens a specified key for access and
     * returns a handle to it.
     *
     * @param hKey       A handle to a key that MUST have been opened previously by using one of the open methods:
     *                   {@link OpenClassesRoot}, {@link OpenCurrentUser}, {@link OpenLocalMachine}, {@link OpenPerformanceData},
     *                   {@link OpenUsers}, BaseRegCreateKey, {@link BaseRegOpenKey}, {@link OpenCurrentConfig},
     *                   {@link OpenPerformanceText}, {@link OpenPerformanceNlsText}.
     * @param subKey     The name of a key to open.
     * @param options    Registry key options. The user rights are represented as a bit field. In addition to the standard
     *                   user rights, as specified in [MS-DTYP] section 2.4.3, the Windows Remote Registry Protocol SHOULD support
     *                   the following user rights.
     *                   <table border="1" summary="">
     *                   <tr>
     *                   <td>Value</td>
     *                   <td>Meaning</td>
     *                   </tr>
     *                   <tr>
     *                   <td>0x00000000</td>
     *                   <td>This key is not volatile. The key and all its values MUST be persisted to the backing store and is
     *                   preserved when the registry server loses context due to a system restart, reboot, or shut down
     *                   process.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>0x00000001</td>
     *                   <td>This key is volatile. The key with all its subkeys and values MUST NOT be preserved when the registry
     *                   server loses context due to a system restart, reboot, or shut down process.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>0x00000002</td>
     *                   <td>This key is a symbolic link to another key. The server stores the target of the symbolic link in an
     *                   implementation-specific format.</td>
     *                   </tr>
     *                   </table>
     * @param accessMask A bit field that describes the requested security access for the handle to the key that is
     *                   being opened.
     *                   <table border="1" summary="">
     *                   <tr>
     *                   <td>Value</td>
     *                   <td>Meaning</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_QUERY_VALUE (0x00000001)</td>
     *                   <td>When set, specifies access to query the values of a registry key.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_SET_VALUE (0x00000002)</td>
     *                   <td>When set, specifies access to create, delete, or set a registry value.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_CREATE_SUB_KEY (0x00000004)</td>
     *                   <td>When set, specifies access to create a subkey of a registry key. Subkeys directly underneath the
     *                   HKEY_LOCAL_MACHINE and HKEY_USERS predefined keys cannot be created even if this bit is set.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_ENUMERATE_SUB_KEYS (0x00000008)</td>
     *                   <td>When set, specifies access to enumerate the subkeys of a registry key.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_CREATE_LINK (0x00000020)</td>
     *                   <td>When set, specifies access to create a symbolic link to another key.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_WOW64_64KEY (0x00000100)</td>
     *                   <td>When set, indicates that a registry server on a 64-bit operating system operates on the 64-bit key
     *                   namespace.</td>
     *                   </tr>
     *                   <tr>
     *                   <td>KEY_WOW64_32KEY (0x00000200)</td>
     *                   <td>When set, indicates that a registry server on a 64-bit operating system operates on the 32-bit key
     *                   namespace.</td>
     *                   </tr>
     *                   </table>
     * @see <a href="https://msdn.microsoft.com/en-us/cc244922">2.2.4 REGSAM</a>
     * @see <a href="https://msdn.microsoft.com/en-us/cc230294">2.4.3 ACCESS_MASK</a>
     * @see <a href="https://msdn.microsoft.com/en-us/cc244886">3.1.1.2 Key Types</a>
     */
    public BaseRegOpenKey(final byte[] hKey, final RPCUnicodeString.NullTerminated subKey, final int options, final int accessMask) {
        super((short) 15);
        this.hKey = hKey;
        this.subKey = subKey;
        this.options = options;
        this.accessMask = accessMask;
    }

    @Override
    public HandleResponse getResponseObject() {
        return new HandleResponse();
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
        packetOut.write(hKey);
        // <NDR: struct> [in] PRRP_UNICODE_STRING lpSubKey
        packetOut.writeMarshallable(this.subKey);
        // <NDR: unsigned long> [in] DWORD dwOptions
        packetOut.align(Alignment.FOUR);
        packetOut.writeInt(options);
        // <NDR: unsigned long> [in] REGSAM samDesired
        // Alignment: 4 - Already aligned
        packetOut.writeInt(accessMask);
    }
}
