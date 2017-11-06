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
package com.rapid7.helper.smbj.io;

import java.io.IOException;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2Header;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;

@SuppressWarnings("serial")
public class SMB2Exception extends IOException {
    private final NtStatus status;
    private final SMB2MessageCommandCode failedCommand;
    private final long statusCode;

    public SMB2Exception(final SMB2Header header, final String message) {
        super(String.format("%s returned %s (%d/%d): %s", header.getMessage(), header.getStatus(), header.getStatus().getValue(), header.getStatusCode(), message));
        status = header.getStatus();
        statusCode = header.getStatusCode();
        failedCommand = header.getMessage();
    }

    public NtStatus getStatus() {
        return status;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public SMB2MessageCommandCode getFailedCommand() {
        return failedCommand;
    }
}
