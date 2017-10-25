/***************************************************************************
 * COPYRIGHT (C) 2017, Rapid7 LLC, Boston, MA, USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Rapid7.
 **************************************************************************/
package com.rapid7.client.dcerpc.io;

import java.io.IOException;

/**
 * An interface for unmarshalling typed objects from {@link PacketInput}.
 */
public interface Unmarshallable<T> {

    public T unmarshall(PacketInput in) throws IOException;
}
