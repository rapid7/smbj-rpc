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
 * This class contains the relative ID and attributes for a member of a domain.
 */
public class Membership {

    private final int relativeID;
    private final int attributes;

    public Membership(int relativeID, int attributes) {
        this.relativeID = relativeID;
        this.attributes = attributes;
    }

    public int getRelativeID() {
        return relativeID;
    }

    public int getAttributes() {
        return attributes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRelativeID(), getAttributes());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof Membership)) {
            return false;
        }
        final Membership other = (Membership) obj;
        return Objects.equals(getRelativeID(), other.getRelativeID())
                && Objects.equals(getAttributes(), other.getAttributes());
    }

    @Override
    public String toString() {
        return String.format("Membership{relativeID: %d, attributes: %d}", getRelativeID(), getAttributes());
    }
}
