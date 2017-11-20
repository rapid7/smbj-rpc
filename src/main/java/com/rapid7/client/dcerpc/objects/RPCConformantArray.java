/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.objects;

import java.io.IOException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * <p>A conformant array is an array in which the maximum number of elements is not known beforehand and therefore is included in the representation of the array.</p>
 *
 * <p>NDR represents a conformant array as an ordered sequence of representations of the array elements, preceded by an unsigned long integer. The integer gives the number of array elements transmitted, including empty elements.</p>
 *
 * <p>A conformant array can contain at most 2^(32-1) elements.</p>
 *
 * <p>Uni-dimensional Conformant Array Representation illustrates a conformant array as it appears in the octet stream.</p>
 */
public abstract class RPCConformantArray<T extends Unmarshallable> implements Unmarshallable {

    /**
     * 4-byte unsigned. Int is used instead of long since Java does not support initialize ArrayList with
     * long.
     */
    private int entriesRead;

    private List<T> array;

    public RPCConformantArray() {
    }

    /**
     * Gets the number of entries read in the buffer.
     *
     * @return Number of entries read. May be {@code null} if the response is not processed.
     */
    public int getEntriesRead() {
        return entriesRead;
    }

    /**
     * Gets the entries contained in the buffer as a list.
     *
     * @return The entries. May be {@code null} if the response is not processed.
     */
    public List<T> getEntries() {
        if (array == null)
            return null;
        return Collections.unmodifiableList(array);
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        // <NDR: unsigned long> unsigned long EntriesRead;
        entriesRead = in.readInt();
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment
        in.align(Alignment.FOUR);
        // <NDR: pointer> [size_is(EntriesRead)] PSAMPR_RID_ENUMERATION Buffer;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0) {
            if (entriesRead > 0)
                array = new ArrayList<>(entriesRead);
            else
                array = Collections.emptyList();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // Entries of domain_list
        if (array != null) {
            // MaximumCount
            in.align(Alignment.FOUR);
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                T t = createEntity();
                array.add(t);
                t.unmarshalPreamble(in);
            }
            for (T t : array) {
                t.unmarshalEntity(in);
            }
            for (T t : array) {
                t.unmarshalDeferrals(in);
            }
        }
    }

    /**
     * Initiate the entity instance contained in the buffer.
     *
     * @return The entity instance in the buffer.
     * @throws UnmarshalException when unable to instantiate buffer
     */
    protected abstract T createEntity() throws UnmarshalException;
}
