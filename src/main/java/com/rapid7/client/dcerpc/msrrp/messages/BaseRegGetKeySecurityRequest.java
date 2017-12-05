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
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.objects.RPCSecurityDescriptor;

public class BaseRegGetKeySecurityRequest extends RequestCall<BaseRegGetKeySecurityResponse> {
    public static short OP_NUM = 12;
    private final byte[] hKey;
    private final int securityDescriptorInfo;
    private final int securityDescriptorSize;

    public BaseRegGetKeySecurityRequest(byte[] hKey, int securityDescriptorInfo, int securityDescriptorSize) {
        super(OP_NUM);
        this.hKey = hKey;
        this.securityDescriptorInfo = securityDescriptorInfo;
        this.securityDescriptorSize = securityDescriptorSize;
    }

    @Override
    public void marshal(PacketOutput out) throws IOException {
        out.write(hKey);
        out.writeInt(securityDescriptorInfo);
        RPCSecurityDescriptor sd = new RPCSecurityDescriptor();
        sd.setMaxSize(securityDescriptorSize);
        out.writeMarshallable(sd);
    }

    @Override
    public BaseRegGetKeySecurityResponse getResponseObject() {
        return new BaseRegGetKeySecurityResponse();
    }
}
