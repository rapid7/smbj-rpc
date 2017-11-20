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
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssrvs.NetrOpCode;

/**
 * <b>3.1.4.8 NetrShareEnum (Opnum 15)</b><br>
 * The NetrShareEnum method retrieves information about each shared resource on a server.
 *
 * <pre>
 * NET_API_STATUS NetrShareEnum(
 *    [in, string, unique] SRVSVC_HANDLE ServerName,
 *    [in, out] LPSHARE_ENUM_STRUCT InfoStruct,
 *    [in] DWORD PreferedMaximumLength,
 *    [out] DWORD* TotalEntries,
 *    [in, out, unique] DWORD* ResumeHandle
 * );
 * </pre>
 *
 * ServerName: An SRVSVC_HANDLE (section 2.2.1.1) pointer that identifies the server. The client MUST map this structure
 * to an RPC binding handle (see [C706] sections 4.3.5 and 5.1.5.2). If this parameter is NULL, the local computer is
 * used.<br>
 * <br>
 * InfoStruct: A pointer to a structure, in the format of a SHARE_ENUM_STRUCT (section 2.2.4.38), as specified in
 * section 2.2.4.38. The SHARE_ENUM_STRUCT structure has a Level member that specifies the type of structure to return
 * in the ShareInfo member. The Level member MUST be one of the values specified in section 2.2.4.38.<br>
 * <br>
 * PreferedMaximumLength: Specifies the preferred maximum length, in bytes, of the returned data. If the specified value
 * is MAX_PREFERRED_LENGTH, the method MUST attempt to return all entries.<br>
 * <br>
 * TotalEntries: The total number of entries that could have been enumerated if the buffer had been big enough to hold
 * all the entries.<br>
 * <br>
 * ResumeHandle: A pointer to a value that contains a handle, which is used to continue an existing share search in
 * ShareList. The handle MUST be zero on the first call and remain unchanged for subsequent calls. If the ResumeHandle
 * parameter is NULL, no resume handle MUST be stored. If this parameter is not NULL and the method returns
 * ERROR_MORE_DATA, this parameter receives a nonzero value that can be passed in subsequent calls to this method to
 * continue with the enumeration in ShareList.<br>
 * <br>
 * <ul>
 * <li>If this parameter is NULL or points to 0x00000000, the enumeration starts from the beginning of the
 * ShareList.</li>
 * </ul>
 * <br>
 * Return Values: The method returns 0x00000000 (NERR_Success) to indicate success; otherwise, it returns a nonzero
 * error code. The method can take any specific error code value, as specified in [MS-ERREF] section 2.2. The most
 * common error codes are listed in the following table.<br>
 * <br>
 * <table border="1" summary="">
 * <tr>
 * <td>Return value/code</td>
 * <td>Description</td>
 * </tr>
 * <tr>
 * <td>NERR_Success (0x00000000)</td>
 * <td>The client request succeeded.</td>
 * </tr>
 * <tr>
 * <td>ERROR_MORE_DATA (0x000000EA)</td>
 * <td>The client request succeeded. More entries are available. Not all entries could be returned in the buffer size
 * that is specified by PreferedMaximumLength.</td>
 * </tr>
 * <tr>
 * <td>ERROR_INVALID_LEVEL (0x0000007C)</td>
 * <td>The system call level is not correct.</td>
 * </tr>
 * </table>
 * If ServerName does not match any Transport.ServerName in TransportList with the SVTI2_SCOPED_NAME bit set in
 * Transport.Flags, the server MUST reset ServerName as "*".<br>
 * <br>
 * The server MUST remove any preceding "\\" from the ServerName parameter and normalize the ServerName parameter as
 * specified in section 3.1.6.8, passing in the updated ServerName parameter as the ServerName, and an empty string as
 * the ShareName.<br>
 * <br>
 * In response to a NetrShareEnum request, the server MUST enumerate the Share entries in ShareList based on the value
 * of the ResumeHandle parameter and query share properties by invoking the underlying server events as specified in
 * [MS-CIFS] section 3.3.4.12 or [MS-SMB] section 3.3.4.7, and [MS-SMB2] section 3.3.4.16, providing the tuple
 * &lt;normalized server name, Share.ShareName&gt; as the input parameter. When the server receives STATUS_SUCCESS for a
 * share, it MUST consider the received SHARE_INFO_503_I and SHARE_INFO_1005 structures as valid. The server MUST return
 * information about each shared resource on a server.<br>
 * <br>
 * The InfoStruct parameter has a Level member. The valid values of Level are 0, 1, 2, 501, 502, and 503. If the Level
 * member is not equal to one of the valid values, the server MUST fail the call with an ERROR_INVALID_LEVEL error
 * code.<br>
 * <br>
 * The server MUST use the shares in valid SHARE_INFO_503_I and SHARE_INFO_1005 structures returned from either CIFS or
 * SMB2 server and fill the return structures as follows. For each share, the server MUST discard the structures
 * received from other file server except the value of share.shi503_current_uses.<br>
 * <br>
 * If the Level member is 503, the server MUST return all shares in SHARE_INFO_503_I structures. Otherwise, the server
 * MUST return the shares in which share.shi503_servername matches ServerName.<br>
 * <br>
 * If the Level member is 0, the server MUST return the information about share resources by filling the
 * SHARE_INFO_0_CONTAINER structure in the ShareInfo member of the InfoStruct parameter. The SHARE_INFO_0_CONTAINER
 * structure contains an array of SHARE_INFO_0 structures. <br>
 * <br>
 * <ul>
 * <li>shi0_netname MUST be set to share.shi503_netname.</li>
 * </ul>
 * <br>
 * If the Level member is 1, the server MUST return the information about share resources by filling the
 * SHARE_INFO_1_CONTAINER structure in the ShareInfo member of the InfoStruct parameter. The SHARE_INFO_1_CONTAINER
 * structure contains an array of SHARE_INFO_1 structures. <br>
 * <br>
 * <ul>
 * <li>shi1_netname MUST be set to share.shi503_netname.</li>
 * <li>shi1_type MUST be set to share.shi503_type.</li>
 * <li>shi1_remark MUST be set to share.shi503_remark.</li>
 * </ul>
 * <br>
 * If the Level member is 2, the server MUST return the information about share resources by filling the
 * SHARE_INFO_2_CONTAINER structure in the ShareInfo member of the InfoStruct parameter. The SHARE_INFO_2_CONTAINER
 * structure contains an array of SHARE_INFO_2 structures. <br>
 * <br>
 * <ul>
 * <li>shi2_netname MUST be set to share.shi503_netname.</li>
 * <li>shi2_type MUST be set to share.shi503_type.</li>
 * <li>shi2_remark MUST be set to share.shi503_remark.</li>
 * <li>shi2_permissions MUST be set to share.shi503_permissions.</li>
 * <li>shi2_max_uses MUST be set to share.shi503_max_uses.</li>
 * <li>shi2_current_uses MUST be set to the sum of share.shi503_current_uses values retrieved from both CIFS and SMB2
 * servers.</li>
 * <li>shi2_path MUST be set to share.shi503_path.</li>
 * <li>shi2_passwd MUST be set to share.shi503_passwd.</li>
 * </ul>
 * <br>
 * If the Level member is 501, the server MUST return the information about share resources by filling the
 * SHARE_INFO_501_CONTAINER structure in the ShareInfo member of the InfoStruct parameter. The SHARE_INFO_501_CONTAINER
 * structure contains an array of SHARE_INFO_501 structures. <br>
 * <br>
 * <ul>
 * <li>shi501_netname MUST be set to share.shi503_netname.</li>
 * <li>shi501_type MUST be set to share.shi503_type.</li>
 * <li>shi501_remark MUST be set to share.shi503_remark.</li>
 * <li>shi501_flags MUST be set to share.ShareFlags.</li>
 * </ul>
 * <br>
 * If the Level member is 502, the server MUST return the information about Share resources by filling the
 * SHARE_INFO_502_CONTAINER structure in the ShareInfo member of the InfoStruct parameter. The SHARE_INFO_502_CONTAINER
 * structure contains an array of SHARE_INFO_502_I structures. <br>
 * <br>
 * <ul>
 * <li>shi502_netname MUST be set to share.shi503_netname.</li>
 * <li>shi502_type MUST be set to share.shi503_type.</li>
 * <li>shi502_remark MUST be set to share.shi503_remark.</li>
 * <li>shi502_permissions MUST be set to share.shi503_permissions.</li>
 * <li>shi502_max_uses MUST be set to share.shi503_max_uses.</li>
 * <li>shi502_current_uses MUST be set to the sum of share.shi503_current_uses values retrieved from both CIFS and SMB2
 * servers.</li>
 * <li>shi502_path MUST be set to share.shi503_path.</li>
 * <li>shi502_passwd MUST be set to share.shi503_passwd.</li>
 * <li>shi502_security_descriptor MUST be set to share.shi503_security_descriptor</li>
 * </ul>
 * <br>
 * If the Level member is 503, the server MUST return the information about share resources in the SHARE_INFO_503_I
 * structure by filling the SHARE_INFO_503_CONTAINER structure in the ShareInfo member of the InfoStruct parameter,
 * except that shi503_current_uses MUST be set to the sum of share.shi503_current_uses values retrieved from both CIFS
 * and SMB2 server. The SHARE_INFO_503_CONTAINER structure contains an array of SHARE_INFO_503_I structures.<br>
 * <br>
 * The server MUST set the STYPE_CLUSTER_FS, STYPE_CLUSTER_SOFS, and STYPE_CLUSTER_DFS bits in the shi*_type field to
 * zero; the client MUST ignore them on receipt.<br>
 * <br>
 * The PreferedMaximumLength parameter specifies the maximum number of bytes that the server can return for the
 * ShareInfo buffer. If PreferedMaximumLength is insufficient to hold all the entries, the server MUST return the
 * maximum number of entries that will fit in the ShareInfo buffer and return ERROR_MORE_DATA. If this parameter is
 * equal to MAX_PREFERRED_LENGTH (section 2.2.2.2), the server MUST return all the requested data.<br>
 * <br>
 * If the server returns NERR_Success or ERROR_MORE_DATA, it MUST set the TotalEntries parameter to equal the total
 * number of entries that could have been enumerated from the current resume position.<br>
 * <br>
 * If PreferedMaximumLength is insufficient to hold all the entries and if the client has specified a ResumeHandle, the
 * server MUST set ResumeHandle to some implementation-specific value that allows the server to continue with this
 * enumeration on a subsequent call to this method with the same value for ResumeHandle.<br>
 * <br>
 * The server MUST maintain the share list in the order in which shares are inserted into ShareList.<br>
 * <br>
 * The following rules specify processing of the ResumeHandle parameter:<br>
 * <br>
 * <ul>
 * <li>If the ResumeHandle parameter is either NULL or points to 0x00000000, the enumeration MUST start from the
 * beginning of the ShareList.</li>
 * <li>If the ResumeHandle parameter points to a nonzero value, the server MUST validate the ResumeHandle.
 * <ul>
 * <li>If the value of the ResumeHandle is less than the size of the ShareList, the server MUST continue enumeration
 * based on the value of ResumeHandle. The value of ResumeHandle specifies the index into the ShareList after which
 * enumeration is to begin.</li>
 * <li>If the value of the ResumeHandle is greater than or equal to the size of the ShareList, the server MUST return
 * NERR_Success and zero entries.</li>
 * </ul>
 * </li>
 * <li>If the client specified a ResumeHandle and if the server returns ERROR_MORE_DATA (0x000000EA), the server MUST
 * set ResumeHandle to the index of the last enumerated share in the ShareList.</li>
 * </ul>
 * <br>
 * Because the ResumeHandle specifies the index into the ShareList, and the ShareList can be modified between multiple
 * requests, the results of a query spanning multiple requests using the ResumeHandle can be unreliable, offering either
 * duplicate or unavailable shares.<br>
 * <br>
 * The server SHOULD enforce security measures to verify that the caller has the required permissions to execute this
 * routine. If the caller does not have the required credentials, the server SHOULD fail the call.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc247276.aspx">3.1.4.8 NetrShareEnum (Opnum 15)</a>
 */
public class NetrShareEnumRequest extends RequestCall<NetrShareEnumResponse> {
    /**
     * A constant of type DWORD that is set to -1. This value is valid as an input parameter to any method in section
     * 3.1.4 that takes a PreferedMaximumLength parameter. When specified as an input parameter, this value indicates
     * that the method MUST allocate as much space as the data requires.
     */
    public final static int MAX_PREFERRED_LENGTH = -1;

    /**
     * Specifies the preferred maximum length, in bytes, of the returned data. If the specified value is
     * MAX_PREFERRED_LENGTH, the method MUST attempt to return all entries.
     */
    private final static int MAX_BUFFER_SIZE = 1048576;

    /**
     * The InfoStruct parameter has a Level member. The valid values of Level are 0, 1, 2, 501, 502, and 503. If the
     * Level member is not equal to one of the valid values, the server MUST fail the call with an ERROR_INVALID_LEVEL
     * error code.
     */
    private final int level;

    /**
     * A pointer to a value that contains a handle, which is used to continue an existing share search in ShareList. The
     * handle MUST be zero on the first call and remain unchanged for subsequent calls. If the ResumeHandle parameter is
     * NULL, no resume handle MUST be stored. If this parameter is not NULL and the method returns ERROR_MORE_DATA, this
     * parameter receives a nonzero value that can be passed in subsequent calls to this method to continue with the
     * enumeration in ShareList.
     */
    private final Integer resumeHandle;

    /**
     * The NetrShareEnum method retrieves information about each shared resource on a server.
     *
     * @param level        The InfoStruct parameter has a Level member. The valid values of Level are 0, 1, 2, 501, 502, and
     *                     503. If the Level member is not equal to one of the valid values, the server MUST fail the call with an
     *                     ERROR_INVALID_LEVEL error code.
     * @param resumeHandle A pointer to a value that contains a handle, which is used to continue an existing share
     *                     search in ShareList. The handle MUST be zero on the first call and remain unchanged for subsequent calls.
     *                     If the ResumeHandle parameter is NULL, no resume handle MUST be stored. If this parameter is not NULL and
     *                     the method returns ERROR_MORE_DATA, this parameter receives a nonzero value that can be passed in
     *                     subsequent calls to this method to continue with the enumeration in ShareList.
     */
    public NetrShareEnumRequest(final int level, final Integer resumeHandle) {
        super(NetrOpCode.NetrShareEnum.getOpCode());
        this.level = level;
        this.resumeHandle = resumeHandle;
    }

    @Override
    public NetrShareEnumResponse getResponseObject() {
        return new NetrShareEnumResponse();
    }

    @Override
    public void marshal(final PacketOutput stubOut) throws IOException {
        stubOut.writeNull();
        stubOut.writeInt(level);
        stubOut.writeInt(level);
        stubOut.writeReferentID();
        stubOut.writeInt(0);
        stubOut.writeNull();
        stubOut.writeInt(MAX_BUFFER_SIZE);
        stubOut.writeIntRef(resumeHandle);
    }
}
