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

import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import java.io.IOException;

/**

 */
public class LSAPRTranslatedSID implements Unmarshallable
{
    private int use;
    private long relativeId;
    private long domainIndex;

    @Override
    public void unmarshalPreamble(PacketInput in)
        throws IOException {
    }

    @Override
    public void unmarshalEntity(PacketInput in)
        throws IOException {
        in.align(Alignment.FOUR);
        use = in.readInt();
        relativeId = in.readUnsignedInt();
        domainIndex = in.readUnsignedInt();
    }

    @Override
    public void unmarshalDeferrals(PacketInput in)
        throws IOException {
    }

    public int getUse() {
        return use;
    }

    public long getRelativeId() {
        return relativeId;
    }

    public long getDomainIndex() {
        return domainIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LSAPRTranslatedSID that = (LSAPRTranslatedSID) o;

        if (use != that.use) return false;
        if (relativeId != that.relativeId) return false;
        return domainIndex == that.domainIndex;
    }

    @Override
    public int hashCode() {
        int result = use;
        result = 31 * result + (int) (relativeId ^ (relativeId >>> 32));
        result = 31 * result + (int) (domainIndex ^ (domainIndex >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "LSAPRTranslatedSID{" +
                "use=" + use +
                ", relativeId=" + relativeId +
                ", domainIndex=" + domainIndex +
                '}';
    }
}
