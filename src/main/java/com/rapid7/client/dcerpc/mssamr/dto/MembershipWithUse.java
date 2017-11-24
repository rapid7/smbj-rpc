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

/**
 * This class contains the relative ID and use for a member of a security issuer.
 */
public class MembershipWithUse extends Membership {

    private final int use;

    public MembershipWithUse(final long relativeID, final int use) {
        super(relativeID);
        this.use = use;
    }

    public int getUse() {
        return use;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + this.use;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof MembershipWithUse)) {
            return false;
        }
        return super.equals(obj)
                && this.use == ((MembershipWithUse) obj).use;
    }

    @Override
    public String toString() {
        return String.format("MembershipWithUse{relativeID: %d, use: %d}", getRelativeID(), this.use);
    }
}
