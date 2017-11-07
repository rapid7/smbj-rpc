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
package com.rapid7.client.dcerpc.mssrvs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.mutable.MutableInt;
import com.hierynomus.protocol.transport.TransportException;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.mssrvs.messages.*;
import com.rapid7.client.dcerpc.transport.RPCTransport;

/**
 * This class implements a partial service service in accordance with [MS-SRVS]: Server Service Remote Protocol which
 * specifies the Server Service Remote Protocol, which remotely enables file and printer sharing and named pipe access
 * to the server through the Server Message Block Protocol.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc247080.aspx">[MS-SRVS]: Server Service Remote Protocol</a>
 */
public class ServerService {
    private final RPCTransport transport;

    public ServerService(final RPCTransport transport) {
        this.transport = transport;
    }

    public List<NetShareInfo0> getShares() throws IOException {
        final List<NetShareInfo0> shares = new LinkedList<>();
        final MutableInt resumeHandle = new MutableInt();
        for (; ; ) {
            final NetrShareEnumRequest request = new NetrShareEnumRequest(2, resumeHandle.getValue());
            final NetrShareEnumResponse response = transport.call(request);
            final int returnCode = response.getReturnValue();
            if (SystemErrorCode.ERROR_SUCCESS.is(returnCode) || SystemErrorCode.ERROR_MORE_DATA.is(returnCode)) {
                final List<NetShareInfo0> responseShares = response.getShares();
                if (SystemErrorCode.ERROR_SUCCESS.is(returnCode)) {
                    shares.addAll(responseShares);
                    break;
                } else {
                    if (responseShares.isEmpty()) {
                        throw new TransportException("NetrShareEnum shares empty.");
                    }
                    final Integer responseResumeHandle = response.getResumeHandle();
                    if (responseResumeHandle == resumeHandle.getValue()) {
                        throw new TransportException("NetrShareEnum resume handle not updated.");
                    }
                    if (responseResumeHandle == null) {
                        throw new TransportException("NetrShareEnum resume handle null.");
                    }
                    shares.addAll(responseShares);
                    resumeHandle.setValue(responseResumeHandle);
                }
            } else {
                throw new RPCException("NetrShareEnum", response.getReturnValue());
            }
        }
        return Collections.unmodifiableList(new ArrayList<>(shares));
    }

    public String getCanonicalizedName(String serverName, String pathName, String prefix, int outBufLength, int pathType, int flags)
            throws IOException {
        final NetprPathCanonicalizeRequest request = new NetprPathCanonicalizeRequest(serverName, pathName, outBufLength, prefix, pathType, flags);
        NetprPathCanonicalizeResponse response = transport.call(request);
        return response.getCanonicalizedPath();
    }
}
