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

package com.rapid7.client.dcerpc.mslsad.dto;

import java.util.Objects;
import com.rapid7.client.dcerpc.dto.SID;

public class PolicyPrimaryDomInfo {
    private final String name;
    private final SID sid;

    public PolicyPrimaryDomInfo(final String name, final SID sid) {
        this.name = name;
        this.sid = sid;
    }

    /**
     * @return The name of this policy's primary domain. May be null.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The {@link SID} for this policy's primary domain. May be null.
     */
    public SID getSID() {
        return sid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSID());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof PolicyPrimaryDomInfo)) {
            return false;
        }
        final PolicyPrimaryDomInfo other = (PolicyPrimaryDomInfo) obj;
        return Objects.equals(getName(), other.getName())
                && Objects.equals(getSID(), other.getSID());
    }

    @Override
    public String toString() {
        return String.format("PolicyPrimaryDomInfo{name:%s,sid:%s}",
                getName(), getSID());
    }
}
