/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.Unmarshallable;

public class RPCReference<T extends Unmarshallable<T>> {

    private T object;
    private final Class<T> clazz;

    public RPCReference(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T getReferent() {
        return object;
    }

    public Class<T> getType() {
        return clazz;
    }

    public RPCReference unmarshall(PacketInput in) throws IOException {
        in.readReferentID();
        return this;
    }

    public void unmarshallData(PacketInput in) throws IOException {
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        object.unmarshall(in);
    }
}
