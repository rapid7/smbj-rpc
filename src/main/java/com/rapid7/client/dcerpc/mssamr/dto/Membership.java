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
 * This class contains the relative ID for a member of a security issuer.
 */
public class Membership {
    private final long relativeID;

    public Membership(final long relativeID) {
        this.relativeID = relativeID;
    }

    public long getRelativeID() {
        return relativeID;
    }

    @Override
    public int hashCode() {
        return (int) this.relativeID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof Membership)) {
            return false;
        }
        return this.relativeID == ((Membership) obj).relativeID;
    }

    @Override
    public String toString() {
        return String.format("Membership{relativeID: %d}", this.relativeID);
    }
}
