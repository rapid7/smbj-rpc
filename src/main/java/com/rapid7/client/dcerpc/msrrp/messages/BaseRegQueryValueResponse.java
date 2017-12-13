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
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantVaryingByteArray;
import com.rapid7.client.dcerpc.messages.RequestResponse;

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
    private Integer type;
    private RPCConformantVaryingByteArray data;

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
        // <NDR: unsigned long>

        if (packetIn.readReferentID() != 0)
            this.type = packetIn.readInt();
        else
            this.type = null;
        // <NDR: pointer[conformant varying array]>
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0) {
            this.data = new RPCConformantVaryingByteArray();
            packetIn.readUnmarshallable(this.data);
        } else {
            this.data = null;
        }
        // <NDR pointer[unsigned long]
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0)
            packetIn.fullySkipBytes(4);
        // <NDR pointer[unsigned long]
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0)
            packetIn.fullySkipBytes(4);
    }
}
