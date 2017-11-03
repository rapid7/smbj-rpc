/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 */
package com.rapid7.client.dcerpc.msvcctl;

import java.io.IOException;
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RControlServiceRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenSCManagerWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceConfigWResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RStartServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceConfigInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.objects.EmptyResponse;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class ServiceControlManagerService
{

    public final static String REMOTE_REGISTRY = "Remoteregistry";
    public final static int FULL_ACCESS = 0x000F003F;

    private final RPCTransport transport;

    public ServiceControlManagerService(final RPCTransport transport) {
        this.transport = transport;
    }

    public ContextHandle getServiceManagerHandle()
        throws IOException {
        ROpenSCManagerWRequest request = new ROpenSCManagerWRequest(null, null, FULL_ACCESS);
        HandleResponse response = transport.call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()))
            return response.getHandle();
        else throw new RPCException("ServiceManagerHandle", response.getReturnValue());
    }

    private ContextHandle getServiceHandle(String serviceName, ContextHandle serviceManagerHandle)
        throws IOException {
        ROpenServiceWRequest request = new ROpenServiceWRequest(serviceManagerHandle, serviceName, FULL_ACCESS);
        HandleResponse response = transport.call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()))
            return response.getHandle();
        else throw new RPCException("ServiceHandle", response.getReturnValue());
    }

    public boolean startService(String service, ContextHandle serviceManagerHandle)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RStartServiceWRequest request = new RStartServiceWRequest(serviceHandle);
        EmptyResponse response = transport.call(request);
        return SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue());
    }

    public IServiceStatusInfo queryService(String service, ContextHandle serviceManagerHandle)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(serviceHandle);
        RQueryServiceStatusResponse response = transport.call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue())) {
            return response.getServiceStatusInfo();
        } else throw new RPCException("QueryService", response.getReturnValue());
    }

    public IServiceStatusInfo stopService(String service, ContextHandle serviceManagerHandle)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RControlServiceRequest request = new RControlServiceRequest(serviceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);
        RQueryServiceStatusResponse response = transport.call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue())) {
            return response.getServiceStatusInfo();
        } else throw new RPCException("StopService", response.getReturnValue());
    }

    public boolean changeServiceConfig(String service,
                                       ContextHandle serviceManagerHandle,
                                       ServiceConfigInfo serviceConfigInfo)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle, serviceConfigInfo);
        RChangeServiceConfigWResponse response = transport.call(request);
        return SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue());

    }

    public ServiceConfigInfo queryServiceConfig(String service, ContextHandle serviceManagerHandle) throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RQueryServiceConfigWRequest request = new RQueryServiceConfigWRequest(serviceHandle, RQueryServiceConfigWRequest.MAX_BUFFER_SIZE);
        RQueryServiceConfigWResponse response = transport.call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue())) {
            return response.getServiceConfigInfo();
        } else throw new RPCException("QueryServiceConfig", response.getReturnValue());
    }
}
