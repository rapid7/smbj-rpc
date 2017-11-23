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

/**
 * This class describes a server's domain.
 */
public class PolicyDomainInfo {
    private final String domainName;
    private final SID domainSID;

    public PolicyDomainInfo(final String domainName, final SID domainSID) {
        this.domainName = domainName;
        this.domainSID = domainSID;
    }

    /**
     * @return The name of this policy's primary domain. May be null.
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * @return The {@link SID} for this policy's primary domain. May be null.
     */
    public SID getDomainSID() {
        return domainSID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.domainName, this.domainSID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof PolicyDomainInfo)) {
            return false;
        }
        final PolicyDomainInfo other = (PolicyDomainInfo) obj;
        return Objects.equals(this.domainName, other.domainName)
                && Objects.equals(this.domainSID, other.domainSID);
    }

    @Override
    public String toString() {
        final String domainNameStr = (this.domainName != null) ? String.format("\"%s\"", this.domainName) : "null";
        return String.format("PolicyPrimaryDomainInfo{domainName: %s, domainSID: %s}",
                domainNameStr, this.domainSID);
    }
}
