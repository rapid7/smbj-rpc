/*
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 *  Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 */
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import com.rapid7.client.dcerpc.io.PacketOutput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Marshallable;
import com.rapid7.client.dcerpc.io.ndr.arrays.RPCConformantReferentArray;
import com.rapid7.client.dcerpc.objects.RPCSID;

/**
 * <b>Alignment: 4</b>
 * <a href="https://msdn.microsoft.com/en-us/library/cc245548.aspx">SAMPR_PSID_ARRAY</a>
 * <blockquote><pre>The SAMPR_PSID_ARRAY structure holds an array of SID values.
 *
 *      typedef struct _SAMPR_PSID_ARRAY {
 *          [range(0,1024)] unsigned long Count;
 *          [size_is(Count)] PSAMPR_SID_INFORMATION Sids;
 *      } SAMPR_PSID_ARRAY,
 *      *PSAMPR_PSID_ARRAY;
 *
 * Count: The number of elements in Sids. If zero, Sids MUST be ignored. If nonzero, Sids MUST point to at least Count * sizeof(SAMPR_SID_INFORMATION) bytes of memory.
 * Sids: An array of pointers to SID values. For more information, see section 2.2.3.5.</pre></blockquote>
 */
public class SAMPRPSIDArray implements Marshallable {

    private RPCSIDReferentConformantArray sids;

    public SAMPRPSIDArray(RPCSID ... sids) {
        this.sids = new RPCSIDReferentConformantArray(sids);
    }

    public RPCSID[] getSids() {
        return (this.sids == null ? null : this.sids.getArray());
    }

    @Override
    public void marshalPreamble(PacketOutput out) {
        // No preamble
    }

    @Override
    public void marshalEntity(PacketOutput out) throws IOException {
        // <NDR: unsigned long> [range(0,1024)] unsigned long Count;
        out.align(Alignment.FOUR);
        if (this.sids == null) {
            out.writeInt(0);
            out.writeNull();
        } else {
            out.writeInt(this.sids.getArray().length);
            out.writeReferentID();
        }
    }

    @Override
    public void marshalDeferrals(PacketOutput out) throws IOException {
        if (this.sids != null)
            out.writeMarshallable(this.sids);
    }

    private static class RPCSIDReferentConformantArray extends RPCConformantReferentArray<RPCSID> {

        private RPCSIDReferentConformantArray(RPCSID[] array) {
            super(array);
        }

        @Override
        protected RPCSID createEntry() {
            return new RPCSID();
        }
    }
}
