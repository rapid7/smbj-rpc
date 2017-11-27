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
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssrvs.NetprPathType;

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
    private String canonicalizedPath;
    private int pathType;

    public void unmarshalResponse(final PacketInput packetIn) throws IOException {
        canonicalizedPath = readChars(packetIn);
        pathType = packetIn.readInt();
    }

    public String getCanonicalizedPath() {
        return canonicalizedPath;
    }

    public NetprPathType getPathType() {
        return NetprPathType.getid(pathType);
    }

    private String readChars(final PacketInput packetIn) throws IOException {
        final StringBuffer result;
        int currentOffset = 0;
        int lengthInBytes = packetIn.readInt();
        int lengthOfChars = lengthInBytes / 2;
        result = new StringBuffer(lengthOfChars);

        while (currentOffset++ < lengthOfChars) {
            final char currentChar = (char) packetIn.readShort();
            if (currentChar == 0) {
                break;
            }
            result.append(currentChar);
        }
        while (currentOffset++ < lengthOfChars) {
            packetIn.readShort();
        }
        packetIn.align();

        return result.toString();
    }
}
