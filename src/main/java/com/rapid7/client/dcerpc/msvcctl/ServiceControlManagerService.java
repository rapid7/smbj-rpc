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

    private final RPCTransport transport;
    private ContextHandle serviceManagerHandle;

    public ServiceControlManagerService(final RPCTransport transport) {
        this.transport = transport;
    }

    private void getServiceManagerHandle()
        throws IOException {
        ROpenSCManagerWRequest request = new ROpenSCManagerWRequest();
        HandleResponse response = transport.call(request);
        serviceManagerHandle = response.getHandle();
    }

    private ContextHandle getServiceHandle(String serviceName)
        throws IOException {
        ROpenServiceWRequest request = new ROpenServiceWRequest(serviceManagerHandle, serviceName);
        HandleResponse response = transport.call(request);
        return response.getHandle();
    }

    public boolean startService(String service)
        throws IOException {
        if (serviceManagerHandle == null) getServiceManagerHandle();
        ContextHandle serviceHandle = getServiceHandle(service);
        RStartServiceWRequest request = new RStartServiceWRequest(serviceHandle);
        EmptyResponse response = transport.call(request);
        return (response.getReturnValue().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode()));
    }

    public RQueryServiceStatusInfo queryService(String service)
        throws IOException {
        if (serviceManagerHandle == null) getServiceManagerHandle();
        ContextHandle serviceHandle = getServiceHandle(service);
        RQueryServiceStatusRequest request = new RQueryServiceStatusRequest(serviceHandle);
        RQueryServiceStatusResponse response = transport.call(request);
        if (response.getReturnCode().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode())){
            return response.getrQueryServiceStatusInfo();
        } else return null;
    }

    public RQueryServiceStatusInfo stopService(String service)
        throws IOException {
        if (serviceManagerHandle == null) getServiceManagerHandle();
        ContextHandle serviceHandle = getServiceHandle(service);
        RControlServiceRequest request = new RControlServiceRequest(serviceHandle, RControlServiceRequest.SERVICE_CONTROL_STOP);
        RQueryServiceStatusResponse response = transport.call(request);
        if (response.getReturnCode().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode())){
            return response.getrQueryServiceStatusInfo();
        } else return null;
    }

    public boolean changeServiceConfig(String service, int serviceType, int startType, int errorControl)
        throws IOException {
        if (serviceManagerHandle == null) getServiceManagerHandle();
        ContextHandle serviceHandle = getServiceHandle(service);
        RChangeServiceConfigWRequest request = new RChangeServiceConfigWRequest(serviceHandle, serviceType, startType, errorControl);
        RChangeServiceConfigWResponse response = transport.call(request);
        return (response.getReturnCode().is(SystemErrorCode.ERROR_SUCCESS.getErrorCode()));

    }
}
