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
package com.rapid7.client.dcerpc;

import com.hierynomus.protocol.commons.EnumWithValue;

/**
 * This document defines the following values for the common header pfc_flags field.<br>
 * <br>
 *
 * <pre>
 * #define PFC_FIRST_FRAG           0x01 // First fragment
 * #define PFC_LAST_FRAG            0x02 // Last fragment
 * #define PFC_PENDING_CANCEL       0x04 // Cancel was pending at sender
 * #define PFC_RESERVED_1           0x08
 * #define PFC_CONC_MPX             0x10 // supports concurrent multiplexing of a single connection.
 * #define PFC_DID_NOT_EXECUTE      0x20 // only meaningful on `fault' packet; if true, guaranteed call did not execute.
 * #define PFC_MAYBE                0x40 // `maybe' call semantics requested
 * #define PFC_OBJECT_UUID          0x80 // if true, a non-nil object UUID was specified in the handle, and is present
 *                                       // in the optional object field. If false, the object field is omitted.
 * </pre>
 *
 * See: http://pubs.opengroup.org/onlinepubs/009629399/chap12.htm
 */
public enum PFCFlag implements EnumWithValue<PFCFlag> {
    /**
     * First fragment.
     */
    FIRST_FRAGMENT(0x01),
    /**
     * Last fragment.
     */
    LAST_FRAGMENT(0x02),
    /**
     * Cancel was pending at sender.
     */
    PENDING_CANCEL(0x04),
    /**
     * Supports concurrent multiplexing of a single connection.
     */
    CONCURRENT_MULTIPLEXING(0x10),
    /**
     * Only meaningful on `fault' packet; if true, guaranteed call did not execute.
     */
    DID_NOT_EXECUTE(0x20),
    /**
     * "maybe" call semantics requested.
     */
    MAYBE(0x40),
    /**
     * If true, a non-nil object UUID was specified in the handle, and is present in the optional object field. If
     * false, the object field is omitted.
     */
    OBJECT_UUID(0x80);

    private final int value;

    PFCFlag(final int value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return value;
    }
}
