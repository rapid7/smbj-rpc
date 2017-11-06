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
package com.rapid7.client.dcerpc.io.ndr;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketInput;

/**
 * Represents an NDR data type suitable for unmarshalling.
 * Unmarshalling consists of three states that are to be called in order:
 * Preamble
 * Entity
 * Deferrals
 */
public interface Unmarshallable extends DataType {
    /**
     * The preamble in NDR typically only consists of any MaximumCount fields from embedded
     * conformant arrays.
     *
     * @param in The stream to read from
     * @throws IOException On error
     */
    void unmarshallPreamble(PacketInput in) throws IOException;

    /**
     * The entity represents the DataType itself. For example:
     * * Structs: All fields (conformant array entries at the end)
     * * Pointers: Referent IDs
     * * Arrays: Size information and entries (if not conformant).
     *
     * @param in The stream to read from
     * @throws IOException On error
     */
    void unmarshallEntity(PacketInput in) throws IOException;

    /**
     * The deferrals are any data that is to be deferred to the end of this data type.
     * For pointers, this would be the reference data for non-null pointers.
     * For conformant arrays, this would be entries.
     *
     * @param in The stream to read from
     * @throws IOException On error
     */
    void unmarshallDeferrals(PacketInput in) throws IOException;
}
