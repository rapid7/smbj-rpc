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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumLevel;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;

public abstract class NetrShareEnumResponse<T extends ShareEnumStruct> extends RequestResponse {
    private T shareEnumStruct;
    private long totalEntries;
    private Long resumeHandle;

    public abstract ShareEnumLevel getShareEnumLevel();

    public T getShareEnumStruct() {
        return shareEnumStruct;
    }

    public long getTotalEntries() {
        return totalEntries;
    }

    public Long getResumeHandle() {
        return resumeHandle;
    }

    abstract T createShareEnumStruct();

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        this.shareEnumStruct = createShareEnumStruct();
        // <NDR: struct> [in, out] LPSHARE_ENUM_STRUCT InfoStruct
        packetIn.readUnmarshallable(this.shareEnumStruct);
        // <NDR: unsigned long> [out] DWORD* TotalEntries
        packetIn.align(Alignment.FOUR);
        this.totalEntries = packetIn.readUnsignedInt();
        // <NDR: pointer[unsigned long]> [in, out, unique] DWORD* ResumeHandle
        // Alignment: 4 - Already aligned
        if (packetIn.readReferentID() != 0)
            // <NDR: unsigned long> [in, out, unique] DWORD* ResumeHandle
            // Alignment: 4 - Already aligned
            this.resumeHandle = packetIn.readUnsignedInt();
        else
            this.resumeHandle = null;
    }

    public static class NetShareEnumResponse0 extends NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct0> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_0_CONTAINER;
        }

        @Override
        ShareEnumStruct.ShareEnumStruct0 createShareEnumStruct() {
            return new ShareEnumStruct.ShareEnumStruct0();
        }
    }

    public static class NetShareEnumResponse1 extends NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct1> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        ShareEnumStruct.ShareEnumStruct1 createShareEnumStruct() {
            return new ShareEnumStruct.ShareEnumStruct1();
        }
    }

    public static class NetShareEnumResponse2 extends NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct2> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_1_CONTAINER;
        }

        @Override
        ShareEnumStruct.ShareEnumStruct2 createShareEnumStruct() {
            return new ShareEnumStruct.ShareEnumStruct2();
        }
    }

    public static class NetShareEnumResponse501 extends NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct501> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_501_CONTAINER;
        }

        @Override
        ShareEnumStruct.ShareEnumStruct501 createShareEnumStruct() {
            return new ShareEnumStruct.ShareEnumStruct501();
        }
    }

    public static class NetShareEnumResponse502 extends NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct502> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_502_CONTAINER;
        }

        @Override
        ShareEnumStruct.ShareEnumStruct502 createShareEnumStruct() {
            return new ShareEnumStruct.ShareEnumStruct502();
        }
    }

    public static class NetShareEnumResponse503 extends NetrShareEnumResponse<ShareEnumStruct.ShareEnumStruct503> {
        @Override
        public ShareEnumLevel getShareEnumLevel() {
            return ShareEnumLevel.SHARE_INFO_503_CONTAINER;
        }

        @Override
        ShareEnumStruct.ShareEnumStruct503 createShareEnumStruct() {
            return new ShareEnumStruct.ShareEnumStruct503();
        }
    }
}
