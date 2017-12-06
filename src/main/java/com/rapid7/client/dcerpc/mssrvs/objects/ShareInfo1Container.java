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
public class ShareInfo1Container extends ShareInfoContainer<ShareInfo1> {
    @Override
    ShareInfo1 createEntry() {
        return new ShareInfo1();
    }

    @Override
    ShareInfo1[] createBuffer(int size) {
        return new ShareInfo1[size];
    }
}
