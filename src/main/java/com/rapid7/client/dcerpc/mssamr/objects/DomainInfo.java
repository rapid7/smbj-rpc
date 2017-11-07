/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPC_UNICODE_STRING;

/**
 * This class represents the structure returned with {@link EnumeratedDomains}.
 *
 * <pre>
 * Relative ID (idx)  - index
 * RPC_UNICODE_STRING - Domain Name
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc245560.aspx">
 *       https://msdn.microsoft.com/en-us/library/cc245560.aspx</a>
 */
public class DomainInfo implements Unmarshallable {

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
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // relative ID.
        in.readInt();
        name = RPC_UNICODE_STRING.of(false);
        name.unmarshalEntity(in);
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        name.unmarshalDeferrals(in);
    }
}
