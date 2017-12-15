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
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantVaryingByteArray;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;

/**
 * <b>Example:</b>
 *
 * <pre>
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
public class BaseRegEnumValueResponse extends RequestResponse {
    private RPCUnicodeString.NullTerminated name;
    private Integer type;
    private RPCConformantVaryingByteArray data;

    /**
     * @return The retrieved value name.
     */
    public RPCUnicodeString.NullTerminated getName() {
        return name;
    }

    /**
     * @return The type of the value.
     */
    public Integer getType() {
        return type;
    }

    /**
     * @return The data of the value entry.
     */
    public RPCConformantVaryingByteArray getData() {
        return data;
    }

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
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
        // <NDR: struct> [out] PRPC_UNICODE_STRING lpValueNameOut
        this.name = new RPCUnicodeString.NullTerminated();
        packetIn.readUnmarshallable(this.name);
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpType
        packetIn.align(Alignment.FOUR);
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0)
            this.type = packetIn.readInt();
        else
            this.type = null;
        // <NDR: pointer[conformant varying array]> [in, out, unique, size_is(lpcbData?*lpcbData:0), length_is(lpcbLen?*lpcbLen:0), range(0, 0x4000000)] LPBYTE lpData,
        // Alignment: 4 - Already aligned
        packetIn.readReferentID();
        this.data = new RPCConformantVaryingByteArray();
        packetIn.readUnmarshallable(this.data);
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpcbData
        packetIn.align(Alignment.FOUR);
        packetIn.fullySkipBytes(8);
        // <NDR: pointer[unsigned long]> [in, out, unique] LPDWORD lpcbLen
        // Alignment: 4 - Already aligned
        packetIn.fullySkipBytes(8);
    }
}
