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
package com.rapid7.client.dcerpc;

import java.io.IOException;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;

@SuppressWarnings("serial")
public class RPCException extends IOException {
    private final int returnValue;
    private final SystemErrorCode errorCode;

    public RPCException(final String opName, final int returnValue) {
        super(formatMessage(opName, returnValue));
        this.returnValue = returnValue;
        this.errorCode = SystemErrorCode.getErrorCode(returnValue);
    }

    public int getReturnValue() {
        return returnValue;
    }

    public SystemErrorCode getErrorCode() {
        return errorCode;
    }

    public boolean hasErrorCode() {
        return errorCode != null;
    }

    private static String formatMessage(final String opName, final int returnValue) {
        final SystemErrorCode errorCode = SystemErrorCode.getErrorCode(returnValue);
        final String errorCodeStr = (errorCode == null) ? "" : String.format("(%s)", errorCode);
        return String.format("%s returned error code: 0x%08X %s", opName, returnValue, errorCodeStr);
    }
}
