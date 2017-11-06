/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.objects.RPC_UNICODE_STRING;

/**********************
 * Relative ID (idx) RPC_UNICODE_STRING -- length -- max length -- reference ID
 **********************
 * Deferred RPC_UNICODE_STRING -- String body
 **********************
 */
public class DomainInfo implements com.rapid7.client.dcerpc.io.ndr.Unmarshallable {

    public DomainInfo() {
    }

    private RPC_UNICODE_STRING name;

    public String getName() {
        return name.getValue();
    }

    public void setName(final RPC_UNICODE_STRING name) {
        this.name = name;
    }

    @Override
    public Alignment getAlignment() {
        return Alignment.FOUR;
    }

    @Override
    public void unmarshallPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshallEntity(PacketInput in) throws IOException {
        // relative ID.
        int relativeId = in.readInt();
        name = RPC_UNICODE_STRING.of(true);
        name.unmarshallEntity(in);
    }

    @Override
    public void unmarshallDeferrals(PacketInput in) throws IOException {
        name.unmarshallDeferrals(in);
    }
}
