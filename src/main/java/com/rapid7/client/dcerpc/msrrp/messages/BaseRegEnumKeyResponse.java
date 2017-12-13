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
    // <NDR: struct> [out] PRRP_UNICODE_STRING lpNameOut
    private RPCUnicodeString.NullTerminated lpNameOut;
    // <NDR: pointer[struct]> [out] PRPC_UNICODE_STRING* lplpClassOut
    private RPCUnicodeString.NullTerminated lpClassOut;
    private long lastWriteTime;

    /**
     * @return The name of the retrieved key.
     */
    public RPCUnicodeString.NullTerminated getLpNameOut() {
        return lpNameOut;
    }

    /**
     * @return The class of the retrieved key. This parameter can be NULL.
     */
    public RPCUnicodeString.NullTerminated getLpClassOut() {
        return lpClassOut;
    }

    /**
     * @return The time when a value was last written (set or created).
     */
    public long getLastWriteTime() {
        return lastWriteTime;
    }

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
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
        // <NDR: struct> [out] PRRP_UNICODE_STRING lpNameOut
        this.lpNameOut = new RPCUnicodeString.NullTerminated();
        packetIn.readUnmarshallable(this.lpNameOut);
        // <NDR: pointer[struct]> [out] PRPC_UNICODE_STRING* lplpClassOut
        packetIn.align(Alignment.FOUR);
        if (packetIn.readReferentID() != 0) {
            this.lpClassOut = new RPCUnicodeString.NullTerminated();
            packetIn.readUnmarshallable(this.lpClassOut);
            // Alignment for lpftLastWriteTime
            packetIn.align(Alignment.FOUR);
        } else {
            this.lpClassOut = null;
            // Alignment for lpftLastWriteTime not necessary
        }
        if (packetIn.readReferentID() != 0) {
            // Alignment: 4 - Already aligned - This is a struct of 2x 4bytes
            this.lastWriteTime = packetIn.readLong();
        } else {
            this.lastWriteTime = 0L;
        }
    }
}
