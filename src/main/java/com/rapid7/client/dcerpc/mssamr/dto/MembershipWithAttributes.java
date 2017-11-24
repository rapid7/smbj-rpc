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
 * This class contains the relative ID and attributes for a member of a security issuer.
 */
public class MembershipWithAttributes extends Membership {

    private final int attributes;

    public MembershipWithAttributes(final long relativeID, final int attributes) {
        super(relativeID);
        this.attributes = attributes;
    }

    /**
     * @return A bit field of attributes associated with this membership.
     * If this membership is for a group, {@link GroupAttributes} can be used to inspect the values.
     */
    public int getAttributes() {
        return attributes;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + this.attributes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof MembershipWithAttributes)) {
            return false;
        }
        return super.equals(obj)
                && this.attributes == ((MembershipWithAttributes) obj).attributes;
    }

    @Override
    public String toString() {
        return String.format("MembershipWithAttributes{relativeID: %d, attributes: %d}", getRelativeID(), getAttributes());
    }
}
