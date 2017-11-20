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
import com.rapid7.client.dcerpc.objects.FileTime;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;

/**
 * <b>Example:</b>
 *
 * <pre>
 * Remote Registry Service, EnumKey
 *     Operation: EnumKey (9)
 *     [Request in frame: 8851]
 *     Pointer to Name (winreg_StringBuf)
 *         Name
 *             Length: 24
 *             Size: 512
 *             Pointer to Name (uint16)
 *                 Referent ID: 0x00020000
 *                 Max Count: 256
 *                 Offset: 0
 *                 Actual Count: 12
 *                 Name: 66
 *                 Name: 67
 *                 Name: 68
 *                 Name: 48
 *                 Name: 48
 *                 Name: 48
 *                 Name: 48
 *                 Name: 48
 *                 Name: 48
 *                 Name: 48
 *                 Name: 48
 *                 Name: 0
 *     Pointer to Keyclass (winreg_StringBuf)
 *         Referent ID: 0x00020004
 *         Keyclass
 *             Length: 2
 *             Size: 65534
 *             Pointer to Name (uint16)
 *                 Referent ID: 0x00020008
 *                 Max Count: 32767
 *                 Offset: 0
 *                 Actual Count: 1
 *                 Name: 0
 *     Pointer to Last Changed Time (NTTIME)
 *         Referent ID: 0x0002000c
 *         Last Changed Time: Jun 15, 2017 15:29:36.566813400 EDT
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class BaseRegEnumKeyResponse extends RequestResponse {
    private String name;
    private FileTime lastWriteTime;
    private int returnValue;

    /**
     * @return The name of the retrieved key.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The time when a value was last written (set or created).
     */
    public FileTime getLastWriteTime() {
        return lastWriteTime;
    }

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code, as
     * specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common
     * error codes are listed in the following table.<br>
     * <br>
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
     * <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer
     * service registry requests because server shutdown has been initiated.</td>
     * </tr>
     * <tr>
     * <td>ERROR_MORE_DATA (0x000000EA)</td>
     * <td>The size of the buffer is not large enough to hold the requested data.</td>
     * </tr>
     * </table>
     */
    public int getReturnValue() {
        return returnValue;
    }

    @Override
    public void unmarshal(final PacketInput packetIn) throws IOException {
        // Remote Registry Service, EnumKey
        //      Operation: EnumKey (9)
        //      [Request in frame: 11177]
        //      Pointer to Name (winreg_StringBuf)
        //          Name
        //              Length: 24
        //              Size: 512
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 256
        //                  Offset: 0
        //                  Actual Count: 12
        //                  Name: 66
        //                  Name: 67
        //                  Name: 68
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 48
        //                  Name: 0
        //      Pointer to Keyclass (winreg_StringBuf)
        //          Referent ID: 0x00020004
        //          Keyclass
        //              Length: 2
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020008
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 1
        //                  Name: 0
        //      Pointer to Last Changed Time (NTTIME)
        //          Referent ID: 0x0002000c
        //          Last Changed Time: Jun 15, 2017 15:29:36.566813400 EDT
        //      Windows Error: WERR_OK (0x00000000)
        final String name = packetIn.readStringBuf(true);
        packetIn.readStringBufRef(true);
        final long lastWriteTime = packetIn.readLongRef();

        returnValue = packetIn.readInt();

        if (ERROR_SUCCESS.is(returnValue)) {
            this.name = name;
            this.lastWriteTime = new FileTime(lastWriteTime);
        } else {
            this.name = null;
            this.lastWriteTime = null;
        }
    }
}
