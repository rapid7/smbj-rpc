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
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumLevel;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct1;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct2;

public abstract class NetrShareEnumResponse<T extends ShareEnumStruct> extends RequestResponse {
    private T shareEnumStruct;
    private long totalEntries;
    private Integer resumeHandle;

    public abstract ShareEnumLevel getShareEnumLevel();

    public T getShareEnumStruct() {
        return shareEnumStruct;
    }

    public long getTotalEntries() {
        return totalEntries;
    }

    public Integer getResumeHandle() {
        return resumeHandle;
    }

    abstract T createShareEnumStruct();

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        this.shareEnumStruct = createShareEnumStruct();
        packetIn.readUnmarshallable(this.shareEnumStruct);
        this.totalEntries = packetIn.readUnsignedInt();
        if (packetIn.readReferentID() != 0)
            this.resumeHandle = packetIn.readInt();
        else
            this.resumeHandle = null;
    }

    public static class NetShareEnumResponse0 extends NetrShareEnumResponse<ShareEnumStruct0> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_0_CONTAINER;
        }

        @Override
        ShareEnumStruct0 createShareEnumStruct() {
            return new ShareEnumStruct0();
        }
    }

    public static class NetShareEnumResponse1 extends NetrShareEnumResponse<ShareEnumStruct1> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        ShareEnumStruct1 createShareEnumStruct() {
            return new ShareEnumStruct1();
        }
    }

    public static class NetShareEnumResponse2 extends NetrShareEnumResponse<ShareEnumStruct2> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        ShareEnumStruct2 createShareEnumStruct() {
            return new ShareEnumStruct2();
        }
    }
}
