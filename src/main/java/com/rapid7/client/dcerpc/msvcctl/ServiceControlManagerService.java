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

import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msrrp.objects.ContextHandle;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RControlServiceRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenSCManagerWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RStartServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.objects.RQueryServiceStatusInfo;
import com.rapid7.client.dcerpc.objects.EmptyResponse;
import com.rapid7.client.dcerpc.transport.RPCTransport;
import java.io.IOException;

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
        ROpenSCManagerWRequest request = new ROpenSCManagerWRequest("test",null, FULL_ACCESS);
        HandleResponse response = transport.call(request);
        return response.getHandle();
    }

    private ContextHandle getServiceHandle(String serviceName, ContextHandle serviceManagerHandle)
        throws IOException {
        ROpenServiceWRequest request = new ROpenServiceWRequest(serviceManagerHandle, serviceName, FULL_ACCESS);
        HandleResponse response = transport.call(request);
        return response.getHandle();
    }

    public boolean startService(String service, ContextHandle serviceManagerHandle)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RStartServiceWRequest request = new RStartServiceWRequest(serviceHandle);
        EmptyResponse response = transport.call(request);
        return (response.getReturnValue().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode()));
    }

    public RQueryServiceStatusInfo queryService(String service, ContextHandle serviceManagerHandle)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(serviceHandle);
        RQueryServiceStatusResponse response = transport.call(request);
        if (response.getReturnCode().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode())){
            return response.getrQueryServiceStatusInfo();
        } else return null;
    }

    public RQueryServiceStatusInfo stopService(String service, ContextHandle serviceManagerHandle)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RControlServiceRequest request = new RControlServiceRequest(serviceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);
        RQueryServiceStatusResponse response = transport.call(request);
        if (response.getReturnCode().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode())){
            return response.getrQueryServiceStatusInfo();
        } else return null;
    }

    public boolean changeServiceConfig(String service, ContextHandle serviceManagerHandle, int serviceType, int startType, int errorControl)
        throws IOException {
        ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle, serviceType, startType, errorControl);
        RChangeServiceConfigWResponse response = transport.call(request);
        return (response.getReturnCode().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode()));

    }
}
