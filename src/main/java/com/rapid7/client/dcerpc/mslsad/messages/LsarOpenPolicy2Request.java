/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mslsad.messages;

import com.rapid7.client.dcerpc.messages.Request;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.protocol.commons.EnumWithValue.EnumUtils;
import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;
import java.util.EnumSet;

/**
 * <p>
 * The LsarOpenPolicy2 method opens a context handle to the <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_ae65dac0-cd24-4e83-a946-6d1097b71553">RPC
 * server</a>. This is the first function that MUST be called to contact the
 * Local Security Authority (Domain Policy) Remote Protocol database.
 * </p>
 * 
 * <dl>
 * <dd><div>
 * 
 * <pre>
 * &nbsp;NTSTATUS LsarOpenPolicy2(
&nbsp;  [in, unique, string] wchar_t* SystemName,
&nbsp;  [in] PLSAPR_OBJECT_ATTRIBUTES ObjectAttributes,
&nbsp;  [in] ACCESS_MASK DesiredAccess,
&nbsp;  [out] LSAPR_HANDLE* PolicyHandle
&nbsp;);
 * </pre>
 * 
 * </div></dd>
 * </dl>
 * 
 * <p>
 * <strong>SystemName: </strong>This parameter does not have any effect on
 * message processing in any environment. It MUST be ignored on receipt.
 * </p>
 * 
 * <p>
 * <strong>ObjectAttributes: </strong>This parameter does not have any effect on
 * message processing in any environment. All fields
 * MUST<a id="Appendix_A_Target_54"></a><a href=
 * "https://msdn.microsoft.com/en-us/library/cc234419.aspx#Appendix_A_54">
 * &lt;54&gt;</a> be ignored except
 * <strong>RootDirectory</strong> which MUST be NULL.
 * </p>
 * 
 * <p>
 * <strong>DesiredAccess: </strong>An <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234244.aspx">ACCESS_MASK</a>
 * value that specifies the requested access rights that MUST be granted on the
 * returned PolicyHandle if the request is successful.
 * </p>
 * 
 * <p>
 * <strong>PolicyHandle: </strong>An <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_8a7f6700-8311-45bc-af10-82e10accd331">RPC</a>
 * context handle (as specified in section
 * <a href="https://msdn.microsoft.com/en-us/library/cc234257.aspx">2.2.2.1</a>)
 * that represents a reference to the abstract data model of a policy object, as
 * specified in section
 * <a href="https://msdn.microsoft.com/en-us/library/cc234319.aspx">3.1.1.1</a>.
 * </p>
 * 
 * <p>
 * <strong>Return Values: </strong>The following is a summary of the return
 * values that an implementation MUST return, as specified by the message
 * processing below.
 * </p>
 * 
 * <dl>
 * <dd>
 * <table summary="table">
 * <tbody>
 * <tr>
 * <th scope="col">
 * <p>
 * Return value/code
 * </p>
 * </th>
 * <th scope="col">
 * <p>
 * Description
 * </p>
 * </th>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * 0x00000000
 * </p>
 * <p>
 * STATUS_SUCCESS
 * </p>
 * </td>
 * <td>
 * <p>
 * The request was successfully completed.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * 0xC0000022
 * </p>
 * <p>
 * STATUS_ACCESS_DENIED
 * </p>
 * </td>
 * <td>
 * <p>
 * The caller does not have the permissions to perform this operation.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td>
 * <p>
 * 0xC000000D
 * </p>
 * <p>
 * STATUS_INVALID_PARAMETER
 * </p>
 * </td>
 * <td>
 * <p>
 * One of the supplied parameters is incorrect. For example, this can happen
 * when <em>ObjectAttributes</em> is NULL or <em>DesiredAccess</em> is zero.
 * </p>
 * </td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * 
 * <p>
 * Processing:
 * </p>
 * 
 * <p>
 * <em>DesiredAccess</em>: A bitmask specifying the access that the caller
 * attempts to obtain on the policy object, which is access-checked according to
 * section <a href=
 * "https://msdn.microsoft.com/en-us/library/gg723371.aspx">3.1.4.2.1</a>. The
 * method-specific portion of the check is the following:
 * </p>
 * 
 * <dl>
 * <dd><div>
 * 
 * <pre>
 * &nbsp;LET serverInfo be a SERVER_INFO_101 structure
&nbsp;CALL ServerGetInfo(101, &amp;serverInfo)
&nbsp;LET isDomainController be a boolean initialized to FALSE
&nbsp;IF (serverInfo.sv101_version_type &amp; (SV_TYPE_DOMAIN_CTRL | SV_TYPE_DOMAIN_BAKCTRL)) THEN
&nbsp;&nbsp;&nbsp;&nbsp; Set isDomainController equal to TRUE
&nbsp;END IF
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
&nbsp;IF ((isDomainController equals FALSE) and (IsRequestorAnonymous() and LsaRestrictAnonymous is set to TRUE)) THEN
&nbsp;&nbsp;&nbsp;&nbsp; Return STATUS_ACCESS_DENIED
&nbsp;END IF
 * </pre>
 * 
 * </div></dd>
 * </dl>
 * 
 * <p>
 * SERVER_INFO_101, SV_TYPE_DOMAIN_CTRL, and SV_TYPE_DOMAIN_BACKCTRL are
 * specified in <a href=
 * "https://msdn.microsoft.com/en-us/library/cc230273.aspx">[MS-DTYP]</a>
 * section
 * <a href="https://msdn.microsoft.com/en-us/library/gg465309.aspx">2.3.12</a>.
 * The ServerGetInfo procedure is specified in [MS-DTYP] section
 * <a href="https://msdn.microsoft.com/en-us/library/gg465317.aspx">2.6</a>. The
 * valid account-rights bits are specified in section <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234246.aspx">2.2.1.1.2</a>, and
 * the <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_e5213722-75a9-44e7-b026-8e4833f0d350">security
 * descriptor</a> is specified in section 3.1.1.1.&nbsp; The
 * IsRequestorAnonymous procedure is specified in section <a href=
 * "https://msdn.microsoft.com/en-us/library/gg723204.aspx">3.1.4.2.3</a>.
 * </p>
 * 
 * <p>
 * <em>PolicyHandle</em>: If the request is successful, the server MUST create
 * and return a context handle (section
 * <a href="https://msdn.microsoft.com/en-us/library/gg723131.aspx">3.1.1.7</a>)
 * via <em>PolicyHandle</em>, with its fields initialized as follows:
 * </p>
 * 
 * <ul>
 * <li>
 * <p>
 *  LsaContextHandle.HandleType = "Policy"
 * </p>
 * 
 * </li>
 * <li>
 * <p>
 *  LsaContextHandle.Object = the policy object
 * </p>
 * 
 * </li>
 * <li>
 * <p>
 *  LsaContextHandle.GrantedAccess = as specified in
 * section 3.1.4.2.1
 * </p>
 * 
 * </li>
 * </ul>
 * <p>
 * The return value MUST be set to STATUS_SUCCESS in this case.
 * </p>
 * 
 * @see <a href=
 *      "https://msdn.microsoft.com/en-us/library/cc234337.aspx">3.1.4.4.1
 *      LsarOpenPolicy2 (Opnum 44)</a>
 */
public class LsarOpenPolicy2Request extends Request<HandleResponse> {

    public LsarOpenPolicy2Request(final String systemName, final EnumSet<AccessMask> desiredAccess) {
        super(OP_NUM);
        putStringRef(systemName, true);

        // LSAPR_OBJECT_ATTRIBUTES
        putInt(24);
        putNull();
        putNull();
        putInt(0);
        putNull();

        putInt(0x12345678);
        putInt(12);
        putShort((short) 2);
        putByte((byte) 0x01);
        putByte((byte) 0x00);
        // LSAPR_OBJECT_ATTRIBUTES ENDS

        putInt((int) EnumUtils.toLong(desiredAccess));
    }

    @Override
    protected HandleResponse parsePDUResponse(final ByteBuffer responseBuffer) throws TransportException {
        return new HandleResponse(responseBuffer);
    }

    private final static short OP_NUM = 44;
}
