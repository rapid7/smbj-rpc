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
        super(String.format(
            "%s returned %s (%d/%d): %s",
            header.getMessage(),
            header.getStatus(),
            header.getStatus().getValue(),
            header.getStatusCode(),
            message));
        this.status = header.getStatus();
        this.statusCode = header.getStatusCode();
        this.failedCommand = header.getMessage();
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
