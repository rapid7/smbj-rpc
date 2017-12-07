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

    public abstract ShareEnumLevel getShareEnumLevel();

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

    public static class NetShareEnumRequest0 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct0> {
        public NetShareEnumRequest0(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_0_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct0> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse0();
        }
    }

    public static class NetShareEnumRequest1 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct1> {
        public NetShareEnumRequest1(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct1> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse1();
        }
    }

    public static class NetShareEnumRequest2 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct2> {
        public NetShareEnumRequest2(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_2_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct2> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse2();
        }
    }

    public static class NetShareEnumRequest501 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct501> {
        public NetShareEnumRequest501(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_501_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct501> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse501();
        }
    }

    public static class NetShareEnumRequest502 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct502> {
        public NetShareEnumRequest502(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_502_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct502> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse502();
        }
    }

    public static class NetShareEnumRequest503 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct503> {
        public NetShareEnumRequest503(Integer resumeHandle) {
            super(resumeHandle);
        }

        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_503_CONTAINER;
        }

        @Override
        public NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct503> getResponseObject() {
            return new NetrShareEnumResponse.NetShareEnumResponse503();
        }
    }
}
