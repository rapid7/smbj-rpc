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
 * <blockquote><pre>The SHARE_INFO_2 structure contains information about the shared resource, including the name, type, and permissions of the resource, comments associated with the resource, the maximum number of concurrent connections, the number of current connections, the local path for the resource, and a password for the current connection. For a description of the fields in this structure, see the description for the SHARE_INFO_502_I (section 2.2.4.26) structure (shi2_xxx denotes the same information as shi502_xxx).
 *
 *      typedef struct _SHARE_INFO_2 {
 *          [string] wchar_t* shi2_netname;
 *          DWORD shi2_type;
 *          [string] wchar_t* shi2_remark;
 *          DWORD shi2_permissions;
 *          DWORD shi2_max_uses;
 *          DWORD shi2_current_uses;
 *          [string] wchar_t* shi2_path;
 *          [string] wchar_t* shi2_passwd;
 *      } SHARE_INFO_2,
 *      *PSHARE_INFO_2,
 *      *LPSHARE_INFO_2;</pre></blockquote>
 */
public class ShareInfo2 extends ShareInfo1 {
    // <NDR: unsigned long> DWORD shi2_permissions;
    int permissions;
    // <NDR: unsigned long> DWORD shi2_max_uses;
    int maxUses;
    // <NDR: unsigned long> DWORD shi2_current_uses;
    int currentUses;
    // <NDR: pointer[struct]> wchar_t* shi2_path;
    WChar.NullTerminated path;
    // <NDR: pointer[struct]> wchar_t* shi2_passwd;
    WChar.NullTerminated passwd;

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public void setMaxUses(int maxUses) {
        this.maxUses = maxUses;
    }

    public int getCurrentUses() {
        return currentUses;
    }

    public void setCurrentUses(int currentUses) {
        this.currentUses = currentUses;
    }

    public String getPath() {
        if (this.path == null)
            return null;
        return this.path.getValue();
    }

    public void setPath(String path) {
        if (path == null)
            this.path = null;
        else
            this.path = WChar.NullTerminated.of(path);
    }

    public String getPasswd() {
        if (this.passwd == null)
            return null;
        return this.passwd.getValue();
    }

    public void setPasswd(String passwd) {
        if (passwd == null)
            this.passwd = null;
        else
            this.passwd = WChar.NullTerminated.of(passwd);
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        super.unmarshalPreamble(in);
        //No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        super.unmarshalEntity(in);
        // <NDR: unsigned long> DWORD shi2_permissions;
        // Alignment: 4 - Already aligned
        this.permissions = in.readInt();
        // <NDR: unsigned long> DWORD shi2_max_uses;
        // Alignment: 4 - Already aligned
        this.maxUses = in.readInt();
        // <NDR: unsigned long> DWORD shi2_current_uses;
        // Alignment: 4 - Already aligned
        this.currentUses = in.readInt();
        // <NDR: pointer[struct]> wchar_t* shi2_path;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.path = new WChar.NullTerminated();
        else
            this.path = null;
        // <NDR: pointer[struct]> wchar_t* shi2_passwd;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.passwd = new WChar.NullTerminated();
        else
            this.passwd = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        super.unmarshalDeferrals(in);
        if (this.path != null)
            in.readUnmarshallable(this.path);
        if (this.passwd != null)
            in.readUnmarshallable(this.passwd);
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + Objects.hash(this.permissions, this.maxUses, this.currentUses, this.path, this.passwd);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfo2)) {
            return false;
        }
        final ShareInfo2 other = (ShareInfo2) obj;
        return super.equals(obj)
                && this.permissions == other.permissions
                && this.maxUses == other.maxUses
                && this.currentUses == other.currentUses
                && Objects.equals(this.path, other.path)
                && Objects.equals(this.passwd, other.passwd);
    }

    @Override
    public String toString() {
        return String.format("SHARE_INFO_2{shi2_netname: %s, shi2_type: %d, shi2_remark: %s, shi2_permissions: %d, " +
                        "shi2_max_uses: %d, shi2_current_uses: %d, shi2_path: %s, shi2_passwd: %s}",
                this.netName, this.type, this.remark, this.permissions, this.maxUses, this.currentUses, this.path, this.passwd);
    }
}
