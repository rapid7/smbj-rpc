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

import java.nio.ByteBuffer;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.messages.Response;

/**
 * <b>Example:</b>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 72, Call: 37, Ctx: 0, [Req: #11405]
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Response (2)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000
 *     Frag Length: 72
 *     Auth Length: 0
 *     Call ID: 37
 *     Alloc hint: 48
 *     Context ID: 0
 *     Cancel count: 0
 *     Opnum: 16
 *     [Request in frame: 11405]
 *     [Time from request: 0.094128491 seconds]
 * Remote Registry Service, QueryInfoKey
 *     Operation: QueryInfoKey (16)
 *     [Request in frame: 11405]
 *     Pointer to Classname (winreg_String)
 *         Classname:
 *             Name Len: 2
 *             Name Size: 0
 *             NULL Pointer: Classname
 *     Pointer to Num Subkeys (uint32)
 *         Num Subkeys: 6
 *     Pointer to Max Subkeylen (uint32)
 *         Max Subkeylen: 22
 *     Pointer to Max Classlen (uint32)
 *         Max Classlen: 0
 *     Pointer to Num Values (uint32)
 *         Num Values: 0
 *     Pointer to Max Valnamelen (uint32)
 *         Max Valnamelen: 0
 *     Pointer to Max Valbufsize (uint32)
 *         Max Valbufsize: 0
 *     Pointer to Secdescsize (uint32)
 *         Secdescsize: 164
 *     Pointer to Last Changed Time (NTTIME)
 *         Last Changed Time: Jun 21, 2017 12:50:30.686403000 EDT
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class BaseRegQueryInfoKeyResponse extends Response {
    private final int subKeys;
    private final int maxSubKeyLen;
    private final int maxClassLen;
    private final int values;
    private final int maxValueNameLen;
    private final int maxValueLen;
    private final int securityDescriptor;
    private final long lastWriteTime;
    private final int returnValue;

    public BaseRegQueryInfoKeyResponse(final ByteBuffer packet)
        throws TransportException {
        super(packet);
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 72, Call: 37, Ctx: 0, [Req: #11405]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 72
        //      Auth Length: 0
        //      Call ID: 37
        //      Alloc hint: 48
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 16
        //      [Request in frame: 11405]
        //      [Time from request: 0.094128491 seconds]
        // Remote Registry Service, QueryInfoKey
        //      Operation: QueryInfoKey (16)
        //      [Request in frame: 11405]
        //      Pointer to Classname (winreg_String)
        //          Classname:
        //              Name Len: 2
        //              Name Size: 0
        //              NULL Pointer: Classname
        //      Pointer to Num Subkeys (uint32)
        //          Num Subkeys: 6
        //      Pointer to Max Subkeylen (uint32)
        //          Max Subkeylen: 22
        //      Pointer to Max Classlen (uint32)
        //          Max Classlen: 0
        //      Pointer to Num Values (uint32)
        //          Num Values: 0
        //      Pointer to Max Valnamelen (uint32)
        //          Max Valnamelen: 0
        //      Pointer to Max Valbufsize (uint32)
        //          Max Valbufsize: 0
        //      Pointer to Secdescsize (uint32)
        //          Secdescsize: 164
        //      Pointer to Last Changed Time (NTTIME)
        //          Last Changed Time: Jun 21, 2017 12:50:30.686403000 EDT
        //      Windows Error: WERR_OK (0x00000000)
        getStringBuf(true);

        subKeys = getInt();
        maxSubKeyLen = getInt();
        maxClassLen = getInt();
        values = getInt();
        maxValueNameLen = getInt();
        maxValueLen = getInt();
        securityDescriptor = getInt();
        lastWriteTime = getLong();
        returnValue = getInt();
    }

    /** @return The count of the subkeys of the specified key. */
    public int getSubKeys() {
        return subKeys;
    }

    /** @return The size of the key's subkey with the longest name. */
    public int getMaxSubKeyLen() {
        return maxSubKeyLen;
    }

    /** @return The longest string that specifies a subkey class. */
    public int getMaxClassLen() {
        return maxClassLen;
    }

    /** @return The number of values that are associated with the key. */
    public int getValues() {
        return values;
    }

    /** @return The size of the key's longest value name. */
    public int getMaxValueNameLen() {
        return maxValueNameLen;
    }

    /** @return The size in bytes of the longest data component in the key's values. */
    public int getMaxValueLen() {
        return maxValueLen;
    }

    /** @return The size in bytes of the key's SECURITY_DESCRIPTOR. */
    public int getSecurityDescriptor() {
        return securityDescriptor;
    }

    /** @return The time when a value was last written (set or created). */
    public long getLastWriteTime() {
        return lastWriteTime;
    }

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code, as
     *         specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common
     *         error codes are listed in the following table.
     *         <table border="1" summary="">
     *         <tr>
     *         <td>Return value/code</td>
     *         <td>Description</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_ACCESS_DENIED (0x00000005)</td>
     *         <td>The caller does not have KEY_QUERY_VALUE access rights.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_INVALID_PARAMETER (0x00000057)</td>
     *         <td>A parameter is incorrect.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_WRITE_PROTECT (0x00000013)</td>
     *         <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer
     *         service registry requests because server shutdown has been initiated.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_MORE_DATA (0x000000EA)</td>
     *         <td>The size of the buffer is not large enough to hold the requested data.</td>
     *         </tr>
     *         </table>
     */
    public int getReturnValue() {
        return returnValue;
    }
}
