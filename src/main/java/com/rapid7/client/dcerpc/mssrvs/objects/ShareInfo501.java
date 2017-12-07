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
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc247149.aspx">SHARE_INFO_501</a>
 * <blockquote><pre>The SHARE_INFO_501 structure contains information about the shared resource, including the name and type of the resource and a comment that is associated with the resource. For a description of the fields in this structure, see the description for the SHARE_INFO_502_I (section 2.2.4.26) structure (shi501_netname, shi501_type, and shi501_remark denote the same information as shi502_xxx in section 2.2.4.26, and shi501_flags denotes the same information as shi1005_flags in section 2.2.4.29).
 *
 *      typedef struct _SHARE_INFO_501 {
 *          [string] wchar_t* shi501_netname;
 *          DWORD shi501_type;
 *          [string] wchar_t* shi501_remark;
 *          DWORD shi501_flags;
 *      } SHARE_INFO_501,
 *      *PSHARE_INFO_501,
 *      *LPSHARE_INFO_501;</pre></blockquote>
 */
public class ShareInfo501 extends ShareInfo1 {
    // <NDR: unsigned long> DWORD shi501_flags;
    private int flags;

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        super.unmarshalEntity(in);
        // <NDR: unsigned long> DWORD shi501_flags;
        // Alignment: 4 - Already aligned
        this.flags = in.readInt();
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + Objects.hash(getFlags());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfo501)) {
            return false;
        }
        final ShareInfo501 other = (ShareInfo501) obj;
        return super.equals(obj)
                && getFlags() == other.getFlags();
    }

    @Override
    public String toString() {
        return String.format("SHARE_INFO_501{shi501_netname: %s, shi501_type: %d, shi501_remark: %s, shi501_flags: %d}",
                getNetName(), getType(), getRemark(), getFlags());
    }
}
