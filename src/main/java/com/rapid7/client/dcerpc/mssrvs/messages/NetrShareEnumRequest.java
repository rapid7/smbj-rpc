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
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumLevel;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;

/**
 * <a href="https://msdn.microsoft.com/en-us/library/cc247276.aspx">NetrShareEnum</a>
 * <blockquote><pre>The NetrShareEnum method retrieves information about each shared resource on a server.
 *
 *      NET_API_STATUS NetrShareEnum(
 *              [in, string, unique] SRVSVC_HANDLE ServerName,
 *              [in, out] LPSHARE_ENUM_STRUCT InfoStruct,
 *              [in] DWORD PreferedMaximumLength,
 *              [out] DWORD* TotalEntries,
 *              [in, out, unique] DWORD* ResumeHandle
 *      );
 *
 * ServerName: An SRVSVC_HANDLE (section 2.2.1.1) pointer that identifies the server. The client MUST map this structure to an RPC binding handle (see [C706] sections 4.3.5 and 5.1.5.2). If this parameter is NULL, the local computer is used.
 * InfoStruct: A pointer to a structure, in the format of a SHARE_ENUM_STRUCT (section 2.2.4.38), as specified in section 2.2.4.38. The SHARE_ENUM_STRUCT structure has a Level member that specifies the type of structure to return in the ShareInfo member. The Level member MUST be one of the values specified in section 2.2.4.38.
 * PreferedMaximumLength: Specifies the preferred maximum length, in bytes, of the returned data. If the specified value is MAX_PREFERRED_LENGTH, the method MUST attempt to return all entries.
 * TotalEntries: The total number of entries that could have been enumerated if the buffer had been big enough to hold all the entries.
 * ResumeHandle: A pointer to a value that contains a handle, which is used to continue an existing share search in ShareList. The handle MUST be zero on the first call and remain unchanged for subsequent calls. If the ResumeHandle parameter is NULL, no resume handle MUST be stored. If this parameter is not NULL and the method returns ERROR_MORE_DATA, this parameter receives a nonzero value that can be passed in subsequent calls to this method to continue with the enumeration in ShareList.
 *      If this parameter is NULL or points to 0x00000000, the enumeration starts from the beginning of the ShareList.</pre></blockquote>
 */
public abstract class NetrShareEnumRequest<T extends ShareEnumStruct> extends RequestCall<NetrShareEnumResponse<T>> {
    // <NDR: pointer[struct]> [in, string, unique] SRVSVC_HANDLE ServerName,
    // Always use local
    // <NDR: struct> [in, out] LPSHARE_ENUM_STRUCT InfoStruct,
    // Implied from ShareEnumLevel
    // <NDR: unsigned long> [in] DWORD PreferedMaximumLength
    private final long preferredMaximumLength;
    // <NDR: unsigned long> [in, out, unique] DWORD* ResumeHandle
    private final Long resumeHandle;

    public NetrShareEnumRequest(final long preferredMaximumLength, final Long resumeHandle) {
        super(NetrOpCode.NetrShareEnum.getOpCode());
        this.preferredMaximumLength = preferredMaximumLength;
        this.resumeHandle = resumeHandle;
    }

    public abstract ShareEnumLevel getShareEnumLevel();

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        // <NDR: pointer[struct]> [in, string, unique] SRVSVC_HANDLE ServerName,
        packetOut.writeNull();
        // [in, out] LPSHARE_ENUM_STRUCT InfoStruct
        // <NDR: unsigned long> DWORD Level
        // Alignment: 4 - Already aligned
        packetOut.writeInt(getShareEnumLevel().getInfoLevel());
        // <NDR: unsigned long> [switch_is(Level)] SHARE_ENUM_UNION ShareInfo;
        // Alignment: 4 - Already aligned
        packetOut.writeInt(getShareEnumLevel().getInfoLevel());
        // <NDR: unsigned long> [switch_is(Level)] SHARE_ENUM_UNION ShareInfo;
        // Alignment: 4 - Already aligned
        packetOut.writeReferentID();
        // SHARE_INFO_*_CONTAINER
        // <NDR: unsigned long> Entries: 0
        // Alignment: 4 - Already aligned
        packetOut.writeInt(0);
        // SHARE_INFO_*_CONTAINER
        // <NDR: pointer[conformant array]> No buffer
        // Alignment: 4 - Already aligned
        packetOut.writeNull();
        // <NDR: unsigned long> [in] DWORD PreferedMaximumLength
        // Alignment: 4 - Already aligned
        packetOut.writeInt(this.preferredMaximumLength);
        // <NDR: pointer[unsigned long]> [in, out, unique] DWORD* ResumeHandle
        // Alignment: 4 - Already aligned
        if (packetOut.writeReferentID(this.resumeHandle)) {
            // <NDR: unsigned long> [in, out, unique] DWORD* ResumeHandle
            // Alignment: 4 - Already aligned
            packetOut.writeInt(this.resumeHandle);
        }
    }

    public static class NetShareEnumRequest0 extends NetrShareEnumRequest<ShareEnumStruct.ShareEnumStruct0> {
        public NetShareEnumRequest0(final long preferredMaximumLength, final Long resumeHandle) {
            super(preferredMaximumLength, resumeHandle);
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
        public NetShareEnumRequest1(final long preferredMaximumLength, final Long resumeHandle) {
            super(preferredMaximumLength, resumeHandle);
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
        public NetShareEnumRequest2(final long preferredMaximumLength, final Long resumeHandle) {
            super(preferredMaximumLength, resumeHandle);
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
        public NetShareEnumRequest501(final long preferredMaximumLength, final Long resumeHandle) {
            super(preferredMaximumLength, resumeHandle);
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
        public NetShareEnumRequest502(final long preferredMaximumLength, final Long resumeHandle) {
            super(preferredMaximumLength, resumeHandle);
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
        public NetShareEnumRequest503(final long preferredMaximumLength, final Long resumeHandle) {
            super(preferredMaximumLength, resumeHandle);
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
