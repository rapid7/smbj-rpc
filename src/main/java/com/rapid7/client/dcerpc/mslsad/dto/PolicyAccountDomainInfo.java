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
 * This class describes a server's account domain.
 */
public class PolicyAccountDomainInfo {
    private final String domainName;
    private final SID domainSID;

    public PolicyAccountDomainInfo(final String domainName, final SID domainSID) {
        this.domainName = domainName;
        this.domainSID = domainSID;
    }

    public String getDomainName() {
        return domainName;
    }

    public SID getDomainSID() {
        return this.domainSID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomainName(), this.domainSID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (! (obj instanceof PolicyAccountDomainInfo)) {
            return false;
        }
        final PolicyAccountDomainInfo other = (PolicyAccountDomainInfo) obj;
        return Objects.equals(this.domainName, other.domainName)
                && Objects.equals(this.domainSID, other.domainSID);
    }

    @Override
    public String toString() {
        final String domainNameStr = (this.domainName != null) ? String.format("\"%s\"", this.domainName) : "null";
        return String.format("PolicyAccountDomainInfo{domainName: %s, domainSID: %s}",
                domainNameStr, this.domainSID);
    }
}
