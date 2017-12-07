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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc247147.aspx">SHARE_INFO_1</a>
 * <blockquote><pre>The SHARE_INFO_1 structure contains information about the shared resource, including the name and type of the resource and a comment associated with the resource. For a description of the fields in this structure, see the description for the SHARE_INFO_502_I (section 2.2.4.26) structure (shi1_xxx denotes the same information as shi502_xxx).
 *
 *      typedef struct _SHARE_INFO_1 {
 *          [string] wchar_t* shi1_netname;
 *          DWORD shi1_type;
 *          [string] wchar_t* shi1_remark;
 *      } SHARE_INFO_1,
 *      *PSHARE_INFO_1,
 *      *LPSHARE_INFO_1;</pre></blockquote>
 */
public class ShareInfo1 extends ShareInfo0 {
    // <NDR: unsigned long> DWORD shi1_type;
    private int type;
    // <NDR: pointer[struct]> wchar_t* shi1_remark;
    private WChar.NullTerminated remark;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WChar.NullTerminated getRemark() {
        return this.remark;
    }

    public void setRemark(final WChar.NullTerminated remark) {
        this.remark = remark;
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        super.unmarshalEntity(in);
        // <NDR: unsigned long> DWORD shi1_type;
        // Alignment: 4 - Already aligned
        this.type = (int) in.readUnsignedInt(); // We don't expect this to require an unsigned int
        // <NDR: pointer[struct]> wchar_t* shi1_remark;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.remark = new WChar.NullTerminated();
        else
            this.remark = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        super.unmarshalDeferrals(in);
        if (this.remark != null)
            in.readUnmarshallable(this.remark);
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + Objects.hash(getType(), getRemark());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfo1)) {
            return false;
        }
        final ShareInfo1 other = (ShareInfo1) obj;
        return super.equals(obj)
                && this.type == other.type
                && Objects.equals(getRemark(), other.getRemark());
    }

    @Override
    public String toString() {
        return String.format("SHARE_INFO_1{shi1_netname: %s, shi1_type: %d, shi1_remark: %s}",
                getNetName(), getType(), getRemark());
    }
}
