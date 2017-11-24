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
 * This class represents some general information about a group.
 */
public class GroupGeneralInformation {
    private final String name;
    private final int attributes;
    private final long memberCount;
    private final String adminComment;

    public GroupGeneralInformation(String name, int attributes, long memberCount, String adminComment) {
        this.name = name;
        this.attributes = attributes;
        this.memberCount = memberCount;
        this.adminComment = adminComment;
    }

    /**
     * @return The name of this group.
     */
    public String getName() {
        return name;
    }

    /**
     * @return A bit field of attributes associated with this group. {@link GroupAttributes} can
     * be used to inspect the values.
     */
    public int getAttributes() {
        return attributes;
    }

    /**
     * @return The number of members in this group.
     */
    public long getMemberCount() {
        return memberCount;
    }

    /**
     * @return The admin comment for this group.
     */
    public String getAdminComment() {
        return adminComment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAttributes(), getMemberCount(), getAdminComment());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof GroupGeneralInformation)) {
            return false;
        }
        GroupGeneralInformation other = (GroupGeneralInformation) obj;
        return Objects.equals(getName(), other.getName())
                && Objects.equals(getAttributes(), other.getAttributes())
                && Objects.equals(getMemberCount(), other.getMemberCount())
                && Objects.equals(getAdminComment(), other.getAdminComment());
    }

    @Override
    public String toString() {
        final String nameStr = (this.name != null) ? String.format("\"%s\"", this.name) : "null";
        final String adminCommentStr = (this.adminComment != null) ? String.format("\"%s\"", this.adminComment) : "null";
        return String.format("GroupGeneralInformation{name: %s, attributes: %d, memberCount: %d, adminComment: %s}",
                nameStr, getAttributes(), getMemberCount(), adminCommentStr);
    }
}
