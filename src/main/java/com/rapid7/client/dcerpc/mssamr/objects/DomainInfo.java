/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCReference;

public class DomainInfo extends RPCReference implements Unmarshallable<DomainInfo> {

    public DomainInfo() {
        super(DomainInfo.class);
    }

    private String name;

    public String getName() {
        return name;
    }

    @Override
    public DomainInfo unmarshall(PacketInput in) throws IOException {
        // Entry index
        in.readInt();
        // Length
        in.readShort();
        // Max length
        in.readShort();
        // Reference ID.
        in.readReferentID();
        return this;
    }

    @Override
    public void unmarshallData(PacketInput in) throws IOException {
        name = in.readString(true);
    }
}
