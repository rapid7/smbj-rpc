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
package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.messages.RequestCall;
import com.rapid7.client.dcerpc.msrrp.messages.HandleResponse;
import com.rapid7.client.dcerpc.msvcctl.ServiceControlManagerService;
import java.io.IOException;

public class ROpenSCManagerWRequest extends RequestCall<HandleResponse>
{
    private final static short OP_NUM = 15;
    private final String name;
    private final String databaseName;
    private final int desiredAccess;

    public ROpenSCManagerWRequest() {
        this("test", null, ServiceControlManagerService.FULL_ACCESS);
    }
    public ROpenSCManagerWRequest(String name, String databaseName, int desiredAccess) {
        super(OP_NUM);
        this.name = name;
        this.databaseName = databaseName;
        this.desiredAccess = desiredAccess;

    }

    @Override public void marshal(PacketOutput packetOut)
        throws IOException
    {
        if (name != null) packetOut.writeStringRef(name, true);
        else packetOut.writeNull();
        if (databaseName != null)packetOut.writeStringRef(databaseName, true);
        else packetOut.writeNull();
        packetOut.writeInt(desiredAccess);
    }

    @Override public HandleResponse getResponseObject()
    {
        return new HandleResponse();
    }
}
