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
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo1;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo2;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo501;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo502;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo503;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfoLevel;
import com.rapid7.client.dcerpc.objects.WChar;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc247236.aspx">NetrShareGetInfo</a>
 * <blockquote><pre>The NetrShareGetInfo method retrieves information about a particular shared resource on the server from the ShareList.
 *
 *      NET_API_STATUS NetrShareGetInfo(
 *          [in, string, unique] SRVSVC_HANDLE ServerName,
 *          [in, string] WCHAR* NetName,
 *          [in] DWORD Level,
 *          [out, switch_is(Level)] LPSHARE_INFO InfoStruct
 *      );
 *
 *  ServerName: An SRVSVC_HANDLE (section 2.2.1.1) pointer that identifies the server. The client MUST map this structure to an RPC binding handle ([C706] sections 4.3.5 and 5.1.5.2). If this parameter is NULL, the local computer is used.
 *  NetName: A pointer to a null-terminated UTF-16 string that specifies the name of the share to return information for.
 *  Level: Specifies the information level of the data. This parameter MUST be one of the following values.
 *      0       LPSHARE_INFO_0
 *      1       LPSHARE_INFO_1
 *      2       LPSHARE_INFO_2
 *      501     LPSHARE_INFO_501
 *      502     LPSHARE_INFO_502_I
 *      503     LPSHARE_INFO_503_I
 *      1005    LPSHARE_INFO_1005
 *  InfoStruct: This parameter is of type LPSHARE_INFO union, as specified in section 2.2.3.6. Its contents are determined by the value of the Level parameter, as shown in the preceding table.</pre></blockquote>
 */
public abstract class NetrShareGetInfoRequest<T extends ShareInfo> extends RequestCall<NetrShareGetInfoResponse<T>> {
    public static final short OP_NUM = 16;

    // <NDR: pointer[struct]> [in, string, unique] SRVSVC_HANDLE ServerName
    // Always use local
    // <NDR: struct> [in, string] WCHAR* NetName
    private final WChar.NullTerminated shareName;
    // <NDR: unsigned long> [in] DWORD Level
    // This is derived from getShareInfoLevel()

    public NetrShareGetInfoRequest(final WChar.NullTerminated shareName) {
        super(OP_NUM);
        if (shareName == null)
            throw new IllegalArgumentException("Despite what the documentation states, this is not a pointer");
        this.shareName = shareName;
    }

    public abstract ShareInfoLevel getShareInfoLevel();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: pointer[struct]> [in, string, unique] SRVSVC_HANDLE ServerName
        // Always use local
        packetOut.writeNull();
        // <NDR: struct> [in, string] WCHAR* NetName
        // Despite what the documentation states, this is not a pointer
        packetOut.writeMarshallable(this.shareName);
        // <NDR: unsigned long> [in] DWORD Level
        packetOut.align(Alignment.FOUR);
        packetOut.writeInt(getShareInfoLevel().getInfoLevel());
    }

    public static class NetrShareGetInfoRequest0 extends NetrShareGetInfoRequest<ShareInfo0> {
        public NetrShareGetInfoRequest0(WChar.NullTerminated shareName) {
            super(shareName);
        }

        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_0;
        }

        @Override
        public NetrShareGetInfoResponse.NetrShareGetInfoResponse0 getResponseObject() {
            return new NetrShareGetInfoResponse.NetrShareGetInfoResponse0();
        }
    }

    public static class NetrShareGetInfoRequest1 extends NetrShareGetInfoRequest<ShareInfo1> {
        public NetrShareGetInfoRequest1(WChar.NullTerminated shareName) {
            super(shareName);
        }

        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_1;
        }

        @Override
        public NetrShareGetInfoResponse.NetrShareGetInfoResponse1 getResponseObject() {
            return new NetrShareGetInfoResponse.NetrShareGetInfoResponse1();
        }
    }

    public static class NetrShareGetInfoRequest2 extends NetrShareGetInfoRequest<ShareInfo2> {
        public NetrShareGetInfoRequest2(WChar.NullTerminated shareName) {
            super(shareName);
        }

        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_2;
        }

        @Override
        public NetrShareGetInfoResponse.NetrShareGetInfoResponse2 getResponseObject() {
            return new NetrShareGetInfoResponse.NetrShareGetInfoResponse2();
        }
    }

    public static class NetrShareGetInfoRequest501 extends NetrShareGetInfoRequest<ShareInfo501> {
        public NetrShareGetInfoRequest501(WChar.NullTerminated shareName) {
            super(shareName);
        }

        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_501;
        }

        @Override
        public NetrShareGetInfoResponse.NetrShareGetInfoResponse501 getResponseObject() {
            return new NetrShareGetInfoResponse.NetrShareGetInfoResponse501();
        }
    }

    public static class NetrShareGetInfoRequest502 extends NetrShareGetInfoRequest<ShareInfo502> {
        public NetrShareGetInfoRequest502(WChar.NullTerminated shareName) {
            super(shareName);
        }

        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_502;
        }

        @Override
        public NetrShareGetInfoResponse.NetrShareGetInfoResponse502 getResponseObject() {
            return new NetrShareGetInfoResponse.NetrShareGetInfoResponse502();
        }
    }

    public static class NetrShareGetInfoRequest503 extends NetrShareGetInfoRequest<ShareInfo503> {
        public NetrShareGetInfoRequest503(WChar.NullTerminated shareName) {
            super(shareName);
        }

        @Override
        public ShareInfoLevel getShareInfoLevel() {
            return ShareInfoLevel.LPSHARE_INFO_503;
        }

        @Override
        public NetrShareGetInfoResponse.NetrShareGetInfoResponse503 getResponseObject() {
            return new NetrShareGetInfoResponse.NetrShareGetInfoResponse503();
        }
    }
}
