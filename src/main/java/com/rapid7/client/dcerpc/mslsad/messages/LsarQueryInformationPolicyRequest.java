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
import com.rapid7.client.dcerpc.mslsad.objects.PolicyInformationClass;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.hierynomus.protocol.transport.TransportException;
import java.nio.ByteBuffer;

/**
 * <p>
 * The LsarQueryInformationPolicy method is invoked to query values that
 * represent the server's information policy.
 * </p>
 * 
 * <dl>
 * <dd><div>
 * 
 * <pre>
 * &nbsp;NTSTATUS LsarQueryInformationPolicy(
&nbsp;  [in] LSAPR_HANDLE PolicyHandle,
&nbsp;  [in] POLICY_INFORMATION_CLASS InformationClass,
&nbsp;  [out, switch_is(InformationClass)] 
&nbsp;    PLSAPR_POLICY_INFORMATION* PolicyInformation
&nbsp;);
 * </pre>
 * 
 * </div></dd>
 * </dl>
 * 
 * <p>
 * <strong>PolicyHandle: </strong>An <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234227.aspx#gt_8a7f6700-8311-45bc-af10-82e10accd331">RPC</a>
 * context handle obtained from either <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234389.aspx">LsarOpenPolicy</a>
 * or <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234337.aspx">LsarOpenPolicy2</a>.
 * </p>
 * 
 * <p>
 * <strong>InformationClass: </strong>A parameter that specifies what type of
 * information the caller is requesting.
 * </p>
 * 
 * <p>
 * <strong>PolicyInformation: </strong>A parameter that references policy
 * information structure on return.
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
 * <table responsive="true" summary="table">
 * <tbody>
 * <tr class="thead" responsive="true">
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
 * <td data-th=" Return value/code ">
 * <p>
 * 0x00000000
 * </p>
 * <p>
 * STATUS_SUCCESS
 * </p>
 * </td>
 * <td data-th=" Description ">
 * <p>
 * The request was successfully completed.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td data-th=" Return value/code ">
 * <p>
 * 0xC000009A
 * </p>
 * <p>
 * STATUS_INSUFFICIENT_RESOURCES
 * </p>
 * </td>
 * <td data-th=" Description ">
 * <p>
 * There are insufficient resources to complete the request.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td data-th=" Return value/code ">
 * <p>
 * 0xC0000022
 * </p>
 * <p>
 * STATUS_ACCESS_DENIED
 * </p>
 * </td>
 * <td data-th=" Description ">
 * <p>
 * The caller does not have the permissions to perform the operation.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td data-th=" Return value/code ">
 * <p>
 * 0xC000000D
 * </p>
 * <p>
 * STATUS_INVALID_PARAMETER
 * </p>
 * </td>
 * <td data-th=" Description ">
 * <p>
 * One of the parameters is incorrect. For instance, this can happen if
 * <em>InformationClass</em> is out of range or if <em>PolicyInformation</em> is
 * NULL.
 * </p>
 * </td>
 * </tr>
 * <tr>
 * <td data-th=" Return value/code ">
 * <p>
 * 0xC0000008
 * </p>
 * <p>
 * STATUS_INVALID_HANDLE
 * </p>
 * </td>
 * <td data-th=" Description ">
 * <p>
 * <em>PolicyHandle</em> is not a valid handle.
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
 * This message MUST be processed in an identical manner to <a href=
 * "https://msdn.microsoft.com/en-us/library/cc234321.aspx">LsarQueryInformationPolicy2</a>.
 * </p>
 * 
 * @see <a href=
 *      "https://msdn.microsoft.com/en-us/library/cc234390.aspx">3.1.4.4.4
 *      LsarQueryInformationPolicy (Opnum 7)</a>
 */
public class LsarQueryInformationPolicyRequest<T extends LsarQueryInformationPolicyResponse> extends Request<T> {

    public LsarQueryInformationPolicyRequest(final ContextHandle handle, final PolicyInformationClass infoLevel) {
        super(OP_NUM);
        this.infoLevel = infoLevel;
        putBytes(handle.getBytes());
        putInt(infoLevel.getInfoLevel());
    }

    @Override
    protected T parsePDUResponse(final ByteBuffer response) throws TransportException {
        switch (infoLevel) {
        case POLICY_AUDIT_EVENTS_INFORMATION:
            return (T) new PolicyAuditEventsInformationResponse(response);
        default:
            throw new UnsupportedOperationException("Unsupported policy information level:" + infoLevel.getInfoLevel());
        }
    }

    private final static short OP_NUM = 7;
    private final PolicyInformationClass infoLevel;
}
