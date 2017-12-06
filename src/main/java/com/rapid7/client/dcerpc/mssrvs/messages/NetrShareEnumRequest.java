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

package com.rapid7.client.dcerpc.mssrvs.messages;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssrvs.NetrOpCode;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumLevel;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct1;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct2;

public abstract class NetrShareEnumRequest<T extends ShareEnumStruct> extends RequestCall<NetrShareEnumResponse<T>> {
    /**
     * Specifies the preferred maximum length, in bytes, of the returned data. If the specified value is
     * MAX_PREFERRED_LENGTH, the method MUST attempt to return all entries.
     */
    private final static int MAX_BUFFER_SIZE = 1048576;

    private final Integer resumeHandle;

    public NetrShareEnumRequest(final Integer resumeHandle) {
        super(NetrOpCode.NetrShareEnum.getOpCode());
        this.resumeHandle = resumeHandle;
    }

    abstract ShareEnumLevel getShareEnumLevel();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.writeNull();
        packetOut.writeInt(getShareEnumLevel().getInfoLevel());
        packetOut.writeInt(getShareEnumLevel().getInfoLevel());
        packetOut.writeReferentID();
        packetOut.writeInt(0);
        packetOut.writeNull();
        packetOut.writeInt(MAX_BUFFER_SIZE);
        if (resumeHandle != null) {
            packetOut.writeReferentID();
            packetOut.writeInt(this.resumeHandle);
        } else {
            packetOut.writeNull();
        }
    }

    public static class NetShareEnumRequest0 extends NetrShareEnumRequest<ShareEnumStruct0> {
        public NetShareEnumRequest0(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_0_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct0> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse0();
        }
    }

    public static class NetShareEnumRequest1 extends NetrShareEnumRequest<ShareEnumStruct1> {
        public NetShareEnumRequest1(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct1> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse1();
        }
    }

    public static class NetShareEnumRequest2 extends NetrShareEnumRequest<ShareEnumStruct2> {
        public NetShareEnumRequest2(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_2_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct2> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse2();
        }
    }
}
