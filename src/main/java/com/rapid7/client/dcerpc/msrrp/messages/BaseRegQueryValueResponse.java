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
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.msrrp.RegistryValueType;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;

/**
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, QueryValue
 *     Operation: QueryValue (17)
 *     [Request in frame: 11394]
 *     Pointer to Type (winreg_Type)
 *         Referent ID: 0x00020000
 *         Type
 *     Pointer to Data (uint8)
 *         Referent ID: 0x00020004
 *         Max Count: 8
 *         Offset: 0
 *         Actual Count: 8
 *         Data: 54
 *         Data: 0
 *         Data: 46
 *         Data: 0
 *         Data: 51
 *         Data: 0
 *         Data: 0
 *         Data: 0
 *     Pointer to Data Size (uint32)
 *         Referent ID: 0x00020008
 *         Data Size: 8
 *     Pointer to Data Length (uint32)
 *         Referent ID: 0x0002000c
 *         Data Length: 8
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class BaseRegQueryValueResponse extends RequestResponse {
    private RegistryValueType type;
    private byte[] data;
    private int returnValue;

    /**
     * @return The {@link RegistryValueType} of the value.
     */
    public RegistryValueType getType() {
        return type;
    }

    /**
     * @return The data of the value entry.
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code, as
     * specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common
     * error codes are listed in the following table.
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
     * <td>The value specified by lpValueName was not found. If lpValueName was not specified, the default value
     * has not been defined.</td>
     * </tr>
     * <tr>
     * <td>ERROR_WRITE_PROTECT (0x00000013)</td>
     * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer
     * service registry requests because server shutdown has been initiated.</td>
     * </tr>
     * <tr>
     * <td>ERROR_MORE_DATA (0x000000EA)</td>
     * <td>The data to be returned is larger than the buffer provided.</td>
     * </tr>
     * </table>
     */
    public int getReturnValue() {
        return returnValue;
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        // Remote Registry Service, QueryValue
        //      Operation: QueryValue (17)
        //      [Request in frame: 11394]
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020000
        //          Type
        //      Pointer to Data (uint8)
        //          Referent ID: 0x00020004
        //          Max Count: 8
        //          Offset: 0
        //          Actual Count: 8
        //          Data: 54
        //          Data: 0
        //          Data: 46
        //          Data: 0
        //          Data: 51
        //          Data: 0
        //          Data: 0
        //          Data: 0
        //      Pointer to Data Size (uint32)
        //          Referent ID: 0x00020008
        //          Data Size: 8
        //      Pointer to Data Length (uint32)
        //          Referent ID: 0x0002000c
        //          Data Length: 8
        //      Windows Error: WERR_OK (0x00000000)
        final int type = packetIn.readIntRef();
        final byte[] data = packetIn.readByteArrayRef();
        packetIn.readIntRef();
        packetIn.readIntRef();

        returnValue = packetIn.readInt();

        if (ERROR_SUCCESS.is(returnValue)) {
            this.type = RegistryValueType.getRegistryValueType(type);
            this.data = data;
        } else {
            this.type = null;
            this.data = null;
        }
    }
}
