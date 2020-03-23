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

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.RegUnicodeString;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <b>3.2.4.1 BaseInitiateShutdown (Opnum 0)</b><br>
 * <br>
 * The BaseInitiateShutdown method is used to initiate the shutdown of the remote computer.
 *
 * <pre>
 * unsigned long BaseInitiateShutdown(
 *    [in, unique] PREGISTRY_SERVER_NAME ServerName,
 *    [in, unique] PREG_UNICODE_STRING lpMessage,
 *    [in] unsigned long dwTimeout,
 *    [in] unsigned char bForceAppsClosed,
 *    [in] unsigned char bRebootAfterShutdown
 * );
 * </pre>
 *
 * Return values: Return Values: The method returns ERROR_SUCCESS (0x00000000) on success; otherwise, it returns
 * a nonzero error code.
 *
 * @see <a href="https://docs.microsoft.com/en-us/openspecs/windows_protocols/ms-rsp/6f927550-8b12-443e-91e4-24df3e9860bd">3.2.4.1 BaseInitiateShutdown (Opnum 0)</a>
 */
public class BaseInitiateShutdownRequest extends RequestCall<EmptyResponse> {
    private final WChar.NullTerminated serverName;
    private final RegUnicodeString.NullTerminated messageToUser;
    private final int timeout;
    private final boolean forceAppsClosed;
    private final boolean rebootAfterShutdown;

    public BaseInitiateShutdownRequest(final WChar.NullTerminated serverName, final RegUnicodeString.NullTerminated messageToUser, final int timeout, final boolean forceAppsClosed, final boolean rebootAfterShutdown) {
        super((short) 0);
        this.serverName = serverName;
        this.messageToUser = messageToUser;
        this.timeout = timeout;
        this.forceAppsClosed = forceAppsClosed;
        this.rebootAfterShutdown = rebootAfterShutdown;
    }

    @Override
    public EmptyResponse getResponseObject() {
        return new EmptyResponse();
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // Server name is a pointer, thus writeReferentID.
        // Alignment is by four for structs and variable-length data.
        if (packetOut.writeReferentID(this.serverName)) {
            packetOut.writeMarshallable(serverName);
            packetOut.align(Alignment.FOUR);
        }

        // <NDR: struct> [in, unique] PREG_UNICODE_STRING lpMessage
        if (packetOut.writeReferentID(this.messageToUser)) {
            packetOut.writeMarshallable(messageToUser);
            packetOut.align(Alignment.FOUR);
        }

        // <NDR: unsigned long> [in] unisnged long dwTimeout
        packetOut.writeInt(timeout);

        packetOut.align(Alignment.ONE);
        packetOut.writeBoolean(forceAppsClosed);
        // Alignment: 1 - Already aligned
        packetOut.writeBoolean(rebootAfterShutdown);
    }
}
