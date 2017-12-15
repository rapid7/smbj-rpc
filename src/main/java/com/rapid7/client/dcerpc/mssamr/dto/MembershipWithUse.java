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

package com.rapid7.client.dcerpc.mssamr.dto;

import com.rapid7.client.dcerpc.dto.SIDUse;

/**
 * This class contains the relative ID and use for a member of a security issuer.
 */
public class MembershipWithUse extends Membership {

    private final SIDUse use;
    private final int useValue;

    public MembershipWithUse(final long relativeID, final int useValue) {
        super(relativeID);
        this.use = SIDUse.fromValue((short) useValue);
        this.useValue = useValue;
    }

    public SIDUse getUse() {
        return this.use;
    }

    public int getUseValue() {
        return useValue;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + this.useValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof MembershipWithUse)) {
            return false;
        }
        return super.equals(obj)
                && getUseValue() == ((MembershipWithUse) obj).getUseValue();
    }

    @Override
    public String toString() {
        final String useStr = (getUse() == null) ? "" : String.format(" (%s)", getUse());
        return String.format("MembershipWithUse{relativeID: %d, use: %d%s}", getRelativeID(),
                this.useValue, useStr);
    }
}
