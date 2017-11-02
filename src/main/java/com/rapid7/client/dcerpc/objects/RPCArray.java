/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.Unmarshallable;

public class RPCArray<T extends Unmarshallable> extends RPCReference {

    private final List<T> list = new ArrayList<>();
    private int entries;
    private final Class<T> containedType;
    private final Queue<RPCReference> deferredObjects = new LinkedList<>();

    public RPCArray(Class<T> clazz) {
        super(RPCArray.class);
        containedType = clazz;
    }

    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public RPCArray<T> unmarshall(PacketInput in) throws IOException {
        in.readReferentID();
        entries = in.readInt();
        for (int i = 0; i < entries; i++) {
            try {
                Unmarshallable t = containedType.newInstance();
                list.add((T) t.unmarshall(in));
                if (t instanceof RPCReference) {
                    deferredObjects.add((RPCReference) t);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IOException("Failed to unmarshall object", e);
            }
        }
        if (entries != list.size())
            throw new IllegalStateException();

        for (RPCReference ref : deferredObjects) {
            ref.unmarshallData(in);
        }
        return this;
    }
}
