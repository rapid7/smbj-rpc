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
package com.rapid7.client.dcerpc.msrrp.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantVaryingByteArray;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.RPCSecurityDescriptor;

/**
<p>The BaseRegGetKeySecurity method is called by the client. In
response, the server returns a copy of the security descriptor that protects
the specified open registry key.</p>

<pre> error_status_t BaseRegGetKeySecurity(
   [in] RPC_HKEY hKey,
   [in] SECURITY_INFORMATION SecurityInformation,
   [in] PRPC_SECURITY_DESCRIPTOR pRpcSecurityDescriptorIn,
   [out] PRPC_SECURITY_DESCRIPTOR pRpcSecurityDescriptorOut
 );
</pre>

<p><strong>hKey: </strong>A handle to a key that MUST have been
opened previously by using one of the open methods that are specified in
section <a href="https://msdn.microsoft.com/en-us/library/cc244912.aspx">3.1.5</a>: <a href="https://msdn.microsoft.com/en-us/library/cc244950.aspx">OpenClassesRoot</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244952.aspx">OpenCurrentUser</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244953.aspx">OpenLocalMachine</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244954.aspx">OpenPerformanceData</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244957.aspx">OpenUsers</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244929.aspx">BaseRegCreateKey</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244939.aspx">BaseRegOpenKey</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244951.aspx">OpenCurrentConfig</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244956.aspx">OpenPerformanceText</a>, <a href="https://msdn.microsoft.com/en-us/library/cc244955.aspx">OpenPerformanceNlsText</a>.</p>

<p><strong>SecurityInformation:</strong> The information that is
needed to determine the type of security that is returned in <em>pRpcSecurityDescriptorOut</em>.
See <a href="https://msdn.microsoft.com/en-us/library/cc244926.aspx">SECURITY_INFORMATION</a>
(includes a list of possible <a href="https://msdn.microsoft.com/en-us/library/cc244893.aspx#gt_7bc13a0c-ca1a-4c78-b1bf-67e243f778bb">values</a>).</p>

<p><strong>pRpcSecurityDescriptorIn: </strong>A pointer to a
buffer containing a security descriptor. The client MUST provide a pointer to a
<a href="https://msdn.microsoft.com/en-us/library/cc244924.aspx">RPC_SECURITY_DESCRIPTOR</a>
with arbitrary contents. The server uses the size of this security descriptor
to validate the client has the correct amount of memory allocated for the
RPC_SECURITY_DESCRIPTOR pointed to by the <em>pRpcSecurityDescriptorOut</em>
parameter</p>

<p><strong>pRpcSecurityDescriptorOut:</strong> A pointer to a
buffer to which the requested security descriptor MUST be written.</p>

<p><strong>Return Values: </strong>The method returns 0
(ERROR_SUCCESS) to indicate success; otherwise, it returns a nonzero error
code, as specified in <a href="https://msdn.microsoft.com/en-us/library/cc231196.aspx">[MS-ERREF]</a>
section <a href="https://msdn.microsoft.com/en-us/library/cc231199.aspx">2.2</a>.
The most common error codes are listed in the following table.</p>
@see <a href="https://msdn.microsoft.com/en-us/library/cc244936.aspx">https://msdn.microsoft.com/en-us/library/cc244936.aspx</a>
 */
public class BaseRegGetKeySecurityRequest extends RequestCall<BaseRegGetKeySecurityResponse> {
    public static short OP_NUM = 12;
    private final byte[] hKey;
    private final int securityDescriptorInfo;
    private final int securityDescriptorSize;

    public BaseRegGetKeySecurityRequest(final byte[] hKey, final int securityDescriptorInfo,
            final int securityDescriptorSize) {
        super(OP_NUM);
        this.hKey = hKey;
        this.securityDescriptorInfo = securityDescriptorInfo;
        this.securityDescriptorSize = securityDescriptorSize;
    }

    @Override
    public void marshal(final PacketOutput out) throws IOException {
        // <NDR: fixed array> [in] RPC_HKEY hKey
        out.write(this.hKey);
        // <NDR: unsigned long> [in] SECURITY_INFORMATION SecurityInformation
        // Alignment: 4 - Already aligned, wrote 20 bytes above
        out.writeInt(this.securityDescriptorInfo);
        // <NDR: struct> [in] PRPC_SECURITY_DESCRIPTOR pRpcSecurityDescriptorIn
        final RPCSecurityDescriptor sd = new RPCSecurityDescriptor();
        sd.setCbInSecurityDescriptor(this.securityDescriptorSize);
        sd.setLpSecurityDescriptor(new byte[0]);
        out.writeMarshallable(sd);
    }

    @Override
    public BaseRegGetKeySecurityResponse getResponseObject() {
        return new BaseRegGetKeySecurityResponse();
    }
}
