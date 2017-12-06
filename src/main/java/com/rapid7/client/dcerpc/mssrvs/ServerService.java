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
import com.rapid7.client.dcerpc.mssrvs.messages.NetprPathCanonicalizeRequest;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareEnumRequest;
import com.rapid7.client.dcerpc.mssrvs.messages.NetrShareEnumResponse;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareEnumStruct;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo0;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo1;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfo2;
import com.rapid7.client.dcerpc.mssrvs.objects.ShareInfoContainer;
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

    public List<NetShareInfo0> getShares0() throws IOException {
        return new GetSharesRequest0().call().getShares();
    }

    public List<NetShareInfo1> getShares1() throws IOException {
        return new GetSharesRequest1().call().getShares();
    }

    public List<NetShareInfo2> getShares2() throws IOException {
        return new GetSharesRequest2().call().getShares();
    }

    class GetSharesRequest0 extends GetSharesRequest<ShareInfo0, NetShareInfo0> {
        @Override
        NetShareInfo0 convert(ShareInfo0 src) {
            return new NetShareInfo0(src.getNetName());
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo0>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest0(resumeHandle);
        }
    }

    class GetSharesRequest1 extends GetSharesRequest<ShareInfo1, NetShareInfo1> {
        @Override
        NetShareInfo1 convert(ShareInfo1 src) {
            return new NetShareInfo1(src.getNetName(), src.getType(), src.getRemark());
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo1>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest1(resumeHandle);
        }
    }

    class GetSharesRequest2 extends GetSharesRequest<ShareInfo2, NetShareInfo2> {
        @Override
        NetShareInfo2 convert(ShareInfo2 src) {
            return new NetShareInfo2(src.getNetName(), src.getType(), src.getRemark(),
                    src.getPermissions(), src.getMaxUses(), src.getCurrentUses(), src.getPath(), src.getPasswd());
        }

        @Override
        NetrShareEnumRequest<? extends ShareEnumStruct<? extends ShareInfoContainer<ShareInfo2>>> createRequest(
                Integer resumeHandle) {
            return new NetrShareEnumRequest.NetShareEnumRequest2(resumeHandle);
        }
    }

    abstract class GetSharesRequest<S extends ShareInfo, N extends NetShareInfo> {
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
}
