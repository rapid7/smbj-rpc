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

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc247147.aspx">SHARE_INFO_502</a>
 * <blockquote><pre>The SHARE_INFO_502_I structure contains information about the shared resource, including the name of the resource, type, and permissions, the number of connections, and other pertinent information.
 *
 *      typedef struct _SHARE_INFO_502_I {
 *          [string] WCHAR* shi502_netname;
 *          DWORD shi502_type;
 *          [string] WCHAR* shi502_remark;
 *          DWORD shi502_permissions;
 *          DWORD shi502_max_uses;
 *          DWORD shi502_current_uses;
 *          [string] WCHAR* shi502_path;
 *          [string] WCHAR* shi502_passwd;
 *          DWORD shi502_reserved;
 *          [size_is(shi502_reserved)] unsigned char* shi502_security_descriptor;
 *      } SHARE_INFO_502_I,
 *      *PSHARE_INFO_502_I,
 *      *LPSHARE_INFO_502_I;
 *
 *  shi502_netname: A pointer to a null-terminated Unicode UTF-16 string that specifies the name of a shared resource. The server MUST ignore this member when processing the NetrShareSetInfo (section 3.1.4.11) method.
 *  shi502_type: Specifies a DWORD value that indicates the type of share. The server MUST ignore this member when processing the NetrShareSetInfo method; otherwise, it MUST be one of the values that are listed in section 2.2.2.4.
 *  shi502_remark: A pointer to a null-terminated Unicode UTF-16 string that specifies an optional comment about the shared resource.
 *  shi502_permissions: This field is not used. The server MUST ignore the value of this parameter on receipt.
 *  shi502_max_uses: Specifies a DWORD value that indicates the maximum number of concurrent connections that the shared resource can accommodate. If the value that is specified by shi502_max_uses is 0xFFFFFFFF, the maximum number of connections MUST be unlimited.
 *  shi502_current_uses: Specifies a DWORD value that indicates the number of current connections to the resource. The server MUST ignore this member on receipt.
 *  shi502_path: A pointer to a null-terminated Unicode UTF-16 string that contains the local path for the shared resource. For disks, shi502_path is the path that is being shared. For print queues, shi502_path is the name of the print queue that is being shared. For communication devices, shi502_path is the name of the communication device that is being shared. For interprocess communications (IPC), shi502_path is the name of the interprocess communication that is being shared. The server MUST ignore this member when processing the NetrShareSetInfo method.
 *  shi502_passwd: This field is not used. The client MUST send a NULL (zero-length) string and the server MUST ignore the value of this parameter on receipt.
 *  shi502_reserved: The length of the security descriptor that is being passed in the shi502_security_descriptor member.
 *  shi502_security_descriptor: Specifies the SECURITY_DESCRIPTOR, as described in [MS-DTYP] section 2.4.6, that is associated with this share.</pre></blockquote>
 */
public class ShareInfo502 extends ShareInfo2 {
    // <NDR: unsigned long> DWORD shi502_reserved;
    // Used internally
    // <NDR: pointer[conformant array]> [size_is(shi502_reserved)] unsigned char* shi502_security_descriptor;
    private byte[] securityDescriptor;

    public byte[] getSecurityDescriptor() {
        return securityDescriptor;
    }

    public void setSecurityDescriptor(byte[] securityDescriptor) {
        this.securityDescriptor = securityDescriptor;
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        super.unmarshalEntity(in);
        // <NDR: unsigned long> DWORD shi502_reserved;
        // Alignment: 4 - Already aligned
        final int reserved = readIndex("reserved", in);
        // <NDR: conformant array> [size_is(shi502_reserved)] unsigned char* shi502_security_descriptor;
        // Alignment: 4 - Already aligned
        //noinspection Duplicates
        if (in.readReferentID() != 0) {
            if (reserved < 0)
                throw new UnmarshalException(String.format("Expected reserved >= 0, got: %d", reserved));
            this.securityDescriptor = new byte[reserved];
        } else {
            this.securityDescriptor = null;
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        super.unmarshalDeferrals(in);
        if (this.securityDescriptor != null) {
            // <NDR: conformant array> [size_is(shi502_reserved)] unsigned char* shi502_security_descriptor;
            // MaximumSize
            in.align(Alignment.FOUR);
            in.fullySkipBytes(4);
            in.readRawBytes(this.securityDescriptor);
        }
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + Arrays.hashCode(this.securityDescriptor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfo502)) {
            return false;
        }
        final ShareInfo502 other = (ShareInfo502) obj;
        return super.equals(obj)
                && Arrays.equals(this.securityDescriptor, other.securityDescriptor);
    }

    @Override
    public String toString() {
        return String.format("SHARE_INFO_502{shi502_netname: %s, shi502_type: %d, shi502_remark: %s, " +
                        "shi502_permissions: %d, shi502_max_uses: %d, shi502_current_uses: %d, shi502_path: %s, " +
                        "shi502_passwd: %s, size(shi502_security_descriptor): %s}",
                getNetName(), getType(), getRemark(), getPermissions(), getMaxUses(), getCurrentUses(), getPath(),
                getPasswd(), (getSecurityDescriptor() == null ? "null" : getSecurityDescriptor().length));
    }

    private int readIndex(String name, PacketInput in) throws IOException {
        final long ret = in.readUnsignedInt();
        // Don't allow array length or index values bigger than signed int
        if (ret > Integer.MAX_VALUE) {
            throw new UnmarshalException(String.format("%s %d > %d", name, ret, Integer.MAX_VALUE));
        }
        return (int) ret;
    }
}
