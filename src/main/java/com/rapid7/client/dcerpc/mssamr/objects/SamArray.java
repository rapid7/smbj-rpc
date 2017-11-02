/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCArray;

public class SamArray<T extends Unmarshallable<T>> implements Unmarshallable<SamArray> {

    private int count;
    private RPCArray<T> array;
    private final Class<T> containedType;

    public SamArray(Class<T> clazz) {
        this.containedType = clazz;
    }

    public int getEntryCount() {
        return count;
    }

    public RPCArray<T> getArray() {
        return array;
    }

    @Override
    public SamArray unmarshall(PacketInput in) throws IOException {
        in.readReferentID();
        count = in.readInt();
        array = new RPCArray(containedType);
        array.unmarshall(in);
        return this;
    }
}
