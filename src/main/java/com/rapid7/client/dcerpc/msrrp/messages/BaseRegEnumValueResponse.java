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

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;
import java.nio.ByteBuffer;
import com.hierynomus.smbj.transport.TransportException;
import com.rapid7.client.dcerpc.messages.Response;
import com.rapid7.client.dcerpc.msrrp.RegistryValueType;

/**
 * <b>Example:</b>
 *
 * <pre>
 * Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 136, Call: 10, Ctx: 0, [Req: #8871]
 *     Version: 5
 *     Version (minor): 0
 *     Packet type: Response (2)
 *     Packet Flags: 0x03
 *     Data Representation: 10000000
 *     Frag Length: 136
 *     Auth Length: 0
 *     Call ID: 10
 *     Alloc hint: 112
 *     Context ID: 0
 *     Cancel count: 0
 *     Opnum: 10
 *     [Request in frame: 8871]
 *     [Time from request: 0.092454264 seconds]
 * Remote Registry Service, EnumValue
 *     Operation: EnumValue (10)
 *     [Request in frame: 8871]
 *     Pointer to Name (winreg_ValNameBuf)
 *         Name
 *     Pointer to Type (winreg_Type)
 *         Referent ID: 0x00020004
 *         Type
 *     Pointer to Value (uint8)
 *         Referent ID: 0x00020008
 *         Max Count: 22
 *         Offset: 0
 *         Actual Count: 22
 *         Value: 67
 *         Value: 0
 *         Value: 58
 *         Value: 0
 *         Value: 92
 *         Value: 0
 *         Value: 87
 *         Value: 0
 *         Value: 105
 *         Value: 0
 *         Value: 110
 *         Value: 0
 *         Value: 100
 *         Value: 0
 *         Value: 111
 *         Value: 0
 *         Value: 119
 *         Value: 0
 *         Value: 115
 *         Value: 0
 *         Value: 0
 *         Value: 0
 *     Pointer to Size (uint32)
 *         Referent ID: 0x0002000c
 *         Size: 22
 *     Pointer to Length (uint32)
 *         Referent ID: 0x00020010
 *         Length: 22
 *     Windows Error: WERR_OK (0x00000000)
 * </pre>
 */
public class BaseRegEnumValueResponse extends Response {
    private final String name;
    private final RegistryValueType type;
    private final byte[] data;
    private final int returnValue;

    public BaseRegEnumValueResponse(final ByteBuffer packet)
        throws TransportException {
        super(packet);
        // Distributed Computing Environment / Remote Procedure Call (DCE/RPC) Response, Fragment: Single, FragLen: 136, Call: 10, Ctx: 0, [Req: #11206]
        //      Version: 5
        //      Version (minor): 0
        //      Packet type: Response (2)
        //      Packet Flags: 0x03
        //      Data Representation: 10000000
        //      Frag Length: 136
        //      Auth Length: 0
        //      Call ID: 10
        //      Alloc hint: 112
        //      Context ID: 0
        //      Cancel count: 0
        //      Opnum: 10
        //      [Request in frame: 11206]
        //      [Time from request: 0.092563019 seconds]
        // Remote Registry Service, EnumValue
        //      Operation: EnumValue (10)
        //      [Request in frame: 11206]
        //      Pointer to Name (winreg_ValNameBuf)
        //          Name
        //              Length: 22
        //              Size: 65534
        //              Pointer to Name (uint16)
        //                  Referent ID: 0x00020000
        //                  Max Count: 32767
        //                  Offset: 0
        //                  Actual Count: 11
        //                  Name: 83
        //                  Name: 121
        //                  Name: 115
        //                  Name: 116
        //                  Name: 101
        //                  Name: 109
        //                  Name: 82
        //                  Name: 111
        //                  Name: 111
        //                  Name: 116
        //                  Name: 0
        //      Pointer to Type (winreg_Type)
        //          Referent ID: 0x00020004
        //          Type
        //      Pointer to Value (uint8)
        //          Referent ID: 0x00020008
        //          Max Count: 22
        //          Offset: 0
        //          Actual Count: 22
        //          Value: 67
        //          Value: 0
        //          Value: 58
        //          Value: 0
        //          Value: 92
        //          Value: 0
        //          Value: 87
        //          Value: 0
        //          Value: 105
        //          Value: 0
        //          Value: 110
        //          Value: 0
        //          Value: 100
        //          Value: 0
        //          Value: 111
        //          Value: 0
        //          Value: 119
        //          Value: 0
        //          Value: 115
        //          Value: 0
        //          Value: 0
        //          Value: 0
        //      Pointer to Size (uint32)
        //          Referent ID: 0x0002000c
        //          Size: 22
        //      Pointer to Length (uint32)
        //          Referent ID: 0x00020010
        //          Length: 22
        //      Windows Error: WERR_OK (0x00000000)
        final String name = getStringBuf(true);
        final int type = getIntRef();
        final byte[] data = getByteArrayRef();

        getIntRef();
        getIntRef();

        returnValue = getInt();

        if (ERROR_SUCCESS.is(returnValue)) {
            this.name = name;
            this.type = RegistryValueType.getRegistryValueType(type);
            this.data = data;
        } else {
            this.name = null;
            this.type = null;
            this.data = null;
        }
    }

    /** @return The retrieved value name. */
    public String getName() {
        return name;
    }

    /** @return The {@link RegistryValueType} of the value. */
    public RegistryValueType getType() {
        return type;
    }

    /** @return The data of the value entry. */
    public byte[] getData() {
        return data;
    }

    /**
     * @return The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code, as
     *         specified in {@link com.rapid7.client.dcerpc.mserref.SystemErrorCode} in [MS-ERREF]. The most common
     *         error codes are listed in the following table.<br>
     *         <br>
     *         <table border="1" summary="">
     *         <tr>
     *         <td>ERROR_ACCESS_DENIED (0x00000005)</td>
     *         <td>The caller does not have KEY_QUERY_VALUE access rights.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_OUTOFMEMORY (0x0000000E)</td>
     *         <td>Not enough storage is available to complete this operation.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_INVALID_PARAMETER (0x00000057)</td>
     *         <td>A parameter is incorrect.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_INSUFFICIENT_BUFFER (0x0000007A)</td>
     *         <td>The data area passed to a system call is too small.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_MORE_DATA (0x000000EA)</td>
     *         <td>More data is available.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_NO_MORE_ITEMS (0x00000103)</td>
     *         <td>No more data is available.</td>
     *         </tr>
     *         <tr>
     *         <td>ERROR_WRITE_PROTECT (0x00000013)</td>
     *         <td>A read or write operation was attempted to a volume after it was dismounted. The server can no longer
     *         service registry requests because server shutdown has been initiated.</td>
     *         </tr>
     *         </table>
     */
    public int getReturnValue() {
        return returnValue;
    }
}
