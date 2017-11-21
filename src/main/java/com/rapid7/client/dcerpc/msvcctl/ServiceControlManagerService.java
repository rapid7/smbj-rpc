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
package com.rapid7.client.dcerpc.msvcctl;

import java.io.IOException;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RControlServiceRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenSCManagerWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RStartServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceConfigInfo;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.objects.ServiceConfigInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class ServiceControlManagerService extends Service {

    public final static String REMOTE_REGISTRY = "Remoteregistry";
    public final static int FULL_ACCESS = 0x000F003F;

    public ServiceControlManagerService(final RPCTransport transport) {
        super(transport);
    }

    public ContextHandle getServiceManagerHandle() throws IOException {
        final ROpenSCManagerWRequest request = new ROpenSCManagerWRequest(null, null, FULL_ACCESS);
        return callExpectSuccess(request, "ROpenSCManagerW").getHandle();
    }

    private ContextHandle getServiceHandle(String serviceName, ContextHandle serviceManagerHandle) throws IOException {
        final ROpenServiceWRequest request = new ROpenServiceWRequest(serviceManagerHandle, serviceName, FULL_ACCESS);
        return callExpectSuccess(request, "ROpenServiceWRequest").getHandle();
    }

    public boolean startService(String service, ContextHandle serviceManagerHandle) throws IOException {
        final ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        final RStartServiceWRequest request = new RStartServiceWRequest(serviceHandle);
        callExpectSuccess(request, "RStartServiceW");
        return true;
    }

    public IServiceStatusInfo queryService(String service, ContextHandle serviceManagerHandle) throws IOException {
        final ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        final RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(serviceHandle);
        return callExpectSuccess(request, "RQueryServiceStatus").getServiceStatusInfo();
    }

    public IServiceStatusInfo stopService(String service, ContextHandle serviceManagerHandle) throws IOException {
        final ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        final RControlServiceRequest request =
                new RControlServiceRequest(serviceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);
        return callExpectSuccess(request, "RControlServiceRequest").getServiceStatusInfo();
    }

    public boolean changeServiceConfig(String service, ContextHandle serviceManagerHandle,
            IServiceConfigInfo serviceConfigInfo) throws IOException {
        final ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        final RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle, serviceConfigInfo);
        callExpectSuccess(request, "RChangeServiceConfigW");
        return true;
    }

    public ServiceConfigInfo queryServiceConfig(String service, ContextHandle serviceManagerHandle) throws IOException {
        final ContextHandle serviceHandle = getServiceHandle(service, serviceManagerHandle);
        final RQueryServiceConfigWRequest request = new RQueryServiceConfigWRequest(serviceHandle, RQueryServiceConfigWRequest.MAX_BUFFER_SIZE);
        return callExpectSuccess(request, "RQueryServiceConfigW").getServiceConfigInfo();
    }
}
