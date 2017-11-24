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
 * This class represents domain display information.
 * Specifically this is applicable for:
 *  * Users: DomainDisplayUser
 *  * Machines: DomainDisplayMachine
 *  * Groups: DomainDisplayGroup
 * OEM users and groups are not captured by this concept.
 */
public class DomainDisplay extends Membership {

    private final String name;
    private final String comment;

    public DomainDisplay(final long relativeID, final String name, final String comment) {
        super(relativeID);
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public int hashCode() {
        int ret = super.hashCode();
        ret = (ret * 31) + Objects.hashCode(getName());
        ret = (ret * 31) + Objects.hash(getComment());
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof DomainDisplay)) {
            return false;
        }
        final DomainDisplay other = (DomainDisplay) obj;
        return super.equals(obj)
                && Objects.equals(getName(), other.getName())
                && Objects.equals(getComment(), other.getComment());
    }

    @Override
    public String toString() {
        final String nameStr = (getName() != null) ? String.format("\"%s\"", getName()) : "null";
        final String commentStr = (getComment() != null) ? String.format("\"%s\"", getComment()) : "null";
        return String.format("DomainDisplay{relativeID: %d, name: %s, comment: %s}",
                getRelativeID(), nameStr, commentStr);
    }
}
