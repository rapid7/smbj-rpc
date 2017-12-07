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
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc247146.aspx">SHARE_INFO_0</a>
 * <blockquote><pre>The SHARE_INFO_0 structure contains the name of the shared resource. For a description of the fields in this structure, see the description for the SHARE_INFO_502_I (section 2.2.4.26) structure (shi0_xxx denotes the same information as shi502_xxx).
 *
 *      typedef struct _SHARE_INFO_0 {
 *          [string] wchar_t* shi0_netname;
 *      } SHARE_INFO_0,
 *      *PSHARE_INFO_0,
 *      LPSHARE_INFO_0;</pre></blockquote>
 */
public class ShareInfo0 extends ShareInfo {
    @Override
    public String toString() {
        return String.format("SHARE_INFO_0{shi0_netname: %s}", getNetName());
    }
}
