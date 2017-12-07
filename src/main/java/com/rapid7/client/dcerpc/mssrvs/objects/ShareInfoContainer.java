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

package com.rapid7.client.dcerpc.mssrvs.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.Arrays;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

public abstract class ShareInfoContainer<T extends ShareInfo> implements Unmarshallable {

    private T[] buffer;

    public T[] getBuffer() {
        return buffer;
    }

    abstract T[] createBuffer(final int size);

    abstract T createEntry();

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        final int entriesRead = readIndex("EntriesRead", in);
        if (in.readReferentID() != 0) {
            if (entriesRead < 0) {
                throw new UnmarshalException(String.format(
                        "Expected entriesRead >= 0, got: %d", entriesRead));
            }
            this.buffer = createBuffer(entriesRead);
        } else {
            this.buffer = null;
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (this.buffer != null) {
            // Maximum Size
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            for (int i = 0; i < this.buffer.length; i++) {
                this.buffer[i] = createEntry();
                this.buffer[i].unmarshalPreamble(in);
            }
            for (final T entry : this.buffer) {
                entry.unmarshalEntity(in);
            }
            for (final T entry : this.buffer) {
                entry.unmarshalDeferrals(in);
            }
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.buffer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfoContainer)) {
            return false;
        }
        return Arrays.equals(this.buffer, ((ShareInfoContainer) obj).buffer);
    }

    private int readIndex(String name, PacketInput in) throws IOException {
        final long ret = in.readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("%s %d > %d", name, ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }

    /**
     * <a href="https://msdn.microsoft.com/en-us/library/cc247156.aspx">SHARE_INFO_0_CONTAINER</a>
     * <blockquote><pre>The SHARE_INFO_0_CONTAINER structure contains a value that indicates the number of entries that the NetrShareEnum method returns and a pointer to the buffer that contains the entries.
     *
     *      typedef struct _SHARE_INFO_0_CONTAINER {
     *          DWORD EntriesRead;
     *          [size_is(EntriesRead)] LPSHARE_INFO_0 Buffer;
     *      } SHARE_INFO_0_CONTAINER;
     *
     *  EntriesRead: The number of entries returned by the method.
     *  Buffer: A pointer to the SHARE_INFO_0 entries returned by the method.</pre></blockquote>
     */
    public static class ShareInfo0Container extends ShareInfoContainer<ShareInfo0> {
        @Override
        ShareInfo0 createEntry() {
            return new ShareInfo0();
        }

        @Override
        ShareInfo0[] createBuffer(int size) {
            return new ShareInfo0[size];
        }
    }

    /**
     * <a href="https://msdn.microsoft.com/en-us/library/cc247157.aspx">SHARE_INFO_1_CONTAINER</a>
     * <blockquote><pre>The SHARE_INFO_1_CONTAINER structure contains a value that indicates the number of entries that the NetrShareEnum method returns and a pointer to the buffer that contains the entries.
     *
     *      typedef struct _SHARE_INFO_1_CONTAINER {
     *          DWORD EntriesRead;
     *          [size_is(EntriesRead)] LPSHARE_INFO_1 Buffer;
     *      } SHARE_INFO_1_CONTAINER;
     *
     *  EntriesRead: The number of entries returned by the method.
     *  Buffer: A pointer to the SHARE_INFO_1 entries returned by the method.</pre></blockquote>
     */
    public static class ShareInfo1Container extends ShareInfoContainer<ShareInfo1> {
        @Override
        ShareInfo1 createEntry() {
            return new ShareInfo1();
        }

        @Override
        ShareInfo1[] createBuffer(int size) {
            return new ShareInfo1[size];
        }
    }

    /**
     * <a href="https://msdn.microsoft.com/en-us/library/cc247158.aspx">SHARE_INFO_2_CONTAINER</a>
     * <blockquote><pre>The SHARE_INFO_2_CONTAINER structure contains a value that indicates the number of entries that the NetrShareEnum method returns and a pointer to the buffer that contains the entries.
     *
     *      typedef struct _SHARE_INFO_2_CONTAINER {
     *          DWORD EntriesRead;
     *          [size_is(EntriesRead)] LPSHARE_INFO_2 Buffer;
     *      } SHARE_INFO_2_CONTAINER,
     *      *PSHARE_INFO_2_CONTAINER,
     *      *LPSHARE_INFO_2_CONTAINER;
     *
     *  EntriesRead: The number of entries returned by the method.
     *  Buffer: A pointer to the SHARE_INFO_2 entries returned by the method.</pre></blockquote>
     */
    public static class ShareInfo2Container extends ShareInfoContainer<ShareInfo2> {
        @Override
        ShareInfo2 createEntry() {
            return new ShareInfo2();
        }

        @Override
        ShareInfo2[] createBuffer(int size) {
            return new ShareInfo2[size];
        }
    }

    /**
     * <a href="https://msdn.microsoft.com/en-us/library/cc247159.aspx">SHARE_INFO_501_CONTAINER</a>
     * <blockquote><pre>The SHARE_INFO_501_CONTAINER structure contains a value that indicates the number of entries that the NetrShareEnum method returns and a pointer to the buffer that contains the entries.
     *
     *      typedef struct _SHARE_INFO_501_CONTAINER {
     *          DWORD EntriesRead;
     *          [size_is(EntriesRead)] LPSHARE_INFO_501 Buffer;
     *      } SHARE_INFO_501_CONTAINER,
     *      *PSHARE_INFO_501_CONTAINER,
     *      *LPSHARE_INFO_501_CONTAINER;
     *
     *  EntriesRead: The number of entries returned by the method.
     *  Buffer: A pointer to the SHARE_INFO_501 entries returned by the method.</pre></blockquote>
     */
    public static class ShareInfo501Container extends ShareInfoContainer<ShareInfo501> {
        @Override
        ShareInfo501 createEntry() {
            return new ShareInfo501();
        }

        @Override
        ShareInfo501[] createBuffer(int size) {
            return new ShareInfo501[size];
        }
    }

    /**
     * <a href="https://msdn.microsoft.com/en-us/library/cc247160.aspx">SHARE_INFO_502_CONTAINER</a>
     * <blockquote><pre>The SHARE_INFO_502_CONTAINER structure contains a value that indicates the number of entries that the NetrShareEnum method returns and a pointer to the buffer that contains the entries.
     *
     *      typedef struct _SHARE_INFO_502_CONTAINER {
     *          DWORD EntriesRead;
     *          [size_is(EntriesRead)] LPSHARE_INFO_502_I Buffer;
     *      } SHARE_INFO_502_CONTAINER,
     *      *PSHARE_INFO_502_CONTAINER,
     *      *LPSHARE_INFO_502_CONTAINER;
     *
     *  EntriesRead: The number of entries returned by the method.
     *  Buffer: A pointer to the SHARE_INFO_502_I entries returned by the method.</pre></blockquote>
     */
    public static class ShareInfo502Container extends ShareInfoContainer<ShareInfo502> {
        @Override
        ShareInfo502 createEntry() {
            return new ShareInfo502();
        }

        @Override
        ShareInfo502[] createBuffer(int size) {
            return new ShareInfo502[size];
        }
    }

    /**
     * <a href="https://msdn.microsoft.com/en-us/library/dd644739.aspx">SHARE_INFO_503_CONTAINER</a>
     * <blockquote><pre>The SHARE_INFO_503_CONTAINER structure contains a value that indicates the number of entries the NetrShareEnum method returns and a pointer to the buffer that contains the entries.
     *
     *      typedef struct _SHARE_INFO_503_CONTAINER {
     *          DWORD EntriesRead;
     *          [size_is(EntriesRead)] LPSHARE_INFO_503_I Buffer;
     *      } SHARE_INFO_503_CONTAINER,
     *      *PSHARE_INFO_503_CONTAINER,
     *      *LPSHARE_INFO_503_CONTAINER;
     *
     * EntriesRead: The number of entries returned by the method.
     * Buffer: A pointer to the SHARE_INFO_503_I entries returned by the method.</pre></blockquote>
     */
    public static class ShareInfo503Container extends ShareInfoContainer<ShareInfo503> {
        @Override
        ShareInfo503 createEntry() {
            return new ShareInfo503();
        }

        @Override
        ShareInfo503[] createBuffer(int size) {
            return new ShareInfo503[size];
        }
    }
}
