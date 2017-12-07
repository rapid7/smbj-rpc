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
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/dd644682.aspx">SHARE_INFO_503</a>
 * <blockquote><pre>The SHARE_INFO_503_I structure contains information about the shared resource, including the name of the resource, type, and permissions, the number of connections, and other pertinent information.
 *
 *      typedef struct _SHARE_INFO_503_I {
 *          [string] WCHAR* shi503_netname;
 *          DWORD shi503_type;
 *          [string] WCHAR* shi503_remark;
 *          DWORD shi503_permissions;
 *          DWORD shi503_max_uses;
 *          DWORD shi503_current_uses;
 *          [string] WCHAR* shi503_path;
 *          [string] WCHAR* shi503_passwd;
 *          [string] WCHAR* shi503_servername;
 *          DWORD shi503_reserved;
 *          [size_is(shi503_reserved)] PUCHAR shi503_security_descriptor;
 *      } SHARE_INFO_503_I,
 *      *PSHARE_INFO_503_I,
 *      *LPSHARE_INFO_503_I;
 *
 *  shi503_netname: A pointer to a null-terminated Unicode UTF-16 string that specifies the name of a shared resource. The server MUST ignore this member when processing the NetrShareSetInfo (section 3.1.4.11) method.
 *  shi503_type: Specifies a DWORD value that indicates the type of share. The server MUST ignore this member when processing the NetrShareSetInfo method. Otherwise, it MUST be one of the values listed in section 2.2.2.4.
 *  shi503_remark: A pointer to a null-terminated Unicode UTF-16 string that specifies an optional comment about the shared resource.
 *  shi503_permissions: This field is not used. The server MUST ignore the value of this parameter on receipt.
 *  shi503_max_uses: Specifies a DWORD value that indicates the maximum number of concurrent connections that the shared resource can accommodate. If the value is 0xFFFFFFFF, the maximum number of connections MUST be unlimited.
 *  shi503_current_uses: Specifies a DWORD value that indicates the number of current connections to the resource. The server MUST ignore this member on receipt.
 *  shi503_path: A pointer to a null-terminated Unicode UTF-16 string that contains the local path for the shared resource. For disks, it is the path being shared. For print queues, it is the name of the print queue being shared. The server MUST ignore this member when processing the NetrShareSetInfo method.
 *  shi503_passwd: This field is not used. The client MUST send a NULL (zero-length) string, and the server MUST ignore the value of this parameter on receipt.
 *  shi503_servername: A pointer to a string that specifies the DNS or NetBIOS name of the server on which the shared resource resides. It SHOULD be either "*" or the string matching one of the server names. Otherwise, the default server name will be used in [shi503_netname, default server name] to locate a scoped share as specified in section 2.2.4.102. A value of "*" indicates that there is no configured server name.
 *  shi503_reserved: The length of the security descriptor passed in the shi503_security_descriptor member.
 *  shi503_security_descriptor: Specifies the SECURITY_DESCRIPTOR, as described in [MS-DTYP] section 2.4.6, that is associated with this share.</pre></blockquote>
 */
public class ShareInfo503 extends ShareInfo2 {
    // <NDR: pointer[struct]> [string] WCHAR* shi503_servername;
    private WChar.NullTerminated serverName;
    // <NDR: unsigned long> DWORD shi502_reserved;
    // Used internally
    // <NDR: pointer[conformant array]> [size_is(shi502_reserved)] unsigned char* shi502_security_descriptor;
    private byte[] securityDescriptor;

    public WChar.NullTerminated getServerName() {
        return this.serverName;
    }

    public void setServerName(WChar.NullTerminated serverName) {
        this.serverName = serverName;
    }

    public byte[] getSecurityDescriptor() {
        return securityDescriptor;
    }

    public void setSecurityDescriptor(byte[] securityDescriptor) {
        this.securityDescriptor = securityDescriptor;
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        super.unmarshalEntity(in);
        // <NDR: pointer[struct]> [string] WCHAR* shi503_servername;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.serverName = new WChar.NullTerminated();
        else
            this.serverName = null;
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
        if (this.serverName != null) {
            // <NDR: pointer[struct]> [string] WCHAR* shi503_servername;
            in.readUnmarshallable(this.serverName);
        }
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
        int ret = super.hashCode();
        ret = (ret * 31) + Objects.hash(getServerName());
        return (ret * 31) + Arrays.hashCode(getSecurityDescriptor());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfo503)) {
            return false;
        }
        final ShareInfo503 other = (ShareInfo503) obj;
        return super.equals(obj)
                && Objects.equals(getServerName(), other.getServerName())
                && Arrays.equals(getSecurityDescriptor(), other.getSecurityDescriptor());
    }

    @Override
    public String toString() {
        return String.format("SHARE_INFO_503{shi503_netname: %s, shi503_type: %d, shi503_remark: %s, " +
                        "shi503_permissions: %d, shi503_max_uses: %d, shi503_current_uses: %d, shi503_path: %s, " +
                        "shi503_passwd: %s, shi503_server_name: %s, size(shi503_security_descriptor): %s}",
                getNetName(), getType(), getRemark(), getPermissions(), getMaxUses(),
                getCurrentUses(), getPath(), getPasswd(), getServerName(),
                (getSecurityDescriptor() == null ? "null" : getSecurityDescriptor().length));
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
