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
package com.rapid7.client.dcerpc.mssamr.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.RPCSID;

/*
 * <a href="https://https://msdn.microsoft.com/en-us/library/cc245547.aspx">SAMPR_SID_INFORMATION</a>
 *
 * The SAMPR_SID_INFORMATION structure holds a SID pointer.
 *
 * <pre>
 * typedef struct _SAMPR_SID_INFORMATION {
 * PRPC_SID SidPointer;
 * } SAMPR_SID_INFORMATION,
 * *PSAMPR_SID_INFORMATION;
 * </pre>
 *
 * SidPointer: A pointer to a SID value, as described in [MS-DTYP] section 2.4.2.3.
 */

public class SAMPRSIDInformation implements Unmarshallable {
    // <NDR: pointer> PRPC_SID Sid;
    private RPCSID sidPointer;

    public RPCSID getSidPointer() {
        return sidPointer;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        in.align(Alignment.FOUR);
        // <NDR: pointer> PRPC_SID Sid;
        if (in.readReferentID() != 0) {
            this.sidPointer = new RPCSID();
        }
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSidPointer());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRSIDInformation)) {
            return false;
        }
        SAMPRSIDInformation other = (SAMPRSIDInformation) obj;
        return Objects.equals(getSidPointer(), other.getSidPointer());
    }

    @Override
    public String toString() {
        return String.format("SAMPRSIDInformation{sidPointer:%s}", getSidPointer());
    }

}
