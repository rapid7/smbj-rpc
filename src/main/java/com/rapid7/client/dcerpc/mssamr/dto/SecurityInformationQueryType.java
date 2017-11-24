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

import com.rapid7.client.dcerpc.dto.ContextHandle;
import com.rapid7.client.dcerpc.mssamr.SecurityAccountManagerService;

/**
 * This class represents a subset of SecurityInformation that is used for queries
 * to SamrQuerySecurityObject ({@link SecurityAccountManagerService#getSecurityObject(ContextHandle, SecurityInformationQueryType...)}.
 */
public enum SecurityInformationQueryType {
    OWNER_SECURITY_INFORMATION((byte) 1),
    GROUP_SECURITY_INFORMATION((byte) 2),
    DACL_SECURITY_INFORMATION((byte) 4),
    SACL_SECURITY_INFORMATION((byte) 8);

    private final byte value;

    SecurityInformationQueryType(final byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public boolean isSet(final int bitfield) {
        return (bitfield & getValue()) != 0;
    }
}
