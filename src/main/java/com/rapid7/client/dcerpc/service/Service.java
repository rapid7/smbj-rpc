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

import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.objects.ContextHandle;

public abstract class Service {
    public RequestResponse checkReturnCode(RequestResponse response, String name) throws RPCException {
        return checkReturnCode(response, name, SystemErrorCode.ERROR_SUCCESS);
    }

    public RequestResponse checkReturnCode(RequestResponse response, String name, SystemErrorCode ... expectCodes)
            throws RPCException{
        if (response == null || expectCodes == null)
            return response;
        for (SystemErrorCode code : expectCodes) {
            if (code != null && code.is(response.getReturnValue())) {
                return response;
            }
        }
        throw new RPCException(name, response.getReturnValue());
    }

    public static class Inbound {
        public static ContextHandle createContextHandle(byte[] contextHandle) {
            if (contextHandle.length != 20) {
                throw new IllegalArgumentException(String.format(
                        "contextHandle must be exactly 20 bytes, got: %d",
                        contextHandle.length));
            }
            ContextHandle ret = new ContextHandle();
            ret.setBytes(contextHandle);
            return ret;
        }
    }

    public static class Outbound {
        public static byte[] transformContextHandle(ContextHandle contextHandle) throws UnmarshalException {
            if (contextHandle == null)
                throw new UnmarshalException("Expecting non-null ndr_context_handle");
            byte[] ret = contextHandle.getBytes();
            if (ret == null)
                throw new UnmarshalException("Expecting non-null ndr_context_handle content");
            else if (ret.length != 20)
                throw new UnmarshalException("Expecting exactly 20 bytes in ndr_context_handle");
            return ret;
        }
    }
}
