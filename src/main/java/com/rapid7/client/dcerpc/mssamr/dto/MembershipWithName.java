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

import java.util.Objects;

/**
 * This class contains the relative ID and name for a member of a security issuer.
 */
public class MembershipWithName extends Membership {

    private final String name;

    public MembershipWithName(final long relativeID, final String name) {
        super(relativeID);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + Objects.hashCode(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof MembershipWithName)) {
            return false;
        }
        return super.equals(obj) &&
                Objects.equals(this.name, ((MembershipWithName) obj).name);
    }

    @Override
    public String toString() {
        final String nameStr = (this.name != null) ? String.format("\"%s\"", this.name) : "null";
        return String.format("MembershipWithName{relativeID: %d, name: %s}", getRelativeID(), nameStr);
    }
}
