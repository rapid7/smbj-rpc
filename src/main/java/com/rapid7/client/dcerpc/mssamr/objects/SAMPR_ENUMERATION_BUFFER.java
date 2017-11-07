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
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/**
 * This is an abstract class that represents the SAMPR_ENUMERATION_BUFFER. The initEntity
 * method must be overriden to instantiate the instance contained in the buffer.
 *
 * The SAMPR_ENUMERATION_BUFFER structure holds an array of SAMPR_RID_ENUMERATION elements.
 *
 * <pre>
 * typedef struct _SAMPR_ENUMERATION_BUFFER {
 *   unsigned long EntriesRead;
 *   [size_is(EntriesRead)] PSAMPR_RID_ENUMERATION Buffer;
 * } SAMPR_ENUMERATION_BUFFER,
 *   *PSAMPR_ENUMERATION_BUFFER;
 * </pre>
 *
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc245561.aspx">
 *       https://msdn.microsoft.com/en-us/library/cc245561.aspx</a>
 */
public abstract class SAMPR_ENUMERATION_BUFFER<T extends Unmarshallable> implements Unmarshallable {

    private Integer entriesRead;
    private List<T> array;

    public SAMPR_ENUMERATION_BUFFER() {
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
            throw new IllegalStateException("");
        return Collections.unmodifiableList(array);
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
        // Entries of sam_array
        entriesRead = in.readInt();
        // Reference ID of domain_list
        if (in.readReferentID() != 0) {
            if (entriesRead > 0)
                array = new ArrayList<>(entriesRead);
        }

    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        // Entries of domain_list
        if (array != null) {
            // MaximumCount
            int count = in.readInt();
            for (int i = 0; i < count; i++) {
                T t = initEntity();
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
     */
    protected abstract T initEntity();
}
