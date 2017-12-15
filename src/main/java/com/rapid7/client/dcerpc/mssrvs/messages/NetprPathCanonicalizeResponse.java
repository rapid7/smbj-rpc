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
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestResponse;

/**
 * Server Service, NetPathCanonicalize
 *   Operation: NetPathCanonicalize (31)
 *   Count: 200
 *   Path: C:\this\works
 *   Path Type: ITYPE_PATH_ABSD (0x00002006)
 *   Windows Error: WERR_OK (0x00000000)
 *
 * Note: Wireshark unable to read packet correctly
 */

public class NetprPathCanonicalizeResponse extends RequestResponse {
    private String outBuf;
    private int pathType;

    @Override
    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
        outBuf = readChars(packetIn);
        packetIn.align(Alignment.FOUR);
        pathType = packetIn.readInt();
    }

    public String getOutBuf() {
        return outBuf;
    }

    public int getPathType() {
        return this.pathType;
    }

    private String readChars(final PacketInput packetIn) throws IOException {
        // Corresponds to [in, range(0,64000)] DWORD OutbufLen
        final int byteCount = packetIn.readInt();
        // UTF-16 string
        final int charCount = byteCount / 2;
        final StringBuilder result = new StringBuilder(charCount);
        int offset = 0;
        while (offset < charCount) {
            offset++;
            final char currentChar = (char) packetIn.readShort();
            // Null terminator indicates end of response content, but not end of buffer
            if (currentChar == 0)
                break;
            result.append(currentChar);
        }
        // Skip the rest of the buffer
        packetIn.fullySkipBytes((charCount - offset) * 2);
        return result.toString();
    }
}
