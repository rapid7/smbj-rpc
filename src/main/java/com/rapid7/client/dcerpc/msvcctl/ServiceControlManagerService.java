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
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceHandle;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceManagerHandle;
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
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class ServiceControlManagerService extends Service {

    public final static String REMOTE_REGISTRY = "Remoteregistry";
    public final static int FULL_ACCESS = 0x000F003F;

    public ServiceControlManagerService(final RPCTransport transport) {
        super(transport);
    }

    public ServiceManagerHandle getServiceManagerHandle() throws IOException {
        final ROpenSCManagerWRequest request = new ROpenSCManagerWRequest(null, null, FULL_ACCESS);
        return parseServiceManagerHandle(callExpectSuccess(request, "ROpenSCManagerW"));
    }

    public boolean startService(ServiceManagerHandle serviceManagerHandle, String service) throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RStartServiceWRequest request = new RStartServiceWRequest(serviceHandle);
        callExpectSuccess(request, "RStartServiceW");
        return true;
    }

    public IServiceStatusInfo queryService(ServiceManagerHandle serviceManagerHandle, String service)
            throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(serviceHandle);
        return callExpectSuccess(request, "RQueryServiceStatus").getServiceStatusInfo();
    }

    public IServiceStatusInfo stopService(ServiceManagerHandle serviceManagerHandle, String service)
            throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RControlServiceRequest request =
                new RControlServiceRequest(serviceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);
        return callExpectSuccess(request, "RControlServiceRequest").getServiceStatusInfo();
    }

    public boolean changeServiceConfig(ServiceManagerHandle serviceManagerHandle, String service,
            IServiceConfigInfo serviceConfigInfo) throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle, serviceConfigInfo);
        callExpectSuccess(request, "RChangeServiceConfigW");
        return true;
    }

    public ServiceConfigInfo queryServiceConfig(ServiceManagerHandle serviceManagerHandle, String service)
            throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RQueryServiceConfigWRequest request =
                new RQueryServiceConfigWRequest(serviceHandle, RQueryServiceConfigWRequest.MAX_BUFFER_SIZE);
        return callExpectSuccess(request, "RQueryServiceConfigW").getServiceConfigInfo();
    }

    private byte[] getServiceHandle(ServiceManagerHandle serviceManagerHandle, String serviceName) throws IOException {
        final ROpenServiceWRequest request =
                new ROpenServiceWRequest(parseHandle(serviceManagerHandle), serviceName, FULL_ACCESS);
        return callExpectSuccess(request, "ROpenServiceWRequest").getHandle();
    }

    private ServiceManagerHandle parseServiceManagerHandle(final HandleResponse response) {
        return new ServiceManagerHandle(response.getHandle());
    }

    private ServiceHandle parseServiceHandle(final HandleResponse response) {
        return new ServiceHandle(response.getHandle());
    }
}
