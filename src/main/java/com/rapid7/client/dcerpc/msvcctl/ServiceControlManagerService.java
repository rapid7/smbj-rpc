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
import com.rapid7.client.dcerpc.RPCException;
import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.messages.HandleResponse;
import com.rapid7.client.dcerpc.mserref.SystemErrorCode;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceConfigInfo;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceHandle;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceManagerHandle;
import com.rapid7.client.dcerpc.msvcctl.dto.ServiceStatusInfo;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceControl;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceError;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStartType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceStatusType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServiceType;
import com.rapid7.client.dcerpc.msvcctl.dto.enums.ServicesAcceptedControls;
import com.rapid7.client.dcerpc.msvcctl.messages.RChangeServiceConfigWRequest;
import com.rapid7.client.dcerpc.msvcctl.messages.RCloseServiceHandleRequest;
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
import com.rapid7.client.dcerpc.objects.WChar;
import com.rapid7.client.dcerpc.service.Service;
import com.rapid7.client.dcerpc.transport.RPCTransport;

public class ServiceControlManagerService extends Service {
    public final static int FULL_ACCESS = 0x000F003F;

    public ServiceControlManagerService(final RPCTransport transport) {
        super(transport);
    }

    public ServiceManagerHandle openServiceManagerHandle() throws IOException {
        final ROpenSCManagerWRequest request = new ROpenSCManagerWRequest(null, null, FULL_ACCESS);
        return new ServiceManagerHandle(callExpectSuccess(request, "ROpenSCManagerW").getHandle());
    }

    public boolean closeServiceManagerHandle(final ServiceManagerHandle serviceManagerHandle) throws IOException {
        return closeHandle(serviceManagerHandle);
    }

    public ServiceHandle openServiceHandle(final ServiceManagerHandle serviceManagerHandle, final String serviceName) throws IOException {
        final ROpenServiceWRequest request =
                new ROpenServiceWRequest(parseHandle(serviceManagerHandle), WChar.NullTerminated.of(serviceName), FULL_ACCESS);
        return new ServiceHandle(callExpectSuccess(request, "ROpenServiceWRequest").getHandle());
    }

    public boolean closeServiceHandle(final ServiceHandle serviceHandle) throws IOException {
        return closeHandle(serviceHandle);
    }

    private boolean closeHandle(final ContextHandle handle) throws IOException {
        if (handle == null)
            return false;
        final RCloseServiceHandleRequest request =
                new RCloseServiceHandleRequest(parseHandle(handle));
        final HandleResponse response = call(request);
        if (SystemErrorCode.ERROR_SUCCESS.is(response.getReturnValue()))
            return true;
        else if (SystemErrorCode.STATUS_INVALID_HANDLE.is(response.getReturnValue()))
            return false;
        throw new RPCException("RCloseServiceHandle", response.getReturnValue());
    }

    public IServiceStatusInfo controlService(final ServiceHandle serviceHandle, final ServiceControl action) throws IOException {
        final RControlServiceRequest request =
                new RControlServiceRequest(serviceHandle.getBytes(), action.getValue());
        final RQueryServiceStatusResponse response = callExpectSuccess(request, "RControlServiceRequest");
        return parseLPServiceStatus(response.getLpServiceStatus());
    }

    public void startService(final ServiceHandle serviceHandle) throws IOException {
        final RStartServiceWRequest request = new RStartServiceWRequest(serviceHandle.getBytes());
        callExpectSuccess(request, "RStartServiceW");
    }

    public IServiceStatusInfo stopService(final ServiceHandle serviceHandle) throws IOException {
        return controlService(serviceHandle, ServiceControl.STOP);
    }

    public IServiceStatusInfo queryService(final ServiceHandle serviceHandle) throws IOException {
        final RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(serviceHandle.getBytes());
        final RQueryServiceStatusResponse response = callExpectSuccess(request, "RQueryServiceStatus");
        return parseLPServiceStatus(response.getLpServiceStatus());
    }

    public void changeServiceConfig(final ServiceHandle serviceHandle, final IServiceConfigInfo serviceConfigInfo) throws IOException {
        final WChar.NullTerminated binaryPathName = (serviceConfigInfo.getBinaryPathName() == null) ? null :
                WChar.NullTerminated.of(serviceConfigInfo.getBinaryPathName());
        final WChar.NullTerminated loadOrderGroup = (serviceConfigInfo.getLoadOrderGroup() == null) ? null :
                WChar.NullTerminated.of(serviceConfigInfo.getLoadOrderGroup());
        final WChar.NullTerminated serviceStartName = (serviceConfigInfo.getServiceStartName() == null) ? null :
                WChar.NullTerminated.of(serviceConfigInfo.getServiceStartName());
        final WChar.NullTerminated displayName = (serviceConfigInfo.getDisplayName() == null) ? null :
                WChar.NullTerminated.of(serviceConfigInfo.getDisplayName());
        final RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle.getBytes(),
                serviceConfigInfo.getServiceType().getValue(),
                serviceConfigInfo.getStartType().getValue(),
                serviceConfigInfo.getErrorControl().getValue(),
                binaryPathName,
                loadOrderGroup,
                serviceConfigInfo.getTagId(),
                serviceConfigInfo.getDependencies(),
                serviceStartName,
                serviceConfigInfo.getPassword(),
                displayName);
        callExpectSuccess(request, "RChangeServiceConfigW");
    }

    public IServiceConfigInfo queryServiceConfig(final ServiceHandle serviceHandle)
            throws IOException {
        final RQueryServiceConfigWRequest request =
                new RQueryServiceConfigWRequest(serviceHandle.getBytes(), RQueryServiceConfigWRequest.MAX_BUFFER_SIZE);
        final RQueryServiceConfigWResponse response = callExpectSuccess(request, "RQueryServiceConfigW");
        return parseLPQueryServiceConfigW(response.getLpServiceConfig());
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
