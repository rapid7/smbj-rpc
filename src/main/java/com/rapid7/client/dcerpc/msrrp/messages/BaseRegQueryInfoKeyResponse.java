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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>Example:</b>
 *
 * <pre>
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
public class BaseRegQueryInfoKeyResponse extends RequestResponse {
    private int subKeys;
    private int maxSubKeyLen;
    private int maxClassLen;
    private int values;
    private int maxValueNameLen;
    private int maxValueLen;
    private int securityDescriptor;
    private long lastWriteTime;

    /**
     * @return The count of the subkeys of the specified key.
     */
    public int getSubKeys() {
        return subKeys;
    }

    /**
     * @return The size of the key's subkey with the longest name.
     */
    public int getMaxSubKeyLen() {
        return maxSubKeyLen;
    }

    /**
     * @return The longest string that specifies a subkey class.
     */
    public int getMaxClassLen() {
        return maxClassLen;
    }

    /**
     * @return The number of values that are associated with the key.
     */
    public int getValues() {
        return values;
    }

    /**
     * @return The size of the key's longest value name.
     */
    public int getMaxValueNameLen() {
        return maxValueNameLen;
    }

    /**
     * @return The size in bytes of the longest data component in the key's values.
     */
    public int getMaxValueLen() {
        return maxValueLen;
    }

    /**
     * @return The size in bytes of the key's SECURITY_DESCRIPTOR.
     */
    public int getSecurityDescriptor() {
        return securityDescriptor;
    }

    /**
     * @return The time when a value was last written (set or created).
     */
    public long getLastWriteTime() {
        return lastWriteTime;
    }

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
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
        // <NDR: struct>
        packetIn.readUnmarshallable(new RPCUnicodeString.NullTerminated());
        // <NDR: unsigned long>
        packetIn.align(Alignment.FOUR);
        this.subKeys = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 - Already aligned
        this.maxSubKeyLen = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 - Already aligned
        this.maxClassLen = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 - Already aligned
        this.values = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 - Already aligned
        this.maxValueNameLen = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 - Already aligned
        this.maxValueLen = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 - Already aligned
        this.securityDescriptor = packetIn.readInt();
        // <NDR: unsigned long>
        // Alignment: 4 (2x unsigned long) - Already aligned
        this.lastWriteTime = packetIn.readLong();
    }
}
