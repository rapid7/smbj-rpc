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

package com.rapid7.client.dcerpc.mssrvs.objects;

import java.io.IOException;
import java.util.Objects;
import com.rapid7.client.dcerpc.io.PacketInput;
import com.rapid7.client.dcerpc.io.ndr.Alignment;
import com.rapid7.client.dcerpc.io.ndr.Unmarshallable;
import com.rapid7.client.dcerpc.objects.WChar;

public class ShareInfo implements Unmarshallable {
    // <NDR: pointer[struct]> wchar_t* shi0_netname;
    private WChar.NullTerminated netName;

    public WChar.NullTerminated getNetName() {
        return this.netName;
    }

    public void setNetName(final WChar.NullTerminated netName) {
        this.netName = netName;
    }

    @Override
    public void unmarshalPreamble(PacketInput in) throws IOException {
        //No preamble
    }

    @Override
    public void unmarshalEntity(PacketInput in) throws IOException {
        // Structure Alignment: 4
        // NOTE: It is assumed that all ShareInfo instances have the same alignment
        in.align(Alignment.FOUR);
        // <NDR: pointer[struct]> wchar_t* shi0_netname;
        // Alignment: 4 - Already aligned
        if (in.readReferentID() != 0)
            this.netName = new WChar.NullTerminated();
        else
            this.netName = null;
    }

    @Override
    public void unmarshalDeferrals(PacketInput in) throws IOException {
        if (this.netName != null)
            in.readUnmarshallable(this.netName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.netName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof ShareInfo)) {
            return false;
        }
        final ShareInfo other = (ShareInfo) obj;
        return Objects.equals(this.netName, other.netName);
    }
}
