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
import java.rmi.UnmarshalException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.RequestResponse;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo1;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo2;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo501;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo502;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo503;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfoLevel;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc247236.aspx">NetrShareGetInfo</a>
 */
public abstract class NetrShareGetInfoResponse<T extends ShareInfo> extends RequestResponse {

    private T shareInfo;

    public T getShareInfo() {
        return this.shareInfo;
    }

    public abstract ShareInfoLevel getShareInfoLevel();

    abstract T createShareInfo();

    @Override
    public void unmarshalResponse(PacketInput packetIn) throws IOException {
        // <NDR: union> [out, switch_is(Level)] LPSHARE_INFO InfoStruct
        final int infoLevel = packetIn.readInt();
        if (infoLevel != getShareInfoLevel().getInfoLevel()) {
            throw new UnmarshalException(String.format(
                    "Incoming LPSHARE_INFO level %d does not match expected: %d",
                    infoLevel, getShareInfoLevel().getInfoLevel()));
        }
        if (packetIn.readReferentID() != 0) {
            this.shareInfo = createShareInfo();
            packetIn.readUnmarshallable(this.shareInfo);
        } else {
            this.shareInfo = null;
        }
    }

    public static class NetrShareGetInfoResponse0 extends NetrShareGetInfoResponse<ShareInfo0> {
        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_0;
        }

        @Override
        ShareInfo0 createShareInfo() {
            return new ShareInfo0();
        }
    }

    public static class NetrShareGetInfoResponse1 extends NetrShareGetInfoResponse<ShareInfo1> {
        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_1;
        }

        @Override
        ShareInfo1 createShareInfo() {
            return new ShareInfo1();
        }
    }

    public static class NetrShareGetInfoResponse2 extends NetrShareGetInfoResponse<ShareInfo2> {
        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_2;
        }

        @Override
        ShareInfo2 createShareInfo() {
            return new ShareInfo2();
        }
    }

    public static class NetrShareGetInfoResponse501 extends NetrShareGetInfoResponse<ShareInfo501> {
        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_501;
        }

        @Override
        ShareInfo501 createShareInfo() {
            return new ShareInfo501();
        }
    }

    public static class NetrShareGetInfoResponse502 extends NetrShareGetInfoResponse<ShareInfo502> {
        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_502;
        }

        @Override
        ShareInfo502 createShareInfo() {
            return new ShareInfo502();
        }
    }

    public static class NetrShareGetInfoResponse503 extends NetrShareGetInfoResponse<ShareInfo503> {
        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_503;
        }

        @Override
        ShareInfo503 createShareInfo() {
            return new ShareInfo503();
        }
    }
}
