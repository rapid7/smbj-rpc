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

package com.rapid7.client.dcerpc.mslsad.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;

/* typedef struct _DOMAIN_LOGOFF_INFORMATION {
 *   OLD_LARGE_INTEGER ForceLogoff;
 * } DOMAIN_LOGOFF_INFORMATION,
 *  *PDOMAIN_LOGOFF_INFORMATION;
 */
public class SAMPRDomainLogOffInfo implements Unmarshallable {

    private int forceLogoff;

    public int getForceLogoff() {
        return forceLogoff;
    }

    public void setForceLogoff(int forceLogoff) {
        this.forceLogoff = forceLogoff;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        this.forceLogoff = in.readShort();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
    }

    @Override
    public int hashCode() {
        return Objects.hash(getForceLogoff());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SAMPRDomainLogOffInfo)) {
            return false;
        }
        SAMPRDomainLogOffInfo other = (SAMPRDomainLogOffInfo) obj;
        return Objects.equals(getForceLogoff(), other.getForceLogoff());
    }

    @Override
    public String toString() {
        return String.format("SAMPRDomainLogOffInfo{forceLogoff:%s}", getForceLogoff());
    }
}
