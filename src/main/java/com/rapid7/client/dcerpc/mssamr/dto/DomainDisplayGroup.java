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
 * This class represents domain display information for a group.
 */
public class DomainDisplayGroup extends DomainDisplay {

    private final int attributes;

    public DomainDisplayGroup(final long relativeID, final String name, final String comment, int attributes) {
        super(relativeID, name, comment);
        this.attributes = attributes;
    }

    /**
     * @return A bit field of attributes associated with this group. {@link GroupAttributes} can
     * be used to inspect the values.
     */
    public int getAttributes() {
        return attributes;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() * 31) + getAttributes();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof DomainDisplayGroup)) {
            return false;
        }
        return super.equals(obj)
                && getAttributes() == ((DomainDisplayGroup) obj).getAttributes();
    }

    @Override
    public String toString() {
        final String nameStr = (getName() != null) ? String.format("\"%s\"", getName()) : "null";
        final String commentStr = (getComment() != null) ? String.format("\"%s\"", getComment()) : "null";
        return String.format("DomainDisplayGroup{relativeID: %d, name: %s, comment: %s, attributes: %d}",
                getRelativeID(), nameStr, commentStr, getAttributes());
    }
}
