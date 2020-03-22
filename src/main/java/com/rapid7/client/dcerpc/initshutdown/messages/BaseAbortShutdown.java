/**
 * Copyright 2020, Vadim Frolov.
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
package com.rapid7.client.dcerpc.initshutdown.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

import java.io.IOException;

/**
 * <b>3.2.4.2 BaseAbortShutdown (Opnum 1)</b><br>
 * <br>
 * The BaseAbortShutdown method is used to terminate the shutdown of the remote computer within the waiting period.
 *
 * <pre>
 * unsigned long BaseAbortShutdown(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName
 * );
 * </pre>
 *
 * ServerName: The value may be NULL as the actual server name is taken from the request details.
 * Return Values: The method returns 0 (ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error code.
 *
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-rsp/39d682ec-3072-4b05-90d4-f964dfaa4b06">3.2.4.2 BaseAbortShutdown (Opnum 1)</a>
 */

public class BaseAbortShutdown extends RequestCall<EmptyResponse> {
    private final WChar.NullTerminated serverName;

    public BaseAbortShutdown(final WChar.NullTerminated serverName) {
        super((short) 1);
        this.serverName = serverName;
    }

    @Override
    public EmptyResponse getResponseObject() {
        return new EmptyResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        if (packetOut.writeReferentID(this.serverName)) {
            packetOut.writeMarshallable(serverName);
        }
    }
}
