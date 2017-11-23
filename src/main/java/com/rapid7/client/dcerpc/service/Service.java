/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */

package com.rapid7.client.dcerpc.service;

import java.io.IOException;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.objects.RPCSID;
import com.rapid7.client.dcerpc.dto.SID;
import com.rapid7.client.dcerpc.objects.RPCUnicodeString;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public abstract class Service {
    private final RPCTransport transport;

    protected Service(final RPCTransport transport) {
        if (transport == null)
            throw new IllegalArgumentException("Expecting non-null transport");
        this.transport = transport;
    }

    protected <R extends RequestResponse> R call(RequestCall<R> request) throws IOException {
        return transport.call(request);
    }

    protected <R extends RequestResponse> R callExpectSuccess(RequestCall<R> request, String name) throws IOException {
        return callExpect(request, name, SystemErrorCode.ERROR_SUCCESS);
    }

    protected <R extends RequestResponse> R callExpect(RequestCall<R> request, String name,
            SystemErrorCode ... expectCodes) throws IOException {
        final R response = call(request);
        if (expectCodes == null)
            return response;
        final int returnValue = response.getReturnValue();
        for (SystemErrorCode code : expectCodes) {
            if (code != null && code.is(returnValue)) {
                return response;
            }
        }
        throw new RPCException(name, returnValue);
    }

    protected RPCUnicodeString.NonNullTerminated[] parseNonNullTerminatedStrings(String[] strings) {
        if (strings == null)
            return null;
        final RPCUnicodeString.NonNullTerminated[] ret = new RPCUnicodeString.NonNullTerminated[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ret[i] = RPCUnicodeString.NonNullTerminated.of(strings[i]);
        }
        return ret;
    }

    protected byte[] parseHandle(final ContextHandle handle) {
        return handle.getBytes();
    }

    protected RPCSID parseSID(final SID sid) {
        if (sid == null)
            return null;
        RPCSID rpcsid = new RPCSID();
        rpcsid.setRevision((char) sid.getRevision());
        rpcsid.setIdentifierAuthority(sid.getIdentifierAuthority());
        rpcsid.setSubAuthority(sid.getSubAuthorities());
        return rpcsid;
    }

    protected RPCSID[] parseSIDs(SID[] sids) {
        final RPCSID[] rpcsids;
        if (sids == null) {
            rpcsids = new RPCSID[0];
            sids = new SID[0];
        }
        else {
            rpcsids = new RPCSID[sids.length];
        }
        for (int i = 0; i < rpcsids.length; i++) {
            rpcsids[i] = parseSID(sids[i]);
        }
        return rpcsids;
    }

    protected SID[] parseRPCSIDs(RPCSID[] rpcsids) {
        final SID[] sids;
        if (rpcsids == null) {
            sids = new SID[0];
            rpcsids = new RPCSID[0];
        } else {
            sids = new SID[rpcsids.length];
        }
        for (int i = 0; i < sids.length; i++) {
            sids[i] = parseRPCSID(rpcsids[i]);
        }
        return sids;
    }

    protected SID parseRPCSID(final RPCSID rpcsid) {
        if (rpcsid == null)
            return null;
        return new SID((byte) rpcsid.getRevision(), rpcsid.getIdentifierAuthority(), rpcsid.getSubAuthority());
    }
}
