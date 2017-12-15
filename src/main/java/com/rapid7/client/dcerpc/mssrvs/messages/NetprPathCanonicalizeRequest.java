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
package com.rapid7.client.dcerpc.mssrvs.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <h1 class="title">3.1.4.30 NetprPathCanonicalize (Opnum 31)</h1>
 * <p>The NetprPathCanonicalize method converts a path name to the
 * canonical format.</p>
 *
 * <dl>
 * <dd>
 * <div><pre> NET_API_STATUS NetprPathCanonicalize(
 *    [in, string, unique] SRVSVC_HANDLE ServerName,
 *    [in, string] WCHAR* PathName,
 *    [out, size_is(OutbufLen)] unsigned char* Outbuf,
 *    [in, range(0,64000)] DWORD OutbufLen,
 *    [in, string] WCHAR* Prefix,
 *    [in, out] DWORD* PathType,
 *    [in] DWORD Flags
 *  );
 * </pre></div>
 * </dd></dl>
 *
 * <p><strong>ServerName: </strong>An <a href="https://msdn.microsoft.com/en-us/library/cc247105.aspx">SRVSVC_HANDLE (section 2.2.1.1)</a>
 * pointer that identifies the <a href="https://msdn.microsoft.com/en-us/library/cc247082.aspx#gt_434b0234-e970-4e8c-bdfa-e16a30d96703">server</a>.
 * The <a href="https://msdn.microsoft.com/en-us/library/cc247082.aspx#gt_60e0e1fa-66fe-41e1-b5e3-ceab97e53506">client</a> MUST map this
 * structure to an <a href="https://msdn.microsoft.com/en-us/library/cc247082.aspx#gt_8a7f6700-8311-45bc-af10-82e10accd331">RPC</a>
 * binding handle (see <a href="https://go.microsoft.com/fwlink/?LinkId=89824">[C706]</a>
 * sections 4.3.5 and 5.1.5.2). The server MUST ignore this parameter.</p>
 *
 * <p><strong>PathName: </strong>A pointer to a null-terminated
 * UTF-16 string that specifies the path name to canonicalize.</p>
 *
 * <p><strong>Outbuf: </strong>A pointer to the output buffer where
 * the canonicalized path name is returned.</p>
 *
 * <p><strong>OutbufLen: </strong>The length, in bytes, of the output
 * buffer, <em>Outbuf</em>. The value of this field MUST be within the range
 * 0–64,000, inclusive.</p>
 *
 * <p><strong>Prefix: </strong>A pointer to a null-terminated UTF-16
 * string that specifies an optional prefix to use when canonicalizing a relative
 * path name.</p>
 *
 * <p><strong>PathType: </strong>A place to store the path type. This
 * parameter MUST be set by the client either to zero or to one of the values
 * defined in section <a href="https://msdn.microsoft.com/en-us/library/cc247100.aspx">2.2.2.9</a>.
 * After successful completion of the request, the server MUST set <em>PathType</em>
 * to one of the values defined in section 2.2.2.9.</p>
 *
 * <p><strong>Flags: </strong>Reserved, MUST be zero.</p>
 *
 * <p><strong>Return Values: </strong>The method returns 0x00000000
 * (NERR_Success) to indicate success; otherwise, it returns a nonzero error code.
 * The method can take any specific error code value, as specified in <a href="https://msdn.microsoft.com/en-us/library/cc231196.aspx">[MS-ERREF]</a>
 * section <a href="https://msdn.microsoft.com/en-us/library/cc231199.aspx">2.2</a>.
 * </p>
 *
 * <p>If the <em>Flags</em> parameter is not equal to zero, the
 * server SHOULD fail the call with an implementation-specific error code.<a href="https://msdn.microsoft.com/en-us/library/cc247297.aspx#Appendix_A_110">&lt;110&gt;</a></p>
 *
 * <p>In response to a NetprPathCanonicalize message, the server
 * MUST compute the canonical version of the specified path name or return an
 * error code.</p>
 *
 * <p>The <em>PathName</em> parameter specifies the path name that
 * needs to be canonicalized.</p>
 *
 * <p>The <em>PathType</em> parameter, if nonzero, MUST specify the
 * path type of the path that is specified by the <em>PathName</em> parameter by a
 * previous successful call to the <a href="https://msdn.microsoft.com/en-us/library/cc247256.aspx">NetprPathType</a> method. Even
 * if it is set to the correct nonzero value by the client, the server can change
 * it because the canonicalized version of a name can be of a different type than
 * the original version. If <em>PathType</em> is zero, the server MUST validate and
 * get the type of <em>PathName</em> (as specified in section 3.1.4.29) first. If
 * this fails, the server MUST fail the call with an ERROR_INVALID_NAME error
 * code.</p>
 *
 * <p>The <em>Prefix</em> parameter, if it is a nonempty string,
 * specifies a path component that MUST be prefixed to <em>PathName</em> to get the
 * full path to canonicalize. The server MUST treat <em>Prefix</em> as a <em>PathName</em>:
 * it MUST validate and get the type of <em>Prefix</em> in the same way as it does
 * the <em>PathName</em>. If this fails, the server MUST fail the call with an
 * ERROR_INVALID_NAME error code. The optional <em>Prefix</em> parameter is a
 * convenience that this method provides to clients. The client is free to
 * construct the complete <em>PathName</em> and pass NULL for the <em>Prefix</em>. For
 * example, this parameter can be used when canonicalizing path names for a list
 * of files in a directory. In such a scenario, the value for <em>Prefix</em> is the
 * absolute path for the directory, and the value for <em>PathName</em> specifies
 * the relative path for a file.</p>
 *
 * <p>The <em>OutBufLen</em> parameter specifies the length of the
 * output buffer <em>OutBuf</em> that is provided by the client. If the length of
 * the canonicalized path name is greater than <em>OutBufLen</em>, the server MUST
 * fail the call with an NERR_BufTooSmall error code.</p>
 *
 * <p>The server MUST construct the path to canonicalize by
 * appending the <em>PathName</em> to the <em>Prefix</em>. If the <em>Prefix</em>
 * parameter does not end with one, the server SHOULD insert an implementation-specific
 * path separator between the <em>Prefix</em> and <em>PathName</em>.<a href="https://msdn.microsoft.com/en-us/library/cc247297.aspx#Appendix_A_111" >&lt;111&gt;</a> The server MUST then
 * canonicalize the resultant path. The canonicalization process is
 * implementation-dependent.<a href="https://msdn.microsoft.com/en-us/library/cc247297.aspx#Appendix_A_112" >&lt;112&gt;</a></p>
 *
 * <p>After the canonicalization is successfully finished, the
 * server MUST determine the path type of the canonicalized path name, as
 * specified in NetprPathType (section 3.1.4.29), and store the result
 * in the <em>PathType</em> parameter. Valid return codes for the <em>PathType</em>
 * parameter are as specified in Path Types (section 2.2.2.9). If this
 * fails, the server MUST fail the call with an ERROR_INVALID_NAME error code.</p>
 *
 * <p>The server MAY<a href="https://msdn.microsoft.com/en-us/library/cc247297.aspx#Appendix_A_113" >&lt;113&gt;</a>
 * enforce security measures to verify that the caller has the required
 * permissions to execute this call. If the server enforces these security
 * measures and the caller does not have the required credentials, the server
 * SHOULD<a href="https://msdn.microsoft.com/en-us/library/cc247297.aspx#Appendix_A_114" >&lt;114&gt;</a> fail the call.</p>
 */


public class NetprPathCanonicalizeRequest extends RequestCall<NetprPathCanonicalizeResponse> {
    // <NDR: pointer[conformant varying array]> [in, string, unique] SRVSVC_HANDLE ServerName
    private final WChar.NullTerminated serverName;
    // <NDR: pointer[conformant varying array]> [in, string] WCHAR* PathName
    private final WChar.NullTerminated pathName;
    // <NDR: unsigned long> [in, range(0,64000)] DWORD OutbufLen
    private final int outBufLen;
    // <NDR: pointer[conformant varying array]> [in, string] WCHAR* Prefix
    private final WChar.NullTerminated prefix;
    // <NDR: unsigned long> [in, out] DWORD* PathType
    private final int pathType;
    // <NDR: long> [in] DWORD Flags
    private final int flags;

    //private final static int MAX_BUFFER_SIZE = 64000;

    public NetprPathCanonicalizeRequest(WChar.NullTerminated serverName, WChar.NullTerminated
            pathName, int outBufLen, WChar.NullTerminated prefix, int pathType, int flags) {
        super(NetrOpCode.NetprPathCanonicalize.getOpCode());
        this.serverName = serverName;
        this.pathName = pathName;
        this.prefix = prefix;
        this.outBufLen = outBufLen;
        this.pathType = pathType;
        this.flags = flags;
    }

    public void marshal(final PacketOutput packetOut) throws IOException {
        if (packetOut.writeReferentID(this.serverName)) {
            packetOut.writeMarshallable(serverName);
            // Alignment for pathName
            packetOut.align(Alignment.FOUR);
        }
        packetOut.writeMarshallable(this.pathName);
        packetOut.align(Alignment.FOUR);
        packetOut.writeInt(outBufLen);
        packetOut.writeMarshallable(this.prefix);
        packetOut.align(Alignment.FOUR);
        packetOut.writeInt(pathType);
        packetOut.writeInt(flags);
    }

    @Override
    public NetprPathCanonicalizeResponse getResponseObject() {
        return new NetprPathCanonicalizeResponse();
    }
}
