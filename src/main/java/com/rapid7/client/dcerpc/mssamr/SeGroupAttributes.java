/**
 * Copyright 2017, Rapid7, Inc.
 *
 * License: BSD-3-clause
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 */
package com.rapid7.client.dcerpc.mssamr;

/**
 * <p>
 * These values are attributes of a security group membership and can be combined by using the bitwise OR operation. They are used by an access check mechanism to specify whether the membership is to be used in an access check decision. The values can be set by using the SamrSetMemberAttributesOfGroup method.
 * </p>
 * @see <a href="https://msdn.microsoft.com/en-us/library/cc245512.aspx">SE_GROUP Attributes</a>
 */
public enum SeGroupAttributes {

    /** The SID cannot have the SE_GROUP_ENABLED attribute removed. */
    SE_GROUP_MANDATORY(0x00000001),
    /** The SID is enabled by default (rather than being added by an application). */
    SE_GROUP_ENABLED_BY_DEFAULT(0x00000002),
    /** The SID is enabled for access checks. */
    SE_GROUP_ENABLED(0x00000004);

    private final int mask;

    private SeGroupAttributes(int mask) {
        this.mask = mask;
    }

    public boolean isSet(int attributes) {
        return (mask & attributes) == mask;
    }
}
