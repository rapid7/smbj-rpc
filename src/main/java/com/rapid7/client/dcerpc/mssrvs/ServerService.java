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
package com.rapid7.client.dcerpc.mssrvs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo1;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo2;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo501;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo502;
import com.rapid7.client.dcerpc.mssrvs.dto.NetShareInfo503;
import com.rapid7.client.dcerpc.mssrvs.messages.NetprPathCanonicalizeRequest;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareEnumRequest;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareEnumResponse;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareGetInfoRequest;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo1;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo2;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo501;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo502;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo503;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfoContainer;
import com.rapid7.client.dcerpc.objects.WChar;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_MORE_ENTRIES;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_NO_MORE_ITEMS;
import static com.rapid7.client.dcerpc.mserref.SystemErrorCode.ERROR_SUCCESS;

/**
 * This class implements a partial service service in accordance with [MS-SRVS]: Server Service Remote Protocol which
 * specifies the Server Service Remote Protocol, which remotely enables file and printer sharing and named pipe access
 * to the server through the Server Message Block Protocol.
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc247080.aspx">[MS-SRVS]: Server Service Remote Protocol</a>
 */
public class ServerService extends Service {

    public ServerService(final RPCTransport transport) {
        super(transport);
    }

    public String getCanonicalizedName(String serverName, String pathName, String prefix,
            int outBufLength, int pathType, int flags) throws IOException {
        final NetprPathCanonicalizeRequest request =
                new NetprPathCanonicalizeRequest(serverName, pathName, outBufLength, prefix, pathType, flags);
        return callExpectSuccess(request, "NetprPathCanonicalize").getCanonicalizedPath();
    }

    public NetShareInfo0 getShare0(String shareName) throws IOException {
        final NetrShareGetInfoRequest.NetrShareGetInfoRequest0 request =
                new NetrShareGetInfoRequest.NetrShareGetInfoRequest0(WChar.NullTerminated.of(shareName));
        return parseShareInfo0(callExpectSuccess(request, "NetrShareGetInfo[0]").getShareInfo());
    }

    public NetShareInfo1 getShare1(String shareName) throws IOException {
        final NetrShareGetInfoRequest.NetrShareGetInfoRequest1 request =
                new NetrShareGetInfoRequest.NetrShareGetInfoRequest1(WChar.NullTerminated.of(shareName));
        return parseShareInfo1(callExpectSuccess(request, "NetrShareGetInfo[1]").getShareInfo());
    }

    public NetShareInfo2 getShare2(String shareName) throws IOException {
        final NetrShareGetInfoRequest.NetrShareGetInfoRequest2 request =
                new NetrShareGetInfoRequest.NetrShareGetInfoRequest2(WChar.NullTerminated.of(shareName));
        return parseShareInfo2(callExpectSuccess(request, "NetrShareGetInfo[2]").getShareInfo());
    }

    public NetShareInfo501 getShare501(String shareName) throws IOException {
        final NetrShareGetInfoRequest.NetrShareGetInfoRequest501 request =
                new NetrShareGetInfoRequest.NetrShareGetInfoRequest501(WChar.NullTerminated.of(shareName));
        return parseShareInfo501(callExpectSuccess(request, "NetrShareGetInfo[501]").getShareInfo());
    }

    public NetShareInfo502 getShare502(String shareName) throws IOException {
        final NetrShareGetInfoRequest.NetrShareGetInfoRequest502 request =
                new NetrShareGetInfoRequest.NetrShareGetInfoRequest502(WChar.NullTerminated.of(shareName));
        return parseShareInfo502(callExpectSuccess(request, "NetrShareGetInfo[502]").getShareInfo());
    }

    public NetShareInfo503 getShare503(String shareName) throws IOException {
        final NetrShareGetInfoRequest.NetrShareGetInfoRequest503 request =
                new NetrShareGetInfoRequest.NetrShareGetInfoRequest503(WChar.NullTerminated.of(shareName));
        return parseShareInfo503(callExpectSuccess(request, "NetrShareGetInfo[503]").getShareInfo());
    }

    public List<NetShareInfo0> getShares0() throws IOException {
        return new GetSharesRequest0().call().getShares();
    }

    public List<NetShareInfo1> getShares1() throws IOException {
        return new GetSharesRequest1().call().getShares();
    }

    public List<NetShareInfo2> getShares2() throws IOException {
        return new GetSharesRequest2().call().getShares();
    }

    public List<NetShareInfo501> getShares501() throws IOException {
        return new GetSharesRequest501().call().getShares();
    }

    public List<NetShareInfo502> getShares502() throws IOException {
        return new GetSharesRequest502().call().getShares();
    }

    public List<NetShareInfo503> getShares503() throws IOException {
        return new GetSharesRequest503().call().getShares();
    }

    private class GetSharesRequest0 extends GetSharesRequest<ShareInfo0, NetShareInfo0> {
        @Override
        NetShareInfo0 convert(ShareInfo0 src) {
            return ServerService.this.parseShareInfo0(src);
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo0>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest0(resumeHandle);
        }
    }

    private class GetSharesRequest1 extends GetSharesRequest<ShareInfo1, NetShareInfo1> {
        @Override
        NetShareInfo1 convert(ShareInfo1 src) {
            return ServerService.this.parseShareInfo1(src);
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo1>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest1(resumeHandle);
        }
    }

    private class GetSharesRequest2 extends GetSharesRequest<ShareInfo2, NetShareInfo2> {
        @Override
        NetShareInfo2 convert(ShareInfo2 src) {
            return ServerService.this.parseShareInfo2(src);
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo2>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest2(resumeHandle);
        }
    }

    private class GetSharesRequest501 extends GetSharesRequest<ShareInfo501, NetShareInfo501> {
        @Override
        NetShareInfo501 convert(ShareInfo501 src) {
            return ServerService.this.parseShareInfo501(src);
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo501>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest501(resumeHandle);
        }
    }

    private class GetSharesRequest502 extends GetSharesRequest<ShareInfo502, NetShareInfo502> {
        @Override
        NetShareInfo502 convert(ShareInfo502 src) {
            return ServerService.this.parseShareInfo502(src);
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo502>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest502(resumeHandle);
        }
    }

    private class GetSharesRequest503 extends GetSharesRequest<ShareInfo503, NetShareInfo503> {
        @Override
        NetShareInfo503 convert(ShareInfo503 src) {
            return ServerService.this.parseShareInfo503(src);
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo503>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest503(resumeHandle);
        }
    }

    private abstract class GetSharesRequest<S extends ShareInfo, N extends NetShareInfo> {
        private final List<N> shares;

        GetSharesRequest() {
            this.shares = new ArrayList<>();
        }

        List<N> getShares() {
            return this.shares;
        }

        abstract N convert(S src);
        abstract NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<S>>> createRequest(Integer resumeHandle);

        GetSharesRequest<S, N> call() throws IOException {
            Integer resumeHandle = null;
            while (true) {
                final NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<S>>> request = createRequest(resumeHandle);
                final NetrShareEnumResponse<? extends ShareEnumStruct<? extends ShareInfoContainer<S>>> response = ServerService.this.call(request);
                final int returnCode = response.getReturnValue();
                if (ERROR_MORE_ENTRIES.is(returnCode) || ERROR_NO_MORE_ITEMS.is(returnCode) || ERROR_SUCCESS.is(returnCode)) {
                    for (S src : response.getShareEnumStruct().getShareInfoContainer().getBuffer()) {
                        shares.add(convert(src));
                    }
                } else {
                    throw new RPCException("NetrShareEnum", returnCode);
                }
                if (ERROR_NO_MORE_ITEMS.is(returnCode) || ERROR_SUCCESS.is(returnCode))
                    return this;
                resumeHandle = response.getResumeHandle();
            }
        }
    }

    private NetShareInfo0 parseShareInfo0(final ShareInfo0 obj) {
        if (obj == null)
            return null;
        return new NetShareInfo0(obj.getNetName());
    }

    private NetShareInfo1 parseShareInfo1(final ShareInfo1 obj) {
        if (obj == null)
            return null;
        return new NetShareInfo1(obj.getNetName(), obj.getType(), obj.getRemark());
    }

    private NetShareInfo2 parseShareInfo2(final ShareInfo2 obj) {
        if (obj == null)
            return null;
        return new NetShareInfo2(obj.getNetName(), obj.getType(), obj.getRemark(),
                obj.getPermissions(), obj.getMaxUses(), obj.getCurrentUses(), obj.getPath(), obj.getPasswd());
    }

    private NetShareInfo501 parseShareInfo501(final ShareInfo501 obj) {
        if (obj == null)
            return null;
        return new NetShareInfo501(obj.getNetName(), obj.getType(), obj.getRemark(), obj.getFlags());
    }

    private NetShareInfo502 parseShareInfo502(final ShareInfo502 obj) {
        if (obj == null)
            return null;
        return new NetShareInfo502(obj.getNetName(), obj.getType(), obj.getRemark(), obj.getPermissions(),
                obj.getMaxUses(), obj.getCurrentUses(), obj.getPath(), obj.getPasswd(), obj.getSecurityDescriptor());
    }

    private NetShareInfo503 parseShareInfo503(final ShareInfo503 obj) {
        if (obj == null)
            return null;
        return new NetShareInfo503(obj.getNetName(), obj.getType(), obj.getRemark(), obj.getPermissions(),
                obj.getMaxUses(), obj.getCurrentUses(), obj.getPath(), obj.getPasswd(), obj.getServerName(),
                obj.getSecurityDescriptor());
    }
}
