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
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceConfigInfo;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceHandle;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceManagerHandle;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.enums.ServicesAcceptedControls;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RControlServiceRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenSCManagerWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.ROpenServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceConfigWResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RQueryServiceStatusResponse;
import com.rapid7.client.dcerpc.msvcctl.messages.RStartServiceWRequest;
import com.rapid7.client.dcerpc.msvcctl.dto.IServiceConfigInfo;
import com.rapid7.client.dcerpc.msvcctl.dto.IServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.objects.LPQueryServiceConfigW;
import com.rapid7.client.dcerpc.msvcctl.objects.LPServiceStatus;
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
        final RQueryServiceStatusResponse response = callExpectSuccess(request, "RQueryServiceStatus");
        return parseLPServiceStatus(response.getLpServiceStatus());
    }

    public IServiceStatusInfo stopService(ServiceManagerHandle serviceManagerHandle, String service)
            throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RControlServiceRequest request =
                new RControlServiceRequest(serviceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);
        final RQueryServiceStatusResponse response = callExpectSuccess(request, "RControlServiceRequest");
        return parseLPServiceStatus(response.getLpServiceStatus());
    }

    public void changeServiceConfig(ServiceManagerHandle serviceManagerHandle, String service,
            IServiceConfigInfo serviceConfigInfo) throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle, serviceConfigInfo);
        callExpectSuccess(request, "RChangeServiceConfigW");
    }

    public IServiceConfigInfo queryServiceConfig(ServiceManagerHandle serviceManagerHandle, String service)
            throws IOException {
        final byte[] serviceHandle = getServiceHandle(serviceManagerHandle, service);
        final RQueryServiceConfigWRequest request =
                new RQueryServiceConfigWRequest(serviceHandle, RQueryServiceConfigWRequest.MAX_BUFFER_SIZE);
        final RQueryServiceConfigWResponse response = callExpectSuccess(request, "RQueryServiceConfigW");
        return parseLPQueryServiceConfigW(response.getLpServiceConfig());
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

    private ServiceStatusInfo parseLPServiceStatus(final LPServiceStatus response) {
        final ServiceType serviceType = ServiceType.fromInt(response.getDwServiceType());
        final ServiceStatusType currentState = ServiceStatusType.fromInt(response.getDwCurrentState());
        final ServicesAcceptedControls controlsAccepted = ServicesAcceptedControls.fromInt(response.getDwControlsAccepted());
        return new ServiceStatusInfo(serviceType, currentState, controlsAccepted, response.getDwWin32ExitCode(),
                response.getDwServiceSpecificExitCode(), response.getDwCheckPoint(), response.getDwWaitHint());
    }

    private ServiceConfigInfo parseLPQueryServiceConfigW(final LPQueryServiceConfigW response) {
        final ServiceType serviceType = ServiceType.fromInt(response.getDwServiceType());
        final ServiceStartType serviceStartType = ServiceStartType.fromInt(response.getDwStartType());
        final ServiceError serviceError = ServiceError.fromInt(response.getDwErrorControl());
        final String binaryPathName = (response.getLpBinaryPathName() == null) ? null : response.getLpBinaryPathName().getValue();
        final String loadOrderGroup = (response.getLpLoadOrderGroup() == null) ? null : response.getLpLoadOrderGroup().getValue();
        final int tagId = response.getDwTagId();
        final String[] dependencies = response.getLpDependencies();
        final String serviceStartName = (response.getLpServiceStartName() == null) ? null : response.getLpServiceStartName().getValue();
        final String displayName = (response.getLpDisplayName() == null) ? null : response.getLpDisplayName().getValue();
        return new ServiceConfigInfo(serviceType, serviceStartType, serviceError,
                binaryPathName, loadOrderGroup, tagId, dependencies, serviceStartName, displayName, null);
    }
}
