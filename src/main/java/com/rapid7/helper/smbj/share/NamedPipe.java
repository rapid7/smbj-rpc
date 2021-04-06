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
package com.rapid7.helper.smbj.share;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.EnumSet;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.SMB2ImpersonationLevel;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.mssmb2.messages.*;
import com.hierynomus.smbj.common.SMBRuntimeException;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.io.ArrayByteChunkProvider;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.PipeShare;
import com.rapid7.helper.smbj.io.SMB2SessionMessage;

public class NamedPipe extends SMB2SessionMessage implements Closeable {
    private final static int FSCTL_PIPE_TRANSCEIVE = 0x0011c017;
    private final static EnumSet<NtStatus> IOCTL_SUCCESS = EnumSet.of(NtStatus.STATUS_SUCCESS, NtStatus.STATUS_BUFFER_OVERFLOW);
    private final static EnumSet<NtStatus> READ_SUCCESS = EnumSet.of(NtStatus.STATUS_SUCCESS, NtStatus.STATUS_BUFFER_OVERFLOW, NtStatus.STATUS_END_OF_FILE);
    private final static EnumSet<NtStatus> WRITE_SUCCESS = EnumSet.of(NtStatus.STATUS_SUCCESS);
    private final PipeShare share;
    private final SMB2FileId fileID;
    private final int transactBufferSize;
    private final int readBufferSize;
    private final int writeBufferSize;

    public NamedPipe(final Session session, final PipeShare share, final String name) throws IOException {
        super(session, share.getTreeConnect().getConfig());

        this.share = share;

        final SMB2CreateRequest createRequest = new SMB2CreateRequest(session.getConnection().getNegotiatedProtocol().getDialect(), session.getSessionId(), share.getTreeConnect().getTreeId(), SMB2ImpersonationLevel.Impersonation, EnumSet.of(AccessMask.MAXIMUM_ALLOWED), null, EnumSet.of(SMB2ShareAccess.FILE_SHARE_READ, SMB2ShareAccess.FILE_SHARE_WRITE), SMB2CreateDisposition.FILE_OPEN_IF, null, new SmbPath(share.getSmbPath(), name));
        final SMB2CreateResponse createResponse = sendAndRead(createRequest, EnumSet.of(NtStatus.STATUS_SUCCESS));

        fileID = createResponse.getFileId();
        transactBufferSize = Math.min(share.getTreeConnect().getConfig().getTransactBufferSize(), session.getConnection().getNegotiatedProtocol().getMaxTransactSize());
        readBufferSize = Math.min(share.getTreeConnect().getConfig().getReadBufferSize(), session.getConnection().getNegotiatedProtocol().getMaxReadSize());
        writeBufferSize = Math.min(share.getTreeConnect().getConfig().getWriteBufferSize(), session.getConnection().getNegotiatedProtocol().getMaxWriteSize());
    }

    public byte[] transact(final byte[] inBuffer) throws IOException {
        final SMB2IoctlResponse response = _ioctl(inBuffer);
        final ByteArrayOutputStream outBuffer = new ByteArrayOutputStream(4096);
        final byte[] outData = response.getOutputBuffer();
        try {
            outBuffer.write(outData);
        } catch (final IOException exception) {
            throw new SMBRuntimeException(exception);
        }
        final NtStatus status = NtStatus.valueOf(response.getHeader().getStatusCode());
        if (status.equals(NtStatus.STATUS_BUFFER_OVERFLOW)) {
            outBuffer.write(read());
        }
        return outBuffer.toByteArray();
    }

    public byte[] read() throws IOException {
        final ByteArrayOutputStream dataBuffer = new ByteArrayOutputStream(4096);
        for (; ; ) {
            final SMB2ReadResponse response = _read();
            final byte[] data = response.getData();
            try {
                dataBuffer.write(data);
            } catch (final IOException exception) {
                throw new SMBRuntimeException(exception);
            }
            final NtStatus status = NtStatus.valueOf(response.getHeader().getStatusCode());
            if (!status.equals(NtStatus.STATUS_BUFFER_OVERFLOW)) {
                break;
            }
        }
        return dataBuffer.toByteArray();
    }

    public void write(final byte[] buffer) throws IOException {
        _write(buffer);
    }

    @Override
    public void close() {
        share.closeFileId(fileID);
    }

    private SMB2IoctlResponse _ioctl(final byte[] inBuffer) throws IOException {
        final SMB2IoctlRequest ioctlRequest = new SMB2IoctlRequest(getDialect(), getSessionID(), share.getTreeConnect().getTreeId(), FSCTL_PIPE_TRANSCEIVE, fileID, new ArrayByteChunkProvider(inBuffer, 0, inBuffer.length, 0), true, transactBufferSize);
        final SMB2IoctlResponse ioctlResponse = sendAndRead(ioctlRequest, IOCTL_SUCCESS);
        return ioctlResponse;
    }

    private SMB2ReadResponse _read() throws IOException {
        final SMB2ReadRequest readRequest = new SMB2ReadRequest(getDialect(), fileID, getSessionID(), share.getTreeConnect().getTreeId(), 0, readBufferSize);
        final SMB2ReadResponse readResponse = sendAndRead(readRequest, READ_SUCCESS);
        return readResponse;
    }

    private SMB2WriteResponse _write(final byte[] buffer) throws IOException {
        final SMB2WriteRequest writeRequest = new SMB2WriteRequest(getDialect(), fileID, getSessionID(), share.getTreeConnect().getTreeId(), new ArrayByteChunkProvider(buffer, 0, buffer.length, 0), writeBufferSize);
        final SMB2WriteResponse writeResponse = sendAndRead(writeRequest, WRITE_SUCCESS);
        return writeResponse;
    }
}
