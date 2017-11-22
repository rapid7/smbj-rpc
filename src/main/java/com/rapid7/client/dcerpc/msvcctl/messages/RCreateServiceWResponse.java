package com.rapid7.client.dcerpc.msvcctl.messages;

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.messages.HandleResponse;

import java.io.IOException;

public class RCreateServiceWResponse extends HandleResponse{
    private int tagId;

    @Override
    public void unmarshal(PacketInput packetIn) throws IOException {
        int tagIdRefId = packetIn.readReferentID();
        if (tagIdRefId != 0) tagId = packetIn.readInt();
        packetIn.readUnmarshallable(getHandle());
    }

    public int getTagId() {
        return tagId;
    }
}
