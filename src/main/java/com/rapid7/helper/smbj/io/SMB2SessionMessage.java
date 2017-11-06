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
import java.io.InterruptedIOException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.EnumSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2Header;
import com.hierynomus.mssmb2.SMB2Packet;
import com.hierynomus.smbj.session.Session;

public abstract class SMB2SessionMessage {
    private final SMB2Dialect dialect;
    private final Session session;
    private final long sessionID;
    private final long timeout;

    public SMB2SessionMessage(final Session session) {
        dialect = session.getConnection().getNegotiatedProtocol().getDialect();
        this.session = session;
        sessionID = session.getSessionId();
        timeout = session.getConnection().getConfig().getTransactTimeout();
    }

    public SMB2Dialect getDialect() {
        return dialect;
    }

    public Session getSession() {
        return session;
    }

    public long getSessionID() {
        return sessionID;
    }

    public <T extends SMB2Packet> Future<T> send(final SMB2Packet packet) throws IOException {
        try {
            return getSession().send(packet);
        } catch (final IOException exception) {
            throw exception;
        } catch (final Exception exception) {
            // Wrap SMBApiException and SMBRuntimeException in an IOException
            throw new IOException(exception);
        }
    }

    public <T extends SMB2Packet> T read(final Future<T> future) throws IOException {
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (final InterruptedException exception) {
            final InterruptedIOException innerException = new InterruptedIOException();
            innerException.initCause(exception);
            throw innerException;
        } catch (final TimeoutException exception) {
            final InterruptedByTimeoutException innerException = new InterruptedByTimeoutException();
            innerException.initCause(exception);
            throw innerException;
        } catch (final ExecutionException exception) {
            throw new IOException(exception);
        }
    }

    public <T extends SMB2Packet> T sendAndRead(final SMB2Packet packet) throws IOException {
        final Future<T> future = send(packet);
        return read(future);
    }

    public <T extends SMB2Packet> T sendAndRead(final SMB2Packet packet, final EnumSet<NtStatus> ok)
            throws IOException {
        final Future<T> future = send(packet);
        final T responsePacket = read(future);
        final SMB2Header responseHeader = responsePacket.getHeader();
        final NtStatus responseStatus = responseHeader.getStatus();
        if (!ok.contains(responseStatus)) {
            throw new SMB2Exception(responseHeader, "expected=" + ok);
        }
        return responsePacket;
    }
}
