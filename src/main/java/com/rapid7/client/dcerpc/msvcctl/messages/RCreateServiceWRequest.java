package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msvcctl.enums.ServiceManagerAccessLevel;
import com.rapid7.client.dcerpc.msvcctl.objects.IServiceConfigInfo;
import com.rapid7.client.dcerpc.objects.ContextHandle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RCreateServiceWRequest extends RequestCall<RCreateServiceWResponse> {
    private final static short OP_NUM = 12;
    private ContextHandle serviceHandle;
    private IServiceConfigInfo serviceConfigInfo;
    private int access;
    private String serviceName;

    public RCreateServiceWRequest(
        final ContextHandle handle,
        final IServiceConfigInfo serviceConfigInfo,
        final ServiceManagerAccessLevel managerAccessLevel,
        final String serviceName){
        super(OP_NUM);
        this.serviceHandle = handle;
        this.serviceConfigInfo = serviceConfigInfo;
        this.access = managerAccessLevel.getValue();
        this.serviceName = serviceName;
    }

    @Override
    public void marshal(PacketOutput packetOut) throws IOException {
        packetOut.write(serviceHandle.getBytes());
        if (serviceName!= null)
            packetOut.writeString(serviceName, true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getDisplayName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getDisplayName(), true);
        else packetOut.writeNull();

        packetOut.writeInt(access);

        packetOut.writeInt(serviceConfigInfo.getServiceType().getValue());
        packetOut.writeInt(serviceConfigInfo.getStartType().getValue());
        packetOut.writeInt(serviceConfigInfo.getErrorControl().getValue());
        if (serviceConfigInfo.getBinaryPathName() != null)
            packetOut.writeString(serviceConfigInfo.getBinaryPathName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getLoadOrderGroup() != null)
            packetOut.writeStringRef(serviceConfigInfo.getLoadOrderGroup(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getTagId() != 0) packetOut.writeIntRef(serviceConfigInfo.getTagId());
        else packetOut.writeNull();

        if (serviceConfigInfo.getDependencies() != null) {
            packetOut.writeReferentID();
            packetOut.writeInt(serviceConfigInfo.getDependencies().getBytes().length); //conformat array, count
            packetOut.write(serviceConfigInfo.getDependencies().getBytes());
            packetOut.align();
            //dependency size
            packetOut.writeInt(serviceConfigInfo.getDependencies().getBytes().length);
        } else {
            packetOut.writeNull();
            //dependency size
            packetOut.writeInt(0);
        }

        if (serviceConfigInfo.getServiceStartName() != null)
            packetOut.writeStringRef(serviceConfigInfo.getServiceStartName(), true);
        else packetOut.writeNull();

        if (serviceConfigInfo.getPassword() != null) {
            // This is not working.  The password may need to be encrypted according to MS-SCMR
            //
            packetOut.writeReferentID();
            packetOut.writeInt(serviceConfigInfo.getPassword().length());
            packetOut.write(serviceConfigInfo.getPassword().getBytes());
            packetOut.align();
            //password size
            packetOut.writeInt(serviceConfigInfo.getPassword().length());
        } else {
            packetOut.writeNull();
            //password size
            packetOut.writeInt(0);
        }


    }

    @Override
    public RCreateServiceWResponse getResponseObject() {
        return new RCreateServiceWResponse();
    }

}
