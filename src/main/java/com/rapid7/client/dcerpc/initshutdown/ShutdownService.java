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
package com.rapid7.client.dcerpc.initshutdown;

import com.rapid7.client.dcerpc.initshutdown.dto.ShutdownReason;
import com.rapid7.client.dcerpc.initshutdown.messages.BaseAbortShutdown;
import com.rapid7.client.dcerpc.initshutdown.messages.BaseInitiateShutdownRequest;
import com.rapid7.client.dcerpc.initshutdown.messages.BaseInitiateShutdownRequestEx;
import com.rapid7.client.dcerpc.messages.EmptyResponse;
import com.rapid7.client.dcerpc.objects.RegUnicodeString;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

import java.io.IOException;
import java.util.EnumSet;

public class ShutdownService extends Service {
    public ShutdownService(final RPCTransport transport) {
        super(transport);
    }

    public int shutdown(final String msg,
                         final int timeout, final boolean forceAppsClosed,
                         final boolean rebootAfterShutdown) throws IOException {

        final BaseInitiateShutdownRequest request = new BaseInitiateShutdownRequest(
                parseWCharNT(null),
                RegUnicodeString.NullTerminated.of(msg),
                timeout, forceAppsClosed, rebootAfterShutdown
        );
        final EmptyResponse response = callExpectSuccess(request, "BaseInitiateShutdownRequest");
        return response.getReturnValue();
    }

    public int abortShutdown() throws IOException {
        final BaseAbortShutdown request = new BaseAbortShutdown(
                parseWCharNT(null)
        );
        final EmptyResponse response = callExpectSuccess(request, "BaseAbortShutdown");
        return response.getReturnValue();
    }

    public int shutdownEx(final String msg,
                           final int timeout,
                           final boolean forceAppsClosed,
                           final boolean rebootAfterShutdown,
                           final EnumSet<ShutdownReason> reasons) throws IOException {

        final BaseInitiateShutdownRequestEx request = new BaseInitiateShutdownRequestEx(
                parseWCharNT(null),
                RegUnicodeString.NullTerminated.of(msg),
                timeout, forceAppsClosed, rebootAfterShutdown, reasons
        );
        final EmptyResponse response = callExpectSuccess(request, "BaseInitiateShutdownRequestEx");
        return response.getReturnValue();
    }
}
